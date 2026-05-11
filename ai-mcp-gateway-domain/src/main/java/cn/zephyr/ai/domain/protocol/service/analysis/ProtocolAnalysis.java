package cn.zephyr.ai.domain.protocol.service.analysis;

import cn.zephyr.ai.domain.protocol.model.entity.AnalysisCommandEntity;
import cn.zephyr.ai.domain.protocol.model.valobj.enums.AnalysisTypeEnum;
import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import cn.zephyr.ai.domain.protocol.service.IProtocolAnalysis;
import cn.zephyr.ai.domain.protocol.service.analysis.strategy.IProtocolAnalysisStrategy;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Zhulejun @Zephyr
 * @description 协议解析服务
 * @create 2026/5/11 上午11:55
 */
@Service
public class ProtocolAnalysis implements IProtocolAnalysis {

    private static final Logger log = LoggerFactory.getLogger(ProtocolAnalysis.class);
    private final Map<String, IProtocolAnalysisStrategy> protocolAnalysisStrategyMap;

    //构造方法注入
    public ProtocolAnalysis(Map<String, IProtocolAnalysisStrategy> protocolAnalysisStrategyMap) {
        this.protocolAnalysisStrategyMap = protocolAnalysisStrategyMap;
    }

    @Override
    public List<HTTPProtocolVO> doAnalysis(AnalysisCommandEntity commandEntity) {
        log.info("协议解析请求 endpoint：{}", JSON.toJSONString(commandEntity.getEndpoints()),commandEntity.getOpenApiJson());

        List<HTTPProtocolVO> list = new ArrayList<>();

        try{
            JSONObject root = JSON.parseObject(commandEntity.getOpenApiJson());
            String baseUrl = root.getJSONArray("servers").getJSONObject(0).getString("url");
            JSONObject paths = root.getJSONObject("paths");
            JSONObject schemas = root.getJSONObject("components").getJSONObject("schemas");

            List<String> endpoint =commandEntity.getEndpoints();
            if(null == endpoint||endpoint.isEmpty()){
                return list;
            }

            for(String end : endpoint){
                JSONObject pashItem = paths.getJSONObject(end);
                if(null == pashItem){
                    continue;
                }

                String method = detectMethod(pashItem);
                JSONObject operation = pashItem.getJSONObject(method);

                HTTPProtocolVO vo = new HTTPProtocolVO();
                vo.setHttpUrl(baseUrl + endpoint);
                vo.setHttpMethod(method);
                vo.setHttpHeaders(JSON.toJSONString(new HashMap<>(){{
                    put("Content-Type", "application/json");
                }}));
                vo.setTimeout(30000);

                List<HTTPProtocolVO.ProtocolMapping> mappings = new ArrayList<>();

                //枚举策略动作处理
                AnalysisTypeEnum.SwaggerAnalysisAction analysisAction = AnalysisTypeEnum.SwaggerAnalysisAction.get(operation);
                IProtocolAnalysisStrategy strategy = protocolAnalysisStrategyMap.get(analysisAction.getCode());
                strategy.doAnalysis(operation, schemas, mappings);

                vo.setMappings(mappings);
                list.add(vo);

            }

        }catch (Exception e){
            log.error("协议解析失败 endpoints:{} openApiJson:{}", JSON.toJSONString(commandEntity.getEndpoints()), commandEntity.getOpenApiJson(), e);
        }
        return list;
    }

    private String detectMethod(JSONObject pathItem){
        if (pathItem.containsKey("post")) {
            return "post";
        }

        if(pathItem.containsKey("get")){
            return "get";
        }

        if(pathItem.containsKey("put")){
            return "put";
        }

        if(pathItem.containsKey("delete")){
            return "delete";
        }

        return "post";
    }

}

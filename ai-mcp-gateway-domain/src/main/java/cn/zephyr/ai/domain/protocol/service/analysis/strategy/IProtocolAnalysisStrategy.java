package cn.zephyr.ai.domain.protocol.service.analysis.strategy;

import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议解析策略接口
 * @create 2026/5/11 上午11:55
 */
@Service
public interface IProtocolAnalysisStrategy {
    /**
     * 解析并构建 HTTP 协议参数映射关系
     * @param operation
     * @param definition
     * @param mappings
     */
    void doAnalysis(JSONObject operation, JSONObject definition, List<HTTPProtocolVO.ProtocolMapping> mappings);
}

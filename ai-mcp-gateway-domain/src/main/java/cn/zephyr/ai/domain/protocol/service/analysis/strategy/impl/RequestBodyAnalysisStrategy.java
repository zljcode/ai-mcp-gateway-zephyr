package cn.zephyr.ai.domain.protocol.service.analysis.strategy.impl;

import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import cn.zephyr.ai.domain.protocol.service.analysis.strategy.AbstractProtocolAnalysisStrategy;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 解析策略
 * @create 2026/5/11 上午11:54
 */
@Slf4j
@Component("requestBodyAnalysis")
@Order(1)
public class RequestBodyAnalysisStrategy extends AbstractProtocolAnalysisStrategy {


    @Override
    public void doAnalysis(JSONObject operation, JSONObject definition, List<HTTPProtocolVO.ProtocolMapping> mappings) {
        JSONObject requestBody = operation.getJSONObject("requestBody");
        if (requestBody == null) {
            return;
        }

        JSONObject content = requestBody.getJSONObject("content");
        JSONObject appJson = content.getJSONObject("application/json");
        if (appJson == null) {
            return;
        }

        JSONObject schema = appJson.getJSONObject("schema");
        String ref = schema.getString("$ref");

        if (ref != null) {
            String refName = ref.substring(ref.lastIndexOf("/") + 1);
            JSONObject reqSchema = definition.getJSONObject(refName);
            String rootName = toLowerCamel(refName);

            HTTPProtocolVO.ProtocolMapping rootMapping = HTTPProtocolVO.ProtocolMapping.builder()
                    .mappingType("request")
                    .parentPath(null)
                    .fieldName(rootName)
                    .mcpPath(rootName)
                    .mcpType(convertType(reqSchema.getString("type")))
                    .mcpDesc(reqSchema.getString("description"))
                    .isRequired(1)
                    .sortOrder(1)
                    .build();
            mappings.add(rootMapping);

            parseProperties(rootName, reqSchema.getJSONObject("properties"), reqSchema.getJSONArray("required"), definition, mappings);
        }

    }
}

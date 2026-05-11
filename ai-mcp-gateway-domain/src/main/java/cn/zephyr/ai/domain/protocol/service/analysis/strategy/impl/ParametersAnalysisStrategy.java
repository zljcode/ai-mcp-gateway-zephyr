package cn.zephyr.ai.domain.protocol.service.analysis.strategy.impl;

import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import cn.zephyr.ai.domain.protocol.service.analysis.strategy.AbstractProtocolAnalysisStrategy;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 解析策略
 * @create 2026/5/11 上午11:53
 */
@Slf4j
@Component("parametersAnalysis")
@Order(2)
public class ParametersAnalysisStrategy extends AbstractProtocolAnalysisStrategy {


    @Override
    public void doAnalysis(JSONObject operation, JSONObject definition, List<HTTPProtocolVO.ProtocolMapping> mappings) {
        JSONArray parameter = operation.getJSONArray("parameters");
        if (null == parameter) {
            return;
        }

        for (int i = 0; i < parameter.size(); i++) {
            JSONObject param = parameter.getJSONObject(i);
            String in = param.getString("in");

            if (!"query".equals(in) && !"path".equals(in)) {
                continue;
            }

            String name = param.getString("name");
            boolean required = param.getBooleanValue("required");
            String description = param.getString("description");

            JSONObject schema = param.getJSONObject("schema");
            String type = schema.getString("type");
            String ref = schema.getString("$ref");

            if (ref != null) {
                String refName = ref.substring(ref.lastIndexOf("/") + 1);
                JSONObject reqSchema = definition.getJSONObject(refName);

                if (type == null) {
                    type = reqSchema.getString("type");
                }
                if (description == null) {
                    description = reqSchema.getString("description");
                }

                HTTPProtocolVO.ProtocolMapping mapping = HTTPProtocolVO.ProtocolMapping.builder()
                        .mappingType("request")
                        .parentPath(null)
                        .fieldName(name)
                        .mcpPath(name)
                        .mcpType(convertType(type))
                        .mcpDesc(description)
                        .isRequired(required ? 1 : 0)
                        .sortOrder(mappings.size() + 1)
                        .build();

                mappings.add(mapping);

                parseProperties(name, reqSchema.getJSONObject("properties"), reqSchema.getJSONArray("required"), definition, mappings);
            } else {
                HTTPProtocolVO.ProtocolMapping mapping = HTTPProtocolVO.ProtocolMapping.builder()
                        .mappingType("request")
                        .parentPath(null)
                        .fieldName(name)
                        .mcpPath(name)
                        .mcpType(convertType(type))
                        .mcpDesc(description)
                        .isRequired(required ? 1 : 0)
                        .sortOrder(mappings.size() + 1)
                        .build();
                mappings.add(mapping);
            }
        }
    }
}

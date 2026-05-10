package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.adapter.repository.ISessionRepository;
import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayToolConfigVO;
import cn.zephyr.ai.domain.session.service.ISessionMessageService;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import jakarta.annotation.Resource;
import jakarta.websocket.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Zhulejun @Zephyr
 * @description 返回服务器支持的工具列表
 * @create 2026/5/9 下午4:43
 */
@Service("toolsListHandler")
public class ToolsListHandler implements IRequestHandler {
    @Resource
    private ISessionRepository sessionRepository;

    @Override
    public McpSchemaVO.JSONRPCResponse handle(String gatewayId, McpSchemaVO.JSONRPCRequest message) {
        //1、网关配置
        McpGatewayConfigVO mcpGatewayConfigVO = sessionRepository.queryMcpGatewayConfigByGatewayId(gatewayId);

        //2、查询网关（gatewayId）下的工具配置
        List<McpGatewayToolConfigVO> mcpGatewayToolConfigVO = sessionRepository.queryMcpGatewayToolConfigListByGatewayId(gatewayId);

        //3、构建工具列表
        List<McpSchemaVO.Tool> tools = buildTools(mcpGatewayConfigVO, mcpGatewayToolConfigVO);

        return new McpSchemaVO.JSONRPCResponse("2.0", message.id(), Map.of(
                "tools", tools),
                null);
    }

    /**
     * 转换为MAP结构
     *
     * @param gatewayConfig
     * @param toolConfigs
     * @return
     */
    public List<McpSchemaVO.Tool> buildTools(McpGatewayConfigVO gatewayConfig, List<McpGatewayToolConfigVO> toolConfigs) {
        //1、通过toolId 一组转换为map结构
        Map<Long, List<McpGatewayToolConfigVO>> toolsmMap = toolConfigs.stream().collect(Collectors.groupingBy(McpGatewayToolConfigVO::getToolId));

        List<McpSchemaVO.Tool> tools = new ArrayList<>();

        for (Map.Entry<Long, List<McpGatewayToolConfigVO>> entry : toolsmMap.entrySet()) {
            Long toolId = entry.getKey();
            List<McpGatewayToolConfigVO> configs = entry.getValue();

            //排序
            configs.sort((o1, o2) -> {
                int s1 = o1.getSortOrder() != null ? o1.getSortOrder() : 0;
                int s2 = o2.getSortOrder() != null ? o2.getSortOrder() : 0;
                return Integer.compare(s1, s2);
            });

            //父子元素 Map parentPath -> List<Children>
            Map<String, List<McpGatewayToolConfigVO>> childerenMap = new HashMap<>();

            List<McpGatewayToolConfigVO> roots = new ArrayList<>();

            for (McpGatewayToolConfigVO config : configs) {
                if (config.getParentPath() == null) {
                    roots.add(config);
                } else {
                    childerenMap.computeIfAbsent(config.getParentPath(), k -> new ArrayList<>()).add(config);
                }
            }

            // 排序
            roots.sort((o1, o2) -> {
                int s1 = o1.getSortOrder() != null ? o1.getSortOrder() : 0;
                int s2 = o2.getSortOrder() != null ? o2.getSortOrder() : 0;
                return Integer.compare(s1, s2);
            });

            //构建输入结构
            Map<String, Object> properties = new HashMap<>();
            List<String> required = new ArrayList<>();

            for (McpGatewayToolConfigVO root : roots) {
                properties.put(root.getFieldName(), buildProperty(root, childerenMap));
                if (Integer.valueOf(1).equals(root.getIsRequired())) {
                    required.add(root.getFieldName());
                }
            }

            //获取类型
            String type = roots.size() == 1 ? roots.get(0).getMcpType() : "object";

            //构造函数
            McpSchemaVO.JsonSchema inputSchema = new McpSchemaVO.JsonSchema(
                    type,
                    properties,
                    required.isEmpty() ? null : required,
                    false,
                    null,
                    null
            );

            //工具描述
            String name = "unknown-tool-" + toolId;
            String desc = "";
            if (gatewayConfig != null && Objects.equals(gatewayConfig.getToolId(), toolId)) {
                name = gatewayConfig.getToolName();
                desc = gatewayConfig.getToolDesc();
            }

            tools.add(new McpSchemaVO.Tool(name, desc, inputSchema));

        }
        return tools;
    }
    /**
     * 递归构建 JSON Schema 格式的参数属性定义
     * @param current 当前配置节点
     * @param childrenMap 子配置映射表，key为父节点路径，value为子节点列表
     * @return JSON Schema 格式的属性定义，包含类型、描述、子属性及必填字段
     */
    private Map<String, Object> buildProperty(McpGatewayToolConfigVO current, Map<String, List<McpGatewayToolConfigVO>> childrenMap) {
        Map<String, Object> properties = new HashMap<>();
        if (current.getMcpType() != null) {
            properties.put("type", current.getMcpType());
        }
        if (current.getMcpDesc() != null && !current.getMcpDesc().isBlank()) {
            properties.put("description", current.getMcpDesc());
        }

        //校验孩子元素
        List<McpGatewayToolConfigVO> children = childrenMap.get(current.getMcpPath());
        if(children != null&& !children.isEmpty()){
            Map<String, Object> props = new HashMap<>();
            List<String> reqs = new ArrayList<>();

            //排序
            children.sort((o1,o2) -> {
                int s1 = o1.getSortOrder() != null ? o1.getSortOrder() : 0;
                int s2 = o2.getSortOrder() != null ? o2.getSortOrder() : 0;
                return Integer.compare(s1, s2);
            });

            for(McpGatewayToolConfigVO child : children){
                // 注意，buildProperty 嵌套递归，一层层的寻找，是否还有孩子元素（children）
                props.put(child.getFieldName(),buildProperty(child,childrenMap));
                if(Integer.valueOf(1).equals(child.getIsRequired())){
                    reqs.add(child.getFieldName());
                }
            }

            properties.put("properties",props);

            if(!reqs.isEmpty()){
                properties.put("required",reqs);
            }
        }
        return properties;
    }
}

package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.adapter.port.ISessionPort;
import cn.zephyr.ai.domain.session.adapter.repository.ISessionRepository;
import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import cn.zephyr.ai.types.enums.McpErrorCodes;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import jakarta.websocket.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;

/**
 * @author Zhulejun @Zephyr
 * @description 执行的工具调用
 * @create 2026/5/9 下午4:43
 */
@Service("toolsCallHandler")
public class ToolsCallHandler implements IRequestHandler {
    @Resource
    private ISessionRepository sessionRepository;

    @Resource
    private ISessionPort port;

    @Override
    public McpSchemaVO.JSONRPCResponse handle(String gatewayId, McpSchemaVO.JSONRPCRequest message) {

        try {
            McpGatewayProtocolConfigVO mcpGatewayProtocolConfigVO = sessionRepository.queryMcpGatewayProtocolConfig(gatewayId);

            //1、转换参数
            McpSchemaVO.CallToolRequest callToolRequest = McpSchemaVO.unmarshalFrom(message.params(), new TypeReference<>() {
            });

            Object arumentsObj = callToolRequest.arguments();

            // todo 暂时工具名称还没有使用，后续会调整
            String name = callToolRequest.name();

            //2、调用接口
            Object result = port.toolCall(mcpGatewayProtocolConfigVO.getHttpConfig(), arumentsObj);

            return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION, message.id(), Map.of(
                    "content", new Object[]{
                            Map.of(
                                    "type", "text",
                                    "text", result
                            ),
                    },
                    "isError", "false"
            ), null);
        } catch (Exception e) {
            return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION, message.id(), null,
                    new McpSchemaVO.JSONRPCResponse.JSONRPCError(McpErrorCodes.INVALID_PARAMS, e.getMessage(), null));
        }
    }

}

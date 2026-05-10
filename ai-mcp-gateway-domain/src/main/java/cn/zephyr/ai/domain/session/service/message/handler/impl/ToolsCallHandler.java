package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.adapter.port.ISessionPort;
import cn.zephyr.ai.domain.session.adapter.repository.ISessionRepository;
import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import cn.zephyr.ai.types.enums.McpErrorCodes;
import cn.zephyr.ai.types.enums.ResponseCode;
import cn.zephyr.ai.types.exception.AppException;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Zhulejun @Zephyr
 * @description 执行的工具调用
 * @create 2026/5/9 下午4:43
 */
@Slf4j
@Service("toolsCallHandler")
public class ToolsCallHandler implements IRequestHandler {

    @Resource
    private ISessionRepository repository;

    @Resource
    private ISessionPort port;

    @Override
    public McpSchemaVO.JSONRPCResponse handle(String gatewayId, McpSchemaVO.JSONRPCRequest message) {
        try {
            // 1. 转换参数
            McpSchemaVO.CallToolRequest callToolRequest =
                    McpSchemaVO.unmarshalFrom(message.params(), new TypeReference<>() {
                    });

            Object argumentsObj = callToolRequest.arguments();
            String toolName = callToolRequest.name();

            // 2. 查询协议信息
            McpToolProtocolConfigVO mcpToolProtocolConfigVO = repository.queryMcpGatewayProtocolConfig(gatewayId, toolName);
            if (null == mcpToolProtocolConfigVO) {
                throw new AppException(ResponseCode.METHOD_NOT_FOUND.getCode(), ResponseCode.METHOD_NOT_FOUND.getInfo());
            }

            // 2. 调用接口
            Object result = port.toolCall(mcpToolProtocolConfigVO.getHttpConfig(), argumentsObj);

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
            return new McpSchemaVO.JSONRPCResponse(McpSchemaVO.JSONRPC_VERSION,
                    message.id(),
                    null,
                    new McpSchemaVO.JSONRPCResponse.JSONRPCError(McpErrorCodes.INVALID_PARAMS, e.getMessage(), null));

        }

    }

}

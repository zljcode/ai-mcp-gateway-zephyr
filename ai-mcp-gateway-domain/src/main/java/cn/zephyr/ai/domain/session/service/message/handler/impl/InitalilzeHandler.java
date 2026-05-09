package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import jakarta.websocket.MessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Zhulejun @Zephyr
 * @description 协议握手，建立客户端与服务器的连接
 * @create 2026/5/9 下午4:42
 */
@Slf4j
@Service("initalilzeHandler")
public class InitalilzeHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handle(McpSchemaVO.JSONRPCRequest message) {
        log.info("模拟处理初始化请求");

        return new McpSchemaVO.JSONRPCResponse("2.0", message.id(), Map.of(
                "protocolVersion", "2024-11-05",
                "capabilities", Map.of(
                        "tool", Map.of(),
                        "resources", Map.of()
                ),
                "serverInfo", Map.of(
                        "name", "MCP Weather Proxy Server",
                        "version", "1.0.0"
                )), null
        );
    }
}

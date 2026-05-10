package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import jakarta.websocket.MessageHandler;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Zhulejun @Zephyr
 * @description 返回服务器支持的工具列表
 * @create 2026/5/9 下午4:43
 */
@Service("toolsListHandler")
public class ToolsListHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handle(String gatewayId,McpSchemaVO.JSONRPCRequest message) {
        return new McpSchemaVO.JSONRPCResponse("2.0", message.id(),
                Map.of(
                        "tools", new Object[]{
                                Map.of(
                                        "name", "toUpperCase",
                                        "description", "小写转大写",
                                        "inputSchema", Map.of(
                                                "type", "object",
                                                "properties", Map.of(
                                                        "word", Map.of(
                                                                "type", "string",
                                                                "description", "单词，字符串"
                                                        )
                                                ),
                                                "required", new String[]{"word"}
                                        )
                                )
                        }
                ), null);
    }
}

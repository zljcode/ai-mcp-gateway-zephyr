package cn.zephyr.ai.domain.session.service.message.handler.impl;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import jakarta.websocket.MessageHandler;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 工具列表处理器
 * @create 2026/5/9 下午4:43
 */
@Service("toolsListHandler")
public class ToolsListHandler implements IRequestHandler {
    @Override
    public McpSchemaVO.JSONRPCResponse handle(McpSchemaVO.JSONRPCRequest message) {
        return null;
    }
}

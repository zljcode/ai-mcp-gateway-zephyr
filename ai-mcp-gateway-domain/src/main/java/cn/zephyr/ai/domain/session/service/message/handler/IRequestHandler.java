package cn.zephyr.ai.domain.session.service.message.handler;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;

/**
 * @author Zhulejun @Zephyr
 * @description 处理请求接口
 * @create 2026/5/9 下午4:44
 */
public interface IRequestHandler {

    McpSchemaVO.JSONRPCResponse handle(McpSchemaVO.JSONRPCRequest message);

}

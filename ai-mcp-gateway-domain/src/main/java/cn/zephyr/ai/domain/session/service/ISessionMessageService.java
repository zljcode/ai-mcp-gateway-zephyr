package cn.zephyr.ai.domain.session.service;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 会话消息服务接口
 * @create 2026/5/9 下午4:46
 */
@Service
public interface ISessionMessageService {

    McpSchemaVO.JSONRPCResponse processHandlerMessage(McpSchemaVO.JSONRPCMessage message);

}

package cn.zephyr.ai.domain.session.adapter.port;

import cn.zephyr.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;

import java.io.IOException;

/**
 * @author Zhulejun @Zephyr
 * @description 会话端口
 * @create 2026/5/10 下午4:10
 */
public interface ISessionPort {

    Object toolCall(McpToolProtocolConfigVO.HTTPConfig httpConfig, Object params) throws IOException;

}

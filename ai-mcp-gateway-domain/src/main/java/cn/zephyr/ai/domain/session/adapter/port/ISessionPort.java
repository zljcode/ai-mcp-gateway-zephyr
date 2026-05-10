package cn.zephyr.ai.domain.session.adapter.port;

import java.io.IOException;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 会话端口
 * @create 2026/5/10 下午4:10
 */
public interface ISessionPort {

    Object toolCall(McpGatewayProtocolConfigVO.HTTPConfig httpConfig, Object params) throws IOException;
}

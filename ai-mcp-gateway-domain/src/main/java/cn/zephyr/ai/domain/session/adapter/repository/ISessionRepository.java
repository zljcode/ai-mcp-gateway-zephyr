package cn.zephyr.ai.domain.session.adapter.repository;

import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpToolConfigVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpToolProtocolConfigVO;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 会话仓储
 * @create 2026/5/10 上午10:55
 */
public interface ISessionRepository {

    McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);

    List<McpToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId);

    McpToolProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId, String toolName);

}

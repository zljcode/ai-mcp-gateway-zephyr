package cn.zephyr.ai.domain.session.adapter.repository;

import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;

/**
 * @author Zhulejun @Zephyr
 * @description 会话仓储
 * @create 2026/5/10 上午10:55
 */
public interface ISessionRepository {

    /**
     * 通过网关ID来查询MCP网关配置
     * @param gatewayId
     * @return
     */
    public McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);
}

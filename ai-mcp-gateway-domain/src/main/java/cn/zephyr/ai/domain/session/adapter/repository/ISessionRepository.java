package cn.zephyr.ai.domain.session.adapter.repository;

import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayConfigVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayToolConfigVO;

import java.util.List;

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
    McpGatewayConfigVO queryMcpGatewayConfigByGatewayId(String gatewayId);

    /**
     * 查找网关工具配置
     * @param gatewayId
     * @return
     */
    List<McpGatewayToolConfigVO> queryMcpGatewayToolConfigListByGatewayId(String gatewayId);

    /**
     * 查找网关协议配置
     * @param gatewayId
     * @return
     */
    McpGatewayProtocolConfigVO queryMcpGatewayProtocolConfig(String gatewayId);
}

package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpGatewayToolPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午6:37
 */
@Mapper
public interface IMcpGatewayToolDao {

    List<McpGatewayToolPO> queryEffectiveTools(String gatewayId);

    Long queryToolProtocolIdByToolName(McpGatewayToolPO mcpGatewayToolPOReq);

}
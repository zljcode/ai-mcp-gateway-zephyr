package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpGatewayPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MCP 网关配置表 DAO。
 */
@Mapper
public interface IMcpGatewayDao {

    int insert(McpGatewayPO po);

    int deleteById(Long id);

    int updateById(McpGatewayPO po);

    int updateAuthStatusByGatewayId(McpGatewayPO po);

    McpGatewayPO queryById(Long id);

    List<McpGatewayPO> queryAll();

    McpGatewayPO queryMcpGatewayByGatewayId(String gatewayId);

    List<McpGatewayPO> queryGatewayList(McpGatewayPO query);

    Long queryGatewayListCount(McpGatewayPO query);

}


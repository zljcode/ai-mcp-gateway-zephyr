package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpGatewayAuthPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午7:06
 */
@Mapper
public interface IMcpGatewayAuthDao {

    int insert(McpGatewayAuthPO po);

    int deleteById(Long id);

    int deleteByGatewayId(String gatewayId);

    int updateById(McpGatewayAuthPO po);

    int updateByGatewayId(McpGatewayAuthPO po);

    McpGatewayAuthPO queryById(Long id);

    List<McpGatewayAuthPO> queryAll();

    List<McpGatewayAuthPO> queryAuthList(McpGatewayAuthPO query);

    Long queryAuthListCount(McpGatewayAuthPO query);

    McpGatewayAuthPO queryMcpGatewayAuthPO(McpGatewayAuthPO req);

    int queryEffectiveGatewayAuthCount(String gatewayId);
}



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

    int insert(McpGatewayToolPO po);

    int updateProtocolByGatewayId(McpGatewayToolPO po);


    List<McpGatewayToolPO> queryEffectiveTools(String gatewayId);

    Long queryToolProtocolIdByToolName(McpGatewayToolPO mcpGatewayToolPOReq);

    List<McpGatewayToolPO> queryAll();

    int deleteByToolId(Long toolId);

    List<McpGatewayToolPO> queryListByGatewayId(String gatewayId);

    List<McpGatewayToolPO> queryToolList(McpGatewayToolPO query);

    Long queryToolListCount(McpGatewayToolPO query);


}
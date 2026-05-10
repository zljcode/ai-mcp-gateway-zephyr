package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpGatewayPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MCP 网关配置表 DAO。
 */
@Mapper
public interface IMcpGatewayDao {

    /** 新增一条记录。 */
    int insert(McpGatewayPO mcpGatewayPO);

    /** 选择性新增一条记录。 */
    int insertSelective(McpGatewayPO mcpGatewayPO);

    /** 根据主键完整更新。 */
    int updateById(McpGatewayPO mcpGatewayPO);

    /** 根据主键选择性更新。 */
    int updateByIdSelective(McpGatewayPO mcpGatewayPO);

    /** 根据主键删除。 */
    int deleteById(Long id);

    /** 根据主键查询。 */
    McpGatewayPO selectById(Long id);

    /** 查询全部记录。 */
    List<McpGatewayPO> selectAll();

    McpGatewayPO queryMcpGatewayByGatewayId(String gatewayId);

}

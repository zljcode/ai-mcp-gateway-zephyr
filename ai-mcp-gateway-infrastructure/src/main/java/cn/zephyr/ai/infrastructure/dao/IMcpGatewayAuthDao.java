package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpGatewayAuthPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户网关权限表 DAO。
 */
@Mapper
public interface IMcpGatewayAuthDao {

    /** 新增一条记录。 */
    int insert(McpGatewayAuthPO mcpGatewayAuthPO);

    /** 选择性新增一条记录。 */
    int insertSelective(McpGatewayAuthPO mcpGatewayAuthPO);

    /** 根据主键完整更新。 */
    int updateById(McpGatewayAuthPO mcpGatewayAuthPO);

    /** 根据主键选择性更新。 */
    int updateByIdSelective(McpGatewayAuthPO mcpGatewayAuthPO);

    /** 根据主键删除。 */
    int deleteById(Long id);

    /** 根据主键查询。 */
    McpGatewayAuthPO selectById(Long id);

    /** 查询全部记录。 */
    List<McpGatewayAuthPO> selectAll();

}

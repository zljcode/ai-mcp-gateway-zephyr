package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpProtocolMappingPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MCP 映射配置表 DAO。
 */
@Mapper
public interface IMcpProtocolMappingDao {

    /** 新增一条记录。 */
    int insert(McpProtocolMappingPO mcpProtocolMappingPO);

    /** 选择性新增一条记录。 */
    int insertSelective(McpProtocolMappingPO mcpProtocolMappingPO);

    /** 根据主键完整更新。 */
    int updateById(McpProtocolMappingPO mcpProtocolMappingPO);

    /** 根据主键选择性更新。 */
    int updateByIdSelective(McpProtocolMappingPO mcpProtocolMappingPO);

    /** 根据主键删除。 */
    int deleteById(Long id);

    /** 根据主键查询。 */
    McpProtocolMappingPO selectById(Long id);

    /** 查询全部记录。 */
    List<McpProtocolMappingPO> selectAll();

    List<McpProtocolMappingPO> queryMcpGatewayToolConfigList(String gatewayId);


}

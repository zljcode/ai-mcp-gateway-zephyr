package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpProtocolMappingPO;
import cn.zephyr.ai.infrastructure.dao.po.McpProtocolRegistryPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MCP 工具注册表 DAO。
 */
@Mapper
public interface IMcpProtocolRegistryDao {

    /**
     * 新增一条记录。
     */
    int insert(McpProtocolRegistryPO mcpProtocolRegistryPO);

    /**
     * 选择性新增一条记录。
     */
    int insertSelective(McpProtocolRegistryPO mcpProtocolRegistryPO);

    /**
     * 根据主键完整更新。
     */
    int updateById(McpProtocolRegistryPO mcpProtocolRegistryPO);

    /**
     * 根据主键选择性更新。
     */
    int updateByIdSelective(McpProtocolRegistryPO mcpProtocolRegistryPO);

    /**
     * 根据主键删除。
     */
    int deleteById(Long id);

    /**
     * 根据主键查询。
     */
    McpProtocolRegistryPO selectById(Long id);

    /**
     * 查询全部记录。
     */
    List<McpProtocolRegistryPO> selectAll();

    /**
     * 查询协议注册（1:1 -> gatewayId:toolId）
     */
    McpProtocolRegistryPO queryMcpProtocolRegistryByGatewayId(String gatewayId);


}

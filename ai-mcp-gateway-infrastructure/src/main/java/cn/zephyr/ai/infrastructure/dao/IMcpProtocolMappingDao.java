package cn.zephyr.ai.infrastructure.dao;

import cn.zephyr.ai.infrastructure.dao.po.McpProtocolMappingPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * MCP 映射配置表 DAO。
 */
@Mapper
public interface IMcpProtocolMappingDao {

    int insert(McpProtocolMappingPO po);

    int deleteById(Long id);

    int updateById(McpProtocolMappingPO po);

    McpProtocolMappingPO queryById(Long id);

    List<McpProtocolMappingPO> queryAll();

    List<McpProtocolMappingPO> queryMcpGatewayToolConfigListByProtocolId(Long protocolId);

}

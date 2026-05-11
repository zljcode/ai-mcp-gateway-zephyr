package cn.zephyr.ai.infrastructure.adapter.repository;

import cn.zephyr.ai.domain.protocol.adapter.repository.IProtocolRepository;
import cn.zephyr.ai.domain.protocol.model.valobj.enums.ProtocolStatusEnum;
import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import cn.zephyr.ai.infrastructure.dao.IMcpProtocolHttpDao;
import cn.zephyr.ai.infrastructure.dao.IMcpProtocolMappingDao;
import cn.zephyr.ai.infrastructure.dao.po.McpProtocolHttpPO;
import cn.zephyr.ai.infrastructure.dao.po.McpProtocolMappingPO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议仓储
 * @create 2026/5/11 下午1:51
 */
@Repository
public class ProtocolRepository implements IProtocolRepository {

    @Resource
    private IMcpProtocolHttpDao mcpProtocolHttpDao;

    @Resource
    private IMcpProtocolMappingDao mcpProtocolMappingDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<Long> saveHttpProtocolAndMapping(List<HTTPProtocolVO> httpProtocolVOS) {
        List<Long> protocolIds = new ArrayList<>();

        for (HTTPProtocolVO httpProtocolVO : httpProtocolVOS) {
            // 0、生成协议ID，八位数字的
            long protocolId = Long.parseLong(RandomStringUtils.randomNumeric(8));

            //1、保存HTTP协议配置
            McpProtocolHttpPO mcpProtocolHttpPO = McpProtocolHttpPO.builder()
                    .protocolId(protocolId)
                    .httpUrl(httpProtocolVO.getHttpUrl())
                    .httpMethod(httpProtocolVO.getHttpMethod())
                    .httpHeaders(httpProtocolVO.getHttpHeaders())
                    .timeout(httpProtocolVO.getTimeout())
                    .retryTimes(3)
                    .status(ProtocolStatusEnum.ENABLE.getCode())
                    .build();
            mcpProtocolHttpDao.insert(mcpProtocolHttpPO);

            //2、保存协议映射配置
            List<HTTPProtocolVO.ProtocolMapping> mappings = httpProtocolVO.getMappings();
            if(mappings == null || mappings.isEmpty()){
                continue;
            }

            for(HTTPProtocolVO.ProtocolMapping mapping : mappings){
                McpProtocolMappingPO mcpProtocolMappingPO = McpProtocolMappingPO.builder()
                        .protocolId(protocolId)
                        .mappingType(mapping.getMappingType())
                        .parentPath(mapping.getParentPath())
                        .fieldName(mapping.getFieldName())
                        .mcpPath(mapping.getMcpPath())
                        .mcpDesc(mapping.getMcpDesc())
                        .mcpType(mapping.getMcpType())
                        .isRequired(mapping.getIsRequired())
                        .sortOrder(mapping.getSortOrder())
                        .build();
                mcpProtocolMappingDao.insert(mcpProtocolMappingPO);
            }

            protocolIds.add(protocolId);
        }

        return protocolIds;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteGatewayProtocol(Long protocolId) {
        mcpProtocolHttpDao.deleteByProtocolId(protocolId);
        mcpProtocolHttpDao.deleteByProtocolId(protocolId);
    }
}

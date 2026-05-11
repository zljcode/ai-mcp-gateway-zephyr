package cn.zephyr.ai.infrastructure.adapter.repository;

import cn.zephyr.ai.domain.admin.adapter.respository.IAdminRepository;
import cn.zephyr.ai.domain.admin.model.entity.GatewayConfigEntity;
import cn.zephyr.ai.infrastructure.dao.po.McpGatewayPO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import cn.zephyr.ai.infrastructure.dao.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Zhulejun @Zephyr
 * @description 管理员存储
 * @create 2026/5/11 下午3:48
 */
@Repository
@Slf4j
public class AdminRepository implements IAdminRepository {


    @Resource
    private IMcpGatewayAuthDao mcpGatewayAuthDao;

    @Resource
    private IMcpGatewayDao mcpGatewayDao;

    @Resource
    private IMcpGatewayToolDao mcpGatewayToolDao;

    @Resource
    private IMcpProtocolHttpDao protocolHttpDao;

    @Resource
    private IMcpProtocolMappingDao protocolMappingDao;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        List<McpGatewayPO> mcpGatewayPOS = mcpGatewayDao.queryAll();
        return mcpGatewayPOS.stream().map(po -> GatewayConfigEntity.builder()
                .gatewayId(po.getGatewayId())
                .gatewayName(po.getGatewayName())
                .gatewayDesc(po.getGatewayDesc())
                .version(po.getVersion())
                .auth(po.getAuth())
                .status(po.getStatus())
                .build()).collect(Collectors.toList());
    }
}

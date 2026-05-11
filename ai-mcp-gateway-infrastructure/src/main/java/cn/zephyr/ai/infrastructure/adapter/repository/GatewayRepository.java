package cn.zephyr.ai.infrastructure.adapter.repository;

import cn.zephyr.ai.domain.gateway.adapter.repository.IGatewayRepository;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.model.valobj.GatewayConfigVO;
import cn.zephyr.ai.domain.gateway.model.valobj.GatewayToolConfigVO;
import cn.zephyr.ai.infrastructure.dao.IMcpGatewayDao;
import cn.zephyr.ai.infrastructure.dao.IMcpGatewayToolDao;
import cn.zephyr.ai.infrastructure.dao.po.McpGatewayPO;
import cn.zephyr.ai.infrastructure.dao.po.McpGatewayToolPO;
import cn.zephyr.ai.types.enums.GatewayEnum;
import cn.zephyr.ai.types.exception.AppException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import static cn.zephyr.ai.types.enums.ResponseCode.DB_UPDATE_FAIL;

/**
 * @author Zhulejun @Zephyr
 * @description 网关仓储服务实现
 * @create 2026/5/11 下午2:45
 */
@Repository
public class GatewayRepository implements IGatewayRepository {

    @Resource
    private IMcpGatewayDao mcpGatewayDao;

    @Resource
    private IMcpGatewayToolDao mcpGatewayToolDao;


    @Override
    public void saveGatewayConfig(GatewayConfigCommandEntity commandEntity) {
        GatewayConfigVO gatewayConfigVO = commandEntity.getGatewayConfigVO();

        McpGatewayPO mcpGatewayPO = new McpGatewayPO();
        mcpGatewayPO.setGatewayDesc(gatewayConfigVO.getGatewayDesc());
        mcpGatewayPO.setGatewayId(gatewayConfigVO.getGatewayId());
        mcpGatewayPO.setGatewayName(gatewayConfigVO.getGatewayName());
        mcpGatewayPO.setGatewayDesc(gatewayConfigVO.getGatewayDesc());
        mcpGatewayPO.setVersion(gatewayConfigVO.getVersion());
        mcpGatewayPO.setAuth(null != gatewayConfigVO.getAuth() ? gatewayConfigVO.getAuth().getCode() : GatewayEnum.GatewayAuthStatusEnum.ENABLE.getCode());
        mcpGatewayPO.setStatus(null != gatewayConfigVO.getAuth() ? gatewayConfigVO.getAuth().getCode() : GatewayEnum.GatewayStatus.NOT_VERIFIED.getCode());

        mcpGatewayDao.insert(mcpGatewayPO);
    }

    @Override
    public void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity) {
        GatewayConfigVO gatewayConfigVO = commandEntity.getGatewayConfigVO();
        if (null == gatewayConfigVO.getAuth()) {
            return;
        }

        McpGatewayPO mcpGatewayPO = new McpGatewayPO();
        mcpGatewayPO.setGatewayId(gatewayConfigVO.getGatewayId());
        mcpGatewayPO.setAuth(null != gatewayConfigVO.getAuth() ? gatewayConfigVO.getAuth().getCode() : null);
        int count = mcpGatewayDao.updateAuthStatusByGatewayId(mcpGatewayPO);
        System.out.println("更新条数：" + count);
        if (1 != count) {
            throw new AppException(DB_UPDATE_FAIL.getCode(), DB_UPDATE_FAIL.getInfo());
        }
    }

    @Override
    public void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity) {
        GatewayToolConfigVO gatewayToolConfigVO = commandEntity.getGatewayToolConfigVO();

        McpGatewayToolPO mcpGatewayToolPO = new McpGatewayToolPO();
        mcpGatewayToolPO.setGatewayId(gatewayToolConfigVO.getGatewayId());
        mcpGatewayToolPO.setToolId(gatewayToolConfigVO.getToolId());
        mcpGatewayToolPO.setToolName(gatewayToolConfigVO.getToolName());
        mcpGatewayToolPO.setToolType(gatewayToolConfigVO.getToolType());
        mcpGatewayToolPO.setToolDescription(gatewayToolConfigVO.getToolDescription());
        mcpGatewayToolPO.setToolVersion(gatewayToolConfigVO.getToolVersion());
        mcpGatewayToolPO.setProtocolId(gatewayToolConfigVO.getProtocolId());
        mcpGatewayToolPO.setProtocolType(gatewayToolConfigVO.getProtocolType());
        mcpGatewayToolDao.insert(mcpGatewayToolPO);
    }

    @Override
    public void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity) {
        GatewayToolConfigVO gatewayToolConfigVO = commandEntity.getGatewayToolConfigVO();

        McpGatewayToolPO mcpGatewayToolPO = new McpGatewayToolPO();
        mcpGatewayToolPO.setGatewayId(gatewayToolConfigVO.getGatewayId());
        mcpGatewayToolPO.setProtocolId(gatewayToolConfigVO.getProtocolId());
        mcpGatewayToolPO.setProtocolType(gatewayToolConfigVO.getProtocolType());

        int count = mcpGatewayToolDao.updateProtocolByGatewayId(mcpGatewayToolPO);
        if (1 != count) {
            throw new AppException(DB_UPDATE_FAIL.getCode(), DB_UPDATE_FAIL.getInfo());
        }
    }

    @Override
    public void deleteGatewayToolConfig(Long toolId) {
        mcpGatewayToolDao.deleteByToolId(toolId);
    }
}

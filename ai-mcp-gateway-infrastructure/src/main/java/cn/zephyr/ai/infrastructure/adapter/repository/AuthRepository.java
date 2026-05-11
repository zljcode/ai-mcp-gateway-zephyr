package cn.zephyr.ai.infrastructure.adapter.repository;

import cn.zephyr.ai.domain.auth.adapter.repository.IAuthRepository;
import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;
import cn.zephyr.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import cn.zephyr.ai.domain.auth.model.valobj.enums.AuthStatusEnum;
import cn.zephyr.ai.infrastructure.dao.IMcpGatewayAuthDao;
import cn.zephyr.ai.infrastructure.dao.po.McpGatewayAuthPO;
import cn.zephyr.ai.infrastructure.dao.po.McpGatewayPO;
import cn.zephyr.ai.types.enums.McpErrorCodes;
import cn.zephyr.ai.types.exception.AppException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import cn.zephyr.ai.infrastructure.dao.IMcpGatewayDao;

/**
 * @author Zhulejun @Zephyr
 * @description 鉴权仓储服务
 * @create 2026/5/10 下午10:00
 */
@Repository
public class AuthRepository implements IAuthRepository {

    @Resource
    private IMcpGatewayAuthDao mcpGatewayAuthDao;

    @Resource
    private IMcpGatewayDao mcpGatewayDao;

    @Override
    public boolean validate(String gatewayId, String apiKey) {
        McpGatewayAuthPO poReq = new McpGatewayAuthPO();
        poReq.setGatewayId(gatewayId);
        poReq.setApiKey(apiKey);
        McpGatewayAuthPO mcpGatewayAuthPO = mcpGatewayAuthDao.queryMcpGatewayAuthPO(poReq);
        if (null == mcpGatewayAuthPO) {
            return false;
        }
        return mcpGatewayAuthPO.getStatus().equals(AuthStatusEnum.AuthConfig.ENABLE.getCode());
    }

    @Override
    public int queryEffectiveGatewayAuthCount(String gatewayId) {
        return mcpGatewayAuthDao.queryEffectiveGatewayAuthCount(gatewayId);
    }

    @Override
    public McpGatewayAuthVO queryEffectiveGatewayAuthInfo(LicenseCommandEntity commandEntity) {

        McpGatewayAuthPO poReq = new McpGatewayAuthPO();
        poReq.setGatewayId(commandEntity.getGatewayId());
        poReq.setApiKey(commandEntity.getApiKey());

        McpGatewayAuthPO mcpGatewayAuthPO = mcpGatewayAuthDao.queryMcpGatewayAuthPO(poReq);
        if (null == mcpGatewayAuthPO) {
            return null;
        }

        return McpGatewayAuthVO.builder()
                .gatewayId(mcpGatewayAuthPO.getGatewayId())
                .apiKey(mcpGatewayAuthPO.getApiKey())
                .rateLimit(mcpGatewayAuthPO.getRateLimit())
                .expireTime(mcpGatewayAuthPO.getExpireTime())
                .status(AuthStatusEnum.AuthConfig.get(mcpGatewayAuthPO.getStatus()))
                .build();
    }

    @Override
    public void saveGatewayAuth(McpGatewayAuthVO mcpGatewayAuthVO) {
        McpGatewayAuthPO existingAuth = mcpGatewayAuthDao.queryMcpGatewayAuthPO(McpGatewayAuthPO.builder().gatewayId(mcpGatewayAuthVO.getGatewayId()).build());

        McpGatewayAuthPO mcpGatewayAuthPO = McpGatewayAuthPO.builder()
                .gatewayId(mcpGatewayAuthVO.getGatewayId())
                .apiKey(mcpGatewayAuthVO.getApiKey())
                .rateLimit(mcpGatewayAuthVO.getRateLimit())
                .expireTime(mcpGatewayAuthVO.getExpireTime())
                .status(mcpGatewayAuthVO.getStatus().getCode())
                .build();

        if (existingAuth != null) {
            mcpGatewayAuthDao.updateByGatewayId(mcpGatewayAuthPO);
        } else {
            mcpGatewayAuthDao.insert(mcpGatewayAuthPO);
        }
    }

    @Override
    public AuthStatusEnum.GatewayConfig queryGatewayAuthStatus(String gatewayId) {
        McpGatewayPO mcpGatewayPO = mcpGatewayDao.queryMcpGatewayByGatewayId(gatewayId);
        if (null == mcpGatewayPO) {
            throw new AppException(McpErrorCodes.INVALID_PARAMS, "无效参数 gatewayId 不存在");
        }
        return AuthStatusEnum.GatewayConfig.get(mcpGatewayPO.getAuth());
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        mcpGatewayAuthDao.deleteByGatewayId(gatewayId);
    }
}

package cn.zephyr.ai.domain.auth.service.license;

import cn.zephyr.ai.domain.auth.adapter.repository.IAuthRepository;
import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;
import cn.zephyr.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import cn.zephyr.ai.domain.auth.model.valobj.enums.AuthStatusEnum;
import cn.zephyr.ai.domain.auth.service.IAuthLicenseService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午9:55
 */
@Service
@Slf4j
public class AuthLicenseService implements IAuthLicenseService {

    @Resource
    private IAuthRepository authRepository;


    @Override
    public boolean checkLicense(LicenseCommandEntity commandEntity) {
        //查询是否为强校验（非强校验，直接返回校验结果 true）
        AuthStatusEnum.GatewayConfig gatewayAuthStatus = authRepository.queryGatewayAuthStatus(commandEntity.getGatewayId());
        if(AuthStatusEnum.GatewayConfig.NOT_VERIFIED.equals(gatewayAuthStatus)){
            return true;
        }
        //查询网关认证配置消息
        McpGatewayAuthVO mcpGatewayAuthVO = authRepository.queryEffectiveGatewayAuthInfo(commandEntity);

        //没有匹配到权限返回 false
        if(null == mcpGatewayAuthVO){
            return false;
        }

        //检查是否开启了认证模式，未开启则为true
        if(AuthStatusEnum.AuthConfig.DISABLE.equals(mcpGatewayAuthVO.getStatus())){
            return true;
        }

        //判断过期时间，未设置过期时间永久有效
        Date expireTime = mcpGatewayAuthVO.getExpireTime();

        boolean isBefore = new Date().before(expireTime);

        if (!isBefore) {
            log.warn("apiKey 权限校验，expireTime 已过期。gatewayId:{} apiKey:{}", commandEntity.getGatewayId(), commandEntity.getApiKey());
        }

        return isBefore;
    }
}

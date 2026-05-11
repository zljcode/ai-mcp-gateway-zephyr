package cn.zephyr.ai.cases.admin.auth;

import cn.zephyr.ai.cases.admin.IAdminAuthService;
import cn.zephyr.ai.domain.auth.model.entity.RegisterCommandEntity;
import cn.zephyr.ai.domain.auth.service.IAuthRegisterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 认证配置管理
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/3/24 08:10
 */
@Slf4j
@Service
public class AdminAuthService implements IAdminAuthService {

    @Resource
    private IAuthRegisterService authRegisterService;

    @Override
    public void saveGatewayAuth(RegisterCommandEntity commandEntity) {
        authRegisterService.register(commandEntity);
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        authRegisterService.deleteGatewayAuth(gatewayId);
    }

}

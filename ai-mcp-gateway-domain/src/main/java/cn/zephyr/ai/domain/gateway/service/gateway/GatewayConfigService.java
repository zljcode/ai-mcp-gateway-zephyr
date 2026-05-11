package cn.zephyr.ai.domain.gateway.service.gateway;

import cn.zephyr.ai.domain.gateway.adapter.repository.IGatewayRepository;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.service.IGatewayConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 网关配置服务
 * @create 2026/5/11 下午2:51
 */
@Service
public class GatewayConfigService implements IGatewayConfigService {

    @Resource
    private IGatewayRepository gatewayRepository;

    @Override
    public void saveGatewayConfig(GatewayConfigCommandEntity commandEntity) {
        gatewayRepository.saveGatewayConfig(commandEntity);
    }

    @Override
    public void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity) {
        gatewayRepository.updateGatewayAuthStatus(commandEntity);
    }
}

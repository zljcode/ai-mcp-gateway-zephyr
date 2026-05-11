package cn.zephyr.ai.domain.gateway.service.tool;

import cn.zephyr.ai.domain.gateway.adapter.repository.IGatewayRepository;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.service.IGatewayToolConfigService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 网关工具配置服务
 * @create 2026/5/11 下午2:51
 */
@Service
public class GatewayToolConfigService implements IGatewayToolConfigService {

    @Resource
    private IGatewayRepository gatewayRepository;

    @Override
    public void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity) {
            gatewayRepository.saveGatewayToolConfig(commandEntity);
    }

    @Override
    public void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity) {
            gatewayRepository.updateGatewayToolProtocol(commandEntity);
    }
}

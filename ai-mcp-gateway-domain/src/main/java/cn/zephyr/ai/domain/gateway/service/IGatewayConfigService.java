package cn.zephyr.ai.domain.gateway.service;

import cn.zephyr.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;

/**
 * @author Zhulejun @Zephyr
 * @description 网关配置服务
 * @create 2026/5/11 下午2:52
 */
public interface IGatewayConfigService {

    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);

    void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity);


}

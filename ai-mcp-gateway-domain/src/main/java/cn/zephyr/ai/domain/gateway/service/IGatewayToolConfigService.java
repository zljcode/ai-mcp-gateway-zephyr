package cn.zephyr.ai.domain.gateway.service;

import cn.zephyr.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;

/**
 * @author Zhulejun @Zephyr
 * @description 网关工具配置接口
 * @create 2026/5/11 下午2:52
 */
public interface IGatewayToolConfigService {

    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);

    void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity);


}

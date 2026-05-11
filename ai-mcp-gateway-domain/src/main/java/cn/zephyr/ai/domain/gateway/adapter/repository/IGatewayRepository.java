package cn.zephyr.ai.domain.gateway.adapter.repository;

import cn.zephyr.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;



/**
 * @author Zhulejun @Zephyr
 * @description 网关服务仓储接口
 * @create 2026/5/11 下午2:48
 */
public interface IGatewayRepository {

    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);

    void updateGatewayAuthStatus(GatewayConfigCommandEntity commandEntity);

    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);

    void updateGatewayToolProtocol(GatewayToolConfigCommandEntity commandEntity);

}

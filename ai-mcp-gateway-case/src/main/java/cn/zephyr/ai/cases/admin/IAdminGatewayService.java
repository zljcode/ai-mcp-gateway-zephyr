package cn.zephyr.ai.cases.admin;

import cn.zephyr.ai.domain.gateway.model.entity.GatewayConfigCommandEntity;
import cn.zephyr.ai.domain.gateway.model.entity.GatewayToolConfigCommandEntity;

/**
 * 网关配置管理
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/3/24 08:09
 */
public interface IAdminGatewayService {

    void saveGatewayConfig(GatewayConfigCommandEntity commandEntity);

    void saveGatewayToolConfig(GatewayToolConfigCommandEntity commandEntity);

}

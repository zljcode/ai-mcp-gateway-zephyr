package cn.zephyr.ai.domain.auth.adapter.repository;

import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;
import cn.zephyr.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import cn.zephyr.ai.domain.auth.model.valobj.enums.AuthStatusEnum;

/**
 * @author Zhulejun @Zephyr
 * @description 鉴权领域仓储
 * @create 2026/5/10 下午9:59
 */
public interface IAuthRepository {


    void saveGatewayAuth(McpGatewayAuthVO mcpGatewayAuthVO);

    boolean validate(String gatewayId, String apiKey);

    int queryEffectiveGatewayAuthCount(String gatewayId);

    McpGatewayAuthVO queryEffectiveGatewayAuthInfo(LicenseCommandEntity commandEntity);

    AuthStatusEnum.GatewayConfig queryGatewayAuthStatus(String gatewayId);

    void deleteGatewayAuth(String gatewayId);



}

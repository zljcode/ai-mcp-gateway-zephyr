package cn.zephyr.ai.domain.gateway.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import cn.zephyr.ai.types.enums.GatewayEnum;

/**
 * @author Zhulejun @Zephyr
 * @description 网关配置值对象
 * @create 2026/5/11 下午2:50
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GatewayConfigVO {

    /**
     * 网关唯一标识
     */
    private String gatewayId;
    /**
     * 网关名称
     */
    private String gatewayName;
    /**
     * 网关描述
     */
    private String gatewayDesc;
    /**
     * 协议版本
     */
    private String version;
    /**
     * 校验状态：0-禁用，1-启用
     */
    private GatewayEnum.GatewayAuthStatusEnum auth;
    /**
     * 网关状态：0-不校验，1-强校验
     */
    private GatewayEnum.GatewayStatus status;

}

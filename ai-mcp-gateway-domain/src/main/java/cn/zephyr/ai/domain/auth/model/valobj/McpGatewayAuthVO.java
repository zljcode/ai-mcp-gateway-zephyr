package cn.zephyr.ai.domain.auth.model.valobj;

import cn.zephyr.ai.domain.auth.model.valobj.enums.AuthStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午10:06
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpGatewayAuthVO {

    /**
     * 网关ID
     */
    private String gatewayId;
    /**
     * API密钥
     */
    private String apiKey;
    /**
     * 速率限制（次/小时）
     */
    private Integer rateLimit;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 状态：0-禁用，1-启用
     */
    private AuthStatusEnum.AuthConfig status;

}

package cn.zephyr.ai.domain.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 限流命令实体对象
 * @create 2026/5/10 下午10:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RateLimitCommandEntity {

    /**
     * 网关ID
     */
    private String gatewayId;

    /**
     * API密钥
     */
    private String apiKey;

}

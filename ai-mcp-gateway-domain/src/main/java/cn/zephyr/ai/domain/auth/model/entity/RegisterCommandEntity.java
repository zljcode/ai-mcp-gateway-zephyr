package cn.zephyr.ai.domain.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zhulejun @Zephyr
 * @description 权限注册命令实体对象
 * @create 2026/5/10 下午10:25
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommandEntity {

    /**
     * 网关ID
     */
    private String gatewayId;
    /**
     * 速率限制（次/小时）
     */
    private Integer rateLimit;
    /**
     * 过期时间
     */
    private Date expireTime;

}

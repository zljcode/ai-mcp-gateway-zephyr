package cn.zephyr.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.time.LocalDateTime;

/**
 * 用户网关权限表对应的持久化对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpGatewayAuthPO {

    /** 主键 ID。 */
    private Long id;
    /** 网关 ID。 */
    private String gatewayId;
    /** API 密钥。 */
    private String apiKey;
    /** 速率限制（次/小时）。 */
    private Integer rateLimit;
    /** 过期时间。 */
    private Date expireTime;
    /** 状态：0-禁用，1-启用。 */
    private Integer status;
    /** 创建时间。 */
    private Date createTime;
    /** 更新时间。 */
    private Date updateTime;

}

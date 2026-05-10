package cn.zephyr.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MCP 网关配置表对应的持久化对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpGatewayPO {

    /** 主键 ID。 */
    private Long id;
    /** 网关唯一标识。 */
    private String gatewayId;
    /** 网关名称。 */
    private String gatewayName;
    /** 网关描述。 */
    private String gatewayDesc;
    /** 状态：0-禁用，1-启用。 */
    private Integer status;
    /** 创建时间。 */
    private LocalDateTime createTime;
    /** 更新时间。 */
    private LocalDateTime updateTime;

}

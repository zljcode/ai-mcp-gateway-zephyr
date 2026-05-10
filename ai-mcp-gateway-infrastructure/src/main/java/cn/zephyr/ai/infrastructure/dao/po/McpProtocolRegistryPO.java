package cn.zephyr.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MCP 工具注册表对应的持久化对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpProtocolRegistryPO {

    /** 主键 ID。 */
    private Long id;
    /** 所属网关 ID。 */
    private String gatewayId;
    /** 工具 ID。 */
    private Long toolId;
    /** MCP 工具名称。 */
    private String toolName;
    /** 工具类型：function / resource。 */
    private String toolType;
    /** 工具描述。 */
    private String toolDescription;
    /** HTTP 接口地址。 */
    private String httpUrl;
    /** HTTP 请求方法。 */
    private String httpMethod;
    /** HTTP 请求头 JSON。 */
    private String httpHeaders;
    /** 超时时间（毫秒）。 */
    private Integer timeout;
    /** 重试次数。 */
    private Integer retryTimes;
    /** 状态：0-禁用，1-启用。 */
    private Integer status;
    /** 创建时间。 */
    private LocalDateTime createTime;
    /** 更新时间。 */
    private LocalDateTime updateTime;

}

package cn.zephyr.ai.infrastructure.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MCP 映射配置表对应的持久化对象。
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class McpProtocolMappingPO {

    /** 主键 ID。 */
    private Long id;
    /** 所属网关 ID。 */
    private String gatewayId;
    /** 所属工具 ID。 */
    private Long toolId;
    /** 映射类型：request / response。 */
    private String mappingType;
    /** 父级路径，用于构建嵌套结构。 */
    private String parentPath;
    /** 字段名称。 */
    private String fieldName;
    /** MCP 完整路径。 */
    private String mcpPath;
    /** MCP 数据类型。 */
    private String mcpType;
    /** MCP 字段描述。 */
    private String mcpDesc;
    /** 是否必填：0-否，1-是。 */
    private Integer isRequired;
    /** HTTP 路径。 */
    private String httpPath;
    /** HTTP 位置：body/query/path/header。 */
    private String httpLocation;
    /** 排序顺序。 */
    private Integer sortOrder;
    /** 创建时间。 */
    private LocalDateTime createTime;
    /** 更新时间。 */
    private LocalDateTime updateTime;

}

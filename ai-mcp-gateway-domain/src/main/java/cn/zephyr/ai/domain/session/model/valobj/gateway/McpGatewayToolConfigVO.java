package cn.zephyr.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 网关协议映射
 * @create 2026/5/10 下午2:32
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class McpGatewayToolConfigVO {

    /**
     * 所属网关 ID。
     */
    private String gatewayId;
    /**
     * 所属工具 ID。
     */
    private Long toolId;
    /**
     * 映射类型：request / response。
     */
    private String mappingType;
    /**
     * 父级路径，用于构建嵌套结构。
     */
    private String parentPath;
    /**
     * 字段名称。
     */
    private String fieldName;
    /**
     * MCP 完整路径。
     */
    private String mcpPath;
    /**
     * MCP 数据类型。
     */
    private String mcpType;
    /**
     * MCP 字段描述。
     */
    private String mcpDesc;
    /**
     * 是否必填：0-否，1-是。
     */
    private Integer isRequired;
    /**
     * 排序顺序。
     */
    private Integer sortOrder;

}

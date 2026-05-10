package cn.zephyr.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description MCP网关配置表 VO对象，用于获取数据库PO对象
 * @create 2026/5/10 上午10:58
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class McpGatewayConfigVO {
    /**
     * 网关ID
     */
    private String gatewayId;

    /**
     * 网关名称
     */
    private String gatewayName;

    /**
     * 工具ID
     */
    private Long toolId;

    /**
     * 工具名称
     */
    private String toolName;

    /**
     * 工具描述
     */
    private String toolDesc;

    /**
     * 工具版本
     */
    private String toolVersion;
}

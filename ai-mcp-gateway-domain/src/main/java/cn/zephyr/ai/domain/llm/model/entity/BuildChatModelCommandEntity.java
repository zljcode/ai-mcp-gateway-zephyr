package cn.zephyr.ai.domain.llm.model.entity;

import cn.zephyr.ai.domain.llm.model.valobj.McpConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 构建对话模型命令
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/4/8 07:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuildChatModelCommandEntity {

    private String gatewayId;

    private McpConfigVO mcpConfigVO;

}

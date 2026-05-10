package cn.zephyr.ai.cases.mcp.message.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;

import cn.zephyr.ai.cases.mcp.message.node.RootNode;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 消息处理工厂，组装节点
 * @create 2026/5/10 下午8:33
 */
@Service
public class DefaultMcpMessageFactory {

    @Resource(name = "mcpMessageRootNode")
    private RootNode rootNode;

    public StrategyHandler<HandleMessageCommandEntity, DynamicContext, ResponseEntity<Void>> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {
        private SessionConfigVO sessionConfigVO;
    }
}

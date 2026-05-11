package cn.zephyr.ai.cases.mcp.session.factory;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.session.node.RootNode;
import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description MCP会话工厂
 * @create 2026/5/9 上午11:21
 */
@Service
public class DefalutMcpSessionFactory {

    @Resource
    private RootNode rootNode;

    public StrategyHandler<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> strategyHandler() {
        return rootNode;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class DynamicContext {
        private SessionConfigVO sessionConfigVO;
        private String apiKey;
    }


}

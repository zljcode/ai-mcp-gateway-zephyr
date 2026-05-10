package cn.zephyr.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.session.AbstractMcpMessageSupport;
import cn.zephyr.ai.cases.mcp.session.factory.DefalutMcpSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 根节点
 * @create 2026/5/9 上午11:21
 */
@Slf4j
@Service
public class RootNode extends AbstractMcpMessageSupport {

    @Resource(name = "mcpSessionVerifyNode")
    private VerifyNode verifyNode;

    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        try {
            log.info("创建会话-mcp session RootNode:{}", requestParameter);

            return router(requestParameter, dynamicContext);
        } catch (Exception e) {
            log.error("创建会话-mcp session RootNode 异常:{}", requestParameter, e);
            throw e;
        }
    }

    @Override
    public StrategyHandler<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String requestParameter, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return verifyNode;
    }
}

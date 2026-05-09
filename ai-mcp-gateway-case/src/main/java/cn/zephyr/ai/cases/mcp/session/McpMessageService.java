package cn.zephyr.ai.cases.mcp.session;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.IMcpSessionService;
import cn.zephyr.ai.cases.mcp.session.factory.DefalutMcpSessionFactory;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description MCP会话服务
 * @create 2026/5/9 上午11:23
 */
@Service
public class McpMessageService implements IMcpSessionService {

    @Resource
    private DefalutMcpSessionFactory defalutMcpSessionFactory;

    /**
     * 构建 SSE会话
     *
     * @param gatewayId
     * @return
     */
    @Override
    public Flux<ServerSentEvent<String>> createMcpSession(String gatewayId) throws Exception {
        StrategyHandler<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>>
                strategyHandler = defalutMcpSessionFactory.strategyHandler();

        return strategyHandler.apply(gatewayId, new DefalutMcpSessionFactory.DynamicContext());
    }
}

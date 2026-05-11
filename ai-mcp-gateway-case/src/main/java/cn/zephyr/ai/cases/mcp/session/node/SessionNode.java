package cn.zephyr.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.session.AbstractMcpMessageSupport;
import cn.zephyr.ai.cases.mcp.session.factory.DefalutMcpSessionFactory;
import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 会话节点
 * @create 2026/5/9 上午11:21
 */
@Slf4j
@Service("mcpSessionSessionNode")
public class SessionNode extends AbstractMcpMessageSupport {

    @Resource(name = "mcpEndNode")
    private EndNode endNode;


    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        log.info("创建会话-SessionNode：{}", requestParameter);
        //创建会话服务
        SessionConfigVO sessionConfigVO = sessionManagementService.createSession(requestParameter,dynamicContext.getApiKey());

        //写入上下文
        dynamicContext.setSessionConfigVO(sessionConfigVO);
        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String s, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return endNode;
    }
}

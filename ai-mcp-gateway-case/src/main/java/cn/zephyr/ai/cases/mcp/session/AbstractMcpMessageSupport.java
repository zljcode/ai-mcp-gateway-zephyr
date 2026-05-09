package cn.zephyr.ai.cases.mcp.session;

import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.session.factory.DefalutMcpSessionFactory;
import cn.zephyr.ai.domain.session.service.ISessionManagementService;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Zhulejun @Zephyr
 * @description 抽象通用方法
 * @create 2026/5/9 上午11:23
 */
public abstract class AbstractMcpMessageSupport extends AbstractMultiThreadStrategyRouter<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> {

    @Resource
    protected ISessionManagementService sessionManagementService;

    @Override
    protected void multiThread (String requestParamter,DefalutMcpSessionFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {

    }

}

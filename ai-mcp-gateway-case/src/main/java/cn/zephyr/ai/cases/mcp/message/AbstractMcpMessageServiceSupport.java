package cn.zephyr.ai.cases.mcp.message;

import cn.bugstack.wrench.design.framework.tree.AbstractMultiThreadStrategyRouter;
import cn.zephyr.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import cn.zephyr.ai.domain.session.service.ISessionManagementService;
import cn.zephyr.ai.domain.session.service.ISessionMessageService;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * @author Zhulejun @Zephyr
 * @description 父类规则树工厂
 * @create 2026/5/10 下午8:32
 */
public abstract class AbstractMcpMessageServiceSupport extends AbstractMultiThreadStrategyRouter<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> {

    @Resource
    protected ISessionMessageService serviceMessageService;

    @Resource
    protected ISessionManagementService sessionManagementService;

    @Override
    protected void multiThread(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {

    }
}
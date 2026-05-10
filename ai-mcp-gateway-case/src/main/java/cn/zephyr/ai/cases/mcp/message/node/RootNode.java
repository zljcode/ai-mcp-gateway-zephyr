package cn.zephyr.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;

import cn.zephyr.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import cn.zephyr.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 消息根节点
 * @create 2026/5/10 下午8:33
 */
@Slf4j
@Service("mcpMessageRootNode")
public class RootNode extends AbstractMcpMessageServiceSupport {

    @Resource(name = "mcpSesssionNode")
    private SesssionNode sesssionNode;

    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        try {
            log.info("消息处理 mcp message RootNode:{}", requestParameter);

            return router(requestParameter, dynamicContext);
        } catch (Exception e) {
            log.error("消息处理 mcp message RootNode:{}", requestParameter, e);
            throw e;
        }
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity handleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return sesssionNode;
    }
}

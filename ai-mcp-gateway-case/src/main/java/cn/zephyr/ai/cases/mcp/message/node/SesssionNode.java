package cn.zephyr.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import cn.zephyr.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Zhulejun @Zephyr
 * @description 会话节点
 * @create 2026/5/10 下午8:33
 */
@Slf4j
@Service("mcpSesssionNode")
public class SesssionNode extends AbstractMcpMessageServiceSupport {

    @Resource(name = "mcpMessageMessageHandlerNode")
    private MessageHandlerNode messageHandlerNode;


    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        log.info("消息处理 mcp message SessionNode:{}", requestParameter);

        SessionConfigVO sessionConfigVO = sessionManagementService.getSession(requestParameter.getSessionId());
        if (sessionConfigVO == null) {
            log.warn("会话不存在或已过期，gatewayId:{} sessionId:{}", requestParameter.getGatewayId(), requestParameter.getSessionId());
            return ResponseEntity.notFound().build();
        }

        dynamicContext.setSessionConfigVO(sessionConfigVO);

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<HandleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext, ResponseEntity<Void>> get(HandleMessageCommandEntity handleMessageCommandEntity, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        return messageHandlerNode;
    }
}

package cn.zephyr.ai.cases.mcp.message.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;

import cn.zephyr.ai.cases.mcp.message.AbstractMcpMessageServiceSupport;
import cn.zephyr.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import cn.zephyr.ai.domain.auth.adapter.repository.IAuthRepository;
import cn.zephyr.ai.domain.auth.model.entity.RateLimitCommandEntity;
import cn.zephyr.ai.domain.auth.service.IAuthRateLimitService;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.enums.SessionMessageHandlerMethodEnum;
import cn.zephyr.ai.types.enums.McpErrorCodes;
import cn.zephyr.ai.types.exception.AppException;
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

    @Resource
    private IAuthRateLimitService rateLimitService;

    @Override
    protected ResponseEntity<Void> doApply(HandleMessageCommandEntity requestParameter, DefaultMcpMessageFactory.DynamicContext dynamicContext) throws Exception {
        try {
            log.info("消息处理 mcp message RootNode:{}", requestParameter);

            //判断命中工具调用做限流处理
            if(requestParameter.getJsonrpcMessage() instanceof McpSchemaVO.JSONRPCRequest request){
                String method = request.method();

                SessionMessageHandlerMethodEnum sessionMessageHandlerMethodEnum = SessionMessageHandlerMethodEnum.getByMethod(method);
                if(SessionMessageHandlerMethodEnum.TOOLS_CALL.equals(sessionMessageHandlerMethodEnum)){
                    //是 （true） 否（false）命中限流
                    boolean isHit = rateLimitService.rateLimit(new RateLimitCommandEntity(requestParameter.getGatewayId(), requestParameter.getApiKey()));
                    if(isHit){
                        log.warn("消息处理 mcp message RootNode - 命中限流{} {}", requestParameter.getGatewayId(), requestParameter.getApiKey());
                        throw new AppException(McpErrorCodes.INSUFFICIENT_PERMISSIONS,"fail to auth apikey rateLimiter");
                    }
                }

            }
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

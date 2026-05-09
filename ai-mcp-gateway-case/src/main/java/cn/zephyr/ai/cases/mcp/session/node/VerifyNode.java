package cn.zephyr.ai.cases.mcp.session.node;

import cn.bugstack.wrench.design.framework.tree.StrategyHandler;
import cn.zephyr.ai.cases.mcp.session.AbstractMcpMessageSupport;
import cn.zephyr.ai.cases.mcp.session.factory.DefalutMcpSessionFactory;
import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;
import cn.zephyr.ai.domain.auth.service.IAuthLicenseService;
import cn.zephyr.ai.types.enums.McpErrorCodes;
import cn.zephyr.ai.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 鉴权核验
 * @create 2026/5/9 上午11:21
 */
@Slf4j
@Service("mcpSessionVerifyNode")
public class VerifyNode extends AbstractMcpMessageSupport {

    @Resource(name = "mcpSessionSessionNode")
    private SessionNode sessionNode;


    @Override
    protected Flux<ServerSentEvent<String>> doApply(String requestParameter, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {

        return router(requestParameter, dynamicContext);
    }

    @Override
    public StrategyHandler<String, DefalutMcpSessionFactory.DynamicContext, Flux<ServerSentEvent<String>>> get(String s, DefalutMcpSessionFactory.DynamicContext dynamicContext) throws Exception {
        return sessionNode;
    }
}

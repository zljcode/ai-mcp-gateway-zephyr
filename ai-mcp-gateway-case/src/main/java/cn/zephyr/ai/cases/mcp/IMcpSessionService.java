package cn.zephyr.ai.cases.mcp;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

/**
 * @author Zhulejun @Zephyr
 * @description MCP会话服务接口
 * @create 2026/5/9 上午11:18
 */
@Service
public interface IMcpSessionService {

    /**
     * 创建SSE会话
     *
     * @param gatewayId 网关ID（后续还要扩展 apiKey 验证字段）
     * @return
     */
    public Flux<ServerSentEvent<String>> createMcpSession(String gatewayId) throws Exception;
}

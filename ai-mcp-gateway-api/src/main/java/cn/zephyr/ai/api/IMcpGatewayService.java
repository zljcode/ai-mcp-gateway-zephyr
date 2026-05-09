package cn.zephyr.ai.api;

import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;

/**
 * @author Zhulejun @Zephyr
 * @description 定义接口标准
 * @create 2026/5/9 上午11:17
 */
public interface IMcpGatewayService {

    /**
     * SSE接口 建立SSE连接 - 后续还可以有RPC接口那样
     *
     * @param gatewayId 网关ID
     * @return
     * @throws Exception
     */
    Flux<ServerSentEvent<String>> establishSSEConnection(String gatewayId) throws Exception;
}

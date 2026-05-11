package cn.zephyr.ai.api;

import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Zhulejun @Zephyr
 * @description Api网关服务接口
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
    Flux<ServerSentEvent<String>> establishSSEConnection(String gatewayId,String apiKey) throws Exception;

    /**
     * 处理SSE消息
     * @param gatewayId
     * @param sessionId
     * @param messageBody
     * @return
     */
    Mono<ResponseEntity<Void>> handleMessage(String gatewayId,String apiKey, String sessionId, String messageBody);
}

package cn.zephyr.ai.trigger.http;

import cn.zephyr.ai.api.IMcpGatewayService;
import cn.zephyr.ai.cases.mcp.IMcpMessageService;
import cn.zephyr.ai.cases.mcp.IMcpSessionService;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;
import cn.zephyr.ai.domain.session.service.ISessionMessageService;
import cn.zephyr.ai.domain.session.service.management.SessionManagementService;
import cn.zephyr.ai.types.enums.ResponseCode;
import cn.zephyr.ai.types.exception.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 实现网关连接接口
 * @create 2026/5/9 上午11:19
 */
@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS})
@RequestMapping("/")
public class McpGatewayController implements IMcpGatewayService {

    @Resource
    private IMcpSessionService mcpSessionService;

    @Resource
    private IMcpMessageService mcpMessageService;

    /*
        如果你的启动后，访问不到服务，那么可以在 System.out.println("xxxx"); 一行打断点，
        看看有没有被 Spring 容器扫描到并管理，
        如果没有，那么你的 app 层的 pom 引入了 trigger 不，此外，spring 默认的扫描路径覆盖到了 trigger 层。
     */
    public McpGatewayController() {
        System.out.println("***");
    }

    /**
     * 实现SSE连接接口 建立SSE连接，创建会话
     *
     * @param gatewayId 网关ID
     * @return
     * @throws Exception
     */
    @GetMapping(value = "{gatewayId}/mcp/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Override
    public Flux<ServerSentEvent<String>> establishSSEConnection(@PathVariable("gatewayId") String gatewayId) throws Exception {
        try {
            log.info("建立MCP SSE连接，gatewayId:{}", gatewayId);
            if (StringUtils.isBlank(gatewayId)) {
                log.info("非法参数，gateway is null");
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }

            return mcpSessionService.createMcpSession(gatewayId);
        } catch (Exception e) {
            log.error("建立 MCP SSE 连接失败，gatewayId：{}", gatewayId, e);
            throw e;
        }
    }

    /**
     * 处理 sse 消息，响应会话
     *
     * @param gatewayId   网关ID
     * @param sessionId   会话ID
     * @param messageBody 请求消息
     * @return 响应结果
     * <br/>
     * {
     * "jsonrpc": "2.0",
     * "method": "initialize",
     * "id": "95835f74-0",
     * "params": {
     * "protocolVersion": "2024-11-05",
     * "capabilities": {},
     * "clientInfo": {
     * "name": "Java SDK MCP Client",
     * "version": "1.0.0"
     * }
     * }
     * }
     */
    @Override
    @PostMapping(value = "{gatewayId}/mcp/sse", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> handleMessage(@PathVariable("gatewayId") String gatewayId,
                                                    @RequestParam("sessionId") String sessionId,
                                                    @RequestBody String messageBody) {
        try {
            log.info("处理 MCP SSE 消息，gatewayId:{} sessionId:{} messageBody:{}", gatewayId, sessionId, messageBody);

            if(StringUtils.isBlank(gatewayId)|| StringUtils.isBlank(sessionId)) {
                log.info("非法参数，gatewayId、sessionId is null");
                throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(),ResponseCode.ILLEGAL_PARAMETER.getInfo());
            }

            HandleMessageCommandEntity commandEntity = new HandleMessageCommandEntity(gatewayId, sessionId, messageBody);
            ResponseEntity<Void> responseEntity = mcpMessageService.handleMessage(commandEntity);

            return Mono.justOrEmpty(responseEntity);

        } catch (Exception e) {
            log.error("处理 MCP SSE 消息失败，gatewayId:{} sessionId:{} messageBody:{}", gatewayId, sessionId, messageBody, e);
            return Mono.just(ResponseEntity.internalServerError().build());
        }
    }

}

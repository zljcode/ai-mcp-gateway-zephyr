package cn.zephyr.ai.trigger.http;

import cn.zephyr.ai.api.IMcpGatewayService;
import cn.zephyr.ai.cases.mcp.IMcpSessionService;
import cn.zephyr.ai.types.enums.ResponseCode;
import cn.zephyr.ai.types.exception.AppException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author Zhulejun @Zephyr
 * @description 实现网关连接接口
 * @create 2026/5/9 上午11:19
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping
public class McpGatewayController implements IMcpGatewayService {

    @Resource
    private IMcpSessionService mcpSessionService;

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
}

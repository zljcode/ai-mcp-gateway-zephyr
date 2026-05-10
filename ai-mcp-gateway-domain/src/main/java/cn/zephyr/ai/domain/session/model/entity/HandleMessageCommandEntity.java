package cn.zephyr.ai.domain.session.model.entity;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import lombok.Data;

import java.io.IOException;

/**
 * @author Zhulejun @Zephyr
 * @description 处理消息命令实体对象
 * @create 2026/5/9 下午2:45
 */
@Data
public class HandleMessageCommandEntity {
    private String gatewayId;
    private String sessionId;
    private String apiKey;

    private McpSchemaVO.JSONRPCMessage jsonrpcMessage;

    public HandleMessageCommandEntity(String gatewayId, String sessionId, String messageBody) throws IOException {
        this.gatewayId = gatewayId;
        this.sessionId = sessionId;
        this.jsonrpcMessage = McpSchemaVO.deserializeJsonRpcMessage(messageBody);
    }

    public HandleMessageCommandEntity(String gatewayId, String sessionId, String apiKey, String messageBody) throws IOException {
        this.gatewayId = gatewayId;
        this.sessionId = sessionId;
        this.apiKey = apiKey;
        this.jsonrpcMessage = McpSchemaVO.deserializeJsonRpcMessage(messageBody);
    }
}

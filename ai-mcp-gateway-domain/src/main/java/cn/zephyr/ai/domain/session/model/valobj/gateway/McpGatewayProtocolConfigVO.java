package cn.zephyr.ai.domain.session.model.valobj.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 协议配置
 * @create 2026/5/10 下午4:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class McpGatewayProtocolConfigVO {
    private HTTPConfig httpConfig;

    @Data
    public static class HTTPConfig {
        private String httpUrl;
        private String httpHeaders;
        private String httpMethod;
        private Integer timeout;
    }
}

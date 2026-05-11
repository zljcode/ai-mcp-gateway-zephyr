package cn.zephyr.ai.domain.gateway.model.entity;

import cn.zephyr.ai.domain.gateway.model.valobj.GatewayToolConfigVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 网关工具配置命令实体
 * @create 2026/5/11 下午2:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatewayToolConfigCommandEntity {

    private GatewayToolConfigVO gatewayToolConfigVO;

    public static GatewayToolConfigCommandEntity buildUpdateGatewayProtocol(String gatewayId, Long toolId, Long protocolId, String protocolType) {
        return GatewayToolConfigCommandEntity.builder()
                .gatewayToolConfigVO(
                        GatewayToolConfigVO.builder()
                                .gatewayId(gatewayId)
                                .toolId(toolId)
                                .protocolId(protocolId)
                                .protocolType(protocolType)
                                .build())
                .build();
    }

}

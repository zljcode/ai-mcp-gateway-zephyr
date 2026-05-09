package cn.zephyr.ai.domain.auth.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 权限证书命令实体对象
 * @create 2026/5/9 下午3:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseCommandEntity {
    /**
     * 网关ID
     */
    private String gatewayId;

    /**
     * API密钥
     */
    private String apiKey;
}

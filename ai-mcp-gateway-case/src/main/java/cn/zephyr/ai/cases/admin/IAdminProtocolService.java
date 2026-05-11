package cn.zephyr.ai.cases.admin;

import cn.zephyr.ai.domain.protocol.model.entity.StorageCommandEntity;

/**
 * 协议配置管理
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/3/24 08:10
 */
public interface IAdminProtocolService {

    void saveGatewayProtocol(StorageCommandEntity commandEntity);

}

package cn.zephyr.ai.cases.admin;

import cn.zephyr.ai.domain.admin.model.entity.GatewayConfigEntity;

import java.util.List;

/**
 * 运营管理
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/3/26
 */
public interface IAdminManageService {

    List<GatewayConfigEntity> queryGatewayConfigList();

}
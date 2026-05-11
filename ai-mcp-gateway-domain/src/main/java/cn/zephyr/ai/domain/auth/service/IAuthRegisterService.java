package cn.zephyr.ai.domain.auth.service;

import cn.zephyr.ai.domain.auth.model.entity.RegisterCommandEntity;

/**
 * @author Zhulejun @Zephyr
 * @description 权限注册服务接口
 * @create 2026/5/10 下午9:54
 */
public interface IAuthRegisterService {

    String register(RegisterCommandEntity commandEntity);

    /**
     * 删除
     */
    void deleteGatewayAuth(String gatewayId);
}

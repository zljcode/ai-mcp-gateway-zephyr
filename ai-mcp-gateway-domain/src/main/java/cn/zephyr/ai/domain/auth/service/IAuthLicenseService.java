package cn.zephyr.ai.domain.auth.service;

import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;

/**
 * @author Zhulejun @Zephyr
 * @description 权限证书服务接口
 * @create 2026/5/9 下午3:11
 */
public interface IAuthLicenseService {

    boolean checkLicense(LicenseCommandEntity commandEntity);
}

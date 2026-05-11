package cn.zephyr.ai.domain.admin.service.impl;

import cn.zephyr.ai.domain.admin.adapter.respository.IAdminRepository;
import cn.zephyr.ai.domain.admin.model.entity.GatewayConfigEntity;
import cn.zephyr.ai.domain.admin.service.IAdminService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/3/26
 */
@Service
public class AdminService implements IAdminService {

    @Resource
    private IAdminRepository adminRepository;

    @Override
    public List<GatewayConfigEntity> queryGatewayConfigList() {
        return adminRepository.queryGatewayConfigList();
    }

}
package cn.zephyr.ai.domain.auth.service.register;

import cn.zephyr.ai.domain.auth.adapter.repository.IAuthRepository;
import cn.zephyr.ai.domain.auth.model.entity.RegisterCommandEntity;
import cn.zephyr.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import cn.zephyr.ai.domain.auth.model.valobj.enums.AuthStatusEnum;
import cn.zephyr.ai.domain.auth.service.IAuthRegisterService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 *@author Zhulejun @Zephyr
 *@description 权限注册服务
 *@create 2026/5/10 下午9:56
 */
@Service
@Slf4j
public class AuthRegisterService implements IAuthRegisterService {

    @Resource
    private IAuthRepository repository;


    @Override
    public String register(RegisterCommandEntity commandEntity) {
        //1、生成Api key | gw网关缩写，方便区分
        String apiKey = "gw-" + RandomStringUtils.randomAlphanumeric(48);

        //2、构建聚合对象
        McpGatewayAuthVO mcpGatewayAuthVO = McpGatewayAuthVO.builder()
                .gatewayId(commandEntity.getGatewayId())
                .apiKey(apiKey)
                .rateLimit(commandEntity.getRateLimit())
                .expireTime(commandEntity.getExpireTime())
                .status(AuthStatusEnum.AuthConfig.ENABLE)
                .build();

        //3、保存依据
        repository.saveGatewayAuth(mcpGatewayAuthVO);

        return apiKey;
    }

    @Override
    public void deleteGatewayAuth(String gatewayId) {
        repository.deleteGatewayAuth(gatewayId);
    }

}

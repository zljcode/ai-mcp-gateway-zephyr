package cn.zephyr.ai.domain.auth.service.ratelimit;

import cn.zephyr.ai.domain.auth.adapter.repository.IAuthRepository;
import cn.zephyr.ai.domain.auth.model.entity.LicenseCommandEntity;
import cn.zephyr.ai.domain.auth.model.valobj.McpGatewayAuthVO;
import cn.zephyr.ai.domain.auth.service.IAuthRateLimitService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.RateLimiter;
import jakarta.annotation.Resource;
import cn.zephyr.ai.domain.auth.model.entity.RateLimitCommandEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午9:55
 */
@Service
@Slf4j
public class AuthRateLimitService implements IAuthRateLimitService {

    @Resource
    private IAuthRepository authRepository;

    private final Cache<String, RateLimiter> rateLimiterCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build();

    @Override
    public boolean rateLimit(RateLimitCommandEntity commandEntity) {
        String gatewayId = commandEntity.getGatewayId();
        String apiKey = commandEntity.getApiKey();

        if (StringUtils.isBlank(apiKey)) {
            return false;
        }

        try{
            //1、获取限流组件
            RateLimiter rateLimiter = rateLimiterCache.get(gatewayId + "_" + apiKey, () ->{
                McpGatewayAuthVO mcpGatewayAuthVO = authRepository.queryEffectiveGatewayAuthInfo(new LicenseCommandEntity(gatewayId, apiKey));
                if(null == mcpGatewayAuthVO || null == mcpGatewayAuthVO.getRateLimit()){
                    throw new IllegalArgumentException("未配置限流");
                }

                //速率限制（次/小时）转换为（次/秒）
                double permitsPerSecond = (double) mcpGatewayAuthVO.getRateLimit() / 3600;
                if(permitsPerSecond <= 0){
                    throw new IllegalArgumentException("限流值不正确");
                }

                return RateLimiter.create(permitsPerSecond);

            });
            //2、尝试获取令牌
            return !rateLimiter.tryAcquire();
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            //如果五配置，按原逻辑返回false（不限流）
            if(cause instanceof IllegalStateException){
                return false;
            }
            //如果是配置为 0/负数，按原逻辑返回 true (限流/禁止)
            if (cause instanceof IllegalArgumentException) {
                return true;
            }

            // 其他异常（如数据库错误），记录日志并放行
            log.error("限流校验失败 gatewayId:{} apiKey:{}", gatewayId, apiKey, e);
            return false;
        }
    }
}

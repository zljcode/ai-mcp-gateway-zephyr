package cn.zephyr.ai.domain.auth.service;

import cn.zephyr.ai.domain.auth.model.entity.RateLimitCommandEntity;

/**
 * @author Zhulejun @Zephyr
 * @description
 * @create 2026/5/10 下午9:54
 */
public interface IAuthRateLimitService {

    boolean rateLimit(RateLimitCommandEntity commandEntity);
}

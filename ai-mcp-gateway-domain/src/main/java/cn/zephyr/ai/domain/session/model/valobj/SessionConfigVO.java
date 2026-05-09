package cn.zephyr.ai.domain.session.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Sinks;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * @author Zhulejun @Zephyr
 * @description 会话配置VO视图对象
 * @create 2026/5/9 上午9:50
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionConfigVO {
    /**
     * 会话唯一标识符
     */
    private String sessionId;

    /**
     * 流式响应
     */
    private Sinks.Many<ServerSentEvent<String>> sink;

    /**
     * 会话时间
     */
    private Instant createTime;

    /**
     * 最后访问时间戳，volatile 确保多线程下可见性
     */
    private volatile Instant lastAccessedTime;

    /**
     * 会话活跃状态标识
     */
    private volatile boolean active;

    public SessionConfigVO(String sessionId, Sinks.Many<ServerSentEvent<String>> sink) {
        this.sessionId = sessionId;
        this.sink = sink;
        this.createTime = Instant.now();
        this.lastAccessedTime = Instant.now();
        this.active = true;
    }

    /**
     * 标记会话为非活跃状态
     */
    public void markInactive() {
        this.active = false;
    }

    /**
     * 更新最后访问时间
     */
    public void updateLastAccessedTime() {
        this.lastAccessedTime = Instant.now();
    }

    /**
     * 过期时间判断
     */
    public boolean isExpired(long timeoutMinutes) {
        return lastAccessedTime.isBefore(Instant.now().minus(timeoutMinutes, ChronoUnit.MINUTES));
    }
}

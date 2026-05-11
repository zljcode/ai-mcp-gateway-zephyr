package cn.zephyr.ai.domain.session.service;


import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;

/**
 * @author Zhulejun @Zephyr
 * @description 会话管理服务接口
 * @create 2026/5/9 上午9:50
 */
public interface ISessionManagementService {
    /**
     * 创建回话
     * @return 会话配置
     */
    SessionConfigVO createSession(String gatewayId,String apiKey);

    /**
     * 删除回话
     * @param sessionId 会话ID
     */
    void removeSession(String sessionId);

    /**
     * 获取会话
     * @param sessionId 会话ID
     * @return 会话配置
     */
    SessionConfigVO getSession(String sessionId);

    /**
     * 清理过期会话
     */
    void cleanupExpiredSessions();

    /**
     * 关闭服务时，清理资源使用
     */
    void shutdown();
}

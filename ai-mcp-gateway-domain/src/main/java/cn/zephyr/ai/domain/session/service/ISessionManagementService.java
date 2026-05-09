package cn.zephyr.ai.domain.session.service;


import cn.zephyr.ai.domain.session.model.valobj.SessionConfigVO;

/**
 * @author Zhulejun @Zephyr
 * @description 会话管理服务接口
 * @create 2026/5/9 上午9:50
 */
public interface ISessionManagementService {
    /**
     * 创建会话
     *
     * @param sessionId
     * @return
     */
    public SessionConfigVO createSession(String sessionId);

    /**
     * 删除会话
     *
     * @param sessionId
     */
    public void removeSession(String sessionId);

    /**
     * 获取会话
     *
     * @param sessionId
     */
    public SessionConfigVO  getSession(String sessionId);

    /**
     * 关闭会话服务
     */
    public void shutDown();
}

package cn.zephyr.ai.cases.mcp;

import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import org.springframework.http.ResponseEntity;

import java.util.logging.Handler;

/**
 * @author Zhulejun @Zephyr
 * @description MCP服务接口
 * @create 2026/5/9 上午11:18
 */
public interface IMcpMessageService {

    ResponseEntity<Void> handleMessage(HandleMessageCommandEntity commandEntity) throws Exception;
}

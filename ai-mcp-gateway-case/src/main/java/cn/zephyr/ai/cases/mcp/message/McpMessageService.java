package cn.zephyr.ai.cases.mcp.message;

import cn.zephyr.ai.cases.mcp.IMcpMessageService;
import cn.zephyr.ai.cases.mcp.message.factory.DefaultMcpMessageFactory;
import cn.zephyr.ai.domain.session.model.entity.HandleMessageCommandEntity;
import jakarta.annotation.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author Zhulejun @Zephyr
 * @description 消息处理接口
 * @create 2026/5/10 下午8:32
 */
@Service
public class McpMessageService implements IMcpMessageService {

    @Resource
    private DefaultMcpMessageFactory factory;

    @Override
    public ResponseEntity<Void> handleMessage(HandleMessageCommandEntity commandEntity) throws Exception {
        DefaultMcpMessageFactory.DynamicContext dynamicContext = new DefaultMcpMessageFactory.DynamicContext();
        return factory.strategyHandler().apply(commandEntity, dynamicContext);
    }
}

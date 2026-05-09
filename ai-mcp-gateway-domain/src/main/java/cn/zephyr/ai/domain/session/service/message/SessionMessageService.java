package cn.zephyr.ai.domain.session.service.message;

import cn.zephyr.ai.domain.session.model.valobj.McpSchemaVO;
import cn.zephyr.ai.domain.session.model.valobj.enums.SessionMessageHandlerMethodEnum;
import cn.zephyr.ai.domain.session.service.ISessionMessageService;
import cn.zephyr.ai.domain.session.service.message.handler.IRequestHandler;
import cn.zephyr.ai.types.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

import static cn.zephyr.ai.types.enums.ResponseCode.METHOD_NOT_FOUND;

/**
 * @author Zhulejun @Zephyr
 * @description 会话消息服务接口
 * @create 2026/5/9 下午4:45
 */
@Slf4j
@Service
public class SessionMessageService implements ISessionMessageService {

    @Resource
    private Map<String, IRequestHandler> requestHandlerMap;

    @Override
    public McpSchemaVO.JSONRPCResponse processHandlerMessage(McpSchemaVO.JSONRPCRequest request) {
        String method = request.method();
        log.info("开始处理方法，方法：{}",method);

        SessionMessageHandlerMethodEnum sessionMessageHandlerMethodEnum = SessionMessageHandlerMethodEnum.getByMethod(method);
        if(null == sessionMessageHandlerMethodEnum){
            throw new AppException(METHOD_NOT_FOUND.getCode(), METHOD_NOT_FOUND.getInfo());
        }

        String handlerName = sessionMessageHandlerMethodEnum.getHandlerName();
        IRequestHandler requestHandler = requestHandlerMap.get(handlerName);
        if(null == requestHandler){
            throw new AppException(METHOD_NOT_FOUND.getCode(), METHOD_NOT_FOUND.getInfo());
        }

        //使用枚举策略模式处理请求
        return requestHandler.handle(request);
    }
}

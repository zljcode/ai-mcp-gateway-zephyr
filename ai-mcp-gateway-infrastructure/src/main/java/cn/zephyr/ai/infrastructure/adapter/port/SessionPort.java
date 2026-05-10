package cn.zephyr.ai.infrastructure.adapter.port;

import cn.zephyr.ai.domain.session.adapter.port.ISessionPort;
import cn.zephyr.ai.domain.session.adapter.repository.ISessionRepository;
import cn.zephyr.ai.domain.session.model.valobj.gateway.McpGatewayProtocolConfigVO;
import cn.zephyr.ai.infrastructure.gateway.GenericHttpGateway;
import cn.zephyr.ai.types.enums.ResponseCode;
import cn.zephyr.ai.types.exception.AppException;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zhulejun @Zephyr
 * @description 会话端口服务
 * @create 2026/5/10 下午4:28
 */
@Service
public class SessionPort implements ISessionPort {

    @Resource
    private GenericHttpGateway gateway;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object toolCall(McpGatewayProtocolConfigVO.HTTPConfig httpConfig, Object params) throws IOException {
        //1、构建请求头
        String httpHeaders = httpConfig.getHttpHeaders();

        Map<String, Object> headers = objectMapper.readValue(httpHeaders, Map.class);

        //2、判断请求方法
        String httpMethod = httpConfig.getHttpMethod();

        //3、参数校验
        if (!(params instanceof Map<?, ?> arguments)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        if (arguments.isEmpty()) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), ResponseCode.ILLEGAL_PARAMETER.getInfo());
        }

        switch (httpMethod.toUpperCase()) {
            //1、构建请求体
            case "POST": {
                Object requestBodyObj = arguments.values().iterator().next();
                RequestBody requestBody = RequestBody.create(JSON.toJSONString(requestBodyObj),
                        MediaType.parse("application/json"));

                Call<ResponseBody> call = gateway.post(httpConfig.getHttpUrl(), headers, requestBody);
                ResponseBody responseBody = call.execute().body();

                assert responseBody != null;
                return responseBody.string();
            }

            //2、执行GET请求
            case "GET": {
                HashMap<String, Object> objectHashMap = new HashMap<>((Map<String, Object>) arguments.values().iterator().next());

                String url = httpConfig.getHttpUrl();

                //替换路径参数
                Matcher matcher = Pattern.compile("\\{([^}]+)\\}").matcher(url);
                while (matcher.find()) {
                    String name = matcher.group(1);
                    if (objectHashMap.containsKey(name)) {
                        url = url.replace("{" + name + "}", String.valueOf(objectHashMap.get(name)));
                        objectHashMap.remove(name);
                    }
                }

                Call<ResponseBody> call = gateway.get(url, headers, objectHashMap);
                ResponseBody responseBody = call.execute().body();

                assert responseBody != null;
                return responseBody.string();
            }
        }

        throw new AppException(ResponseCode.METHOD_NOT_FOUND.getCode(), ResponseCode.METHOD_NOT_FOUND.getInfo());
    }
}

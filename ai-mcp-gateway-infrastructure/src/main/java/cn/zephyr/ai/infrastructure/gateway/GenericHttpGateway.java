package cn.zephyr.ai.infrastructure.gateway;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

import java.util.Map;

/**
 * 资料：<a href="https://bugstack.cn/md/road-map/http.html">HTTP 框架案例</a>
 */
/**
 * @author Zhulejun @Zephyr
 * @description HTTP 网关适配器
 * @create 2026/5/10 下午4:07
 */
public interface GenericHttpGateway {

    @POST
    Call<ResponseBody> post(
            @Url String url,
            @HeaderMap Map<String, Object> headers,
            @Body RequestBody body
    );

    @GET
    Call<ResponseBody> get(
            @Url String url,
            @HeaderMap Map<String, Object> headers,
            @QueryMap Map<String, Object> queryParams
    );

}

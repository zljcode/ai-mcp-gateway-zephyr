package cn.zephyr.ai.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Zhulejun @Zephyr
 * @description 分页返回对象
 * @create 2026/5/11 下午4:26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePage<T> implements Serializable {

    private String code;
    private String info;
    private T data;

    /**
     * 总记录数
     */
    private Long total;

}
package cn.zephyr.ai.domain.protocol.model.valobj.enums;

import cn.zephyr.ai.types.enums.ResponseCode;
import cn.zephyr.ai.types.exception.AppException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Zhulejun @Zephyr
 * @description 协议状态枚举
 * @create 2026/5/11 下午2:00
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ProtocolStatusEnum {

    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    private Integer code;
    private String info;

    public static ProtocolStatusEnum getByCode(Integer code) {
        if (null == code) {
            return null;
        }
        for (ProtocolStatusEnum anEnum : ProtocolStatusEnum.values()) {
            if (anEnum.getCode().equals(code)) {
                return anEnum;
            }
        }

        throw new AppException(ResponseCode.ENUM_NOT_FOUND.getCode(), ResponseCode.ENUM_NOT_FOUND.getInfo());
    }
}

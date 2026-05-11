package cn.zephyr.ai.domain.protocol.model.entity;

import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议存储命令实体
 * @create 2026/5/11 下午1:47
 */
@Data
@Builder
public class StorageCommandEntity {

    /**
     * 协议列表数据
     */
    private List<HTTPProtocolVO> httpProtocolVOS;
}

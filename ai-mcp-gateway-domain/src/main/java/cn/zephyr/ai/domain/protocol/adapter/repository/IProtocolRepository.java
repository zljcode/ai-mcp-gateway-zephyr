package cn.zephyr.ai.domain.protocol.adapter.repository;

import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议存储仓储接口
 * @create 2026/5/11 下午1:50
 */
public interface IProtocolRepository {

    List<Long> saveHttpProtocolAndMapping(List<HTTPProtocolVO> httpProtocolVOS);
}

package cn.zephyr.ai.domain.protocol.service;

import cn.zephyr.ai.domain.protocol.model.entity.StorageCommandEntity;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议存储服务接口
 * @create 2026/5/11 上午11:53
 */
public interface IProtocolStorage {

    /**
     * 存储操作
     * @param commandEntity
     * @return
     */
    List<Long> doStorage(StorageCommandEntity commandEntity);
}

package cn.zephyr.ai.domain.protocol.service.storage;

import cn.zephyr.ai.domain.protocol.adapter.repository.IProtocolRepository;
import cn.zephyr.ai.domain.protocol.model.entity.StorageCommandEntity;
import cn.zephyr.ai.domain.protocol.service.IProtocolStorage;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议存储服务
 * @create 2026/5/11 上午11:53
 */
@Service
public class ProtocolStorage implements IProtocolStorage {

    @Resource
    private IProtocolRepository protocolRepository;

    @Override
    public List<Long> doStorage(StorageCommandEntity commandEntity) {
        return protocolRepository.saveHttpProtocolAndMapping(commandEntity.getHttpProtocolVOS());
    }
}

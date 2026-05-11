package cn.zephyr.ai.domain.protocol.service;

import cn.zephyr.ai.domain.protocol.model.entity.AnalysisCommandEntity;
import cn.zephyr.ai.domain.protocol.model.valobj.http.HTTPProtocolVO;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议解析接口
 * @create 2026/5/11 上午11:53
 */
public interface IProtocolAnalysis {

    List<HTTPProtocolVO> doAnalysis(AnalysisCommandEntity analysisCommandEntity);
}

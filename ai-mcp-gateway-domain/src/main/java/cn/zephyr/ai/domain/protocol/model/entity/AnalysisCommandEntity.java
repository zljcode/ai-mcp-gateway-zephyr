package cn.zephyr.ai.domain.protocol.model.entity;

import cn.zephyr.ai.domain.protocol.model.valobj.enums.AnalysisTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 协议解析命令实体
 * @create 2026/5/11 上午11:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisCommandEntity {

    /**
     * 解析类型枚举
     */
    private AnalysisTypeEnum type;

    /**
     * swagger 导出的 api json 数据
     */
    private String openApiJson;

    /**
     * 解析的接口端点
     */
    private List<String> endpoints;

}

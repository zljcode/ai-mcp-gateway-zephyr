package cn.zephyr.ai.api;

import cn.zephyr.ai.api.dto.GatewayConfigDTO;
import cn.zephyr.ai.api.dto.GatewayConfigResponseDTO;
import cn.zephyr.ai.api.dto.GatewayConfigRequestDTO;
import cn.zephyr.ai.api.response.Response;

import java.util.List;

/**
 * @author Zhulejun @Zephyr
 * @description 运营配置管理服务接口
 * @create 2026/5/11 下午3:41
 */
public interface IAdminService {

    Response<GatewayConfigResponseDTO> saveGatewayConfig(GatewayConfigRequestDTO.GatewayConfig requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayToolConfig(GatewayConfigRequestDTO.GatewayToolConfig requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayProtocol(GatewayConfigRequestDTO.GatewayProtocol requestDTO);

    Response<GatewayConfigResponseDTO> saveGatewayAuth(GatewayConfigRequestDTO.GatewayAuth requestDTO);

    Response<List<GatewayConfigDTO>> queryGatewayConfigList();

}

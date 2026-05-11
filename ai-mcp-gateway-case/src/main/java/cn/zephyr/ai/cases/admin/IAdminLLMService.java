package cn.zephyr.ai.cases.admin;

import cn.zephyr.ai.api.dto.GatewayLLMRequestDTO;
import cn.zephyr.ai.api.dto.GatewayLLMResponseDTO;

/**
 * LLM 对话模型服务，测试 MCP
 *
 * @author xiaofuge bugstack.cn @小傅哥
 * 2026/4/8 07:50
 */
public interface IAdminLLMService {

    GatewayLLMResponseDTO testCallGateway(GatewayLLMRequestDTO requestDTO);

}

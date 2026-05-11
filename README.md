# AI MCP Gateway

[![Java 17](https://img.shields.io/badge/Java-17-blue)](https://adoptium.net/)
[![Spring Boot 3.4](https://img.shields.io/badge/Spring%20Boot-3.4-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-Apache%202.0-orange)](https://www.apache.org/licenses/LICENSE-2.0)

AI MCP Gateway 是一个基于 **Model Context Protocol (MCP)** 的网关服务系统，将 MCP 客户端请求路由到下游 HTTP/API 服务，并提供可视化管理后台。

---

## 架构概览

```
MCP Clients (Claude / Cursor / VS Code)
  │
  │  SSE (Server-Sent Events)
  ▼
┌──────────────────────────────────────┐
│  AI MCP Gateway  (:8777)             │
│                                      │
│  ┌──────────┐  ┌──────────────────┐ │
│  │ McpGateway│  │  AdminController │ │
│  │ Controller│  │  (管理后台 API)   │ │
│  └────┬─────┘  └───────┬──────────┘ │
│       │                │             │
│  ┌────▼────────────────▼──────────┐ │
│  │       Case 层（用例编排）       │ │
│  │  Session | Message | Admin | LLM│ │
│  └────┬───────────────────────────┘ │
│       │                              │
│  ┌────▼───────────────────────────┐ │
│  │      Domain 层（领域模型）       │ │
│  │  Auth | Gateway | Protocol      │ │
│  │  Session | LLM | Admin          │ │
│  └────┬───────────────────────────┘ │
│       │                              │
│  ┌────▼───────────────────────────┐ │
│  │   Infrastructure 层（持久化）    │ │
│  │   MyBatis + MySQL + Guava       │ │
│  └────────────────────────────────┘ │
└──────────────────────────────────────┘
  │
  ▼
MCP Demo Server  (:8701)
  │  /api/v1/mcp/get_company_employee
  │  /api/v1/mcp/query-by-id-*
  ▼
```

---

## 模块说明

| 模块 | 层级 | 职责 |
|------|------|------|
| `ai-mcp-gateway-api` | API 契约层 | 接口定义（`IAdminService`、`IMcpGatewayService`）、DTO、Response |
| `ai-mcp-gateway-app` | 启动 & 配置 | Spring Boot 入口、`LLMConfig`、`ThreadPoolConfig`、MyBatis XML |
| `ai-mcp-gateway-trigger` | 触发器层 | REST Controller：`McpGatewayController`（MCP SSE 入口）+ `AdminController`（管理后台） |
| `ai-mcp-gateway-case` | 用例编排层 | 业务流程串联：MCP 会话/消息编排、Admin CRUD 编排、LLM 对话编排 |
| `ai-mcp-gateway-domain` | 领域层 | 领域模型 + 服务 + 仓储接口（6 个域：auth / gateway / protocol / session / llm / admin） |
| `ai-mcp-gateway-infrastructure` | 基础设施层 | DAO、PO、MyBatis 仓储实现、数据库访问 |
| `ai-mcp-gateway-types` | 通用类型 | 异常、响应码、枚举 |

### Domain 领域划分

| 域 | 包路径 | 职责 |
|----|--------|------|
| **auth** | `domain.auth` | 三层鉴权体系：注册 API Key + 证书校验 + 限流 |
| **gateway** | `domain.gateway` | 网关配置 CRUD、工具配置管理 |
| **protocol** | `domain.protocol` | Swagger/OpenAPI 解析 → `HTTPProtocolVO` → 持久化存储 |
| **session** | `domain.session` | SSE 会话管理（创建、超时清理、消息转发） |
| **llm** | `domain.llm` | ChatModel 构建 + MCP 客户端初始化 + 对话缓存 |
| **admin** | `domain.admin` | 管理端查询服务（分页、关联查询） |

---

## 核心功能

### 1. MCP SSE 网关

- 客户端通过 `GET /api-gateway/{gatewayId}/mcp/sse?api_key=xxx` 建立 SSE 长连接
- 网关进行鉴权校验（API Key + 限流），创建会话，返回 `endpoint` 事件
- 客户端 POST 消息到 endpoint URL，网关路由到对应 Tool Handler 处理
- 支持 MCP 标准协议：`initialize` → `tools/list` → `tools/call`

### 2. 管理后台

| 端点 | 功能 |
|------|------|
| `POST /admin/save_gateway_config` | 新增网关配置 |
| `GET /admin/query_gateway_config_page` | 网关分页查询 |
| `POST /admin/save_gateway_tool_config` | 新增工具配置 |
| `GET /admin/query_gateway_tool_page` | 工具分页查询 |
| `POST /admin/save_gateway_protocol` | 新增协议配置 |
| `POST /admin/import_gateway_protocol` | Swagger JSON 导入协议 |
| `POST /admin/analysis_protocol` | Swagger JSON 解析预览 |
| `POST /admin/save_gateway_auth` | 注册 API Key |
| `POST /admin/test_call_gateway` | LLM 对话测试 MCP 接口 |
| `POST /admin/delete_gateway_*` | 删除配置 |

### 3. LLM 验证服务

- 通过 Spring AI + DeepSeek 大模型，调用 MCP 网关转发请求到下游服务
- ChatModel 按 gatewayId 缓存，支持 `reload` 强制重建 MCP 连接

### 4. 三层鉴权

| 层级 | 说明 |
|------|------|
| **注册鉴权** | API Key 生成（`gw-` + 48 位随机字符），绑定网关 + 过期时间 |
| **证书校验** | SSE 连接时校验 API Key 有效性和网关认证状态 |
| **限流控制** | 基于 Guava RateLimiter，按 `gatewayId + apiKey` 维度限制 ToolsCall 频率 |

---

## 技术栈

| 技术 | 版本 | 用途 |
|------|------|------|
| Java | 17 | 运行环境 |
| Spring Boot | 3.4.3 | 应用框架 |
| Spring AI | 1.1.6 | LLM 对话 + MCP 客户端/服务端 |
| MyBatis | 3.0.4 | ORM |
| MySQL | 8.x | 数据存储 |
| Guava | 32.1.3 | RateLimiter 限流 |
| Fastjson | 2.0.28 | JSON 序列化 |
| Lombok | 1.18+ | 代码简化 |
| Bootstrap 5 | CDN | 管理后台前端 UI |

---

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.x

### 1. 初始化数据库

执行 SQL 建表脚本（位于 `docs/dev-ops/mysql/sql/`）：

```
ai_mcp_gateway_v2.sql
```

核心数据表：

| 表 | 说明 |
|----|------|
| `mcp_gateway` | 网关配置表 |
| `mcp_gateway_tool` | 网关工具配置表 |
| `mcp_gateway_auth` | 鉴权配置表（API Key） |
| `mcp_protocol_http` | HTTP 协议配置表 |
| `mcp_protocol_mapping` | 协议字段映射表（嵌套参数） |

### 2. 配置环境变量

复制 `.env.example` 为 `.env`，填入真实值：

```bash
DB_USERNAME=root
DB_PASSWORD=your_password
DB_URL=jdbc:mysql://127.0.0.1:13306/ai_mcp_gateway_v2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&serverTimezone=UTC&useSSL=true
DEEPSEEK_API_KEY=sk-your-deepseek-api-key
```

### 3. 启动

```bash
# 启动网关服务（端口 8777，context-path=/api-gateway）
mvn clean package -DskipTests
java -jar ai-mcp-gateway-app/target/ai-mcp-gateway-app-1.0.0.jar

# 或 IDE 中运行 Application.java
```

### 4. 启动 MCP Demo Server

（另一个项目 `ai-mcp-gatewa-demo-mcp-server`，端口 8701）

### 5. 访问管理后台

打开 `docs/dev-ops/nginx/html/index.html`

---

## 调用流程示例

```
1. 管理后台配置网关 (gateway_005) + 工具 + 协议 + API Key

2. 管理后台测试 LLM 调用：
   POST /admin/test_call_gateway
   { gatewayId: "gateway_005", authApiKey: "gw-xxx",
     message: "获取公司雇员信息，城市北京，公司谷歌，雇员小傅哥" }

3. 网关内部流程：
   AdminLLMService
     → LLMService.buildChatModel(McpConfigVO)
       → HttpClientSseClientTransport 连接 /api-gateway/gateway_005/mcp/sse?api_key=xxx
       → McpSyncClient.initialize() —— 获取 MCP Server 工具列表
       → OpenAiChatModel + SyncMcpToolCallbackProvider
     → chatModel.call(message) —— DeepSeek 大模型调用 tools/call
     → Gateway → MCP Server → 返回结果

4. MCP Client 直连方式：
   HttpClientSseClientTransport
     .builder("http://localhost:8777")
     .sseEndpoint("/api-gateway/gateway_005/mcp/sse?api_key=gw-xxx")
     .build()
   → 同上流程
```

---

## 构建

```bash
mvn clean package -DskipTests
```

---

## 项目结构

```
ai-mcp-gateway-zephyr/
├── pom.xml                          # 根 POM（多模块管理）
├── .env.example                     # 环境变量模板
├── README.md
├── docs/
│   ├── dev-ops/
│   │   ├── mysql/sql/               # 数据库建表脚本
│   │   ├── nginx/html/              # 管理后台前端页面
│   │   │   ├── index.html           # 登录页
│   │   │   ├── admin.html           # 管理后台 SPA
│   │   │   ├── views/               # 页面视图
│   │   │   │   ├── gateway-list.html
│   │   │   │   ├── gateway-test.html
│   │   │   │   ├── gateway-tool.html
│   │   │   │   ├── gateway-protocol.html
│   │   │   │   ├── gateway-auth.html
│   │   │   │   └── dashboard.html
│   │   │   ├── css/style.css
│   │   │   └── js/app.js, config.js
│   │   └── bak/                     # 各阶段 SQL 备份
│   └── nginx/                       # Nginx 配置
│
├── ai-mcp-gateway-api/              # API 层：接口 + DTO
├── ai-mcp-gateway-app/              # 启动模块：入口 + 配置 + MyBatis XML
├── ai-mcp-gateway-trigger/          # 触发器层：Controller
├── ai-mcp-gateway-case/             # 用例层：业务编排
├── ai-mcp-gateway-domain/           # 领域层：模型 + 服务 + 仓储接口
├── ai-mcp-gateway-infrastructure/   # 基础设施层：DAO + PO + 仓储实现
└── ai-mcp-gateway-types/            # 通用类型：异常 + 枚举 + 响应码
```

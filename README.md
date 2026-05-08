# ai-mcp-gateway

`ai-mcp-gateway` 是一个多模块的 Spring Boot 项目。

## 模块说明

- `ai-mcp-gateway-app` - 应用启动模块
- `ai-mcp-gateway-api` - API 接口契约和 DTO
- `ai-mcp-gateway-domain` - 领域层
- `ai-mcp-gateway-infrastructure` - 基础设施层
- `ai-mcp-gateway-trigger` - 触发器和适配层
- `ai-mcp-gateway-types` - 通用类型和常量
- `ai-mcp-gateway-case` - 用例层

## 配置说明

开发环境配置文件 `application-dev.yml` 使用环境变量占位符来配置数据库：

- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_URL`

示例环境变量文件见 [`.env.example`](./.env.example)。

当前默认占位值为：

- `your_username`
- `your_password`
- `jdbc:mysql://127.0.0.1:3306/your_database?...`

## 本地启动

1. 复制 [`.env.example`](./.env.example) 为 `.env`。
2. 填写你本地的数据库配置。
3. 启动前在终端或 IDE 中加载这些环境变量。
4. 以 `ai-mcp-gateway-app` 模块作为启动入口运行应用。

## 构建

```bash
mvn clean package
```

## 说明

- 项目基于 Java 17。
- `application.yml` 默认激活 `dev` 配置。

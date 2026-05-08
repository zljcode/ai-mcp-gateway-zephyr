# ai-mcp-gateway

`ai-mcp-gateway` is a multi-module Spring Boot project.

## Modules

- `ai-mcp-gateway-app` - application bootstrap
- `ai-mcp-gateway-api` - API contracts and DTOs
- `ai-mcp-gateway-domain` - domain layer
- `ai-mcp-gateway-infrastructure` - infrastructure layer
- `ai-mcp-gateway-trigger` - trigger/adapters
- `ai-mcp-gateway-types` - shared types and constants
- `ai-mcp-gateway-case` - use-case layer

## Configuration

The development profile uses environment-variable placeholders for database access:

- `DB_USERNAME`
- `DB_PASSWORD`
- `DB_URL`

An example file is provided in [`.env.example`](./.env.example).

Default placeholder values in `application-dev.yml` are:

- `your_username`
- `your_password`
- `jdbc:mysql://127.0.0.1:3306/your_database?...`

## Local Setup

1. Copy [`.env.example`](./.env.example) to `.env`.
2. Fill in your local database values.
3. Load the variables in your shell or IDE before starting the app.
4. Run the application from the `ai-mcp-gateway-app` module.

## Build

```bash
mvn clean package
```

## Notes

- The project targets Java 17.
- `application-dev.yml` is the active profile by default through `application.yml`.

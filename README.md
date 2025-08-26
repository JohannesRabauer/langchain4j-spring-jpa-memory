# LangChain4j + Spring Boot + JPA Memory Demo

A minimal, production-friendly demo that shows how to:
- Run a Spring Boot application that persists chat memory using JPA.
- Talk to an LLM via LangChain4j.
- Explore and test the API using OpenAPI/Swagger UI.

The infrastructure services (database and LLM) are started via Docker Compose, and the application is started via Maven. Swagger UI is available for quick verification and manual testing.

## Prerequisites

- Docker and Docker Compose
- Java (17+) and Maven (3.9+)
- Network ports available: 8080 (app), plus any ports used by your database/LLM containers

## Quick Start

1) Start infrastructure (database + LLM)  
   Run from the project root:
   ```bash
   docker compose up -d
   ```
   - If this is your first run, the LLM container may need to download/pull a model; this can take a few minutes.

2) Start the application  
   From the project root:
   ```bash
   mvn -DskipTests spring-boot:run
   ```
   Alternatively:
   ```bash
   mvn clean package
   java -jar target/*.jar
   ```

## Verify It’s Working

Open your browser and navigate to:
- Swagger UI: http://localhost:8080/swagger-ui/index.html

Use “Try it out” within Swagger UI to send a message with a memory identifier and receive a response from the LLM.

## Configuration

You can configure the application via standard Spring configuration (application properties or environment variables), for example:
- Database connection: `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
- LLM settings (example keys): `ollama.base-url`, `ollama.model-name`

These can be changed to suit your environment without changing code.

## Swapping Placeholders

This demo uses:
- A JPA database (commonly PostgreSQL in examples)
- An LLM accessible via LangChain4j (commonly an Ollama instance in examples)

Both are placeholders:
- You can replace PostgreSQL with any JPA-compatible database (e.g., MySQL/MariaDB, SQL Server, H2, etc.) by adjusting dependencies and connection properties.
- You can replace Ollama with any LangChain4j-supported LLM/provider by changing model configuration and provider settings.

## Troubleshooting

- Ensure Docker containers are healthy:
  ```bash
  docker compose ps
  docker compose logs -f
  ```
- Verify ports are available (8080 for the app).
- First-time model pulls for the LLM can be large and take extra time.
- Check application logs for startup or connectivity issues:
  ```bash
  mvn -DskipTests spring-boot:run
  ```

## Contributing

Issues and pull requests are welcome! Please open an issue to discuss major changes.

## License

This project is open source. See the LICENSE file for details.
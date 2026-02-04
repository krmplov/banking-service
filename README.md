# banking-service

*Java Backend Application (Spring Boot, Multi-Module Maven)*

- Разработано многомодульное backend-приложение на Spring Boot
- Реализована слоистая архитектура с логическим разделением:
  - Gateway (аутентификация, безопасность, маршрутизация)
  - Presentation (REST API)
  - Business Logic (доменная логика)
  - Data Access (JPA, репозитории, Kafka producer)
  - Storage (Kafka consumer, хранилище данных)
- Использованы:
  - Spring Web для REST API
  - Spring Data JPA для работы с базой данных
  - Spring Security + JWT для аутентификации и авторизации
  - Spring Kafka для асинхронного взаимодействия между модулями
- Проект реализован с использованием Maven multi-module подхода
- Реализована глобальная обработка ошибок и валидация входных данных

Технологии:
Java, Spring Boot, Spring Security, Spring Data JPA, Kafka, Maven, REST API, JWT, Swagger (OpenAPI)

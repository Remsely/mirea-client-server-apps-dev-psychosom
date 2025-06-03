# Psychosom

## 📑 Содержание

1. [Описание проекта](#-описание-проекта)
2. [Структура проекта и стек технологий](#-структура-проекта-и-стек-технологий)
3. [Инструкция по локальному запуску](#-инструкция-по-локальному-запуску)

## 🧠 Описание проекта

**Psychosom** — это клиент-серверное fullstack-приложение для онлайн-записи на приём к психологу.

**Функциональные возможности:**

- Каталог психологов с возможностью фильтрации и выбора;
- Просмотр профиля психолога с описанием и отзывами;
- Возможность записи на консультацию к психологу;
- Telegram-бот для отслеживания записей как психологом, так и пациентом.

> 🔗 Ссылка на развернутое приложение: [http://remsely.psychosom.edu](http://109.73.196.45:3000)

---

## 🧱 Структура проекта и стек технологий

```
/
├── back/ # Серверная часть
├── front/ # Клиентская часть
├── test_data/ # Тестовые данные, которые можно использовать для заполнения системы
```

### 📦 Стек серверной части (`back/`)

- **Язык и фреймворк**: Kotlin + Spring Boot
- **Сборка**: Gradle
- **Безопасность**: Spring Security + OAuth2 + JWT (RSA)
- **WebSocket**: Spring Boot WebSocket
- **ORM и миграции**: Spring Data JPA, PostgreSQL, Flyway
- **Документация API**: springdoc-openapi
- **Библиотеки функционального программирования**: Arrow Core, Optics
- **Интеграция с Telegram**: telegrambots-spring-boot-starter
- **S3-хранилище**: AWS SDK, MinIO
- **Логирование**: SLF4J, Logback
- **Тестирование**: JUnit 5, Kotest, Spring Boot Test

### 🖥️ Стек клиентской части (`front/`)

- **Фреймворк**: React + Next.js
- **Сборка**: Bun
- **Стили**: SCSS
- **Аутентификация**: NextAuth
- **Состояние**: Zustand, React Query
- **UI-библиотеки**: Radix UI, Lucide Icons
- **Прочее**: react-hook-form, day-picker, QR-коды, слайдеры, toasts и др.

---

## 🛠️ Инструкция по локальному запуску

### 1. Клонировать репозиторий

```bash
git clone https://github.com/Remsely/mirea-client-server-apps-dev-psychosom.git
cd mirea-client-server-apps-dev-psychosom
```

### 2. Добавить RSA-ключи для JWT

Создайте директорию `back/security/src/main/resources/jwt/` и добавьте в нее два файла: `private_key.pem` и
`public_key.pem` со сгенерированными RSA-ключами на 2048 бит. Сгенерировать можно онлайн, например,
на [этом сайте](https://emn178.github.io/online-tools/rsa/key-generator/).

Примеры валидных ключей:

```
-----BEGIN PRIVATE KEY-----
MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC0jsdGw/B7yCQP
4Dy9lH5jl0a7zFENgM1aOpw4I9inKPXvOJQ1kV6xGATMfvsSS7uw7OoPb3NRqgMz
rxxqViSb7Kcwqz71k6/mj2gOMJhG0waTPm0tetaX7ZrRh6XoEm8x7N1LRCWOkYO+
lXOGE7dN6CU5WfVIJzePPq2SI1huOxliT8SY9SsiT02idqtI/6pnPNgP5P30k3J7
ZBuIsTO52M0nxvwBW5TRIlS7F194HrL2reiMZqpJ2gTnLPHCyk5imZk8UmbQi+8c
cr+/bsNc0321hieavcjfP1jrBmRQ99kRcFUY5kDI1HxWMsqXkfa1Dy+N7JpJrtqs
82xrHYcbAgMBAAECggEBAJUxuXY+59LQYQQBTQ91yS3vzVHWlU7sFEMvVPCKpjYS
AY3HGahd03nQF/8GGd5XV+LtEMbqV8auDfFCC9/OCHgTB1+O0PpJK7r+2oOHIfy7
2J4IolxHOVTWz+u9A5kMynbde0n7KHFyygLMKWYrmna0kTWfoK8bQKyLpQ6xSBvk
L2reKX9PvQqR4MuIg7+t5H/f27ty8tN42pnKeqs4clGNE78Ym6oG1PBMmQ0naPRq
9LleovLPnHJdQyTrGx9AzN+OZov/OQ/tIyxUsoCYsPWiz/Tb3vVKSnLN0UkLW4Fb
3Q0klEu/CCPHmROk+YE+THlZX0tp5cEV4cFHE4wYqLECgYEA/fFnY+G5ImV2XWj3
n5i+KDJ+KWtYdZvPkzhImdTd4/iX4bcOzf7M1BeNIJynLxEwrXzbv7K+tPosOz59
ACMuhpl3utojbCaiUaGy2NHOp6gj6URIkzk1kOhozVMsL2qL4lznR24m9vw7s+n7
/+zjkt1qmZyl4QMe6tTZTALDlxMCgYEAtgUydooofCF5Xl13FpRS3djElXMEb4uL
BoKfqrpPkTqJWcaYQdCHzvtjwjSIG+Ld9kCUnNymjFwK0JVppxmR18pww0x2YqyR
gaNPlMLFZ4RHZogrQxMYRE6OtUZb/RXs0xII2bPTr/QNb4J3JPLUthJM8UCP+lfJ
I3dNDObaqNkCgYEAoZ82LYTpXrHy7qqzB3mgdVcFovj2OQwsxcdJtdacTs7WY8st
egAUf1xp7vqlETjAoXpZJh3ucKLU6fPwfNjncLVWVa8mYlwIbSob+RE3lM9X48wY
4NLFe+/fW+b2tUr7sttsO7Moy4NnwC9Iuu8bPEr94LypepeQ4ucLUexkbc8CgYBR
8M8B3DpTf0JC//J/gLA5XL9KxCeIC5q/iIhWdF8jcuWoO6YJYtXwkO0c3Uc0vpHL
rND6OL7lSv43Xjvc1L76FRFkagSVcThj0uAdiP0TF9KIzt4i6PKwtWi7JHx+16Lw
dhrz3md6u3Rc8a+vO34UpPJsXPMz8NcEl01hdH+VKQKBgQDQ+w/d/vKgdHNkfEq/
YG+2O+sseCr9RjpAkVB747aAeNvwFM2rijcDWMRj7ZvgXxG4NLl0+f+8hiqM1G1q
aM8287pnflQEv1+OChU7Q3sAYU50iehzvsrHpzSUo8Ox1eViVy40564y8MdP9K4Y
Ndu8M9BzxZzcmMG6uAlWG7L0hw==
-----END PRIVATE KEY-----
```

```
-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAtI7HRsPwe8gkD+A8vZR+
Y5dGu8xRDYDNWjqcOCPYpyj17ziUNZFesRgEzH77Eku7sOzqD29zUaoDM68calYk
m+ynMKs+9ZOv5o9oDjCYRtMGkz5tLXrWl+2a0Yel6BJvMezdS0QljpGDvpVzhhO3
TeglOVn1SCc3jz6tkiNYbjsZYk/EmPUrIk9NonarSP+qZzzYD+T99JNye2QbiLEz
udjNJ8b8AVuU0SJUuxdfeB6y9q3ojGaqSdoE5yzxwspOYpmZPFJm0IvvHHK/v27D
XNN9tYYnmr3I3z9Y6wZkUPfZEXBVGOZAyNR8VjLKl5H2tQ8vjeyaSa7arPNsax2H
GwIDAQAB
-----END PUBLIC KEY-----
```

### 3. Запустить приложение через Docker Compose

В корневой директории проекта выполнить команду:

```bash
docker compose up --build
```

После запуска будет доступен следующий функционал:

| URL                                                                                            | Назначение                                    |
|------------------------------------------------------------------------------------------------|-----------------------------------------------|
| [http://localhost:3000](http://localhost:3000)                                                 | Веб-интерфейс приложения                      |
| [http://localhost:8080](http://localhost:8080)                                                 | API серверной части                           |
| [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/) | Документация API Серверной части в Swagger UI |

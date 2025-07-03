🛠️ Project Management System (Spring Boot + Keycloak + JWT)

Полноценная система управления проектами с безопасностью на основе ролей через Keycloak и CRUD-функциональностью. Подходит для изучения Spring Security, JWT, Keycloak и тестирования REST API.

📌 Описание

Это учебный проект, в котором реализованы:

- Многопользовательская система с ролями (ADMIN, MANAGER, DEVELOPER)
- CRUD-операции над проектами и задачами
- Безопасность с помощью Keycloak (OAuth2, JWT)
- Валидация данных, работа с DTO и MapStruct
- Интеграционные и авторизационные тесты

---

## 🔐 Роли пользователей

| Роль       | Описание                                                      |
|------------|---------------------------------------------------------------|
| ADMIN    | Полный доступ ко всему, включая управление ролями и проектами |
| MANAGER  | Создание проектов, управление командой и задачами             |
| DEVELOPER| Выполнение задач, ограниченный доступ к изменениям            |

---

 📦 Сущности

### 🧑‍💼 User
- id: Long
- username: String
- email: String
- roles: Set<Role>

### 📁 Project
- id: Long
- name: String
- description: String
- owner: User
- teamMembers: Set<User>

### ✅ Task
- id: Long
- title: String
- description: String
- status: NEW | IN_PROGRESS | DONE
- assignee: User
- project: Project

### 🎭 Role (enum)
- ADMIN, MANAGER, DEVELOPER

---

## 🔧 Функционал

### User
- Регистрация и получение профиля
- Просмотр всех пользователей (только ADMIN, MANAGER)
- Назначение ролей (только ADMIN)

### Project
- ✅ Создание: ADMIN, MANAGER
- 👁️ Просмотр по ID: участники + ADMIN
- ✏️ Обновление: владелец проекта или ADMIN
- ❌ Удаление: только ADMIN
- ➕/➖ Участники: владелец или ADMIN
- 📚 Список проектов пользователя: любой

### Task
- ✅ Создание: участники проекта
- 👁️ Просмотр: участники проекта
- ✏️ Обновление: только исполнитель (assignee) или MANAGER/ADMIN
- 🔄 Статус: только assignee
- ❌ Удаление: MANAGER, ADMIN
- 📋 Список задач проекта: участники проекта

---

## ✅ Требования

- Spring Boot 3+
- Keycloak (в docker или локально)
- Spring Security + OAuth2 Resource Server
- Spring Data JPA
- MapStruct / ModelMapper
- Lombok
- JUnit + MockMvc для тестов

---

## 🧪 Тестирование

Покрытие включает:
- ✅ Успешные CRUD операции
- ❌ Попытки нарушить правила безопасности (403)
- 🔁 Бизнес-правила (например, чужие задачи нельзя менять)
- 🧪 Работа с мок-ролями (JWT) для проверки безопасности

---

## 🚀 Развёртывание

1. Запусти Keycloak:
   `bash
   docker-compose up -d

## 📎 Swagger UI 
http://localhost:8082/swagger-ui/index.html

# Задача: **PP 3.1.3.**

---

#### Тема: _10 Spring_
#### № **ITMPJ-2764**
#### Наименование: _Микросервисная система_
#### Дедлайн: _25/11/2024_
#### Код модуля: _PP3_ //  _PreProject3_

---
# Микросервисная система

## Условие:

1. Написать `Rest`-контроллеры для вашего приложения.;
2. Необходимо установить `Postman`/`Insomnia` и разобраться как он работает;
3. С помощью `Postman`/`Insomnia` протестировать все `Rest endpoints`.

---

## В этой теме Вы изучите
* Spring Boot
* Spring Security
* Rest
* Микросервисы

---

[Ссылка на оригинальную страницу](http://jira.it-mentor.tech/browse/ITMPJ-2764)

[Ссылка на оригинальную репозиторий](https://github.com/VanderDT/Task-7.git)

[Ссылка на данную страницу](https://github.com/yury-connect/ITM_task022_SpringSecurityRest_Task_3_1_3.git)

---

## Документирую код выполненного задания по **PP 3.1.3**.

> ### Для контроллера `RestAdminController` применены следующие **CRUD** операции:
> 
> **POST** `/users` — создание нового пользователя;
> 
> **GET** `/users` — получение списка пользователей;
> 
> **GET** `/users/{id}` — получение одного пользователя;
> 
> **PUT** `/users/{id}` — обновление информации о пользователе;
> 
> **DELETE** `/users/{id}` — удаление пользователя;
> 

> ### Для контроллера `RestAdminController` использованы следующие статусы ответов для успешных операций:
> 
> `POST` — для создания ресурса с `201 Created`;
> 
> `GET` — для получения ресурса с `200 OK`;
> 
> `PUT` — для обновления ресурса с `200 OK`;
> 
> `DELETE` — для удаления ресурса с `204 No Content`;
>

> ### Основные маршруты проекта:
> #### Залогинивание:
> > **POST** `/api/auth/register` - Регистрация нового пользователя (POST)
> >
> > **POST** `/api/auth/logout` - Разлогинивание текущего пользователя (POST)
> >
> > **POST** `/api/auth/login` - Получение токена/ залогинивание/ аутентификация (POST)
> 
> #### Admin:
> > **GET** `/api/authenticated/admin/users/{id}` Получение конкретного пользователя по ID (GET)
> > 
> > **PUT** `/api/authenticated/admin/users/{id}` Обновление данных пользователя (PUT)
> > 
> > **DELETE** `/api/authenticated/admin/users/{id}` Удаление пользователя (DELETE)
> >
> > **GET** `/api/authenticated/admin/users` Получение всех пользователей (GET)
> >
> > **POST** `/api/authenticated/admin/users` Создание одного нового пользователя (POST)
> >
> > **POST** `/api/authenticated/admin/users/batch` Создание списка новых пользователей (POST)
> >
> > **DELETE** `/api/authenticated/admin/users` Удаление всех пользователей (DELETE)
> > 
> 
> #### SuperAdmin (все те-же действия, что и Admin, но дополняет своими):
> > **GET** `/api/authenticated/superadmin/system_info` Получение информации о системе, на которой запущено приложение (GET)
> >
>
> #### User:
> > GET /api/authenticated/user/users Получение информации о себе-же /залогиненном пользователе (GET)
> > 
> 
> #### Guest:
> > **GET** `/api/authenticated/guest/guests` Информирование гостей об успешном залогинивании (GET)
> >
> > #### Скрин ниже:
> ![скрин](/imgs/FireShot_Capture_Swagger_UI_localhost.png)
> 
> Полный список маршрутов можно получить через модуль SWAGER, перейдя по [ссылке](http://localhost:8088/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config) при запущеном проекте.
> _http://localhost:8088/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config_
> 


### Иерархия файлов проекта:
```text
ITM_task022_SpringSecurityRest_Task_3_1_3
src
└───main
   ├───java
   │   └───ru
   │       └───itmentor
   │           └───spring
   │               └───boot_security
   │                   └───demo
   │                       │   SpringBootSecurityDemoApplication.java
   │                       │
   │                       ├───configs
   │                       │       JwtRequestFilter.java
   │                       │       MvcConfig.java
   │                       │       PasswordEncoderConfig.java
   │                       │       SecurityConfig.java
   │                       │       SuccessUserHandler.java
   │                       │       SwaggerConfig.java
   │                       │
   │                       ├───constants
   │                       │       Constants.java
   │                       │
   │                       ├───controller
   │                       │   ├───mvc
   │                       │   │       AbstractController.java
   │                       │   │       AdminController.java
   │                       │   │       GuestController.java
   │                       │   │       HelloController.java
   │                       │   │       LoginController.java
   │                       │   │       SuperAdminController.java
   │                       │   │       UserController.java
   │                       │   │
   │                       │   └───rest
   │                       │           RestAdminController.java
   │                       │           RestAuthController.java
   │                       │           RestGuestController.java
   │                       │           RestServicesController.java
   │                       │           RestSuperAdminController.java
   │                       │           RestUserController.java
   │                       │
   │                       ├───dto
   │                       │       UserDTO.java
   │                       │       UserDTO.java~
   │                       │
   │                       ├───model
   │                       │       AuthRequest.java
   │                       │       AuthResponse.java
   │                       │       Role.java
   │                       │       User.java
   │                       │
   │                       ├───repository
   │                       │       RoleRepository.java
   │                       │       UserRepository.java
   │                       │
   │                       ├───service
   │                       │       RoleService.java
   │                       │       RoleServiceImpl.java
   │                       │       UserService.java
   │                       │       UserServiceImpl.java
   │                       │       UserUtilService.java
   │                       │       UserUtilServiceImpl.java
   │                       │
   │                       └───util
   │                               DatabaseInitializer.java
   │                               DtoUtils.java
   │                               JwtUtil.java
   │                               UserGenerator.java
   │
   └───resources
       │   application─mysql.properties
       │   application─postgresql.properties
       │   application.properties
       │
       ├───postman
       │       IT─Mentor.postman_collection_v2
       │       IT─Mentor.postman_collection_v2_1
       │       Task_3.1.3_localhost.postman_environment
       │
       ├───SQLs
       │       sql_examples.sql
       │       SQL_PostgreSQL_scrypt_1_delete_tables.sql
       │       SQL_PostgreSQL_scrypt_2_create_tebles.sql
       │       SQL_PostgreSQL_scrypt_3_add_users.sql
       │       SQL_PostgreSQL_scrypt_FULL.sql
       │
       ├───static
       │   └───css
       │           styles.css
       │
       └───templates
           ├───admin─pages
           │       all_users.html
           │       create_user_page.html
           │       delete_user_page.html
           │       update_user_page.html
           │       view_user_page.html
           │
           ├───guest─pages
           │       guest_info_page.html
           │
           ├───sevice─pages
           │       greating_page.html
           │       login_page.html
           │       registration_page.html
           │       system─info.html
           │
           └───users_pages
                   user_info_page.html
```

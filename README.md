# Задача: **PP 3.1.2.** // (Task-7)

---

#### Тема: _10 Spring_
#### № **ITMPJ-2764**
#### Наименование: _Микросервисная система_
#### Дедлайн: _25/11/2024_
#### Код модуля: _PP3_ //  _PreProject3_

---
# Микросервисная система

## Условие:

Склонируйте проект по [ссылке](https://github.com/VanderDT/Task-7) и просмотрите его.

Модуль _Spring Security_ позволяет нам внедрять права доступа, а также контролировать их исполнение без ручных проверок.

_Spring Security_ базируется на 2х интерфейсах, которые определяют связь сущностей с секьюрностью: `UserDetails` и `GrantedAuthority`.

> `UserDetails` - то, что будет интерпретироваться системой как пользователь.
>
> `GrantedAuthority` - сущность, описывающая права юзера.

Оба эти интерфейса имеют множество реализаций: просмотрите класс `WebSecurityConfig`, в методе `configure()` 
с помощью настроек `userDetailsService()` мы собираем единственный на всю программу экземпляр `UserDetails }}` 
с _именем_ и _паролем_ `user` , а его роль `"USER"` так же будет преобразована в экземпляр `{{GrantedAuthority`.

Это простейший способ создания _секьюрности_. Так же мы можем использовать _jdbc-аутентификацию_ 
путем написания запроса, возвращающего _пользователя_ и _роль_.

Как вы понимаете, такие способы максимально просты, но лишены достаточной гибкости, потому наиболее часто 
используемый вариант настройки выглядит как имплементация `{{ UserDetails}}` и `{{GrantedAuthority}}` 
в классах-сущностях с переопределением существующих методов.



##### Рассмотрим приложение.

Новые классы:

**WebSecurityConfig** - настройка _секьюрности_ по определенным `URL`, а также настройка `UserDetails` и `GrantedAuthority`.

**LoginSuccessHandler** -  _хэндлер_, содержащий в себе алгоритм действий при успешной аутентификации. Например, 
тут мы можем отправить пользователя с ролью _админа_ на _админку_ после _логина_, а с ролью _юзер_ 
на _главную страницу сайта_ и т.п.

## Задание:

> 1. Перенесите классы и зависимости из предыдущей задачи.;
> 
> 2. Создайте класс `Role` и свяжите `User` с ролями так, чтобы юзер мог иметь несколько ролей.;
>
> 3. Имплементируйте модели `{{Role}}` и `{{User}}` интерфейсами `{{GrantedAuthority}}` и `{{UserDetails}}` соответственно. 
> Измените настройку секьюрности с `{{inMemory}}` на `{{userDetailService}}`;
>
> 4. Все _CRUD-операции_ должны быть доступны только пользователю с ролью `admin` по `url: /admin/`. 
>  Добавление/ изменение юзера должно быть _с ролями за один запрос_;
>
> 5. Пользователь с ролью `user` имеет доступ только по своему `url` `/user` и получать данные только о себе. 
> Доступ к этому `url` должен быть только у пользователей с ролью `user` и `admin`. Не забывайте 
> про несколько ролей у пользователя!;
>
> 6. Настройте `logout` с любого `url`;
>
> 7. Необходимо установить `Postman`/`Insomnia` _(на выбор)_ и разобраться как он работает;
>
> 8. С помощью `Postman`/`Insomnia` протестировать все `endpoints`.;

---

## В этой теме Вы изучите
* Spring Boot
* Spring Security
* Rest
* Микросервисы

---

---

---

[Ссылка на оригинальную страницу](http://jira.it-mentor.tech/browse/ITMPJ-2764)

[Ссылка на оригинальную репозиторий](https://github.com/VanderDT/Task-7.git)

[Ссылка на данную страницу](https://github.com/yury-connect/ITM_task021_SpringSecurity_Task_3_1_2.git)

---

> Основные маршруты проекта:
>
> `http://localhost:8080/login` - залогиниться
> `http://localhost:8080/logout` - разлогиниться
>
> `http://localhost:8080/authenticated/superadmin` - админка (для Супер-админов, доп. возможности)
> `http://localhost:8080/authenticated/admin` - админка (работа с пользователями)
> `http://localhost:8080/authenticated/user` - для пользователей (только просмотр себя)
> `http://localhost:8080/authenticated/guest` - для гостей (только залогинивание и гостевая страница)
> 
> 

---

### Структура проекта:
```html
ITM_task021_SpringSecurity_Task_3_1_2
main
├───java
│   └───ru
│       └───itmentor
│           └───spring
│               └───boot_security
│                   └───demo
│                       │   SpringBootSecurityDemoApplication.java
│                       │
│                       ├───configs
│                       │       MvcConfig.java
│                       │       PasswordEncoderConfig.java
│                       │       SecurityConfig.java
│                       │       SuccessUserHandler.java
│                       │
│                       ├───constants
│                       │       Constants.java
│                       │
│                       ├───controller
│                       │       AbstractController.java
│                       │       AdminController.java
│                       │       GuestController.java
│                       │       HelloController.java
│                       │       LoginController.java
│                       │       SuperAdminController.java
│                       │       UserController.java
│                       │
│                       ├───model
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
│                               UserGenerator.java
│
└───resources
    │       application─mysql.properties
    │       application─postgresql.properties
    │       application.properties
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

---




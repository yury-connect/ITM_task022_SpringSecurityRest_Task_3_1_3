
---

## Если что-то сидит на порту 8080 _и приложение не стартует?_
### Находим и убиваем _// или меняем порт запуска приложения_.

#### `Вариант 1`: Завершите процесс, использующий порт 8080:

> Если вы работаете в терминале на **Windows**, выполните:
> ```bash
> netstat -ano | findstr :8080
> ```
> Найдите `PID` _(идентификатор процесса)_, затем завершите его:
> ```bash
> taskkill /PID <pid> /F
> ```
>
> На **Linux** или **macOS**:
> ```bash
> lsof -i :8080
> ```
> Найдите `PID` процесса, затем завершите его:
> ```bash
> kill -9 <pid>
> ```
> 
> ##### Пример (из жизни):
> ```powershell
> Windows PowerShell
> (C) Корпорация Майкрософт (Microsoft Corporation). Все права защищены.
>
> Попробуйте новую кроссплатформенную оболочку PowerShell (https://aka.ms/pscore6)
> 
> PS C:\Windows\system32> netstat -ano | findstr :8080
> TCP    0.0.0.0:8080           0.0.0.0:0              LISTENING       4040
> PS C:\Windows\system32> taskkill /PID 4040 /F
> Успешно: Процесс, с идентификатором 4040, успешно завершен.
> PS C:\Windows\system32>
> ```
>

#### **Вариант 2**: Измените порт для вашего приложения:

> Откройте файл `application.properties` или `application.yml` и укажите другой порт:
> ```properties
> server.port=8081
> ```
> или, для `application.yml`:
> ```yml
> server:
>   port: 8081
> ```
> 
 
---



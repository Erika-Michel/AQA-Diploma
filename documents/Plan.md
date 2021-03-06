# План автоматизации тестирования [сервиса] по покупке туров aqa-shop.jar
[сервиса]: https://github.com/netology-code/qa-diploma

### 1.	Автоматизированные сценарии
В ходе работы планируется выполнить автоматизацию следующих сценариев тестирования:
* **Тестирование GUI и удобства пользования**
  Проверка авто-тестами возможных пользовательских сценариев покупки тура с двумя вариантами оплаты:
  * оплата дебетовой картой
  * получение кредита по данным банковской карты
* **Тестирование формы отправки данных оплаты**
  Проверка авто-тестами позитивных и негативных сценариев отправки формы с платежными данными.
* **Интеграционное тестирование с СУБД**
  Авто-тесты на работу сервиса в интеграции с двумя СУБД (MySQL и PostgreSQL), проверка сохранения внесенных данных в базе: был ли совершён платёж, успешно ли он был совершён и каким образом (при этом данные карт сохранять не допускается).

  **Список тест-кейсов с ОР**

* Переключение со страницы оплаты на страницу оформления кредита - **страницы успешно переключена**

* Переключение со страницы оформления кредита на страницу оплаты - **страницы успешно переключена**

* Отправка заполненной формы для оплаты с действующей карты - **операция одобрена**

* Отправка заполненной формы для оформления кредита с действующей карты - **операция одобрена**

* Отправка заполненной формы для оплаты с заблокированной карты - **операция отклонена**

* Отправка заполненной формы для оформления кредита с заблокированной карты - **операция отклонена**

* Отправка заполненной формы для оплаты с действующей карты со сроком действия более 5 лет - **отображение ошибки в связи с большим сроком**

* Отправка заполненной формы для оформления кредита с действующей карты со сроком действия более 5 лет - **отображение ошибки в связи с большим сроком**

* Отправка заполненной формы для оплаты с действующей карты с истекшим годом - **отображение ошибки в связи с истекшим сроком**

* Отправка заполненной формы для оформления кредита с действующей карты с истекшим годом - **отображение ошибки в связи с истекшим сроком**

* Отправка заполненной формы для оплаты с действующей карты со сроком действия 1 месяц - **операция одобрена**

* Отправка заполненной формы для оформления кредита с действующей карты со сроком действия 1 месяц - **операция одобрена**

* Отправка заполненной формы для оплаты с действующей карты со сроком действия до текущего месяца - **операция одобрена**

* Отправка заполненной формы для оформления кредита с действующей карты со сроком действия до текущего месяца - **операция одобрена**

* Отправка заполненной формы для оплаты с действующей карты с невалидным значением в поле месяц (проверка значений 13 и 00) - **отображение ошибки в связи с неверным сроком**

* Отправка заполненной формы для оформления кредита с действующей карты с невалидным значением в поле месяц (проверка значений 13 и 00) - **отображение ошибки в связи с неверным сроком**

* Отправка заполненной формы для оплаты с действующей карты с валидным значением в поле месяц (проверка значений 01 и 12) - **операция одобрена**

* Отправка заполненной формы для оформления кредита с действующей карты с валидным значением в поле месяц (проверка значений 01 и 12) - **операция одобрена**

* Отправка пустой формы оплаты - **отображение ошибок под полями**

* Отправка пустой формы оформления кредита - **отображение ошибок под полями**

* Отправка пустого поля Номер карты на странице оплаты - **отображение ошибки**

* Отправка пустого поля Номер карты на странице оформления кредита - **отображение ошибки**

* Отправка пустого поля Месяц на странице оплаты - **отображение ошибки**

* Отправка пустого поля Месяц на странице оформления кредита - **отображение ошибки**

* Отправка пустого поля Год на странице оплаты - **отображение ошибки**

* Отправка пустого поля Год на странице оформления кредита - **отображение ошибки**

* Отправка пустого поля Владелец  на странице оплаты - **отображение ошибки**

* Отправка пустого поля Владелец на странице оформления кредита - **отображение ошибки**

* Отправка пустого поля CVC/CVV на странице оплаты - **отображение ошибки**

* Отправка пустого поля CVC/CVV на странице оформления кредита - **отображение ошибки**

* Отправка латиницы в поле Номер карты на странице оплаты - **отображение ошибки**

* Отправка латиницы в поле Номер карты на странице оформления кредита - **отображение ошибки**

* Отправка латиницы в поле Номер карты на странице оплаты - **отображение ошибки**

* Отправка латиницы в поле Номер карты на странице оформления кредита - **отображение ошибки**

* Отправка кириллицы в поле Номер карты на странице оплаты - **отображение ошибки**

* Отправка кириллицы в поле Номер карты на странице оформления кредита - **отображение ошибки**

* Отправка спецсимволов в поле Номер карты на странице оплаты - **отображение ошибки**

* Отправка спецсимволов в поле Номер карты на странице оформления кредита - **отображение ошибки**

* Отправка латиницы в поле Месяц на странице оплаты - **отображение ошибки**

* Отправка латиницы в поле Месяц на странице оформления кредита - **отображение ошибки**

* Отправка кириллицы в поле Месяц на странице оплаты - **отображение ошибки**

* Отправка кириллицы в поле Месяц на странице оформления кредита - **отображение ошибки**

* Отправка спецсимволов в поле Месяц на странице оплаты - **отображение ошибки**

* Отправка спецсимволов в поле Месяц на странице оформления кредита - **отображение ошибки**

* Отправка латиницы в поле Год на странице оплаты - **отображение ошибки**

* Отправка латиницы в поле Год на странице оформления кредита - **отображение ошибки**

* Отправка кириллицы в поле Год на странице оплаты - **отображение ошибки**

* Отправка кириллицы в поле Год на странице оформления кредита - **отображение ошибки**

* Отправка спецсимволов в поле Год на странице оплаты - **отображение ошибки**

* Отправка спецсимволов в поле Год на странице оформления кредита - **отображение ошибки**

* Отправка кириллицы в поле Владелец на странице оплаты - **отображение ошибки**

* Отправка кириллицы в поле Владелец на странице оформления кредита - **отображение ошибки**

* Отправка цифр в поле Владелец на странице оплаты - **отображение ошибки**

* Отправка цифр в поле Владелец на странице оформления кредита - **отображение ошибки**

* Отправка спецсимволов в поле Владелец на странице оплаты - **отображение ошибки**

* Отправка спецсимволов в поле Владелец на странице оформления кредита - **отображение ошибки**

* Отправка латиницы в поле CVC/CVV на странице оплаты - **отображение ошибки**

* Отправка латиницы в поле CVC/CVV на странице оформления кредита - **отображение ошибки**

* Отправка кириллицы в поле CVC/CVV на странице оплаты - **отображение ошибки**

* Отправка кириллицы в поле CVC/CVV на странице оформления кредита - **отображение ошибки**

* Отправка спецсимволов в поле CVC/CVV на странице оплаты - **отображение ошибки**

* Отправка спецсимволов в поле CVC/CVV на странице оформления кредита - **отображение ошибки**

* Отправка формы со страницы оплаты - **номер карты не сохранен в БД**

* Отправка формы со страницы оформления кредита - **номер карты не сохранен в БД**

* Отправка платежа с действующей карты со страницы оплаты - **в БД платеж сохранен как одобренный**

* Отправка платежа с действующей карты со страницы оформления кредита- **в БД платеж сохранен как одобренный**

* Отправка платежа с недействительной карты со страницы оплаты - **в БД платеж сохранен как отклоненный**

* Отправка платежа с недействительной карты со страницы оформления кредита - **в БД платеж сохранен как отклоненный**


### 2.	Используемые инструменты
* **GIT** – система контроля версий
* **Java 11** – используемый язык программирования
* **IntelliJ IDEA** – среда разработки
* **Gradle** – система автоматической сборки, используемая в рамках курса
* **Lombok** – использование аннотаций для оптимизации строк кода
* **Junit5** – тестовый фреймфорк
* **Google Chrome** – поиск в консоли разработчика селекторов для написания тестов, изучение UI
* **Selinide** – написание тестов с UI
* **MySQL** – СУБД, используемая приложением
* **PostgreSQL** – СУБД, используемая приложением
* **DBeaver** – работа с СУБД
* **Docker** – развертывание симулятора банковских сервисов, используемых сервисом
* **Allure** – генерация отчетности в удобочитаемом графическом формате

### 3.	Возможные риски при автоматизации
* Отсутствие ТЗ для SUT, может уйти больше времени на изучение поведения сервиса и написание тестов на основе личного опыта использования подобных сервисов.
* Поиск селекторов может быть осложнен отсутствием Id у элементов дерева, что может привести к увеличению затраченного времени на написание авто-тестов.
* Развертывание SUT может быть осложнено интеграцией сразу двух СУБД и симулятора банковских сервисов.

### 4.	Интервальная оценка затраченного времени с учетом рисков (в часах)
* **5** - планирование автоматизации
* **20-24** - развертывание SUT
* **48-54** - написание авто-тестов
* **8** - создание отечности, заведение выявленных багов
* **8** - возможные доработки

### 5.	План сдачи работ
**15.10.2021** – сдача авто-тестов  
**17.10.2021** - сдача отчетности по итогам автоматизированного тестирования









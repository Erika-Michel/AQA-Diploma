# Проект автоматизации тестирования [сервиса] по покупке туров aqa-shop.jar
[сервиса]: https://github.com/netology-code/qa-diploma

На локальной машине должен быть предварительно установлен Docker и скачаны образы MySQL и Postgres.

По умолчанию проект настроен на подключение к MySQL, для подключения к Postgres необходимо в файле application.properties закомментировать строку 3 при помощи # и активировать строку 4, удалив #

## Процедура запуска авто-тестов:
* склонировать репозиторий командой git clone
* запустить базы данных и мок банковского сервиса командой docker-compose up -d
* запустить SUT командой java -jar artifacts/aqa-shop.jar
* запустить тесты командой gradlew clean test
* по окончании работы оставить работу контейнеров Docker командой docker-compose down

## Просмотр отчетов
* просмотреть отчеты Allure командой gradlew allureServe
* сохранить отчеты Allure командой gradlew allureReport (файлы сохраняются в ./build/reports/allure-report)
* отчеты Gradle сохраняются в ./build/reports/tests/test 








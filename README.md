# job4j_accidents
Приложение "Автонарушители".

## Описание проекта
- В системе существуют две роли. Обычные пользователи и автоинспекторы.
- Пользователь имеет возможность добавлять описание автонарушения.
- В заявлении указывает: адрес, номер машины, описание нарушения и фотографию нарушения.
- У заявки есть статус. Принята. Отклонена. Завершена.

## Стек технологий
- Java 17
- Hibernate 5.5.3
- PostgreSQL 14
- Maven 3.8
- Spring boot 2.7
- Bootstrap 4.4

## Требования к окружению
- JDK 17
- Maven
- PostgreSQL

## Запуск проекта
- ```git clone git@github.com/PesterevViacheslav/job4j_accidents.git```
- Postgres. ```create database accidents;```
- Прописать креды в ```src/main/resources/db.properties```
- ```mvn install```
- перейти по [http://localhost:8080/accidents]

## Взаимодействие с приложением
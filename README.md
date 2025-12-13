# Smart Home Tech

## Languages and tools: 
<p style="display: flex; gap: 10px; flex-wrap: wrap;">
  <img width="100" height="100" alt="Java" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/java/java-original-wordmark.svg" />
  <img width="100" height="100" alt="Spring" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/spring/spring-original-wordmark.svg" />
  <img width="100" height="100" alt="IntelliJ" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/intellij/intellij-original.svg" />
  <img width="100" height="100" alt="PostgreSQL" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/postgresql/postgresql-original-wordmark.svg" />
  <img width="100" height="100" alt="Kafka" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/apachekafka/apachekafka-original.svg" />
  <img width="100" height="100" alt="Maven" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/maven/maven-original.svg" />
  <img width="100" height="100" alt="gRPC" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/grpc/grpc-plain.svg" />
  <img width="100" height="100" alt="Docker" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/docker/docker-plain.svg" />
  <img width="100" height="100" alt="Docker" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/postman/postman-original.svg" />
  <img width="100" height="100" alt="Docker" src="https://raw.githubusercontent.com/devicons/devicon/54cfe13ac10eaa1ef817a343ab0a9437eb3c2e08/icons/swagger/swagger-original.svg" />
</p>

# Описание проекта
Платформа для сбора, обработки и анализа телеметрии от датчиков умного дома. Система автоматически анализирует данные с датчиков и запускает соответствующие сценарии умного дома.

# Модуль telemetry

## Архитектура системы
Система состоит из следующих микросервисов:

## • Hub Router
Приём данных от пользовательских хабов и их отправка в сервис `Collector`.

## • Collector
Принимает данные от `Hub Router`, конвертирует данные в формат `Apache Avro` и сохраняет данные в `Apache Kafka`. 

## • Aggregator
Считывает данные из `Apache Kafka`, создает "снимок" датчиков в доме и сохраняет их в другой топик в `Apache Kafka`. 

## • Analyzer
Считывает аггрегированные данные, проверяет условия сценариев умного дома. При выполнении условий отправляет команды в `Hub Router` и запускаются соответствующие сценарии. 

# Модуль commerce ( интернет магазин )
Данный модуль представляет собой реализацию онлайн магазина умных устройств. Он состоит из следующих сервисов:

## • Shopping store
Данный сервис представляет собой витрину товаров, из которых пользователи выбирают товары для заказа.

## • Shopping cart 
Сервис `shopping cart` отвечает за работу с корзиной пользователей. Пользователи будут добавлять товары в корзину, чтобы сделать заказ.

## • Order
Сервис `order` работает с заказами пользователей, оформляет их.

## • Warehouse
Данный сервис представляет собой склад. 

## • Payment
В этом сервисе сосредоточена логика, связанная с оплатой заказов.

## • Delivery
Данный сервис ответственен за доставку готовых заказов пользователям.

# Модуль infa 
Данный модуль содержит всю инфраструктуру приложения, включая входной шлюз (`Gateway`), конфигурационные файлы (`Config`) и реестр сервисов (`Eureka`).   

## • Подмодуль discovery-server
Данный подмодуль является реестром сервисов и, соответственно, реализует паттерн `Service Discovery`. Другие модули регистрируются в него и могут вызывать друг друга по имени приложения без ручной настройки сетевых адресов. 

## • Подмодуль config-server
В данном подмодуле содержатся все конфигурационные файлы всех модулей приложения, он является сервером `Spring-Cloud-Config` и реализует паттерн `Externalized Configuration`.  


## • Подмодуль gateway-server
Подмодуль `gateway-server` является входным шлюзом для всех входящих запросов, оттуда они адресуются в соответствующие контроллеры через роуты. Данный модуль реализует паттерн `API Gateway`. 

• Документация API: 
[collector.json](json/collector.json),
[shopping-cart.json](json/shopping-cart.json), [shopping-store.json](json/shopping-store.json), [warehouse.json](json/warehouse.json)

• API Gateway: http://localhost:8080


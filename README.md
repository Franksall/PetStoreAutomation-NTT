# 🐾 PetStoreAutomation — API Testing con Serenity BDD

![Java](https://img.shields.io/badge/Java-17-orange)
![Maven](https://img.shields.io/badge/Maven-Build-blue)
![Serenity BDD](https://img.shields.io/badge/Serenity_BDD-3.1.20-green)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-brightgreen)
![Screenplay](https://img.shields.io/badge/Pattern-Screenplay-purple)
![Testing](https://img.shields.io/badge/Testing-API_Automation-informational)

## 📖 Descripción del proyecto

**PetStoreAutomation** es un proyecto de automatización de pruebas para servicios REST, desarrollado utilizando **Java, Maven, Serenity BDD, Cucumber y el patrón Screenplay**.

El objetivo es verificar el comportamiento de diferentes endpoints de la API pública **Swagger PetStore**, mediante escenarios de prueba positivos y negativos.

Se automatizan operaciones de consulta, creación y actualización de mascotas, así como el registro y consulta de pedidos.

El proyecto implementa buenas prácticas de organización, reutilización de componentes, validaciones automáticas y generación de reportes de ejecución.

## 🎯 Objetivos

**Objetivo general**

Implementar un framework de automatización de pruebas API utilizando Serenity BDD y el patrón Screenplay para validar el comportamiento de los servicios REST de Swagger PetStore.

**Objetivos específicos**

- Automatizar solicitudes HTTP GET, POST y PUT.
- Validar códigos de respuesta HTTP y datos JSON.
- Implementar escenarios positivos y negativos mediante Cucumber.
- Utilizar `Scenario Outline` y `Examples` para ejecutar pruebas con diferentes datos.
- Organizar el código mediante el patrón Screenplay.
- Generar reportes HTML con Serenity BDD.
- Ejecutar las pruebas desde Maven e IntelliJ IDEA.

## 🛠️ Tecnologías utilizadas

| Tecnología | Descripción |
|---|---|
| Java 17 | Lenguaje de programación |
| Maven | Gestión de dependencias y ejecución |
| Serenity BDD 3.1.20 | Framework de automatización y reportes |
| Cucumber | Definición de escenarios BDD |
| Gherkin | Lenguaje Given, When, Then |
| Screenplay | Patrón de diseño para organizar las pruebas |
| Rest Assured | Interacción y validación de servicios REST |
| JUnit 4 | Ejecución y aserciones |
| IntelliJ IDEA | Entorno de desarrollo |
| Swagger PetStore | API pública utilizada para las pruebas |

## 🌐 API utilizada

**Swagger PetStore**

- Documentación: https://petstore.swagger.io/
- URL base: `https://petstore.swagger.io/v2`

La API permite trabajar con recursos de mascotas, pedidos y usuarios.

En este proyecto se automatizan los recursos **Pet** y **Store**.

### Endpoints automatizados

| Método | Endpoint | Funcionalidad |
|---|---|---|
| GET | `/pet/{petId}` | Consultar una mascota |
| POST | `/pet` | Registrar una mascota |
| PUT | `/pet` | Actualizar una mascota |
| POST | `/store/order` | Registrar un pedido |
| GET | `/store/order/{orderId}` | Consultar un pedido |

## 🧪 Casos de prueba

El proyecto contiene **7 escenarios**, con **10 ejecuciones** gracias al uso de pruebas parametrizadas.

| Código | Escenario | Tipo | Resultado esperado |
|---|---|---|---|
| CP01 | Consultar mascota inexistente | Negativo | HTTP 404 |
| CP02 | Registrar mascotas con diferentes datos | Positivo | HTTP 200 |
| CP03 | Consultar mascota registrada | Positivo | HTTP 200 y datos correctos |
| CP04 | Actualizar datos de una mascota | Positivo | HTTP 200 y datos actualizados |
| CP05 | Registrar un pedido | Positivo | HTTP 200 |
| CP06 | Consultar pedido registrado | Positivo | HTTP 200 y datos correctos |
| CP07 | Consultar pedido inexistente | Negativo | HTTP 404 |

### Pruebas parametrizadas

Se utiliza `Scenario Outline` para ejecutar un mismo escenario con distintos conjuntos de datos.

Ejemplo de creación de mascotas:

```gherkin
@CP02_CrearMascota @Positivo
Scenario Outline: Registrar mascotas exitosamente
  Given el actor establece el endpoint de PetStore
  When el actor registra una mascota llamada "<nombre>" con estado "<estado>"
  Then el codigo de respuesta debe ser 200
  And el nombre de la mascota creada debe ser "<nombre>"

  Examples:
    | nombre | estado    |
    | Max    | available |
    | Luna   | pending   |
    | Rocky  | sold      |
```

De esta manera se reutilizan los mismos Step Definitions y Tasks sin duplicar código Java.

## 🏗️ Arquitectura del proyecto

La automatización utiliza el patrón **Screenplay**, que organiza las responsabilidades en componentes reutilizables.

### Estructura principal

```text
PetStoreAutomation/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/example/
│   │           ├── questions/
│   │           │   └── ResponseCode.java
│   │           └── tasks/
│   │               ├── CreatePet.java
│   │               ├── GetPet.java
│   │               ├── UpdatePet.java
│   │               └── store/
│   │                   ├── CreateOrder.java
│   │                   └── GetOrder.java
│   │
│   └── test/
│       ├── java/
│       │   └── org/example/
│       │       ├── stepdefinitions/
│       │       │   ├── ParameterDefinition.java
│       │       │   ├── PetStepDefinition.java
│       │       │   └── OrderStepDefinition.java
│       │       └── RunnerTest.java
│       │
│       └── resources/
│           └── features/
│               └── Pet.feature
│
├── .gitignore
├── pom.xml
└── README.md
```

### Componentes del patrón Screenplay

**Actor**

Representa a quien realiza las acciones de automatización. El actor tiene la capacidad de interactuar con la API mediante `CallAnApi`.

**Tasks**

Representan las acciones que ejecuta el actor. En este proyecto se utilizan para crear, consultar y actualizar mascotas, así como registrar y consultar pedidos.

**Questions**

Permiten obtener información para realizar las validaciones. Por ejemplo, `ResponseCode.java` consulta el código HTTP de la última respuesta.

**Step Definitions**

Relacionan los pasos escritos en Gherkin con las acciones implementadas en Java.

**RunnerTest**

Configura la ejecución de los escenarios de Cucumber mediante Serenity BDD.

**Features**

Contienen los escenarios de prueba escritos en lenguaje Gherkin.

## ⚙️ Requisitos previos

Para ejecutar el proyecto se necesita:

- Java JDK 17.
- Maven, instalado o disponible mediante el entorno de desarrollo.
- IntelliJ IDEA u otro IDE compatible con Java.
- Conexión a Internet para acceder a Swagger PetStore.

## 🚀 Instalación y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/Franksall/PetStoreAutomation-NTT.git
```

### 2. Ingresar al proyecto

```bash
cd PetStoreAutomation-NTT
```

### 3. Ejecutar las pruebas

Si Maven está instalado y configurado en el PATH:

```bash
mvn clean verify
```

También se puede utilizar el panel Maven de IntelliJ IDEA y ejecutar el objetivo:

```text
clean verify
```

Este comando compila el proyecto, ejecuta las pruebas configuradas y genera los reportes de Serenity.

### 4. Ejecutar mediante IntelliJ IDEA

También es posible ejecutar `RunnerTest.java` directamente desde el IDE, utilizando la opción **Run**.

Para regenerar el reporte HTML completo, se recomienda ejecutar `clean verify`.

## 📊 Reportes Serenity BDD

Serenity BDD permite consultar información de las pruebas ejecutadas, incluyendo:

- Escenarios ejecutados.
- Resultados exitosos y fallidos.
- Pasos Given, When y Then.
- Detalles de las validaciones.
- Información de la ejecución.

Después de ejecutar Maven, abrir:

```text
target/site/serenity/index.html
```

El archivo `index.html` puede visualizarse desde un navegador web.

### Resultado de ejecución verificado

En la ejecución de referencia se obtuvieron los siguientes resultados:

| Indicador | Resultado |
|---|---|
| Escenarios definidos | 7 |
| Pruebas ejecutadas | 10 |
| Pruebas exitosas | 10 |
| Pruebas fallidas | 0 |
| Errores | 0 |
| Resultado Maven | BUILD SUCCESS |

**Resultado:** las diez ejecuciones finalizaron correctamente en la ejecución registrada.

## ✅ Validaciones implementadas

Las pruebas comprueban tanto el código HTTP como determinados datos de las respuestas JSON.

Entre las validaciones implementadas se encuentran:

- Códigos HTTP `200` y `404`.
- Nombre de la mascota registrada.
- Estado de la mascota.
- Identificador de la mascota consultada.
- Persistencia de los datos después de una actualización.
- Identificador del pedido.
- Estado del pedido registrado o consultado.

Los escenarios positivos comprueban el funcionamiento esperado de las operaciones.

Los escenarios negativos verifican la respuesta del servicio ante consultas de recursos inexistentes.

Un escenario negativo puede considerarse exitoso cuando la API devuelve el error HTTP esperado, como `404 Not Found`.

## 🔄 Flujo general de automatización

```text
Pet.feature
    |
    v
RunnerTest
    |
    v
Step Definitions
    |
    v
Actor (Screenplay)
    |
    v
Tasks
    |
    v
Swagger PetStore API
    |
    v
Respuesta HTTP / JSON# 🐾 PetStoreAutomation — API Testing con Serenity BDD

📌 Consideraciones

Swagger PetStore es una API pública de demostración. Sus datos pueden ser modificados por otros usuarios y algunas respuestas pueden variar entre ejecuciones.

Para reducir conflictos, las pruebas de creación de mascotas utilizan identificadores generados durante la ejecución.

Las pruebas que consultan o actualizan recursos creados anteriormente reutilizan sus identificadores mediante la memoria del actor de Screenplay.

Los escenarios negativos con identificadores fijos dependen del estado del servicio público.

📚 Conclusiones

El proyecto permite automatizar pruebas de servicios REST utilizando herramientas habituales del ecosistema Java.

La implementación del patrón Screenplay facilita la separación de responsabilidades y la reutilización de acciones.

El uso de Cucumber y Gherkin permite describir los escenarios de prueba de manera comprensible, mientras que Serenity BDD proporciona reportes para analizar los resultados.

Asimismo, el uso de Scenario Outline y Examples permite ampliar la cobertura de datos sin duplicar la lógica de automatización.

Proyecto: PetStoreAutomation — NTT 

Repositorio: https://github.com/Franksall/PetStoreAutomation-NTT
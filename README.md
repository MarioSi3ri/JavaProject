# 🛒 Sistema de Gestión de Productos

[![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-Build%20Tool-02303A?logo=gradle&logoColor=white)](https://gradle.org/)
[![SQLite](https://img.shields.io/badge/SQLite-Development-003B57?logo=sqlite&logoColor=white)](https://www.sqlite.org/)
[![CI](https://github.com/MarioSi3ri/JavaProject/actions/workflows/continuous-integration.yml/badge.svg)](https://github.com/MarioSi3ri/JavaProject/actions/workflows/continuous-integration.yml)

API REST desarrollada en Java para administrar productos, pedidos y la relación entre ambos. El proyecto pertenece al **Equipo 22** para el proyecto final del programa "**Diplomado en Full Stack Java**" del Bootcamp impartido por **BEDU** y **Santander Academy**. Está construido con Spring Boot, JPA/Hibernate y Gradle, incorporando validaciones, documentación OpenAPI y pruebas automatizadas.

> Proyecto académico: **Sistema de Gestión de Productos**.

---

## 📋 Tabla de contenidos

- [Descripción](#-descripción)
- [Funcionalidades](#-funcionalidades)
- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Arquitectura del proyecto](#-arquitectura-del-proyecto)
- [Requisitos previos](#-requisitos-previos)
- [Instalación](#-instalación)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [Uso de la API](#-uso-de-la-api)
- [Pruebas y calidad](#-pruebas-y-calidad)
- [Integración continua](#-integración-continua)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Contribución](#-contribución)
- [Autores](#-autores)

---

## 📖 Descripción

El sistema proporciona una API REST para gestionar el catálogo de productos y el ciclo de vida de los pedidos. Permite crear, consultar, actualizar y eliminar productos, crear pedidos, añadir o quitar productos de un pedido y finalizar la compra generando el resumen correspondiente.

La aplicación sigue una organización por capas para separar responsabilidades:

- **Controladores:** exponen los endpoints HTTP.
- **Servicios:** contienen la lógica de negocio.
- **Repositorios:** gestionan el acceso a datos mediante Spring Data JPA.
- **Modelos:** representan las entidades persistidas.
- **DTOs y mappers:** separan el modelo interno de los datos expuestos por la API.
- **Excepciones:** centralizan los errores de dominio, como productos o pedidos inexistentes.

---

## ✨ Funcionalidades

### Gestión de productos

- Crear nuevos productos.
- Consultar todos los productos.
- Consultar un producto por su identificador.
- Filtrar productos por tipo.
- Actualizar productos existentes.
- Eliminar productos.
- Validar los datos recibidos en las peticiones.

### Gestión de pedidos

- Crear un pedido nuevo.
- Consultar todos los pedidos.
- Consultar un pedido por su identificador.
- Añadir productos a un pedido.
- Quitar productos de un pedido.
- Consultar los productos asociados a un pedido.
- Finalizar un pedido y obtener el resumen o ticket de compra.

### Características técnicas

- API REST basada en Spring Web.
- Persistencia mediante Spring Data JPA e Hibernate.
- Base de datos SQLite para desarrollo y pruebas locales.
- Compatibilidad preparada para MySQL mediante configuración alternativa.
- Documentación interactiva con OpenAPI/Swagger UI.
- Pruebas con JUnit, Mockito, Spring Boot Test y H2.
- Reporte de cobertura mediante JaCoCo.
- Análisis de calidad preparado para SonarQube.
- Integración continua con GitHub Actions.

---

## 🧰 Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| **Java 17** | Lenguaje principal del proyecto. |
| **Spring Boot 3.2.0** | Framework para la creación y ejecución de la aplicación. |
| **Spring Web** | Desarrollo de controladores y API REST. |
| **Spring Data JPA** | Persistencia y acceso a datos. |
| **Hibernate** | Implementación ORM y dialecto para SQLite. |
| **SQLite** | Base de datos utilizada por defecto en el entorno local. |
| **MySQL Connector/J** | Conector disponible para despliegues con MySQL. |
| **Gradle** | Gestión de dependencias, compilación y tareas del proyecto. |
| **Lombok** | Reducción de código repetitivo en modelos y componentes. |
| **MapStruct** | Conversión entre entidades y DTOs. |
| **Bean Validation** | Validación de los datos de entrada. |
| **Springdoc OpenAPI** | Documentación y exploración de la API. |
| **JUnit 5 y Mockito** | Pruebas unitarias y de integración. |
| **H2** | Base de datos en memoria para pruebas. |
| **JaCoCo** | Generación de métricas de cobertura. |
| **SonarQube** | Análisis estático y control de calidad. |
| **GitHub Actions** | Automatización de compilación e integración continua. |

---

## 🏗️ Arquitectura del proyecto

La aplicación utiliza una arquitectura por capas basada en Spring Boot:

```text
Cliente HTTP
    ↓
Controller
    ↓
Service
    ↓
Repository
    ↓
Base de datos
```

Las principales entidades del dominio son:

- `Producto`: código, nombre, tipo y precio.
- `Pedido`: representa una orden de compra.
- `ProductosPedido`: entidad que relaciona productos con pedidos.
- `TipoProducto`: enumeración para clasificar los productos.

---

## ✅ Requisitos previos

Antes de instalar el proyecto, asegúrate de tener instalado:

- **JDK 17** o una versión compatible.
- **Git**.
- Una terminal o IDE compatible con proyectos Gradle, como IntelliJ IDEA, Eclipse o Visual Studio Code.
- Opcionalmente, **MySQL** si se desea utilizar la configuración de despliegue preparada para esa base de datos.

El proyecto incluye Gradle Wrapper, por lo que no es necesario instalar Gradle globalmente.

Comprueba las versiones instaladas:

```bash
java -version
git --version
```

---

## 📥 Instalación

1. Clona el repositorio:

   ```bash
   git clone https://github.com/MarioSi3ri/JavaProject.git
   ```

2. Accede al directorio del proyecto:

   ```bash
   cd JavaProject
   ```

3. Da permisos de ejecución al wrapper en Linux o macOS:

   ```bash
   chmod +x gradlew
   ```

4. Compila el proyecto y ejecuta las pruebas:

   **Linux/macOS:**

   ```bash
   ./gradlew build
   ```

   **Windows:**

   ```powershell
   .\gradlew.bat build
   ```

---

## ⚙️ Configuración

Por defecto, la aplicación está configurada para utilizar SQLite mediante el archivo `src/main/resources/application.properties`:

- Driver: `org.sqlite.JDBC`.
- Dialecto: `SQLiteDialect`.
- Base de datos: `src/main/resources/sqlite.db`.
- Creación/actualización del esquema: `update`.

La configuración de MySQL se encuentra preparada como referencia dentro del mismo archivo. Para utilizarla, define las variables de entorno correspondientes y adapta las propiedades de conexión:

```properties
spring.datasource.url=jdbc:mysql://${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

> Recomendación: no guardes credenciales reales directamente en el repositorio. Utiliza variables de entorno o un archivo local ignorado por Git.

---

## ▶️ Ejecución

### Ejecutar en modo desarrollo

**Linux/macOS:**

```bash
./gradlew bootRun
```

**Windows:**

```powershell
.\gradlew.bat bootRun
```

La aplicación se iniciará normalmente en:

```text
http://localhost:8080
```

### Ejecutar el archivo JAR

Genera el artefacto compilado:

```bash
./gradlew bootJar
```

Después, ejecútalo con:

```bash
java -jar build/libs/*.jar
```

---

## 🔌 Uso de la API

La API utiliza las siguientes rutas principales:

### Productos

| Método | Endpoint | Descripción |
|---|---|---|
| `GET` | `/productos` | Lista todos los productos. |
| `GET` | `/productos/{id}` | Obtiene un producto por ID. |
| `GET` | `/productos?tipo={tipo}` | Filtra productos por tipo. |
| `POST` | `/productos` | Crea un producto. |
| `PUT` | `/productos/{id}` | Actualiza un producto. |
| `DELETE` | `/productos/{id}` | Elimina un producto. |

Ejemplo de creación de producto:

```bash
curl -X POST http://localhost:8080/productos \
  -H "Content-Type: application/json" \
  -d '{
    "codigo": 1001,
    "nombre": "Teclado mecánico",
    "tipo": "ELECTRONICA",
    "precio": 59.99
  }'
```

> Los valores válidos de `tipo` dependen de los elementos definidos en el enum `TipoProducto` del proyecto.

### Pedidos

| Método | Endpoint | Descripción |
|---|---|---|
| `POST` | `/pedidos` | Crea un pedido nuevo. |
| `GET` | `/pedidos` | Lista todos los pedidos. |
| `GET` | `/pedidos/{id}` | Obtiene un pedido por ID. |
| `POST` | `/pedidos/productos` | Añade un producto a un pedido. |
| `DELETE` | `/pedidos/productos/{id}` | Elimina un producto asociado a un pedido. |
| `GET` | `/pedidos/{id}/productos` | Lista los productos de un pedido. |
| `PUT` | `/pedidos/finalizar` | Finaliza el pedido activo y devuelve su resumen. |

Ejemplo de consulta de productos de un pedido:

```bash
curl http://localhost:8080/pedidos/1/productos
```

### Documentación OpenAPI

Springdoc expone una interfaz interactiva para explorar y probar los endpoints. Con la aplicación en ejecución, consulta:

```text
http://localhost:8080/swagger-ui/index.html
```

También puedes consultar el documento OpenAPI en formato JSON:

```text
http://localhost:8080/v3/api-docs
```

---

## 🧪 Pruebas y calidad

Ejecutar todas las pruebas:

```bash
./gradlew test
```

El proyecto configura JaCoCo para generar el reporte de cobertura después de las pruebas:

```bash
./gradlew test jacocoTestReport
```

El reporte HTML se genera habitualmente en:

```text
build/reports/jacoco/test/html/index.html
```

Para ejecutar el análisis configurado de SonarQube —requiere una instancia y credenciales correctamente configuradas— utiliza:

```bash
./gradlew sonar
```

Imagen representativa de las pruebas aplicadas y el resultado:
<p align="center">
<img src="https://github.com/user-attachments/assets/e27f2c3f-5c45-4699-a383-14f252ef8b43" alt="Banner del Perfil" />
  </p>

---

## 🔄 Integración continua

El repositorio incluye el workflow `continuous-integration.yml`, que se ejecuta en GitHub Actions cuando se realizan cambios en el repositorio. Actualmente, el flujo automatiza:

1. Descarga del código fuente.
2. Configuración de Java 17 mediante Temurin.
3. Configuración de Gradle.
4. Compilación del proyecto con `gradle build`.

Esto permite detectar errores de compilación y problemas básicos de integración antes de incorporar cambios al proyecto.

---

## 📁 Estructura del proyecto

```text
JavaProject/
├── .github/
│   └── workflows/
│       └── continuous-integration.yml
├── gradle/
├── src/
│   ├── main/
│   │   ├── java/org/backend/proyecto/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── mapper/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── BackendApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/org/backend/proyecto/
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── README.md
```

---

## 🤝 Contribución

Las contribuciones son bienvenidas. Para proponer cambios:

1. Crea un fork del repositorio.
2. Crea una rama descriptiva:

   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```

3. Implementa los cambios y añade o actualiza las pruebas correspondientes.
4. Verifica que la compilación y las pruebas pasen:

   ```bash
   ./gradlew build
   ```

5. Realiza un commit claro y descriptivo.
6. Abre un Pull Request explicando los cambios realizados.

---

## 👥 Autores

Proyecto desarrollado por el **Equipo 22: [diefpt](https://github.com/diefpt), [Edsonortega](https://github.com/Edsonortega) & [AlxMeza](https://github.com/AlxMeza)**.

Repositorio: [MarioSi3ri/JavaProject](https://github.com/MarioSi3ri/JavaProject)

---

## 📄 Licencia

Este proyecto se distribuye bajo la **Licencia MIT**. Consulta el archivo [LICENSE](LICENSE) para conocer los términos completos.

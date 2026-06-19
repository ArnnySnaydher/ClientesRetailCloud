# TechRetail - Microservicio de Clientes

Este microservicio ha sido desarrollado en Java con Spring Boot utilizando una Arquitectura Hexagonal y principios SOLID para gestionar la información de los clientes.

---

## Requisitos Previos

- Java 17 o superior
- Maven 3.8+ (o el wrapper `./mvnw` incluido)
- Docker y Docker Compose (opcional)

---

## Cómo Iniciar el Proyecto

### 1. Ejecución Local (Maven)

Para compilar y ejecutar la aplicación localmente en el puerto `8080`:

```bash
# En Windows (CMD / PowerShell)
mvnw.cmd spring-boot:run

# En Linux / macOS
./mvnw spring-boot:run
```

### 2. Ejecución con Docker Compose

Para levantar el microservicio y sus contenedores relacionados:

```bash
docker-compose up --build
```

La aplicación estará disponible en `http://localhost:8080`.

---

## Endpoints de la API

### 1. Crear Cliente
- **Método**: `POST`
- **Ruta**: `/clientes`
- **Cuerpo de la Petición (JSON)**:
```json
{
  "nombre": "Arnny",
  "apellido": "Quispe",
  "edad": 27,
  "fechaNacimiento": "1998-12-18"
}
```

### 2. Obtener KPIs de Clientes
- **Método**: `GET`
- **Ruta**: `/kpideclientes`
- **Descripción**: Retorna el promedio de edad y la desviación estándar de la población de todos los clientes.

### 3. Listar Clientes (Con fecha probable de fallecimiento)
- **Método**: `GET`
- **Ruta**: `/listclientes`
- **Descripción**: Obtiene la lista completa de clientes con la fecha probable de su fallecimiento calculada en base a la expectativa de vida (80 años).

### Documentación Interactiva (Swagger / OpenAPI)
- **Local**: http://localhost:8080/swagger-ui/index.html
- **Producción (GKE)**: http://35.254.117.143/swagger-ui/index.html

---

## Pruebas Unitarias y Cobertura (JaCoCo)

Para ejecutar el set de pruebas unitarias e integración y generar el reporte de cobertura:

```bash
./mvnw clean test
```

El reporte HTML detallado de cobertura se generará en:
`target/site/jacoco/index.html`

---

## Integración con SonarQube

Para enviar los resultados de las pruebas y el reporte de cobertura de JaCoCo a un servidor local de SonarQube:

1. Asegúrate de tener levantada una instancia de SonarQube (por ejemplo en `http://localhost:9000`).
2. Ejecuta el análisis ejecutando el siguiente comando con tu token de autenticación:

```bash
./mvnw clean verify sonar:sonar \
  -Dsonar.projectKey=techretail-service \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=TU_TOKEN_DE_SONARQUBE
```

---

## Colección de Postman

En la raíz del proyecto se incluye el archivo:
`techretail_clients.postman_collection.json`

Para usarlo:
1. Abre Postman.
2. Haz clic en **Import**.
3. Selecciona el archivo JSON mencionado.
4. Ajusta la variable de entorno `BASE_URL` en Postman si deseas apuntar a producción en lugar de local (`http://localhost:8080`).

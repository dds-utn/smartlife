# SmartLife (DDSI — UTN BA)

Implementación del caso práctico **SmartLife**. El alcance y las reglas de negocio del dominio siguen el enunciado oficial:

[**[DDSI UTN BA] SmartLife — Caso práctico** (Google Docs)](https://docs.google.com/document/d/1N7W2UuWuqmDRuR1pTH5QtjBoojgJwH9_ujmGvih9fmA/edit?tab=t.0#heading=h.9alaamq85m85)

El código de este repositorio está pensado para **respetar ese enunciado** (entidades, flujos y responsabilidades que allí se definen).

---

## Requisitos previos

- JDK 21
- Maven 3.9+
- Docker (opcional, solo para construir y ejecutar contenedores)

---

## Estructura del repositorio

```
smartlife/
├── pom.xml                 # POM padre: versiones y dependencyManagement
├── common-lib/             # Librería compartida (JAR), disponible en el reactor
├── sales-service/          # Servicio de ventas — puerto 8082 (ver application.yaml)
└── trends-service/         # Servicio de tendencias de consumo — puerto 8083 (ver application.yaml)
```

`common-lib` forma parte del reactor Maven; los builds Docker usan la raíz como contexto para resolver el POM padre y los módulos (por ejemplo `-pl sales-service -am` o `-pl trends-service -am`).

---

## Tecnologías

| Tecnología          | Versión       |
|---------------------|---------------|
| Java                | 21            |
| Spring Boot         | 4.0.5         |
| Spring Cloud BOM    | 2025.1.1      |
| Lombok              | 1.18.42       |
| Maven               | 3.9+          |

El BOM de Spring Cloud está declarado en el POM padre para que los módulos puedan incorporar dependencias de Spring Cloud sin fijar versión en cada uno.

---

## Desarrollo local (Maven)

Todos los comandos se ejecutan desde la **raíz del proyecto**.

### Compilar todos los módulos

```bash
mvn clean install
```

Esto construye `common-lib`, `sales-service`, `trends-service` y el resto del reactor según el orden definido en el POM padre.

### Ejecutar el servicio de ventas

```bash
mvn spring-boot:run -pl sales-service
```

El puerto por defecto está definido en `sales-service/src/main/resources/application.yaml` (8082).

### Ejecutar el servicio de tendencias

```bash
mvn spring-boot:run -pl trends-service
```

El puerto por defecto está definido en `trends-service/src/main/resources/application.yaml` (8083).

---

## Construcción de imágenes Docker

El proyecto es multi-módulo Maven. **El contexto de construcción debe ser la raíz del repositorio**; si se limita a la carpeta del servicio, Maven no encontrará el POM padre ni el resto del reactor.

### Construcción manual (CLI)

Desde la raíz del proyecto, usando el Dockerfile del servicio con `-f` y contexto `.`:

```bash
docker build -t sales-service-img -f sales-service/Dockerfile .
```

Para `trends-service`:

```bash
docker build -t trends-service-img -f trends-service/Dockerfile .
```

### Ejecutar el contenedor

Ajustá el mapeo de puertos al que exponga la aplicación dentro del contenedor (en `application.yaml` está **8082** para ventas y **8083** para tendencias):

```bash
docker run -p 8082:8082 sales-service-img
```

```bash
docker run -p 8083:8083 trends-service-img
```

### Nota sobre `ARG SERVICE_NAME`

El Dockerfile define `ARG SERVICE_NAME` (por defecto `sales-service` o `trends-service` según la carpeta). Solo hace falta sobreescribirlo si reutilizás el mismo patrón de build para otro módulo:

```bash
docker build --build-arg SERVICE_NAME=otro-service -f otro-service/Dockerfile .
```

---

## Estado del proyecto

`sales-service` concentra el dominio de ventas alineado al enunciado SmartLife (comercio, productos, ventas, impuestos, observadores, etc.). `trends-service` expone indicadores de tendencia de consumo (nivel, ícono y leyenda por producto). `common-lib` está preparada para código compartido entre servicios a medida que el trabajo práctico lo requiera.

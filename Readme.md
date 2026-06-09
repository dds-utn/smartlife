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

## Mensajería asincrónica (RabbitMQ)

`sales-service` publica el evento **`VentaRegistrada`** en el exchange topic `eventos` con routing key `venta.registrada`. `trends-service` consume desde la cola durable `trends.venta-registrada.queue` (bindeada a ese routing key) y actualiza el contador de ventas por producto. La cola tiene DLX configurada (`eventos.dlx` → `trends.venta-registrada.dlq`) y los listeners reintentan con backoff exponencial (3 intentos) antes de mandar a DLQ.

### Levantar RabbitMQ local (sin docker-compose)

Para desarrollo desde el IDE, basta con un contenedor:

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:3.13-management
```

- AMQP en `localhost:5672`.
- Management UI en `http://localhost:15672` (user/pass `guest`/`guest`).
- Para persistir datos entre reinicios agregar `-v rabbitmq-data:/var/lib/rabbitmq`.

Los `application.yaml` de ambos servicios apuntan por defecto a `localhost:5672`, así que con este contenedor levantado y los servicios corriendo con `mvn spring-boot:run` la integración queda funcionando.

### Levantar todo el ecosistema (docker-compose)

Desde la raíz:

```bash
docker compose up --build
```

Esto levanta `rabbitmq` + `sales-service` + `trends-service` en la misma red. Los servicios esperan al healthcheck de RabbitMQ antes de arrancar y se conectan vía el hostname `rabbitmq` (configurado por variable de entorno `SPRING_RABBITMQ_HOST`).

- sales-service: `http://localhost:8082`
- trends-service: `http://localhost:8083`
- RabbitMQ management: `http://localhost:15672`

Para apagar todo: `docker compose down`.

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

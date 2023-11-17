# TENPO CHALLENGE

## Contexto

Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:

Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:

El servicio externo puede ser un mock, tiene que devolver el % sumado.

Dado que ese % varía poco, podemos considerar que el valor que devuelve ese servicio no va cambiar por 30 minutos. Cache implementado

Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.

Si el servicio falla, se puede reintentar hasta 3 veces. Implementado

Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.

La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado. Rate limit implementado Testeado

El historial se debe almacenar en una database PostgreSQL.

Incluir errores http. Mensajes y descripciones para la serie 4XX. Implementado Handler Testeado


Se deben incluir tests unitarios.

Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose

Debes agregar un Postman Collection o Swagger para que probemos tu API

Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo.

Tener en cuenta que la aplicación funcionará de la forma de un sistema distribuido donde puede existir más de una réplica del servicio funcionando en paralelo. Cache Distribuido implementado

### Descripcion

#### Stack
    * Spring boot 3
    * Java 17
    * Gradle 8.4
#### Arquitectura
Se desarollo bajo arquitectura hexagonal con DDD
### Test
```bash
  ./gradlew test
```
### Instalacion

La imagen del servicio se encuentra en Docker-Hub
```bash
  docker pull gonzazago/tenpo:latest
```

Ejecutar el servicio desde docker-compose con el siguiente comando
```bash
  docker-compose up --build
```

Documentacion de la API
* http://localhost:8080/swagger-ui/index.html#/

### Mock Server
Se genero el siguiente mock server

    - https://a6530118-93ae-4994-9512-73735a03c189.mock.pstmn.io/percentage

De momento solo devuelve un valor
### Collection
Se agrega dentro de la carpeta collection la coleccion para ser ejecutada desde Postman





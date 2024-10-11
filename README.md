# Sistema de Gestión de Pedidos

---

Descripción

El **Sistema de Gestión de Pedidos** es una aplicación basada en microservicios diseñada para gestionar pedidos de clientes de manera eficiente y escalable. Utiliza tecnologías modernas como Java 17, Spring Boot, PostgreSQL y Docker para asegurar una alta disponibilidad, facilidad de mantenimiento y despliegue.

---

Tecnologías Utilizadas

- **Lenguaje de Programación**: Java 17
- **Framework**: Spring Boot
- **Base de Datos**: PostgreSQL
- **Containerización**: Docker
- **Orquestación**: Docker Compose
- **Testing**: JUnit, Mockito
- **Documentación API**: Swagger 
- **Monitoreo**: Spring Boot Actuator

---

Arquitectura

El sistema sigue una **Arquitectura de Microservicios**, lo que permite una separación clara de responsabilidades y facilita la escalabilidad y el mantenimiento. Cada microservicio se encarga de una funcionalidad específica y se comunica con otros servicios a través de APIs REST.

Diagrama de Arquitectura

```plaintext
+------------------+         
|                  |        
|  Cliente         +
|                  |         
+--------+---------+          
         |                              
         |                
+--------v---------+         
|                  |          
|  OrderService    |        
|                  |          
+------------------+      
         |
         |
+--------v---------+
|                  |
|  PostgreSQL DB   |
|                  |
+------------------+
```

---

Instalación y Configuración
Clonar el Repositorio
```
git clone ... sistema-gestion-pedidos.git
cd sistema-gestion-pedidos
```

Build del Proyecto
```
mvn clean package
```
Esto generará el archivo JAR en el directorio target.

Ejecutar con Docker Compose
Desde el directorio raíz del proyecto, ejecuta:
```
docker-compose up --build
```

Esto levantará los contenedores de PostgreSQL y del microservicio de pedidos.

---

Estructura del proyecto

```
.
├── OrderService
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com.example.orderservice
│   │   │   │       ├── controller
│   │   │   │       ├── model
│   │   │   │       ├── repository
│   │   │   │       ├── service
│   │   │   │       └── config
│   │   │   └── resources
│   │   │       ├── application.yml
│   │   │       └── schema.sq
│   │   └── test
│   │       └── java
│   │           └── com.example.orderservice
│   │               ├── controller
│   │               └── service
├── Dockerfile
├── pom.xml
├── docker-compose.yml
└── README.md
```

---

API REST
El microservicio de pedidos expone una API REST que permite gestionar los pedidos. A continuación se detallan los endpoints disponibles.

Endpoints
Crear un Nuevo Pedido

```
URL: /orders
Método: POST
Cuerpo de la Solicitud:
json
Copiar código
{
  "customerId": 1,
  "productId": 101,
  "status": "pendiente"
}
Respuesta:
json
Copiar código
{
  "id": 1,
  "customerId": 1,
  "productId": 101,
  "status": "pendiente"
}
```

Consultar un Pedido por ID

```
URL: /orders/{id}
Método: GET
Respuesta:
json
Copiar código
{
  "id": 1,
  "customerId": 1,
  "productId": 101,
  "status": "pendiente"
}

```

Listar Todos los Pedidos

```
URL: /orders
Método: GET
Respuesta:
json
Copiar código
[
  {
    "id": 1,
    "customerId": 1,
    "productId": 101,
    "status": "pendiente"
  },
  {
    "id": 2,
    "customerId": 2,
    "productId": 102,
    "status": "procesado"
  }
]
```


Actualizar el Estado de un Pedido

```
URL: /orders/{id}/status
Método: PUT
Parámetros de Consulta:
status: Nuevo estado del pedido (e.g., procesado, entregado)
Respuesta:
json
Copiar código
{
  "id": 1,
  "customerId": 1,
  "productId": 101,
  "status": "procesado"
}
```


Eliminar un Pedido

```
URL: /orders/{id}
Método: DELETE
Respuesta: 204 No Content
```

---

Swagger UI

Para una documentación interactiva de la API, se ha integrado Swagger UI. Accede a ella en:

```
http://localhost:8080/swagger-ui/
```

---

Testing

Tests Unitarios
Se han implementado tests unitarios utilizando JUnit 5 y Mockito para asegurar la calidad y fiabilidad del código.

Ejecutar Tests
Navega al directorio OrderService y ejecuta:

```
mvn test
```

Descripción de los Tests
OrderServiceTest: Prueba las funcionalidades del servicio de pedidos, incluyendo la creación, obtención, actualización y eliminación de pedidos.
OrderControllerTest: Prueba los endpoints del controlador de pedidos, verificando que respondan correctamente a las solicitudes HTTP.

---

Logging

Los logs se imprimen en la consola y pueden ser visualizados directamente en la terminal donde se ejecuta el contenedor Docker.

---

Monitoreo

Se ha integrado Spring Boot Actuator para monitorear la aplicación y obtener métricas sobre su rendimiento.

---

Dockerización

Cada microservicio y la base de datos están containerizados utilizando Docker, facilitando su despliegue y escalabilidad.

Ejecutar con Docker Compose
Para construir y levantar los contenedores, ejecuta:

```
docker-compose up --build
```


---

Sobre Lambda 

Para transformar el sistema de gestión de pedidos hecho en Spring Boot a AWS Lambda, hay que realizar algunos cambios, ya que AWS Lambda está basado en una arquitectura serverless y la manera en la que funciona es diferente de la arquitectura tradicional en la que se ejecuta una aplicación Spring Boot. 

Un enfoque sería dividir las funcionalidades del sistema de pedidos en funciones más pequeñas, siguiendo el patrón de microservicios.

Cada CRUD (Create, Read, Update, Delete) puede ser una Lambda independiente o bien se podria tener una sola Lambda que maneje diferentes operaciones según la solicitud.

Independiente del enfoque, hay que extraer la lógica del controlador de Spring Boot a una lógica que pueda ser ejecutada dentro de una Handler de Lambda.

Por ejemplo, se puede utilizar AWS API Gateway para recibir las solicitudes HTTP y luego invocar el Lambda, que procesará la solicitud y devolverá la respuesta.

Todo lo anterior se debe realizar con Spring Cloud Function que es una librería que facilita la transformación de una aplicación Spring Boot para que se ejecute en AWS Lambda con esto se puede reutilizar la lógica de la aplicación y adaptarla para ser usada como función lambda sin tener que reescribir la lógica completa.

Para convertir el proyecto Spring Boot en una función lambda, haremos lo siguiente:

Añadir la dependencia de Spring Cloud Function al archivo pom.xml:

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-function-adapter-aws</artifactId>
    <version>3.2.7</version> <!-- Ajusta la versión según tu caso -->
</dependency>
```

Creamos el bean que represente la lógica de la función:

```
@Bean
public Function<OrderRequest, OrderResponse> createOrder() {
    return orderRequest -> {
        //Guardamos el OrderRequest en la BD, puede ser una DynamoDB 
        OrderResponse response = new OrderResponse();
        response.setMessage("Orden creada satisfactoriamente");
        response.setOrderId(UUID.randomUUID().toString());
        return response;
    };
}
```


Luego empaquetamos la aplicación para AWS Lambda, debes crear un fat JAR con las dependencias necesarias. Al usar Spring Cloud Functiok se puede simplemente desplegar el JAR en Lambda. Usaremos AWS CLI para desplegarlo.

Comando AWS CLI: 

```
aws lambda create-function --function-name gestion-pedidos \
--zip-file fileb://target/jar-file.jar --handler com.ejemplo.Handler::handleRequest \
--runtime java11 --role arn:aws:iam::rol-predeterminado
```

Integración con API Gateway

Usaremos AWS API Gateway para exponer un endpoint HTTP que pueda ser invocado para las operaciones CRUD. Para esto debemos configurar cada ruta en el API Gateway para que apunte a la función Lambda que creamos.

Persistencia de datos:

Podemos usar Amazon RDS o DynamoDB para almacenar los datos 




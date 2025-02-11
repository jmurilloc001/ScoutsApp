# ScoutsApp
Web Application with Spring Boot and React

## ¿DE QUÉ TRATA?

***ScoutsApp*** es una aplicación que gestiona un grupo **Scouts**.

Cuenta con las siguientes características:
- Uso exclusivo de personas pertenecientes al grupo scout.
- Servicio que provee la gestión de pedidos. (Tienda)
- Un inventario de los materiales con los que cuenta el grupo.
- Post noticias sobre el grupo. (Acampadas, actividades, rutas...)
- Registro de asistencia a las reuniones y salidas de los educandos.

## BBDD

En este caso, he querido variar, y he usado como ***BBDD*** *PostgreSQL* 

Es un sistema gestor de base de datos objeto-relacional, lo que hace que sea una opción muy potente para mi programa.

Las tablas las he creado con Hibernate y JPA, desde las clases @Entity

En este caso he creado las tablas:

- Producto






## ¿CÓMO HE DIVIDIDO EL PROYECTO?

En el caso de mi proyecto, he usado una distribución con los siguientes paquetes:

  - controllers
  - entities
  - exceptions
  - models
  - repositories
  - services
  - util


## MANEJO DE ERRORES

Para los errores, he creado una clase llamada ***HandlerExceptionController*** en el package *controller*.

Esta clase lo que se ocupa es de en el caso de haber un problema, crear un **ResponseEntity<Error>**.
La clase Error, es una creada por mi en el package *models*, y cuenta con propiedades básicas como la fecha, el mensaje, o el status de la request.

Además de todo esto, he creado alguna excepción nueva, como ***ProductNotFoundException***.

## IDE 

El *IDE* que he usado para realizar el proyecto es IntelliJ Community.

El IDE, es gratuito, y tiene su versión de pago, **Ultimate**. La versión **Community** no cuenta con la integración
con Spring Boot.

Por lo tanto, la creación del proyecto, la he tenido que hacer de manera externa.

## Inicialización del proyecto

Para crear el proyecto, he usado ***Spring initializr***.

- Version de java: **JDK 17**
- Versión de Spring Boot: **3.4.2**
- Se ha creado con *Maven*.
- Añadidas las siguientes dependencias:
  - **Spring Boot DevTools**
  - **Spring Web**
  - **Spring Boot Actuator**
  - **JPA**


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

## SEGURIDAD

Para la seguridad he usado **SpringSecurity**.

Para ello, he creado una clase User, que está relacionada con la clase Affiliate, creando una relación **one to one**.
He usado *SpringValidate* para poder validar de forma más efectiva ciertos campos, como que no esten vacios, o en blanco, el nombre, y el password etc.

Además he creado alguna *anotación* propia para algún caso específico, como comprobar si el nombre ya existe en la base de datos.
A la anotación le he llamado: **@ExistsByUsername**.

He creado una clase de ***Roles*** que es la que indica los permisos que van a tener los usuarios.
En mi aplicación, todos tienen el rol de usuario, y además, pueden tener otros roles como el de *admin*.

Para la creación de los tokens, he usado ***JWT*** JSON Web Tokens.

## ROLES

- ***ADMIN***
- ***SCOUTER***
- ***COORDI***
- ***USER***

## MANEJO DE ERRORES

Para los errores, he creado una clase llamada ***HandlerExceptionController*** en el package *controller*.

Esta clase lo que se ocupa es de en el caso de haber un problema, crear un **ResponseEntity<Error>**.
La clase Error, es una creada por mi en el package *models*, y cuenta con propiedades básicas como la fecha, el mensaje, o el status de la request.

Además de todo esto, he creado alguna excepción nueva, como ***ProductNotFoundException***.

## IDE 

El *IDE* que he usado para realizar el proyecto es IntelliJ Community.

El IDE, es gratuito, y tiene su versión de pago, **Ultimate**. La versión **Community** no cuenta con la integración
con Spring Boot.

Por lo tanto, la creación del proyecto, la he tenido que hacer de manera externa usando Spring Initializr.

## Inicialización del proyecto

Para crear el proyecto, he usado ***Spring initializr***.

- Version de java: **JDK 17**
- Versión de Spring Boot: **3.4.2**
- Se ha creado con *Maven*.
- Añadidas las siguientes dependencias:
  - **Spring Boot DevTools**
  - **Spring Web**
  - **Spring Boot Actuator**
  - **Spring Boot JPA**
  - **PostGres SQL**
  - **Spring Security**
  - **Spring Validator**
  - **JWT**

## RUTAS

Por controladores:

- UserController:

  - GET: /users -> ok
  - GET: /users/{id} -> ok
  - POST: /users -> ok
  - POST: /users/register -> ok (Si tengo un token invalido no va)
  - PUT: /users/{id} -> ok
  - DELETE: /users/{id} -> ok
  - PATCH: /users/{userid}/affiliates/{affiliateid} -> ok
  - PATCH /users/{id}/password/{password} -> ok
  - PATCH /users/{id}/role/{role} -> ok
  - DELETE /users/{id}/role/{role} -> ok
  
- AffiliateController:

  - GET: /affiliates -> ok
  - GET: /affiliates/{id} -> ok
  - POST: /affiliates -> ok
  - DELETE: /affiliates/{id} -> ok
  - PUT: /affiliates/{id} -> ok (Tener cuidado en el front, para que se puedan modificar los campos que se quiera, y no todos)
  - PUT: /affiliates/{id}/importantdata (inscripcion_date, seccion, reuniones) -> ok

- ProductController:

  - GET: /products -> ok
  - GET: /produsts?price-min= -> ok
  - GET: /products?price-max= -> ok
  - GET: /products?date-after-year= -> ok
  - GET: /products?date-after-year=...&date-after-month= -> ok
  - GET: /products?date-after-year=...&date-after-month=...&date-after-day= -> ok
  - GET: /products/date-after?date=yyyy-MM-dd -> ok
  - POST: /products -> ok
  - PUT: /products/{id} -> ok
  - PATCH: /products/{id} -> ok
  - DELETE: /products/{id} -> COMPROBAR


ERROR IMPORTANTE: UNA VEZ QUE SE ME AÑADEN O ELIMINAN ROLES, NO ME DEJA HACER LOGIN CON EL USUARIO
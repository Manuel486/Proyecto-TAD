# Proyecto de microservicios de TAD

Este proyecto de microservicios utiliza Docker y Docker Compose para la orquestación y administración de los servicios. Incluye varios microservicios configurados para trabajar juntos en una red de Docker. A continuación se detalla la guía paso a paso para la configuración y ejecución del proyecto.

## Requisitos

- Docker
- Docker Compose

## Construcción de imágenes de Docker

Para construir las imágenes Docker de cada microservicio, navega al directorio de cada microservicio y ejecuta el siguiente comando:

`docker build -t microservices/[nombre-del-microservicio]:latest .
`

## Levantar servicios individuales

Si no deseas levantar todos los servicios a la vez, puedes levantar servicios individuales utilizando el comando docker-compose up seguido del nombre del servicio:

`docker-compose up -d microservice-config`

## Levantar todos los servicios

Para levantar todos los servicios definidos en docker-compose.yml:

`docker-compose up -d
`

## Diagrama de la arquitectura de microservicios

![Diagrama](img/diagrama.png)

## Rutas

- http://localhost:8100/swagger-ui/index.html <b>(Usuarios)</b>

![Swagger de usuarios](img/swagger-usuarios.png)

- http://localhost:8092/swagger-ui/index.html <b>(Proyectos)</b>

![Swagger de proyectos](img/swagger-proyectos.png)

- http://localhost:8095/swagger-ui/index.html <b>(Anotaciones)</b>

![Swagger de anotaciones](img/swagger-anotaciones.png)

- http://localhost:8093/swagger-ui/index.html <b>(Autenticación)</b>

![Swagger de autenticacion](img/swagger-autenticacion.png)

- http://localhost:8761 <b>(Eureka Server)</b>

![Eureka Server](img/eureka-server.png)

## Docker compose

![Docker Compose](img/docker-compose.png)

## Configuraciones

![Configuracion](img/configuraciones.png)

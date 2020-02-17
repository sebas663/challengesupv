# Challengesupv

## Api publica con datos precargados para poder ver el resultado de los endpoint sin cargar datos.

GET, POST, PUT
http://104.198.150.70/api/personas

GET, DELETE
http://104.198.150.70/api/personas/{id}

POST
http://104.198.150.70/api/personas/{id}/padre/{id2}

GET
http://104.198.150.70/api/relaciones/{id1}/{id2}

GET
http://104.198.150.70/api/estadisticas

## Ejemplo del body para el  POST

```
 {
  "firstName" : "Ayana",                --> requerido
  "secondName" : "Jewelery",
  "lastName" : "Barrows",               --> requerido
  "secondLastName" : "Homel",
  "documentType" : "DNI",               --> requerido
  "documentNumber" : 40227000,          --> requerido    
  "country" : "ARG",                    --> requerido, solo acepta hasta 3 caracteres en mayuscula
  "sex" : "M",                          --> requerido, solo acepta M o H
  "birthday" : "1982-02-12",            --> requerido, solo mayores de 18 anos
  "telephoneNumber" : "1155832610",     --> 
  "email" : "Ayana.Jewelery@gmail.com"
 }
```
## Clonando el proyecto se puede correr localmente en un terminal

Parado sobre el directorio app:

	Linux   ./mvnw

	Windowx mvnw

	Se accede http://localhost:8081/api/personas

Parado sobre el directorio challengesupv:

        Win, linux, si es linux depende la instalacion de Docker y Docker-compose, se puede requerir "sudo"
```
$] docker-compose up -d

```   
	levanta una instancia de nginx, la aplicacion y una instancia de maria db

	Se accede http://localhost/api/personas

O se puede correr levantando el proyecto en un ide 

	Recomiendo Spring Tool Suite 4 

	Se importa como un proyecto existente maven
	una vez que compilo el workspace se puede correr seleccionando en el ide
	click derecho sobre el root del proyecto --> Run as --> Spring Boot App

La instalacion de Spring Tool Suite 4, Maven 3.6, Docker, Docker-compose 
se puede ver en sus respectivas pagina oficiales

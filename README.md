
Proyecto Spring boot de Aplicacion API REST para un gestor de tareas con acceso a base de datos H2

#Servidor y Puerto de escucha : 
    Apache Tomcat en puerto 8081

#Configuracion de conexion a BBDD y puerto : archivo application.properties

#Dependencias /starters: 
    Starters para persistencia: H2 Driver y Spring Data JPA
    Starters para API Rest Spring Web 
    Documentacion: Formato JavaDoc y uso de libreria springfox para generar documentacion en formato swagger
                    Documentacion de API Rest disponible en: http://localhost:8081/swagger-ui/


#Acceso de Prueba: colleccion de Postman / navegador a traves de http://localhost:8081/swagger-ui/ 

#Testing : JUnit, usando Clase TestRestTemplate para test de integracion y unitarios --> Permite lanzar peticiones HTTP

#Acceso integrado con App FrontEnd desarrollada con libreria React (En desarrollo)

#Entidad Tarea:
Entidad:
    Clase Tarea
Repositorio:
    TareaRepository (@Repository que extiende del repositorio JpaRepository<Clase,PK >)
Servicio:
    TareaService
Controlador:
    TareaController (@RestController)
Configuracion:
    clase SwaggerConfig
Peticiones HTTP permitidas: 
    Buscar todos las tareas (Metodo GET)
    Buscar una tarea por id (Metodo GET)
    Actualizar una tarea (Metodo PUT)
    Borrar una tarea (Metodo DELETE)
    Crear una tarea (METODO POST)
    Borrar todas las tareas (METODO DELETE)
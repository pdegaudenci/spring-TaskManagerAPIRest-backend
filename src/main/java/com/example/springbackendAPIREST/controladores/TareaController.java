package com.example.springbackendAPIREST.controladores;

import com.example.springbackendAPIREST.entidades.Tarea;
import com.example.springbackendAPIREST.servicios.TareaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
public class TareaController {
    private TareaService tareaService;
    private final Logger log = LoggerFactory.getLogger(Tarea.class);
    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    /**
     * Gestiona una Peticion HTTP GET para obtener todas las tareas
     * http://localhost:8081/api/tareas (METODO GET)
     * @return ResponseEntity con lista de tareas o con estado NO_CONTENT si no existen tareas
     */
    @GetMapping("/api/tareas")
    @ApiOperation("Metodo que devuelve una lista de tareas obtenida del la BBDD")
    public ResponseEntity<Tarea> findAll()
    {
        List<Tarea> tareas=tareaService.findAll();
        if(tareas.size()>0)
            return new ResponseEntity(tareas,null,HttpStatus.OK);
        return new ResponseEntity(tareas,null,HttpStatus.NO_CONTENT);
    }

    /**
     * Metodo para gestionar la busqueda de una tarea en funcion de la id
     * URL: http://localhost:8081/api/tareas/id (METODO GET)
     * @param id : Se envia en la peticion HTTP GET
     * @return ResponseEntity con respuesta HTTP conteniendo la tarea o estado NOT_FOUND en caso de que no exista una tarea con esa id
     */
    @GetMapping("/api/tarea/{id}")
    @ApiOperation("Metodo que devuelve una tarea buscandola por ID en la BBDD")
    public ResponseEntity<Tarea> findById(@ApiParam("parametro de tipo Long correspondiente al id unico de cada tarea--> URL: http://localhost:8081/api/tareas/id") @PathVariable Long id){

        if(id<1)
            return ResponseEntity.badRequest().build();

        Optional<Tarea> opt=tareaService.findById(id);

        if(opt.isEmpty()){
            log.warn("No se existe la tarea con id :"+id.toString());
            return ResponseEntity.notFound().build();
        }
        else{
            log.info("La tarea se obtuvo correctamente");
            return ResponseEntity.ok().body(opt.get());
        }
    }


    /**
     * Metodo que crea y almacena una tarea nueva en la BBDD
     * URL: http://localhost:8081/api/tareas (METODO POST)
     * @param tarea de la Clase Tarea recibida en el Body de la peticion HTTP POST
     * @return ResponseEntity con la Tarea creada o con estado BADREQUEST si hubo un error con los datos recibidos
     */
    @PostMapping("/api/tareas")
    @ApiOperation("Metodo que devuelve una tarea creada, la cual es enviada en el body de una peticion HTTP POST --> URL: http://localhost:8081/api/tareas")
    public ResponseEntity<Tarea> createTask(@ApiParam("Tarea a crear que es enviada en el body de la peticion HTTP en formato JSON")@RequestBody Tarea tarea){
        if(tarea.getId()!=null){
            log.error("Error al proporcionar un id para crear una tarea");
            return ResponseEntity.badRequest().build();
        }
        if(tarea==null || tarea.getDescripcion().equals("") || tarea.getNivel() == null || tarea.getNombre().equals("") || tarea.isCompletado() == null){
            log.error("Error en los campos(Nombre,descripcion,nivel o estado ) enviados de la tarea");
            return ResponseEntity.badRequest().build();
        }
        Tarea tareaCreada=tareaService.createTask(tarea);
        return ResponseEntity.ok().body(tareaCreada);
    }

    /**
     * Metodo que crea y almacena una tarea nueva en la BBDD
     * URL: http://localhost:8081/api/tarea (METODO PUT)
     * @param tarea : de tipo Tarea que es recibida en body de Peticion HTTP
     * @return tarea actualizada
     */
    @PutMapping("/api/tarea")
    @ApiOperation("Metodo que gestiona actualizacion de una tarea,  enviada en el body de una peticion HTTP PUT --> URL: http://localhost:8081/api/tarea")
    public ResponseEntity<Tarea> update(@ApiParam("Tarea a actualizar que es enviada en el body de la peticion HTTP en formato JSON")@RequestBody Tarea tarea) {
        if (tarea.getId() == null) {
            log.warn("Debe especificar id de tarea a actualizar");
            return ResponseEntity.badRequest().build();
        }
        if (!tareaService.buscarTareaPorId(tarea.getId())) {
            log.warn("Tarea no existente");
            //Devuelve que no se ha encontrado la peticion
            return ResponseEntity.notFound().build();
        }
        Tarea resultado = tareaService.actualizarTarea(tarea);
        return ResponseEntity.ok(resultado);
    }

    /**
     * Metodo que borra una tarea en funcion de su id
     * URL: http://localhost:8081/api/tareas/id (METODO DELETE)
     * @param id de tipo Long correspondiente a la tarea que se quiere borrar
     * @return  ResponseEntity<Tarea>
     */
    @DeleteMapping("/api/tareas/{id}")
    @ApiOperation("Metodo que devuelve ResponseEntity.NO_CONTENT si fue borrada correctamente  o ResponseEntity.NOTFOUND  -->URL: http://localhost:8081/api/tareas/id")
    public ResponseEntity<Tarea> borrarTarea(@ApiParam("Id de la tarea a borrar de tipo Long")@PathVariable Long id) {
        if (!tareaService.borrarTarea(id)) {
            log.warn("Intentando borrar  tarea no existente");
            return ResponseEntity.notFound().build();
        }
//Se ha borrado correctamente pero no devuelve contenido
        return ResponseEntity.noContent().build();
    }

    /**
     * MEtodo que borra todas las tareas
     * URL: http://localhost:8081/api/tareas (METODO DELETE)
     * @return String con mensaje que se borraron todas las tareas
     */
    @DeleteMapping("/api/tareas")
    @ApiOperation("Metodo que borra todas las tareas --> URL: http://localhost:8081/api/tareas")
    public String borrarTodos(){
        tareaService.borrarTodos();
        log.info("Se han borrado todas las tareas");
        return "Se han borrado todas las tareas";
    }



}

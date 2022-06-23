package com.example.springbackendAPIREST.controladores;

import com.example.springbackendAPIREST.entidades.LEVELS;
import com.example.springbackendAPIREST.entidades.Tarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase que testea los metodos del controlador de tareas simulando peticiones HTTP --> Cada metodo tiene la siguiente estructura:
 *      Configuracion de la prueba
 *      Ejecucion de comportamiento
 *      Comprobacion de resultados
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TareaControllerTest {
    /**
     * Permite acceder a servicio REST , generando peticiones de cliente HTTP
     */
    private TestRestTemplate testRestTemplate;

    @Autowired //Para que Spring inyecte este objeto builder automaticamente
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port; // Generacion automatica

    /**
     * Metodo que se ejecuta antes de cada test e inicializa objeto que construye peticiones HTTP en cada test
     */
    @BeforeEach
    void setUp() {
        //Contruyo objeto que genera las peticiones HTTP
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @DisplayName("Prueba la obtencion de todos los libros ")
    @Test
    void findAll() {
        // Configuracion de prueba y ejecutar comportamiento
        ResponseEntity<Tarea[]> resultado=testRestTemplate.getForEntity("/api/tareas",Tarea[].class);

        // Ejecutar comprobaciones
        assertEquals(HttpStatus.OK,resultado.getStatusCode());
        assertEquals(200,resultado.getStatusCodeValue());

        List<Tarea> listado = Arrays.asList(resultado.getBody());
    }
    @DisplayName("Prueba obtener un libro por id (id negativo y una peticion id normal) ")
    @Test
    void findById() {
        ResponseEntity<Tarea> resultado=testRestTemplate.getForEntity("/api/tarea/2",Tarea.class);
        assertEquals(HttpStatus.OK,resultado.getStatusCode());

        ResponseEntity<Tarea> idNegativo=testRestTemplate.getForEntity("/api/tarea/-1",Tarea.class);
        assertEquals(HttpStatus.BAD_REQUEST,idNegativo.getStatusCode());


    }

    /**
     * Estructura de la prueba:
     *      Establecimiento de cabecera
     *      Generacion de JSON que simulara el body que recibe el metodo crearTarea del controlador
     *      Creacion de una entidad de Tipo Tarea de la PETICION HTTP (Que contiene cabecera y JSON)
     *      Ejecucion de comportamiento
     *      Comprobacion de resultado
     *
     */
    @DisplayName("Creacion de nueva tarea ")
    @Test
    void createTask() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // tipo de contenido de cabecera
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //CREO JSON
        String json = """
                                
                {
                     
                        "nombre": "tareaCreada mediante metodo de test",
                        "descripcion": "este es un ejemplo de tarea creada por post",
                        "nivel": "URGENTE",
                        "completado": false
                    }
                   
                """;


        HttpEntity<String> peticion=new HttpEntity<>(json,headers);

        ResponseEntity<Tarea> respuesta=testRestTemplate.exchange("/api/tareas", HttpMethod.POST,peticion,Tarea.class);// Ejecuta peticion que devuelva datos de tipo libro

       Tarea tarea=respuesta.getBody();

       assertEquals("tareaCreada mediante metodo de test",tarea.getNombre());
        assertEquals("este es un ejemplo de tarea creada por post",tarea.getDescripcion());
        assertEquals(LEVELS.URGENTE,tarea.getNivel());
        assertEquals(false,tarea.isCompletado());
        assertNotNull(tarea.getId());

    }

    @Test
    void update() {

    }

    @Test
    void borrarTarea() {

        testRestTemplate.delete("/api/tareas/1");

        ResponseEntity<Tarea> resultado = testRestTemplate.getForEntity("/api/tarea/1",Tarea.class);

        assertEquals(HttpStatus.NOT_FOUND,resultado.getStatusCode());

    }

    @Test
    void borrarTodos() {
    }
}
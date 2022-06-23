package com.example.springbackendAPIREST.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collection;
import java.util.Collections;

/**
 * Clase Configuration de Swagger para generacion de documentacion de API Rest
 * http://localhost:8081/swagger-ui/
 * http://localhost:8081/v2/api-docs/
 */
@Configuration
public class SwaggerConfig {

    /**
     * Componente disponible en applicattion context
     * @return {Docket} Contenedor que contiene toda la configuracion para generar la documentacion swagger
     *
     */
    @Bean
    public Docket api(){
        // methods chaining --> Implementa patron de diseño Builder
        return new Docket(DocumentationType.SWAGGER_2).
                apiInfo(apiDetails()).
                select().
                apis(RequestHandlerSelectors.any()).
                paths(PathSelectors.any()).build(); // Contruye sobre las rutas de los endpoints
    }

    /**
     * Metodo para generar la informacion general de la API Rest
     * @return objeto de la clase ApiInfo
     */
    private ApiInfo apiDetails(){
    return new ApiInfo("Springboot --> Gestor de Tareas API REST  (backend) ",
            "Documentacion Swagger de API Rest","v1.0","OpenSource Beta version",
            new Contact("Pedro Sebastian Degaudenci","https://github.com/pdegaudenci","pdegaudenci@hotmail.com"),
            "https://github.com","MIT", Collections.emptyList()
    );
    }
}

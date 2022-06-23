package com.example.springbackendAPIREST;

import com.example.springbackendAPIREST.entidades.LEVELS;
import com.example.springbackendAPIREST.entidades.Tarea;
import com.example.springbackendAPIREST.respositorios.TareaRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringBackendApirestApplication {

	public static void main(String[] args) {


		ApplicationContext context =SpringApplication.run(SpringBackendApirestApplication.class, args);

		TareaRepository repository = context.getBean(TareaRepository.class);

		Tarea tarea1 = new Tarea(null,"tarea1","este es un ejemplo de tarea", LEVELS.NORMAL,true);

		repository.save(tarea1);
		System.out.println(repository.findAll());


	}

}

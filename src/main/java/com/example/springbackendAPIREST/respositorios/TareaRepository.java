package com.example.springbackendAPIREST.respositorios;

import com.example.springbackendAPIREST.entidades.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends JpaRepository<Tarea,Long> {

}

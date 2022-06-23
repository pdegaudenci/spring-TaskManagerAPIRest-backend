package com.example.springbackendAPIREST.servicios;

import com.example.springbackendAPIREST.entidades.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TareaServiceTest {

    @Autowired
    TareaService tareaService;
    @Test
    void findAll() {
        List<Tarea> lista=tareaService.findAll();

        assertTrue(lista!=null);

    }

    @Test
    void findById() {
    }

    @Test
    void createTask() {
    }

    @Test
    void borrarTarea() {
    }

    @Test
    void borrarTodos() {
    }

    @Test
    void buscarTareaPorId() {
    }

    @Test
    void actualizarTarea() {
    }
}
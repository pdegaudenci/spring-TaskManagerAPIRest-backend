package com.example.springbackendAPIREST.servicios;

import com.example.springbackendAPIREST.entidades.Tarea;
import com.example.springbackendAPIREST.respositorios.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Clase Servicio que gestiona los datos de las tareas, obtenidos de la BBDD
 */
@Service
public class TareaService {

    TareaRepository tareaRepository;

    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    public List<Tarea> findAll(){
        return tareaRepository.findAll();
    }

    public Optional<Tarea> findById(Long id){
        return tareaRepository.findById(id);

    }

    public Tarea createTask(Tarea tarea){
        return tareaRepository.save(tarea);
    }

    public Boolean borrarTarea(Long id){
       if(tareaRepository.existsById(id)){
           tareaRepository.deleteById(id);
           return true;
       }
       return false;

    }
    public void borrarTodos(){
        if(tareaRepository.findAll().size()>0)
            tareaRepository.deleteAll();

    }

    public boolean buscarTareaPorId(Long id){
        return tareaRepository.existsById(id);
    }

    public Tarea actualizarTarea(Tarea tarea) {
        Tarea tareaBuscada = findById(tarea.getId()).get();
        Tarea tareaActualizada = new Tarea();


        if(tarea.getNivel()== null)
            tareaActualizada.setNivel(tareaBuscada.getNivel());
        else
            tareaActualizada.setNivel(tarea.getNivel());
        if(tarea.getNombre()==null)
            tareaActualizada.setNombre(tareaBuscada.getNombre());
        else
            tareaActualizada.setNombre(tarea.getNombre());
        if( tarea.getDescripcion() ==null)
            tareaActualizada.setDescripcion(tareaBuscada.getDescripcion());
        else
            tareaActualizada.setDescripcion(tarea.getDescripcion());
        if(tarea.isCompletado() == null)
            tareaActualizada.setCompletado(tareaBuscada.isCompletado());
        else
            tareaActualizada.setCompletado(tarea.isCompletado());
        tareaActualizada.setId(tareaBuscada.getId());
        return tareaRepository.save(tareaActualizada);
    }
}

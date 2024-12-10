package com.gestionador.security.tareas;

import com.gestionador.security.user.User;
import com.gestionador.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.gestionador.security.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tareas")
public class TareasController {

    @Autowired
    private TareasRepository tareasRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Tareas> getAllTareas() {
        return tareasRepository.findAll();
    }

    @GetMapping("/usuario/{userId}")
    public List<Tareas> getTareasByUserId(@PathVariable Integer userId) {
        return tareasRepository.findByUserId(userId);
    }

    @PostMapping("/usuario/{userId}")
    public Tareas createTarea(@PathVariable Integer userId, @RequestBody Tareas tarea) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + userId + " no encontrado"));

        tarea.setUser(user);
        return tareasRepository.save(tarea);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tareas> getTareaById(@PathVariable Long id) {
        Tareas tarea = tareasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con ID " + id + " no encontrada"));
        return ResponseEntity.ok(tarea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tareas> updateTarea(@PathVariable Long id, @RequestBody Tareas tareaDetails) {
        Tareas tarea = tareasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con ID " + id + " no encontrada"));

        tarea.setTitulo(tareaDetails.getTitulo());
        tarea.setDescripcion(tareaDetails.getDescripcion());
        tarea.setStatus(tareaDetails.getStatus());
        tarea.setFechaCreacion(tareaDetails.getFechaCreacion());
        tarea.setFechaConcretada(tareaDetails.getFechaConcretada());

        Tareas updatedTarea = tareasRepository.save(tarea);
        return ResponseEntity.ok(updatedTarea);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTarea(@PathVariable Long id) {
        Tareas tarea = tareasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea con ID " + id + " no encontrada"));

        tareasRepository.delete(tarea);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}

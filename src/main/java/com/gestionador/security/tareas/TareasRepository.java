package com.gestionador.security.tareas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TareasRepository extends JpaRepository<Tareas, Long> {
    List<Tareas> findByUserId(Integer userId);
}

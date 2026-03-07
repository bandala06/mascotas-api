package com.example.mascotas.mascotasServicios;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaServicioRepository extends CrudRepository<MascotaServicio, Long> {
    // ¡Con solo esta línea, Spring Boot ya sabe hacer todo el CRUD en la BD!
}
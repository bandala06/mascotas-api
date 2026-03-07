package com.example.mascotas.direccion;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DireccionRepository extends CrudRepository<Direccion, Long> {
}
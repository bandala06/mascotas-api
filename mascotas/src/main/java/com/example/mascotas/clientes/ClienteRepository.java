package com.example.mascotas.clientes;

import org.springframework.data.repository.CrudRepository;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    Long idCliente(Long idCliente);
}

package com.example.mascotas.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioRepository servicioRepository;

    // Leer todos los servicios (GET)
    @GetMapping
    public ResponseEntity<Iterable<Servicio>> findAll() {
        return ResponseEntity.ok(servicioRepository.findAll());
    }

    // Leer un servicio por ID (GET)
    @GetMapping("/{idServicio}")
    public ResponseEntity<Servicio> findById(@PathVariable Long idServicio) {
        return servicioRepository.findById(idServicio)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo servicio (POST)
    @PostMapping
    public ResponseEntity<Servicio> create(@RequestBody Servicio servicio) {
        return ResponseEntity.ok(servicioRepository.save(servicio));
    }

    // Actualizar un servicio existente (PUT)
    @PutMapping("/{idServicio}")
    public ResponseEntity<Servicio> update(@PathVariable Long idServicio, @RequestBody Servicio servicioActualizado) {
        return servicioRepository.findById(idServicio)
                .map(servicioExistente -> {
                    // Actualizamos las descripciones y el precio
                    servicioExistente.setDescripcion(servicioActualizado.getDescripcion());
                    servicioExistente.setDescripcion2(servicioActualizado.getDescripcion2());
                    servicioExistente.setPrecio(servicioActualizado.getPrecio());

                    return ResponseEntity.ok(servicioRepository.save(servicioExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un servicio (DELETE)
    @DeleteMapping("/{idServicio}")
    public ResponseEntity<Void> delete(@PathVariable Long idServicio) {
        if (servicioRepository.existsById(idServicio)) {
            servicioRepository.deleteById(idServicio);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
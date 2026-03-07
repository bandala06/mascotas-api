package com.example.mascotas.direccion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/direccion")
public class DireccionController {

    @Autowired
    private DireccionRepository direccionRepository;

    // Leer todas las direcciones (GET)
    @GetMapping
    public ResponseEntity<Iterable<Direccion>> findAll() {
        return ResponseEntity.ok(direccionRepository.findAll());
    }

    // Leer una dirección por ID (GET)
    @GetMapping("/{idDireccion}")
    public ResponseEntity<Direccion> findById(@PathVariable Long idDireccion) {
        return direccionRepository.findById(idDireccion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva dirección (POST)
    @PostMapping
    public ResponseEntity<Direccion> create(@RequestBody Direccion direccion) {
        return ResponseEntity.ok(direccionRepository.save(direccion));
    }

    // Actualizar una dirección existente (PUT)
    @PutMapping("/{idDireccion}")
    public ResponseEntity<Direccion> update(@PathVariable Long idDireccion, @RequestBody Direccion direccionActualizada) {
        return direccionRepository.findById(idDireccion)
                .map(direccionExistente -> {
                    // Actualizamos calle y número
                    direccionExistente.setCalle(direccionActualizada.getCalle());
                    direccionExistente.setNumero(direccionActualizada.getNumero());

                    return ResponseEntity.ok(direccionRepository.save(direccionExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una dirección (DELETE)
    @DeleteMapping("/{idDireccion}")
    public ResponseEntity<Void> delete(@PathVariable Long idDireccion) {
        if (direccionRepository.existsById(idDireccion)) {
            direccionRepository.deleteById(idDireccion);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
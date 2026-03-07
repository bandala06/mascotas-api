package com.example.mascotas.mascotasServicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mascota-servicio") // Esta será la URL de tu API
public class MascotaServicioController {

    @Autowired
    private MascotaServicioRepository mascotaServicioRepository;

    // Leer todos (GET)
    @GetMapping
    public ResponseEntity<Iterable<MascotaServicio>> findAll() {
        return ResponseEntity.ok(mascotaServicioRepository.findAll());
    }

    // Leer uno por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<MascotaServicio> findById(@PathVariable Long id) {
        return mascotaServicioRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo registro (POST)
    @PostMapping
    public ResponseEntity<MascotaServicio> create(@RequestBody MascotaServicio mascotaServicio) {
        return ResponseEntity.ok(mascotaServicioRepository.save(mascotaServicio));
    }

    // Actualizar un registro existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<MascotaServicio> update(@PathVariable Long id, @RequestBody MascotaServicio actualizado) {
        return mascotaServicioRepository.findById(id)
                .map(existente -> {
                    // Actualizamos la fecha y la nota
                    existente.setFecha(actualizado.getFecha());
                    existente.setNota(actualizado.getNota());

                    // Nota: Si quieres cambiar la Mascota o el Servicio asociado,
                    // tendrías que actualizar esos objetos aquí también.

                    return ResponseEntity.ok(mascotaServicioRepository.save(existente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar un registro (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (mascotaServicioRepository.existsById(id)) {
            mascotaServicioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
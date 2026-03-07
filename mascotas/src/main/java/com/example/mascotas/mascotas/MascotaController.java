package com.example.mascotas.mascotas;

import com.example.mascotas.clientes.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping( "/mascota")
public class MascotaController {

    @Autowired
    private MascotasRepository mascotasRepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping()
    public ResponseEntity<Iterable<Mascota>> findAll(UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.ok(mascotasRepository.findAll());



    }
// metodo crear mascota
   @PostMapping
   public ResponseEntity<Mascota> create(@RequestBody Mascota mascota,
                                          UriComponentsBuilder uriComponentsBuilder) {
        return ResponseEntity.ok(mascotasRepository.save(mascota));
   }
    // Leer una sola mascota por su ID (GET)
    @GetMapping("/{idMascota}")
    public ResponseEntity<Mascota> findById(@PathVariable Long idMascota) {
        return mascotasRepository.findById(idMascota)
                .map(ResponseEntity::ok) // Si la encuentra, devuelve un 200 OK con la mascota
                .orElseGet(() -> ResponseEntity.notFound().build()); // Si no, devuelve un 404 Not Found
    }

    // Actualizar una mascota existente (PUT)
    @PutMapping("/{idMascota}")
    public ResponseEntity<Mascota> update(@PathVariable Long idMascota, @RequestBody Mascota mascotaActualizada) {
        return mascotasRepository.findById(idMascota)
                .map(mascotaExistente -> {
                    // Actualizamos los campos con la nueva información
                    mascotaExistente.setNombre(mascotaActualizada.getNombre());
                    mascotaExistente.setTipo(mascotaActualizada.getTipo());
                    mascotaExistente.setSexo(mascotaActualizada.getSexo());
                    mascotaExistente.setEdad(mascotaActualizada.getEdad());
                    mascotaExistente.setEnPeligro(mascotaActualizada.isEnPeligro());

                    // Si también necesitas actualizar el cliente asociado, podrías hacerlo aquí

                    // Guardamos y devolvemos la mascota actualizada
                    return ResponseEntity.ok(mascotasRepository.save(mascotaExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar una mascota (DELETE)
    @DeleteMapping("/{idMascota}")
    public ResponseEntity<Void> delete(@PathVariable Long idMascota) {
        if (mascotasRepository.existsById(idMascota)) {
            mascotasRepository.deleteById(idMascota);
            return ResponseEntity.noContent().build(); // Devuelve 204 No Content si se eliminó con éxito
        }
        return ResponseEntity.notFound().build(); // Devuelve 404 si el ID no existe
    }



}

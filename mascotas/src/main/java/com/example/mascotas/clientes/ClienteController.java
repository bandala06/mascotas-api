package com.example.mascotas.clientes;


import com.example.mascotas.mascotas.Mascota;
import com.example.mascotas.mascotas.MascotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotasRepository mascotasRepository;

    /// metodo para obtener todos los clientes
    @GetMapping()
    public ResponseEntity<Iterable<Cliente>> findAll() {
        return ResponseEntity.ok(this.clienteRepository.findAll());

    }

    //metodo crear cliente
    @PostMapping
    public ResponseEntity<Cliente> create
    (@RequestBody Cliente cliente,
     UriComponentsBuilder uriBuilder) {

        if (cliente.getDireccion() != null) {
            cliente.getDireccion().setCliente(cliente);

        }

        if (cliente.getMascotas() != null && !cliente.getMascotas().isEmpty()) {
            cliente.getMascotas().forEach(mascota -> mascota.setCliente(cliente));

        }

        Cliente create = clienteRepository.save(cliente);
        URI uri = uriBuilder.path("/cliente/{idCliente}").buildAndExpand(
                create.getIdCliente()
        ).toUri();

        return ResponseEntity.created(uri).body(create);


    }

    /// buscar cliente id
    @GetMapping("/{idCliente}")
    public ResponseEntity<Cliente> findById(@PathVariable Long idCliente) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(idCliente);

        if (clienteOptional.isPresent()) {
            return ResponseEntity.ok(clienteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    // Actualizar
    @PutMapping("/{idCliente}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long idCliente, @RequestBody Cliente clienteActualizado) {
        return clienteRepository.findById(idCliente)
                .map(clienteExistente -> {
                    clienteExistente.setNombre(clienteActualizado.getNombre());
                    clienteExistente.setApPaterno(clienteActualizado.getApPaterno());
                    clienteExistente.setApMaterno(clienteActualizado.getApMaterno());
                    clienteExistente.setEmail(clienteActualizado.getEmail());
                    // Nota: Si quieres actualizar la dirección o mascotas, se requiere lógica adicional
                    return ResponseEntity.ok(clienteRepository.save(clienteExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar
    @DeleteMapping("/{idCliente}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long idCliente) {
        if (clienteRepository.existsById(idCliente)) {
            clienteRepository.deleteById(idCliente);
            return ResponseEntity.noContent().build(); // Retorna 204 No Content si fue exitoso
        }
        return ResponseEntity.notFound().build(); // Retorna 404 si no existe
    }
}



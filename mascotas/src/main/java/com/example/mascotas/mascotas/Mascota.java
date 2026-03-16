package com.example.mascotas.mascotas;


import com.example.mascotas.clientes.Cliente;
import com.example.mascotas.mascotasServicios.MascotaServicio;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
@Table(name = "mascota")

public class Mascota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mascota")
    private Long idMascota;
    @Column (nullable = false, length = 100)
    private String nombre;
    private char sexo;
    @Column(nullable = false, length = 100)
    private String tipo;
    private byte edad;
    private boolean enPeligro;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCliente")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cliente cliente;

    @OneToMany(mappedBy = "mascota")
    @JsonIgnore
    private List<MascotaServicio> mascotaServicio;
}

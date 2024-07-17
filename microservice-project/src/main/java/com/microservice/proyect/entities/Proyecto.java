package com.microservice.proyect.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "proyectos")
public class Proyecto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titulo;
    private String descripcion;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    private Integer ciclo;

    @Column(name = "usuario_id_duenio")
    private Integer usuarioIdDuenio;

    @ElementCollection
    @CollectionTable(name = "usuario_proyectos", joinColumns = @JoinColumn(name = "id_proyecto"))
    @Column(name = "usuario_id")
    private List<Integer> usuarioIds;
}

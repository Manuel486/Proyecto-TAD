package com.microservice.annotation.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "anotaciones")
public class Anotacion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contenido;

    @Column(name = "id_usuario")
    private Integer usuarioId;

    @Column(name = "id_proyecto")
    private Integer proyectoId;
}

package com.example.Festivos.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "festivos")
public class Festivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate fecha;
    private String nombre;
    private Integer tipo;
    private Integer diasDePascua;

    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getDiasDePascua() {
        return diasDePascua;
    }

    public void setDiasDePascua(Integer diasDePascua) {
        this.diasDePascua = diasDePascua;
    }
}

package com.example.Festivos.service;

import com.example.Festivos.entity.Festivo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FestivoService {
    List<Festivo> getAllFestivos();
    Optional<Festivo> getFestivoById(Long id);
    Festivo createFestivo(Festivo festivo);
    Festivo updateFestivo(Long id, Festivo festivo);
    void deleteFestivo(Long id);
    boolean isFestivo(LocalDate fecha);
}
package com.example.Festivos.service.impl;


import com.example.Festivos.dao.FestivoDao;
import com.example.Festivos.entity.Festivo;
import com.example.Festivos.service.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Service
public class FestivoServiceImpl implements FestivoService {

    @Autowired
    private FestivoDao festivoDao;

    @Override
    public List<Festivo> getAllFestivos() {
        return festivoDao.findAll();
    }

    @Override
    public Optional<Festivo> getFestivoById(Long id) {
        return festivoDao.findById(id);
    }

    @Override
    public Festivo createFestivo(Festivo festivo) {
        return festivoDao.save(festivo);
    }

    @Override
    public Festivo updateFestivo(Long id, Festivo festivoDetails) {
        Optional<Festivo> festivoOptional = festivoDao.findById(id);
        if (festivoOptional.isPresent()) {
            Festivo festivo = festivoOptional.get();
            festivo.setFecha(festivoDetails.getFecha());
            festivo.setNombre(festivoDetails.getNombre());
            festivo.setTipo(festivoDetails.getTipo());
            festivo.setDiasDePascua(festivoDetails.getDiasDePascua());
            return festivoDao.save(festivo);
        }
        return null;
    }

    @Override
    public void deleteFestivo(Long id) {
        festivoDao.deleteById(id);
    }

    @Override
    public boolean isFestivo(LocalDate fecha) {
        List<Festivo> festivos = festivoDao.findAll();
        LocalDate pascua = calcularDomingoDePascua(fecha.getYear());

        return festivos.stream().anyMatch(festivo -> {
            LocalDate festivoFecha;
            switch (festivo.getTipo()) {
                case 1: // Fijo
                    festivoFecha = festivo.getFecha();
                    break;
                case 2: // Ley de "Puente festivo"
                    festivoFecha = festivo.getFecha().with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.MONDAY));
                    break;
                case 3: // Basado en el domingo de pascua
                    festivoFecha = pascua.plusDays(festivo.getDiasDePascua());
                    break;
                case 4: // Basado en el domingo de pascua y Ley de "Puente festivo"
                    festivoFecha = pascua.plusDays(festivo.getDiasDePascua()).with(java.time.temporal.TemporalAdjusters.nextOrSame(java.time.DayOfWeek.MONDAY));
                    break;
                default:
                    throw new IllegalArgumentException("Tipo de festivo no soportado: " + festivo.getTipo());
            }
            return festivoFecha.equals(fecha);
        });
    }

    private LocalDate calcularDomingoDePascua(int year) {
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 24) % 30;
        int e = (2 * b + 4 * c + 6 * d + 5) % 7;
        int day = 15 + d + e;

        if (day > 31) {
            return LocalDate.of(year, 4, day - 31);
        } else {
            return LocalDate.of(year, 3, day);
        }
    }
}
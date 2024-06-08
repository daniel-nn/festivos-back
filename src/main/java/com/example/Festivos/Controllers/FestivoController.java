package com.example.Festivos.Controllers;

import com.example.Festivos.entity.Festivo;
import com.example.Festivos.service.FestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/festivos")
public class FestivoController {

    @Autowired
    private FestivoService festivoService;

    @GetMapping("/all")
    public ResponseEntity<List<Festivo>> getAllFestivos() {
        try {
            List<Festivo> festivos = festivoService.getAllFestivos();
            return new ResponseEntity<>(festivos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Festivo> getFestivoById(@PathVariable Long id) {
        try {
            Optional<Festivo> festivoData = festivoService.getFestivoById(id);
            return festivoData.map(festivo -> new ResponseEntity<>(festivo, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Festivo> createFestivo(@RequestBody Festivo festivo) {
        try {
            Festivo _festivo = festivoService.createFestivo(festivo);
            return new ResponseEntity<>(_festivo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Festivo> updateFestivo(@PathVariable Long id, @RequestBody Festivo festivoDetails) {
        try {
            Festivo updatedFestivo = festivoService.updateFestivo(id, festivoDetails);
            if (updatedFestivo != null) {
                return new ResponseEntity<>(updatedFestivo, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFestivo(@PathVariable Long id) {
        try {

            festivoService.deleteFestivo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/validate/{fecha}")
    public ResponseEntity<String> validateFestivo(@PathVariable String fecha) {
        try {
            LocalDate date = LocalDate.parse(fecha);
            boolean isFestivo = festivoService.isFestivo(date);
            if (isFestivo) {
                return new ResponseEntity<>("La fecha " + fecha + " es un festivo.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("La fecha " + fecha + " no es un festivo.", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Fecha no válida.", HttpStatus.BAD_REQUEST);
        }
    }
}
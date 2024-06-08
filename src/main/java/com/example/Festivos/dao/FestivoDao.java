package com.example.Festivos.dao;

import com.example.Festivos.entity.Festivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivoDao extends JpaRepository<Festivo, Long> {
}

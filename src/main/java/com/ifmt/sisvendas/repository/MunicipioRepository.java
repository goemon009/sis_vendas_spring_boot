package com.ifmt.sisvendas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Municipio;

public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {

    List<Municipio> findByRegiaoIdOrderByNomeAsc(Integer idRegiao);

}
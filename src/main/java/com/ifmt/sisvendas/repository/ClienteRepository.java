package com.ifmt.sisvendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
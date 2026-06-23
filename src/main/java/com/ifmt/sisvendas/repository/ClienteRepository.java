package com.ifmt.sisvendas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifmt.sisvendas.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCnpj(String cnpj);
}

package com.fer.Clientes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fer.Clientes.model.Comida;

public interface ComidaRepository extends JpaRepository<Comida, Long> {
}

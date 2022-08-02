package com.fsbr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsbr.entities.Estados;


public interface EstadosRepository extends JpaRepository<Estados, String> {
}
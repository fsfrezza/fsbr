package com.fsbr.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsbr.entities.Cadastramento;

@Repository
public interface CadastramentoRepository extends JpaRepository<Cadastramento, Integer> {

	Optional<Cadastramento> findByCpf(String cpf);
	List<Cadastramento> findByNomeOrCpf(String nome, String cpf);
}
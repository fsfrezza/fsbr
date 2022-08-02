package com.fsbr.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fsbr.entities.Cadastramento;
import com.fsbr.repositories.CadastramentoRepository;

@RestController
public class CadastramentoController {

	@Autowired
	CadastramentoRepository cadastramentoRepository;

	@GetMapping("/pessoas")
	public List<Cadastramento> index() {
		return cadastramentoRepository.findAll();
	}

	@GetMapping("/pessoa/{id}")
	public Cadastramento pessoaById(@PathVariable int id) {
		return cadastramentoRepository.findById(id).orElse(null);
	}

	@GetMapping("/pessoa-cpf/{cpf}")
	public Cadastramento pessoaByCpf(@PathVariable String cpf) {
		return cadastramentoRepository.findByCpf(cpf).orElse(null);
	}

	@GetMapping("/pessoa-cpf-nome/{cpf}/{nome}")
	public List<Cadastramento> findByNomeOrCpf(@PathVariable String nome, @PathVariable String cpf) {
		nome = nome == null ? null : nome.replaceAll("_",  " ");
		return cadastramentoRepository.findByNomeOrCpf(nome, cpf);
	}

	@PostMapping("/pessoa")
	public Cadastramento create(@RequestBody Cadastramento pessoa) {
		return cadastramentoRepository.save(pessoa);
	}

	@PutMapping("/pessoa/{id}")
	public Cadastramento update(@PathVariable int id, @RequestBody Map<String, String> body) {
		Cadastramento pessoa = cadastramentoRepository.findById(id).orElse(null);
		pessoa.setNome(body.get("nome"));
		pessoa.setCpf(body.get("cpf"));
		pessoa.setCidade(body.get("cidade"));
		pessoa.setEstado(body.get("estado"));
		return cadastramentoRepository.save(pessoa);
	}

	@DeleteMapping("pessoa/{id}")
	public boolean delete(@PathVariable int id) {
		Cadastramento pessoa = cadastramentoRepository.findById(id).orElse(null);
		cadastramentoRepository.delete(pessoa);
		return true;
	}
}
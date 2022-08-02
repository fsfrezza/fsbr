package com.fsbr.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsbr.entities.Estados;
import com.fsbr.repositories.EstadosRepository;

@RestController
public class EstadosController {

	@Autowired
	EstadosRepository estadoRepository;

	@GetMapping("/estados")
	public List<Estados> getEstados() {
		return estadoRepository.findAll();
	}
}
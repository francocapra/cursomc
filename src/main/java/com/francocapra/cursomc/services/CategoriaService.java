package com.francocapra.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francocapra.cursomc.domain.Categoria;
import com.francocapra.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {

	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Instanciar automaticamente
	private CategoriaRepository repo;
	
	public Categoria buscar(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElse(null); 
		
	}
}

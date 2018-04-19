package com.francocapra.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.francocapra.cursomc.domain.Pedido;
import com.francocapra.cursomc.repositories.PedidoRepository;
import com.francocapra.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	//Declaração de dependencia,(Mecanismo de Injeção de dependencia, ou inversão de controle)
	@Autowired //Instanciar automaticamente
	private PedidoRepository repo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName() )); 
		
	}
}

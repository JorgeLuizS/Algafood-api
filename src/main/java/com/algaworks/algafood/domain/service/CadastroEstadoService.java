package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
	
	
	private static final String MSG_UF_EM_USO = "A UF com id = %d já está sendo utilizada.\n";


	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	
	public List<Estado>listar(){
		return estadoRepository.findAll();
	}
	
	
	
	public Estado buscar(Long id) {
		return estadoRepository.findById(id).orElse(null);
		
	}
	
	

	public Estado buscarOuFalhar(Long estadoId) {
		
		return estadoRepository.findById(estadoId)
				.orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));

	}
	
	
	
	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
		
	}
	
	
	
	@Transactional
	public void excluir(Long estadoId) {
		
		try {
		
			estadoRepository.deleteById(estadoId);
			estadoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw new EstadoNaoEncontradoException(estadoId);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_UF_EM_USO.formatted(estadoId));
		}
		
	}




}

package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	
	private static final String MSG_GRUPO_EM_USO = "Grupo com id = %d não pode ser removido, pois já está em uso.\n";;
	@Autowired
	GrupoRepository grupoRepository;
	
		
	public List<Grupo>listar(){
		return grupoRepository.findAll();
	}
	
	
	
	public Grupo buscar(Long id) {
		return grupoRepository.findById(id).orElse(null);
		
	}
	
	

	public Grupo buscarOuFalhar(Long estadoId) {
		
		return grupoRepository.findById(estadoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(estadoId));

	}
	
	
	
	@Transactional
	public Grupo salvar(Grupo estado) {
		return grupoRepository.save(estado);
		
	}
	
	
	
	@Transactional
	public void excluir(Long grupoId) {
		
		try {
		
			grupoRepository.deleteById(grupoId);
			grupoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(grupoId);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_GRUPO_EM_USO.formatted(grupoId));
		}
		
	}




}

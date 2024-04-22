package com.algaworks.algafood.domain.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
public class CadastroGrupoService {
	
	
	private static final String MSG_GRUPO_EM_USO = "Grupo com id = %d não pode ser removido, pois já está em uso.\n";
	
	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private CadastroPermissaoService cadastroPermissao;
		
	public List<Grupo>listar(){
		return grupoRepository.findAll();
	}
	
		

	public Grupo buscarOuFalhar(Long grupoId) {
		
		return grupoRepository.findById(grupoId)
				.orElseThrow(() -> new GrupoNaoEncontradoException(grupoId));

	}
	
	
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
		
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

	
	

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
		
		grupo.adicionarPermissao(permissao);
		
	}
	
	
	
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
		Grupo grupo = buscarOuFalhar(grupoId);
		Permissao permissao = cadastroPermissao.buscarOuFalhar(permissaoId);
		
		grupo.removerPermissao(permissao);
		
	}
	
}

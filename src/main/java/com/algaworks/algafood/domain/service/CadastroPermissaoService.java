package com.algaworks.algafood.domain.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@Service
public class CadastroPermissaoService {
	
	private static final String MSG_PERMISSAO_EM_USO = "A permissao de código %d está em uso por outra entidade";
	
	@Autowired
	PermissaoRepository permissaoRepository;
	
	
	
	public List<Permissao>listar(){
		return permissaoRepository.findAll();
	}
	
	
	
	public Permissao buscar(Long id) {
		return permissaoRepository.findById(id).orElse(null);
	}
	
	
	
	public Permissao buscarOuFalhar(Long permissaoId) {
		return permissaoRepository.findById(permissaoId)
				.orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
	}
	
	
	
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return permissaoRepository.save(permissao);
		
	}
	
	
	@Transactional
	public void excluir(Long permissaoId) {
		try {
			
			permissaoRepository.deleteById(permissaoId);
			permissaoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
					
			throw new PermissaoNaoEncontradaException(permissaoId);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
                    MSG_PERMISSAO_EM_USO.formatted(permissaoId));
		}
		
	}


}

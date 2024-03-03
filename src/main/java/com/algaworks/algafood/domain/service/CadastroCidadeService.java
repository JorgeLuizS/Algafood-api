package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
	
	private static final String MSG_CIDADE_EM_USO = "A cidade com código %d já está em uso por outra entidade.\n";
	

	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CadastroEstadoService cadastroEstado;
	
	
	
	
	
	public List<Cidade>listar(){
		return cidadeRepository.findAll();
	}
	
	
	
	public Cidade buscar(Long id) {
		return cidadeRepository.findById(id).orElse(null);
	}
	
	
	public Cidade buscarOuFalhar(Long cidadeId) {
		return cidadeRepository.findById(cidadeId)
				.orElseThrow(() -> new CidadeNaoEncontradaException(cidadeId));
	}
	
	
	@Transactional
	public Cidade salvar(Cidade cidade) {
		Estado estado = cadastroEstado.buscarOuFalhar(cidade.getEstado().getId());
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
		
	}
	
	
	@Transactional
	public void excluir(Long id) {
		try {
			
			cidadeRepository.deleteById(id);
			cidadeRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
					
			throw new CidadeNaoEncontradaException(id);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_CIDADE_EM_USO.formatted(id));
		}
	}
	
	
	
	

}

package com.algaworks.algafood.domain.service;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;

@Service
public class CadastroFormaPagamentoService {
	
	private static final String MSG_FORMA_PAGAMENTO_EM_USO = "A forma de pagamento de código %d já está em uso";
	
	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;
	
	
	
	
	public List<FormaPagamento>listar(){
		return formaPagamentoRepository.findAll();
	}
	
	
	
	public FormaPagamento buscar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId).orElse(null);
	}
	
	
	
	
	public FormaPagamento buscarOuFalhar(Long formaPagamentoId) {
		return formaPagamentoRepository.findById(formaPagamentoId)
				.orElseThrow(() -> new FormaPagamentoNaoEncontradaException(formaPagamentoId));
	}
	
	
	
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		
		return formaPagamentoRepository.save(formaPagamento);
		
	}

	
	@Transactional
	public void excluir(Long formaPagamentoId) {
		
		try {
			
			formaPagamentoRepository.deleteById(formaPagamentoId);
			formaPagamentoRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
					
			throw new FormaPagamentoNaoEncontradaException(formaPagamentoId);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_FORMA_PAGAMENTO_EM_USO.formatted(formaPagamentoId));
		}
		
		
	}


}

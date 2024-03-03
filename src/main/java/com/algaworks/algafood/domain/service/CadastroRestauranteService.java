package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	private static final String MSG_RESTAURANTE_EM_USO = "O restaurante com id = %d já está em uso.\n";

	@Autowired
	RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	

	@Autowired
	CadastroFormaPagamentoService cadastroFormasPagamento;
	
	public List<Restaurante>listar(){
		return restauranteRepository.findAll();
	}
	
	
	
	public Restaurante buscar(Long id) {
		return restauranteRepository.findById(id).orElse(null);
	}
	
	
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}
	
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidade.buscarOuFalhar(cidadeId);
		
		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);
				
		return restauranteRepository.save(restaurante);
		
	}
	
	
	@Transactional
	public void excluir(Long restauranteId) {
		
		try {
			
			restauranteRepository.deleteById(restauranteId);
			restauranteRepository.flush();
			
		}catch(EmptyResultDataAccessException e) {
					
			throw new RestauranteNaoEncontradoException(restauranteId);
		
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
                    MSG_RESTAURANTE_EM_USO.formatted(restauranteId));
		}
				
	}
	
	
	@Transactional
	public void ativar (Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.ativar();
		
		
	}
	
	
	@Transactional
	public void inativar (Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		
		restauranteAtual.inativar();
		
	}
	
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormasPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.removerFormaPagamento(formaPagamento);
	}
	
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormasPagamento.buscarOuFalhar(formaPagamentoId);
		
		restaurante.adicionarFormaPagamento(formaPagamento);
	}
	

}

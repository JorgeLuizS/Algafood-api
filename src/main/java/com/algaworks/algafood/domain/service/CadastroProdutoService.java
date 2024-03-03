package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
		
	private static final String MSG_PRODUTO_EM_USO = "O produto de id %d já está em uso";
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	
	
	public List<Produto>listar(){
		return produtoRepository.findAll();
	}
	
	
	
	public Produto buscar(Long produtoId) {
		return produtoRepository.findById(produtoId).orElse(null);
	}
	
	
	
	
	public Produto buscarOuFalhar(Long produtoId) {
		return produtoRepository.findById(produtoId)
				.orElseThrow(() -> new ProdutoNaoEncontradoException(produtoId));
	}
	
	
	
	@Transactional
	public Produto salvar(Produto Produto) {
		return produtoRepository.save(Produto);
		
	}

	
	@Transactional
	public void excluir(Long ProdutoId) {
		
		buscarOuFalhar(ProdutoId);
		
		try {
			produtoRepository.deleteById(ProdutoId);
			produtoRepository.flush();
			
		}catch(DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(MSG_PRODUTO_EM_USO.formatted(ProdutoId));
		}
		
		
	}


}

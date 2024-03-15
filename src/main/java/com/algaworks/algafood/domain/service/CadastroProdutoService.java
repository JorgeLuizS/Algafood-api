package com.algaworks.algafood.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CadastroProdutoService {
		
	@Autowired
	ProdutoRepository produtoRepository;
	
	
	
	public List<Produto>listar(){
		return produtoRepository.findAll();
	}
	
	
	
	public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId)
            .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }   
	
	
	
	@Transactional
	public Produto salvar(Produto Produto) {
		return produtoRepository.save(Produto);
		
	}

	
}

package com.algaworks.algafood.domain.exception;



public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	
	public ProdutoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	
	public ProdutoNaoEncontradoException(Long produtoId) {
		this("Não existe um produto com código %d".formatted(produtoId));
		
	}
	
	
	public ProdutoNaoEncontradoException(Long restauranteId, Long produtoId) {
        this("Não existe um cadastro de produto com código %d para o restaurante de código %d".formatted(
                produtoId, restauranteId));
    }
	
		

}

package com.algaworks.algafood.domain.exception;



public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	
	public PermissaoNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	
	
	public PermissaoNaoEncontradaException(Long cidadeId) {
		this("Não existe um cadastro de permissao com código %d".formatted(cidadeId));
		
	}
	
		

}

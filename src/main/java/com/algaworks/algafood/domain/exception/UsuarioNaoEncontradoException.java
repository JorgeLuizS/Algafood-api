package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException{

	private static final long serialVersionUID = 1L;


	public UsuarioNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	
	public UsuarioNaoEncontradoException(Long restauranteId) {
		this("Não existe um cadastro de usuario com código %d".formatted(restauranteId));
		
	}

}

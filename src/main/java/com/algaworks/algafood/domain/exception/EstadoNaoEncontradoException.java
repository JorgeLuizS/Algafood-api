package com.algaworks.algafood.domain.exception;



public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	
	
	public EstadoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	
	public EstadoNaoEncontradoException(Long estadoId) {
		this("Não existe um cadastro de  UF com código %d".formatted(estadoId));
		
	}
	
		

}

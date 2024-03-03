package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
	 
	RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso não encontrado"),
	RECURSO_EM_USO("/recurso-em-uso", "recurso em uso"),
	ERRO_NEGOCIO("/erro-negocio", "Violação de regra de negócio"), 
	MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"), 
	PARAMETRO_INVALIDO("/parametro-invalido", "Parâmetro inválido"), 
	ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sisema"), 
	DADOS_INVALIDOS("/dados-invalidos", "Dados inválidos");
	
	
	
	private String title;
	private String uri;
	
	ProblemType(String path, String title){
		this.uri = "https://algafood.com.br" + path;
		this.title = title;
	}

}

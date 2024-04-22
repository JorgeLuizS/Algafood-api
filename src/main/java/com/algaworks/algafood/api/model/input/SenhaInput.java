package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/*
 * Empregada na criação de um novo usuário
 */
@Setter
@Getter
public class SenhaInput {
	
	@NotBlank
	private String senhaAtual;
	
	@NotBlank
	private String senhaNova;
	    

}

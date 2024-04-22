package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

/*
 * Empregada na criação de um novo usuário
 */
@Setter
@Getter
public class UsuarioInput {
	
	@NotBlank
	private String nome;
	
	@NotBlank
	@Email
	private String email;
	    

}

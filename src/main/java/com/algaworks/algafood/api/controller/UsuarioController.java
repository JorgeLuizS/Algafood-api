package com.algaworks.algafood.api.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.UsuarioInputDisassembler;
import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.model.input.SenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioComSenhaInput;
import com.algaworks.algafood.api.model.input.UsuarioInput;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;
	
	@Autowired
	UsuarioModelAssembler usuarioModelAssembler;
	
	@Autowired
	UsuarioInputDisassembler usuarioInputDisassembler;
	
	
	@GetMapping
	public List<UsuarioModel>listar(){
		
		return usuarioModelAssembler.toCollectionModel(cadastroUsuario.listar()); 
		
	}
	
	
	@GetMapping("/{usuarioId}")
	public UsuarioModel buscar(@PathVariable Long usuarioId){
		
		Usuario usuario =  cadastroUsuario.buscarOuFalhar(usuarioId);
		
		return usuarioModelAssembler.toModel(usuario);
	}


	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UsuarioModel adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput){
		Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
		
		return  usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuario));
			
		
	}
	
	
	
	
	@PutMapping("/{usuarioId}")
	public UsuarioModel atualizar(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInput usuarioInput){
		
		Usuario usuarioAtual = cadastroUsuario.buscarOuFalhar(usuarioId);
		
		usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
		
		return usuarioModelAssembler.toModel(cadastroUsuario.salvar(usuarioAtual));
	}
	
	
	@PutMapping("/{usuarioId}/senha")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizarSenha(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInput senha){
		
		cadastroUsuario.alterarSenha(usuarioId, senha.getSenhaAtual(), senha.getSenhaNova());
	}
	
	
	
}

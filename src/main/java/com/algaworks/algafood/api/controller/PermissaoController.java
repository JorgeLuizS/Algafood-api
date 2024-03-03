package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;

@RestController
@RequestMapping("/permissoes")
public class PermissaoController {

	@Autowired
	private CadastroPermissaoService cadastroPermissao;

	
	
	
	@GetMapping
	public List<Permissao> listar() {

		return cadastroPermissao.listar();

	}
	
	

	@GetMapping("/{permissaoId}")
	public Permissao buscar(@PathVariable Long permissaoId) {

		return cadastroPermissao.buscarOuFalhar(permissaoId);


	}
	
	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Permissao adicionar(@RequestBody Permissao permissao) {

		permissao.setId(null);
		return cadastroPermissao.salvar(permissao);

	}
	
	

	@PutMapping("/{permissaoId}")
	public Permissao atualizar(@PathVariable Long permissaoId, @RequestBody Permissao permissao) {

		Permissao permissaoAtual = cadastroPermissao.buscarOuFalhar(permissaoId);

		BeanUtils.copyProperties(permissao, permissaoAtual, "id");

		return cadastroPermissao.salvar(permissaoAtual);

	}
	
	

	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long permissaoId) {
		
		cadastroPermissao.excluir(permissaoId);


	}

}

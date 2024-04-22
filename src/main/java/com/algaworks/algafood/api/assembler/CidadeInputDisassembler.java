package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;

@Component
public class CidadeInputDisassembler {
	
	
	@Autowired
	ModelMapper modelMapper;
	
	public Cidade toDomainObject(CidadeInput cidadeInput) {
        return modelMapper.map(cidadeInput, Cidade.class);
    }
	
	
	public void copyToDomainObject(CidadeInput cidadeInput, Cidade cidade) {
		/*
		 * Para evitar:
		 * org.hibernate.HibernateException: identifier of an instance of com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 4
		 */
		cidade.setEstado(new Estado());	//Criar uma nova instância, não gerenciada pelo JPA, de Cozinha.
		
		modelMapper.map(cidadeInput, cidade);
	}

}

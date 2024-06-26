package com.algaworks.algafood.api.controller;

import static java.util.Map.entry;    

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;


@RestController
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private EmissaoPedidoService emissaoPedido;
	
	@Autowired
	private PedidoModelAssembler pedidoModelAssembler;
	
	@Autowired
	private PedidoResumoModelAssembler pedidoResumoModelAssembler;
	

	@Autowired
	private PedidoInputDisassembler pedidoInputDisassembler;
	
	
	
	@GetMapping
	public Page<PedidoResumoModel>pesquisar(PedidoFilter filtro, @PageableDefault(size = 10)Pageable pageable){
		pageable = traduzirPageable(pageable);
		
		Page<Pedido>pedidosPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(filtro), pageable);
		List<PedidoResumoModel>pedidosResumoModel = pedidoResumoModelAssembler.toCollectionModel(pedidosPage.getContent());
		Page<PedidoResumoModel>pedidoResumoModelPage = new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
		
		return pedidoResumoModelPage; 
		
	}
	
	
	private Pageable traduzirPageable(Pageable apiPagealble) {
		Map<String,String>mapeamento = Map.ofEntries(
		entry("codigo", "codigo"),
		entry("restaurante", "restaurante"),
		entry("restaurante.id", "restaurante.id"),
		entry("restaurante.nome", "restaurante.nome"),
		entry("valorTotal", "valorTotal"),
		entry("subtotal","subtotal"),
		entry("taxaFrete", "taxaFrete"),
		entry("status", "status"),
		entry("dataCriacao", "dataCriacao"),
		entry("cliente", "cliente"),
		entry("cliente.id", "cliente.id"),
		entry("cliente.nome", "cliente.nome")
		);
		
		return PageableTranslator.translate(apiPagealble, mapeamento);
	}
	
	
//	private Pageable traduzirPageable(Pageable apiPagealble) {
//		var mapeamento = ImmutableMap.of(
//				"codigo", "codigo",
//				"restaurante.nome", "restaurante.nome",
//				"cliente.id", "cliente.id",
//				"cliente.nome", "cliente.nome",
//				"valorTotal", "valorTotal"
//			);
//		
//		return PageableTranslator.translate(apiPagealble, mapeamento);
//	}
	
	
//Referência: 13.2. Limitando os campos retornados pela API com @JsonFilter do Jackson
//	@GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String campos){
//		List<Pedido>pedidos = pedidoRepository.findAll();
//		List<PedidoResumoModel>pedidosModel = pedidoResumoModelAssembler.toCollectionModel(pedidos);
//		
//		MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidosModel);
//		
//		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
//	filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());
//	
//	if(StringUtils.isNotBlank(campos)) {
//		filterProvider.addFilter("pedidoFilter", 
//				SimpleBeanPropertyFilter.filterOutAllExcept(campos.split(",")));
//	}
//		
//		pedidosWrapper.setFilters(filterProvider);
//		
//		
//		return pedidosWrapper;
//	}
	
	
	
	
	@GetMapping("/{codigoPedido}")
	public PedidoModel buscar(@PathVariable String codigoPedido){
		
		Pedido pedido =  emissaoPedido.buscarOuFalhar(codigoPedido);
		
		return pedidoModelAssembler.toModel(pedido);
	}
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
	    try {
	        Pedido novoPedido = pedidoInputDisassembler.toDomainObject(pedidoInput);

	        // TODO pegar usuário autenticado
	        novoPedido.setCliente(new Usuario());
	        novoPedido.getCliente().setId(1L);

	        novoPedido = emissaoPedido.emitir(novoPedido);

	        return pedidoModelAssembler.toModel(novoPedido);
	    } catch (EntidadeNaoEncontradaException e) {
	        throw new NegocioException(e.getMessage(), e);
	    }
	}


	
}

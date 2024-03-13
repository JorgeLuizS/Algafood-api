package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.ItemPedido;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;

@Service
public class EmissaoPedidoService {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	CadastroProdutoService cadastroProduto;
	
	@Autowired
	CadastroRestauranteService cadastroRestaurante;
	
	@Autowired
	CadastroFormaPagamentoService cadastroFormaPagamento;
	
	@Autowired
	CadastroCidadeService cadastroCidade;
	
	@Autowired
	CadastroUsuarioService cadastroUsuario;
	
	
	
	public List<Pedido>listar(){
		return pedidoRepository.findAll();
	}
	
	
		public Pedido buscarOuFalhar(String codigoPedido) {
	    return pedidoRepository.findByCodigo(codigoPedido)
	        .orElseThrow(() -> new PedidoNaoEncontradoException(codigoPedido));
	}
	
	
	@Transactional
	public Pedido emitir(Pedido pedido) {
		validarPedido(pedido);
		validarItens(pedido);
		
		pedido.calcularValorTotal();
		
		return pedidoRepository.save(pedido);
		
	}


	
	private void validarItens(Pedido pedido) {
		List<ItemPedido>itens = pedido.getItens();
		
		itens.stream()
		.forEach(item -> {
			Produto produto = cadastroProduto.buscarOuFalhar(pedido.getRestaurante().getId(), item.getProduto().getId());
			
			item.setPedido(pedido);
			item.setProduto(produto);
			item.setPrecoUnitario(produto.getPreco());
		});
	}


	
	private void validarPedido(Pedido pedido) {
		Cidade cidade = cadastroCidade.buscarOuFalhar(pedido.getEnderecoEntrega().getCidade().getId());
		Usuario cliente = cadastroUsuario.buscarOuFalhar(pedido.getCliente().getId());
		Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(pedido.getRestaurante().getId());
		FormaPagamento formaPagamento = cadastroFormaPagamento.buscarOuFalhar(pedido.getFormaPagamento().getId());
		
		pedido.getEnderecoEntrega().setCidade(cidade);
		pedido.setCliente(cliente);
		pedido.setRestaurante(restaurante);
		pedido.setFormaPagamento(formaPagamento);
		pedido.definirFrete();
		
		if(restaurante.naoAceitaFormaPagamento(formaPagamento)) {
			throw new NegocioException("O restaurante %d não aceita a forma de pagamento %d".formatted(restaurante.getId(), formaPagamento.getId()));
		}
		
	}
	
}

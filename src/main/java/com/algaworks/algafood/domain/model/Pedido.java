package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	private BigDecimal subtotal;
	
	private BigDecimal taxaFrete;
	
	private BigDecimal valorTotal;
	
	
	@Embedded
	Endereco enderecoEntrega;
	

	@Column(nullable = false)
	private StatusPedido status;
	
	
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCriacao;
	
	
	@Column(nullable = true, columnDefinition = "datetime")
	private OffsetDateTime dataConfirmacao;
	
	
	@Column(nullable = true, columnDefinition = "datetime")
	private OffsetDateTime dataCancelamento;
	
	
	@Column(nullable = true, columnDefinition = "datetime")
	private OffsetDateTime dataEntrega;
	
	
	@ManyToOne
	@JoinColumn(name = "forma_pagamento_id", nullable = false)
	FormaPagamento formaPagamento;
	
	
	@ManyToOne
	@JoinColumn(name = "restaurante_id", nullable = false)
	Restaurante restaurante;
	
	
	@ManyToOne
	@JoinColumn(name = "usuario_cliente_id", nullable = false)//TODO: alterar no BD o nome da coluna.
	Usuario cliente;
	
	
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido>itens = new ArrayList<>();
	

}

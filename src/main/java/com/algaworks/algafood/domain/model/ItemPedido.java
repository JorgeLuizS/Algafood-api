package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {
	
	@EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private BigDecimal precoUnitario;
	
	@Column(nullable = false)
	private BigDecimal precoTotal;
	
	@Column(nullable = true)
	private String observacao;
	
	@ManyToOne
	@JoinColumn(name = "pedido_id", nullable = false)
	Pedido pedido;
	
	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	Produto produto;
	
	
	

}

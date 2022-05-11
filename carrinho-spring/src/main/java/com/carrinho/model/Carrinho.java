package com.carrinho.model;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//import org.springframework.data.annotation.Id;

@Entity
public class Carrinho {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column (name = "ref_pedido")
	private String pedido;
	@Column (name = "data_compra")
	private String dataCompra;
	@Column (name = "nome_produto")
	private String produto;
	@Column (name = "preco_produto")
	private double preco;
	@Column (name = "quantidade_produto")
	private String qtd;
	@Column (name = "valor_total")
	private double valorTotal;
		
	public Carrinho() {}
	
	public Carrinho(long id, String pedido, String dataCompra, String produto, double preco, String qtd,
			double valorTotal) {
		this.id = id;
		this.pedido = pedido;
		this.dataCompra = dataCompra;
		this.produto = produto;
		this.preco = preco;
		this.qtd = qtd;
		this.valorTotal = valorTotal;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}


}
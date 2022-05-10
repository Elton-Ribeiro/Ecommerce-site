package com.carrinho.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;


@Entity
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column (name = "ref_pedido")
	private String pedido;
	@Column (name = "nome_produto")
	private String produto;
	@Column (name = "preco_produto")
	private String preco;
	@Column (name = "quantidade_produto")
	private String qtd;
	@Column (name = "valor_total")
	private String valorTotal;
	
	public Cart() {}

	public Cart(long id, String pedido, String produto, String preco, String qtd, String valorTotal) {
		super();
		this.id = id;
		this.pedido = pedido;
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

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getQtd() {
		return qtd;
	}

	public void setQtd(String qtd) {
		this.qtd = qtd;
	}

	public String getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(String valorTotal) {
		this.valorTotal = valorTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, pedido, preco, produto, qtd, valorTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		return id == other.id && Objects.equals(pedido, other.pedido) && Objects.equals(preco, other.preco)
				&& Objects.equals(produto, other.produto) && Objects.equals(qtd, other.qtd)
				&& Objects.equals(valorTotal, other.valorTotal);
	}

	@Override
	public String toString() {
		return "Cart [id=" 
				+ id 
				+ ", pedido=" 
				+ pedido 
				+ ", produto=" 
				+ produto 
				+ ", preco=" 
				+ preco 
				+ ", qtd=" 
				+ qtd
				+ ", valorTotal=" 
				+ valorTotal 
				+ "]";
	}


	

}

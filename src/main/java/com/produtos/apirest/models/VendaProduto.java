package com.produtos.apirest.models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class VendaProduto {
	
	@EmbeddedId
	VendaProdutoKey id;
	
	@ManyToOne
    @MapsId("produtoId")
    @JoinColumn(name = "produto_id")
	Produto produto;
	
	@ManyToOne
    @MapsId("vendaId")
    @JoinColumn(name = "venda_id")
	Venda venda;
	
	int qnt;

	public VendaProdutoKey getId() {
		return id;
	}

	public void setId(VendaProdutoKey id) {
		this.id = id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public VendaProduto() {
		
	}
	
	public VendaProduto(VendaProdutoKey id, int qnt) {
		this.id = id;
		this.qnt = qnt;
	}
	
	
	
}

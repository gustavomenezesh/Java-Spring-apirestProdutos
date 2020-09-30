package com.produtos.apirest.models;

public class ItemVenda {
	
	Produto produto;
	
	int qnt;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public ItemVenda(Produto produto, int qnt) {
		this.produto = produto;
		this.qnt = qnt;
	}
	
	

}

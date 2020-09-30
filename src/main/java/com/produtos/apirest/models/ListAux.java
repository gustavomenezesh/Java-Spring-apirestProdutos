package com.produtos.apirest.models;

import java.util.ArrayList;

public class ListAux {
	
	Long id;
	
	ArrayList<VendaProdutoQnt> itens;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ArrayList<VendaProdutoQnt> getItens() {
		return itens;
	}

	public void setItens(ArrayList<VendaProdutoQnt> itens) {
		this.itens = itens;
	}

	public ListAux(Long id, ArrayList<VendaProdutoQnt> itens) {
		this.id = id;
		this.itens = itens;
	}
	
	

}

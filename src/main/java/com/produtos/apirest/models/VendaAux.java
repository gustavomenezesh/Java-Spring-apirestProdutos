package com.produtos.apirest.models;

import java.util.List;

public class VendaAux {

	Venda venda;
	
	List<Long> itens; 

	public List<Long> getItens() {
		return itens;
	}

	public void setItens(List<Long> itens) {
		this.itens = itens;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public VendaAux(Venda venda, List<Long> itens) {
		this.venda = venda;
		this.itens = itens;
	}

	
}

package com.produtos.apirest.models;

import java.util.ArrayList;

public class ModelVenda {
	
	Venda venda;
	
	ArrayList<ItemVenda> itens;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public ArrayList<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(ArrayList<ItemVenda> itens) {
		this.itens = itens;
	}

	public ModelVenda(Venda venda, ArrayList<ItemVenda> itens) {
		this.venda = venda;
		this.itens = itens;
	}
	
	

}

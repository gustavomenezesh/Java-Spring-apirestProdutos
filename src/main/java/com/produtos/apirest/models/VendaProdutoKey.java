package com.produtos.apirest.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VendaProdutoKey implements Serializable{
	
	@Column(name="produto_id")
	Long produtoId;
	
	@Column(name="venda_id")
	Long vendaId;

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public Long getVendaId() {
		return vendaId;
	}

	public void setVendaId(Long vendaId) {
		this.vendaId = vendaId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produtoId == null) ? 0 : produtoId.hashCode());
		result = prime * result + ((vendaId == null) ? 0 : vendaId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaProdutoKey other = (VendaProdutoKey) obj;
		if (produtoId == null) {
			if (other.produtoId != null)
				return false;
		} else if (!produtoId.equals(other.produtoId))
			return false;
		if (vendaId == null) {
			if (other.vendaId != null)
				return false;
		} else if (!vendaId.equals(other.vendaId))
			return false;
		return true;
	}
	
	

	public VendaProdutoKey() {
		
	}

	public VendaProdutoKey(Long produtoId, Long vendaId) {
		this.produtoId = produtoId;
		this.vendaId = vendaId;
	}
	
	

}

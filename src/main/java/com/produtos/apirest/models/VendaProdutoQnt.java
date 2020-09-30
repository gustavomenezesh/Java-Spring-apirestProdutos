package com.produtos.apirest.models;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VendaProdutoQnt {

	Long idVenda;
	BigDecimal valor;
	String date;
	Long idProduto;
	String nome;
	int qnt;
	
	public Long getIdVenda() {
		return idVenda;
	}
	public void setIdVenda(Long idVenda) {
		this.idVenda = idVenda;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Long getIdProduto() {
		return idProduto;
	}
	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getQnt() {
		return qnt;
	}
	public void setQnt(int qnt) {
		this.qnt = qnt;
	}
	public VendaProdutoQnt(Long idVenda, BigDecimal valor, String date, Long idProduto, String nome, int qnt) {
		this.idVenda = idVenda;
		this.valor = valor;
		this.date = date;
		this.idProduto = idProduto;
		this.nome = nome;
		this.qnt = qnt;
	}
	@Override
	public String toString() {
		return "VendaProdutoQnt [idVenda=" + idVenda + ", valor=" + valor + ", date=" + date + ", idProduto="
				+ idProduto + ", nome=" + nome + ", qnt=" + qnt + "]";
	}
	
	
	
}

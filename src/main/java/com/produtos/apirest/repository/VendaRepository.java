package com.produtos.apirest.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.produtos.apirest.models.Venda;
import com.produtos.apirest.models.VendaProdutoQnt;

public interface VendaRepository extends JpaRepository<Venda, Long>{
	
	Venda findById(long id);
	
	@Query(value="SELECT * FROM tb_venda WHERE date >= ?1 ", nativeQuery=true)
	List<Venda> listByDate(String date);
	
	@Query(value="select venda_id, v.valor, v.date, produto_id, p.nome, qnt from venda_produto inner join tb_venda as v on venda_id=v.id inner join tb_produto as p on produto_id=p.id", nativeQuery=true)
	List<VendaProdutoQnt> listItens();

}

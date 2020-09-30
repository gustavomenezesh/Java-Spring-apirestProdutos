package com.produtos.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.produtos.apirest.models.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

	Produto findById(long id);
	
	@Query(value="SELECT * FROM tb_produto p WHERE p.nome LIKE %?1%", nativeQuery=true)
	List<Produto> findByName(String nome);
	
}

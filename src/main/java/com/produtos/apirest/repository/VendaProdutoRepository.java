package com.produtos.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.produtos.apirest.models.VendaProduto;

public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Long>{

}

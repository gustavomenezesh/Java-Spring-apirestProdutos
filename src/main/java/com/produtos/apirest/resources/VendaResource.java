package com.produtos.apirest.resources;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.produtos.apirest.models.DataVendas;
import com.produtos.apirest.models.ItemVenda;
import com.produtos.apirest.models.ListAux;
import com.produtos.apirest.models.Produto;
import com.produtos.apirest.models.Venda;
import com.produtos.apirest.models.ModelVenda;
import com.produtos.apirest.models.VendaAux;
import com.produtos.apirest.models.VendaProduto;
import com.produtos.apirest.models.VendaProdutoKey;
import com.produtos.apirest.models.VendaProdutoQnt;
import com.produtos.apirest.repository.ProdutoRepository;
import com.produtos.apirest.repository.VendaProdutoRepository;
import com.produtos.apirest.repository.VendaRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API REST Produtos")
@CrossOrigin(origins="*")
public class VendaResource{

	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	VendaProdutoRepository vendaProdutoRepository;
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/vendas")
	@ApiOperation(value="Retorna uma lista de vendas")
	public List<Venda> listarVendas(){
		return vendaRepository.findAll();
	}
	
	@GetMapping("/vendas/{id}")
	@ApiOperation(value="Retorna uma venda específica")
	public Venda vendasById(@PathVariable(value="id") long id){
		return vendaRepository.findById(id);
	}
	
	@PostMapping("/venda")
	@ApiOperation(value="Salva uma nova venda")
	public Venda salvaVenda(@RequestBody VendaAux vendaAux) {
		
		Venda vendaRealizada = vendaRepository.save(vendaAux.getVenda());
		Long idVenda = vendaRealizada.getId();
		
		for(int i = 0; i < vendaAux.getItens().size(); i=i+2) {
			VendaProdutoKey vendaProdutoKey = new VendaProdutoKey(vendaAux.getItens().get(i), idVenda);
			System.out.println(vendaProdutoKey.getVendaId()+" "+vendaProdutoKey.getProdutoId());
			VendaProduto vendaProduto = new VendaProduto(vendaProdutoKey, (int)(long)vendaAux.getItens().get(i+1));		
			jdbcTemplate.update("INSERT INTO venda_produto (produto_id, venda_id, qnt) VALUES (?, ?, ?)", vendaProduto.getId().getProdutoId(), vendaProduto.getId().getVendaId(), vendaProduto.getQnt());
		}
		
		return vendaRealizada;
	}
	
	@DeleteMapping("/venda")
	@ApiOperation(value="Deleta uma venda")
	public void deletaVenda(@RequestBody Venda venda) {
		jdbcTemplate.update("DELETE FROM venda_produto WHERE venda_id=?", venda.getId());
		vendaRepository.delete(venda);
	}
	
	@DeleteMapping("/DelVendaByPeriod")
	@ApiOperation(value="Deleta uma venda")
	public void deletaVendaPeriodo(@RequestBody DataVendas data) {
		jdbcTemplate.query("SELECT * FROM tb_venda WHERE date >= ? and date <= ?",new Object[] { data.getInicio(), data.getFim() }, (rs, rowNum)-> new Venda(rs.getLong("id"), rs.getBigDecimal("valor"),
				LocalDate.parse(rs.getString("date").split(" ")[0]))).forEach(venda->{
					jdbcTemplate.update("DELETE FROM venda_produto WHERE venda_id=?", venda.getId());
					vendaRepository.delete(venda);
				});;
	}
	
	@GetMapping("/vendasByDate/{date}")
	@ApiOperation(value="Retorna uma lista de vendas de um certo periodo")
	public List<Venda> vendasByName(@PathVariable(value="date") String date){
		return vendaRepository.listByDate(date);
	}
	
	@GetMapping("/vendasItem")
	@ApiOperation(value="Retorna uma lista de vendas com seus itens")
	public List<ModelVenda> listAll(){
		
		ArrayList<ListAux> listAux = new ArrayList<ListAux>();
		List<ModelVenda> modelsVenda = new ArrayList<ModelVenda>();
		
		
		jdbcTemplate.query("select venda_id, v.valor, v.date, produto_id, p.nome, qnt from venda_produto inner join tb_venda as v on venda_id=v.id inner join tb_produto as p on produto_id=p.id", (rs, rowNum)-> new VendaProdutoQnt(rs.getLong("venda_id"),
				rs.getBigDecimal("valor"), rs.getString("date"), rs.getLong("produto_id"), rs.getString("nome"), rs.getInt("qnt"))).forEach(vendaItem -> {
			
					boolean flag = false;
					for(int i = 0; i < listAux.size(); i++) {
						if(vendaItem.getIdVenda() == listAux.get(i).getId()) {
							listAux.get(i).getItens().add(vendaItem);
							flag = true;
						}
					}
					if(!flag) {
						ListAux aux = new ListAux(vendaItem.getIdVenda(), new ArrayList<VendaProdutoQnt>());
						aux.getItens().add(vendaItem);
						listAux.add(aux);
					}
					
				});
		
		for(int i = 0; i < listAux.size(); i++) {
			ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>(); 
			LocalDate date = LocalDate.parse(listAux.get(i).getItens().get(0).getDate().split(" ")[0]);
			Venda venda = new Venda(listAux.get(i).getItens().get(0).getIdVenda(), listAux.get(i).getItens().get(0).getValor(), date);
			
			for(int j = 0; j < listAux.get(i).getItens().size(); j++) {		
				ItemVenda item = new ItemVenda(new Produto(listAux.get(i).getItens().get(j).getIdProduto(), listAux.get(i).getItens().get(j).getNome(), null, null), listAux.get(i).getItens().get(j).getQnt());
				itens.add(item);
			}
			ModelVenda model = new ModelVenda(venda, itens);
			modelsVenda.add(model);
		}
		
		return modelsVenda;
	}
	
	@GetMapping("/vendasItensDate")
	@ApiOperation(value="Retorna uma lista de vendas com seus itens")
	public List<ModelVenda> listByDate(@RequestBody DataVendas data){
		
		ArrayList<ListAux> listAux = new ArrayList<ListAux>();
		List<ModelVenda> modelsVenda = new ArrayList<ModelVenda>();
		
		
		jdbcTemplate.query("select venda_id, v.valor, v.date, produto_id, p.nome, qnt from venda_produto inner join tb_venda as v on venda_id=v.id inner join tb_produto as p on produto_id=p.id where v.date >= ? and v.date <= ?",new Object[] { data.getInicio(), data.getFim() }, (rs, rowNum)-> new VendaProdutoQnt(rs.getLong("venda_id"),
				rs.getBigDecimal("valor"), rs.getString("date"), rs.getLong("produto_id"), rs.getString("nome"), rs.getInt("qnt"))).forEach(vendaItem -> {
					
					boolean flag = false;
					for(int i = 0; i < listAux.size(); i++) {
						if(vendaItem.getIdVenda() == listAux.get(i).getId()) {
							listAux.get(i).getItens().add(vendaItem);
							flag = true;
						}
					}
					if(!flag) {
						ListAux aux = new ListAux(vendaItem.getIdVenda(), new ArrayList<VendaProdutoQnt>());
						aux.getItens().add(vendaItem);
						listAux.add(aux);
					}
					
				});
		
		for(int i = 0; i < listAux.size(); i++) {
			ArrayList<ItemVenda> itens = new ArrayList<ItemVenda>(); 
			LocalDate date = LocalDate.parse(listAux.get(i).getItens().get(0).getDate().split(" ")[0]);
			Venda venda = new Venda(listAux.get(i).getItens().get(0).getIdVenda(), listAux.get(i).getItens().get(0).getValor(), date);
			
			for(int j = 0; j < listAux.get(i).getItens().size(); j++) {		
				ItemVenda item = new ItemVenda(new Produto(listAux.get(i).getItens().get(j).getIdProduto(), listAux.get(i).getItens().get(j).getNome(), null, null), listAux.get(i).getItens().get(j).getQnt());
				itens.add(item);
			}
			ModelVenda model = new ModelVenda(venda, itens);
			modelsVenda.add(model);
		}
		
		return modelsVenda;
	}
}

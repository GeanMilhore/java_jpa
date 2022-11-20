package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PedidoDao {

	private EntityManager em;

	public PedidoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Pedido pedido) {
		this.em.persist(pedido);
	}	
	
	public BigDecimal valorTotalVendido() {
		String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
		return em.createQuery(jpql, BigDecimal.class).getSingleResult();
	}
	
	public List<RelatorioDeVendasVo> relatorioDeVendas(){
		String jpql = "SELECT new br.com.alura.loja.vo.RelatorioDeVendasVo "
				+ "("
				+ "produto.nome , "
				+ "SUM(item.quantidade) , "
				+ "MAX(pedido.data)"
				+ ") "
				+ "FROM Pedido pedido "
				+ "JOIN pedido.itens item "
				+ "JOIN item.produto produto "
				+ "GROUP BY produto.nome ";
		return em.createQuery(jpql, RelatorioDeVendasVo.class ).getResultList();
		
	}
	
	public Pedido buscarPedidoComCliente(Long id) {
		return em.createQuery
				("SELECT p FROM Pedido p "
				+ "JOIN FETCH p.cliente c "
				+ "WHERE p.id = :id",
				Pedido.class)
				.setParameter("id", id)
				.getSingleResult();
	}
	
	public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro){
		String jpql = "SELECT p FROM Produto p WHERE 1=1 ";
		
		if(nome != null && !jpql.isEmpty()) {
			jpql.concat("AND p.nome = :nome ");
		}
		
		if(preco != null) {
			jpql.concat("AND p.preco = :preco ");
		}
		
		if(dataCadastro != null ) {
			jpql.concat("AND p.dataCadastro = :dataCadastro ");
		}
		
		TypedQuery<Produto> query = em.createQuery(jpql, Produto.class);
		
		if(nome != null && !jpql.isEmpty()) query.setParameter("nome", nome);
		if(preco != null) query.setParameter("preco", preco);
		if(dataCadastro != null ) query.setParameter("dataCadastro", dataCadastro);
		
		return query.getResultList();
	}
}




















package br.com.alura.loja.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Produto;

public class ProdutoDao {

	private EntityManager em;

	public ProdutoDao(EntityManager em) {
		this.em = em;
	}
	
	public void cadastrar(Produto produto) {
		this.em.persist(produto);
	}	
	
	public Produto buscarPorId(Long id) {
		return em.find(Produto.class, id);
	} 
	
	public List<Produto> buscarTodos(){
		String jpql = "SELECT p FROM Produto p ";
		return em.createQuery(jpql, Produto.class).getResultList();
	}
	
	// namedParameter
	public List<Produto> buscarPorNome(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", nome)
				.getResultList();
	}
	
	public List<Produto> buscarPorNomeDaCategoria(String categoria){
		String jpql = "SELECT p FROM Produto p where p.categoria.nome = :nome";
		return em.createQuery(jpql, Produto.class)
				.setParameter("nome", categoria)
				.getResultList();
	}
	
	public BigDecimal buscarPrecoProduto(String nome){
		String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = :nome";
		return em.createQuery(jpql, BigDecimal.class)
				.setParameter("nome", nome)
				.getSingleResult();
	}
	
	
	public List<Produto> buscarPorNomeOrdinalParameter(String nome){
		String jpql = "SELECT p FROM Produto p WHERE p.nome = ?1";
		return em.createQuery(jpql, Produto.class)
				.setParameter(1, nome)
				.getResultList();
	}
}

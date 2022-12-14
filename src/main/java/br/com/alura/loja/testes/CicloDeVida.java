package br.com.alura.loja.testes;

import javax.persistence.EntityManager;

import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.util.JPAUtil;

public class CicloDeVida {

	public static void main(String[] args) {
		Categoria celulares = new Categoria("CELULARES");
		
		EntityManager em = JPAUtil.getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(celulares);
		celulares.getId().setNome("XPTO");
		
		em.flush();
		em.clear();
		
		celulares = em.merge(celulares);
		celulares.getId().setNome("Cellphone");
		em.flush();
		em.clear();
		em.remove(celulares);
		em.flush();
	}
}

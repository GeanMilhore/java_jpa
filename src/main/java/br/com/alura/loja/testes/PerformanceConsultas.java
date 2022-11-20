package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDao;
import br.com.alura.loja.dao.ClienteDao;
import br.com.alura.loja.dao.PedidoDao;
import br.com.alura.loja.dao.ProdutoDao;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.ItemPedido;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendasVo;

public class PerformanceConsultas {

	public static void main(String[] args) {
		popularBancoDeDados();
		cadastrarAndTestarPedidos();
		System.out.println("=========================================IMPORTANTE==================================");
		EntityManager em = JPAUtil.getEntityManager();
		
		Pedido pedido = em.find(Pedido.class, 1l);
		System.out.println(pedido.getCliente());
	}

	private static void cadastrarAndTestarPedidos() {
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		Produto produto = produtoDao.buscarPorId(1l);
		Produto produto2 = produtoDao.buscarPorId(2l);
		Produto produto3 = produtoDao.buscarPorId(3l);
		
		Cliente cliente = clienteDao.buscarPorId(1l);

		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente);	
		pedido.adicionarItem(new ItemPedido(10, pedido, produto));
		
		Pedido pedido2 = new Pedido(cliente);
		pedido2.adicionarItem(new ItemPedido(40, pedido2, produto2));
		pedido2.adicionarItem(new ItemPedido(2, pedido2, produto3));
		
		Pedido pedido3 = new Pedido(cliente);
		pedido3.adicionarItem(new ItemPedido(20, pedido3, produto3));

		PedidoDao pedidoDao = new PedidoDao(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		pedidoDao.cadastrar(pedido3);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		
		System.out.println("VALOR TOTAL: " + totalVendido);
		
		List<RelatorioDeVendasVo> relatorio = pedidoDao.relatorioDeVendas();
		
		relatorio.forEach(System.out::println);
		
		em.close();
	}
	

	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria eletronico = new Categoria("ELETRONICO");
		Categoria eletrodomestico = new Categoria("ELETRODOMESTICOS");

		Cliente cliente = new Cliente("Rodrigo", "123456");
		
		Produto celular = new Produto("Xiaomi Redmi","Celular com 8 gb de ram",new BigDecimal("1250"),celulares);
		Produto videoGame = new Produto("XBOX", "Xbox 360", new BigDecimal(2500), eletronico);
		Produto microondas = new Produto("Microondas", "microondas forte", new BigDecimal(600), eletrodomestico);
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDao produtoDao = new ProdutoDao(em);
		CategoriaDao categoriaDao = new CategoriaDao(em);
		ClienteDao clienteDao = new ClienteDao(em);
		
		em.getTransaction().begin();	
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(eletronico);
		categoriaDao.cadastrar(eletrodomestico);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videoGame);
		produtoDao.cadastrar(microondas);
		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}
}

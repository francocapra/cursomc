package com.francocapra.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.francocapra.cursomc.domain.Categoria;
import com.francocapra.cursomc.domain.Cidade;
import com.francocapra.cursomc.domain.Cliente;
import com.francocapra.cursomc.domain.Endereco;
import com.francocapra.cursomc.domain.Estado;
import com.francocapra.cursomc.domain.ItemPedido;
import com.francocapra.cursomc.domain.Pagamento;
import com.francocapra.cursomc.domain.PagamentoComBoleto;
import com.francocapra.cursomc.domain.PagamentoComCartao;
import com.francocapra.cursomc.domain.Pedido;
import com.francocapra.cursomc.domain.Produto;
import com.francocapra.cursomc.domain.enums.EstadoPagamento;
import com.francocapra.cursomc.domain.enums.TipoCliente;
import com.francocapra.cursomc.repositories.CategoriaRepository;
import com.francocapra.cursomc.repositories.CidadeRepository;
import com.francocapra.cursomc.repositories.ClienteRepository;
import com.francocapra.cursomc.repositories.EstadoRepository;
import com.francocapra.cursomc.repositories.ItemPedidoRepository;
import com.francocapra.cursomc.repositories.PagamentoRepository;
import com.francocapra.cursomc.repositories.PedidoRepository;
import com.francocapra.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private com.francocapra.cursomc.repositories.EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

//		Categoria e Produto
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 1000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

//		Estado e Cidade
		Estado est1 = new Estado(null, "Rio Grande do Sul");

		Cidade c1 = new Cidade(null, "Porto Alegre", est1);
		Cidade c2 = new Cidade(null, "Canoas", est1);

		est1.getCidades().addAll(Arrays.asList(c1, c2));

		// #First INSERT Estado
		estadoRepository.saveAll(Arrays.asList(est1));
		// #After INSERT Cidade
		cidadeRepository.saveAll(Arrays.asList(c1, c2));

//		Cliente e Endereco
		Cliente cli1 = new Cliente(null, "Maria Silva", "email@dominio.com.br", "123456789", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		this.clienteRepository.saveAll(Arrays.asList(cli1));
		this.enderecoRepository.saveAll(Arrays.asList(e1, e2));

//		Pedido e Pagamento
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);

		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pgto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
//		Item de Pedido
		ItemPedido ip1 = new ItemPedido(ped1, p1 , 200.00 , 1, 2000.00 );
		ItemPedido ip2 = new ItemPedido(ped1, p3 , 0.00 , 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2 , 100.00 , 2, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3) );		
		
				
	}
}

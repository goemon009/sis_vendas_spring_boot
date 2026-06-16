package com.ifmt.sisvendas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.model.ItemPedidoCliente;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@RestController
@RequestMapping("/pedidos-cliente")
public class PedidoClienteController {

    private final PedidoClienteRepository repository;
    private final ItemPedidoClienteRepository itemRepository;
    private final ProdutoRepository produtoRepository;
    private final ComissaoRepository comissaoRepository;

    public PedidoClienteController(
            PedidoClienteRepository repository,
            ItemPedidoClienteRepository itemRepository,
            ProdutoRepository produtoRepository,
            ComissaoRepository comissaoRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
        this.comissaoRepository = comissaoRepository;
    }

    @GetMapping("/{id}/detalhes")
    public Map<String, Object> buscarPedidoComItens(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        List<ItemPedidoCliente> itens
                = itemRepository.findByPedidoClienteIdPedidoCliente(id);

        return Map.of(
                "pedido", pedido,
                "itens", itens
        );
    }

    @GetMapping
    public List<PedidoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PedidoCliente cadastrar(@RequestBody PedidoCliente pedidoCliente) {
        return repository.save(pedidoCliente);
    }

    @GetMapping("/{id}")
    public PedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public PedidoCliente atualizar(@PathVariable Integer id, @RequestBody PedidoCliente dados) {
        PedidoCliente pedidoCliente = repository.findById(id).orElse(null);

        if (pedidoCliente == null) {
            return null;
        }

        pedidoCliente.setVlTotal(dados.getVlTotal());
        pedidoCliente.setDtSolicitacao(dados.getDtSolicitacao());
        pedidoCliente.setDtProgramacaoEntrega(dados.getDtProgramacaoEntrega());
        pedidoCliente.setStatus(dados.getStatus());
        pedidoCliente.setCliente(dados.getCliente());
        pedidoCliente.setPromotor(dados.getPromotor());

        return repository.save(pedidoCliente);
    }

    @PutMapping("/{id}/aprovar-estoque")
    public PedidoCliente aprovarEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_ESTOQUE");

        return repository.save(pedido);
    }

    @PutMapping("/{id}/pendente-estoque")
    public PedidoCliente pendenteEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("PENDENTE_ESTOQUE");

        return repository.save(pedido);
    }

    @PutMapping("/{id}/aprovar-venda")
    public PedidoCliente aprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_VENDA");

        return repository.save(pedido);
    }

    @PutMapping("/{id}/reprovar-venda")
    public PedidoCliente reprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("REPROVADO_VENDA");

        return repository.save(pedido);
    }

    @PutMapping("/{id}/programar")
    public PedidoCliente programarEntrega(@PathVariable Integer id, @RequestBody PedidoCliente dados) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("PEDIDO_PROGRAMADO");
        pedido.setDtProgramacaoEntrega(dados.getDtProgramacaoEntrega());

        return repository.save(pedido);
    }

    @PutMapping("/{id}/processar")
    public PedidoCliente processar(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        if (!"PEDIDO_PROGRAMADO".equals(pedido.getStatus())) {
            return pedido;
        }

        List<ItemPedidoCliente> itens
                = itemRepository.findByPedidoClienteIdPedidoCliente(id);

        BigDecimal valorTotalComissao = BigDecimal.ZERO;

        for (ItemPedidoCliente item : itens) {
            Produto produto = item.getProduto();

            Integer estoqueAtual = produto.getQtdEstoque();
            Integer quantidadeVendida = item.getQtd();

            produto.setQtdEstoque(estoqueAtual - quantidadeVendida);
            produtoRepository.save(produto);

            BigDecimal subtotal = item.getVlUnitario()
                    .multiply(BigDecimal.valueOf(item.getQtd()));

            BigDecimal percentualComissao = produto.getPercentualComissao();

            if (percentualComissao == null) {
                percentualComissao = produto.getCategoriaProduto().getPercentualComissao();
            }

            if (percentualComissao != null) {
                BigDecimal valorComissaoItem = subtotal
                        .multiply(percentualComissao)
                        .divide(BigDecimal.valueOf(100));

                valorTotalComissao = valorTotalComissao.add(valorComissaoItem);
            }
        }

        Comissao comissao = new Comissao();
        comissao.setValor(valorTotalComissao);
        comissao.setData(LocalDate.now());
        comissao.setStatus("LANCADA");
        comissao.setPromotor(pedido.getPromotor());
        comissao.setPedidoCliente(pedido);

        comissaoRepository.save(comissao);

        pedido.setStatus("PROCESSADO");

        return repository.save(pedido);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}

package com.ifmt.sisvendas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.dto.PedidoClienteDTO;
import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.model.ItemPedidoCliente;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.ClienteRepository;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;

@RestController
@RequestMapping("/pedidos-cliente")
public class PedidoClienteController {

    private final PedidoClienteRepository repository;
    private final ItemPedidoClienteRepository itemRepository;
    private final ProdutoRepository produtoRepository;
    private final ComissaoRepository comissaoRepository;
    private final ClienteRepository clienteRepository;
    private final PromotorRepository promotorRepository;

    public PedidoClienteController(
            PedidoClienteRepository repository,
            ItemPedidoClienteRepository itemRepository,
            ProdutoRepository produtoRepository,
            ComissaoRepository comissaoRepository,
            ClienteRepository clienteRepository,
            PromotorRepository promotorRepository) {

        this.repository = repository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
        this.comissaoRepository = comissaoRepository;
        this.clienteRepository = clienteRepository;
        this.promotorRepository = promotorRepository;
    }

    @GetMapping
    public List<PedidoCliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public PedidoCliente cadastrar(@RequestBody PedidoClienteDTO pedidoClienteDTO) {
        Cliente cliente = clienteRepository.findById(pedidoClienteDTO.getIdCliente()).orElse(null);
        Promotor promotor = promotorRepository.findById(pedidoClienteDTO.getIdPromotor()).orElse(null);

        if (cliente == null || promotor == null) {
            return null;
        }

        PedidoCliente pedidoCliente = new PedidoCliente();

        aplicarDadosDTO(
                pedidoCliente,
                pedidoClienteDTO,
                cliente,
                promotor
        );

        return repository.save(pedidoCliente);
    }

    @GetMapping("/{id}")
    public PedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/{id}/detalhes")
    public Map<String, Object> buscarPedidoComItens(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        List<ItemPedidoCliente> itens =
                itemRepository.findByPedidoClienteIdPedidoCliente(id);

        return Map.of(
                "pedido", pedido,
                "itens", itens
        );
    }

    @PutMapping("/{id}")
    public PedidoCliente atualizar(
            @PathVariable Integer id,
            @RequestBody PedidoClienteDTO pedidoClienteDTO) {

        PedidoCliente pedidoCliente = repository.findById(id).orElse(null);

        if (pedidoCliente == null) {
            return null;
        }

        Cliente cliente = clienteRepository.findById(pedidoClienteDTO.getIdCliente()).orElse(null);
        Promotor promotor = promotorRepository.findById(pedidoClienteDTO.getIdPromotor()).orElse(null);

        if (cliente == null || promotor == null) {
            return null;
        }

        aplicarDadosDTO(
                pedidoCliente,
                pedidoClienteDTO,
                cliente,
                promotor
        );

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
    public PedidoCliente programarEntrega(
            @PathVariable Integer id,
            @RequestBody PedidoClienteDTO pedidoClienteDTO) {

        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("PEDIDO_PROGRAMADO");
        pedido.setDtProgramacaoEntrega(pedidoClienteDTO.getDtProgramacaoEntrega());

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

        List<ItemPedidoCliente> itens =
                itemRepository.findByPedidoClienteIdPedidoCliente(id);

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

    @GetMapping("/status/{status}")
    public List<PedidoCliente> listarPorStatus(@PathVariable String status) {
        return repository.findByStatus(status);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            PedidoCliente pedidoCliente,
            PedidoClienteDTO pedidoClienteDTO,
            Cliente cliente,
            Promotor promotor) {

        pedidoCliente.setVlTotal(pedidoClienteDTO.getVlTotal());
        pedidoCliente.setDtSolicitacao(pedidoClienteDTO.getDtSolicitacao());
        pedidoCliente.setDtProgramacaoEntrega(pedidoClienteDTO.getDtProgramacaoEntrega());
        pedidoCliente.setStatus(pedidoClienteDTO.getStatus());
        pedidoCliente.setCliente(cliente);
        pedidoCliente.setPromotor(promotor);
    }
}
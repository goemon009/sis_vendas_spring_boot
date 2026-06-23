package com.ifmt.sisvendas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.model.ItemPedidoCliente;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import com.ifmt.sisvendas.repository.ItemPedidoClienteRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/pedidos-cliente")
@Tag(
        name = "Pedidos de Cliente",
        description = "Operações de cadastro, consulta e processamento dos pedidos realizados por clientes."
)
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

    @Operation(summary = "Listar pedidos de clientes", description = "Retorna todos os pedidos de clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso")
    @GetMapping
    public List<PedidoCliente> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Cadastrar pedido de cliente", description = "Cadastra um novo pedido de cliente.")
    @ApiResponse(responseCode = "200", description = "Pedido cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro do pedido")
    @PostMapping
    public PedidoCliente cadastrar(@RequestBody PedidoCliente pedidoCliente) {
        return repository.save(pedidoCliente);
    }

    @Operation(
            summary = "Listar pedidos por situação",
            description = """
                    Retorna todos os pedidos de cliente filtrados por situação.
                    Situações previstas: SOLICITADO, APROVADO_ESTOQUE, PENDENTE_ESTOQUE,
                    APROVADO_VENDA, REPROVADO_VENDA, PEDIDO_PROGRAMADO e PROCESSADO.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso")
    @GetMapping("/status/{status}")
    public List<PedidoCliente> listarPorStatus(@PathVariable String status) {
        return repository.findByStatusOrderByDtSolicitacaoAsc(status);
    }

    @Operation(summary = "Buscar pedido de cliente por ID", description = "Retorna um pedido de cliente pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Pedido encontrado")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @GetMapping("/{id}")
    public PedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "Atualizar pedido de cliente", description = "Atualiza os dados de um pedido de cliente existente.")
    @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
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

    @Operation(
            summary = "Aprovar pedido pelo estoque",
            description = "Muda o status do pedido de SOLICITADO para APROVADO_ESTOQUE após avaliação de disponibilidade dos produtos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido aprovado pelo estoque com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/aprovar-estoque")
    public PedidoCliente aprovarEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_ESTOQUE");

        return repository.save(pedido);
    }

    @Operation(
            summary = "Marcar pedido como pendente de estoque",
            description = "Muda o status do pedido de SOLICITADO para PENDENTE_ESTOQUE quando pelo menos um item não possui estoque suficiente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido marcado como pendente de estoque"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/pendente-estoque")
    public PedidoCliente pendenteEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("PENDENTE_ESTOQUE");

        return repository.save(pedido);
    }

    @Operation(
            summary = "Aprovar pedido pela venda",
            description = "Muda o status do pedido de APROVADO_ESTOQUE para APROVADO_VENDA após análise das condições do cliente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido aprovado pela venda com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/aprovar-venda")
    public PedidoCliente aprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_VENDA");

        return repository.save(pedido);
    }

    @Operation(
            summary = "Reprovar pedido pela venda",
            description = "Muda o status do pedido de APROVADO_ESTOQUE para REPROVADO_VENDA quando o cliente não atende às condições comerciais."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido reprovado pela venda"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}/reprovar-venda")
    public PedidoCliente reprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("REPROVADO_VENDA");

        return repository.save(pedido);
    }

    @Operation(
            summary = "Programar entrega do pedido",
            description = "Define a data prevista de entrega e muda o status do pedido para PEDIDO_PROGRAMADO."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrega do pedido programada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
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

    @Operation(
            summary = "Processar pedido de cliente",
            description = "Processa um pedido programado, realiza a baixa do estoque dos produtos e gera o lançamento de comissão para o promotor de venda."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido processado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
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

    @Operation(summary = "Excluir pedido de cliente", description = "Remove um pedido de cliente pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Pedido excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}

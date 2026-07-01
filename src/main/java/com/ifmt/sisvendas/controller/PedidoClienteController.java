package com.ifmt.sisvendas.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller responsável pelos endpoints REST de pedidos de cliente.
 *
 * Além das operações básicas de cadastro e consulta, concentra endpoints
 * de mudança de status do pedido e processamento da venda.
 */
@RestController
@RequestMapping("/pedidos-cliente")
@Tag(
        name = "Pedidos de Cliente",
        description = "Endpoints para cadastro, consulta e processamento de pedidos de cliente."
)
public class PedidoClienteController {

    private static final Logger logger =
            LoggerFactory.getLogger(PedidoClienteController.class);

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

    /**
     * Retorna todos os pedidos de cliente cadastrados.
     */
    @GetMapping
    public List<PedidoCliente> listar() {
        return repository.findAll();
    }

    /**
     * Cadastra um novo pedido de cliente.
     *
     * Os dados são recebidos por meio de um DTO para simplificar
     * a criação do pedido e seus relacionamentos.
     */
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

    /**
     * Busca um pedido de cliente pelo identificador.
     */
    @GetMapping("/{id}")
    public PedidoCliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    /**
     * Retorna os dados gerais do pedido junto com seus itens.
     *
     * Esse endpoint facilita a visualização completa de um pedido,
     * incluindo os produtos e quantidades vinculados.
     */
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

    /**
     * Atualiza os dados gerais de um pedido de cliente.
     */
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

    /**
     * Altera o status do pedido para APROVADO_ESTOQUE.
     *
     * Essa etapa representa a aprovação do pedido após análise
     * de disponibilidade dos produtos em estoque.
     */
    @PutMapping("/{id}/aprovar-estoque")
    public PedidoCliente aprovarEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_ESTOQUE");

        return repository.save(pedido);
    }

    /**
     * Altera o status do pedido para PENDENTE_ESTOQUE.
     *
     * Esse status indica que pelo menos um item do pedido
     * não possui estoque suficiente.
     */
    @PutMapping("/{id}/pendente-estoque")
    public PedidoCliente pendenteEstoque(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("PENDENTE_ESTOQUE");

        return repository.save(pedido);
    }

    /**
     * Altera o status do pedido para APROVADO_VENDA.
     *
     * Essa etapa representa a aprovação comercial do pedido
     * após análise das condições do cliente.
     */
    @PutMapping("/{id}/aprovar-venda")
    public PedidoCliente aprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("APROVADO_VENDA");

        return repository.save(pedido);
    }

    /**
     * Altera o status do pedido para REPROVADO_VENDA.
     *
     * Esse status indica que o pedido não foi aprovado
     * na análise comercial.
     */
    @PutMapping("/{id}/reprovar-venda")
    public PedidoCliente reprovarVenda(@PathVariable Integer id) {
        PedidoCliente pedido = repository.findById(id).orElse(null);

        if (pedido == null) {
            return null;
        }

        pedido.setStatus("REPROVADO_VENDA");

        return repository.save(pedido);
    }

    /**
     * Programa a entrega do pedido e altera seu status para PEDIDO_PROGRAMADO.
     *
     * Essa etapa define a data prevista para execução ou entrega do pedido.
     */
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

    /**
     * Processa um pedido de cliente.
     *
     * Essa operação representa a finalização da venda:
     * valida o pedido, percorre seus itens, baixa o estoque dos produtos,
     * calcula a comissão do promotor e altera o status do pedido para PROCESSADO.
     */
    @Transactional
    @Operation(
            summary = "Processar pedido de cliente",
            description = """
                    Processa um pedido de cliente com status PEDIDO_PROGRAMADO.
                    A operacao baixa o estoque dos produtos, calcula a comissao do promotor,
                    gera um lancamento de comissao com status LANCADA e altera o pedido para PROCESSADO.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido processado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Pedido sem condicao valida para processamento"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado")
    })
    @PutMapping("/{id}/processar")
    public ResponseEntity<EntityModel<PedidoCliente>> processar(@PathVariable Integer id) {
        logger.info("Iniciando processamento do pedido de cliente. ID: {}", id);

        // Busca o pedido informado pelo ID recebido na URL.
        PedidoCliente pedido = repository.findById(id).orElse(null);

        // Valida se o pedido existe antes de executar qualquer regra de negócio.
        if (pedido == null) {
            logger.warn("Pedido de cliente nao encontrado para processamento. ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        // O pedido só pode ser processado após ter sido programado.
        if (!"PEDIDO_PROGRAMADO".equals(pedido.getStatus())) {
            logger.warn(
                    "Pedido {} nao pode ser processado. Status atual: {}",
                    id,
                    pedido.getStatus()
            );

            return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
        }

        if (pedido.getPromotor() == null) {
            logger.warn("Pedido {} nao possui promotor vinculado.", id);
            return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
        }

        // Busca os itens vinculados ao pedido.
        List<ItemPedidoCliente> itens =
                itemRepository.findByPedidoClienteIdPedidoCliente(id);

        if (itens.isEmpty()) {
            logger.warn("Pedido {} nao possui itens para processamento.", id);
            return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
        }

        // Valida os itens antes de alterar estoque ou gerar comissão.
        for (ItemPedidoCliente item : itens) {
            Produto produto = item.getProduto();

            if (produto == null || item.getQtd() == null || item.getVlUnitario() == null) {
                logger.warn("Pedido {} possui item invalido para processamento.", id);
                return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
            }

            if (produto.getQtdEstoque() == null) {
                logger.warn("Produto {} nao possui estoque informado.", produto.getIdProduto());
                return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
            }

            if (produto.getQtdEstoque() < item.getQtd()) {
                logger.warn(
                        "Estoque insuficiente para o produto {}. Estoque atual: {}, quantidade solicitada: {}",
                        produto.getIdProduto(),
                        produto.getQtdEstoque(),
                        item.getQtd()
                );

                return ResponseEntity.badRequest().body(montarPedidoModel(pedido));
            }
        }

        BigDecimal valorTotalComissao = BigDecimal.ZERO;

        for (ItemPedidoCliente item : itens) {
            Produto produto = item.getProduto();

            Integer estoqueAtual = produto.getQtdEstoque();
            Integer quantidadeVendida = item.getQtd();

            // Baixa o estoque dos produtos vendidos.
            produto.setQtdEstoque(estoqueAtual - quantidadeVendida);
            produtoRepository.save(produto);

            logger.info(
                    "Estoque atualizado. Produto: {}, quantidade vendida: {}, novo estoque: {}",
                    produto.getIdProduto(),
                    quantidadeVendida,
                    produto.getQtdEstoque()
            );

            BigDecimal subtotal = item.getVlUnitario()
                    .multiply(BigDecimal.valueOf(item.getQtd()));

            // Calcula a comissão usando primeiro o percentual do produto.
            BigDecimal percentualComissao = produto.getPercentualComissao();

            // Caso o produto não tenha comissão própria, usa a comissão da categoria.
            if (percentualComissao == null && produto.getCategoriaProduto() != null) {
                percentualComissao = produto.getCategoriaProduto().getPercentualComissao();
            }

            if (percentualComissao != null) {
                BigDecimal valorComissaoItem = subtotal
                        .multiply(percentualComissao)
                        .divide(BigDecimal.valueOf(100));

                valorTotalComissao = valorTotalComissao.add(valorComissaoItem);
            }
        }

        // Cria o lançamento de comissão para o promotor.
        Comissao comissao = new Comissao();
        comissao.setValor(valorTotalComissao);
        comissao.setData(LocalDate.now());
        comissao.setStatus("LANCADA");
        comissao.setPromotor(pedido.getPromotor());
        comissao.setPedidoCliente(pedido);

        comissaoRepository.save(comissao);

        logger.info(
                "Comissao lancada para o promotor {} no valor de {}",
                pedido.getPromotor().getIdPromotor(),
                valorTotalComissao
        );

        // Finaliza o pedido alterando seu status para PROCESSADO.
        pedido.setStatus("PROCESSADO");

        PedidoCliente pedidoProcessado = repository.save(pedido);

        logger.info("Pedido de cliente processado com sucesso. ID: {}", id);

        return ResponseEntity.ok(montarPedidoModel(pedidoProcessado));
    }

    /**
     * Lista pedidos de cliente filtrados pelo status informado.
     *
     * Essa consulta facilita o acompanhamento dos pedidos em cada etapa
     * do fluxo de venda.
     */
    @GetMapping("/status/{status}")
    public List<PedidoCliente> listarPorStatus(@PathVariable String status) {
        return repository.findByStatus(status);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private EntityModel<PedidoCliente> montarPedidoModel(PedidoCliente pedido) {
        return EntityModel.of(
                pedido,
                linkTo(methodOn(PedidoClienteController.class)
                        .buscarPorId(pedido.getIdPedidoCliente()))
                        .withSelfRel(),

                linkTo(methodOn(PedidoClienteController.class)
                        .buscarPedidoComItens(pedido.getIdPedidoCliente()))
                        .withRel("detalhes-do-pedido"),

                linkTo(methodOn(PedidoClienteController.class)
                        .listar())
                        .withRel("listar-pedidos"),

                linkTo(methodOn(PedidoClienteController.class)
                        .listarPorStatus(pedido.getStatus()))
                        .withRel("pedidos-por-status")
        );
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

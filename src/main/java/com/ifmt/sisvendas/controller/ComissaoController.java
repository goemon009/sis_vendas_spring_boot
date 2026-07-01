package com.ifmt.sisvendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.dto.ComissaoDTO;
import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.model.PedidoCliente;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.ComissaoRepository;
import com.ifmt.sisvendas.repository.PedidoClienteRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/comissoes")
@Tag(
        name = "Comissões",
        description = "Endpoints para cadastro, consulta e quitação de comissões dos promotores."
)
public class ComissaoController {

    private static final Logger logger =
            LoggerFactory.getLogger(ComissaoController.class);

    private final ComissaoRepository repository;
    private final PromotorRepository promotorRepository;
    private final PedidoClienteRepository pedidoClienteRepository;

    public ComissaoController(
            ComissaoRepository repository,
            PromotorRepository promotorRepository,
            PedidoClienteRepository pedidoClienteRepository) {

        this.repository = repository;
        this.promotorRepository = promotorRepository;
        this.pedidoClienteRepository = pedidoClienteRepository;
    }

    @Operation(
            summary = "Listar comissões por status, promotor e período",
            description = """
                    Retorna os lançamentos de comissão filtrados por status,
                    promotor de venda e intervalo de data.
                    Status previstos: LANCADA e QUITADA.
                    """
    )
    @ApiResponse(responseCode = "200", description = "Comissões retornadas com sucesso")
    @GetMapping("/status/{status}/promotor/{idPromotor}")
    public List<Comissao> listarPorStatusPromotorEPeriodo(
            @PathVariable String status,
            @PathVariable Integer idPromotor,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {

        logger.info(
                "Consultando comissões. Status: {}, promotor: {}, período: {} até {}",
                status,
                idPromotor,
                dataInicio,
                dataFim
        );

        return repository.findByStatusAndPromotorIdPromotorAndDataBetween(
                status,
                idPromotor,
                dataInicio,
                dataFim
        );
    }

    @GetMapping
    public List<Comissao> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Comissao cadastrar(@RequestBody ComissaoDTO comissaoDTO) {
        Promotor promotor =
                promotorRepository.findById(comissaoDTO.getIdPromotor()).orElse(null);

        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(comissaoDTO.getIdPedidoCliente()).orElse(null);

        if (promotor == null || pedidoCliente == null) {
            return null;
        }

        Comissao comissao = new Comissao();

        aplicarDadosDTO(
                comissao,
                comissaoDTO,
                promotor,
                pedidoCliente
        );

        return repository.save(comissao);
    }

    @GetMapping("/{id}")
    public Comissao buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Comissao atualizar(
            @PathVariable Integer id,
            @RequestBody ComissaoDTO comissaoDTO) {

        Comissao comissao =
                repository.findById(id).orElse(null);

        if (comissao == null) {
            return null;
        }

        Promotor promotor =
                promotorRepository.findById(comissaoDTO.getIdPromotor()).orElse(null);

        PedidoCliente pedidoCliente =
                pedidoClienteRepository.findById(comissaoDTO.getIdPedidoCliente()).orElse(null);

        if (promotor == null || pedidoCliente == null) {
            return null;
        }

        aplicarDadosDTO(
                comissao,
                comissaoDTO,
                promotor,
                pedidoCliente
        );

        return repository.save(comissao);
    }

    @Operation(
            summary = "Quitar comissão",
            description = "Altera o status de uma comissão de LANCADA para QUITADA, representando o pagamento ao promotor de venda."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Comissão quitada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Comissão não está em situação válida para quitação"),
            @ApiResponse(responseCode = "404", description = "Comissão não encontrada")
    })
    @PutMapping("/{id}/quitar")
    public ResponseEntity<EntityModel<Comissao>> quitar(@PathVariable Integer id) {
        logger.info("Iniciando quitação da comissão. ID: {}", id);

        Comissao comissao = repository.findById(id).orElse(null);

        if (comissao == null) {
            logger.warn("Comissão não encontrada para quitação. ID: {}", id);
            return ResponseEntity.notFound().build();
        }

        if (!"LANCADA".equals(comissao.getStatus())) {
            logger.warn(
                    "Comissão {} não pode ser quitada. Status atual: {}",
                    id,
                    comissao.getStatus()
            );

            return ResponseEntity.badRequest().body(montarComissaoModel(comissao));
        }

        comissao.setStatus("QUITADA");

        Comissao comissaoQuitada = repository.save(comissao);

        logger.info("Comissão quitada com sucesso. ID: {}", id);

        return ResponseEntity.ok(montarComissaoModel(comissaoQuitada));
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private EntityModel<Comissao> montarComissaoModel(Comissao comissao) {
        return EntityModel.of(
                comissao,
                linkTo(methodOn(ComissaoController.class)
                        .buscarPorId(comissao.getIdComissao()))
                        .withSelfRel(),

                linkTo(methodOn(ComissaoController.class)
                        .listar())
                        .withRel("listar-comissoes")
        );
    }

    private void aplicarDadosDTO(
            Comissao comissao,
            ComissaoDTO comissaoDTO,
            Promotor promotor,
            PedidoCliente pedidoCliente) {

        comissao.setValor(comissaoDTO.getValor());
        comissao.setData(comissaoDTO.getData());
        comissao.setStatus(comissaoDTO.getStatus());
        comissao.setPromotor(promotor);
        comissao.setPedidoCliente(pedidoCliente);
    }
}

package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.model.EntradaMercadoria;
import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.EntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/entradas-mercadoria")
@Tag(
        name = "Entradas de Mercadoria",
        description = "Operações de cadastro, conferência e processamento de entradas de mercadoria."
)
public class EntradaMercadoriaController {

    private final EntradaMercadoriaRepository repository;
    private final ItemEntradaMercadoriaRepository itemRepository;
    private final ProdutoRepository produtoRepository;

    public EntradaMercadoriaController(
            EntradaMercadoriaRepository repository,
            ItemEntradaMercadoriaRepository itemRepository,
            ProdutoRepository produtoRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
    }

    @Operation(summary = "Listar entradas de mercadoria", description = "Retorna todas as entradas de mercadoria cadastradas.")
    @ApiResponse(responseCode = "200", description = "Entradas retornadas com sucesso")
    @GetMapping
    public List<EntradaMercadoria> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Cadastrar entrada de mercadoria", description = "Cadastra uma nova entrada de mercadoria.")
    @ApiResponse(responseCode = "200", description = "Entrada cadastrada com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro da entrada")
    @PostMapping
    public EntradaMercadoria cadastrar(@RequestBody EntradaMercadoria entradaMercadoria) {
        return repository.save(entradaMercadoria);
    }

    @Operation(summary = "Buscar entrada por ID", description = "Retorna uma entrada de mercadoria pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Entrada encontrada")
    @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    @GetMapping("/{id}")
    public EntradaMercadoria buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "Atualizar entrada de mercadoria", description = "Atualiza os dados de uma entrada de mercadoria existente.")
    @ApiResponse(responseCode = "200", description = "Entrada atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    @PutMapping("/{id}")
    public EntradaMercadoria atualizar(@PathVariable Integer id, @RequestBody EntradaMercadoria dados) {
        EntradaMercadoria entradaMercadoria = repository.findById(id).orElse(null);

        if (entradaMercadoria == null) {
            return null;
        }

        entradaMercadoria.setNumeroNota(dados.getNumeroNota());
        entradaMercadoria.setDataEntrada(dados.getDataEntrada());
        entradaMercadoria.setStatus(dados.getStatus());
        entradaMercadoria.setFornecedor(dados.getFornecedor());

        return repository.save(entradaMercadoria);
    }

    @Operation(summary = "Excluir entrada de mercadoria", description = "Remove uma entrada de mercadoria pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Entrada excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Entrada não encontrada")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    @Operation(
            summary = "Conferir entrada de mercadoria",
            description = "Muda o status da entrada de mercadoria de DIGITADA para CONFERIDA após conferência dos produtos físicos recebidos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada de mercadoria conferida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrada de mercadoria não encontrada")
    })
    @PutMapping("/{id}/conferir")
    public EntradaMercadoria conferir(@PathVariable Integer id) {
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        entrada.setStatus("CONFERIDA");

        return repository.save(entrada);
    }

    @Operation(
            summary = "Processar entrada de mercadoria",
            description = "Processa uma entrada conferida, adicionando as quantidades recebidas ao estoque dos respectivos produtos."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Entrada de mercadoria processada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrada de mercadoria não encontrada")
    })
    @PutMapping("/{id}/processar")
    public EntradaMercadoria processar(@PathVariable Integer id) {
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        if (!"CONFERIDA".equals(entrada.getStatus())) {
            return entrada;
        }

        List<ItemEntradaMercadoria> itens =
                itemRepository.findByEntradaMercadoriaIdEntradaMercadoria(id);

        for (ItemEntradaMercadoria item : itens) {
            Produto produto = item.getProduto();

            Integer estoqueAtual = produto.getQtdEstoque();
            Integer quantidadeEntrada = item.getQtd();

            produto.setQtdEstoque(estoqueAtual + quantidadeEntrada);

            produtoRepository.save(produto);
        }

        entrada.setStatus("PROCESSADA");

        return repository.save(entrada);
    }
}

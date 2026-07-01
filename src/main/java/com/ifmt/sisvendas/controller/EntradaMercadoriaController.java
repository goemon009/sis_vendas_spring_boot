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

import com.ifmt.sisvendas.dto.EntradaMercadoriaDTO;
import com.ifmt.sisvendas.model.EntradaMercadoria;
import com.ifmt.sisvendas.model.Fornecedor;
import com.ifmt.sisvendas.model.ItemEntradaMercadoria;
import com.ifmt.sisvendas.model.Produto;
import com.ifmt.sisvendas.repository.EntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.FornecedorRepository;
import com.ifmt.sisvendas.repository.ItemEntradaMercadoriaRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

/**
 * Controller responsável pelos endpoints REST de entrada de mercadoria.
 *
 * Controla o recebimento de produtos comprados de fornecedores,
 * incluindo cadastro, conferência e processamento da entrada.
 */
@RestController
@RequestMapping("/entradas-mercadoria")
public class EntradaMercadoriaController {

    private final EntradaMercadoriaRepository repository;
    private final ItemEntradaMercadoriaRepository itemRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;

    public EntradaMercadoriaController(
            EntradaMercadoriaRepository repository,
            ItemEntradaMercadoriaRepository itemRepository,
            ProdutoRepository produtoRepository,
            FornecedorRepository fornecedorRepository) {
        this.repository = repository;
        this.itemRepository = itemRepository;
        this.produtoRepository = produtoRepository;
        this.fornecedorRepository = fornecedorRepository;
    }

    @GetMapping
    public List<EntradaMercadoria> listar() {
        return repository.findAll();
    }

    @PostMapping
    public EntradaMercadoria cadastrar(@RequestBody EntradaMercadoriaDTO entradaMercadoriaDTO) {
        Fornecedor fornecedor =
                fornecedorRepository.findById(entradaMercadoriaDTO.getIdFornecedor()).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        EntradaMercadoria entradaMercadoria = new EntradaMercadoria();
        aplicarDadosDTO(entradaMercadoria, entradaMercadoriaDTO, fornecedor);

        return repository.save(entradaMercadoria);
    }

    @GetMapping("/{id}")
    public EntradaMercadoria buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public EntradaMercadoria atualizar(
            @PathVariable Integer id,
            @RequestBody EntradaMercadoriaDTO entradaMercadoriaDTO) {

        EntradaMercadoria entradaMercadoria = repository.findById(id).orElse(null);

        if (entradaMercadoria == null) {
            return null;
        }

        Fornecedor fornecedor =
                fornecedorRepository.findById(entradaMercadoriaDTO.getIdFornecedor()).orElse(null);

        if (fornecedor == null) {
            return null;
        }

        aplicarDadosDTO(entradaMercadoria, entradaMercadoriaDTO, fornecedor);

        return repository.save(entradaMercadoria);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    /**
     * Altera o status da entrada de mercadoria para CONFERIDA.
     *
     * Essa etapa representa a verificação dos produtos recebidos
     * em relação ao que foi digitado no sistema.
     */
    @PutMapping("/{id}/conferir")
    public EntradaMercadoria conferir(@PathVariable Integer id) {
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        entrada.setStatus("CONFERIDA");

        return repository.save(entrada);
    }

    /**
     * Processa uma entrada de mercadoria conferida.
     *
     * A operação adiciona as quantidades recebidas ao estoque dos produtos
     * e altera o status da entrada para PROCESSADA.
     */
    @PutMapping("/{id}/processar")
    public EntradaMercadoria processar(@PathVariable Integer id) {
        // Busca a entrada de mercadoria que será processada.
        EntradaMercadoria entrada = repository.findById(id).orElse(null);

        if (entrada == null) {
            return null;
        }

        if (!"CONFERIDA".equals(entrada.getStatus())) {
            return entrada;
        }

        // Busca os itens vinculados à entrada.
        List<ItemEntradaMercadoria> itens =
                itemRepository.findByEntradaMercadoriaIdEntradaMercadoria(id);

        for (ItemEntradaMercadoria item : itens) {
            Produto produto = item.getProduto();

            Integer estoqueAtual = produto.getQtdEstoque();
            Integer quantidadeEntrada = item.getQtd();

            // Soma a quantidade recebida ao estoque atual de cada produto.
            produto.setQtdEstoque(estoqueAtual + quantidadeEntrada);

            produtoRepository.save(produto);
        }

        // Finaliza a entrada alterando seu status para PROCESSADA.
        entrada.setStatus("PROCESSADA");

        return repository.save(entrada);
    }

    private void aplicarDadosDTO(
            EntradaMercadoria entradaMercadoria,
            EntradaMercadoriaDTO entradaMercadoriaDTO,
            Fornecedor fornecedor) {

        entradaMercadoria.setNumeroNota(entradaMercadoriaDTO.getNumeroNota());
        entradaMercadoria.setDataEntrada(entradaMercadoriaDTO.getDataEntrada());
        entradaMercadoria.setStatus(entradaMercadoriaDTO.getStatus());
        entradaMercadoria.setFornecedor(fornecedor);
    }
}

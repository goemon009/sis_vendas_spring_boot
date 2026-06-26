package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.dto.CategoriaProdutoDTO;
import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaProdutoController {

    private final CategoriaProdutoRepository repository;

    public CategoriaProdutoController(CategoriaProdutoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<CategoriaProduto> listar() {
        return repository.findAll();
    }

    @GetMapping("/ordenadas")
    public List<CategoriaProduto> listarOrdenadasPorNome() {
        return repository.findAllByOrderByNomeAsc();
    }

    @PostMapping
    public CategoriaProduto cadastrar(@RequestBody CategoriaProdutoDTO categoriaDTO) {
        CategoriaProduto categoria = new CategoriaProduto();
        aplicarDadosDTO(categoria, categoriaDTO);

        return repository.save(categoria);
    }

    @GetMapping("/{id}")
    public CategoriaProduto buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public CategoriaProduto atualizar(@PathVariable Integer id,
                                      @RequestBody CategoriaProdutoDTO categoriaDTO) {
        CategoriaProduto categoria = repository.findById(id).orElse(null);

        if (categoria == null) {
            return null;
        }

        aplicarDadosDTO(categoria, categoriaDTO);

        return repository.save(categoria);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            CategoriaProduto categoria,
            CategoriaProdutoDTO categoriaDTO) {

        categoria.setNome(categoriaDTO.getNome());
        categoria.setPercentualComissao(categoriaDTO.getPercentualComissao());
        categoria.setPercentualDesconto(categoriaDTO.getPercentualDesconto());
    }
}
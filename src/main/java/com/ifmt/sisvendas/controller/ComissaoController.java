package com.ifmt.sisvendas.controller;

import java.time.LocalDate;
import java.util.List;

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

@RestController
@RequestMapping("/comissoes")
public class ComissaoController {

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

    @GetMapping("/status/{status}/promotor/{idPromotor}")
    public List<Comissao> listarPorStatusPromotorEPeriodo(
            @PathVariable String status,
            @PathVariable Integer idPromotor,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

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

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
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
package com.ifmt.sisvendas.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Integer id,
                             @RequestBody Cliente dados) {

        Cliente cliente =
                repository.findById(id).orElse(null);

        if (cliente == null) {
            return null;
        }

        cliente.setRazaoSocial(dados.getRazaoSocial());
        cliente.setNomeFantasia(dados.getNomeFantasia());
        cliente.setCnpj(dados.getCnpj());
        cliente.setInscricaoEstadual(dados.getInscricaoEstadual());
        cliente.setEndereco(dados.getEndereco());
        cliente.setPromotor(dados.getPromotor());

        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}
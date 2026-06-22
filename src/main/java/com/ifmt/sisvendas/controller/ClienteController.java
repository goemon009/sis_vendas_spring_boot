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

import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.repository.ClienteRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/clientes")
@Tag(name = "Clientes", description = "Operações de cadastro e manutenção dos clientes atendidos pelos promotores.")
public class ClienteController {

    private final ClienteRepository repository;

    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados.")
    @ApiResponse(responseCode = "200", description = "Clientes retornados com sucesso")
    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente.")
    @ApiResponse(responseCode = "200", description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro do cliente")
    @PostMapping
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        return repository.save(cliente);
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os dados de um cliente pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente.")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Integer id, @RequestBody Cliente dados) {
        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente == null) {
            return null;
        }

        cliente.setRazaoSocial(dados.getRazaoSocial());
        cliente.setNomeFantasia(dados.getNomeFantasia());
        cliente.setCnpj(dados.getCnpj());
        cliente.setInscricaoEstadual(dados.getInscricaoEstadual());
        cliente.setEndereco(dados.getEndereco());
        cliente.setPromotor(dados.getPromotor());
        cliente.setMunicipio(dados.getMunicipio());

        return repository.save(cliente);
    }

    @Operation(summary = "Excluir cliente", description = "Remove um cliente pelo seu identificador.")
    @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }
}

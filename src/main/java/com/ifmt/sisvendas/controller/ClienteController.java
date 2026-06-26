package com.ifmt.sisvendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.ifmt.sisvendas.dto.ClienteDTO;
import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.ClienteRepository;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;
    private final PromotorRepository promotorRepository;
    private final MunicipioRepository municipioRepository;

    public ClienteController(
            ClienteRepository repository,
            PromotorRepository promotorRepository,
            MunicipioRepository municipioRepository) {
        this.repository = repository;
        this.promotorRepository = promotorRepository;
        this.municipioRepository = municipioRepository;
    }

    @GetMapping
    public List<Cliente> listar() {
        return repository.findAll();
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody ClienteDTO clienteDTO) {
        Promotor promotor = promotorRepository.findById(clienteDTO.getIdPromotor()).orElse(null);
        Municipio municipio = municipioRepository.findById(clienteDTO.getIdMunicipio()).orElse(null);

        if (promotor == null || municipio == null) {
            return null;
        }

        Cliente cliente = new Cliente();
        aplicarDadosDTO(cliente, clienteDTO, promotor, municipio);

        return repository.save(cliente);
    }

    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Integer id) {
        return repository.findById(id).orElse(null);
    }

    @GetMapping("/cnpj/{cnpj}")
    public Cliente buscarPorCnpj(@PathVariable String cnpj) {
        return repository.findByCnpj(cnpj);
    }

    @GetMapping("/promotor/{idPromotor}/valor-vendido")
    public List<Object[]> listarClientesPorValorVendido(
            @PathVariable Integer idPromotor,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        return repository.buscarClientesPorPromotorOrdenadosPorValorVendido(
                idPromotor,
                dataInicio,
                dataFim
        );
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente == null) {
            return null;
        }

        Promotor promotor = promotorRepository.findById(clienteDTO.getIdPromotor()).orElse(null);
        Municipio municipio = municipioRepository.findById(clienteDTO.getIdMunicipio()).orElse(null);

        if (promotor == null || municipio == null) {
            return null;
        }

        aplicarDadosDTO(cliente, clienteDTO, promotor, municipio);

        return repository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        repository.deleteById(id);
    }

    private void aplicarDadosDTO(
            Cliente cliente,
            ClienteDTO clienteDTO,
            Promotor promotor,
            Municipio municipio) {

        cliente.setRazaoSocial(clienteDTO.getRazaoSocial());
        cliente.setNomeFantasia(clienteDTO.getNomeFantasia());
        cliente.setCnpj(clienteDTO.getCnpj());
        cliente.setInscricaoEstadual(clienteDTO.getInscricaoEstadual());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setPromotor(promotor);
        cliente.setMunicipio(municipio);
    }
}
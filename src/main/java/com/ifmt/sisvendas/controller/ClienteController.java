package com.ifmt.sisvendas.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifmt.sisvendas.dto.ClienteDTO;
import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.model.Promotor;
import com.ifmt.sisvendas.repository.ClienteRepository;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.PromotorRepository;

/**
 * Controller responsável pelos endpoints REST de clientes.
 *
 * Permite manter o cadastro de clientes, buscar cliente por CNPJ
 * e consultar clientes vinculados a promotores de venda.
 */
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private static final Logger logger =
            LoggerFactory.getLogger(ClienteController.class);

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
        logger.info("Listando todos os clientes.");
        return repository.findAll();
    }

    @PostMapping
    public Cliente cadastrar(@RequestBody ClienteDTO clienteDTO) {
        Promotor promotor = promotorRepository.findById(clienteDTO.getIdPromotor()).orElse(null);
        Municipio municipio = municipioRepository.findById(clienteDTO.getIdMunicipio()).orElse(null);

        if (promotor == null || municipio == null) {
            logger.warn("Tentativa de cadastro de cliente com promotor ou município inexistente.");
            return null;
        }

        Cliente cliente = new Cliente();
        aplicarDadosDTO(cliente, clienteDTO, promotor, municipio);

        Cliente clienteSalvo = repository.save(cliente);

        logger.info("Cliente cadastrado com sucesso. ID: {}, CNPJ: {}",
                clienteSalvo.getIdCliente(),
                clienteSalvo.getCnpj());

        return clienteSalvo;
    }

    /**
     * Busca um cliente pelo identificador e retorna links HATEOAS relacionados.
     *
     * Os links permitem navegar para ações associadas ao cliente,
     * como listagem geral, atualização e exclusão.
     */
    @GetMapping("/{id}")
    public EntityModel<Cliente> buscarPorId(@PathVariable Integer id) {
        Cliente cliente = repository.findById(id).orElse(null);

        if (cliente == null) {
            logger.warn("Cliente não encontrado. ID: {}", id);
            return null;
        }

        logger.info("Cliente encontrado. ID: {}", id);

        return EntityModel.of(
                cliente,
                Link.of("http://localhost:8080/clientes/" + id).withSelfRel(),
                Link.of("http://localhost:8080/clientes").withRel("todos-clientes"),
                Link.of("http://localhost:8080/clientes/cnpj/" + cliente.getCnpj()).withRel("buscar-por-cnpj")
        );
    }

    /**
     * Busca um cliente pelo CNPJ.
     *
     * Essa consulta permite localizar uma loja cliente usando seu documento fiscal.
     */
    @GetMapping("/cnpj/{cnpj}")
    public Cliente buscarPorCnpj(@PathVariable String cnpj) {
        Cliente cliente = repository.findByCnpj(cnpj);

        if (cliente == null) {
            logger.warn("Cliente não encontrado pelo CNPJ: {}", cnpj);
            return null;
        }

        logger.info("Cliente encontrado pelo CNPJ: {}", cnpj);
        return cliente;
    }

    /**
     * Lista clientes de um promotor ordenados pelo valor vendido em um período.
     *
     * Essa consulta apoia a análise de desempenho comercial dos promotores.
     */
    @GetMapping("/promotor/{idPromotor}/valor-vendido")
    public List<Object[]> listarClientesPorValorVendido(
            @PathVariable Integer idPromotor,
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim) {

        logger.info(
                "Consultando clientes do promotor {} por valor vendido entre {} e {}.",
                idPromotor,
                dataInicio,
                dataFim
        );

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
            logger.warn("Tentativa de atualizar cliente inexistente. ID: {}", id);
            return null;
        }

        Promotor promotor = promotorRepository.findById(clienteDTO.getIdPromotor()).orElse(null);
        Municipio municipio = municipioRepository.findById(clienteDTO.getIdMunicipio()).orElse(null);

        if (promotor == null || municipio == null) {
            logger.warn("Tentativa de atualizar cliente {} com promotor ou município inexistente.", id);
            return null;
        }

        aplicarDadosDTO(cliente, clienteDTO, promotor, municipio);

        Cliente clienteAtualizado = repository.save(cliente);

        logger.info("Cliente atualizado com sucesso. ID: {}", id);

        return clienteAtualizado;
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Integer id) {
        logger.info("Excluindo cliente. ID: {}", id);
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

package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.Cliente;
import com.ifmt.sisvendas.repository.ClienteRepository;

class ClienteControllerQueryTests {

    @Test
    void deveBuscarClientePorCnpj() {
        ClienteRepository repository = mock(ClienteRepository.class);
        ClienteController controller = new ClienteController(repository);
        Cliente cliente = new Cliente();

        when(repository.findByCnpj("12345678000199")).thenReturn(Optional.of(cliente));

        Cliente resultado = controller.buscarPorCnpj("12345678000199");

        assertSame(cliente, resultado);
        verify(repository).findByCnpj("12345678000199");
    }

    @Test
    void deveListarClientesDoPromotorOrdenadosPorValorVendidoNoPeriodo() {
        ClienteRepository repository = mock(ClienteRepository.class);
        ClienteController controller = new ClienteController(repository);
        Cliente cliente = new Cliente();
        LocalDate dataInicio = LocalDate.of(2026, 1, 1);
        LocalDate dataFim = LocalDate.of(2026, 1, 31);

        when(repository.buscarClientesPorPromotorOrdenadosPorValorVendido(7, dataInicio, dataFim))
                .thenReturn(List.of(cliente));

        List<Cliente> resultado = controller.listarClientesPorValorVendido(7, dataInicio, dataFim);

        assertSame(cliente, resultado.get(0));
        verify(repository).buscarClientesPorPromotorOrdenadosPorValorVendido(7, dataInicio, dataFim);
    }
}

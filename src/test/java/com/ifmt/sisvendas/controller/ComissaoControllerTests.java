package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.repository.ComissaoRepository;

class ComissaoControllerTests {

    @Test
    void deveListarComissoesPorStatusPromotorEPeriodo() {
        ComissaoRepository repository = mock(ComissaoRepository.class);
        ComissaoController controller = new ComissaoController(repository);
        LocalDate dataInicio = LocalDate.of(2026, 6, 1);
        LocalDate dataFim = LocalDate.of(2026, 6, 7);
        List<Comissao> comissoes = List.of(new Comissao(), new Comissao());

        when(repository.findByStatusAndPromotorIdPromotorAndDataBetween(
                "LANCADA", 1, dataInicio, dataFim)).thenReturn(comissoes);

        List<Comissao> resultado = controller.listarPorStatusPromotorEPeriodo(
                "LANCADA", 1, dataInicio, dataFim);

        assertSame(comissoes, resultado);
        verify(repository).findByStatusAndPromotorIdPromotorAndDataBetween(
                "LANCADA", 1, dataInicio, dataFim);
    }

    @Test
    void deveQuitarComissaoLancada() {
        ComissaoRepository repository = mock(ComissaoRepository.class);
        ComissaoController controller = new ComissaoController(repository);
        Comissao comissao = new Comissao();
        comissao.setStatus("LANCADA");

        when(repository.findById(1)).thenReturn(Optional.of(comissao));
        when(repository.save(comissao)).thenReturn(comissao);

        Comissao resultado = controller.quitar(1);

        assertSame(comissao, resultado);
        assertEquals("QUITADA", resultado.getStatus());
        verify(repository).save(comissao);
    }
}

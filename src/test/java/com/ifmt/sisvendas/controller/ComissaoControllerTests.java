package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.Comissao;
import com.ifmt.sisvendas.repository.ComissaoRepository;

class ComissaoControllerTests {

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

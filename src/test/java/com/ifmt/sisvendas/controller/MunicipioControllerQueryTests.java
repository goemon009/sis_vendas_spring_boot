package com.ifmt.sisvendas.controller;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ifmt.sisvendas.model.Municipio;
import com.ifmt.sisvendas.repository.MunicipioRepository;
import com.ifmt.sisvendas.repository.RegiaoRepository;

class MunicipioControllerQueryTests {

    @Test
    void deveListarMunicipiosAtendidosPorPromotor() {
        MunicipioRepository repository = mock(MunicipioRepository.class);
        MunicipioController controller = new MunicipioController(repository, mock(RegiaoRepository.class));
        List<Municipio> municipios = List.of(new Municipio(), new Municipio());

        when(repository.buscarMunicipiosPorPromotor(1)).thenReturn(municipios);

        List<Municipio> resultado = controller.listarPorPromotor(1);

        assertSame(municipios, resultado);
        verify(repository).buscarMunicipiosPorPromotor(1);
    }
}

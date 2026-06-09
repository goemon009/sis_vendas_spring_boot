package com.ifmt.sisvendas.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.ifmt.sisvendas.model.CategoriaProduto;
import com.ifmt.sisvendas.repository.CategoriaProdutoRepository;
import com.ifmt.sisvendas.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("h2")
class ProdutoControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaRepository;

    @BeforeEach
    void limparBanco() {
        produtoRepository.deleteAll();
        categoriaRepository.deleteAll();
    }

    @Test
    void deveCadastrarProdutoUsandoCategoriaComoNumero() throws Exception {
        CategoriaProduto categoria = criarCategoria();

        String payload = """
            {
              "nome": "Teclado",
              "vlCusto": 80.00,
              "qtdEstoque": 10,
              "qntdReservadaProduto": 1,
              "qntdMinEstoque": 2,
              "qntdMaxEstoque": 30,
              "percentualComissao": 8.5,
              "percentualPromocao": 2.0,
              "margemLucro": 25.0,
              "categoriaProduto": %d
            }
            """.formatted(categoria.getIdCategoriaProduto());

        mockMvc.perform(post("/produtos")
                .contentType(APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nome").value("Teclado"))
            .andExpect(jsonPath("$.categoriaProduto.idCategoriaProduto").value(categoria.getIdCategoriaProduto()))
            .andExpect(jsonPath("$.categoriaProduto.nome").value("Eletronicos"));
    }

    @Test
    void deveRetornar400QuandoCategoriaNaoExistir() throws Exception {
        String payload = """
            {
              "nome": "Mouse",
              "vlCusto": 50.00,
              "qtdEstoque": 10,
              "qntdReservadaProduto": 1,
              "qntdMinEstoque": 2,
              "qntdMaxEstoque": 30,
              "percentualComissao": 8.5,
              "percentualPromocao": 2.0,
              "margemLucro": 25.0,
              "idCategoriaProduto": 999
            }
            """;

        mockMvc.perform(post("/produtos")
                .contentType(APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message").value("CategoriaProduto invalida ou inexistente."));
    }

    private CategoriaProduto criarCategoria() {
        CategoriaProduto categoria = new CategoriaProduto();
        categoria.setNome("Eletronicos");
        categoria.setPercentualComissao(new BigDecimal("10.50"));
        categoria.setPercentualDesconto(new BigDecimal("5.00"));
        return categoriaRepository.save(categoria);
    }
}

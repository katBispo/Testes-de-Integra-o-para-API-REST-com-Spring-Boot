package com.example.demo.service;

import com.example.demo.exception.CidadeException;
import com.example.demo.model.Cidade;
import com.example.demo.repository.CidadeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CidadeServiceTest {

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cidade cidade;

    @BeforeEach
    public void setUp() {
        cidadeRepository.deleteAll();

        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setUf("SP");
        cidade.setTaxa(15.5f);
    }

    @Test
    public void cadastrarCidadeComCamposObrigatoriosNulosDeveLancarException() {
        CidadeException exception = assertThrows(CidadeException.class,
                () -> {
                    cidade.setNome(null);
                    cidadeService.salvar(cidade);
                });

        exception = assertThrows(CidadeException.class,
                () -> {
                    cidade.setNome("São Paulo");
                    cidade.setUf(null);
                    cidadeService.salvar(cidade);
                });

        exception = assertThrows(CidadeException.class,
                () -> {
                    cidade.setUf("SP");
                    cidade.setTaxa(null);
                    cidadeService.salvar(cidade);
                });
    }

    @Test
    public void deveSalvarUmaNovaCidade() {
        cidadeService.salvar(cidade);
        List<Cidade> cidades = cidadeRepository.findAll();
        assertEquals(1, cidades.size());
    }

    @Test
    public void deveBuscarCidadePorNome() {
        cidadeService.salvar(cidade);

        Cidade c = cidadeService.buscarPorNome("São Paulo")
                        .orElseThrow(() -> new RuntimeException("Cidade não encontrada"));
        assertNotNull(c);
        assertEquals("São Paulo", c.getNome());
    }
}

package com.example.demo.repository;

import com.example.demo.model.Cidade;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CidadeRepositoryTest {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Test
    public void deveCadastrarCidadeComSucesso() {
        Cidade cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setUf("SP");
        cidade.setTaxa(15.5f);

        Cidade salva = cidadeRepository.save(cidade);
        assertNotNull(salva.getCodigo());
    }

    @Test
    public void naoDeveCadastrarCidadeComCamposNulos() {
        Cidade cidade = new Cidade(); // faltam nome, UF e taxa

        assertThrows(ConstraintViolationException.class, () -> {
            cidadeRepository.saveAndFlush(cidade);
        });
    }

    @Test
    public void deveBuscarCidadePorNome() {
        Cidade cidade = new Cidade();
        cidade.setNome("Fortaleza");
        cidade.setUf("CE");
        cidade.setTaxa(8.5f);
        cidadeRepository.save(cidade);

        Optional<Cidade> resultado = cidadeRepository.findByNome("Fortaleza");
        assertTrue(resultado.isPresent());
        assertEquals("CE", resultado.get().getUf());
    }
}
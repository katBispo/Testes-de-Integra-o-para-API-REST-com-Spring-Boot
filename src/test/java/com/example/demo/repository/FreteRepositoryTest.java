package com.example.demo.repository;


import com.example.demo.model.Cidade;
import com.example.demo.model.Cliente;
import com.example.demo.model.Frete;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class FreteRepositoryTest {

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cliente cliente;
    private Cidade cidade;

    @BeforeEach
    public void setup() {
        cliente = new Cliente();
        cliente.setNome("Ana");
        cliente.setEndereco("Av. Central");
        cliente.setTelefone("(11) 91234-5678");
        cliente = clienteRepository.save(cliente);

        cidade = new Cidade();
        cidade.setNome("Recife");
        cidade.setUf("PE");
        cidade.setTaxa(10.0f);
        cidade = cidadeRepository.save(cidade);
    }

    @Test
    public void deveCadastrarFreteComSucesso() {
        Frete frete = new Frete();
        frete.setDescricao("Entrega de livros");
        frete.setPeso(12.5f);
        frete.setValor(35.0f);
        frete.setCliente(cliente);
        frete.setCidade(cidade);

        Frete salvo = freteRepository.save(frete);
        assertNotNull(salvo.getCodigo());
    }

    @Test
    public void naoDeveCadastrarFreteComCamposNulos() {
        Frete frete = new Frete(); // sem preencher

        assertThrows(ConstraintViolationException.class, () -> {
            freteRepository.saveAndFlush(frete);
        });
    }

    @Test
    public void deveBuscarFretesPorClienteOrdenadosPorValor() {
        Frete f1 = new Frete("Frete 1", 10f, 50f, cliente, cidade);
        Frete f2 = new Frete("Frete 2", 5f, 20f, cliente, cidade);
        Frete f3 = new Frete("Frete 3", 7f, 40f, cliente, cidade);

        freteRepository.saveAll(List.of(f1, f2, f3));

        List<Frete> resultado = freteRepository.findByClienteOrderByValorAsc(cliente);
        assertEquals(3, resultado.size());
        assertEquals("Frete 2", resultado.get(0).getDescricao());
        assertEquals("Frete 3", resultado.get(1).getDescricao());
        assertEquals("Frete 1", resultado.get(2).getDescricao());
    }
}
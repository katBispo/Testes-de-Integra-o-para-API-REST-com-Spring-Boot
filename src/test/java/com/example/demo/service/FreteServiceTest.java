package com.example.demo.service;

import com.example.demo.exception.FreteException;
import com.example.demo.model.Cidade;
import com.example.demo.model.Cliente;
import com.example.demo.model.Frete;
import com.example.demo.repository.CidadeRepository;
import com.example.demo.repository.ClienteRepository;
import com.example.demo.repository.FreteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FreteServiceTest {

    @Autowired
    private FreteService freteService;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private Cliente cliente;
    private Cidade cidade;

    @BeforeEach
    public void setUp() {
        freteRepository.deleteAll();
        clienteRepository.deleteAll();
        cidadeRepository.deleteAll();

        cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEndereco("Rua A");
        cliente.setTelefone("999999999");
        clienteRepository.save(cliente);

        cidade = new Cidade();
        cidade.setNome("Rio de Janeiro");
        cidade.setUf("RJ");
        cidade.setTaxa(20f);
        cidadeRepository.save(cidade);
    }

    @Test
    public void cadastrarFreteComClienteOuCidadeNaoCadastradosDeveLancarException() {
        FreteException exception = assertThrows(FreteException.class,
                () -> {
                    Frete frete = new Frete();
                    frete.setDescricao("Teste");
                    frete.setPeso(10f);
                    frete.setValor(10f);

                    frete.setCliente(null); 
                    frete.setCidade(cidade);
                    freteService.cadastrarFrete(frete);
                });
        assertTrue(exception.getMessage().contains("Cliente não cadastrado"));

        exception = assertThrows(FreteException.class,
                () -> {
                    Frete frete = new Frete();
                    frete.setDescricao("Teste");
                    frete.setPeso(10f);
                    frete.setValor(10f);
                    frete.setCliente(cliente);
                    frete.setCidade(null); 
                    freteService.cadastrarFrete(frete);
                    
                });
        assertTrue(exception.getMessage().contains("Cidade não cadastrada"));
    }

    @Test
    public void deveSalvarNovoFrete() {
        Frete frete = new Frete();
        frete.setDescricao("Entrega 1");
        frete.setPeso(5f);
        frete.setValor(10f);
        frete.setCliente(cliente);
        frete.setCidade(cidade);

        freteService.cadastrarFrete(frete);

        List<Frete> fretes = freteRepository.findAll();
        assertEquals(1, fretes.size());
    }

    @Test
    public void deveRetornarFretesDeClienteOrdenadosPorValor() {
        Frete f1 = new Frete();
        f1.setDescricao("Frete 1");
        f1.setPeso(5f);
        f1.setValor(10f);
        f1.setCliente(cliente);
        f1.setCidade(cidade);
        freteService.cadastrarFrete(f1);

        Frete f2 = new Frete();
        f2.setDescricao("Frete 2");
        f2.setPeso(10f);
        f2.setValor(10f);
        f2.setCliente(cliente);
        f2.setCidade(cidade);
        freteService.cadastrarFrete(f2);

        List<Frete> fretes = freteService.listarPorClienteOrdenadoPorValor(cliente.getCodigoCliente());
        assertEquals(2, fretes.size());
        assertTrue(fretes.get(0).getValor() <= fretes.get(1).getValor());
    }

}

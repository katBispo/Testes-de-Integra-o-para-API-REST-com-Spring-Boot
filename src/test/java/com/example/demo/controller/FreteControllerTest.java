package com.example.demo.controller;


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
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FreteControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    private String baseUrl;

    private RestTemplate restTemplate = new RestTemplate();

    private Cliente cliente;
    private Cidade cidade;

    @BeforeEach
    public void setup() {
        baseUrl = "http://localhost:" + port + "/fretes";
        freteRepository.deleteAll();
        clienteRepository.deleteAll();
        cidadeRepository.deleteAll();

        cliente = new Cliente();
        cliente.setNome("João");
        cliente.setTelefone("11999999999");
        cliente = clienteRepository.save(cliente);

        cidade = new Cidade();
        cidade.setNome("São Paulo");
        cidade.setUf("SP");
        cidade.setTaxa(10f);
        cidade = cidadeRepository.save(cidade);
    }

    @Test
    public void deveCadastrarFreteComSucesso() {
        Frete frete = new Frete();
        frete.setPeso(2.0f);
        frete.setCliente(cliente);
        frete.setCidade(cidade);

        ResponseEntity<Frete> response = restTemplate.postForEntity(baseUrl, frete, Frete.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getValor() > 0);
    }

    @Test
    public void deveListarFretesPorCliente() {
        Frete frete = new Frete();
        frete.setPeso(2.0f);
        frete.setCliente(cliente);
        frete.setCidade(cidade);
        freteRepository.save(frete);

        String url = baseUrl + "/cliente/" + cliente.getCodigoCliente();
        ResponseEntity<Frete[]> response = restTemplate.getForEntity(url, Frete[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
    }

    @Test
    public void deveLancarExcecaoParaClienteInexistente() {
        Frete frete = new Frete();
        frete.setPeso(2.0f);

        Cliente clienteInexistente = new Cliente();
        clienteInexistente.setCodigoCliente(999);
        frete.setCliente(clienteInexistente);
        frete.setCidade(cidade);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(baseUrl, frete, Frete.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getResponseBodyAsString().contains("Cliente não cadastrado"));
    }

    @Test
    public void deveLancarExcecaoParaCidadeInexistente() {
        Frete frete = new Frete();
        frete.setPeso(2.0f);

        Cidade cidadeInexistente = new Cidade();
        cidadeInexistente.setCodigo(999);
        frete.setCidade(cidadeInexistente);
        frete.setCliente(cliente);

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> {
            restTemplate.postForEntity(baseUrl, frete, Frete.class);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getResponseBodyAsString().contains("Cidade não cadastrada"));
    }
}

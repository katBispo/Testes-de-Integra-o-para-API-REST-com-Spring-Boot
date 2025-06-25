package com.example.demo.service;

import com.example.demo.exception.ClienteException;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClienteServiceTest {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteRepository clienteRepository;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        clienteRepository.deleteAll(); 

        cliente = new Cliente();
        cliente.setNome("João Silva");
        cliente.setEndereco("Rua A, 123");
        cliente.setTelefone("999999999");
    }

    @Test
    public void cadastrarClienteComTodosCamposObrigatorios() {
        Cliente salvo = clienteService.salvar(cliente);
        assertNotNull(salvo.getCodigoCliente());

        List<Cliente> clientes = clienteRepository.findAll();
        assertEquals(1, clientes.size());
    }

    @Test
    public void cadastrarClienteComNomeNuloDeveLancarException() {
        cliente.setNome(null);
        Exception exception = assertThrows(ClienteException.class, () -> {
            clienteService.salvar(cliente);
        });

        String expectedMessage = "Nome é obrigatório";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }

    @Test
    public void cadastrarClienteComTelefoneNuloDeveLancarException() {
        cliente.setTelefone(null);
        Exception exception = assertThrows(ClienteException.class, () -> {
            clienteService.salvar(cliente);
        });

        String expectedMessage = "Telefone é obrigatório";
        assertTrue(exception.getMessage().contains(expectedMessage));
    }
}

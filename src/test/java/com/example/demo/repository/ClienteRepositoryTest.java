package com.example.demo.repository;

import com.example.demo.model.Cliente;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    public void deveCadastrarClienteComSucesso() {
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEndereco("Rua A");
        cliente.setTelefone("(11) 91234-5678");

        Cliente salvo = clienteRepository.save(cliente);
        assertNotNull(salvo.getCodigoCliente());
    }

    @Test
    public void naoDeveCadastrarClienteComCamposNulos() {
        Cliente cliente = new Cliente(); 

        assertThrows(ConstraintViolationException.class, () -> {
            clienteRepository.saveAndFlush(cliente);
        });
    }

    @Test
    public void deveBuscarClientePorTelefone() {
        Cliente cliente = new Cliente();
        cliente.setNome("Maria");
        cliente.setEndereco("Rua B");
        cliente.setTelefone("(11) 91234-5678");
        clienteRepository.save(cliente);

        Optional<Cliente> resultado = clienteRepository.findByTelefone("(11) 91234-5678");
        assertTrue(resultado.isPresent());
        assertEquals("Maria", resultado.get().getNome());
    }
}

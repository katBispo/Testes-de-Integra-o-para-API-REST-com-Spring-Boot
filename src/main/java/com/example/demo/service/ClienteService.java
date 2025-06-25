package com.example.demo.service;
import com.example.demo.exception.ClienteException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import java.util.Optional;

import java.util.List;
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (ConstraintViolationException e) {
            throw new ClienteException(
                    e.getConstraintViolations().stream().map(v -> v.getMessage()).findFirst().orElse("Dados inválidos")
            );
        }
    }


    public Optional<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefone(telefone);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}
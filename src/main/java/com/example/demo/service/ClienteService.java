package com.example.demo.service;
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
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorTelefone(String telefone) {
        return clienteRepository.findByTelefone(telefone);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }
}
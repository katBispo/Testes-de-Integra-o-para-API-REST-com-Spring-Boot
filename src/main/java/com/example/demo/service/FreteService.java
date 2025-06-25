package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;

import java.util.List;
@Service
public class FreteService {

    private static final float VALOR_FIXO = 10.0f;

    @Autowired
    private FreteRepository freteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    public Frete cadastrarFrete(Frete frete) {
        if (frete.getCliente() == null || !clienteRepository.existsById(frete.getCliente().getCodigoCliente())) {
            throw new IllegalArgumentException("Cliente não cadastrado");
        }

        if (frete.getCidade() == null || !cidadeRepository.existsById(frete.getCidade().getCodigo())) {
            throw new IllegalArgumentException("Cidade não cadastrada");
        }

        float valorCalculado = frete.getPeso() * VALOR_FIXO + frete.getCidade().getTaxa();
        frete.setValor(valorCalculado);

        return freteRepository.save(frete);
    }

    public List<Frete> listarTodos() {
        return freteRepository.findAll();
    }

    public List<Frete> listarPorClienteOrdenadoPorValor(Integer codigoCliente) {
        return freteRepository.findByClienteCodigoClienteOrderByValorAsc(codigoCliente);
    }

    public Frete freteComMaiorValor() {
        return freteRepository.findTopByOrderByValorDesc();
    }

    public Cidade cidadeComMaisFretes() {
        return freteRepository.cidadeComMaisFretes();
    }
    
}



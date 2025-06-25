package com.example.demo.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.repository.*;
import com.example.demo.model.*;
import java.util.Optional;
import java.util.List;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Optional<Cidade> buscarPorNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
}

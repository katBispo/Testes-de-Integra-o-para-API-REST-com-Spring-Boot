package com.example.demo.service;
import com.example.demo.exception.CidadeException;
import com.example.demo.exception.ClienteException;
import jakarta.validation.ConstraintViolationException;
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
        try {
            return cidadeRepository.save(cidade);
        }catch (ConstraintViolationException e) {
            throw new CidadeException(
                    e.getConstraintViolations().stream().map(v -> v.getMessage()).findFirst().orElse("Dados inválidos")
            );
        }
    }

    public Optional<Cidade> buscarPorNome(String nome) {
        return cidadeRepository.findByNome(nome);
    }

    public List<Cidade> listarTodas() {
        return cidadeRepository.findAll();
    }
}

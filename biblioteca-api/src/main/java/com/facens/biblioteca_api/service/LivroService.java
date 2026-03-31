package com.facens.biblioteca_api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.facens.biblioteca_api.model.Livro;
import com.facens.biblioteca_api.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public List<Livro> listarTodo() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    public Livro criar(Livro livro) {
        livro.setId(null);
        if(livro.getEmprestado() == null) {
            livro.setEmprestado(false);
        }

        if(Boolean.FALSE.equals(livro.getEmprestado())) {
            livro.setDataEmprestimo(null);
        }

        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro livro = buscarPorId(id);
        livro.setAutor(livroAtualizado.getAutor());
        livro.setTitulo(livro.getTitulo());

        if(livroAtualizado.getEmprestado() != null) {
            livro.setEmprestado(livroAtualizado.getEmprestado());
            if(Boolean.TRUE.equals(livroAtualizado.getEmprestado()) && livroAtualizado.getDataEmprestimo() == null) {
                livro.setDataEmprestimo(LocalDate.now());
            } else if (Boolean.FALSE.equals(livroAtualizado.getEmprestado())) {
                livro.setDataEmprestimo(null);
            } else {
                livro.setDataEmprestimo(livroAtualizado.getDataEmprestimo());
            }
        }

        return livroRepository.save(livro);
    }

    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }

    public Livro emprestar(Long id) {
        Livro livro = buscarPorId(id);

        if(Boolean.TRUE.equals(livro.getEmprestado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro já está emprestado");
        }

        livro.setEmprestado(true);
        livro.setDataEmprestimo(LocalDate.now());
        return livroRepository.save(livro);
    }

    public Livro devolver(Long id) {
        Livro livro = buscarPorId(id);

        if(Boolean.FALSE.equals(livro.getEmprestado())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Livro não está emprestado");
        }

        livro.setEmprestado(false);
        livro.setDataEmprestimo(null);

        return livroRepository.save(livro);
    }
}
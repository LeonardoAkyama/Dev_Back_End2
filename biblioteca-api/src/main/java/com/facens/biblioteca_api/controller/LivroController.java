package com.facens.biblioteca_api.controller;



    import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.facens.biblioteca_api.model.Livro;
import com.facens.biblioteca_api.service.LivroService;


@RestController
@RequestMapping("/livros")
public class LivroController {

    public final LivroService livroService;

    public LivroController (LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public List<Livro> getLivros() {
        return livroService.listarTodo();
    }

    @GetMapping("/teste")
    public String testandoAPI() {
        return "API Rodando normalmente";
    }

    @GetMapping("/{id}")
    public Livro BuscaPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro criarLivro (@RequestBody Livro livro) {
        return livroService.criar(livro);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLivro(@PathVariable Long id) {
        livroService.deletar(id);
    }

    @PutMapping("/{id}")
    public Livro atualizarLivro(@PathVariable Long id, @RequestBody Livro livroAtualizado) {
        return livroService.atualizar(id, livroAtualizado);
    }

    @PutMapping("/{id}/emprestar")
    public Livro emprestarLivro(@PathVariable Long id) {
        return livroService.emprestar(id);
    }

    @PutMapping("/{id}/devolver")
    public Livro devolverLivro(@PathVariable Long id) {
        return livroService.devolver(id);
    }

}



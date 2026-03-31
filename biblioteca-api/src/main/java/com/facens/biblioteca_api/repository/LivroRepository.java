package com.facens.biblioteca_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.facens.biblioteca_api.model.Livro;

public interface LivroRepository extends JpaRepository<Livro, Long>{

}
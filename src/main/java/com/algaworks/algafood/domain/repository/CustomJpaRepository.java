package com.algaworks.algafood.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

//5.20. Estendendo o JpaRepository para customizar o repositório base - 3'30"

@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID>{

	Optional<T> buscarPrimeiro();
}

package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

//5.3. Criando um repositório com Spring Data JPA (SDJ)
@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long>{

	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	//5.8. Conhecendo os prefixos de query methods - 8'30"
	boolean existsByNome(String nome);
}

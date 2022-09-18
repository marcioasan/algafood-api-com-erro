package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

//5.11. Implementando um repositório SDJ customizado - 13'30", 5.17. Implementando Specifications com SDJ - 6' fala sobre JpaSpecificationExecutor
//5.20. Estendendo o JpaRepository para customizar o repositório base - 6'14" - extends CustomJpaRepository
@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{
	
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//5.9. Usando queries JPQL customizadas com @Query
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinha); //Chamando essa consulta sem a anotação @Query, irá executar a consulta configurada no arquivo orm.xml - 5.10. Externalizando consultas JPQL para um arquivo XML
	
//	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	//5.8. Conhecendo os prefixos de query methods - 2'40"
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	//5.8. Conhecendo os prefixos de query methods - 5'
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	//5.8. Conhecendo os prefixos de query methods - 9'35"
	int countByCozinhaId(Long cozinha);
}

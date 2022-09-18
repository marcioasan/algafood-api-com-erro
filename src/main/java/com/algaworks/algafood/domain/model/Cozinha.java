package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Data;
import lombok.EqualsAndHashCode;


@JsonRootName("cozinha") //4.15. Customizando as representações XML e JSON com @JsonIgnore, @JsonProperty e @JsonRootName - 7'
@Data //3.15. Conhecendo e usando o Lombok - 9'
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cozinha {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//4.15. Customizando as representações XML e JSON com @JsonIgnore, @JsonProperty e @JsonRootName - 3'30" - @JsonProperty muda o nome da representação que será devolvida para 'titulo', mas o nome no domínio continuará sendo 'nome'
	//@JsonIgnore
	//@JsonProperty("titulo")
	@Column(nullable = false)
	private String nome;
}

package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

//4.16. Customizando a representação em XML com Wrapper e anotações do Jackson - 3'30", 4'20", 7'30", 10'
@JacksonXmlRootElement(localName = "cozinhas") //poderia usar também @JsonRootName("cozinhas")
@Data
public class CozinhasXmlWrapper {

	@JsonProperty("cozinha") //representa uma cozinha
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull //Essa anotação do Lombok gera um construtor para a classe CozinhasXmlWrapper que recebe cozinha como parâmetro
	private List<Cozinha> cozinhas;
}

//*** Essa classe foi removida na aula 4.29. Desafio: modelando e implementando a consulta de recursos de restaurantes, mas eu mantive
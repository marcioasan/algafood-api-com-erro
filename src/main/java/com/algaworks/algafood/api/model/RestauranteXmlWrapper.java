package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

//*** Ver explicações sobre as anotações em CozinhasXmlWrapper.java ***


@JacksonXmlRootElement
@Data
public class RestauranteXmlWrapper {

	@JsonProperty("restaurante") //representa um restaurante
	@JacksonXmlElementWrapper(useWrapping = false)
	@NonNull
	private List<Restaurante> restaurantes;
}

package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.api.model.RestauranteXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	//4.16. Customizando a representação em XML com Wrapper e anotações do Jackson - 5'
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public RestauranteXmlWrapper listarXml(){
		return new RestauranteXmlWrapper(restauranteRepository.findAll());
	}
	
	@GetMapping("/{idRestaurante}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long idRestaurante) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(idRestaurante);
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){//4.30. Modelando e implementando a inclusão de recursos de restaurantes - 21' ResponseEntity<?> 
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")	
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
			
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");//4.25. Modelando e implementando a atualização de recursos com PUT - 9' - O parâmetro "id" será ignorado no copyProperties
				Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalvo);				
			}
			return ResponseEntity.notFound().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	//4.33. Analisando solução para atualização parcial de recursos com PATCH
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) {
		
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		
		merge(campos, restauranteAtual.get());
		
		return atualizar(restauranteId, restauranteAtual.get());
	}

	//4.34. Finalizando a atualização parcial com a API de Reflections do Spring - 2'30", 5'50", 8'50" 
	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		System.out.println("********** " + restauranteOrigem);
		
		
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
						
			System.out.println("---> " + nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

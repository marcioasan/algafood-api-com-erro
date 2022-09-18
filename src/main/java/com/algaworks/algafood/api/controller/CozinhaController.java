package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.CozinhasXmlWrapper;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas") //quando passa só um parâmetro, não precisa da palavra 'value'
//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	//4.13. Implementando content negotiation para retornar JSON ou XML - 6'
//	@GetMapping(produces = {MediaType.APPLICATION_XML_VALUE})
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) //pode ser colocado no escopo da classe '@RequestMapping'	
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}

	//4.16. Customizando a representação em XML com Wrapper e anotações do Jackson - 5'
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXmlWrapper listarXml(){
		return new CozinhasXmlWrapper(cozinhaRepository.findAll());
	}
	
	//4.20. Manipulando a resposta HTTP com ResponseEntity - 1'
	//5.4. Refatorando o código do projeto para usar o repositório do SDJ
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Optional<Cozinha> cozinha =  cozinhaRepository.findById(cozinhaId);
		
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		//ou
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinha);
		return cozinhaSalva;
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		
		if (cozinhaAtual.isPresent()) {
//			cozinhaAtual.setNome(cozinha.getNome());
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");//4.25. Modelando e implementando a atualização de recursos com PUT - 9' - O parâmetro "id" será ignorado no copyProperties 
			
			Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinhaAtual.get());
			return ResponseEntity.ok(cozinhaSalva);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	//4.26. Modelando e implementando a exclusão de recursos com DELETE
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<?> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
}

/* Requests
Post com json - Content-Type - application/json
{    
    "nome": "Americana"
}

Post com xml - Content-Type - application/xml
<cozinha>    
	<nome>Japonesa</nome>
</cozinha>
*/
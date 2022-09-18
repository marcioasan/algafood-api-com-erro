package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public Cidade salvar(Cidade cidade) {
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		System.out.println("***** DELETANDO CIDADE *****");
		try {
			cidadeRepository.deleteById(cidadeId);			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um estado com o id %d", cidadeId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removido, pois está em uso", cidadeId));
		}		
	}
}

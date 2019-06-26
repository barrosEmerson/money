package br.com.barrostech.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.barrostech.model.Pessoa;
import br.com.barrostech.repositories.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	public Pessoa update(Long codigo, Pessoa pessoa) {
		Pessoa pessoaSalva = findById(codigo);
		BeanUtils.copyProperties(pessoa, pessoaSalva, "codigo");
		pessoaRepository.save(pessoaSalva);
		return pessoaSalva;
	}
	
	
	public void updatePropertyActive(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = findById(codigo);
		pessoaSalva.setAtivo(ativo);
		pessoaRepository.save(pessoaSalva);
	}

	public Pessoa findById(Long codigo) {
		Pessoa pessoaSalva = pessoaRepository.findOne(codigo);
		if(pessoaSalva == null) {
			 throw new EmptyResultDataAccessException(1);
		}
		return pessoaSalva;
	}



}

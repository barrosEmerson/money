package br.com.barrostech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.barrostech.model.Lancamento;
import br.com.barrostech.model.Pessoa;
import br.com.barrostech.repositories.LancamentoRepository;
import br.com.barrostech.repositories.PessoaRepository;
import br.com.barrostech.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private LancamentoRepository lancamentoRepository;

	public Lancamento save(Lancamento lancamento) {
		Pessoa pessoa = pessoaRepository.findOne(lancamento.getPessoa().getCodigo());
		
		if(pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
		
		return lancamentoRepository.save(lancamento);
	}
	
	public Lancamento findById(Long id) {
		Lancamento lanc = lancamentoRepository.findOne(id);
		
		if (lanc == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return lanc;
	}
	
	public void delete(Long id) {
		Lancamento lanc = findById(id);
		
		lancamentoRepository.delete(lanc.getCodigo());
	}

}

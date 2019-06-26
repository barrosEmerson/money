package br.com.barrostech.repositories.lancamento;

import java.util.List;

import br.com.barrostech.model.Lancamento;
import br.com.barrostech.repositories.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	
	public List<Lancamento>filtrar(LancamentoFilter lancamentoFIlter);

}

package br.com.barrostech.repositories.lancamento;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.barrostech.model.Lancamento;
import br.com.barrostech.repositories.filter.LancamentoFilter;

public class LancametoRepositoryImpl implements LancamentoRepositoryQuery {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento>criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento>root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarResticoes(lancamentoFilter,builder,root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		return query.getResultList();
		
	}

	private Predicate[] criarResticoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		
		List<Predicate> predicates = new ArrayList<>();
		
		if(!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")),"%"+ lancamentoFilter.getDescricao().toLowerCase()+"%"));
			
		}
		if(lancamentoFilter.getDataVencimentoDe()!=null) {
//			predicates.add(e);
			
		}
		if(lancamentoFilter.getDataVencimentoAte()!=null) {
//			predicates.add(e);
			
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

}
 	
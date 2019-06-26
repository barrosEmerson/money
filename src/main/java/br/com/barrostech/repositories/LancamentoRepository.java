package br.com.barrostech.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.barrostech.model.Lancamento;

@Repository
public interface LancamentoRepository extends PagingAndSortingRepository<Lancamento, Long>{
	
	@Query("select la from Lancamento la where (:descricao is null or descricao like %:descricao%) and ((:dataDe is null and :dataAte is null) or (dataVencimento between :dataDe and :dataAte))")
	public Page<Lancamento> findByLancamentoFilter(@Param("descricao") String descricao, @Param("dataDe") LocalDate dataVencimentoDe, @Param("dataAte") LocalDate dataVencimentoAte, Pageable pageable );
	

}

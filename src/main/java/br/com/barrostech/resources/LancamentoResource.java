package br.com.barrostech.resources;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.barrostech.event.ResourceCreateEvent;
import br.com.barrostech.exceptionhandler.MoneyExceptionHandler.Erro;
import br.com.barrostech.model.Lancamento;
import br.com.barrostech.repositories.LancamentoRepository;
import br.com.barrostech.repositories.filter.LancamentoFilter;
import br.com.barrostech.service.LancamentoService;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

	@Autowired
	private LancamentoRepository lancamentoRepository;
	
	@Autowired
	private LancamentoService lancamentoService;
	
	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping
	public Page<Lancamento> pesquisar(LancamentoFilter lancamentoFIlter, Pageable pageable){
		return lancamentoRepository.findByLancamentoFilter(lancamentoFIlter.getDescricao(), lancamentoFIlter.getDataVencimentoDe(),
				lancamentoFIlter.getDataVencimentoAte(), pageable);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Lancamento>findById(@PathVariable Long codigo){
		Lancamento lanc = lancamentoRepository.findOne(codigo);
		
		if(lanc == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(lanc);
	}
	
	@PostMapping
	public ResponseEntity<Lancamento> create(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		Lancamento lanc = lancamentoService.save(lancamento);
		publisher.publishEvent(new ResourceCreateEvent(this, response, lanc.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(lanc);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo){
		lancamentoService.delete(codigo);
	}
	
	@ExceptionHandler({br.com.barrostech.service.exception.PessoaInexistenteOuInativaException.class})
	public ResponseEntity<Object>handlePessoaInexistenteOuInativaException(br.com.barrostech.service.exception.PessoaInexistenteOuInativaException ex){
		String msgUser = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
		String msgDev = ex.toString();
		List<Erro>erros = Arrays.asList(new Erro(msgUser, msgDev));
		return ResponseEntity.badRequest().body(erros);
	}
}

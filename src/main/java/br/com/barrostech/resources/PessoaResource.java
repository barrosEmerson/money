package br.com.barrostech.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
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

import br.com.barrostech.event.ResourceCreateEvent;
import br.com.barrostech.model.Pessoa;
import br.com.barrostech.repositories.PessoaRepository;
import br.com.barrostech.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ApplicationEventPublisher publishser;
	
	@Autowired
	private PessoaService pessoaService;
	
	@GetMapping
	public List<Pessoa> listar(){
		return pessoaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> create(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response) {
		Pessoa pes = pessoaRepository.save(pessoa);
		publishser.publishEvent(new ResourceCreateEvent(this, response, pes.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(pes);
		
	}
	@GetMapping("{codigo}")
	public ResponseEntity<Pessoa> findById(@PathVariable Long codigo) {
		Pessoa pes = pessoaRepository.findOne(codigo);
		if (pes==null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(pes);
	}
	
	@DeleteMapping("/{codigo}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long codigo) {
		pessoaRepository.delete(codigo);
	}
	
	@PutMapping("/{codigo}")
	public ResponseEntity<Pessoa> update(@PathVariable Long codigo,@Valid @RequestBody Pessoa pessoa){
		Pessoa pessoaSalva = pessoaService.update(codigo, pessoa);
		return ResponseEntity.ok(pessoaSalva);
	}
	
	@PutMapping("/{codigo}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePropertyActive(@PathVariable Long codigo, @RequestBody Boolean ativo) {
		pessoaService.updatePropertyActive(codigo, ativo);
	}
}

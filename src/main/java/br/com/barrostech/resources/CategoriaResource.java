package br.com.barrostech.resources;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.barrostech.event.ResourceCreateEvent;
import br.com.barrostech.model.Categoria;
import br.com.barrostech.repositories.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Categoria> listar(){
		return categoriaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Categoria> create(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria cat = categoriaRepository.save(categoria);
		publisher.publishEvent(new ResourceCreateEvent(this, response, cat.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(cat);
		
	}
	
	@GetMapping("{codigo}")
	public ResponseEntity<Categoria> findById(@PathVariable Long codigo) {
		Categoria cat = categoriaRepository.findOne(codigo);
		if (cat==null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(cat);
	}
}

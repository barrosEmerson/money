package br.com.barrostech.event.listener;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.barrostech.event.ResourceCreateEvent;

@Component
public class ResourceCreateListener implements ApplicationListener<ResourceCreateEvent>{

	@Override
	public void onApplicationEvent(ResourceCreateEvent event) {
		HttpServletResponse response = event.getResponse();
		Long codigo = event.getCodigo();
		
		addHeaderLocation(response, codigo);
	}

	private void addHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}

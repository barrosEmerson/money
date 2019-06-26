package br.com.barrostech.exceptionhandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class MoneyExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	private static final Logger Log = LoggerFactory.getLogger(MoneyExceptionHandler.class);
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Log.info("Foram adicionados campos inválidos!");
		String msgUser = messageSource.getMessage("mensagem.invalida", null, LocaleContextHolder.getLocale());
		String msgDev = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
		List<Erro>erros = Arrays.asList(new Erro(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request); 
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,WebRequest request) {
		
		Log.info("Este recurso não existe na base de dados!");
		String msgUser = messageSource.getMessage("recurso.nao-encontrado", null, LocaleContextHolder.getLocale());
		String msgDev = ex.toString();
		List<Erro>erros = Arrays.asList(new Erro(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
	
		List<Erro>erros = listError(ex.getBindingResult());
		return handleExceptionInternal(ex, erros, headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object>handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		Log.info("Este recurso não existe na base de dados!");
		String msgUser = messageSource.getMessage("recurso.operacao-nao-permitida", null, LocaleContextHolder.getLocale());
		String msgDev = ExceptionUtils.getRootCauseMessage(ex);
		List<Erro>erros = Arrays.asList(new Erro(msgUser, msgDev));
		return handleExceptionInternal(ex, erros, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	private List<Erro> listError(BindingResult bindingResult){
		List<Erro>erros = new ArrayList<>();
		
		for(FieldError fieldError : bindingResult.getFieldErrors()) {
			String msgUser = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
			String msgDev = fieldError.toString();
			erros.add(new Erro(msgUser, msgDev));
		}
		
		return erros;
	}
	
	public static class Erro {
		private String msgUser;
		private String msgDev;
		
		public Erro(String msgUser, String msgDev) {
			this.msgUser = msgUser;
			this.msgDev = msgDev;
			
		}

		public String getMsgUser() {
			return msgUser;
		}

		public String getMsgDev() {
			return msgDev;
		}
		
		
	}

}

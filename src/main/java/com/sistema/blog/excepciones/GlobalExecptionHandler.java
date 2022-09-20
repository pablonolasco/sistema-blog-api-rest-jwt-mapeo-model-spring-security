package com.sistema.blog.excepciones;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sistema.blog.dto.ErrorDetalles;

// permite manejar exepciones handler de toda la aplicacion
@ControllerAdvice
public class GlobalExecptionHandler extends ResponseEntityExceptionHandler{

	/**
	 * metodo que controlara todas las excepciones de la aplicacion not found 
	 * @param exception
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetalles>manejadorResourceNotFoundException(ResourceNotFoundException exception,WebRequest webRequest){
		ErrorDetalles errorDetalles= new ErrorDetalles(new Date(),exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetalles,HttpStatus.NOT_FOUND);
	}
	
	/**
	 * metodo que controlara todas las excepciones de la aplicacion bad request
	 * @param exception
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(BlogAppException.class)
	public ResponseEntity<ErrorDetalles>manejadorResourceNotFoundException(BlogAppException exception,WebRequest webRequest){
		ErrorDetalles errorDetalles= new ErrorDetalles(new Date(),exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetalles,HttpStatus.BAD_REQUEST);
	}
	
	/**
	 * metodo que controla todas las exeception internal server
	 * @param exception
	 * @param webRequest
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetalles>manejadorResourceNotFoundException(Exception exception,WebRequest webRequest){
		ErrorDetalles errorDetalles= new ErrorDetalles(new Date(),exception.getMessage(),webRequest.getDescription(false));
		return new ResponseEntity<>(errorDetalles,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * el dato no sea valido lanzara este metodo
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// TODO Auto-generated method stub
		Map<String, String>errores= new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String nombreCampo=((FieldError) error).getField();
			String mensaje= error.getDefaultMessage();
			errores.put(nombreCampo, mensaje);
		});
		
		return new  ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
	}
}

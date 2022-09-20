package com.sistema.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

import java.util.List;

import javax.validation.Valid;

import com.sistema.blog.services.IPublicacionService;
import com.sistema.blog.utils.AppConstantes;
@RestController
@RequestMapping(value = "/api/publicaciones")
@CrossOrigin
public class PublicacionController {
	
	@Autowired
	private IPublicacionService publicacionService;
	
	@GetMapping("/list")
	public ResponseEntity<List<PublicacionDTO>>obtenerPublicaciones(){
		return new ResponseEntity<List<PublicacionDTO>>(publicacionService.obtenerPublicaciones(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<PublicacionRespuesta> obtenerPublicacionesPaginadas(
			@RequestParam(value = "page", defaultValue = AppConstantes.NUMBER_OF_PAGE,required = false) int numeroPagina,
			@RequestParam(value = "size", defaultValue = AppConstantes.SIZE_PAGE,required = false) int sizePage,
			@RequestParam(value = "orderby",defaultValue = AppConstantes.ORDER_BY,required = false) String order,
			@RequestParam(value="orden", defaultValue = AppConstantes.ORDER_DIRECTION, required = false) String orden
			){
		return new ResponseEntity<PublicacionRespuesta>(publicacionService.obtenerPublicacionesPaginadas(numeroPagina, sizePage,order,orden),HttpStatus.OK);
	}
	
	@GetMapping("/{idPublicacion}")
	public ResponseEntity<PublicacionDTO>obtenerPublicacion(@PathVariable(name = "idPublicacion") Long id){
		//return new ResponseEntity<PublicacionDTO>(publicacionService.obtenerPublicacionPorId(id),HttpStatus.OK);
		return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
	}
	
	/**
	 * @ResponseEntity es el instrumento que nos permite manipular/modificar el HTTP Response (la respuesta). 
	 * Por tanto, podemos afirmar que el Response Entity es el objeto que enviamos en esa respuesta
	 * 
	 * @RequestBody anotación nos permite recuperar el cuerpo de la solicitud. 
	 * Luego podemos devolverlo como una cadena o deserializarlo en un objeto Java antiguo simple (POJO)
	 * @param publicacionDTO
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping()
	public ResponseEntity<PublicacionDTO> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
		return new ResponseEntity<>(publicacionService.crearPublicacionDTO(publicacionDTO),HttpStatus.CREATED);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{idPublicacion}")
	public ResponseEntity<PublicacionDTO>actualizarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO,@PathVariable("idPublicacion") Long id ){
		return new ResponseEntity<PublicacionDTO>(
				this.publicacionService.actualizarPublicacion(publicacionDTO, id),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{idPublicacion}")
	public ResponseEntity<String> eliminarPublicacion(@PathVariable(name="idPublicacion")Long id) {
		publicacionService.eliminarPublicacionDTO(id);
		return new ResponseEntity<>("Publicacion eliminada",HttpStatus.OK);
	}

}

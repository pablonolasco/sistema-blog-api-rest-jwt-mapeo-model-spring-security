package com.sistema.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.ComentariosDTO;
import com.sistema.blog.services.IComentarioService;
import java.util.List;

import javax.validation.Valid;
@RestController
@RequestMapping(value = "/api/")
public class ComentariosCotroller {

	@Autowired
	private IComentarioService comentarioService;
	
	@GetMapping("/publicaciones/{idPublicacion}/comentarios")
	public ResponseEntity<List<ComentariosDTO>>obtenerComentariosIdPublicacion(@PathVariable(name = "idPublicacion",required = true) Long id){
		return new ResponseEntity<List<ComentariosDTO>>(comentarioService.obtenerComentariosPorIdPublicacion(id),HttpStatus.OK);
	}
	
	@GetMapping("/publicaciones/{publicacion_id}/comentarios/{idComentario}")
	public ResponseEntity<ComentariosDTO>obtenerComentarioPorId(
			@PathVariable(name="publicacion_id",required = true)Long publicacion_id,
			@PathVariable(name="idComentario",required = true)Long idComentario
			){
		ComentariosDTO comentariosDTO= comentarioService.obtenerComentarioPorId(publicacion_id,idComentario);
		return new ResponseEntity<ComentariosDTO>(comentariosDTO,HttpStatus.OK);
	}
	
	@PostMapping("/publicaciones/{idPublicacion}/comentarios")
	public ResponseEntity<ComentariosDTO>guardarComentario(@Valid @RequestBody ComentariosDTO comentarioDTO, 
				@PathVariable(name = "idPublicacion", required = true) Long id ){
		return new ResponseEntity<ComentariosDTO>(comentarioService.crearComentario(id, comentarioDTO),HttpStatus.CREATED);
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
	
	@PutMapping("/publicaciones/{publicacionId}/comentarios/{idComentario}")
	public ResponseEntity<ComentariosDTO>actualizarComentario(@Valid @RequestBody ComentariosDTO comentariosDTO,
			@PathVariable(name = "publicacionId",required = true) Long publicacionId,
			@PathVariable(name = "idComentario",required = true)Long idComentario){
		return new ResponseEntity<ComentariosDTO>(comentarioService.actualizarComentario(publicacionId, comentariosDTO, idComentario),HttpStatus.OK);
	}
	
	@DeleteMapping("/publicaciones/{publicacionId}/comentarios/{idComentario}")
	public ResponseEntity<String>eliminarComentario(
			@PathVariable(name = "publicacionId",required = true) Long publicacionId,
			@PathVariable(name = "idComentario",required = true) Long idComentario
			){
		comentarioService.eliminarComentario(publicacionId, idComentario);
		return new ResponseEntity<>("Comentario Eliminado",HttpStatus.OK);
	}
}

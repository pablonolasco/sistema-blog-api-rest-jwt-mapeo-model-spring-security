package com.sistema.blog.services;

import java.util.List;

import com.sistema.blog.dto.ComentariosDTO;
public interface IComentarioService {
	
	ComentariosDTO crearComentario(Long idPublicacion,ComentariosDTO comentarioDTO);
	List<ComentariosDTO>obtenerComentariosPorIdPublicacion(Long id);
	ComentariosDTO obtenerComentarioPorId(Long idPublicacion, Long idComentario);
	ComentariosDTO actualizarComentario(Long publicacionId,ComentariosDTO comentariosDTO, Long idComentario);
	void eliminarComentario(Long publicacion_id,Long idComentario);

}

package com.sistema.blog.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sistema.blog.dto.ComentariosDTO;
import com.sistema.blog.entity.Comentarios;
import com.sistema.blog.entity.Publicacion;
import com.sistema.blog.excepciones.BlogAppException;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repository.IComentariosRepository;
import com.sistema.blog.repository.IPublicacionRepository;

@Service
public class ComentarioServiceImpl implements IComentarioService {

	private static final Logger log = LoggerFactory.getLogger(ComentarioServiceImpl.class);
	
	@Autowired
	private ModelMapper modelMaper;
	
	@Autowired
	private IComentariosRepository comentariosRepository;
	@Autowired
	private IPublicacionRepository publicacionRepository;

	@Override
	public List<ComentariosDTO> obtenerComentariosPorIdPublicacion(Long id) {
		// TODO Auto-generated method stub
		List<Comentarios> listaComentario = comentariosRepository.findByPublicacionId(id);
		// listaComentario=comentariosRepository.find
		return listaComentario.stream().map(comentario -> mapearComentariosDTO(comentario))
				.collect(Collectors.toList());
	}

	@Override
	public ComentariosDTO crearComentario(Long idPublicacion, ComentariosDTO comentarioDTO) {
		Comentarios nuevoComentario = null;
		try {
			Comentarios comentarios = mapearComentarios(comentarioDTO);
			comentarios.setPublicacion(publicacionRepository.getById(idPublicacion));

			nuevoComentario = comentariosRepository.save(comentarios);
		} catch (Exception e) {
			log.debug("error: {}", e.getMessage());
		}
		return mapearComentariosDTO(nuevoComentario);
	}

	@Override
	public ComentariosDTO obtenerComentarioPorId(Long idPublicacion, Long idComentario) {
		Publicacion publicacion= publicacionRepository.findById(idPublicacion)
				.orElseThrow(()->new ResourceNotFoundException("Publicacion", "id", idPublicacion));
		Comentarios comentarios=comentariosRepository.findById(idComentario).
				orElseThrow(()->new ResourceNotFoundException("Comentario", "id", idComentario));
		
		if (!comentarios.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
		
		return mapearComentariosDTO(comentarios);
	}

	@Override
	public ComentariosDTO actualizarComentario(Long publicacionId,ComentariosDTO comentariosDTO, Long idComentario) {
		// TODO Auto-generated method stub
		Publicacion publicacion= publicacionRepository.findById(publicacionId)
				.orElseThrow(()->new ResourceNotFoundException("Publicacion", "id", publicacionId));
		Comentarios comentarios= comentariosRepository.findById(idComentario)
				.orElseThrow(()->new ResourceNotFoundException("Comentario", "id", idComentario));
		
		if (!comentarios.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
		
		comentarios.setNombre(comentariosDTO.getNombre());
		comentarios.setEmail(comentariosDTO.getEmail());
		comentarios.setCuerpo(comentariosDTO.getCuerpo());
		
		Comentarios comentariosActualizado=comentariosRepository.save(comentarios);
		
		return mapearComentariosDTO(comentariosActualizado);
	}

	@Override
	public void eliminarComentario(Long publicacion_id, Long idComentario) {
		// TODO Auto-generated method stub
		
		Publicacion publicacion= publicacionRepository.findById(publicacion_id)
				.orElseThrow(()->new ResourceNotFoundException("Publicacion", "id", publicacion_id));
		Comentarios comentarios = comentariosRepository.findById(idComentario).
				orElseThrow(()->new ResourceNotFoundException("Comentario", "id", idComentario));
		
		if (!comentarios.getPublicacion().getId().equals(publicacion.getId())) {
			throw new BlogAppException(HttpStatus.BAD_REQUEST, "El comentario no pertenece a la publicacion");
		}
		
		comentariosRepository.delete(comentarios);
		
	}

	private ComentariosDTO mapearComentariosDTO(Comentarios comentarios) {
		ComentariosDTO comentariosDTO = null;
		try {
			comentariosDTO= modelMaper.map(comentarios, ComentariosDTO.class);
			/*comentariosDTO = new ComentariosDTO(comentarios.getId(), comentarios.getCuerpo(), comentarios.getEmail(),
					comentarios.getNombre());*/
		} catch (Exception e) {
			log.debug("error: {}", e.getMessage());
		}

		return comentariosDTO;
	}


	private Comentarios mapearComentarios(ComentariosDTO comentariosDTO) {
		Comentarios comentarios = new Comentarios();
		try {
			comentarios= modelMaper.map(comentariosDTO,Comentarios.class);
		/*	comentarios.setId(comentariosDTO.getId());
			comentarios.setNombre(comentariosDTO.getNombre());
			comentarios.setEmail(comentariosDTO.getEmail());
			comentarios.setCuerpo(comentariosDTO.getCuerpo());*/
		} catch (Exception e) {
			log.debug("error: {}", e.getMessage());

		}

		return comentarios;
	}



}

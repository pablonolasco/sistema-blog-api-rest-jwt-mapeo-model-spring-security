package com.sistema.blog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;
import com.sistema.blog.entity.Publicacion;
import com.sistema.blog.excepciones.ResourceNotFoundException;
import com.sistema.blog.repository.IPublicacionRepository;


@Service
@Transactional
public class PublicacionServiceImpl implements IPublicacionService {

	@Autowired
	private ModelMapper modelMaper;
	
	@Autowired
	private IPublicacionRepository publicacionRepository;

	private static final Logger log = LoggerFactory.getLogger(PublicacionServiceImpl.class);

	@Override
	public PublicacionDTO crearPublicacionDTO(PublicacionDTO publicacionDTO) {

		// Entity
		Publicacion publicacion = null;
		// DTO
		PublicacionDTO publicacionRespDto = null;
		try {
			// Convertimos de DTO a entidad
			publicacion = mapearEntidad(publicacionDTO);
			// Guarda en el repository
			Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);
			// Convertimos de entidad a DTO
			publicacionRespDto = mapearDTO(nuevaPublicacion);
		} catch (Exception e) {
			// TODO: handle exception
			log.error("error :".concat(e.getMessage()));
		}
		return publicacionRespDto;
	}

	/**
	 * Mapear cada registro y regresarlo en forma de lista
	 */
	@Override
	public List<PublicacionDTO> obtenerPublicaciones() {
		// TODO Auto-generated method stub
		List<Publicacion> publicaciones = publicacionRepository.findAll();
		return publicaciones.stream().map(publicacion -> mapearDTO(publicacion)).collect(Collectors.toList());
	}


	@Override
	public PublicacionDTO obtenerPublicacionPorId(Long id) {
		Publicacion publicacion = this.publicacionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id));
		return mapearDTO(publicacion);
	}

	@Override
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
		Publicacion nuevaPublicacion = null;
		try {
			Optional<Publicacion> publicacionOptional =Optional.of( this.publicacionRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Publicacion", "id", id)));
			if (publicacionOptional.isPresent()) {
				
				publicacionOptional.get().setTitulo(publicacionDTO.getTitulo());
				publicacionOptional.get().setDescripcion(publicacionDTO.getDescripcion());
				publicacionOptional.get().setContenido(publicacionDTO.getContenido());
				nuevaPublicacion = this.publicacionRepository.save(publicacionOptional.get());
				//	log.info("objeto {}",actualizaPublicacion.getId());
			} else {
				throw new ResourceNotFoundException("Publicacion", "id", id);
			}
			
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,String.format("Error en la peticion: %s ", e.getMessage()));
		}

		return mapearDTO(nuevaPublicacion);
	}

	@Override
	public void eliminarPublicacionDTO(Long id) {
		try {
			Optional<Publicacion>publicacionOptional=Optional.of(publicacionRepository.findById(id)
					.orElseThrow(()->new ResourceNotFoundException("Publicacion", "id", id)));
			if (publicacionOptional.isPresent()) {
				publicacionRepository.delete(publicacionOptional.get());
			} else {
				throw new ResourceNotFoundException("Publicacion", "id", id);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error ".concat(e.getMessage()));
		}
		
	}

	@Override
	public PublicacionRespuesta obtenerPublicacionesPaginadas(int numeroPagina, int sizePage, String order,String tipoOrden) {
		Page<Publicacion>resultPublicaciones=publicacionRepository.findAll(PageRequest.of(numeroPagina, sizePage,Sort.by(Direction.fromString(tipoOrden),order)));
		List<PublicacionDTO>publicacionDTOs= resultPublicaciones.stream().map(publicacion->mapearDTO(publicacion)).collect(Collectors.toList());
		PublicacionRespuesta publicacionRespuesta= new PublicacionRespuesta();
		publicacionRespuesta.setContenido(publicacionDTOs);
		publicacionRespuesta.setNumeroPagina(resultPublicaciones.getNumber());
		publicacionRespuesta.setSizePagina(resultPublicaciones.getSize());
		publicacionRespuesta.setTotalElementos(resultPublicaciones.getTotalElements());
		publicacionRespuesta.setTotalPaginas(resultPublicaciones.getTotalPages());
		publicacionRespuesta.setUltima(resultPublicaciones.isLast());
		return publicacionRespuesta;
	}

	/**
	 * comvierte entidad a dto
	 * 
	 * @return
	 */
	private PublicacionDTO mapearDTO(Publicacion publicacion) {
		
		/*PublicacionDTO publicacionRespDto = new PublicacionDTO(publicacion.getId(), publicacion.getTitulo(),
				publicacion.getDescripcion(), publicacion.getContenido());*/
		PublicacionDTO publicacionRespDto = modelMaper.map(publicacion, PublicacionDTO.class);
		return publicacionRespDto;
	}
	
	/**
	 * convierte de DTO a entidad
	 * 
	 * @param publicacionDTO
	 * @return
	 */
	private Publicacion mapearEntidad(PublicacionDTO publicacionDTO) {
		/*Publicacion publicacion = new Publicacion();
		publicacion.setTitulo(publicacionDTO.getTitulo());
		publicacion.setDescripcion(publicacionDTO.getDescripcion());
		publicacion.setContenido(publicacionDTO.getContenido());*/
		Publicacion publicacion = modelMaper.map(publicacionDTO, Publicacion.class);
		return publicacion;
	}
}

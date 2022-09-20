package com.sistema.blog.services;

import com.sistema.blog.dto.PublicacionDTO;
import com.sistema.blog.dto.PublicacionRespuesta;

import java.util.List;
public interface IPublicacionService {
	public PublicacionDTO crearPublicacionDTO(PublicacionDTO publicacionDTO);
	public List<PublicacionDTO>obtenerPublicaciones();
	//public List<PublicacionDTO>obtenerPublicacionesPaginadas(int numeroPagina, int sizePage);
	public PublicacionRespuesta obtenerPublicacionesPaginadas(int numeroPagina, int sizePage, String order,String tipoOrden);
	public PublicacionDTO obtenerPublicacionPorId(Long id);
	public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);
	public void eliminarPublicacionDTO(Long id);
	
}

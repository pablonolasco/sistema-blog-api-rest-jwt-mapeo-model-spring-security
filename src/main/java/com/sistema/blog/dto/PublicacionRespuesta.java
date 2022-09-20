package com.sistema.blog.dto;

import java.util.List;
public class PublicacionRespuesta {

	private List<PublicacionDTO> contenido;
	private Integer numeroPagina;
	private Integer sizePagina;
	private Long totalElementos;
	private Integer totalPaginas;
	private Boolean ultima;
	
	public PublicacionRespuesta() {
		super();
	}
	
	public PublicacionRespuesta(List<PublicacionDTO> contenido, Integer numeroPagina, Integer sizePagina,
			Long totalElementos, Integer totalPaginas, Boolean ultima) {
		super();
		this.contenido = contenido;
		this.numeroPagina = numeroPagina;
		this.sizePagina = sizePagina;
		this.totalElementos = totalElementos;
		this.totalPaginas = totalPaginas;
		this.ultima = ultima;
	}


	public List<PublicacionDTO> getContenido() {
		return contenido;
	}
	public void setContenido(List<PublicacionDTO> contenido) {
		this.contenido = contenido;
	}
	public Integer getNumeroPagina() {
		return numeroPagina;
	}
	public void setNumeroPagina(Integer numeroPagina) {
		this.numeroPagina = numeroPagina;
	}
	public Integer getSizePagina() {
		return sizePagina;
	}
	public void setSizePagina(Integer sizePagina) {
		this.sizePagina = sizePagina;
	}
	public Long getTotalElementos() {
		return totalElementos;
	}
	public void setTotalElementos(Long totalElementos) {
		this.totalElementos = totalElementos;
	}
	public Integer getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(Integer totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	public Boolean getUltima() {
		return ultima;
	}
	public void setUltima(Boolean ultima) {
		this.ultima = ultima;
	}
	
	
	
}

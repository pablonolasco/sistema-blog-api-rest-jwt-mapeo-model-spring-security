package com.sistema.blog.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sistema.blog.entity.Comentarios;

public class PublicacionDTO {

	
	private Long id;
	
	@NotEmpty
	@Size(min = 2, message = "El Titulo de la publicacion deberia tener al menos 2 caracteres")
	private String titulo;
	
	@NotEmpty
	@Size(min=10, message = "La descripcion de la publicacion deberia tener al menos 2 caracteres")
	private String descripcion;
	
	@NotEmpty
	private String contenido;
	
	private Set<Comentarios>comentarios;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Set<Comentarios> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentarios> comentarios) {
		this.comentarios = comentarios;
	}
	
	public PublicacionDTO() {
		super();
	}
	
	public PublicacionDTO(Long id, String titulo, String descripcion, String contenido) {
		super();
		this.id = id;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.contenido = contenido;
	}
	
}

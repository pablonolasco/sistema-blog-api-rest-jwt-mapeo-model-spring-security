package com.sistema.blog.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ComentariosDTO implements Serializable{

	private static final long serialVersionUID = -8923778983319072201L;
	
	private Long id;
	
	@NotEmpty(message = "El campo nombre es obligatorio")
	private String nombre;
	
	@NotEmpty(message = "El campo email es obligatorio")
	@Email
	private String email;
	
	@Size(min = 2, message = "El cuerpo del comentario debe tener minimo dos letras")
	private String cuerpo;
	
	public ComentariosDTO() {
		super();
	}

	public ComentariosDTO(Long id, String cuerpo, String email, String nombre) {
		super();
		this.id = id;
		this.cuerpo = cuerpo;
		this.email = email;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cuerpo == null) ? 0 : cuerpo.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ComentariosDTO other = (ComentariosDTO) obj;
		if (cuerpo == null) {
			if (other.cuerpo != null)
				return false;
		} else if (!cuerpo.equals(other.cuerpo))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComentariosDTO [id=" + id + ", nombre=" + nombre + ", email=" + email + ", cuerpo=" + cuerpo + "]";
	}
	
	
	
	

}

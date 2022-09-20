package com.sistema.blog.entity;

import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "usuarios", uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),@UniqueConstraint (columnNames = {"email"})})
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@Email
	private String email;
	
	@Column
	@NotEmpty
	private String nombre;
	
	@Column
	@NotEmpty
	@Size(min = 2, message = "La contraseña debe tener mas de dos caracteres")
	private String password;
	
	@Column
	@NotEmpty
	private String username;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "usuarios_roles", 
	joinColumns = @JoinColumn(name="usuario_id",referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name="rol_id",referencedColumnName = "id"))
	private Set<Rol>roles= new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public Usuario() {
		super();
	}

	public Usuario(Long id, @Email String email, @NotEmpty String nombre,
			@NotEmpty @Size(min = 2, message = "La contraseña debe tener mas de dos caracteres") String password,
			@NotEmpty String username) {
		super();
		this.id = id;
		this.email = email;
		this.nombre = nombre;
		this.password = password;
		this.username = username;
	}
	
	
	
}

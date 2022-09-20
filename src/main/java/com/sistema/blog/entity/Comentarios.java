/**
 * 
 */
package com.sistema.blog.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author pnolasco
 *
 *         Existen dos formas de inicializar un bean Eager | Lazy
 * 
 *         Eager: Los beans de tipo eager son inicializados sin importar si se
 *         utilizaran o no dentro de la aplicacion
 * 
 *         Lazy: Los beans de tipo lazy se crean hasta el momento en el que se
 *         utilizaran por primera vez
 * 
 *         Los beans singleton son por default eager, los beans prototype son
 *         por default lazy.
 * 
 *         Si se desea definir un bean singleton como lazy se utiliza la
 *         anotación @Lazy.
 * 
 *         NOTA: Si un bean singleton es lazy pero otro bean que depende de el
 *         no lo es, ambos serán considerados eager.
 */
@Entity
@Table(name = "comentarios")
public class Comentarios {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "cuerpo", nullable = false)
	private String cuerpo;

	// se llama cuando lo necesita
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "publicacion_id", nullable = false)
	private Publicacion publicacion;

	public Comentarios() {
		super();
	}

	
	public Comentarios(Long id, String nombre, String email, String cuerpo, Publicacion publicacion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.cuerpo = cuerpo;
		this.publicacion = publicacion;
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

	public Publicacion getPublicacion() {
		return publicacion;
	}

	public void setPublicacion(Publicacion publicacion) {
		this.publicacion = publicacion;
	}

}

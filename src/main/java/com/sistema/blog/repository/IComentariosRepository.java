package com.sistema.blog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistema.blog.entity.Comentarios;
@Repository
public interface IComentariosRepository extends JpaRepository<Comentarios, Long> {

	public List<Comentarios>findByPublicacionId(Long publicacionId);
	public Optional<Comentarios> findByPublicacionIdAndId(Long publicacionId, Long id);
}

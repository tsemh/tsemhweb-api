package io.tsemh.tsemhapirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.tsemh.tsemhapirest.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{

	List<Categoria> findByTipo(String tipo);
	Categoria findByTitulo(String titulo);
	Categoria findByTituloAndTipo(String titulo, String tipo);
	
}

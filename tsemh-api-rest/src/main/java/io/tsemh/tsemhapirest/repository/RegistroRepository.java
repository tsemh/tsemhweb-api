package io.tsemh.tsemhapirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.tsemh.tsemhapirest.entity.Categoria;
import io.tsemh.tsemhapirest.entity.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Long> {
	
	List<Registro> findByCategoria(Categoria categoria);
	List<Registro> findByTipo(String tipo);
	List<Registro> findByDestaque(Boolean destaque);
    List<Registro> findByDestaqueOrderByDataCriacaoAsc(Boolean destaque);
	
    @Query("SELECT COUNT(r) FROM Registro r WHERE r.destaque = true")
    long countByDestaque(Boolean destaque);
    
    @Query("SELECT DISTINCT r.tipo FROM Registro r")
    List<String> findDistinctTipo();
}
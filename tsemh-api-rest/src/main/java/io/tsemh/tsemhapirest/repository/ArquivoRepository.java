package io.tsemh.tsemhapirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import io.tsemh.tsemhapirest.entity.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{
	
	List<Arquivo> findByTipo(String tipo);

}

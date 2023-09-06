package io.tsemh.tsemhapirest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.tsemh.tsemhapirest.entity.Categoria;
import io.tsemh.tsemhapirest.entity.Registro;
import io.tsemh.tsemhapirest.entity.Usuario;
import io.tsemh.tsemhapirest.repository.CategoriaRepository;
import io.tsemh.tsemhapirest.repository.UsuarioRepository;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository; 
	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/cadastrar")
	@Transactional
	public Categoria postCategoria(@RequestBody Categoria categoria, @RequestParam long idUsuario) {
	    Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
	    if (usuarioOptional.isPresent()) {
	        Usuario usuario = usuarioOptional.get();
	        categoria.setUsuario(usuario);
	        return categoriaRepository.save(categoria);
	    } else {
	        throw new RuntimeException("Usuário não encontrado com ID " + idUsuario);
	    }
	}
	
	@GetMapping
	public List<Categoria> getAllCategoria() {
		return categoriaRepository.findAll();
	}
	
	@GetMapping("{idCategoria}")
	public Categoria getCategoriaById(@PathVariable long idCategoria) {
		return categoriaRepository.findById(idCategoria).get();
	}
	
	@GetMapping("tipo")
	public List<Categoria> getCategoriaByTipo(@RequestParam String tipo) {
	    if(tipo == null) {
	        throw new RuntimeException("Tipo não encontrado");
	    }
	    return categoriaRepository.findByTipo(tipo);
	}
	
	@PutMapping("/editar/{idCategoria}")
	public Categoria putCategoria(@RequestBody Categoria categoria, @PathVariable long idCategoria) {
		categoria.setId(idCategoria);
		return categoriaRepository.save(categoria);
	}
	
	@DeleteMapping("/deletar/{idCategoria}")
	public void deleteCategoria(@PathVariable long idCategoria) {
		categoriaRepository.deleteById(idCategoria);
	}
}

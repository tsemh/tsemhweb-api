package io.tsemh.tsemhapirest.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import io.tsemh.tsemhapirest.entity.Usuario;
import io.tsemh.tsemhapirest.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository; 
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@PostMapping("/cadastrar")
	@Transactional
	public Usuario postUsuario(@RequestBody Usuario usuario) {
	    List<Usuario> usuarios = usuarioRepository.findAll();

	    if (usuarios.isEmpty()) {
	    	 String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
	         usuario.setSenha(senhaCriptografada);
	         return usuarioRepository.save(usuario);
	    } else {
	        throw new RuntimeException("Não é possível criar um novo usuário. Já existe um usuário cadastrado.");
	    }
	}

	@GetMapping
	public List<Usuario> getAllUsuario() {
		return usuarioRepository.findAll();
	}
	
	@GetMapping("{id}")
	public Usuario getUsuarioById(@PathVariable long id) {
		return usuarioRepository.findById(id).get();
	}
	
	@PutMapping("/editar-visivel/{id}")
	public Usuario putUsuarioVisivel(@RequestBody Usuario usuario, @PathVariable long id) {
	    Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
	    if (existingUsuario.isPresent()) {
	        Usuario savedUsuario = existingUsuario.get();
	        
	        usuario.setEmail(savedUsuario.getEmail());
	        usuario.setSenha(savedUsuario.getSenha());
	        usuario.setId(savedUsuario.getId());
	        
	        return usuarioRepository.save(usuario);
	    } else {
	        throw new NoSuchElementException("Usuario with ID " + id + " does not exist");
	    }
	}
	
	@PutMapping("/editar/{id}")
	public Usuario putUsuario(@RequestBody Usuario usuario, @PathVariable long id) {
	    Optional<Usuario> existingUsuario = usuarioRepository.findById(id);
	    if (existingUsuario.isPresent()) {
	        return usuarioRepository.save(usuario);
	    } else {
	        throw new NoSuchElementException("Usuario with ID " + id + " does not exist");
	    }
	}

	@DeleteMapping("/deletar/{id}")
	public void deleteUsuario(@PathVariable long id) {
		usuarioRepository.deleteById(id);
	}
}
	
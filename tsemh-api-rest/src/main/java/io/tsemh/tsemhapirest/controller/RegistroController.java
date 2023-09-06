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
import io.tsemh.tsemhapirest.repository.RegistroRepository;
import io.tsemh.tsemhapirest.repository.UsuarioRepository;

@RestController
@RequestMapping("/registro")
public class RegistroController {
	
		@Autowired
		private RegistroRepository registroRepository; 
		@Autowired
		private UsuarioRepository usuarioRepository; 
		@Autowired
		private CategoriaRepository categoriaRepository; 
	
		@PostMapping("/cadastrar")
		@Transactional
		public Registro postRegistro(@RequestBody Registro registro, @RequestParam long idUsuario, @RequestParam String titulo) {
		    Usuario usuario = getUsuarioById(idUsuario);
		    checkDestaqueLimit();
		    Categoria categoria = getCategoriaByTituloAndTipo(titulo, registro.getTipo(), idUsuario);
		    registro.setUsuario(usuario);
		    registro.setCategoria(categoria);
		    setRegistroDestaque(registro, categoria);
		    return registroRepository.save(registro);
		}
	
			private Usuario getUsuarioById(long idUsuario) {
			    Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
			    if (usuarioOptional.isPresent()) {
			        return usuarioOptional.get();
			    } else {
			        throw new RuntimeException("Usuário não encontrado com ID " + idUsuario);
			    }
			}
	
			private void checkDestaqueLimit() {
			    long count = registroRepository.countByDestaque(true);
			    if (count >= 4) {
			        List<Registro> registrosComDestaque = registroRepository.findByDestaqueOrderByDataCriacaoAsc(true);
			        if (!registrosComDestaque.isEmpty()) {
			            Registro registroMaisAntigo = registrosComDestaque.get(0);
			            registroMaisAntigo.setDestaque(false);
			            registroRepository.save(registroMaisAntigo);
			        } else {
			            throw new RuntimeException("Excedeu o limite de registros com destaque, mas não foi possível encontrar o registro mais antigo.");
			        }
			    }
			}
			
			private Categoria getCategoriaByTituloAndTipo(String titulo, String tipo, long idUsuario) {
			    Categoria categoria = categoriaRepository.findByTituloAndTipo(titulo, tipo);
			    if (categoria == null) {
			        Usuario usuario = getUsuarioById(idUsuario);
			        categoria = new Categoria();
			        categoria.setTipo(tipo);
			        categoria.setTitulo(titulo);
			        categoria.setUsuario(usuario);
			        categoriaRepository.save(categoria);
			    }
			    return categoria;
			}
	
			private void setRegistroDestaque(Registro registro, Categoria categoria) {
			    if (categoria.getTipo().equals(registro.getTipo())) {
			        registro.setDestaque(true);
			    } else {
			        throw new RuntimeException("Tipo de categoria não corresponde ao tipo do registro.");
			    }
			}
		
		@GetMapping
		public List<Registro> getAllRegistro() {
			return registroRepository.findAll();
		}
		
		@GetMapping("{id}")
		public Registro getRegistroById(@PathVariable long id) {	
			return registroRepository.findById(id).get();
		}
		
		@GetMapping("categoria")
		public List<Registro> getRegistroByCategoria(@RequestParam Long categoriaId){
		    Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
		    if(categoria == null) {
		    	throw new RuntimeException("Categoria não encontrada com ID " + categoriaId);
		    }
		    return registroRepository.findByCategoria(categoria);
		}
		
		@GetMapping("tipo")
		public List<Registro> getRegistroByTipo(@RequestParam String tipo) {
		    if(tipo == null) {
		        throw new RuntimeException("Tipo não encontrado");
		    }
		    return registroRepository.findByTipo(tipo);
		}
		
		@GetMapping("destaque")
		public List<Registro> getRegistroByDestaque(@RequestParam Boolean destaque) {

		    return registroRepository.findByDestaque(destaque);
		}
		
		@GetMapping("tipos")
		public List<String> getTiposDeRegistro() {
		    return registroRepository.findDistinctTipo();
		}
		
		@PutMapping("/editar/{id}")
		public Registro putRegistro(@RequestBody Registro registro, @PathVariable int id) {
			registro.setId(id);
			return registroRepository.save(registro);
		}
		
		@DeleteMapping("/deletar/{id}")
		public void deleteRegistro(@PathVariable long id) {
			registroRepository.deleteById(id);
		}
}

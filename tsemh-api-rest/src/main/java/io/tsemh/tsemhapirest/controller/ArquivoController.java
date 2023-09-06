package io.tsemh.tsemhapirest.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.tsemh.tsemhapirest.entity.Arquivo;
import io.tsemh.tsemhapirest.repository.ArquivoRepository;
import io.tsemh.tsemhapirest.entity.Registro;
import io.tsemh.tsemhapirest.repository.RegistroRepository;

public class ArquivoController {
	
	private static String bancoDeArquivo = "C:\\Users\\tiago\\Documents\\Tiago\\programacao\\"
			+ "Projetos\\MeuPortfolioWeb\\Codigos\\tsemh-portfolio-web\\Banco-de-arquivos";

	@Autowired
	private ArquivoRepository arquivoRepository;
	@Autowired
	private RegistroRepository registroRepository; 

	@PostMapping("/arquivos")
	@Transactional
	public Arquivo postArquivo(@RequestBody MultipartFile arquivo, @RequestParam Long idRegistro) throws IOException {
	    Optional<Registro> registroOptional = registroRepository.findById(idRegistro);
	    if (registroOptional.isPresent()) {
	        Registro registro = registroOptional.get();

	        String nomeArquivo = arquivo.getOriginalFilename();
	        String caminhoCompletoArquivo = bancoDeArquivo + File.separator + nomeArquivo;
	        
	        try (InputStream inputStream = arquivo.getInputStream()) {
	            Files.copy(inputStream, Paths.get(caminhoCompletoArquivo), StandardCopyOption.REPLACE_EXISTING);
	        }

	        Arquivo arquivoEntity = new Arquivo();
	        arquivoEntity.setNome(nomeArquivo);
	        arquivoEntity.setCaminho(caminhoCompletoArquivo);
	        arquivoEntity.setRegistro(registro);
	        return arquivoRepository.save(arquivoEntity);
	    } else {
	        throw new RuntimeException("Registro não encontrado com ID " + idRegistro);
	    }
	}
		
	@GetMapping
	public List<Arquivo> getAllArquivo() {
		return arquivoRepository.findAll();
	}
	
	@GetMapping("{id}")
	public Arquivo getArquivoById(@PathVariable long id) {
		return arquivoRepository.findById(id).get();
	}
	
	@GetMapping("tipo")
	public List<Arquivo> getArquivoByTipo(@RequestParam String tipo) {
	    if(tipo == null) {
	        throw new RuntimeException("Tipo não encontrado");
	    }
	    return arquivoRepository.findByTipo(tipo);
	}
	
	@PutMapping("{id}")
	public Arquivo putArquivo(@RequestBody Arquivo arquivo, @PathVariable long id) {
		arquivo.setId(id);
		return arquivoRepository.save(arquivo);
	}
	
	@DeleteMapping("{id}")
	public void deleteArquivo(@PathVariable long id) {
		arquivoRepository.deleteById(id);
	}
}




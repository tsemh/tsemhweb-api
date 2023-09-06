package io.tsemh.tsemhapirest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.tsemh.tsemhapirest.entity.Usuario;
import io.tsemh.tsemhapirest.record.TokenJwt;
import io.tsemh.tsemhapirest.service.TokenService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://localhost:4200")
public class AutenticacaoController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping
	public ResponseEntity efetuarLogin(@RequestBody @Valid Usuario usuario) {
		var authenticationToken = new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha());
		var authentication = authenticationManager.authenticate(authenticationToken);
		var tokenJwt = tokenService.geraToken((Usuario) authentication.getPrincipal());
		return ResponseEntity.ok(new TokenJwt(tokenJwt));
	}
}

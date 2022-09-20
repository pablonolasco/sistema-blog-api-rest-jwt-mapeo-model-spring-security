package com.sistema.blog.controller;

import java.util.Collections;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistema.blog.dto.LoginDTO;
import com.sistema.blog.dto.RegistroLoginDTO;
import com.sistema.blog.entity.Rol;
import com.sistema.blog.entity.Usuario;
import com.sistema.blog.repository.IRolRepository;
import com.sistema.blog.repository.IUsuarioRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private AuthenticationManager authenticationManager;
	
	// == registro login
	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Autowired
	private IRolRepository rolRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/iniciarSesion")
	public ResponseEntity<String>login(@RequestBody LoginDTO loginDTO){
		/*Authentication authentication= authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUserOrEmail(), loginDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);*/
        Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	//	log.info("user {}",loginDTO.getUsernameOrEmail());
		return new ResponseEntity<>("Ha iniciado sesión con exito !",HttpStatus.OK);
	}
	
	@PostMapping("/registrar")
	public ResponseEntity<?>registrarUsuario(@Valid @RequestBody RegistroLoginDTO registroLoginDTO){
		
		if (usuarioRepository.existsByUsername(registroLoginDTO.getUsername())) {
			return new ResponseEntity<>("El nombre del usuario ya se encuentra registrado",HttpStatus.BAD_REQUEST);
		}
		
		if (usuarioRepository.existsByEmail(registroLoginDTO.getEmail())) {
			return new ResponseEntity<>("El email ya se encuentra registrado",HttpStatus.BAD_REQUEST);
		}
		
		Usuario usuario= new Usuario();
		usuario.setNombre(registroLoginDTO.getNombre());
		usuario.setUsername(registroLoginDTO.getUsername());
		usuario.setPassword(passwordEncoder.encode(registroLoginDTO.getPassword()));
		usuario.setEmail(registroLoginDTO.getEmail());
		Rol roles=rolRepository.findByNombre("ROLE_ADMIN").get();
		usuario.setRoles(Collections.singleton(roles));
		
		usuarioRepository.save(usuario);
		return new ResponseEntity<>("Usuario registrado exitosamente",HttpStatus.CREATED);
		
	}
}

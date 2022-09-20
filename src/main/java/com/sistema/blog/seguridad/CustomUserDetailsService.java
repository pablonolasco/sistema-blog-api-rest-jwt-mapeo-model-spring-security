package com.sistema.blog.seguridad;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sistema.blog.entity.Rol;
import com.sistema.blog.entity.Usuario;
import com.sistema.blog.repository.IUsuarioRepository;

import java.util.Set;
import java.util.stream.Collectors;
/**
 * servicio que obtiene el usuario y sus roles
 * @author pnolasco
 *
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	
	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private IUsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		log.info("user");
		Usuario usuario = usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email : " + usernameOrEmail));
	
		return new User(usuario.getEmail(), usuario.getPassword(), mapearRoles(usuario.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapearRoles(Set<Rol> roles){
		return roles.stream().map(
				rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

	}

}

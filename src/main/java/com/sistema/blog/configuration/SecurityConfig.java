package com.sistema.blog.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.sistema.blog.seguridad.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDatilsService;

	/**
	 * bean que encripta las contraseñas
	 * 
	 * @return
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * metodo que se declara la configuracion
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		/*
		 * http.csrf().disable() .authorizeRequests()// =permite restringir el acceso a
		 * recursos .antMatchers(HttpMethod.GET,"/api/**") //= define los recursos a
		 * proteger .permitAll()// =autoriza a todas las peticiones get .anyRequest()//
		 * =para cualquier otra debe de estar autenticado .authenticated() .and()
		 * .httpBasic();// =configura una basic authentication
		 */
		http.csrf().disable().authorizeRequests()// =permite restringir el acceso a recursos
				.antMatchers(HttpMethod.GET, "/api/**") // = define los recursos a proteger
				.permitAll()// =autoriza a todas las peticiones get
				.antMatchers("/api/auth/**")// =para cualquier otra debe de estar autenticado
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.httpBasic();// =configura una basic authentication

	}

	/**
	 * metodo que configura la autenticacion con base a los usuarios de la base de
	 * datos
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(userDatilsService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	/**
	 * metodo que crea los usuarios permitidos para usar la api
	 */
	/*
	 * @Override
	 * 
	 * @Bean protected UserDetailsService userDetailsService() { UserDetails pablo =
	 * User.builder().
	 * username("pablo").password(passwordEncoder().encode("1234")).roles("USER").
	 * build(); UserDetails admin = User.builder().
	 * username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").
	 * build(); // =clase que registra a los usuarios en memoria return new
	 * InMemoryUserDetailsManager(pablo,admin); }
	 */
}

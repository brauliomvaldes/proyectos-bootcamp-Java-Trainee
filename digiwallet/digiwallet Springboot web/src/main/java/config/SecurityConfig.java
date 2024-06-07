package config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity    // permite utilizar anotaciones para configurar, anotar @PreAuthorize() en el controlador
						// controlador @PreAuthorize("denyall()")  método controlador @PreAuthorize("permitAll())
public class SecurityConfig {
	
		
	/*
	 * @Bean public SecurityFilterChain securityFilterChain(HttpSecurity
	 * httpSecurity) throws Exception { // configuraciones de seguridad //
	 * DelegatingFilterProxy
	 * 
	 * return httpSecurity .csrf(csrf->csrf.disable()) // vulnabilidad web en
	 * formularios, en rest no necesaria, seguridad basada en token
	 * .httpBasic(Customizer.withDefaults()) // cuando se autentica con usuario y
	 * contraseña .sessionManagement(session->session.sessionCreationPolicy(
	 * SessionCreationPolicy.STATELESS)) // para emplear token y no sessiones
	 * .authorizeHttpRequests(http-> { // configurar los endpoints públicos
	 * http.requestMatchers(HttpMethod.GET, "*").permitAll(); // configurar los
	 * endpoints privados http.requestMatchers(HttpMethod.GET,
	 * "/").hasAnyAuthority("READ"); // configurar el rest de endpoints no
	 * especificados http.anyRequest().denyAll(); // no permite el acceso
	 * .authenticated() permite acceso si esta autenticado }) .build(); }
	 */
	
	// empleando anotaciones
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// configuraciones de seguridad 
		// DelegatingFilterProxy
		
		return httpSecurity
				.csrf(csrf->csrf.disable()) // vulnabilidad web en formularios, en rest no necesaria, seguridad basada en token
				.httpBasic(Customizer.withDefaults())          // cuando se autentica con usuario y contraseña
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // para emplear token y no sessiones
				
				.build();
	}
	
	
	@Bean
	public AuthenticationManager autenthicationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	// creación de usuario en memoria
	@Bean
	public UserDetailsService userDetailsService() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		
		// Creación m,anul
		// Simula que el usuario viene de una base de datos, para efectos de prueba
		userDetailsList.add(User.withUsername("braulio")   
				.password("123")
				.roles("ADMIN")
				.authorities("READ", "CREATE")
				.build());
		userDetailsList.add(User.withUsername("mariano")   
				.password("123")
				.roles("USER")
				.authorities("READ")
				.build());
				
				
		return new InMemoryUserDetailsManager(userDetailsList);  // retorna el usuario en memoria
	}
	
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}

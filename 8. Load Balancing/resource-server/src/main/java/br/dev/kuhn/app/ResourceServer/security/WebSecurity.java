package br.dev.kuhn.app.ResourceServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Configuration
public class WebSecurity {

    @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
	 
		http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/users/**").hasRole("user")
                .anyRequest().authenticated()
                .and()
            .oauth2ResourceServer().jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter);

        return http.build();
	}
    
}
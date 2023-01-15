package br.dev.kuhn.app.ResourceServer.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurity {
    
    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //     http.authorizeHttpRequests()
    //             .requestMatchers(HttpMethod.GET, "/users/**").hasRole("developer")
    //             .anyRequest().authenticated()
    //             .and()
    //         .oauth2ResourceServer().jwt();

    //     return http.build();
    // }

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
	
//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		CorsConfiguration corsConfiguration = new CorsConfiguration();
//		corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
//		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST"));
//		corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
//		
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", corsConfiguration);
//		
//		return source;
//	}
    
}
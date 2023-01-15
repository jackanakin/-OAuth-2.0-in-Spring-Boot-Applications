package br.kuhn.dev.pkceutil;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PkceutilApplication {

	public static void main(String[] args) {
		SpringApplication.run(PkceutilApplication.class, args);

		try {
            
            PkceUtil pkce = new PkceUtil();
     
            String codeVerifier = pkce.generateCodeVerifier();
            System.out.println("Code verifier: " + codeVerifier);
            
            String codeChallenge = pkce.generateCodeChallange(codeVerifier);
            System.out.println("Code challenge: " + codeChallenge);
 
        } catch ( UnsupportedEncodingException | NoSuchAlgorithmException ex ) {
            Logger.getLogger(PkceutilApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
	}	

}

package muhasebe.security;


import javax.servlet.http.HttpServletResponse;

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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // tüm metotlardan önce security çalışacak
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenFilter filter;

	@Autowired
	private UserDetailsService service;

	@Autowired
	public void passordEncoder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(service).passwordEncoder(getBCryptPasswordEncoder());//password encoder
	}

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return super.authenticationManagerBean();// instance üretiliyor
	}
	
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // Enable CORS and disable CSRF
        http = http.cors().and().csrf().disable();

        // Set session management to stateless
        http = http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        // Set unauthorized requests exception handler
        http = http
            .exceptionHandling()
            .authenticationEntryPoint(
                (request, response, ex) -> {
                    response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        ex.getMessage()
                    );
                }
            )
            .and();

        // Set permissions on endpoints
        http.authorizeRequests()
            // Our public endpoints
            .antMatchers(HttpMethod.POST, "/api/auth/token").permitAll()
            .antMatchers("/v3/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "swagger-ui/index.html","/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security").permitAll()
            .antMatchers("/api/v2/api-docs", "/api/configuration/ui", "/api/swagger-resources", "/api/configuration/security", "/api/swagger-ui.html", "/api/webjars/**", "/api/swagger-resources/configuration/ui", "/api/swagger-resources/configuration/security").permitAll()
            .antMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
            .antMatchers("/api/swagger-ui/**","/api/v3/api-docs/**").permitAll()
            .antMatchers("/api/v3/api-docs", "/api/configuration/ui", "/api/swagger-resources", "/api/configuration/security", "/api/swagger-ui.html", "/api/webjars/**", "/api/swagger-resources/configuration/ui", "/api/swagger-resources/configuration/security").permitAll()
            // Our private endpoints
            // Our private endpoints
            .antMatchers("/test/**").hasRole("ADMIN")
            .anyRequest().authenticated();

        // Add JWT token filter
        http.addFilterBefore(
        		filter,
            UsernamePasswordAuthenticationFilter.class
        );
    }
	
	
}

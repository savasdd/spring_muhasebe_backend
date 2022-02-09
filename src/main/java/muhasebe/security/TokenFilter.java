package muhasebe.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class TokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManager manager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Bearer iki parçaya bölüp token almak gerekiyor
		final String header = request.getHeader("Authorization");// header alındı
		String token = null;
		String username = null;

		if (header != null && header.contains("Bearer")) {
			token = header.substring(7);
			try {
				username = manager.getUserFromToken(token);// token ile username alındı

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

		if (username != null && token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			if (manager.tokenValidate(token)) { // ilk defa login olduysa token hala valid ise login olacak
				UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(username, null,
						new ArrayList<>());
				login.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(login);
			}
		}

		filterChain.doFilter(request, response);// işleme devam et

	}

}

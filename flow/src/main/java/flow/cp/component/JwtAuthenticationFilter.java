package flow.cp.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import flow.cp.service.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
	@Autowired
	private TokenProvider tokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException{
		try {
			String token =parseBearerToken(request);
			log.info("Filter is Filter");
			
			if(token != null && !token.equalsIgnoreCase("null")) {
				String userId = tokenProvider.validateAndGetUserId(token);
				log.info("Authenticated user ID : " + userId);
				AbstractAuthenticationToken authentication 
							= new UsernamePasswordAuthenticationToken(userId,null, AuthorityUtils.NO_AUTHORITIES);
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContext securityContext = 
						SecurityContextHolder.createEmptyContext();
						securityContext.setAuthentication(authentication);
						SecurityContextHolder.setContext(securityContext);

			}
		}catch(Exception ex) {
			logger.error("Could not set user authentication in security context");
		}
		filterChain.doFilter(request, response);
	}
	
	private String parseBearerToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
}
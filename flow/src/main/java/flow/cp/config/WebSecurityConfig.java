package flow.cp.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import com.fasterxml.jackson.databind.ObjectMapper;

import flow.cp.component.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;

//spring security 사용하기 -> 비밀번호 암호화

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{
	//websecurity에서 웹사이트에서 접근을 허용할 수 있는 범위 선정
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private final ObjectMapper objectMapper;
	@Autowired
	
	public WebSecurityConfig(ObjectMapper objectMapper) {
	this.objectMapper = objectMapper;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception  {
		http.cors()
			.and()
				.csrf().disable()
				.httpBasic().disable()	//
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
				.requestMatchers("/","/auth/**","/copy/**").permitAll()
				.anyRequest().authenticated();
		
		http.exceptionHandling()
			.authenticationEntryPoint((request,response, e) ->{
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("status", HttpServletResponse.SC_FORBIDDEN);
				data.put("message", e.getMessage());
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				objectMapper.writeValue(response.getOutputStream(), data); 
			});
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		return http.build();
	}
}

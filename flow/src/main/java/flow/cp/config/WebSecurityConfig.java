package flow.cp.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import flow.cp.component.JwtAuthenticationFilter;

//spring security 사용하기 -> 비밀번호 암호화

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfiguration{
	//websecurity에서 웹사이트에서 접근을 허용할 수 있는 범위 선정
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
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
				.requestMatchers("/","/auth/**").permitAll()
				.anyRequest().authenticated();
		http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
		return http.build();
	}
}

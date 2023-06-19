package flow.cp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import flow.cp.component.JwtAuthenticationFilter;

//순환참조를 방지하기위해 wewbSecurityConfig와 분리하였음
//비밀번호 암호화를 위한 config
@Configuration
public class PasswordEncoderConfig {
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

}

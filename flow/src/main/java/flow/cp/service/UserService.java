package flow.cp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import flow.cp.entity.UserEntity;
import flow.cp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public UserEntity create (final UserEntity user) {
		if(user == null|| user.getEmail() == null || user.getPassword() ==null || user.getUsername() ==null) {
			throw new RuntimeException("json 오류");
		}
		final String email = user.getEmail();
		
		if(userRepository.existsByEmail(email)) {
			log.warn("이메일 이미 존재함. {}", email);
			//throw new RuntimeException("이미 존재하는 이메일");
			return responsebody;
		}
		
		return userRepository.save(user);
	}
	
	// 로그인시 이메일 존재 확인하기
	public boolean isExistEmail(final String email) {
		return userRepository.existsByEmail(email);
	}
	public UserEntity FindUser(final String email) {
		return userRepository.findByEmail(email);
	}
	
	public UserEntity FindUserById(final String id) {
		return userRepository.getByIdEntity(id);
	}
}

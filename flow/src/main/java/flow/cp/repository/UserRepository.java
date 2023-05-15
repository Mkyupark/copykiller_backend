package flow.cp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import flow.cp.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
	// 회원가입시 존재하는 이메일인지 확인하기
	Boolean existsByEmail(String email);
	// 로그인시 회원정보가 있는지 찾기
	UserEntity findByEmail(String email);
	
	@Query("SELECT u FROM UserEntity u WHERE u.id = :id")
	UserEntity getByIdEntity(@Param("id") String id);
}

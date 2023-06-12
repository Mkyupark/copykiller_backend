package flow.cp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flow.cp.entity.LogEntity;
import flow.cp.entity.UserEntity;

public interface CopyRepository extends JpaRepository<LogEntity, String> {
	@Query("SELECT u FROM LogEntity u WHERE u.id = :id")
	LogEntity getByIdEntity(@Param("id") String id);
	
	// and(소문자 불가능) 대문자로 해야한다. or도 마찬가지임.
	@Query("SELECT t FROM LogEntity t WHERE t.url1 = :url1 AND t.url2 = :url2")
	LogEntity findByLogEntity(@Param("url1") String url1, @Param("url2") String url2);
	
	// entity가 외래키    
	@Query("SELECT u FROM LogEntity u WHERE u.user= :user")
	List<LogEntity> findAllLogEntity(@Param("user") UserEntity user);
}

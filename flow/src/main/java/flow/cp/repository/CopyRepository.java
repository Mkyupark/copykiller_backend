package flow.cp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flow.cp.entity.LogEntity;

public interface CopyRepository extends JpaRepository<LogEntity, String> {
	@Query("SELECT u FROM LogEntity u WHERE u.id = :id")
	LogEntity getByIdEntity(@Param("id") String id);
	
	@Query("SELECT t FROM LogEntity t Where u.url1 = :url1 and u.url2 =: url2")
	Optional<LogEntity> getByEntity(@Param("url1") String url1, @Param("url2") String url2);
}

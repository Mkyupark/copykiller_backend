package flow.cp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flow.cp.entity.LogEntity;
import flow.cp.entity.TextResultEntity;

public interface TextResultRepository extends JpaRepository<TextResultEntity, String> {
	List<TextResultEntity> findByLog(LogEntity log);
}

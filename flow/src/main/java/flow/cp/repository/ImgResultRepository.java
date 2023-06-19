package flow.cp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flow.cp.entity.ImgResultEntity;
import flow.cp.entity.LogEntity;
import flow.cp.entity.TextResultEntity;

public interface ImgResultRepository extends JpaRepository<ImgResultEntity, String> {
	List<ImgResultEntity> findByLog(LogEntity log);
}

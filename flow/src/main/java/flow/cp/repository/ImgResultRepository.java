package flow.cp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import flow.cp.entity.ImgResultEntity;
import flow.cp.entity.LogEntity;

public interface ImgResultRepository extends JpaRepository<ImgResultEntity, String> {
}

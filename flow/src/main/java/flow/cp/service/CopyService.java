package flow.cp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import flow.cp.dto.LogDTO;
import flow.cp.entity.ImgResultEntity;
import flow.cp.entity.LogEntity;
import flow.cp.entity.TextResultEntity;
import flow.cp.entity.UserEntity;
import flow.cp.repository.CopyRepository;
import flow.cp.repository.ImgResultRepository;
import flow.cp.repository.TextResultRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CopyService {
	@Autowired
	CopyRepository copyRepository;
	
	@Autowired
	TextResultRepository textResultRepository;
	
	@Autowired
	ImgResultRepository imgResultRepository;
	
	//초기 영상비교에서 데이터 임시저장후 비교후 데이터 저장
	public LogEntity createCopyLog(final LogEntity logEntity) {
		return copyRepository.save(logEntity);
	}
	
	//이미지와 텍스트 결과 저장할 때 외래키 찾기 위해 선언
	public LogEntity FindUserById(final String id) {
		return copyRepository.getByIdEntity(id);
	}
	
//	public Optional<LogEntity> FindLogEntityByUrl(final LogDTO logDTO) {
//		// 데이터 불러오기
//		Optional<LogEntity> log = copyRepository.getByEntity(logDTO.getUrl1(), logDTO.getUrl2());
//		if(log != null) {
//			LogEntity exsitingLog = log.get();
//			exsitingLog.setTextrate(logDTO.getTextrate());
//			exsitingLog.setImgrate(logDTO.getImgrate());
//			copyRepository.save(exsitingLog);
//		}else {
//			System.out.println("찾고자 하는 데이터 없음");
//		}
//		return log;
//	}
	
	public LogEntity updateLogInfo(final LogDTO logDTO) {
		LogEntity updateLogEntity = copyRepository.findByLogEntity(logDTO.getUrl1(), logDTO.getUrl2()); 
		if(updateLogEntity != null) {
			updateLogEntity.setImgrate(logDTO.getImgrate());
			updateLogEntity.setTextrate(logDTO.getTextrate());
			updateLogEntity.setTotalrate(logDTO.getTotalrate());
			updateLogEntity.setTitle1(logDTO.getTitle1());
			updateLogEntity.setTitle2(logDTO.getTitle2());
			LogEntity update = copyRepository.save(updateLogEntity);
			return update;
		}else {
			return null;
		}
	}
	public void createTextResult(final List<TextResultEntity> textResultList) {
		for(TextResultEntity entity : textResultList) {
			
			textResultRepository.save(entity);
		}
	}
	public void createImgResult(final List<ImgResultEntity> imgResultList) {
		for(ImgResultEntity entity: imgResultList) {
			imgResultRepository.save(entity);
		}
	}
	
	public List<LogEntity> sendLogData(final UserEntity user) {
		return copyRepository.findAllLogEntity(user);
	}
	
	public List<ImgResultEntity> sendImgData (final LogEntity log){
		return imgResultRepository.findByLog(log);
	}
	public List<TextResultEntity> sendTextData (final LogEntity log){
		return textResultRepository.findByLog(log);
	}
}

package flow.cp.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import flow.cp.dto.ImgResultDTO;
import flow.cp.dto.LogDTO;
import flow.cp.dto.ResponseDTO;
import flow.cp.dto.TextResultDTO;
import flow.cp.entity.ImgResultEntity;
import flow.cp.entity.LogEntity;
import flow.cp.entity.TextResultEntity;
import flow.cp.service.CopyService;
import flow.cp.service.TokenProvider;
import flow.cp.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


//표절률 로그데이터 데이터 저장 및 검사 후 업데이트
//텍스트 표절률과 이미지 표절률 데이터 저장
@CrossOrigin(origins ="*")
@RestController
@RequestMapping("/copy")
public class CopyController {
	@Autowired
	public CopyService copyService;
	
	@Autowired
	public UserService userService;
	
	@Autowired
	public TokenProvider tokenProvider;
	
	public String LogId;
	
	//영상비교페이지에서 클릭시 임시로 표절률 결과 없이 데이터 저장
	//유튜브 url이 뒤에만 들어감, youtube.com/ => 이뒤에꺼
	@PostMapping("/log")
	public ResponseEntity<?> saveInitiateLogData(@AuthenticationPrincipal String userId, @RequestBody LogDTO logDTO){
		try {
			String token = logDTO.getToken();
			String id = tokenProvider.validateAndGetUserId(token);
			
			System.out.println(token);
			System.out.println(id);
			LogEntity sample = LogEntity.builder().user(userService.FindUserById(id)).url1("www.youtube.com/"+ logDTO.getUrl1()).url2("www.youtube.com/"+ logDTO.getUrl2()).build();			
			LogEntity temp = copyService.createCopyLog(sample);
			LogId = temp.getId();
			return ResponseEntity.ok().body(logDTO);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	//검사결과페이지에서 버튼을 누르면 데이터 저장
	//텍스트 결과 저장
	// 프론트에서 보낼때 json 리스트로 보내야함 [ {}, {}, {} ] 형식
	@PostMapping("/textresult")
	public ResponseEntity<?> saveInitiateCopyRateData(@RequestBody List<TextResultDTO> textResultDTOList){
		try {
			List<TextResultEntity> textResultList = new ArrayList<>(textResultDTOList.size());
			for(TextResultDTO dto: textResultDTOList) {
				TextResultEntity entity = TextResultEntity.builder().log(copyService.FindUserById(LogId)).
													timeline1(dto.getTimeline1()).timeline2(dto.getTimeline2()).text_copy_rate(dto.getCopyrate()).build();
				textResultList.add(entity);
				System.out.println(entity);
			}
			System.out.println(textResultList);
			copyService.createTextResult(textResultList);
			return ResponseEntity.ok().body(textResultDTOList);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	//검사결과페이지에서 버튼을 누르면 데이터 저장
	//이미지 결과 저장
	@PostMapping("/imgresult")
	public ResponseEntity<?> saveInitiateImgCopyRateData(@RequestBody List<ImgResultDTO> ImgResultDTOList){
		try {
			List<ImgResultEntity> ImgResultList = new ArrayList<>(ImgResultDTOList.size());
			for(ImgResultDTO dto: ImgResultDTOList) {
				ImgResultEntity entity = ImgResultEntity.builder().log(copyService.FindUserById(LogId)).
													timeline1(dto.getTimeline1()).timeline2(dto.getTimeline2()).img_copyrate(dto.getCopyrate()).build();
				ImgResultList.add(entity);
				System.out.println(entity);
			}
			System.out.println(ImgResultList);
			copyService.createImgResult(ImgResultList);
			return ResponseEntity.ok().body(ImgResultDTOList);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
	//결과 데이터 저장
	//인공지능 모듈에서 결과값을 돌리고 logEntity 업데이트
	@PostMapping("/update")
	public ResponseEntity<?> updateLogEntity(@RequestBody LogDTO logDTO){
		try {
			// 프로트에서 url1과 url2 받아와서 저장할 위치 찾기

			Optional<LogEntity> log = copyService.FindLogEntityByUrl(logDTO);
			return ResponseEntity.ok().body(log);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
//	@PostMapping("/totalresult")
//	public ResponseEntity<?> saveTotalRate(@RequestBody )
	// ai 모듈로 데이터 전송
//	@PostMapping("/sendAiModule")
//	public ResponseEntity<?> sendToAiModule(@RequestBody LogDTO logDTO){
//		try {
//			LogDTO sample = logDTO.builder()
//					.url1(logDTO.getUrl1())
//					.url2(logDTO.getUrl2())
//					.build();
//
//			return ResponseEntity.ok().body(sample);
//		}catch(Exception e) {
//			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			return ResponseEntity.badRequest().body(responseDTO);
//		}
//	}
}
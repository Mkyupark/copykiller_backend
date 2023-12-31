package flow.cp.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.VideoAbuseReportReasonListResponse;

import flow.cp.dto.ImgResultDTO;
import flow.cp.dto.LogDTO;
import flow.cp.dto.ResponseDTO;
import flow.cp.dto.TextResultDTO;
import flow.cp.entity.ImgResultEntity;
import flow.cp.entity.LogEntity;
import flow.cp.entity.TextResultEntity;
import flow.cp.entity.UserEntity;
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
	
//	@Autowired
//	public YoutubeService youtubeService;
//	
//	@Autowired
//	private YoutubeCommandRunner youtubeCommandRunner;
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
			LogEntity sample = LogEntity.builder()
				.user(userService.FindUserById(id))
				.url1(logDTO.getUrl1())
				.title1(logDTO.getTitle1())
				.url2(logDTO.getUrl2())
				.title2(logDTO.getTitle2())
				.textrate(logDTO.getTextrate())
				.imgrate(logDTO.getImgrate())
				.totalrate(logDTO.getTotalrate())
				.build();
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
			System.out.println(LogId);
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
	@PostMapping("/test")
	public ResponseEntity<?> updateLogEntity(@RequestBody LogDTO logDTO){
		try {
			LogEntity update = copyService.updateLogInfo(logDTO);
			System.out.println(logDTO);
			if(update != null) {
				return ResponseEntity.ok().body(logDTO);
			}else {
				return ResponseEntity.notFound().build();
			}
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}

	@PostMapping("/loglist")
	public ResponseEntity<?> sendLogEntityList(@RequestBody LogDTO logDTO){
		try {
			System.out.println(logDTO.getToken());
			String id = tokenProvider.validateAndGetUserId(logDTO.getToken());
			System.out.println(id);
			UserEntity user = userService.FindUserById(id);
			List<LogEntity> logList = copyService.sendLogData(user);
			System.out.println(logList);			
			return ResponseEntity.ok().body(logList);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
//	@PostMapping("/report")
//	public ResponseEntity<?> reportCopyKiller(){
//		try {
//			youtubeCommandRunner.run();
//			return ResponseEntity.ok().body(null);
//		}catch(Exception e) {
//			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			return ResponseEntity.badRequest().body(responseDTO);
//		}
//	}
	@PostMapping("/rollback")
	public ResponseEntity<?> rollbacklogData(@RequestBody LogDTO logDTO){
		try {
			System.out.println(logDTO.getId());
			LogEntity log = copyService.FindUserById(logDTO.getId());
			List<TextResultEntity> textList = copyService.sendTextData(log);
			List<ImgResultEntity> imgList = copyService.sendImgData(log);
			
			Map<String, Object> response = new HashMap();
			response.put("textList", textList);
			response.put("imgList", imgList);
			
			return ResponseEntity.ok().body(response);
		}catch(Exception e) {
			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
			return ResponseEntity.badRequest().body(responseDTO);
		}
	}
	
//	@PostMapping("/update")
//	public ResponseEntity<?> updateLogEntity(@RequestBody LogDTO logDTO){
//		try {
//			// 프로트에서 url1과 url2 받아와서 저장할 위치 찾기
//
//			Optional<LogEntity> log = copyService.FindLogEntityByUrl(logDTO);
//			return ResponseEntity.ok().body(log);
//		}catch(Exception e) {
//			ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
//			return ResponseEntity.badRequest().body(responseDTO);
//		}
//	}
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

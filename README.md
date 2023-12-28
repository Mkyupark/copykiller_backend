# 유튜브 카피킬러 - backend
#### 2023-1 창의설계프로젝트 팀 플로우  
해당 프로그램은 웹 기반으로 테블릿에서 유저에게 표절률이 의심되는 영상을 입력과 원본 영상을 입력시 영상 표절률을 제공하는 플렛폼입니다.
주요 기능은 표절의심 구간 비교, 이전 결과 기록 저장, 이전 결과 기록 보기 등이 있으며 이것으로 영상을 표절하는 일을 사전에 방지하고 표절시 구체적인 수치로 신고를 할때 도움이 될 것으로 기대하고 있습니다. 

## 앱 사용영상   

<div align="center">  
  
[![Thumbnail](https://github.com/2023-CreativeDesign-flow/backend/blob/master/copykiller%20%EB%A9%94%EC%9D%B8.png)](https://youtu.be/ek_KhaDyGWw)    

</div>  
  
  
## 개요
  - 목적: UCC 저작권 침해 방지 및 예방    
  - 사용 언어
    - 서버: spring boot
    - DB: mysql
  - 사용 패키지 및 API:
    - 영상 재생 및 신고: youtube API  
    - 서버 배포: groom 
  
  - DB : MYSQL
  - DB 구조 :  

![img](https://github.com/2023-CreativeDesign-flow/backend/blob/master/DB%20%EA%B5%AC%EC%A1%B0.png)




  
  
  
  
  
  
  
## 코드 사용법  
### 디렉토리 구조
![img](https://github.com/2023-CreativeDesign-flow/backend/blob/master/spring%20boot%20%EA%B5%AC%EC%A1%B0.png)
### 설명
###   - flow.cp 패키지
spring boot를 실행하는 파일
  
###   - flow.command 패키지  
유튜브 표절률을 실행하는 파일
  
###   - flow.cp.config 패키지
회원 관리를 하기 위해 JWT 토큰을 설정하고 발급하는 파일
  
###   - flow.cp.controller 패키지
front에서 정보를 주고 받는 역할을 하며 받은 정보를 service로 전달하는 역할
  
###   - flow.cp.dto 패키지
데이타 전송 객체로 엔티티 대신해서 데이터 전송을 하기 위해 사용됨
  
###   - flow.cp.entity 패키지
객체를 만들고 mysql db에 테이블을 생성하는 역할

###   - flow.cp.repository 패키지
쿼리를 설정해 service로 부터 받아온 명령에 따라 데이터베이스의 정보를 가져오거나 저장하는 역할을 함
  
###   - flow.cp.service 패키지
controller와 repository의 중간다리 역할로 controller에서 받아온 데이터를 처리해 repository에 명령을 전달하는 역할
  

## 창의설계 프로젝트- backend, API의 출처와 버전
### DB
    MYSQL: ^8.0.32

#### Server
  #### Spring boot - gradle 파일
      implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    	implementation 'org.springframework.boot:spring-boot-starter-web'
    	compileOnly 'org.projectlombok:lombok'
    	developmentOnly 'org.springframework.boot:spring-boot-devtools'
    	runtimeOnly 'com.mysql:mysql-connector-j'
    	annotationProcessor 'org.projectlombok:lombok'
    	testImplementation 'org.springframework.boot:spring-boot-starter-test'
    	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: '2.6.7'
    		testImplementation 'org.springframework.security:spring-security-test'
    	implementation group:'io.jsonwebtoken', name:'jjwt', version:'0.9.1'
    	implementation 'org.springframework.boot:spring-boot-starter-security'
    	implementation group: 'javax.xml.bind', name: 'jaxb-api', version: '2.1'
    	implementation group: 'com.google.apis', name: 'google-api-services-youtube', version: 'v3-rev212-1.25.0'
    	implementation group: 'com.google.oauth-client', name: 'google-oauth-client-jetty', version: '1.34.1'
 

## 시스템 구조
![image](https://github.com/Mkyupark/copykiller_backend/assets/102354411/dd8f6460-f4dd-422d-8e71-011c0555669e)


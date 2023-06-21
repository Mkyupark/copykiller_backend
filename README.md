# 유튜브 카피킬러 - backend
#### 2023-1 창의설계프로젝트 팀 플로우  
해당 프로그램은 웹 기반으로 테블릿에서 유저에게 표절률이 의심되는 영상을 입력과 원본 영상을 입력시 영상 표절률을 제공하는 플렛폼입니다.
주요 기능은 표절의심 구간 비교, 이전 결과 기록 저장, 이전 결과 기록 보기 등이 있으며 이것으로 영상을 표절하는 일을 사전에 방지하고 표절시 구체적인 수치로 신고를 할때 도움이 될 것으로 기대하고 있습니다. 

## 앱 사용영상   

<div align="center">  
  
[![Thumbnail](https://user-images.githubusercontent.com/102962030/206456509-f2e7c97b-3ca0-4efd-a11b-c8fceba4595c.png)](https://youtu.be/ek_KhaDyGWw)    

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
###   - lib 폴더
가장 기본이 되는 main.dart와 그 아래에 각 탭에 해당하는 PageClass가 존재합니다.  
DB 폴더 : 말 그대로 각 탭에서 필요한 자료구조를 정의한 Class 모음   
PROVIDERS 폴더 : 앱 전반에서 필요한 정보(로그인 유저 정보 등)를 PROVIDER Class 로 정의해서 정리해둔 폴더   
LOGIN 폴더 : 로그인을 할 때 필요한 소스코드 모음  
UI 폴더 : 각 탭에 해당하는 UI PageClass 모음  
  
###   - DB 폴더  
각 탭에서 필요한 자료구조를 정의한 Class 모음입니다.  
이름 그대로 1번부터 커뮤니티, 백과사전, 문의사항, (Data는 추상클래스이므로 생략), 맛집지도, 맛집리뷰 자료구조를 정의하였고, 해당 앱은 Firebase를 사용하기 때문에, 각 클래스마다 기본적으로 add 함수와 getData 함수가 존재합니다. (각 firebase로부터 쓰기와 읽기)  
추가적으로 해당 데이터를 Firebase(이하 FB)에서 삭제하거나 갱신하는 등의 함수가 포함됩니다.   
또한 FB로부터 데이터를 읽어서 위젯을 반환하는 함수도 일부 정의되어 있습니다.  
  
###   - LOGIN 폴더  
로그인과 관련된 파일 모음입니다.
EmailLoginPage.dart는 이메일 로그인 시 보여지는 화면에 대한 파일입니다. 
social_login.dart 은 소셜로그인과 관련된 클래스가 상속받아야하는 추상클래스를 정의한 파일입니다. kakao_login과 naver_login파일의 클래스는 이를 상속받습니다. 
firebase_auth_remote_data_source.dart 는 카카오 로그인 토큰을 만들어주는 클래스를 포함합니다.
main_view_model.dart 은 소셜 로그인 시 토큰을 받고 firebaseAuth에 로그인하는 과정을 수행합니다.
  
###   - PROVIDER 폴더  
모든 탭에서 정보가 필요한 LoginedUser 클래스와 기타 일부 탭에서 필요한 DictionaryItemInfo, MapInfo 클래스를 PROVIDER로 정의하였습니다.  
백과사전과 맛집정보는 각각 2번째, 4번째 탭에 존재하는데, 설정 탭에 스크랩한 사전, 작성한 리뷰 등을 볼 수 있도록 하기 위하여 해당 정보를 PROVIDER로 정의하였습니다.  
PROVIDER는 flutter에서 특정 객체의 통신을 위한 Class로, 만약 Scaffold 위젯을 PROVIDER로 wrapping 하게 된다면, Scaffold 하위 위젯들이 해당 PROVIDER 객체를 사용할 수 있습니다. 
  
###   - UI 폴더  
Supplementary 폴더 : 각 탭에서 추가적으로 필요한 페이지나 클래스가 정리  
그 외에는 main.dart 의 하위 위젯들로 각 탭(백과사전, 커뮤니티, 맛집지도, 설정)을 출력하는 주요 Class 들이 포함되어 있습니다.  
  
###   - Supplementary 폴더  
해당 폴더는 각 탭에서 추가적으로 필요한 클래스 모음 폴더입니다.  
파일 이름에 Page가 들어가면 추가 Page가 출력되는 클래스이고, 그렇지 않을 클래스는 탭의 DomainLogic을 돕기 위한 클래스입니다.  
예를 들어 CheckClick이나 ThemeColor Class의 경우 각각 더블 클릭을 방지하기 위한 함수가 정의된 클래스, 앱의 테마 색을 정해둔 클래스로 각 탭의 원활한 동작을 돕습니다. 반대로 Page가 들어간 클래스는 예를 들어 CommunityPostPage 의 경우 커뮤니티 탭에서 특정 게시글을 터치(클릭)했을 때, 해당 게시글을 자세하게 출력하기 위한 클래스로 추가적인 페이지가 생성됩니다.  
  
  
  
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
 
## 기여자   
  
  
<div align="center">
  <a href="https://github.com/dongwon99">
    <img src="https://user-images.githubusercontent.com/102962030/206461747-4d56a152-6963-46ea-853a-603465037070.png", width=200, alt="dongwon", title="dongwon99"/>
  </a>
  <a href="https://github.com/hyejizip">
    <img src="https://user-images.githubusercontent.com/102962030/206461751-ba5681fd-0512-47fb-b04f-8205789e6995.png", width=200, alt="hyeji", title="hyejizip"/>
  </a>
  <a href="https://github.com/Juhyorim">
    <img src="https://user-images.githubusercontent.com/102962030/206461754-a786f9e2-953c-4a9f-971e-62c00ffe2a49.png", width=200, alt="lime", title="lime"/>
  </a>
  <a href="https://github.com/2jin8">
    <img src="https://user-images.githubusercontent.com/102962030/206461745-c50d1bd1-9072-4aa8-954a-1203c22e0eda.png", width=200, alt="2jin8", title="2jin8"/>
  </a>
  

</div>  



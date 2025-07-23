## 소개

<p align="center">

<img width="617" height="132" alt="Group 4 (2)" src="https://github.com/user-attachments/assets/189f8549-2923-451f-a6c3-1cc6d6a5a89d" />
</p>

<p align="center">
Gamesemo(게임세일모아) - 게임 플랫폼 세일 정보 통합 웹사이트

**Gamesemo**는 스팀(Steam), 닌텐도(Nintendo), 게임스플래넷(Games Planet), 다이렉트 게임즈(Direct Games) 등 다양한 게임 플랫폼의 세일 정보를 모아 유저들에게 **쉽고 빠르게** 전달하는 웹 기반 통합 세일 정보 서비스입니다. 
</p>

---

## 목차

- [소개](#소개)
- [개발 기간](#개발-기간)
- [Notion 링크](#notion-링크)
- [프로젝트 목표](#프로젝트-목표)
- [주요 기능](#주요-기능)
- [사용 기술 스택](#사용-기술-스택)
- [화면 구성 예정](#화면-구성-예정)
- [프로젝트 구조](#프로젝트-구조)
- [구현](#구현)


---

## 개발 기간

> **기간:** 2025년 6월 1일 ~ 6월 29일
<p align="center">
 <img width="2388" height="1318" alt="Group 3" src="https://github.com/user-attachments/assets/5c5466a8-98ff-4a35-85d6-9451c599216b" />
</p>

---

## Notion 링크

<p align="center">
   <a href="https://www.notion.so/1-Gamesemo-201678f892a780829c78eb6ea64d315f" target="_blank">
    <img width="443" height="100" alt="Group 5" src="https://github.com/user-attachments/assets/c773344a-b015-4d70-9bc8-b365ac9be5f2" />
  </a>
</p>


---

## 프로젝트 목표

- 여러 플랫폼의 게임 할인 정보를 **한곳에서 확인 가능**하게 만들자
- 위시리스트 기능으로 사용자가 원하는 게임을 **모아보고 관리할 수 있도록**
- 할인 중인 게임이 있으면 **이메일로 자동 알림**
- 각 게임 상세페이지에서 해당 플랫폼의 구매 링크로 **빠르게 이동 가능**

---

## 주요 기능

| 기능 | 설명 |
|------|------|
| 세일 정보 통합 | Steam, Nintendo, Games Planet, Direct Games 세일 정보를 크롤링 또는 API로 취합 |
| 이메일 알림 | 새로 할인으로 등록된 게임들을 취합해 매일 자동 이메일 전송 |
| 위시리스트 | 관심 게임을 저장하고, 세일 여부를 추적 가능 |
| 플랫폼 링크 | 클릭 한 번으로 해당 게임의 플랫폼 스토어 페이지로 이동 |
| 정기 업데이트 | 최신 할인 정보를 주기적으로 자동 반영 |
| 게임 리뷰 | 유튜버들의 게임 리뷰를 제공 |

---

## 사용 기술 스택

- **Backend**: Java 17, Spring Boot, MyBatis, Oracle SQL
- **Frontend**: HTML, CSS, JavaScript, JSP, jQuery
- **ETC**: Apache Tomcat, Lombok, Git/GitHub, Figma, Scheduler, JavaMailSender

### Frontend:

<div align=left>
  <img src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white"> 
  <img src="https://img.shields.io/badge/css-663399?style=for-the-badge&logo=css&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> <br>
  <img src="https://img.shields.io/badge/jquery-0769AD?style=for-the-badge&logo=jquery&logoColor=white">
  <img src="https://img.shields.io/badge/JSP&JSTL-C2302F?style=for-the-badge&logoColor=white">
 <img src="https://img.shields.io/badge/Bootstrapap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"/>
</div>

  
### Backend:

<div align=left>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white">
  <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/MyBatis-000000?style=for-the-badge">
  <img src="https://img.shields.io/badge/Lombok-D9230F?style=for-the-badge">

</div>

### Database:

<div align=left>
   <img src="https://img.shields.io/badge/ORACLE-F80000?style=for-the-badge&logo=oracle&logoColor=white"/>
   <img src="https://img.shields.io/badge/eXERD-750B0F?style=for-the-badge&logo=exerd&logoColor=white">
</div>
  
### Cloud & Deployment: 

<div align=left>
<img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=Amazon%20EC2&logoColor=white">
<img src="https://img.shields.io/badge/apache tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=white">
</div>

### Build Tool: 

<div align=left>
<img src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
</div>


### API & Service: 

<div align=left>
<img src="https://img.shields.io/badge/YouTube API-FF0000?style=for-the-badge&logo=youtube&logoColor=white">
<img src="https://img.shields.io/badge/google smtp-4285F4?style=for-the-badge&logo=google&logoColor=white">
</div>

### Version Control:

<div align=left>
  <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
</div>

### Collaboration & Documentation: 

<div align=left>
  <img src="https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white">
</div>

---


## 프로젝트 구성원 및 업무

| 기능 영역 | 담당자 |Github                                                   |
| ---------------------- | --- | -------------------------------------------------------- |
| 이메일 인증, 게임상세, 공지사항, 세일목록 메일 | 이재원 | [`@wlee412`](https://github.com/wlee412) |
| 회원가입,아이디&비밀번호 찾기 | 양지선 | [`@nyangji`](https://github.com/nyangji) |
| 소셜로그인, 위시리스트 | 이영교 | [`@YeongGyo`](https://github.com/YeongGyo) |
| 메이페이지, 관리자페이지 | 윤태권 | [`@TG-0806`](https://github.com/TG-0806) |
| 게임 DB 정규화, 게임세일 목록 | 윤성찬 | [`@Dae-Ban`](https://github.com/Dae-Ban)
| 자유게시판, 리뷰게시판 | 조민정 | [`@minkimmin`](https://github.com/minkimmin) |

---

## 화면 구성

- 메인 페이지 : 최신 세일 정보, 인기 게임 요약
- 일반 로그인 & 소셜 로그인 / 회원가입 / 비밀번호&아이디 찾기 : 이메일 - 회원 인증 코드 발송
- 마이페이지 : 위시리스트 및 이메일 수신 설정
- 게임 상세페이지 : 게임 타이틀, 원가, 할인가, 위시리스트 추가, 해당 플랫폼 상세페이지 이동, 유튜브 리뷰
- 관리자 페이지 : 공지사항 작성/삭제/수종 , 회원관리, 게시글 관리
- 리뷰게시판 : 추천&비추천, 게시판물 작성 - 스마트에디터 
- 자유게시판 
- 게임 목록 : 플렛폼별 목록, 게임 검색 필터

---

## 프로젝트 구조

```
Gamesemo/
├── build.gradle
├── .gitignore
├── .gitattributes
├── README.md
├── gradle
│ └── wrapper
│ ├── gradle-wrapper.jar
│ └── gradle-wrapper.properties
├── src
│ └── main
│ ├── java
│ │ └── com
│ │ └── example
│ │ └── demo
│ │ ├── config
│ │ ├── controller
│ │ ├── mapper
│ │ ├── merger
│ │ ├── model
│ │ ├── page
│ │ ├── scheduler
│ │ ├── scraper
│ │ ├── security
│ │ ├── service
│ │ ├── task
│ │ ├── util
│ │ └── GamesemoApplication.java
│ ├── resources
│ │ ├── mapper
│ │ ├── static
│ │ │ ├── css
│ │ │ ├── images
│ │ │ ├── images2
│ │ │ ├── js
│ │ │ ├── smarteditor2
│ │ │ └── upload
│ │ └── application.properties
│ └── webapp
│ ├── WEB-INF
│ │ └── views
│ │ ├── admin
│ │ ├── announcement
│ │ ├── common
│ │ ├── community
│ │ ├── game
│ │ ├── login
│ │ ├── member
│ │ ├── report
│ │ ├── review
│ │ ├── verify
│ │ ├── wishlist
│ │ └── main.jsp
│ └── upload
└── test
└── java
└── com
└── example
└── demo
```




---
## 구현 

### 메인화면

<img width="1905" height="883" alt="image" src="https://github.com/user-attachments/assets/1147e4cc-52a5-471c-8043-fbc742020009" />

---

### 세일목록

<img width="1904" height="906" alt="image" src="https://github.com/user-attachments/assets/be8cb652-021c-4bac-b09f-1e11f41695fa" />

---

### 게임상세

<img width="1904" height="908" alt="image" src="https://github.com/user-attachments/assets/d63173cc-275b-4900-a1ba-8470cee1a29a" />


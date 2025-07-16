<p align="center">

<img width="617" height="132" alt="Group 4 (2)" src="https://github.com/user-attachments/assets/189f8549-2923-451f-a6c3-1cc6d6a5a89d" />
</p>

<p align="center">
Gamesemo(게임세일모아) - 게임 플랫폼 세일 정보 통합 웹사이트

**Gamesemo**는 스팀(Steam), 닌텐도(Nintendo), 게임스플래넷(Games Planet), 다이렉트 게임즈(Direct Games) 등 다양한 게임 플랫폼의 세일 정보를 모아 유저들에게 **쉽고 빠르게** 전달하는 웹 기반 통합 세일 정보 서비스입니다. 
</p>

---

## 목차

- [개발 기간](#개발-기간)
- [Notion 링크](#notion-링크)
- [프로젝트 목표](#프로젝트-목표)
- [주요 기능](#주요-기능)
- [사용 기술 스택](#사용-기술-스택)
- [화면 구성 예정](#화면-구성-예정)
- [개발 기간](#개발-기간)
- [메인화면](#메인화면)
- [세일목록](#세일목록)
- [게임상세](#게임상세)

---

## 개발 기간

**기간:** 2025년 6월 1일 ~ 6월 29일


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
| 세일 정보 통합 | Steam, PS5, Nintendo, Xbox 세일 정보를 크롤링 또는 API로 취합 |
| 이메일 알림 | 위시리스트에 등록된 게임이 세일되면 자동 이메일 전송 |
| 위시리스트 | 관심 게임을 저장하고, 세일 여부를 추적 가능 |
| 플랫폼 링크 | 클릭 한 번으로 해당 게임의 플랫폼 스토어 페이지로 이동 |
| 정기 업데이트 | 최신 할인 정보를 주기적으로 자동 반영 |

---

## 사용 기술 스택

- **Backend**: Java 17, Spring Boot, MyBatis, Oracle SQL
- **Frontend**: HTML, CSS, JavaScript, JSP, jQuery
- **ETC**: Apache Tomcat, Lombok, Git/GitHub, Figma, Scheduler, JavaMailSender

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

## 메인화면
<img width="1905" height="883" alt="image" src="https://github.com/user-attachments/assets/1147e4cc-52a5-471c-8043-fbc742020009" />

## 세일목록
<img width="1904" height="906" alt="image" src="https://github.com/user-attachments/assets/be8cb652-021c-4bac-b09f-1e11f41695fa" />

## 게임상세
<img width="1904" height="908" alt="image" src="https://github.com/user-attachments/assets/d63173cc-275b-4900-a1ba-8470cee1a29a" />


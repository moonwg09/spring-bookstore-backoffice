# 📚 MBook - 도서 쇼핑몰 및 사내 백오피스 통합 플랫폼
> **일반 사용자를 위한 도서 이커머스 프론트엔드와 재고(WMS)·발주·정산·상품 마스터 데이터를 관리하는 사내 백오피스를 유기적으로 결합한 통합 웹 플랫폼**

[![Java](https://img.shields.io/badge/Java-11-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring](https://img.shields.io/badge/Spring-STS3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Oracle](https://img.shields.io/badge/Oracle-Database-F80000?style=for-the-badge&logo=oracle&logoColor=white)](https://www.oracle.com/)
[![MyBatis](https://img.shields.io/badge/MyBatis-Framework-000000?style=for-the-badge&logo=apache&logoColor=white)](https://mybatis.org/)
[![PortOne](https://img.shields.io/badge/PortOne-PG%20Integration-00C7AE?style=for-the-badge)](https://portone.io/)

---

## 📌 Project Overview
본 프로젝트는 **쇼핑몰 프론트엔드 사용자 경험(UX)**과 **사내 운영 효율성을 위한 백오피스 관리 시스템**을 유기적으로 연동한 통합 웹 서비스입니다. 
데이터베이스 설계부터 PG 결제 연동, 동적 UI 구현에 이르기까지 웹 애플리케이션 개발의 전 과정에 걸쳐 안정성과 확장성을 고려하여 개발되었습니다.

* **개발 기간**: 2026.03 ~ 2026.06
* **개발 인원**: 백엔드 1명 (개인 프로젝트)
* **담당 역할**: 백엔드 개발, 데이터베이스 설계 및 구축, PG 결제 연동

---

## 🛠 Tech Stack
* **Language**: Java 11
* **Framework**: Spring Framework (STS 3, Legacy), MyBatis
* **Frontend**: JSP, JSTL, HTML/CSS, JavaScript (jQuery)
* **Database**: Oracle Database
* **Payment Gateway**: PortOne (아임포트) SDK
* **Environment**: Apache Tomcat, Git / GitHub

---

## 🚀 Key Features

### 1️⃣ 통합 인증 및 권한 분기 시스템 (Unified Authentication)
* 사내 관리자용 `Employee` 테이블과 쇼핑몰 고객용 `Member` 테이블을 도메인별로 이원화하여 설계
* 로그인 요청 시 통합 컨트롤러에서 관리자 계정 유무를 선검사한 뒤, 성공 권한에 따라 각각 백오피스 메인(`@/backoffice`) 또는 쇼핑몰 메인(`@/shop`)으로 자동 분기 리다이렉트 구현

### 2️⃣ 포트원(PortOne) PG 결제 연동 및 데이터 정합성 보장
* 아임포트(PortOne) SDK를 연동하여 신용카드 등 외부 PG사 간편결제 프로세스 구축
* 결제 승인 후 서버 단에서 검증을 거쳐 장바구니 비우기 및 창고 재고(`Inventory`) 자동 차감 처리를 트랜잭션으로 묶어 데이터 정합성 보장

### 3️⃣ 상품 리뷰 및 평점 도메인
* 구매 고객이 도서별로 평점(1.0~5.0)과 한줄평을 남길 수 있는 리뷰 등록 및 삭제 기능 구현
* 작성자 회원 정보 조인(`LEFT JOIN`)을 통해 상세 페이지 내 실시간 리뷰 목록 및 작성자 표기 반영

### 4️⃣ 사내 백오피스 WMS(재고) 및 기준 정보 관리
* 도서, 저자, 출판사, 카테고리 등 쇼핑몰 마스터 데이터의 CRUD 관리 기능 구현
* 창고 재고 상태(`Inventory`) 실시간 모니터링 및 발주·정산 관리 백오피스 프로세스 구축

### 5️⃣ 장바구니 및 마이룸(주문 내역) 도메인
* 도서 상세 페이지에서 수량 선택 후 장바구니 담기 및 주문/결제 동선 연결
* 마이페이지(마이룸)를 통해 회원의 프로필 정보, 보유 충전금 및 과거 주문 내역 상세 조회 기능 제공

---

## 💡 Troubleshooting & Problem Solving

### ⚠️ 세션 권한 충돌 및 예외 처리 문제
* **문제 상황**: 초기 단일 회원 테이블 구조에서 관리자와 일반 회원을 동시에 처리하려다 보니, 백오피스 접근 시 세션 권한 충돌 및 상세 페이지 진입 시 `NullPointerException` (500 에러) 발생
* **해결 및 성과**: 관리자(`Employee`)와 고객(`Member`) 테이블을 명확히 분리하고, 로그인 컨트롤러와 세션 매핑(`loginEmployee` / `loginUser`)을 이원화함. 이를 통해 권한 검증 로직을 재설계하여 보안성을 강화하고 시스템 전반의 예외 발생 원인을 근본적으로 차단함.

---

## 📂 Project Structure
```text
src/
├── main/
│   ├── java/
│   │   └── com/backoffice/
│   │       ├── controller/   # 쇼핑몰 및 백오피스 컨트롤러
│   │       ├── service/      # 비즈니스 로직 인터페이스 및 구현체
│   │       ├── mapper/       # MyBatis Mapper 인터페이스
│   │       └── model/        # VO 클래스 (Member, Employee, Book, Category 등)
│   ├── resources/
│   │   └── mapper/           # MyBatis SQL XML 맵퍼 파일
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── spring/       # root-context.xml, servlet-context.xml 설정
│       │   └── views/        # 쇼핑몰 및 백오피스 JSP 화면
│       └── resources/        # CSS, JS, 이미지 등 정적 자원
└── .gitignore

# 📚 VBook - 도서 쇼핑몰 및 사내 백오피스 통합 플랫폼
> **일반 고객을 위한 인터넷 서점 프론트엔드와 재고(WMS)·발주·정산 관리를 위한 사내 백오피스가 결합된 통합 웹 서비스**

[![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)](https://www.oracle.com/java/)
[![MyBatis](https://img.shields.io/badge/MyBatis-000000?style=for-the-badge&logo=apache&logoColor=white)](https://mybatis.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

---

## 📌 Project Overview
본 프로젝트는 **쇼핑몰 프론트엔드 사용자 경험(UX)**과 **사내 운영 효율성을 위한 백오피스 관리 시스템**을 유기적으로 연동한 통합 웹 서비스입니다. 
데이터베이스 설계부터 PG 결제 연동, 동적 UI 구현에 이르기까지 웹 애플리케이션 개발의 전 과정에 걸쳐 안정성과 확장성을 고려하여 개발되었습니다.

* **개발 기간**: 2026.03 ~ 2026.06
* **핵심 도메인**: 
  * **프론트엔드 (쇼핑몰)**: 통합 회원 인증, 도서 카탈로그, 장바구니, 포트원 PG 결제, 마이룸, 상품 리뷰
  * **백오피스 (사내 시스템)**: 마스터 데이터 관리(도서/저자/출판사/카테고리), WMS(재고 관리), 발주 및 정산 관리

---

## 🛠 Tech Stack
* **Language**: Java 
* **Framework**: Spring Framework (Spring Legacy), MyBatis
* **Frontend**: JSP, JSTL, HTML/CSS, JavaScript (jQuery)
* **Database**: Oracle Database
* **Payment Gateway**: PortOne (아임포트) SDK
* **Environment**: STS (Spring Tool Suite), Apache Tomcat

---

## 🌟 Key Features & Problem Solving
단순한 구현을 넘어 **구조적 설계와 문제 해결**에 초점을 맞추었습니다.

### 1️⃣ 통합 인증 및 권한 분기 시스템 (Unified Authentication)
* **Problem**: 단일 회원 테이블 구조에서는 사내 관리자와 일반 고객의 권한 충돌 및 보안 취약점 발생 우려.
* **Solution**: 사내 관리자용 `Employee` 테이블과 쇼핑몰 고객용 `Member` 테이블을 이원화 설계. 공통 로그인 컨트롤러에서 관리자 여부를 선검사하여 성공 권한에 따라 각각 백오피스 메인(`@/backoffice`) 또는 쇼핑몰 메인(`@/shop`)으로 자동 분기 리다이렉트 구현.

### 2️⃣ 포트원(PortOne) PG 간편결제 및 트랜잭션 정합성 보장 🔒
* **Solution**: 외부 PG사(이니시스 등) 결제 연동 API를 활용해 신용카드 결제 프로세스 구축. 서버 단에서 결제 검증과 동시에 **`@Transactional`**을 활용하여 주문 데이터 생성, 장바구니 비우기, 창고 재고(`Inventory`) 차감 로직을 하나의 원자적 단위로 묶어 데이터 무결성 확보.

### 3️⃣ 다층 계층형 카테고리와 동적 네비게이션바 🏷️
* **Solution**: 부모-자식(`parent_id`) 구조의 카테고리 데이터를 자바 단에서 트리 구조로 계층화하여 상단 네비게이션 바에 마우스 호버(Hover) 드롭다운 형태로 동적 렌더링 구현.

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

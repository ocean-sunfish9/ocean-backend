# 9해조 개복치(커뮤니티 사이트 프로젝트)

![개복치 로고](https://github.com/user-attachments/assets/ff72a8e1-cdfb-46da-8771-2efbaa3393df)

### 👊 Cache을 이용한 성능개선 프로젝트 👊

#### 프로젝트 진행 기간: 24.11.22 ~ 24.11.29

## 프로젝트 목표

#### In-memory/Redis Cache을 이용한 검색 성능개선 구현 <br>

## 👨‍👨‍👧‍👧 팀 구성

| 이름    | 역할 | 담당 기능                                        |
|-------|----|----------------------------------------------|
| 조성준   | 팀원 | Docker-Compose(Mysql, Redis), GithubActions CI, Redis 파이프 라인 설정, 인기 게시글 검색기능  |
| 이하승   | 팀원 | 게시글 검색 기능 In-memory / redis 이용해 성능 개선,  Ngrinder 성능 측정                      |
| 김동주   | 팀원 | SpringSecurity, JWT, 어뷰징 방지, 인기 검색어 조회                                           |
| 고아라   | 팀장 | 댓글 작성 수정 삭제 CRUD, 연관관계 매핑, 와이어 프레임                                        |

## Tools

### 🖥 language & Server 🖥

<img src="https://img.shields.io/badge/intellij idea-207BEA?style=for-the-badge&logo=intellij%20idea&logoColor=white"> <br>
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> <br>
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"> <br>
<img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> <hr>

### 👏 Cowork Tools 👏

<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> <br> 
<img src="https://img.shields.io/badge/notion-000000?style=or-the-badge&logo=notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-FE5196?style=or-the-badge&logo=slack&logoColor=white"/>
<br>
<hr/>

## 와이어 프레임
![Screenshot 2024-11-28 at 22 54 41](https://github.com/user-attachments/assets/ee4a4b38-3a58-4353-804a-4684a8725c16)

## ERD

<img width="1603" alt="캡처5" src="https://github.com/user-attachments/assets/581d6a6e-9171-4cda-a12e-9e1afc9cf6cc">

## 캐싱 전략

### 캐싱 전략 3대 요소
![image](https://github.com/user-attachments/assets/d9323b5e-5dab-456b-804f-e94b39e10752)
#### 빈번한 접근
- 자주 사용하는 데이터의 '복사본'을 저장함으로써 데이터 요청 시 DB 접근을 줄여 성능을 향상시킴.
#### 복잡한 연산 감소
- 데이터를 처리하기 위해 복잡한 로직이 필요한 경우, 결과를 캐싱하여 불필요한 재계산을 방지함.
#### 변경되지 않는 데이터
- 자주 변경되지 않는 데이터의 경우 캐싱하여 효율성을 높일 수 있음.

---

### Cache-Aside (Lazy Loading)
![image](https://github.com/user-attachments/assets/adec1bea-5603-4a14-8a1e-450b09edb7b3)
![image](https://github.com/user-attachments/assets/1419fec0-869e-4ab3-89d3-30ddd63b3c92)



Cache-Aside 패턴은 필요할 때만 데이터를 캐시에 로드하는 방식입니다. 데이터가 요청될 때 캐시에서 찾고, 없으면 데이터베이스에서 가져와 캐시에 저장합니다. 이 방식은 데이터 일관성을 유지하기 쉽고, 필요할 때만 캐시를 사용하므로 메모리 사용량을 줄일 수 있습니다.

### Write-Back
![image](https://github.com/user-attachments/assets/003acdbc-6270-405f-b51f-1efcdb362416)


Write-Back 패턴은 데이터가 수정될 때 즉시 데이터베이스에 반영하지 않고, 캐시에만 저장한 후 일정 주기마다 한꺼번에 데이터베이스에 반영하는 방식입니다. 이 방식은 쓰기 작업을 효율적으로 처리할 수 있지만, 데이터 손실 가능성이 있으므로 주의가 필요합니다.

### Master & Slave + Sentinel
![image](https://github.com/user-attachments/assets/109f0141-1c59-41cc-af8e-43fc0dd0a26e)

1. **Master**
  - 모든 쓰기 작업을 처리하는 기본 Redis 서버.
  - 데이터를 Slave 에게 전송하여 항상 최신 데이터 유지
2. **Slave**
  - 데이터를 복제하는 보조 Redis 서버.
  - 읽기 작업을 주로 처리하며, Master가 다운될 경우 백업 역할 가능.
3. **Sentinel**
  - 모니터링, 자동 장애 복구, 알림을 담당하며 고가용성(high availability)을 제공.

## 캐싱을 사용하는 이유

1. **성능 향상**: 자주 접근하는 데이터를 캐시에 저장하여 데이터베이스 접근 시간을 줄이고 응답 속도를 향상시킬 수 있습니다.
2. **부하 분산**: 캐싱을 통해 데이터베이스에 대한 접근 빈도를 줄여 데이터베이스 부하를 분산시킬 수 있습니다.
3. **비용 절감**: 데이터베이스 사용량을 줄여 비용을 절감할 수 있습니다. 특히 클라우드 서비스에서는 데이터베이스 사용량이 비용에 직접적으로 영향을 미칩니다.

## Performance Test

### V1. Non-Caching
![image](https://github.com/user-attachments/assets/aa77578a-0778-4ddb-bfbb-ecc3c9eed604)

### V2. In-Memory-Caching
![image](https://github.com/user-attachments/assets/1be6c3e1-4787-4a8a-8afe-468d43fcbc50)

### V3. Redis-Caching
![image](https://github.com/user-attachments/assets/530b00a1-ca5f-42c5-9f9c-7226312f8152)

<img width="961" alt="캡처6" src="https://github.com/user-attachments/assets/58d3322e-7576-4444-bf55-10fb1e2c4a60">

## 개발 환경 공통화
![image](https://github.com/user-attachments/assets/12f9be15-87a7-481c-9452-e9e5b6d47174)


### 1. 일관된 개발 환경 제공
- 동일한 컨테이너 환경에서의 작업으로 “환경 불일치 문제“ 해결.
- “작동하는 내 컴퓨터“ 문제 방지.

### 2. 개발 속도 향상
- 프로젝트 환경 설정 및 관리가 쉽고 빠름.
- 프로젝트 중도 참여 시 환경 구축 및 작업이 쉽다.

### 3. 확장성 및 유연성
- 수평적 확장 용이성: 컨테이너 기반의 환경은 애플리케이션의 확장을 매우 유연하게 만들어줍니다. 동일한 컨테이너를 여러 개 배포하여 빠르게 시스템을 확장할 수 있습니다.
- 다양한 서비스 배포: 각 기능을 독립된 컨테이너로 분리하여 마이크로서비스 아키텍처로의 전환이 쉽고, 특정 서비스에 대한 독립적 업데이트 및 확장이 가능합니다.
- 클라우드 플랫폼 연동: Docker는 AWS, GCP, Azure와 같은 클라우드 서비스와의 연동이 쉬워 인프라를 클라우드로 이동하거나 통합할 때 유연하게 대처할 수 있습니다.

## API 명세서

### 인증 API

#### 회원가입 (Sign Up)
- **URL**: `/api/auth/signup`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string",
    "email": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "username": "string",
    "email": "string"
  }
  ```
- **Description**: 이 API는 사용자가 새로운 계정을 등록할 수 있도록 합니다. 요청 본문에 유저명, 비밀번호, 이메일을 포함해야 합니다.

#### 로그인 (Login)
- **URL**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "token": "string"
  }
  ```
- **Description**: 이 API는 사용자가 로그인하고 인증 토큰을 받을 수 있도록 합니다. 유저명과 비밀번호를 요청 본문에 포함해야 하며, 성공적으로 인증되면 토큰이 반환됩니다.

### 게시글 API

#### 게시글 생성 (Create Post)
- **URL**: `/api/posts`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "title": "string",
    "content": "string",
    "category": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number"
  }
  ```
- **Description**: 이 API는 새로운 게시글을 생성합니다. 요청 본문에 제목, 내용, 카테고리를 포함해야 합니다.

#### 게시글 검색 (Search Posts)
- **URL**: `/api/posts/search`
- **Method**: `GET`
- **Query Parameters**:
  - `keyword`: 검색 키워드
  - `page`: 페이지 번호
  - `size`: 페이지 크기
- **Response**:
  ```json
  {
    "content": [
      {
        "id": "number",
        "title": "string",
        "content": "string",
        "category": "string"
      }
    ],
    "totalPages": "number",
    "totalElements": "number"
  }
  ```
- **Description**: 이 API는 검색 키워드에 따라 게시글을 검색하고, 페이지네이션된 결과를 반환합니다.

### 댓글 API

#### 댓글 생성 (Create Comment)
- **URL**: `/api/comments`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "postId": "number",
    "content": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "postId": "number",
    "content": "string"
  }
  ```
- **Description**: 이 API는 특정 게시글에 댓글을 생성합니다. 요청 본문에 게시글 ID와 댓글 내용을 포함해야 합니다.

#### 댓글 수정 (Update Comment)
- **URL**: `/api/comments/{id}`
- **Method**: `PUT`
- **Request Body**:
  ```json
  {
    "content": "string"
  }
  ```
- **Response**:
  ```json
  {
    "id": "number",
    "postId": "number",
    "content": "string"
  }
  ```
- **Description**: 이 API는 특정 댓글의 내용을 수정합니다. 요청 본문에 새로운 댓글 내용을 포함해야 합니다.

#### 댓글 삭제 (Delete Comment)
- **URL**: `/api/comments/{id}`
- **Method**: `DELETE`
- **Description**: 이 API는 특정 댓글을 삭제합니다. 댓글 ID를 URL 경로에 포함해야 합니다.


## 프로젝트 구조

```plaintext
api
├── abuse.service
├── auth
├── comment
├── enums
├── filter
├── handler
├── hotkeyword
├── post
├── user
└── util
common
├── annotation
├── entity
├── exception
├── validator
└── CustomPageImpl
config
domain
├── abuse -> 김동주
├── comment -> 고아라
├── hotkeyword -> 조성준, 김동주
├── keyword -> 조성준
├── post -> 조성준, 이하승
└── user-> 김동주
```

## Application 핵심 기능 시연 영상




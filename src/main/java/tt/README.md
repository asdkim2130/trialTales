# 체험단 사이트 서비스 관계도
| 1         | ----> | N    |
|-----------|-------|------|
| Member (1)| ---->  | (N)Campaign(체험단종류)|
| Member (1)| ---->  | (N) Application(체험단신청)|
| Campaign (1)| ---->  | (N) Application(체험단신청) |
| Member (1)| ---->  | (N) Review(리뷰)|
| Campaign (1)| ---->  |  (N) Review(리뷰)|

---

# Campaign
## Campaign 생성
- "/campaigns" 로 생성
- 관리자만 접근 가능
- 관리자 권한이 없으면 에러
- 모집기간을 7일로 설정, 기간이 지나면 모집종료 업데이트


## Campaign - 조회
-  "/{campaignId}"로 조회
-  캠페인Id로 조회


## Campaign - 수정
-  "/{campaignId}"로 수정
-  관리자만 접근 가능
-  관리자 권한이 없으면 에러

## Campaign - 삭제
-  /{campaignId} 로 삭제
-  관리자만 접근 가능
-  관리자 권한이 없으면 에러

## 모집 종료된 캠페인 조회
-  "/expired"로 조회

## 스케줄링 작업: 
- 모집 종료 상태 업데이트

---

# Application

## Application 생성 
-  일반 사용자가 작성 가능

## Application 조회
-  관리자 권한 필요 

### 개별 신청서 조회
- application_Id로 조회

### 특정 사용자가 작성한 모든 신청 목록 조회
- member_Id로 조회


### 승인 상태(PENDING, APPROVED)별 신청 목록 조회
- Status로 조회(Query dsl사용)

## Application 수정
-  관리자 권한 필요
-  신청 상태 status 변경만 가능(PENDING -> APPROVED)

## Application 삭제
- 관리자 권한 필요

---

# Review
*추가기능:별점 순 오름차순(Query dsl)

생성회원생성->켐페인생성->순으로 진행완료시
리뷰작성 가능(userId)

## 조회
- (userId)로 조회

## 수정
- 수정은 관리자만 접근할수 있도록 서비스에서 비즈니스로직 구현(reviewId)

## 삭제
- 삭제는 관리자만 접근할수 있도록 서비스에서 비즈니스로직 구현(reviewId)

---
# Member
## 회원가입 처리
- 기본 USER
- 비밀번호 암호화

## 로그인 처리
- 사용자 정보 확인 후 로그인처리
- JWT 토큰 생성 및 반환
- 아이디 또는 비밀번호 오류 에러


## 회원탈퇴
- 관리자만 다른 사용자를 탈퇴 기능
- 일반 사용자는 자기 자신만 탈퇴할 수 있음

## 닉네임 수정 처리
- 관리자나 본인만 닉네임을 변경 할 수 있음
- 닉네임 중복 시 에러

## 로그인한 사용자의 닉네임 조회
- 사용자 정보 조회
- 사용자 이름으로 회원을 찾기
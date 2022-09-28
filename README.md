# 실시간 순위 정보 제공 서비스 개발
 
### 개요
고객 문의를 접수하고 상담사가 답변을 할 수 있는 프론트엔드 및 API 애플리케이션을 작성하는 과제입니다.


### 프로젝트 설명
* 환경 구성
1. Spring Boot 2.5.3
2. Java 11
3. DB h2
4. JPA, Spring Security, JWT
4. 사용 툴 : STS, Chrome

* 소스 구조

|-- README.md
`-- src/main/java/com/kakaopaysec/rrss
    |--api
    |   |-- log
    |   |   |-- controller
	|   |   |-- service
	|	|   |-- repository
	|	|   |-- dto
	|	|   |-- entity
	|   |   `-- mapper
    |   |-- stock
    |   |   |-- controller
	|   |   |-- service
	|	|   |-- repository
	|	|   |-- dto
	|	|   |-- entity
	|   |   `-- mapper
    |   |-- trade
    |       |-- controller
	|       |-- service
	|		|-- repository
	|		|-- dto
	|		|-- entity
	|       `-- mapper
    `-- core
        |-- config
        |-- error
        |-- handler
        |-- scheduler
        `-- util
        
    
* 테이블 명세

STOCK 종목 테이블

| 필드           | 설명           | 기타 |
|----------------|----------------|------|
| STOCK_SEQ      | 종목코드       |      |
| STOCK_NAME     | 종목명         |      |
| CREATE_USER_ID | 생성 사용자 ID |      |
| CREATE_DATE    | 생성 일시      |      |
| MODIFY_USER_ID | 수정 사용자 ID |      |
| MODIFY_DATE    | 수정 일시      |      |


TRADE 거래 테이블

| 필드           | 설명           | 기타                   |
|----------------|----------------|------------------------|
| TRADE_SEQ      | 거래 순번      |                        |
| STOCK_SEQ      | 종목코드       |                        |
| TRADE_TYPE     | 매수/매도      | 001 : 매수, 002 : 매도 |
| OLD_PRICE      | 기존 금액      |                        |
| TRADE_PRICE    | 매수/매도 금액 |                        |
| TRADE_DAY    	 | 일자         	|      |
| CREATE_USER_ID | 생성 사용자 ID |                        |
| CREATE_DATE    | 생성 일시      |                        |


HISTORY 히스토리 테이블

| 필드           | 설명           | 기타 |
|----------------|----------------|------|
| LOG_SEQ        | 로그 순번      |      |
| STOCK_SEQ      | 종목코드       |      |
| BEGIN_PRICE    | 시작금액       |      |
| END_PRICE      | 마감금액       |      |
| VIEW_CNT       | 조회수         |      |
| TRADE_CNT      | 거래수         |      |
| LOG_DAY    	 | 일자         	|      |
| CREATE_USER_ID | 생성 사용자 ID  |      |
| CREATE_DATE    | 생성 일시      |      |


### 실행 방법
1. 모든 제의 상위 5건 조회
	* 호출 URL : GET localhost:9090/api/v1/realtime-ranking
	* 파라미터 : 페이지 사이즈 (기본 20, 예시 : localhost:9090/api/v1/realtime-ranking?size=20)
	* 리턴 값 : 사용자 목록, 페이징 정보 리턴		
2. 주제별 API 조회
	* 호출 URL : GET localhost:9090/api/v1/realtime-ranking/{subject}
	* 파라미터 : 주제 파라메터 (VIEW : 많이 본,  INCREASE : 많이 오른, REDUCE : 많이 내린, VOLUME : 거래량 많은)
	* 리턴 값 : 사용자 정보, 존재하지 않는 경우 NO_CONTENT(204) 발생 
3. 순위 랜덤 변경
	* 호출 URL : PUT localhost:9090/api/v1/random-trade
	* 리턴 값 : 성공 시 OK(200) 발생
4. 스케줄러 호출
	* 스케줄러는 5초마다 동작 
	* 시작 호출 URL : GET localhost:9090/api/v1/random-trade-start
	* 종료 호출 URL : GET localhost:9090/api/v1/random-trade-end


### 문제 해결 전략
- 요구사항에 부합하며 간단한 DB 구조설계
- 구조화된 API 설계로 직관적인 프로그래밍
- JPA 사용을 통해 수월한 도메인 관리 가능
- JWT, Spring Security 개념 적용을 통해 backend 안정화


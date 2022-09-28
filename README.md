# 실시간 순위 정보 제공 서비스 개발
 
### 개요
고객에게 실시간 순위 정보를 제공하는 서비스를 개발합니다. 
메인화면에는 주제별 상위 5건 데이터를 한번에 보여주고, 상세화면에는 주제 각각의 순위를 최대 100건 까지 제공합니다.

### 프로젝트 설명
* 환경 구성
1. Spring Boot 2.5.3
2. Java 11
3. DB h2
4. 사용 툴 : STS, Chrome, Postman

* 소스 구조

|-- README.md
`-- src/main/java/com/kakaopaysec/rrss
    |--api
    |   |-- realtimeranking : 실시간 순위
    |   |   |-- controller
    |   |-- log : 일자/종목 별 조회수, 거래량 관리
    |   |   |-- controller
	|   |   |-- service
	|	|   |-- repository
	|	|   |-- dto
	|	|   |-- entity
	|   |   `-- mapper
    |   |-- stock : 종목 관리
    |   |   |-- controller
	|   |   |-- service
	|	|   |-- repository
	|	|   |-- dto
	|	|   |-- entity
	|   |   `-- mapper
    |   |-- trade : 거래내역 관리, 순위 랜덤 변경
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
| TRADE_TYPE     | 매수/매도      | 001 : 매수, 002 : 매도, 003 : 초기데이터  |
| OLD_PRICE      | 기존 금액      |                        |
| TRADE_PRICE    | 매수/매도 금액                         |
| TRADE_DAY    	 | 일자         	|      				  |
| TRADE_TIME     | 일시			|    					  |
| CREATE_USER_ID | 생성 사용자 ID |                        |
| CREATE_DATE    | 생성 일시      |                        |


LOG 로그 테이블

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
| MODIFY_USER_ID | 수정 사용자 ID |      |
| MODIFY_DATE    | 수정 일시      |      |


### 실행 방법
1. 모든 주제의 상위 5건 조회
	* 호출 URL : GET localhost:9090/api/v1/realtime-ranking
	* 파라미터 : 페이지 사이즈 (기본 20, 예시 : localhost:9090/api/v1/realtime-ranking?size=20)
	* 리턴 값 : 주제별 상위 5건 목록, 페이징 정보 리턴, 성공 시 OK(200) 발생
	  * 순위 항목 정보 - stockName":종목명","beginPrice":시작가,"endPrice":현재가,"viewCnt":조회수,"tradeCnt":거래량,"differenePrice":차액,"differenePriceRate":차액비율
2. 주제별 API 조회
	* 호출 URL : GET localhost:9090/api/v1/realtime-ranking/{subject}
	* 파라미터 : 페이지 사이즈 (기본 20, 예시 : localhost:9090/api/v1/realtime-ranking?size=20)
			  주제 파라메터 (VIEW : 많이 본,  INCREASE : 많이 오른, REDUCE : 많이 내린, VOLUME : 거래량 많은)
	* 리턴 값 : 주제별 상위 n건 목록, 성공 시 OK(200) 발생
	  * 순위 항목 정보 - stockName":종목명","beginPrice":시작가,"endPrice":현재가,"viewCnt":조회수,"tradeCnt":거래량,"differenePrice":차액,"differenePriceRate":차액비율
3. 순위 랜덤 변경
	* 호출 URL : PUT localhost:9090/api/v1/random-trades
	* 리턴 값 : 성공 시 OK(200) 발생
4. 스케줄러 호출
	* 스케줄러는 5초마다 동작 
	* 시작 호출 URL : GET localhost:9090/api/v1/random-trades/starat
	* 종료 호출 URL : GET localhost:9090/api/v1/random-trades/end


### 문제 해결 전략
- 요구사항에 부합하고 간단한 DB 구조설계를 했습니다.
- 실제로 주식 거래가 일어나듯 거래데이터 기준으로 실시간 순위를 조회했습니다. 단, 각 종목의 주식의 개수는 1개로 전제합니다.
- 스케줄러를 이용하여 5초단위로 거래데이터를 적재하는 기능도 추가 개발했습니다.
- 구조화된 API 설계로 도메인 관리가 수월했으며 이로인해 직관적인 프로그래밍을 했습니다.


# SPRINGBOOT

시작하기
---
|TITLE|LINK|DESCRIPTION|
|------|---|---|
|SB_INIT_JSP|[바로가기](DOCUMENT/01_)|-|
|SB_PARAMETER|[바로가기](DOCUMENT/02_)|-|
|SB_VALIDATION|-|생략|
|SB_EXCEPTION|-|생략|
|SB_DATASOURCE|-|생략|
|SB_MYBATIS|-|생략|
|SB_JPA|[바로가기](DOCUMENT/07_)|-|
|SB_TX|[바로가기](DOCUMENT/08_)|-|
|RESTCONTROLLER|-|생략|
|RESTFUL API|[바로가기](DOCUMENT/10_)|-|
|UP/DOWNLOAD|-|생략|
|AOP|-|생략|
|FILTER_INTERCEPTOR|-|생략|
|LISTENER|-|생략|
|SCHEDULED|-|생략|
|BATCH|[바로가기](DOCUMENT/16_)|-|
|SPRINGSECURITY|[바로가기](DOCUMENT/17_)|-|

TMP
---
```
CREATE TABLE if not exists `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

```







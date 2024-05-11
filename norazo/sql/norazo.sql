CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	VARCHAR2(100)		NOT NULL,
	"MEMBER_NICKNAME"	NVARCHAR2(10)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	VARCHAR2(300)		NULL,
	"GENDER"	CHAR(1)		NOT NULL,
	"BIRTH_DATE"	DATE		NOT NULL,
	"PROFILE_IMG"	VARCHAR(300)		NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_INTRODUCE"	NVARCHAR2(300)		NULL
);

COMMENT ON COLUMN "MEMBER"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "MEMBER"."MEMBER_EMAIL" IS '회원 이메일';
COMMENT ON COLUMN "MEMBER"."MEMBER_PW" IS '회원 비밀번호';
COMMENT ON COLUMN "MEMBER"."MEMBER_NICKNAME" IS '회원 닉네임';
COMMENT ON COLUMN "MEMBER"."MEMBER_TEL" IS '회원 전화번호';
COMMENT ON COLUMN "MEMBER"."MEMBER_ADDRESS" IS '회원 주소';
COMMENT ON COLUMN "MEMBER"."GENDER" IS '회원 성별(M, F)';
COMMENT ON COLUMN "MEMBER"."BIRTH_DATE" IS '회원 생년월일';
COMMENT ON COLUMN "MEMBER"."PROFILE_IMG" IS '프로필 이미지';
COMMENT ON COLUMN "MEMBER"."ENROLL_DATE" IS '회원가입일';
COMMENT ON COLUMN "MEMBER"."MEMBER_DEL_FL" IS '탈퇴여부(Y, N)';
COMMENT ON COLUMN "MEMBER"."MEMBER_INTRODUCE" IS '회원 자기소개';

--SELECT * FROM "MEMBER";




CREATE TABLE "UPLOAD_FILE" (
	"FILE_NO"	NUMBER		NOT NULL,
	"FILE_PATH"	VARCHAR2(500)		NOT NULL,
	"FILE_ORIGINAL_NAME"	VARCHAR2(300)		NOT NULL,
	"FILE_RENAME"	VARCHAR2(100)		NOT NULL,
	"FILE_UPLOAD_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "UPLOAD_FILE"."FILE_NO" IS '파일 번호(PK)';
COMMENT ON COLUMN "UPLOAD_FILE"."FILE_PATH" IS '파일 요청 경로';
COMMENT ON COLUMN "UPLOAD_FILE"."FILE_ORIGINAL_NAME" IS '파일 원본명';
COMMENT ON COLUMN "UPLOAD_FILE"."FILE_RENAME" IS '파일 변경명';
COMMENT ON COLUMN "UPLOAD_FILE"."FILE_UPLOAD_DATE" IS '업로드 날짜';
COMMENT ON COLUMN "UPLOAD_FILE"."MEMBER_NO" IS '회원번호(PK)';




CREATE TABLE "BOARD" (
	"BOARD_NO"	NUMBER		NOT NULL,
	"BOARD_TITLE"	NVARCHAR2(100)		NOT NULL,
	"BOARD_CONTENT"	NVARCHAR2(2000)		NOT NULL,
	"BOARD_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"BOARD_UPDATE_DATE"	DATE		NULL,
	"BOARD_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_CODE"	NUMBER		NOT NULL,
	"SPORTS_CODE"	NVARCHAR2(20)		NULL,
	"MEMBER_COUNT_LIMIT"	NUMBER		NULL,
	"MEETING_LOCATION"	NVARCHAR2(100)		NULL,
	"MEETING_DATE"	DATE		NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글 번호(PK)';
COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글 제목';
COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글 내용';
COMMENT ON COLUMN "BOARD"."BOARD_WRITE_DATE" IS '게시글 작성일';
COMMENT ON COLUMN "BOARD"."BOARD_UPDATE_DATE" IS '게시글 마지막 수정일';
COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '삭제 여부(Y, N)';
COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '작성한 회원번호(FK)';
COMMENT ON COLUMN "BOARD"."BOARD_CODE" IS '게시판 종류 코드번호(FK)';
COMMENT ON COLUMN "BOARD"."SPORTS_CODE" IS '스포츠 종목';
COMMENT ON COLUMN "BOARD"."MEMBER_COUNT_LIMIT" IS '참가인원 제한수 (모임글)';
COMMENT ON COLUMN "BOARD"."MEETING_LOCATION" IS '모임 장소(모임글)';
COMMENT ON COLUMN "BOARD"."MEETING_DATE" IS '모임 날짜(모임글)';
	
CREATE SEQUENCE SEQ_BOARD_NO;

--SELECT * FROM BOARD WHERE BOARD_CODE = 1;

-- 스포츠 타입 조회
--SELECT * FROM SPORTS_TYPE;




------------------- 모임 게시글 샘플 데이터 -------------------
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '5:5 풋살 하실 분들 모집합니다', 'A', DEFAULT, NULL, DEFAULT, 1, 1, 'football', 10, '월드컵 경기장');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '즐겁게 축구 같이 하실 분들 지금 당장 참석!!!', 'B', DEFAULT, NULL, DEFAULT, 1, 1, 'football', 10, '용산구 축구장');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '전 축구 국가대표 출신 무료 강좌!', 'C', DEFAULT, NULL, DEFAULT, 1, 1, 'football', 10, '은평구 축구장');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '오후 3시부터 달리실 분~~~', 'D', DEFAULT, NULL, DEFAULT, 1, 1, 'football', 10, '종로구 축구장');
--
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '농구 3:3 인원 모집', 'D', DEFAULT, NULL, DEFAULT, 1, 1, 'basketball', 6, '마포구 농구장');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '소수인원으로 농구하실 분 지금 바로 참석', 'D', DEFAULT, NULL, DEFAULT, 1, 1, 'basketball', 4, '평화의 공원 1번 코트장');
--
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '핸드볼 처음이신 분도 하실 수 있습니다 [삼척핸드볼스포츠클럽]', 'D', DEFAULT, NULL, DEFAULT, 1, 1, 'handball', 10, '삼척핸드볼스포츠클럽');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '올림픽 공원에서 핸드볼 경기 진행 예정!', 'D', DEFAULT, NULL, DEFAULT, 1, 1, 'handball', 10, '올림픽공원');
--
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '더클라임 연남점에서 만나요!', '초보분들도 환영입니다', DEFAULT, NULL, DEFAULT, 3, 1, 'climbing', 8, '더클라임 연남점', '2024-05-16');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '뛰뛰동산 알레에서 함께 뛰어요', '뛰뛰동산 알레 좋아하는 클라이머 모여랏!', DEFAULT, NULL, DEFAULT, 5, 1, 'climbing', 6, '알레 강동점', '2024-05-17');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '염창 플라스틱 홀드 뿌셔뿌셔', '다같이 염라스틱 클밍 후 맛있는 핫도그 먹어용!', DEFAULT, NULL, DEFAULT, 7, 1, 'climbing', 6, '더 플라스틱 염창점', '2024-05-18');
--
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '야구 동호회 신입 모집합니다', '재미있게 해봅시다', DEFAULT, NULL, DEFAULT, 1, 1, 'baseball', 10, '숭실고등학교 운동장', '2024-05-18');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '동대항전 야구 경기 진행 중입니다', '정정당당 승부 벌여봅시다', DEFAULT, NULL, DEFAULT, 1, 1, 'baseball', 10, '신사동 야구 베이스 캠프', '2024-05-17 11:00:00');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '야구에 관심 있는 분들 지금 바로 참석 클릭!!!', '잠심 야구 경기장에서 진행되는 야구 시합', DEFAULT, NULL, DEFAULT, 1, 1, 'baseball', 10, '잠심 야구 경기장', '2024-05-18 14:00');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '연세대 VS 고려대', '연고전인가 고연전인가 장소는 연세대학교 운동장에서 결정된다', DEFAULT, NULL, DEFAULT, 1, 1, 'baseball', 10, '연세대학교 운동장', '2024-05-19 10:00');
--
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '300평의 클라이밍장 더클 양재로 오세요', '안클 즐클 행클!', DEFAULT, NULL, DEFAULT, 1, 1, 'climbing', 10, '더클라임 양재점', '2024-05-21 14:00');
--INSERT INTO "BOARD" VALUES(SEQ_BOARD_NO.NEXTVAL, '캐치스톤에서 즐클 해봅시당', '층고 높고 시원시원한 캐치스톤에서 다함께 즐클해요', DEFAULT, NULL, DEFAULT, 1, 1, 'climbing', 10, '부천역 캐치스톤', '2024-05-20 19:00');



CREATE TABLE "BOARD_TYPE" (
	"BOARD_CODE"	NUMBER		NOT NULL,
	"BOARD_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_CODE" IS '게시판 종류 코드번호(PK)';
COMMENT ON COLUMN "BOARD_TYPE"."BOARD_NAME" IS '게시판 이름';

INSERT INTO "BOARD_TYPE" VALUES(1, '모임 게시판');
INSERT INTO "BOARD_TYPE" VALUES(2, '자유 게시판');
INSERT INTO "BOARD_TYPE" VALUES(3, '문의 게시판');
INSERT INTO "BOARD_TYPE" VALUES(4, '내가 만든 모임');
INSERT INTO "BOARD_TYPE" VALUES(5, '내가 속한 모임');
INSERT INTO "BOARD_TYPE" VALUES(6, '내가 찜한 모임');

--SELECT * FROM "BOARD_TYPE";




CREATE TABLE "BOARD_LIKE" (
	"LIKE_NO" NUMBER 		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_LIKE"."LIKE_NO" IS '좋아요 순서(PK)';
COMMENT ON COLUMN "BOARD_LIKE"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "BOARD_LIKE"."BOARD_NO" IS '게시글 번호(PK)';

CREATE SEQUENCE SEQ_LIKE_NO NOCACHE;

--SELECT * FROM BOARD_LIKE;





CREATE TABLE "BOARD_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NOT NULL,
	"IMG_ORIGINAL_NAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_RENAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_ORDER"	NUMBER		NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_IMG"."IMG_NO" IS '이미지 번호(PK)';
COMMENT ON COLUMN "BOARD_IMG"."IMG_PATH" IS '이미지 경로';
COMMENT ON COLUMN "BOARD_IMG"."IMG_ORIGINAL_NAME" IS '이미지 원본명';
COMMENT ON COLUMN "BOARD_IMG"."IMG_RENAME" IS '이미지 변경명';
COMMENT ON COLUMN "BOARD_IMG"."IMG_ORDER" IS '이미지 순서';
COMMENT ON COLUMN "BOARD_IMG"."BOARD_NO" IS '게시글 번호(FK)';

-- 서버에서 게시글 작성 시 이미지 삽입 대 사용할 시퀀스 번호
CREATE SEQUENCE SEQ_IMG_NO NOCACHE;

-- SEQ_IMG_NO 시퀀스의 다음 값을 반환하는 함수 생성
--CREATE OR REPLACE FUNCTION NEXT_IMG_NO
---- 반환형
--RETURN NUMBER
---- 사용할 변수
--IS IMG_NO NUMBER;
--BEGIN
--	SELECT SEQ_IMG_NO.NEXTVAL
--	INTO IMG_NO
--	FROM DUAL;
--	RETURN IMG_NO;
--END;
--;

--SELECT NEXT_IMG_NO() FROM DUAL;

--SELECT * FROM BOARD_IMG
--ORDER BY IMG_NO DESC; 


-- 축구 사진
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test1', '001.png', NULL, 1);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test2', '002.webp', NULL, 2);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test3', '003.png', NULL, 3);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test4', '004.webp', NULL, 4);
--
-- 농구 사진
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test5', '005.webp', NULL, 21);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test6', '006.webp', NULL, 22);
--
-- 핸드볼 사진
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test7', '007.webp', NULL, 23);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test8', '008.webp', NULL, 24);
--
-- 클라이밍 사진
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test9', '009.jpg', NULL, 61);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test10', '010.jpg', NULL, 62);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test11', '011.jpg', NULL, 63);
--
-- 야구 사진
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test12', '012.jpg', NULL, 89);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test13', '013.jpg', NULL, 94);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test14', '014.jpg', NULL, 95);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test15', '015.jpg', NULL, 96);
--
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test16', '016.jpg', NULL, 97);
--INSERT INTO BOARD_IMG VALUES(SEQ_BOARD_IMG_NO.NEXTVAL, '/images/board/', 'test17', '017.jpg', NULL, 98);





CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	NVARCHAR2(500)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글 번호(PK)';
COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';
COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';
COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '삭제여부(Y, N)';
COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS '게시글 번호(FK)';
COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '부모 댓글 번호(PK)';

CREATE SEQUENCE SEQ_COMMENT_NO NOCACHE;

SELECT * FROM "COMMENT";
DELETE FROM "COMMENT";
SELECT * FROM "BOARD" WHERE BOARD_NO = 19;


CREATE TABLE "SPORTS_TYPE" (
	"SPORTS_NO"	NUMBER		NOT NULL,
	"SPORTS_CODE"	NVARCHAR2(20)		NOT NULL,
	"SPORTS_KR_NAME"	NVARCHAR2(20)		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NULL,
	"IMG_NAME"	NVARCHAR2(50)		NULL
);

COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_NO" IS '종목 번호';
COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_CODE" IS '종목 영어 이름';
COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_KR_NAME" IS '종목 한글 이름';
COMMENT ON COLUMN "SPORTS_TYPE"."IMG_PATH" IS '종목 이미지 저장된 파일 경로';
COMMENT ON COLUMN "SPORTS_TYPE"."IMG_NAME" IS '종목 이미지 파일명';

CREATE SEQUENCE "SEQ_SPORTS_TYPE_NO";
 
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'football', '축구', '/category/', '축구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'basketball', '농구', '/category/', '농구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'handball', '핸드볼', '/category/', '핸드볼.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'volleyball', '배구', '/category/', '배구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'baseball', '야구', '/category/', '야구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'rugby', '럭비', '/category/', '럭비.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'billiards', '당구', '/category/', '당구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'golf', '골프', '/category/', '골프.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'footvolleyball', '족구', '/category/', '족구.png');
INSERT INTO "SPORTS_TYPE" VALUES(SEQ_SPORTS_TYPE_NO.NEXTVAL, 'climbing', '클라이밍', '/category/', '클라이밍.png');


--SELECT * FROM SPORTS_TYPE;
--SELECT * FROM BOARD;




CREATE TABLE "ATTEND_MEMBER" (
	"ATTEND_NO" NUMBER 		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ATTEND_MEMBER"."ATTEND_NO" IS '참석 번호(PK)';
COMMENT ON COLUMN "ATTEND_MEMBER"."MEMBER_NO" IS '회원 번호(PK)';
COMMENT ON COLUMN "ATTEND_MEMBER"."BOARD_NO" IS '게시글 번호(PK)';

CREATE SEQUENCE SEQ_ATTEND_NO NOCACHE;

--SELECT MEMBER_NO, PROFILE_IMG, MEMBER_NICKNAME
--FROM MEMBER
--JOIN ATTEND_MEMBER USING(MEMBER_NO)
--WHERE BOARD_NO = 1;





CREATE TABLE "TB_AUTH_KEY" (
	"KEY_NO"	NUMBER		NOT NULL,
	"EMAIL"	NVARCHAR2(50)		NOT NULL,
	"AUTH_KEY"	CHAR(6)		NOT NULL,
	"CREATE_TIME"	DATE	DEFAULT SYSDATE	NOT NULL
);

COMMENT ON COLUMN "TB_AUTH_KEY"."KEY_NO" IS '인증키';
COMMENT ON COLUMN "TB_AUTH_KEY"."EMAIL" IS '인증';
COMMENT ON COLUMN "TB_AUTH_KEY"."AUTH_KEY" IS '인증';
COMMENT ON COLUMN "TB_AUTH_KEY"."CREATE_TIME" IS '인증';

CREATE SEQUENCE SEQ_KEY_NO;



-- 내가 속한 모임 (만든 모임 제외)
SELECT BOARD_NO, BOARD_TITLE, MEMBER_NICKNAME, 
	CASE 
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24 / 60 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60 * 60) || '초 전' 
		 
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60) || '분 전' 
	 	 
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24) || '시간 전' 
		 
		ELSE TO_CHAR(BOARD_WRITE_DATE, 'YYYY-MM-DD')
	END BOARD_WRITE_DATE 
FROM "BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE BOARD_NO IN(SELECT BOARD_NO FROM ATTEND_MEMBER WHERE MEMBER_NO = 39)
AND MEMBER_NO != 39
AND BOARD_DEL_FL = 'N'
ORDER BY MEETING_DATE;

SELECT COUNT(*)
FROM "BOARD"
JOIN "MEMBER" USING(MEMBER_NO)
WHERE BOARD_NO IN(SELECT BOARD_NO FROM ATTEND_MEMBER WHERE MEMBER_NO = 39)
AND MEMBER_NO != 39
AND BOARD_DEL_FL = 'N';

-- 내가 찜한 모임
SELECT B.BOARD_NO, B.BOARD_TITLE, M.MEMBER_NICKNAME,
	CASE 
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24 / 60 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60 * 60) || '초 전'
		
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 / 24 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24 * 60) || '분 전'
	
		WHEN SYSDATE - BOARD_WRITE_DATE < 1 
		THEN FLOOR((SYSDATE - BOARD_WRITE_DATE) * 24) || '시간 전'
		
		ELSE TO_CHAR(BOARD_WRITE_DATE, 'YYYY-MM-DD')
	END BOARD_WRITE_DATE
FROM "BOARD" B
JOIN "MEMBER" M ON (B.MEMBER_NO = M.MEMBER_NO)
JOIN "BOARD_LIKE" BL ON (B.BOARD_NO = BL.BOARD_NO)
WHERE B.MEMBER_NO != 40
AND B.BOARD_NO IN(SELECT BOARD_NO FROM BOARD_LIKE WHERE MEMBER_NO = 40)
AND B.BOARD_DEL_FL = 'N'
ORDER BY BL.LIKE_NO;

SELECT COUNT(*)
FROM "BOARD" B
JOIN "MEMBER" M ON (B.MEMBER_NO = M.MEMBER_NO)
JOIN "BOARD_LIKE" BL ON (B.BOARD_NO = BL.BOARD_NO)
WHERE B.MEMBER_NO != 40
AND B.BOARD_NO IN(SELECT BOARD_NO FROM BOARD_LIKE WHERE MEMBER_NO = 40)
AND B.BOARD_DEL_FL = 'N'
ORDER BY BL.LIKE_NO;

SELECT * FROM BOARD;


SELECT BOARD_NO, MEMBER_NO
FROM ATTEND_MEMBER;

CREATE TABLE "BANNER_IMG" (
	"IMG_NO"	NUMBER		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NOT NULL,
	"IMG_ORIGINAL_NAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_RENAME"	NVARCHAR2(50)		NOT NULL,
	"IMG_ORDER"	NUMBER		NULL
);

COMMENT ON COLUMN "BANNER_IMG"."IMG_NO" IS '이미지 번호(PK)';
COMMENT ON COLUMN "BANNER_IMG"."IMG_PATH" IS '이미지 경로';
COMMENT ON COLUMN "BANNER_IMG"."IMG_ORIGINAL_NAME" IS '이미지 원본명';
COMMENT ON COLUMN "BANNER_IMG"."IMG_RENAME" IS '이미지 변경명';
COMMENT ON COLUMN "BANNER_IMG"."IMG_ORDER" IS '이미지 순서';





ALTER TABLE "MEMBER" ADD CONSTRAINT "PK_MEMBER" PRIMARY KEY (
	"MEMBER_NO"
);

ALTER TABLE "UPLOAD_FILE" ADD CONSTRAINT "PK_UPLOAD_FILE" PRIMARY KEY (
	"FILE_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"BOARD_NO"
);

ALTER TABLE "BOARD_TYPE" ADD CONSTRAINT "PK_BOARD_TYPE" PRIMARY KEY (
	"BOARD_CODE"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "PK_BOARD_LIKE" PRIMARY KEY (
	"LIKE_NO",
	"MEMBER_NO",
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "PK_BOARD_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "PK_COMMENT" PRIMARY KEY (
	"COMMENT_NO"
);

ALTER TABLE "SPORTS_TYPE" ADD CONSTRAINT "PK_SPORTS_TYPE" PRIMARY KEY (
	"SPORTS_CODE"
);

ALTER TABLE "ATTEND_MEMBER" ADD CONSTRAINT "PK_ATTEND_MEMBER" PRIMARY KEY (
	"ATTEND_NO",
	"MEMBER_NO",
	"BOARD_NO"
);

ALTER TABLE "TB_AUTH_KEY" ADD CONSTRAINT "PK_TB_AUTH_KEY" PRIMARY KEY (
	"KEY_NO"
);

ALTER TABLE "BANNER_IMG" ADD CONSTRAINT "PK_BANNER_IMG" PRIMARY KEY (
	"IMG_NO"
);

ALTER TABLE "UPLOAD_FILE" ADD CONSTRAINT "FK_MEMBER_TO_UPLOAD_FILE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_BOARD_TYPE_TO_BOARD_1" FOREIGN KEY (
	"BOARD_CODE"
)
REFERENCES "BOARD_TYPE" (
	"BOARD_CODE"
);

ALTER TABLE "BOARD" ADD CONSTRAINT "FK_SPORTS_TYPE_TO_BOARD_1" FOREIGN KEY (
	"SPORTS_CODE"
)
REFERENCES "SPORTS_TYPE" (
	"SPORTS_CODE"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_MEMBER_TO_BOARD_LIKE_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "BOARD_LIKE" ADD CONSTRAINT "FK_BOARD_TO_BOARD_LIKE_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "BOARD_IMG" ADD CONSTRAINT "FK_BOARD_TO_BOARD_IMG_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_BOARD_TO_COMMENT_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_MEMBER_TO_COMMENT_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "COMMENT" ADD CONSTRAINT "FK_COMMENT_TO_COMMENT_1" FOREIGN KEY (
	"PARENT_COMMENT_NO"
)
REFERENCES "COMMENT" (
	"COMMENT_NO"
);

ALTER TABLE "ATTEND_MEMBER" ADD CONSTRAINT "FK_MEMBER_TO_ATTEND_MEMBER_1" FOREIGN KEY (
	"MEMBER_NO"
)
REFERENCES "MEMBER" (
	"MEMBER_NO"
);

ALTER TABLE "ATTEND_MEMBER" ADD CONSTRAINT "FK_BOARD_TO_ATTEND_MEMBER_1" FOREIGN KEY (
	"BOARD_NO"
)
REFERENCES "BOARD" (
	"BOARD_NO"
);

----------------------------------------------------------------------------------------

ALTER TABLE "MEMBER" ADD CONSTRAINT "GENDER"
CHECK("GENDER" IN ('M', 'F') );

ALTER TABLE "MEMBER" ADD CONSTRAINT "MEMBER_DEL_CHECK"
CHECK("MEMBER_DEL_FL" IN ('Y', 'N') );

ALTER TABLE "BOARD" ADD CONSTRAINT "BOARD_DEL_CHECK"
CHECK("BOARD_DEL_FL" IN ('Y', 'N') );

ALTER TABLE "COMMENT" ADD CONSTRAINT "COMMENT_DEL_CHECK"
CHECK("COMMENT_DEL_FL" IN ('Y', 'N') );

COMMIT;

----------------------------------------------------------------------------------------



DROP TABLE BOARD_IMG;
DROP TABLE BOARD_LIKE;
DROP TABLE UPLOAD_FILE;
DROP TABLE ATTEND_MEMBER;
DROP TABLE BANNER_IMG;
DROP TABLE TB_AUTH_KEY;
DROP TABLE "COMMENT";
DROP TABLE BOARD;
DROP TABLE BOARD_TYPE;
DROP TABLE SPORTS_TYPE;
DROP TABLE "MEMBER";
DROP SEQUENCE SEQ_SPORTS_TYPE_NO;
DROP SEQUENCE SEQ_KEY_NO;
DROP SEQUENCE SEQ_BOARD_NO;
DROP SEQUENCE SEQ_IMG_NO;
DROP SEQUENCE SEQ_ATTEND_NO;
DROP SEQUENCE SEQ_LIKE_NO;
DROP SEQUENCE SEQ_COMMENT_NO;
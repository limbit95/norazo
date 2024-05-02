CREATE TABLE "MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"MEMBER_EMAIL"	VARCHAR2(50)		NOT NULL,
	"MEMBER_PW"	VARCHAR2(100)		NOT NULL,
	"MEMBER_NICKNAME"	VARCHAR2(10)		NOT NULL,
	"MEMBER_TEL"	CHAR(11)		NOT NULL,
	"MEMBER_ADDRESS"	VARCHAR2(300)		NULL,
	"GENDER"	CHAR(1)		NOT NULL,
	"BIRTH_DATE"	DATE		NOT NULL,
	"PROFILE_IMG"	VARCHAR(300)		NULL,
	"ENROLL_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"MEMBER_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"MEMBER_INTRODUCE"	NVARCHAR2(300)		NULL
);
SELECT * FROM "MEMBER";
-- 샘플 
INSERT INTO "MEMBER"
VALUES(1,'test@naver.com','pass01!','테스트','01012345678',NULL,'M','1991-08-29',NULL,DEFAULT,DEFAULT,NULL);

UPDATE "MEMBER" SET 
MEMBER_PW = '$2a$10$ur5HlwMXraQbyE1HqIvbRuwQ0vLuioAGMmr6M7QQtOrBkOgApTX6S'
WHERE MEMBER_NO = '1';

SELECT MEMBER_NO, MEMBER_EMAIL, MEMBER_PW, MEMBER_NICKNAME, MEMBER_TEL,MEMBER_ADDRESS,GENDER,
TO_CHAR(BIRTH_DATE,'YYYY-MM-DD')BIRTH_DATE,PROFILE_IMG, 
TO_CHAR(ENROLL_DATE,'YYYY"년" MM"월" DD"일" HH24"시" MI"분" SS"초" ') 
FROM "MEMBER"; 
COMMIT;
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
	"MEETING_LOCATION"	NVARCHAR2(100)		NULL
);

COMMENT ON COLUMN "BOARD"."BOARD_NO" IS '게시글 번호(PK)';
COMMENT ON COLUMN "BOARD"."BOARD_TITLE" IS '게시글 제목';
COMMENT ON COLUMN "BOARD"."BOARD_CONTENT" IS '게시글 내용';
COMMENT ON COLUMN "BOARD"."BOARD_WRITE_DATE" IS '게시글 작성일';
COMMENT ON COLUMN "BOARD"."BOARD_UPDATE_DATE" IS '게시글 마지막 수정일';
COMMENT ON COLUMN "BOARD"."BOARD_DEL_FL" IS '삭제 여부(Y, N)';
COMMENT ON COLUMN "BOARD"."MEMBER_NO" IS '작성한 회원번호(FK)';
COMMENT ON COLUMN "BOARD"."BOARD_CODE" IS '게시판 종류 코드번호(FK)';
COMMENT ON COLUMN "BOARD"."SPORTS_CODE" IS '종목 코드';
COMMENT ON COLUMN "BOARD"."MEMBER_COUNT_LIMIT" IS '참가인원 제한수 (모임글)';
COMMENT ON COLUMN "BOARD"."MEETING_LOCATION" IS '모임 장소(모임글)';

CREATE TABLE "BOARD_TYPE" (
	"BOARD_CODE"	NUMBER		NOT NULL,
	"BOARD_NAME"	NVARCHAR2(20)		NOT NULL
);

COMMENT ON COLUMN "BOARD_TYPE"."BOARD_CODE" IS '게시판 종류 코드번호(PK)';
COMMENT ON COLUMN "BOARD_TYPE"."BOARD_NAME" IS '게시판 이름';



INSERT INTO "BOARD_TYPE" VALUES(1, '모임 게시판');
INSERT INTO "BOARD_TYPE" VALUES(2, '자유 게시판');
INSERT INTO "BOARD_TYPE" VALUES(3, '문의 게시판');

SELECT * FROM "BOARD_TYPE";



CREATE TABLE "BOARD_LIKE" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "BOARD_LIKE"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "BOARD_LIKE"."BOARD_NO" IS '게시글 번호(PK)';

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

CREATE TABLE "COMMENT" (
	"COMMENT_NO"	NUMBER		NOT NULL,
	"COMMENT_CONTENT"	NVARCHAR2(500)		NOT NULL,
	"COMMENT_WRITE_DATE"	DATE	DEFAULT SYSDATE	NOT NULL,
	"COMMENT_DEL_FL"	CHAR(1)	DEFAULT 'N'	NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL,
	"MEMBER_NO"	NUMBER		NOT NULL,
	"PARENT_COMMENT_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "COMMENT"."COMMENT_NO" IS '댓글 번호(PK)';
COMMENT ON COLUMN "COMMENT"."COMMENT_CONTENT" IS '댓글 내용';
COMMENT ON COLUMN "COMMENT"."COMMENT_WRITE_DATE" IS '댓글 작성일';
COMMENT ON COLUMN "COMMENT"."COMMENT_DEL_FL" IS '삭제여부(Y, N)';
COMMENT ON COLUMN "COMMENT"."BOARD_NO" IS '게시글 번호(FK)';
COMMENT ON COLUMN "COMMENT"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "COMMENT"."PARENT_COMMENT_NO" IS '부모 댓글 번호(PK)';

CREATE TABLE "SPORTS_TYPE" (
	"SPORTS_NO"	NUMBER		NOT NULL,
	"SPORTS_EN_NAME"	NVARCHAR2(20)		NOT NULL,
	"SPORTS_NAME"	NVARCHAR2(20)		NOT NULL,
	"IMG_PATH"	VARCHAR2(200)		NULL,
	"IMG_NAME"	NVARCHAR2(50)		NULL
);

COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_NO" IS '종목 번호';
COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_EN_NAME" IS '종목 영어 이름';
COMMENT ON COLUMN "SPORTS_TYPE"."SPORTS_NAME" IS '종목 한글 이름';
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


CREATE TABLE "ATTEND_MEMBER" (
	"MEMBER_NO"	NUMBER		NOT NULL,
	"BOARD_NO"	NUMBER		NOT NULL
);

COMMENT ON COLUMN "ATTEND_MEMBER"."MEMBER_NO" IS '회원번호(PK)';
COMMENT ON COLUMN "ATTEND_MEMBER"."BOARD_NO" IS '게시글 번호(PK)';

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
	"SPORTS_EN_NAME"
);

ALTER TABLE "ATTEND_MEMBER" ADD CONSTRAINT "PK_ATTEND_MEMBER" PRIMARY KEY (
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
	"SPORTS_EN_NAME"
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
DROP TABLE BOARD_TYPE;
DROP TABLE UPLOAD_FILE;
DROP TABLE ATTEND_MEMBER;
DROP TABLE BANNER_IMG;
DROP TABLE TB_AUTH_KEY;
DROP TABLE BOARD;
DROP TABLE SPORTS_TYPE;
DROP TABLE BOARD_TYPE;
DROP TABLE "COMMENT";
DROP TABLE MEMBER;
DROP SEQUENCE SEQ_SPORTS_TYPE_NO;
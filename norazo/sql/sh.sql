
SELECT * FROM "MEMBER";

SELECT * FROM "BOARD" WHERE BOARD_NO = 98;

SELECT * FROM SPORTS_TYPE;

SELECT * FROM "BOARD_IMG";

SELECT * FROM "BOARD";

SELECT MEMBER_EMAIL, MEMBER_NICKNAME, MEMBER_INTRODUCE FROM "MEMBER" JOIN "BOARD" USING (MEMBER_NO) WHERE BOARD_NO = 98;

SELECT BOARD_NO, BOARD_TITLE, MEMBER_NICKNAME, MEMBER_COUNT_LIMIT, MEETING_LOCATION, 
		TO_CHAR(MEETING_DATE, 'MM"월" DD"일" "("DY")" HH24"시" MI"분"') MEETING_DATE,
		
			(SELECT IMG_PATH || IMG_RENAME 
			FROM "BOARD_IMG" C 
			WHERE C.BOARD_NO = B.BOARD_NO) THUMBNAIL, 
			
			(SELECT COUNT(*)
			FROM "ATTEND_MEMBER" A
			WHERE A.BOARD_NO = B.BOARD_NO) ATTEND_MEMBER_COUNT
			
		FROM "BOARD" B
		JOIN "MEMBER" USING(MEMBER_NO)
		WHERE BOARD_DEL_FL = 'N'
		AND SPORTS_CODE = 'climbing'
		AND BOARD_NO = 98;
		
	SELECT m.MEMBER_NO, m.PROFILE_IMG, m.MEMBER_NICKNAME
	 	FROM MEMBER m
	 	JOIN ATTEND_MEMBER a ON m.MEMBER_NO = a.MEMBER_NO
	 	JOIN BOARD b ON a.BOARD_NO = b.BOARD_NO
	 	WHERE b.BOARD_NO = 1190
	 	ORDER BY b.MEMBER_NO;
	 	
 SELECT * FROM ATTEND_MEMBER;
	 
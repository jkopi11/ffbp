DROP PROCEDURE IF EXISTS updateLineup;
DELIMITER //
CREATE PROCEDURE updateLineup (IN userID INT, IN lineupID INT, IN playerID INT, IN siteName varchar(10))
	BEGIN
		IF EXISTS (SELECT * FROM lineup_players WHERE USER_ID=userID AND LINEUP_ID = lineupID AND SITE_P_ID = playerID AND SITE_NAME = siteName) THEN
			INSERT INTO logs (log) VALUES ('UPDATE');
			UPDATE lineup_players SET ACTIVE=true WHERE USER_ID=userID AND LINEUP_ID = lineupID AND SITE_P_ID = playerID AND SITE_NAME = siteName;
		ELSE
			SET @usql = CONCAT('INSERT INTO lineup_players (SITE_NAME,SITE_P_ID,LINEUP_ID,USER_ID,ACTIVE) VALUES (',siteName,',',playerID,',',lineupID,',',userID,',true);');
			INSERT INTO logs (log) VALUES (@usql);
			INSERT INTO lineup_players (SITE_NAME,SITE_P_ID,LINEUP_ID,USER_ID,ACTIVE) VALUES (siteName, playerID, lineupID, userID, true);
		END IF;
 	END// 
DELIMITER ;
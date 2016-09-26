DROP PROCEDURE IF EXISTS updateLineup;
DELIMITER //
CREATE PROCEDURE updateLineup (IN lineupID INT, IN playerID INT, IN siteName varchar(10), IN week int)
	BEGIN
		IF EXISTS (SELECT * FROM lineup_players WHERE LINEUP_ID = lineupID AND SITE_P_ID = playerID AND SITE_NAME = siteName AND WEEK = week) THEN
			UPDATE lineup_players SET ACTIVE=true WHERE LINEUP_ID = lineupID AND SITE_P_ID = playerID AND SITE_NAME = siteName AND WEEK = week;
		ELSE
			INSERT INTO lineup_players (SITE_NAME,SITE_P_ID,LINEUP_ID,WEEK) VALUES (siteName, playerID, lineupID, week);
		END IF;
	END//
DELIMITER ;
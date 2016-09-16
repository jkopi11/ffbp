DROP PROCEDURE IF EXISTS updatePlayers;
DELIMITER //
CREATE PROCEDURE updatePlayers (IN pID INT, IN pName VARCHAR(255), IN siteId VARCHAR(10), IN siteScore VARCHAR(10), IN week INT, IN score FLOAT)
	BEGIN
		DECLARE queryID INT;
		INSERT INTO logs (log) VALUES (CONCAT(pID,' ', pName,' ',siteId,' ',siteScore,' ',week,' ',score));
		/* Updating a score that already has a projection */
		IF EXISTS (SELECT players.P_ID FROM players LEFT JOIN projections ON players.P_ID=projections.P_ID WHERE (players.Y_ID = pID OR players.ESPN_ID = pID OR players.CBS_ID = pID) AND players.PNAME = pName AND projections.WEEK = week) THEN
			INSERT INTO logs (log) VALUES ('SCENARIO 1');
			SET @usql = CONCAT('UPDATE players LEFT JOIN projections ON players.P_ID=projections.P_ID SET projections.',siteScore,'=',score,' WHERE players.',siteId,'=',pID,' AND projections.WEEK = ',week,';');
			INSERT INTO logs (log) VALUES (@nsql);
			PREPARE stmt FROM @usql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		/* if player is stored and week has been entered, but site id has not been stored */
		ELSEIF EXISTS (SELECT players.P_ID FROM players LEFT JOIN projections ON players.P_ID = projections.P_ID WHERE players.PNAME = pName AND projections.WEEK = week) THEN
			INSERT INTO logs (log) VALUES ('SCENARIO 2');
			SELECT players.P_ID INTO queryID FROM players LEFT JOIN projections ON players.P_ID = projections.P_ID WHERE players.PNAME = pName AND projections.WEEK = week;
			SET @usql = CONCAT('UPDATE players LEFT JOIN projections ON players.P_ID=projections.P_ID SET projections.',siteScore,'=',score,', players.', siteID,'=',pID,' WHERE players.P_ID=', queryID,' AND projections.WEEK=',week,';');
			INSERT INTO logs (log) VALUES (@usql);
			PREPARE stmt FROM @usql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		/* if site id is stored but week has not been entered meaning there is no record in that week in the projections table */
		ELSEIF EXISTS (SELECT P_ID FROM players WHERE (Y_ID = pID OR ESPN_ID = pID OR CBS_ID = pID) AND PNAME = pName) THEN
			INSERT INTO logs (log) VALUES ('SCENARIO 3');
			/* might have to add something to check for null values for the site ids */
			SELECT P_ID INTO queryID FROM players WHERE (Y_ID = pID OR ESPN_ID = pID OR CBS_ID = pID) AND PNAME = pName;
			SET @nsql = CONCAT('INSERT INTO projections (P_ID, WEEK, ', siteScore, ') VALUES (', queryID, ',',week,',', score, ');');
			INSERT INTO logs (log) VALUES (@nsql);
			PREPARE stmt FROM @nsql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		/* if player is stored, but week and site id has not been stored */
		ELSEIF EXISTS(SELECT * FROM players WHERE players.PNAME=pName) THEN
			SET @c = (SELECT COUNT(*) FROM players WHERE PNAME = pName);
			INSERT INTO logs (log) VALUES (CONCAT('SCENARIO 4',@c));
			SELECT P_ID INTO queryID FROM players WHERE players.PNAME = pName;
			SET @usql = CONCAT('UPDATE players SET ',siteId,'=',pID,' WHERE P_ID = ',queryID,';');
			INSERT INTO logs (log) VALUES (@usql);
			PREPARE stmt FROM @usql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
			SET @usql = CONCAT('INSERT INTO projections (P_ID, WEEK, ', siteScore, ') VALUES (', queryID,',', week,',', score, ')');
			INSERT INTO logs (log) VALUES (@usql);
			PREPARE stmt FROM @usql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		/* if player has not been entered */
		ELSE
			INSERT INTO logs (log) VALUES ('SCENARIO 5');
			SET @nsql = CONCAT('INSERT INTO players (PNAME,', siteId,') VALUES (\'',pName,'\', ',pID,')');
			INSERT INTO logs (log) VALUES (@nsql);
			PREPARE stmt FROM @nsql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
			SET @qID = LAST_INSERT_ID();
			SET @nsql = CONCAT('INSERT INTO projections (P_ID,WEEK,',siteScore,') VALUES (',@qID,',',week,',',score,')');
			PREPARE stmt FROM @nsql;
			EXECUTE stmt;
			DEALLOCATE PREPARE stmt;
		END IF;
	END//
DELIMITER ;
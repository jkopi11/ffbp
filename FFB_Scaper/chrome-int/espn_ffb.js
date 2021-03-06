var players = [];

function parseESPN () {
	var headerRow = document.querySelectorAll('.playerTableBgRowSubhead td'),
    playerRows = document.querySelectorAll(".pncPlayerRow"),
    columns = [],
    markup = '',
    playerObj, player, row, data,slot,playerName,projection,projNum;
	
	for (var i = 0; i < headerRow.length; i++) {
		if (headerRow[i].querySelector('a')) {
			columns.push(headerRow[i].querySelector('a').innerHTML);
		} else {
			columns.push(headerRow[i].innerHTML);
		}
	    
	}
	
	for (var ii = 0; ii < playerRows.length; ii++) {
		player = playerRows[ii];
		playerObj = {};
		row = player.querySelectorAll('td');
		playerName = row[columns.indexOf('PLAYER')]; 
		if (typeof playerName !== "undefined") {
			playerName = playerName.querySelector('a');
			if (playerName) {
				playerObj['ESPN_ID'] = parseInt(playerName.getAttribute('playerid'));
				playerObj['NAME'] = playerName.innerHTML;
			}
		} else {
			continue;
		}
		
		projection = row[columns.indexOf('PROJ')]; 
		if (typeof projection !== "undefined") {
			projNum = parseInt(projection.innerHTML);
			if (projNum == null || typeof projNum === 'undefined' || isNaN(projNum)) {
				projNum = 0;
			}
			playerObj['ESPN_PROJ'] = projNum; 
		} else {
			continue;
		}
		players.push(JSON.stringify(playerObj));
	}
}

parseESPN();

players
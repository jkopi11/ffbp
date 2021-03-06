var players = [];

function parseYahoo () {
	var headerRow = document.querySelectorAll('table.ysf-rosterswapper thead tr.Last th'),
    playerRows = document.querySelectorAll("table.ysf-rosterswapper tbody tr"),
    columns = [],
    markup = '', playerId,
    playerObj, player, row, data,slot,playerName,projection;

	for (var i = 0; i < headerRow.length; i++) {
		columns.push(headerRow[i].innerText.trim());
	}
	for (var ii = 0; ii < playerRows.length; ii++) {
		player = playerRows[ii];
		playerObj = {};
		row = player.querySelectorAll('td');
		playerName = row[columns.indexOf('Offense')]; 
		if (typeof playerName !== "undefined") {
			playerName = playerName.querySelector('a.name');
			if (playerName) {
				url = playerName.href;
				playerId = parseInt(url.substring(url.lastIndexOf('/')+1));
				if (playerId) {
					players.push(playerId);	
				}
			}
		} else {
			continue;
		}
	}
}

parseYahoo();

// Chrome plugins pass the last variable reference in a script
players
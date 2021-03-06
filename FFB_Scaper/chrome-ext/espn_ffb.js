var players = [];

function parseESPN () {
	var headerRow = document.querySelectorAll('.playerTableBgRowSubhead td'),
    playerRows = document.querySelectorAll(".pncPlayerRow"),
    columns = [],
    markup = '',
    playerObj, player, row, data,slot,playerName,projection;

	for (var i = 0; i < headerRow.length; i++) {
	    columns.push(headerRow[i].innerHTML);
	}
	for (var ii = 0; ii < playerRows.length; ii++) {
		player = playerRows[ii];
		playerObj = {};
		row = player.querySelectorAll('td');
		playerName = row[columns.indexOf('PLAYER, TEAM POS')];
		if (typeof playerName != "undefined") {
			playerName = playerName.querySelector('a');
			if (playerName) {
				players.push(playerName.getAttribute('playerid'));
			}
		}
	}
}

parseESPN();

// Chrome plugins pass the last variable reference in a script
players
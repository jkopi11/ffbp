var players = [];

function parseESPN () {
	var playerRows = document.querySelectorAll(".pncPlayerRow"),
    playerObj, player, row,playerName;
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

players
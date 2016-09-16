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
		slot = row[columns.indexOf('SLOT')];
		if (typeof slot != 'undefined') {
			playerObj['SLOT'] = slot.innerHTML;
		}
		playerName = row[columns.indexOf('PLAYER, TEAM POS')]; 
		if (typeof playerName != "undefined") {
			playerName = playerName.querySelector('a');
			if (playerName) {
				playerObj['ESPNID'] = playerName.getAttribute('playerid');
				
				playerObj['NAME'] = playerName.innerHTML;
//				markup +=  playerName.getAttribute('playerid') + ' ' + playerName.innerHTML + ' ';
			}
		}
		
		projection = row[columns.indexOf('PROJ')]; 
		if (typeof projection != "undefined") {
			playerObj['PROJ'] = projection.innerHTML;
//			markup += projection.innerHTML + '<br>';
		}
		players.push(playerObj);
	}
}

parseESPN();

players
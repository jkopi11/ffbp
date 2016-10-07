var players = [];

function parseYahoo () {
	var headerRow = document.querySelectorAll('div.players thead tr'),
    playerRows = document.querySelectorAll('div.players tbody tr'),
    siteName = scriptOptions.siteName,
    siteScoreFldName = scriptOptions.siteScoreFldName,
    columns = [],
    markup = '',
    playerObj, player, row, data,slot,playerName,projection,projNum,url;
	
	headerRow = headerRow[1].querySelectorAll('th');
	
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
				playerObj[siteName] = parseInt(url.substring(url.lastIndexOf('/')+1));
				playerObj['NAME'] = playerName.innerHTML.replace("'","");
			}
		} else {
			continue;
		}
		
		projection = row[columns.indexOf('Fan Pts')]; 
		if (typeof projection !== "undefined") {
			projNum = parseInt(projection.innerText.trim());
			if (projNum == null || typeof projNum === 'undefined' || isNaN(projNum)) {
				projNum = 0;
			}
			playerObj[siteScoreFldName] = projNum; 
		} else {
			continue;
		}
		players.push(JSON.stringify(playerObj));
	}
}

parseYahoo();

players
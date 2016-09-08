var tab, 
	tab_title = '',
	getRosterBtn = document.getElementById("getRosterBtn"),
	displayTeam = function (players){
		console.log(players);
		var markup = '<table><tr><th>NAME</th><th>PROJ</th></tr>',
			players = players[0],
			player;
		for (var i = 0; i < players.length; i++) {
			player = players[i];
			markup += '<tr><td>' + player.NAME + '</td><td>' + player.PROJ + '</td></tr>';
		}
		markup += '</table>';
		document.querySelector("#id1").innerHTML = "<p>tab title: " + tab_title + "</p>" + markup;
	},
	getRosterFnc = function(){
		chrome.tabs.executeScript(tab.id, {
			  file : 'espn_ffb.js'
		  }, displayTeam);
	};

chrome.tabs.query({active: true}, function(tabs) {
	tab = tabs[0];
	tab_title = tab.title;
	var espnHttpRgx = /^(http|https):\/\/games\.espn\.com\/ffl\/clubhouse/,
		url = tab.url;
	
	if (espnHttpRgx.test(url)) {
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else {
		getRosterBtn.className += " disabled";
	}
});

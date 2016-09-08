var tab, currentPlayers, siteName, fileName = 'espn_ffb.js', 
	tab_title = '',
	enabledBtnCls = "btn btn-lg btn-primary btn-block",
	disabledBtnCls = "btn btn-lg btn-primary btn-block disabled",
	getRosterBtn = document.getElementById("getProjectionBtn"),
	loadRosterBtn = document.getElementById("loadProjectionBtn"),
	displayTeam = function (players){
//		var markup = '<table><tr><th>NAME</th><th>PROJ</th></tr>',
//			players = players[0],
//			player;
//		currentPlayers = players;
//		for (var i = 0; i < players.length; i++) {
//			player = players[i];
//			markup += '<tr><td>' + player.NAME + '</td><td>' + player.PROJ + '</td></tr>';
//		}
//		markup += '</table>';
		var currentPlayers = players[0];
		loadRosterBtn.addEventListener('click',loadRosterFnc);
		loadRosterBtn.className = enabledBtnCls;
	},
	getRosterFnc = function(){
		if (siteName == 'Y') {
			fileName = 'yahoo_ffb.js'
		}
		chrome.tabs.executeScript(tab.id, {
			  file : fileName
		  }, displayTeam);
	},
	loadRosterFnc = function(){
		$.post( "http://localhost:8080/FFB_Analyzer/api/loadprojections", {
			week : $("#weekSelection option:selected").text(),
			projections : JSON.stringify(currentPlayers)
		});
	};

chrome.tabs.query({active: true}, function(tabs) {
	tab = tabs[0];
	tab_title = tab.title;
	var espnHttpRgx = /^(http|https):\/\/games\.espn\.com\/ffl\/freeagency/,
	    yahooHttpRgx = /^(http|https):\/\/football\.fantasysports\.yahoo\.com\/f1\/([0-9]{6})\/players/,
		url = tab.url;
	
	siteName = espnHttpRgx.test(url) ? 'ESPN' : null;
	
	if (siteName == null) {
		siteName = yahooHttpRgx.test(url) ? 'Y' : null;
	}
	
	if (espnHttpRgx.test(url)) {
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else {
		getRosterBtn.className = disabledBtnCls;		
	}
});


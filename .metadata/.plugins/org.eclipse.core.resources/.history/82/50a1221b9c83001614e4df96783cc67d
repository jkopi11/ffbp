var tab, currentPlayers, siteName, siteScoreFldName, fileName = 'espn_ffb.js', week,
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
		currentPlayers = players[0];
		loadRosterBtn.addEventListener('click',loadRosterFnc);
		loadRosterBtn.className = enabledBtnCls;
	},
	getRosterFnc = function(){
		if (siteName == 'Y_ID') {
			fileName = 'yahoo_ffb.js'
		}
		chrome.tabs.executeScript(tab.id, {
			  file : fileName
		  }, displayTeam);
	},
	loadRosterFnc = function(){
		console.log('load roster');
		$.post( "http://localhost:8080/FFB_Analyzer/api/loadprojections", {
			site : siteName,
			week : $("#weekSelection option:selected").text(),
			scoreType : siteScoreFldName,
			projections : JSON.stringify(currentPlayers)
		});
	};

chrome.tabs.query({active: true}, function(tabs) {
	tab = tabs[0];
	tab_title = tab.title;
	var espnHttpRgx = /^(http|https):\/\/games\.espn\.com\/ffl\/freeagency/,
	    yahooHttpRgx = /^(http|https):\/\/football\.fantasysports\.yahoo\.com\/f1\/([0-9]{6,7})\/players/,
		url = tab.url;
	
	siteName = espnHttpRgx.test(url) ? 'ESPN_ID' : null;
	
	if (siteName == 'ESPN_ID') {
		siteScoreFldName = 'ESPN_PROJ';
	}
	
	if (siteName == null) {
		siteName = yahooHttpRgx.test(url) ? 'Y_ID' : null;
		siteScoreFldName = 'Y_PROJ';
	}
	
	if (siteName !== 'undefined') {
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else {
		getRosterBtn.className = disabledBtnCls;		
	}
});


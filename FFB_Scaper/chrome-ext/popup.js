var tab, siteName, currentPlayers,
	tab_title = '',
	fileName = 'espn_ffb.js',
	getRosterBtn = document.getElementById("getRosterBtn"),
	saveTeam = function (p){
		console.log(p);
		currentPlayers = p[0];
		console.log(currentPlayers);
		$.post( "http://localhost:8080/FFB_Analyzer/api/savelineup", {
			site : siteName,
			userId : 0,
			players : JSON.stringify(currentPlayers)
		});
	},
	getRosterFnc = function(){
		if (siteName == 'Y_ID') {
			fileName = 'yahoo_ffb.js'
		}
		chrome.tabs.executeScript(tab.id, {
			  file : fileName
		  }, saveTeam);
	};
	

chrome.tabs.query({active: true}, function(tabs) {
	tab = tabs[0];
	tab_title = tab.title;
	var espnHttpRgx = /^(http|https):\/\/games\.espn\.com\/ffl\/clubhouse/,
		yahooHttpRgx = /^(http|https):\/\/football\.fantasysports\.yahoo\.com\/f1\/([0-9]{6,7})\/[0-9]{1,2}\/team/,
		url = tab.url;
	
	if (espnHttpRgx.test(url)) {
		siteName = 'ESPN_ID';
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else if (yahooHttpRgx.test(url)){
		siteName = 'Y_ID';
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else {
		getRosterBtn.className += " disabled";
	}
});


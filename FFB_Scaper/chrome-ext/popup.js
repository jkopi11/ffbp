var tab, siteName,
	tab_title = '',
	getRosterBtn = document.getElementById("getRosterBtn"),
	saveTeam = function (players){
		currentPlayers = players[0];
		console.log(currentPlayers);
		$.post( "http://localhost:8080/FFB_Analyzer/api/savelineup", {
			site : siteName,
			userId : 0,
			players : JSON.stringify(currentPlayers)
		});
	},
	getRosterFnc = function(){
		chrome.tabs.executeScript(tab.id, {
			  file : 'espn_ffb.js'
		  }, saveTeam);
	};
	

chrome.tabs.query({active: true}, function(tabs) {
	tab = tabs[0];
	tab_title = tab.title;
	var espnHttpRgx = /^(http|https):\/\/games\.espn\.com\/ffl\/clubhouse/,
		url = tab.url;
	
	if (espnHttpRgx.test(url)) {
		siteName = 'ESPN';
		getRosterBtn.addEventListener('click',getRosterFnc);
	} else {
		getRosterBtn.className += " disabled";
	}
});


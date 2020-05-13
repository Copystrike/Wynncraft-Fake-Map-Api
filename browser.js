// Stop all current Intervals, this will stop the browser from requesting data from the wynncraft api.
for (var i = 1; i < 99999; i++) window.clearInterval(i);

// this is the orginal method used to request the account location, I've cleaned it up a little.
overviewer.util.ready(function () {
	startInterval(function () {
		if (!document.hidden) {
			$.ajax({
				url: 'http://localhost:44889/map/getMyLocation', cache: true, success: function (result) {
					if (result.error) {
						$('.player-track').hide();
						return;
					}

					$('.player-track-server-name').each(function () {
						$(this).html(result.server);
					});

					$('.player-track').show();
					addPlayerLabel(result);

					if (!runPlayer)
						runPlayer = true;

					for (var i in result.party) {
						var partyMember = result.party[i];
						addPlayerLabel(partyMember)
					}
				}
			});
		}
	}, 500);
});

//Cache player
var players = []
var ovconf = overviewer.current_layer[overviewer.current_world].tileSetConfig;

//Rewriten to add cache support, Poor surgeplay... Why Wyncraft??
function addPlayerLabel(player) {
	var latLng = overviewer.util.fromWorldToLatLng(player.x, player.y, player.z, ovconf);

	//Caching the profile picture
	if (players[player.uuid] === undefined) {
		playerFaceIcon = L.icon({
			iconUrl: 'https://mc-heads.net/avatar/' + player.uuid + "/32",
			iconRetinaUrl: 'https://mc-heads.net/avatar/' + player.uuid + "/32",
			iconSize: [32, 32]
		});

		players[player.uuid] = { playerFaceIcon, map: {} };

	} else {
		playerFaceIcon = players[player.uuid].playerFaceIcon;
	}

	//Updating the marker the right way :)
	if (players[player.uuid].map.marker === undefined) {
		//Player Face Marker
		var playerFaceData = L.marker(latLng, {
			icon: playerFaceIcon,
			title: players[player.uuid].name,
			zIndexOffset: 99999
		});
		players[player.uuid].map.marker = playerFaceData.addTo(overviewer.map);

	} else {
		//"health" + player.health + "/" + player.maxHealth
		var popup = L.popup({
			offset: [0, -10]
		}).setContent("Player: " + player.name + "<br>" +
			"Health: " + player.health + "/" + player.maxHealth + "<br>" +
			"X: " + player.x + " Y: " + player.y + " Z: " + player.z);
		players[player.uuid].map.marker.setLatLng(latLng);
		players[player.uuid].map.marker.bindPopup(popup);
	}

	if (players[player.uuid].map.nameLabel === undefined) {
		var nameLabel = new L.DivIcon({
			// NameLabel
			className: 'nameLabel',
			// $('.nl').css('font-family', 'Titillium Web').css('top', '20px').css('right', '22px').css('position', 'relative');
			html: '<div class="nl" style="color:white; font-family: Titillium Web; display: flex; justify-content: center; margin: 19px 0"><span style="font-weight:bold; font-size:14px; text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black, 3px 3px 3px #000;">' + player.name + '</span></div>'
		});
		var nameLabel = L.marker(latLng, {icon: nameLabel, zIndexOffset: 99996});
		players[player.uuid].map.nameLabel = nameLabel.addTo(overviewer.map);

	} else players[player.uuid].map.nameLabel.setLatLng(latLng);

	// HealtLabel
	if (players[player.uuid].map.healtLabel === undefined) {
		var healthLabel = new L.DivIcon({
			className: 'healthLabel',
			// $('.hb').css('top', '38px').css('right', '42px').css('position', 'relative');
			html: '<div class="hb" style="top: 38px; right: 42px; position: relative; background-color:#3C3C3C; width:100px; height:10px; border-radius:3px;"><div style="border-radius:3px;background-color:#27C500;float:left;height:10px;width:' + Math.floor((player.health / player.maxHealth) * 100) + '%;"></div></div>'
		});
		var healthLabel = L.marker(latLng, {icon: healthLabel, zIndexOffset: 99996});
		players[player.uuid].map.healtLabel = healthLabel.addTo(overviewer.map);

	} else {
		const healtLabel = players[player.uuid].map.healtLabel;
		healtLabel.getElement().firstElementChild.firstElementChild.style.width = Math.floor((player.health / player.maxHealth) * 100) + "%";
		healtLabel.setLatLng(latLng)
	}
}
//So we have coords at the bottom.
document.querySelector("#mcmap").style.position = "inherit";
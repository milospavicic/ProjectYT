$('document').ready(function(e){
    showHomepage();
    
    $('#followButton').click(function() {
        var tempName = $(this).text();
        if(tempName == "Follow"){
            $(this).text("Unfollow");
            $("#followButton").attr('class', 'btn btn-default');
        }else{
            $(this).text("Follow");
            $("#followButton").attr('class', 'btn btn-danger');
        }
    });
    $('.changeActive').click(function(){
        $('.active').removeClass('active');
        $(this).addClass('active');
    });
    $('#userHomepage').click(function(){
        showHomepage();
    });
    $('#userVideos').click(function(){
        showRecentVideos();
    });
    $('#userPlaylists').click(function(){
        showPlaylists();
    });
    $('#userInfo').click(function(){
        showInfo();
    });
    
});
function playPauseVideo(){
    var video = document.getElementById('welcomeVideo');
    if (video.paused){ 
    video.play();
        $('#btnPlayPause').removeClass('btn btn-default');
        $('#btnPlayPause').addClass('btn btn-danger');
        $('#btnPlayPause span').removeClass('glyphicon glyphicon-play');
        $('#btnPlayPause span').addClass('glyphicon glyphicon-pause');
    }
    else{
        video.pause()
        $('#btnPlayPause').removeClass('btn btn-danger');
        $('#btnPlayPause').addClass('btn btn-default');
        $('#btnPlayPause span').removeClass('glyphicon glyphicon-pause');
        $('#btnPlayPause span').addClass('glyphicon glyphicon-play');
    }
}
function muteVolume(){
    var video = document.getElementById('welcomeVideo');
    if(video.muted){
        video.muted = false;
        $('#btnMute').removeClass('btn btn-danger');
        $('#btnMute').addClass('btn btn-default');
    }
    else{
        video.muted = true;
        $('#btnMute').removeClass('btn btn-default');
        $('#btnMute').addClass('btn btn-danger');
    }
        
}
function replayVideo() {
    var video = document.getElementById('welcomeVideo');
    video.currentTime = 0;
  	video.play();
}
function stopVideo() {
    var video = document.getElementById('welcomeVideo');
  	video.pause();
  	if (video.currentTime)
        video.currentTime = 0;
}
function errorPage(){
    var errorDiv = $('<div class="row"><div class="col-md-12 col-sm-12"><div class="videoAndControl thumbnail"><img id="videoPlayer" style="width=100%; height=430" src="pictures/errorPic.jpg"></img></div></div></div>')
    $('.mainDiv').empty();
    $('.mainDiv').append(errorDiv);
    $('.mainDiv').css("width", "95%");
}

function randomDate(start, end) {
    var newDate = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
    return newDate.getDate()+"."+(newDate.getMonth()+1)+"."+newDate.getFullYear()+".";
}
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
function showRecentVideos(){
    $('#content').empty();
    $('#content').append("<h4>Most recent videos</h4>");
    $.ajax({
		url: 'https://jsonplaceholder.typicode.com/photos',
		type: 'GET',
		dataType: 'json',
		success: function(response) {

			// Uzimamo samo prvih 50 slika
			for(var i=12; i<18; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title);
                var views = 'Views: '+Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-4 col-sm-4"><a href="videoPage.html?id='+i+'" target="_self"><img id="videoImage" src="'+slika.thumbnailUrl+'" alt="video" style="width:100%"><div class="caption"><p id="titleBar">'+title+'</p></div><p id="videoInfo">User<br>'+views+'<br>'+newDate+'</p></a></div>');

				$('#content').append(newDiv);
			}

		},
		error: function(request, message, error) {
            errorPage();
		}
	});
}
function showPlaylists(){
    $('#content').empty();
    $('#content').append("<h4>User's playlists</h4>");
    $.ajax({
		url: 'https://jsonplaceholder.typicode.com/photos',
		type: 'GET',
		dataType: 'json',
		success: function(response) {

			// Uzimamo samo prvih 50 slika
			for(var i=0; i<3; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,45));
                var views = 'Views: '+Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-4 col-sm-4"><a href="#" target="_self"><img id="videoImage" src="'+slika.thumbnailUrl+'" alt="video" style="width:100%"><div class="caption"><p id="titleBar">'+title+'</p></div><p id="videoInfo">Crated by: User</p></a></div>');

				$('#content').append(newDiv);
			}

		},
		error: function(request, message, error) {
            errorPage();
		}
	});
    
    $.ajax({
		url: 'https://jsonplaceholder.typicode.com/photos',
		type: 'GET',
		dataType: 'json',
		success: function(response) {
            $('#content').append("<h4>Saved playlists</h4>");
			// Uzimamo samo prvih 50 slika
			for(var i=0; i<3; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,15));
                var views = 'Views: '+Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-4 col-sm-4"><a href="#" target="_self"><img id="videoImage" src="'+slika.thumbnailUrl+'" alt="video" style="width:100%"><div class="caption"><p id="titleBar">'+title+'</p></div><p id="videoInfo">Crated by: User</p></a></div>');

				$('#content').append(newDiv);
			}

		},
		error: function(request, message, error) {
            errorPage();
		}
	});
}
function showHomepage(){
    $('#content').empty();
    $('#content').append("<h4>Featured</h4>");
    $.ajax({
		url: 'https://jsonplaceholder.typicode.com/photos',
		type: 'GET',
		dataType: 'json',
		success: function(response) {
            var videoId =Math.floor(Math.random() * 100);
            var slika = response[videoId];
            var title = capitalizeFirstLetter(slika.title);
            var views = 'Views: '+Math.floor(Math.random() * 100000);
            var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
            var newDiv = $('<div class="col-md-8 col-sm-12"><video id="welcomeVideo" src="videos/videoplayback.mp4" alt="video" width="100%" ></video><div id="controls" class=""><button id="btnPlayPause" class="btn btn-default" title="play" accesskey="P" onclick="playPauseVideo();"><span class="glyphicon glyphicon-play"></span></button><button id="btnStop" class="btn btn-default" title="stop" accesskey="X" onclick="stopVideo();"><span class="glyphicon glyphicon-stop"></span></button><button id="btnReplay" class="btn btn-default" title="replay" accesskey="R" onclick="replayVideo();"><span class="glyphicon glyphicon-repeat"></span></button><button id="btnMute" class="btn btn-default" title="mute" onclick="muteVolume();"><span class="glyphicon glyphicon-volume-off"></span></button></div><div class="caption"><p id="titleBar">'+title+'</p></div><p id="videoInfo">User<br>'+views+'<br>'+newDate+'</p></a></div>');

            $('#content').append(newDiv);
			

		},
		error: function(request, message, error) {
            errorPage();
		}
	});
    $.ajax({
		url: 'https://jsonplaceholder.typicode.com/photos',
		type: 'GET',
		dataType: 'json',
		success: function(response) {
            
            var newDiv = '<div class="col-md-4 col-sm-4 col-xs-6 list-group" id="channelDiv"><ul><li id="simmChanelHeader" class="list-group-item"><h4>Simmilar channels</h4></li>';
			for(var i=0; i<9; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,10));
                newDiv += '<li class="list-group-item"><a href="#"><img id="channelPic" src="'+slika.thumbnailUrl+'" alt="video" style="width:10%">'+slika.title.substring(0,10)+'</a></li>';
			}
            newDiv += '</ul></div>';
            $('#content').append($(newDiv));
		},
		error: function(request, message, error) {
            errorPage();
		}
	});
}
function showInfo(){
    $('#content').empty();
    $('#content').append($('<h4 id="infoDivHeader">User info</h4><div class="col-xl-offset-1 col-xl-10 col-md-offset-1 col-md-10 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10 thumbnail" id="infoDiv"><p>Description:<br>laudantium enim quasi est quidem magnam voluptate ipsam eos tempora quo necessitatibus dolor quam autem quasi reiciendis et nam sapiente accusantiumlaudantium enim quasi est quidem magnam voluptate ipsam eos tempora quo necessitatibus dolor quam autem quasi reiciendis et nam sapiente accusantium</p><br><p>First name: Milos<br>Last Name: Pavicic<br>Country: Serbia<br>Email: mynewproject@gmail.com<br>Joined on: 13.1.2014.<br></p></div>'));
}
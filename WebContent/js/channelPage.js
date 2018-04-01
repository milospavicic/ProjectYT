var channelName = "";
var videos = null;
var likes = null;
var channel = null;
$('document').ready(function(e){
	var tempName = window.location.search.slice(1).split('&')[0].split('=')[1];
	channelName = tempName;
	$.post('ChannelServlet',{"channelName":channelName},function(data){
		videos = data.videos;
		likes = data.likedVideos;
		channel = data.channel;
		showRecentVideos();
	});
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
    $('#userVideos').click(function(){
        showRecentVideos();
    });
    $('#userLiked').click(function(){
    	showLikes();
    });
    $('#userInfo').click(function(){
        showInfo();
    });
    
});
function errorPage(){
    var errorDiv = $('<div class="row"><div class="col-md-12 col-sm-12"><div class="videoAndControl thumbnail"><img id="videoPlayer" style="width=100%; height=430" src="pictures/errorPic.jpg"></img></div></div></div>')
    $('.mainDiv').empty();
    $('.mainDiv').append(errorDiv);
    $('.mainDiv').css("width", "95%");
}

function showLikes(){
	var myDiv = $('#content');
    myDiv.empty();
	for(it in likes){
    	myDiv.append('<div class="col-md-4">'+
				'<a href="videoPage.html?id='+likes[it].id+'" target="_self">'+
				'<img id="videoImage" src="'+likes[it].pictureUrl+'" alt="video" style="width:100%">'+
				'<div class="caption">'+
					'<p id="titleBar">'+likes[it].videoName+'</p>'+
				'</div>'+
				'<p id="videoInfo"><a href="channelPage.html?channel='+likes[it].owner.userName+'" id="channelName">'+likes[it].owner.userName+'</a><br>'+likes[it].views+' views<br>Posted on: '+likes[it].datePosted+'</p>'+
				'</a>'+
				'</div>');
    	}
}

function showRecentVideos(){
    var myDiv = $('#content');
    myDiv.empty();
    	for(it in videos){
        	myDiv.append('<div class="col-md-4">'+
    				'<a href="videoPage.html?id='+videos[it].id+'" target="_self">'+
    				'<img id="videoImage" src="'+videos[it].pictureUrl+'" alt="video" style="width:100%">'+
    				'<div class="caption">'+
    					'<p id="titleBar">'+videos[it].videoName+'</p>'+
    				'</div>'+
    				'<p id="videoInfo"><a href="channelPage.html?channel='+videos[it].owner.userName+'" id="channelName">'+videos[it].owner.userName+'</a><br>'+videos[it].views+' views<br>Posted on: '+videos[it].datePosted+'</p>'+
    				'</a>'+
    				'</div>');
    	}
}
function showInfo(){
	var myDiv = $('#content');
	myDiv.empty();
    var newDiv = "";
    newDiv+='<h4 id="infoDivHeader">User info</h4>';
    newDiv+='<div class="col-xl-offset-1 col-xl-10 col-md-offset-1 col-md-10 col-sm-offset-1 col-sm-10 col-xs-offset-1 col-xs-10 thumbnail" id="infoDiv">';
    if(channel.description!=null){
    	newDiv+='<p>Description:<br>'+channel.description+'</p><br>';
    }
    newDiv+='<p>First name: '+channel.firstName+'<br>'+
	 			'Last Name: '+channel.lastName+'<br>'+
 				'Email: '+channel.email+'<br>'+
 				'Joined on: '+channel.registrationDate+'<br>'+
 				'Role: '+channel.userType+'<br>';
    if(channel.blocked==true){
    	newDiv+='Status: Blocked<br></p>';
    }else{
    	newDiv+='Status: Open<br></p>';
 	}
    myDiv.append(newDiv);
}
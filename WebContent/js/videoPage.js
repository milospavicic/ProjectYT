var  loggedInUser = "";
$(document).ready(function(e) {
	var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
    console.log("start of videoPage.js");
	$.get('FollowUserServlet',{},function(dataOne){
		$.get('VideoServlet',{"videoId":videoId},function(data){
			if(data.video.deleted==true){
				errorPage();
				return;
			}
			if(data.user == null){
				loggedInUser="false";
				if(data.video.blocked==true){
					errorPage();
				}
				if(data.video.ratingEnabled==false){
					$('#ldBtnGroup').hide();
				}
				if(data.video.commentsEnabled==false){
		            $('#myCommentButton').prop('disabled',true);
		            $('#hideShowBtn').prop('disabled',true);
				}
			}else{
				loggedInUser=data.user.userName;
				if(data.video.blocked==true){
					if(data.user.userType!="ADMIN" || data.video.owner.userName!=data.user.userName){
						errorPage();
					}
				}
				if(data.video.ratingEnabled==false && data.user.userType!="ADMIN"){
					$('#ldBtnGroup').hide();
				}
				if(data.video.commentsEnabled==false && data.user.userType!="ADMIN"){
		            $('#myCommentButton').prop('disabled',true);
		            $('#hideShowBtn').prop('disabled',true);
				}
			}
						
				
			var videoName=$('#videoName');
			var vCounter=$('#vCounter');
			var desc=$('#desc');
			var likeButton=$('#likeButton');
			var dislikeButton=$('#dislikeButton');
			var channelsDiv = $('#recommendedVideos .row');
			var video = $('#videoPlayer');
			video.attr('src',data.video.videoUrl+"?autoplay=1");
			
			videoName.text(data.video.videoName);//works
			vCounter.text("Views: "+data.video.views);
			likeButton.html('<span class="glyphicon glyphicon-thumbs-up"></span>'+' '+data.video.numberOfLikes);
			dislikeButton.html('<span class="glyphicon glyphicon-thumbs-down"></span>'+' '+data.video.numberOfDislikes);
			if(data.likedVideo!=null){
				if(data.likedVideo.likeOrDislike==false){
					dislikeButton.prop('disabled',true);
				}else{
					likeButton.prop('disabled',true);
				}
			}
			desc.append(
					'<a href="channelPage.html?channel='+data.video.owner.userName+'"><img src="'+data.video.owner.profileUrl+'" alt="Lights" style="width:70px;height: 70px"></a>'+
	                '<ul id="nameAndDate">'+
	                    '<li><a href="channelPage.html?channel='+data.video.owner.userName+'">'+data.video.owner.userName+'</a></li>'+
	                    '<li><p>Published on<br>'+data.video.datePosted+'</p></li>'+
	                '</ul>');
			
			var status = "false";
			for(temp in dataOne.userSubs){
				var stringOne = "";
				var stringTwo = "";
				stringOne=dataOne.userSubs[temp];
				stringTwo=data.video.owner.userName;
				if(stringOne==stringTwo){
					status = "true";
					break;
				}		
			}
			if(status=="false"){
				desc.append(		
		                '<button id="btnFollow" type="button" class="btn btn-danger" onclick="follow(this,\''+data.video.owner.userName+'\')">Follow</button>'+
		                '<p class="thumbnail" id="textDescription">'+data.video.description+'</p>');
			}else{
				desc.append(		
		                '<button id="btnFollow" type="button" class="btn btn-default" onclick="follow(this,\''+data.video.owner.userName+'\')">Unfollow</button>'+
		                '<p class="thumbnail" id="textDescription">'+data.video.description+'</p>');
			}
			
			for(it in data.recommended){
				channelsDiv.append('<div class="col-md-2 col-sm-4 col-xs-6" id="test">'+
						'<a href="videoPage.html?id='+data.recommended[it].id+'" target="_self">'+
						'<img id="imgFormat" src="'+data.recommended[it].pictureUrl+'" alt="video" style="width:100%">'+
						'<div class="caption"><p id="titleBar">'+data.recommended[it].videoName+'</p></div>'+
						'<p id="stats">'+data.recommended[it].owner.userName+'<br>'+data.recommended[it].views+'<br>'+data.recommended[it].datePosted+'</p></a></div>');

			}
		});
	});
    loadComments(videoId,"id","DESC");  
    
    $('#likeButton').click(function() {
    	if(loggedInUser=="false"){
    		$("#login-modal").modal('toggle');
    		return;
    	}
        if($('#dislikeButton').prop('disabled')==true){
            var likes ='<span class="glyphicon glyphicon-thumbs-up"></span> '+(parseInt($('#likeButton').text())+1);
            var dislikes ='<span class="glyphicon glyphicon-thumbs-down"></span> '+(parseInt($('#dislikeButton').text())-1);
            $(this).html(likes); 
            $('#dislikeButton').html(dislikes);
            $('#dislikeButton').prop('disabled',false);
        }else{
            var likes ='<span class="glyphicon glyphicon-thumbs-up"></span> '+(parseInt($('#likeButton').text())+1);
            $(this).html(likes);
        }
        $(this).prop('disabled',true);
        $.get('LikeVideoServlet',{"videoId":videoId,"loggedInUser":storedName,"status":"liked"},function(data){});
    });
    $('#dislikeButton').click(function() {
    	if(loggedInUser=="false"){
    		$("#login-modal").modal('toggle');
    		return;
    	}
        if($('#likeButton').prop('disabled')==true){
            var likes ='<span class="glyphicon glyphicon-thumbs-up"></span> '+(parseInt($('#likeButton').text())-1);
            var dislikes ='<span class="glyphicon glyphicon-thumbs-down"></span> '+(parseInt($('#dislikeButton').text())+1);
            $(this).html(dislikes);
            $('#likeButton').html(likes);
            $('#likeButton').prop('disabled',false);
        }else{
            var dislikes ='<span class="glyphicon glyphicon-thumbs-down"></span> '+(parseInt($('#dislikeButton').text())+1);
            $(this).html(dislikes);
        }
        $(this).prop('disabled',true);
        $.get('LikeVideoServlet',{"videoId":videoId,"loggedInUser":storedName,"status":"dislike"},function(data){});
    });
    $('#myFormButton').click(function(){
    	if(loggedInUser=="false"){
    		$("#login-modal").modal('toggle');
    		return;
    	}
    	var commentText = $('#myCommentText').val();
    	if(commentText=="") return;
    	$.post('CommentsServlet',{"videoId":videoId,"commentText":commentText,"loggedInUser":storedName},function(data){
    		$('#myCommentText').val("");
    		$('#myComment').collapse("hide");
    		refreshComments();
    	});
    });
});
function errorPage(){
    var errorDiv = $('<div class="row"><div class="col-md-12 col-sm-12"><div class="videoAndControl thumbnail"><img id="videoPlayer" style="width=100%; height=430" src="pictures/errorPic.jpg"></img></div></div></div>')
    $('.mainDiv').empty();
    $('.mainDiv').append(errorDiv);
    $('.mainDiv').css("width", "95%");
}
function follow(btn,userName){
	if(loggedInUser=="false"){
		$("#login-modal").modal('toggle');
		return;
	}
    var tempName = $("#"+btn.id).text();
    if(tempName == "Follow"){
        $("#"+btn.id).text("Unfollow");
        $("#"+btn.id).attr('class', 'btn btn-default');
        console.log(storedName+" loggedInUser");
        $.post('FollowUserServlet',{"userName":userName,"loggedInUser":loggedInUser,"status":"follow"},function(data){});
    }else{
        $("#"+btn.id).text("Follow");
        $("#"+btn.id).attr('class', 'btn btn-danger');
        console.log(storedName+" loggedInUser !");
        $.post('FollowUserServlet',{"userName":userName,"loggedInUser":loggedInUser,"status":"unfollow"},function(data){});
    }
}

function loadComments(videoId,column,ascDsc){
	console.log("loadingComments");
    var commentsDiv = $('.commentRow');
    commentsDiv.empty();
    $.get('CommentsServlet',{"videoId":videoId,"columnName":column,"ascDes":ascDsc},function(data){
    	for(it in data.comments){
    		var rating = 0;
    		rating = data.comments[it].likeNumber-data.comments[it].dislikeNumber;
    		commentsDiv.append('<div class="col-md-12 col-sm-12">'+
    				'<div class="thumbnail" id="comment">'+
    				'<a href="channelPage.html" id="commentOwner">'+data.comments[it].user.userName+'</a>'+
    				'<p id="commentDate">'+data.comments[it].datePosted+'</p>'+
    				'<div class="commentText"><p>'+data.comments[it].text+'</p></div>'+
    				'<button type="button" class="btn btn-default">Reply</button>'+
    				'<p class="likes" id="rating'+data.comments[it].id+'">'+rating+'</p>'+
    				'<div class="btn-group" id="commLDBtnGroup">'+
    				'<button type="button" class="btn btn-danger" id="like'+data.comments[it].id+'" onclick="commLike('+data.comments[it].id+')"><span class="glyphicon glyphicon-thumbs-up"></span></button>'+
    				'<button type="button" class="btn btn-default" id="dislike'+data.comments[it].id+'" onclick="commDislike('+data.comments[it].id+')"><span class="glyphicon glyphicon-thumbs-down"></span></button>'+
    				'</div></div></div>');
    		for(temp in data.likes){
    			if(data.comments[it].id==data.likes[temp].comment.id){
    				if(data.likes[temp].likeOrDislike==true)
    					$('#like'+data.comments[it].id+'').prop('disabled',true);
    				else
    					$('#dislike'+data.comments[it].id+'').prop('disabled',true);
    			}
    		}
    	}
    });
}
function refreshComments() {
	var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
	var option = commSelect.options[commSelect.selectedIndex].value;
	if(option=="MP"){
		loadComments(videoId,"(likeNumber-dislikeNumber)","DESC");
	}
	else if(option=="LP"){
		loadComments(videoId,"(likeNumber-dislikeNumber)","DESC");
	}else{
	    loadComments(videoId,"id",option);
	}
}
function commLike(commId){
	if(loggedInUser=="false"){
		$("#login-modal").modal('toggle');
		return;
	}
	
	if($('#dislike'+commId+'').prop('disabled')==true){
		var rating = (parseInt($('#rating'+commId+'').text())+2);
		$('#rating'+commId+'').text(rating);
		$('#like'+commId+'').prop('disabled',true);
		$('#dislike'+commId+'').prop('disabled',false);
	}else{
		var rating = (parseInt($('#rating'+commId+'').text())+1);
		$('#rating'+commId+'').text(rating);
        $('#like'+commId+'').prop('disabled',true);
	}
	
	$.get('LikeCommentServlet',{"commentId":commId,"loggedInUser":loggedInUser,"status":"liked"},function(data){});
}
function commDislike(commId){
	if(loggedInUser=="false"){
		$("#login-modal").modal('toggle');
		return;
	}
	if($('#like'+commId+'').prop('disabled')==true){
		var rating = (parseInt($('#rating'+commId+'').text())-2);
		$('#rating'+commId+'').text(rating);
		$('#dislike'+commId+'').prop('disabled',true);
		$('#like'+commId+'').prop('disabled',false);
	}else{
		var rating = (parseInt($('#rating'+commId+'').text())-1);
		$('#rating'+commId+'').text(rating);
        $('#dislike'+commId+'').prop('disabled',true);
	}
	$.get('LikeCommentServlet',{"commentId":commId,"loggedInUser":loggedInUser,"status":"disliked"},function(data){});
}

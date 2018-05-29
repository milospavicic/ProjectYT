var loggedInUser = "";
var editCommentId = 0;
var editVideoId = 0;
var deleteCommentId = 0;
var blocked = false;
$(document).ready(function(e) {
	var videoId = window.location.search.slice(1).split('&')[0].split('=')[1];
	editVideoId = videoId;
    console.log("start of videoPage.js");
	$.get('FollowUserServlet',{},function(dataOne){
		$.get('VideoServlet',{"videoId":videoId},function(data){
			if(data.video==null){
				errorPage();
				return;
			}
			if(data.loggedInUser!=null){
				if(data.loggedInUser.blocked == true){
					$('#uploadVideo').attr("href", "#blockedModal");
					blocked = data.loggedInUser.blocked;
				}
			}
			if(data.video)
			if(data.loggedInUser == null){
				loggedInUser="false";
				if(data.video.blocked==true){
					errorPage();
					return;
				}
				if(data.video.ratingEnabled==false){
					$('#ldBtnGroup').hide();
				}
				if(data.video.commentsEnabled==false){
		            $('#myCommentButton').prop('disabled',true);
		            $('#hideShowBtn').prop('disabled',true);
				}
			}else{
				loggedInUser=data.loggedInUser.userName;
				if(data.video.blocked==true){
					$('#blockOptionVideo').hide();
					if(data.video.owner.userName!=data.loggedInUser.userName){
						if(data.loggedInUser.userType!="ADMIN"){
							errorPage();
							return;
						}else{
							$('#unblockOptionVideo').show();
						}
					}
				}else{
					$('#unblockOptionVideo').hide();
				}
				if(data.video.ratingEnabled==false && data.loggedInUser.userType!="ADMIN"){
					$('#ldBtnGroup').hide();
				}
				if(data.video.commentsEnabled==false && data.loggedInUser.userType!="ADMIN"){
		            $('#myCommentButton').prop('disabled',true);
		            $('#hideShowBtn').prop('disabled',true);
				}
				
				console.log(data.video.owner.userName+"  "+data.loggedInUser.userName)
				if(data.video.owner.userName==data.loggedInUser.userName){
					$('#editVideoOption').show();
					$('#deleteOptionVideo').show();
		            $('#myCommentButton').prop('disabled',false);
		            $('#hideShowBtn').prop('disabled',false);
		            $('#ldBtnGroup').show();
				}
				
			}
			fillEditVideo(data.video);			
			document.title = ''+data.video.videoName+' - MyTube';
			var videoName=$('#videoName');
			var vCounter=$('#vCounter');
			var desc=$('#desc');
			var likeButton=$('#likeButton');
			var dislikeButton=$('#dislikeButton');
			var channelsDiv = $('#recommendedVideos .row');
			var video = $('#videoPlayer');
			video.attr('src',data.video.videoUrl+"?autoplay=1");
			
			
			if(data.video.blocked==true){
				videoName.html(data.video.videoName + " <i>( Blocked )</i>")
			}else{
				videoName.text(data.video.videoName);//works
			}
			
			vCounter.text("Views: "+data.video.views);
			likeButton.html('<span class="glyphicon glyphicon-thumbs-up"></span>'+' '+data.video.numberOfLikes);
			dislikeButton.html('<span class="glyphicon glyphicon-thumbs-down"></span>'+' '+data.video.numberOfDislikes);
			if(data.likedVideo!=null){
				if(data.likedVideo.likeOrDislike==true){
					setVideoLikeActive();
				}else{
					setVideoDislikeActive();
				}
			}
			if(data.video.owner.lol==true){
				var profilePic = "pictures/"+data.video.owner.profileUrl;
			}else{
				var profilePic = +data.video.owner.profileUrl;
			}
			
			desc.append(
					'<a href="channelPage.html?channel='+data.video.owner.userName+'"><img src="'+profilePic+'" alt="Lights" style="width:70px;height: 70px"></a>'+
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
				if(data.recommended[it].blocked==true){
					var videoName = data.recommended[it].videoName + ' - <i>Blocked</i>';
				}else{
					var videoName = data.recommended[it].videoName;
				}
				channelsDiv.append('<div class="col-md-2 col-sm-4 col-xs-6" id="test">'+
						'<a href="videoPage.html?id='+data.recommended[it].id+'" target="_self">'+
						'<img id="imgFormat" src="'+data.recommended[it].pictureUrl+'" alt="video" style="width:100%">'+
						'<div class="caption"><p id="titleBar">'+videoName+'</p></div>'+
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
    	if(blocked == true){
    		$("#blockedModal").modal('toggle');
    		return;
    	}
    	$.get('LikeVideoServlet',{"videoId":videoId,"status":"liked"},function(data){
    		setVideoLikeDislikeNumbers(data.video);
    		if(data.status=="neutral"){
    			setVideoButtonsDefault();
    		}else if(data.status=="like"){
    			setVideoLikeActive();
    		}else{
    			setVideoDislikeActive();
    		}
    	});
    });
    $('#dislikeButton').click(function() {
		if(loggedInUser=="false"){
			$("#login-modal").modal('toggle');
			return;
		}
    	if(blocked == true){
    		$("#blockedModal").modal('toggle');
    		return;
    	}
    	$.get('LikeVideoServlet',{"videoId":videoId,"status":"dislike"},function(data){
    		setVideoLikeDislikeNumbers(data.video);
    		if(data.status=="neutral"){
    			setVideoButtonsDefault();
    		}else if(data.status=="like"){
    			setVideoLikeActive();
    		}else{
    			setVideoDislikeActive();
    		}
    	});
    });
    //NEW COMMENT
    $('#myFormButton').click(function(){
    	if(loggedInUser=="false"){
    		$("#login-modal").modal('toggle');
    		return;
    	}
    	if(blocked == true){
    		$("#blockedModal").modal('toggle');
    		return;
    	}
    	var commentText = $('#myCommentText').val();
    	if(commentText=="") return;
    	$.post('CommentsServlet',{"status":"new","videoId":videoId,"commentText":commentText},function(data){
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
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
    var tempName = $("#"+btn.id).text();
    if(tempName == "Follow"){
        $("#"+btn.id).text("Unfollow");
        $("#"+btn.id).attr('class', 'btn btn-default');
        $.post('FollowUserServlet',{"userName":userName,"status":"follow"},function(data){});
    }else{
        $("#"+btn.id).text("Follow");
        $("#"+btn.id).attr('class', 'btn btn-danger');
        $.post('FollowUserServlet',{"userName":userName,"status":"unfollow"},function(data){});
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
    		commentsDiv.append('<div class="col-md-12 col-sm-12" id="div'+data.comments[it].id+'">'+
    				'<div class="thumbnail" id="comment">'+
    				'<a href="channelPage.html" id="commentOwner">'+data.comments[it].user.userName+'</a>'+
    				'<p id="commentDate">'+data.comments[it].datePosted+'</p>'+
    				'<div class="commentText"><p id="commentEdit'+data.comments[it].id+'">'+data.comments[it].text+'</p></div>'+
    				'<button type="button" class="btn btn-default">Reply</button>'+
    				'<p class="likes" id="rating'+data.comments[it].id+'">'+rating+'</p>'+
    				'<div class="btn-group" id="commLDBtnGroup">'+
    				'<button type="button" class="btn btn-default" id="like'+data.comments[it].id+'" onclick="commLike('+data.comments[it].id+')"><span class="glyphicon glyphicon-thumbs-up"></span></button>'+
    				'<button type="button" class="btn btn-default" id="dislike'+data.comments[it].id+'" onclick="commDislike('+data.comments[it].id+')"><span class="glyphicon glyphicon-thumbs-down"></span></button>'+
    				'<button type="button" class="btn btn-default" id="edit'+data.comments[it].id+'" onclick="editComment('+data.comments[it].id+',\''+data.comments[it].text+'\')"><span class="glyphicon glyphicon-edit"></span></button>'+
    				'<button type="button" class="btn btn-default" id="delete'+data.comments[it].id+'" onclick="deleteComment('+data.comments[it].id+')"><span class="glyphicon glyphicon-trash"></span></button>'+		
    		'</div></div></div>');
    		for(temp in data.likes){
    			if(data.comments[it].id==data.likes[temp].comment.id){
    				if(data.likes[temp].likeOrDislike==true)
    					setCommentLikeActive(data.comments[it].id);
    				else
    					setCommentDislikeActive(data.comments[it].id);
    			}
    		}
    	}
    });
}
function editComment(commentId,commentText){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$('#editCommentText').val(commentText);
	editCommentId = commentId;
	$('#editComment-modal').modal('show');
}
function editCommentButton(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	var commentText = $('#editCommentText').val();
	$.post('CommentsServlet',{"status":"edit","commentText":commentText,"commentId":editCommentId},function(data){
		$('#editComment-modal').modal('hide');
		$('#commentEdit'+editCommentId+'').text(commentText);
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
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.get('LikeCommentServlet',{"commentId":commId,"status":"liked"},function(data){
		setCommentRating(data.comment);
		if(data.status=="neutral"){
			setCommentButtonsDefault(data.comment.id);
		}else if(data.status=="like"){
			setCommentLikeActive(data.comment.id);
		}else{
			setCommentDislikeActive(data.comment.id);
		}
	});
}
function commDislike(commId){
	if(loggedInUser=="false"){
		$("#login-modal").modal('toggle');
		return;
	}
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.get('LikeCommentServlet',{"commentId":commId,"status":"disliked"},function(data){
		setCommentRating(data.comment);
		if(data.status=="neutral"){
			setCommentButtonsDefault(data.comment.id);
		}else if(data.status=="like"){
			setCommentLikeActive(data.comment.id);
		}else{
			setCommentDislikeActive(data.comment.id);
		}
	});
	
}
function fillEditVideo(video){
	$('#editDescription').val(video.description);
	$('#editPicUrl').val(video.pictureUrl);
	if(video.visibility==("PUBLIC")){
		console.log("its public");
		$('#selectOne option[value=1]').attr('selected',true);
	}
	if(video.visibility==("PRIVATE")){
		console.log("its private");
		$('#selectOne option[value=2]').attr('selected',true);
	}
	if(video.visibility==("UNLISTED")){
		console.log("its unlisted");
		$('#selectOne option[value=3]').attr('selected',true);
	}
	
	if(video.commentsEnabled==true){
		$('#selectTwo option[value=3]').attr('selected',true);
	}else{
		$('#selectTwo option[value=2]').attr('selected',true);
	}
	
	if(video.ratingEnabled==true){
		$('#selectThree option[value=3]').attr('selected',true);
	}else{
		$('#selectThree option[value=2]').attr('selected',true);
	}
}
function saveEditVideo(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	var desc = $('#editDescription').val();
	var picurl = $('#editPicUrl').val();
	var visib = $('#selectOne').val();
	var comm = $('#selectTwo').val();
	var rating = $('#selectThree').val();
	console.log(editVideoId);
	$.post('VideoServlet',{"status":"edit","videoId":editVideoId,"desc":desc,"picurl":picurl,"visib":visib,"comm":comm,"rating":rating},function(data){location.reload();});
}
function setVideoButtonsDefault(){
    $('#likeButton').removeClass();
    $('#dislikeButton').removeClass();
    
    $('#likeButton').addClass("btn btn-default");
    $('#dislikeButton').addClass("btn btn-default");
}
function setVideoLikeActive(){
    $('#likeButton').removeClass();
    $('#dislikeButton').removeClass();
    
    $('#likeButton').addClass("btn btn-danger");
    $('#dislikeButton').addClass("btn btn-default");
}
function setVideoDislikeActive(){
    $('#likeButton').removeClass();
    $('#dislikeButton').removeClass();
    
    $('#likeButton').addClass("btn btn-default");
    $('#dislikeButton').addClass("btn btn-danger");
}
function setVideoLikeDislikeNumbers(video){
    var likes ='<span class="glyphicon glyphicon-thumbs-up"></span> '+video.numberOfLikes;
    var dislikes ='<span class="glyphicon glyphicon-thumbs-down"></span> '+video.numberOfDislikes;
    $('#likeButton').html(likes);
    $('#dislikeButton').html(dislikes);
}
function setCommentButtonsDefault(commId){
	$('#like'+commId+'').removeClass();
	$('#dislike'+commId+'').removeClass();
	
	$('#like'+commId+'').addClass("btn btn-default");
	$('#dislike'+commId+'').addClass("btn btn-default");
}
function setCommentLikeActive(commId){
	$('#like'+commId+'').removeClass();
	$('#dislike'+commId+'').removeClass();
	
	$('#like'+commId+'').addClass("btn btn-danger");
	$('#dislike'+commId+'').addClass("btn btn-default");
}
function setCommentDislikeActive(commId){
	$('#like'+commId+'').removeClass();
	$('#dislike'+commId+'').removeClass();
	
	$('#like'+commId+'').addClass("btn btn-default");
	$('#dislike'+commId+'').addClass("btn btn-danger");
}
function setCommentRating(comment){
	var rating = comment.likeNumber-comment.dislikeNumber;
	$('#rating'+comment.id+'').html(rating)
}
function deleteComment(id){
	$('#deleteCommentModal').modal();
	deleteCommentId = id;
}
function deleteCommentById(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.post('CommentsServlet',{"status":"delete","commentId":deleteCommentId},function(data){
		$('#deleteCommentModal').modal('hide');
		$('#div'+deleteCommentId+'').hide();
		console.log('hiden');
	});
}
function blockVideo(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.post('VideoServlet',{"status":"block","videoId":editVideoId},function(data){
		location.reload();
	});
}
function unblockVideo(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.post('VideoServlet',{"status":"unblock","videoId":editVideoId},function(data){
		location.reload();
	});
}
function deleteVideo(){
	if(blocked == true){
		$("#blockedModal").modal('toggle');
		return;
	}
	$.post('VideoServlet',{"status":"delete","videoId":editVideoId},function(data){
		location.reload();
	});
}
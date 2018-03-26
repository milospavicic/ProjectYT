var loggedInUser = "";
$('document').ready(function(e){
	$.get('FollowUserServlet',{},function(dataOne){console.log(dataOne.userSubs);
		var userDiv = $('.row');
		$.get('GetUsersServlet',{},function(data){
		    if(data.loggedInUser==null){
		        window.location.href = "index.html";
		    }else{
		    	loggedInUser = data.loggedInUser.userName;
		    }
			var counter = 1;
			for(it in data.users){
				if(data.users[it].userName==loggedInUser)
					continue;
				if(counter>10) break;
				var description = data.users[it].channelDescription;
				if(description==null) 
					description="";
				var divForAppend = "";
				for(user in data.userSubs){
					console.log(data.userSubs + "temp");
				}
				divForAppend = '<div class="col-md-12 col-sm-12 col-xs-12" id="div'+counter+'">'+
									'<a href="channelPage.html?channel='+data.users[it].userName+'" id="picDiv">'+
									'<img src="'+data.users[it].profileUrl+'" alt="Lights" style="width:150px;height: 150px">'+
									'<ul id="channelInfo">'+
										'<li><h3>'+data.users[it].userName+'</h3></li>'+
										'<li id="subsNVideos'+data.users[it].userName+'"><p id="temp'+data.users[it].userName+'">Followers: '+data.users[it].subsNumber+'</p></li>'+
										'<li><p>Name: '+data.users[it].firstName+" "+data.users[it].lastName+'</p></li>'+
										'<li><p>Email: '+data.users[it].email+'</p></li>'+
										'<li><p>Role: '+data.users[it].userType+'</p></li>'+
									'</ul>'+
									'</a>';
				
				var tempStatus = "false";
				for(temp in dataOne.userSubs){
					var stringOne = "";
					var stringTwo = "";
					stringOne=dataOne.userSubs[temp];
					stringTwo=data.users[it].userName;
					
					if(stringOne==stringTwo){
						tempStatus = "true";
						break;
					}		
				}
				console.log("tempStatus "+tempStatus)
				if(tempStatus == "false"){
					divForAppend+='<div class="btn-group" role="group" id="channelButton">'+
										'<button type="button" class="btn btn-danger" id="btnFollow'+counter+'" onclick="follow(this,\''+data.users[it].userName+'\',\''+data.users[it].subsNumber+'\')">Follow</button>'+
										'<button type="button" class="btn btn-default" id="btnEdit'+counter+'" "><span class="glyphicon glyphicon-edit"></span></button>'+
										'<button type="button" class="btn btn-default" id="btnDelete'+counter+'" "><span class="glyphicon glyphicon-trash"></span></button>'+
								'</div>'+
					     	'</div>';
				}
				else{
					divForAppend+='<div class="btn-group" role="group" id="channelButton">'+
									   '<button type="button" class="btn btn-default" id="btnFollow'+counter+'" onclick="follow(this,\''+data.users[it].userName+'\',\''+data.users[it].subsNumber+'\')">Unfollow</button>'+
									   '<button type="button" class="btn btn-default" id="btnEdit'+counter+'" "><span class="glyphicon glyphicon-edit"></span></button>'+
									   '<button type="button" class="btn btn-default" id="btnDelete'+counter+'" "><span class="glyphicon glyphicon-trash"></span></button>'+
								  '</div>'+
							'</div>';
				}
	
				userDiv.append(divForAppend);
				counter++;
			}
		});
	});

});  
function errorPage(){
    var errorDiv = $('<div class="row"><div class="col-md-12 col-sm-12"><div class="videoAndControl thumbnail"><img id="videoPlayer" style="width=100%; height=430" src="pictures/errorPic.jpg"></img></div></div></div>')
    $('.mainDiv').empty();
    $('.mainDiv').append(errorDiv);
    $('.mainDiv').css("width", "95%");
    $('.mainDiv').css("margin-bottom", "30px");
}
function follow(btn,userName,followers){
    var tempName = $("#"+btn.id).text();
    var newText =  $('#subsNVideos'+userName);
    
    var tempFollowers =  $('#temp'+userName).text();
    tempFollowers = tempFollowers.substring(11);
    if(tempName == "Follow"){
        $("#"+btn.id).text("Unfollow");
        $("#"+btn.id).attr('class', 'btn btn-default');
        $.post('FollowUserServlet',{"userName":userName,"status":"follow"},function(data){});
        tempFollowers = parseInt(tempFollowers)+1;
        console.log(tempFollowers+" fo");
        newText.empty();
        newText.append('<p id="temp'+userName+'">Followers: '+tempFollowers+'</p>');
    }else{
        $("#"+btn.id).text("Follow");
        $("#"+btn.id).attr('class', 'btn btn-danger');
        $.post('FollowUserServlet',{"userName":userName,"status":"unfollow"},function(data){});
        tempFollowers = parseInt(tempFollowers)-1;
        console.log(tempFollowers+" un");
        newText.empty();
        newText.append('<p id="temp'+userName+'">Followers: '+tempFollowers+'</p>');
    }
}
$('document').ready(function(e){
    showVideos();
});
function errorPage(){
    var errorDiv = $('<div class="row"><div class="col-md-12 col-sm-12"><div class="videoAndControl thumbnail"><img id="videoPlayer" style="width=100%; height=430" src="pictures/errorPic.jpg"></img></div></div></div>')
    $('.mainDiv').empty();
    $('.mainDiv').append(errorDiv);
    $('.mainDiv').css("width", "95%");
    $('.mainDiv').css("margin-bottom", "30px");
    $('.mainDiv').removeClass('thumbnail');
}
function randomDate(start, end) {
    var newDate = new Date(start.getTime() + Math.random() * (end.getTime() - start.getTime()));
    return newDate.getDate()+"."+(newDate.getMonth()+1)+"."+newDate.getFullYear()+".";
}
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
function showVideos(){
      $.ajax({
          
          url: 'https://jsonplaceholder.typicode.com/photos',
          type: 'GET',
          dataType: 'json',
          success: function(response) {

			// Uzimamo samo prvih 50 slika
			for(var i=0; i<3; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

				$('#collapseOne').append(newDiv);
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

			// Uzimamo samo prvih 50 slika
			for(var i=4; i<7; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

				$('#collapseTwo').append(newDiv);
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

			// Uzimamo samo prvih 50 slika
			for(var i=7; i<10; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

				$('#collapseThree').append(newDiv);
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

			// Uzimamo samo prvih 50 slika
            for(var i=10; i<13; i++) {
                var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

                $('#collapseFour').append(newDiv);
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

			// Uzimamo samo prvih 50 slika
			for(var i=13; i<16; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

				$('#collapseFive').append(newDiv);
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

			// Uzimamo samo prvih 50 slika
            for(var i=16; i<19; i++) {
                var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

                $('#collapseSix').append(newDiv);
            }

		},
		error: function(request, message, error) {
			errorPage();
		}
	});
}
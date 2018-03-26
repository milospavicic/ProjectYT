$('document').ready(function(e){
    searchVideos(1);
    $('#paginationItem a').click(function(){
        console.log("123");
        $('.active').removeClass('active');
        $(this).parent("li").addClass('active');
        searchVideos($(this).text());
    })
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
function searchVideos(n){
    $('.row').empty();
      $.ajax({
          
          url: 'https://jsonplaceholder.typicode.com/photos',
          type: 'GET',
          dataType: 'json',
          success: function(response) {
            var max = n*10;
            var min = max-10;
			// Uzimamo samo prvih 50 slika
			for(var i=min; i<max; i++) {
				var slika = response[i];
                var title = capitalizeFirstLetter(slika.title.substring(0,25));
                var views = Math.floor(Math.random() * 100000);
                var newDate = 'Posted on: '+randomDate(new Date(2010, 0, 1), new Date());
                var newDiv = $('<div class="col-md-12 col-sm-12 col-xs-12"><a href="videoPage.html" id="picDiv"><img src="'+slika.thumbnailUrl+'" alt="Lights" style="width:190px;height: 150px"><ul id="videoInfo"><li><h3>'+title+'</h3></li><li id="videoUserViewsDate"><p><a href="channelPage.html">User</a> - '+views+' views - '+newDate+'</p></li><li><p>'+slika.title+slika.title+'</p></li></ul></a></div>');

				$('.row').append(newDiv);
			}

		},
		error: function(request, message, error) {
			errorPage();
		}
	});
}
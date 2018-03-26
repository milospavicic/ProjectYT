$('document').ready(function(e){
	console.log("nav.js file start");
    loginStatus();
    $('#searchSubmit').click(function(e){
        if($.trim($('#searchParameter').val()) == ''){
            e.preventDefault(); 
        }
    });
    $('#logInSubmit').on('click',function(event){
        var uname = document.getElementById("usernameLogin").value.trim();
        var pasw = document.getElementById("passwordLogin").value.trim();
        console.log(uname+"	"+pasw);
        var status = "";
        if(uname =="" || pasw==""){
            $("#loginErrorMessage").text("All fields must be filled!");
            $("#badLoginModal").modal();
        }else{
        	$.post('LoginServlet',{'userName': uname, 'password': pasw},function(data){
        		if(data.status=="failed"){
                    $("#loginErrorMessage").text("Wrong username or password!");
                    $("#badLoginModal").modal();
        		}else{
        			$("#login-modal").modal('toggle');
        			location.reload();
        		}
        	});
        }
    });
    //-------------------------------------------------------------------------
    $('#registerSubmit').on('click',function(event){
        var uname = document.getElementById("usernameRegister").value.trim();
        var pasw = document.getElementById("passwordRegister").value.trim();
        var fname = document.getElementById("firstNameRegister").value.trim();
        var lname = document.getElementById("lastNameRegister").value.trim();
        var email = document.getElementById("emailRegister").value.trim();
        var regStatus = "";
        if(uname == "" || pasw=="" || fname=="" || lname=="" || email==""){
            $("#registerErrorMessage").text("All fields must be filled!");
            $("#badRegisterModal").modal(); 
        }else{
        	$.get('RegisterServlet',{'userName': uname, 'password': pasw,'firstName': fname, 'lastName': lname,'email': email},function(data){
        		if(data.status=="taken"){
                    $("#registerErrorMessage").text("Already used username!");
                    $("#badRegisterModal").modal(); 
        		}else{
                    $("#registerErrorMessage").html($('<div class="alert alert-success"><strong>Successful registration!</strong> You may now procced to login.</div>'));
                    $("#badRegisterModal").modal();
                    $("#register-modal").modal('toggle');
        		}
        	}); 	
        } 
    });
    event.preventDefault();
    
    //USER DROPDOWN
    $('ul[role="menu"]')
        .on('show.bs.collapse', function (e) {
        $(e.target).prev('a[role="menuitem"]').addClass('active');
    })
        .on('hide.bs.collapse', function (e) {
        $(e.target).prev('a[role="menuitem"]').removeClass('active');
    });

    $('a[data-toggle="collapse"]').click(function (event) {
        event.stopPropagation();
        event.preventDefault();

        var col_id = $(this).attr("href");
        $(col_id).collapse('toggle'); 
    });
    //USER DROPDOWN END 
});
function loginStatus(){
	$.get('LoginServlet',{},function(data){
	    if(data.user == null){
	        var usersTabInDropdown = document.getElementById("users");
	        usersTabInDropdown.style.display = "none";
	        $('#deleteOptionVideo').hide();
	        $('#deleteOptionChannel').hide(); 
	        $('#editVideoOption').hide();
	        $('#blockOptionVideo').hide();
	    }else{
	        var y = document.getElementById("navbarNotLoggedIn");
	        y.style.display = "none";
	        var navbarLoggedIn = document.getElementById("navbarLoggedIn");
	        navbarLoggedIn.style.display = "block";
	        var temp = '<span class="glyphicon glyphicon-user"></span>'+" "+data.user.userName+" "+'<span class="caret"></span>';
	        $("#userNameTab").html(temp);
	        if(data.user.userType=="USER"){
	            var usersTabInDropdown = document.getElementById("users");
	            usersTabInDropdown.style.display = "none";
	            $('#deleteOptionVideo').hide();
	            $('#deleteOptionChannel').hide();
	            $('#editVideoOption').hide();
	            $('#blockOptionVideo').hide();
	        }
	    }
	});
}
function logout(){
	$.get('LogoutServlet',{},function(data){location.reload();});
}
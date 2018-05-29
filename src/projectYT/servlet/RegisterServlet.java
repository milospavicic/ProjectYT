package projectYT.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import projectYT.dao.UserDAO;
import projectYT.model.User;
import projectYT.model.User.UserType;

/**
 * Servlet implementation class RegisterServlet
 */
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String lol = request.getParameter("lol");
		String profileUrl = request.getParameter("profileUrl");
		String status = "success";
		User newUser = UserDAO.getUserByName(userName);
		if(newUser!=null) status="taken";
		else {
			Date newDate = new Date();
			String myNewDate = UserDAO.dateToStringForWrite(newDate);
			//String basicProfileUrl = "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png";
			newUser = new User();
			newUser.setUserName(userName);
			newUser.setPassword(password);
			newUser.setFirstName(firstName);
			newUser.setLastName(lastName);
			newUser.setEmail(email);
			newUser.setChannelDescription("");
			newUser.setDeleted(false);
			newUser.setBlocked(false);
			newUser.setRegistrationDate(myNewDate);
			newUser.setProfileUrl(profileUrl);
			newUser.setUserType(UserType.USER);
			if(lol.equals("true")) {
				newUser.setLol(true);
				HttpSession session = request.getSession();
				session.setAttribute("channel", newUser);
			}else {
				newUser.setLol(false);
			}
			UserDAO.addUser(newUser);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}

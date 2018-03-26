package projectYT.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		String status = "success";
		System.out.println(userName + " " + password + " " +firstName + " " + lastName+ " " + email);

		User newUser = UserDAO.getUserByName(userName);
		if(newUser!=null) status="taken";
		else {
			Date newDate = new Date();
			String myNewDate = UserDAO.dateToStringForWrite(newDate);
			String basicProfileUrl = "http://www.personalbrandingblog.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640-300x300.png";
			newUser = new User(userName, password, firstName, lastName, email, null, myNewDate, false, null, null, null, UserType.USER, false,basicProfileUrl);
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

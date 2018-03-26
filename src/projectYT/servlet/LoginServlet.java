package projectYT.servlet;

import java.io.IOException;
import java.util.ArrayList;
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

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		Map<String, Object> data = new HashMap<>();
		data.put("user", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		User user = null;
		String status = "success";
		try {
			user = UserDAO.getUserByName(userName);

			if (user == null) throw new Exception("NoUser");
			if (!user.getPassword().equals(password)) throw new Exception("BadPass");
			
			HttpSession session = request.getSession();
			session.setAttribute("loggedInUser", user);
		}catch (Exception e) {
			status="failed";
			System.out.println(e.getMessage());
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("status", status);
		data.put("user", user);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println(jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}

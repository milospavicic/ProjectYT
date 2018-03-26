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


public class GetUsersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Ucitavanje usera zapoceto");

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		
		Map<String, Object> data = new HashMap<>();
		ArrayList<User> users=UserDAO.getAll();
		data.put("users", users);
		data.put("loggedInUser", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje usera: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

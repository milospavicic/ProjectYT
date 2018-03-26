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


public class FollowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		ArrayList<String> list = new ArrayList<String>();
		if(loggedInUser!=null) {
			list = UserDAO.userSubs(loggedInUser.getUserName());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("userSubs", list);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Lista kanala koje prati : " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User subscriber = (User) session.getAttribute("loggedInUser");
		
		String channel = request.getParameter("userName");
		String status = request.getParameter("status");
		System.out.println("follow/unfollow "+channel+"	"+subscriber.getUserName()+" "+status);
		if(status.equals("follow")) {
			UserDAO.addSubs(channel, subscriber.getUserName());
		}
		else {
			UserDAO.deleteSubs(channel, subscriber.getUserName());
		}
	}

}

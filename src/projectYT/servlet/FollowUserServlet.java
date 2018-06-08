package projectYT.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import projectYT.dao.UserDAO;
import projectYT.model.User;
import projectYT.tools.UserLogCheck;

public class FollowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User loggedInUser =  UserLogCheck.findCurrentUser(request);

		ArrayList<String> list = new ArrayList<String>();
		if (loggedInUser != null) {
			list = UserDAO.userSubs(loggedInUser.getUserName());
		}
		Map<String, Object> data = new HashMap<>();
		data.put("userSubs", list);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Lista kanala koje prati : " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User subscriber = UserLogCheck.findCurrentUser(request);

		String channel = request.getParameter("userName");
		String status = request.getParameter("status");
		String endStatus = "failed";
		if(subscriber!=null) {
			System.out.println("follow/unfollow " + channel + "	" + subscriber.getUserName() + " " + status);
			
			try {
				endStatus = "Success";
				if (subscriber != null && subscriber.getBlocked() != true) {
					if (status.equals("follow")) {
						UserDAO.addSubs(channel, subscriber.getUserName());
					} else {
						UserDAO.deleteSubs(channel, subscriber.getUserName());
					}
				}
			}catch (Exception e) {
				endStatus = "failed";
			}
		}

		
		Map<String, Object> data = new HashMap<>();
		data.put("endStatus", endStatus);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

}

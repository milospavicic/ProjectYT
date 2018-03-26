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
import projectYT.dao.VideoDAO;
import projectYT.model.User;
import projectYT.model.Video;


public class GetVideosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Ucitavanje videa zapoceto");
		HttpSession session = request.getSession();
		ArrayList<Video> videos= null;
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		if(loggedInUser!= null) {
			System.out.println(loggedInUser.getUserName()+" - loggedInUser");
			if(loggedInUser.getUserType().toString().equals("ADMIN")) {
				videos=VideoDAO.allVideos();
			}
			else {
				videos =VideoDAO.publicVideos();
			}
		}
		else {
			videos =VideoDAO.publicVideos();
		}
		ArrayList<User> topSixChannels = null;
		topSixChannels = UserDAO.getTopSixChannels();
		Map<String, Object> data = new HashMap<>();
		//ArrayList<User> users=UserDAO.getAll();
		data.put("topSixChannels", topSixChannels);
		//data.put("users", users);
		data.put("videos", videos);
		//data.put("user", loggedInUser);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

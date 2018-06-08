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

import projectYT.dao.VideoDAO;
import projectYT.model.User;
import projectYT.model.User.UserType;
import projectYT.model.Video;
import projectYT.tools.UserLogCheck;


public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		String parameter = request.getParameter("parameter");
		int order = Integer.parseInt(request.getParameter("order"));
		String orderBy = "";
		System.out.println(order);
		switch (order) {
		case 1:
			orderBy="ORDER BY videoName DESC";
			break;
		case 2:
			orderBy="ORDER BY videoName ASC";
			break;
		case 3:
			orderBy="ORDER BY owner DESC";
			break;
		case 4:
			orderBy="ORDER BY owner ASC";
			break;
		case 5:
			orderBy="ORDER BY views DESC";
			break;
		case 6:
			orderBy="ORDER BY views ASC";
			break;
		case 7:
			orderBy="ORDER BY datePosted DESC";
			break;
		case 8:
			orderBy="ORDER BY datePosted ASC";
			break;
		}
		boolean videoNameChecked = Boolean.parseBoolean(request.getParameter("videoNameChecked"));
		boolean ownerChecked = Boolean.parseBoolean(request.getParameter("ownerChecked"));
		boolean viewsChecked = Boolean.parseBoolean(request.getParameter("viewsChecked"));
		boolean dateChecked = Boolean.parseBoolean(request.getParameter("dateChecked"));
		boolean commentChecked = Boolean.parseBoolean(request.getParameter("commentChecked"));
		
		ArrayList<Video> videos = new ArrayList<>();
		if(loggedInUser!=null) {
			if(loggedInUser.getUserType()==UserType.USER){
				System.out.println("paramUser");
				videos = VideoDAO.search(parameter, false, orderBy, videoNameChecked, ownerChecked, viewsChecked, dateChecked, commentChecked);
			}else {
				System.out.println("paramAdmin");
				videos = VideoDAO.search(parameter, true, orderBy, videoNameChecked, ownerChecked, viewsChecked, dateChecked, commentChecked);
			}
		}else {
			System.out.println("paramNotLoggedIn");
			videos = VideoDAO.search(parameter, false, orderBy, videoNameChecked, ownerChecked, viewsChecked, dateChecked, commentChecked);
		}
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		data.put("loggedInUser", loggedInUser);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Search: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

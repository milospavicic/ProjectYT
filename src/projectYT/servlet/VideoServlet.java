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

import projectYT.dao.LikeDAO;
import projectYT.dao.UserDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.Video;

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		Video video = VideoDAO.getVideo(videoId);
		
		int tempViews = video.getViews();
		video.setViews(tempViews+1);
		VideoDAO.updateVideo(video);
		Like likedVideo = null;
		if(loggedInUser!=null) {
			likedVideo = LikeDAO.videoLikedByUser(videoId, loggedInUser.getUserName());
		}
		ArrayList<Video> recommended = VideoDAO.getRecommended();
		
		Map<String, Object> data = new HashMap<>();
		data.put("video", video);
		data.put("user", loggedInUser);
		data.put("likedVideo", likedVideo);
		data.put("recommended", recommended);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

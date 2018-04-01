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
import projectYT.model.User;
import projectYT.model.Video;
import projectYT.model.User.UserType;


public class ChannelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String channelName = request.getParameter("channelName");
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		ArrayList<Video> videos = new ArrayList<Video>();
		if(loggedInUser!=null) {
			if(channelName.equals(loggedInUser.getUserName()) || loggedInUser.getUserType()==UserType.ADMIN) {
				videos = VideoDAO.getVideosForUser(channelName, true);
			}else {
				videos = VideoDAO.getVideosForUser(channelName, false);
			}
		}else {
			videos = VideoDAO.getVideosForUser(channelName, false);
		}
		
		ArrayList<Integer> videoIds = LikeDAO.likedVideosByUser(channelName);
		ArrayList<Video> likedVideos = new ArrayList<Video>();
		for (Integer it : videoIds) {
			likedVideos.add(VideoDAO.getVideo(it));
		}
		User channel = UserDAO.getUserByName(channelName);
		
		Map<String, Object> data = new HashMap<>();
		data.put("videos", videos);
		data.put("likedVideos", likedVideos);
		data.put("channel", channel);
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Channel servlet: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

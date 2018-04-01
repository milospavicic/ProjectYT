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
import com.sun.media.jfxmedia.control.VideoFormat;

import projectYT.dao.LikeDAO;
import projectYT.dao.UserDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.Video;
import projectYT.model.Video.Visibility;

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
		String status = request.getParameter("status");
		if(status.equals("edit")) {
			int videoId = Integer.parseInt(request.getParameter("videoId"));
			Video videoForEdit = VideoDAO.getVideo(videoId);
			String title = request.getParameter("title");
			String desc = request.getParameter("desc");
			String picurl = request.getParameter("picurl");
			int visib = Integer.parseInt(request.getParameter("visib"));
			int comm = Integer.parseInt(request.getParameter("comm"));
			int rating = Integer.parseInt(request.getParameter("rating"));
			
			videoForEdit.setVideoName(title);
			videoForEdit.setDescription(desc);
			videoForEdit.setPictureUrl(picurl);
			if(visib==1) 
				videoForEdit.setVisibility(Visibility.PUBLIC);
			else if(visib==2) 
				videoForEdit.setVisibility(Visibility.PRIVATE);
			else 
				videoForEdit.setVisibility(Visibility.UNLISTED);
			
			
			if(comm==1)
				videoForEdit.setCommentsEnabled(true);
			else 
				videoForEdit.setCommentsEnabled(false);
			
			
			if(rating==1)
				videoForEdit.setRatingEnabled(true);
			else
				videoForEdit.setRatingEnabled(false);
			
			VideoDAO.updateVideo(videoForEdit);
		}
	}
}

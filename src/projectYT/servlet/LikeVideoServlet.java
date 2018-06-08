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

import projectYT.dao.LikeDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.Video;
import projectYT.tools.DateConverter;
import projectYT.tools.UserLogCheck;

public class LikeVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		Video video = VideoDAO.getVideo(videoId);
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		
		String status = request.getParameter("status");
		boolean likeOrDislike = false;
		if(status.equals("liked")) likeOrDislike= true;
		System.out.println(likeOrDislike);
		String returnStatus = "failed";
		if(loggedInUser!=null && loggedInUser.getBlocked() != true) {
			Like likeExists = LikeDAO.videoLikedByUser(videoId, loggedInUser.getUserName());
			if(likeExists==null) {
				System.out.println("newLike: "+likeOrDislike);
				Date likeDate = new Date();
				int likeId =LikeDAO.getLikeId();
				Like newLike = new Like(likeId, likeOrDislike, DateConverter.dateToStringForWrite(likeDate), video, null, loggedInUser,false);
				LikeDAO.addLikeDislike(newLike);
				System.out.println("???");
				LikeDAO.addVideoLikeDislike(newLike.getId(),video.getId());
				if(likeOrDislike==false) {
					int tempDis = video.getNumberOfDislikes();
					video.setNumberOfDislikes(tempDis+1);
					returnStatus = "dislike";
				}else {
					int tempLikes = video.getNumberOfLikes();
					video.setNumberOfLikes(tempLikes+1);
					returnStatus = "like";
				}
				VideoDAO.updateVideo(video);
			}
			else {
				if(likeOrDislike==false) {
					if(likeExists.isLikeOrDislike()==true) {
						System.out.println("dislike was like");
						int tempLikes = video.getNumberOfLikes();
						video.setNumberOfLikes(tempLikes-1);
						int tempDis = video.getNumberOfDislikes();
						video.setNumberOfDislikes(tempDis+1);
						returnStatus = "dislike";
					}else {
						System.out.println("dislike was dislike");
						int tempDis = video.getNumberOfDislikes();
						video.setNumberOfDislikes(tempDis-1);
						LikeDAO.deleteLikeVideoComplete(videoId, likeExists.getId());
						returnStatus = "neutral";
					}
				}else {
					if(likeExists.isLikeOrDislike()==false) {
						System.out.println("like was dislike");
						int tempDis = video.getNumberOfDislikes();
						video.setNumberOfDislikes(tempDis-1);
						int tempLikes = video.getNumberOfLikes();
						video.setNumberOfLikes(tempLikes+1);
						returnStatus = "like";
					}else {
						System.out.println("like was like");
						int tempLikes = video.getNumberOfLikes();
						video.setNumberOfLikes(tempLikes-1);
						LikeDAO.deleteLikeVideoComplete(videoId, likeExists.getId());
						returnStatus = "neutral";
					}
				}
				likeExists.setLikeOrDislike(likeOrDislike);
				LikeDAO.updateLike(likeExists);
				VideoDAO.updateVideo(video);
			}
		}
		System.out.println("returnStatus: "+returnStatus);
		Map<String, Object> data = new HashMap<>();
		data.put("video", video);
		data.put("status", returnStatus);
		
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

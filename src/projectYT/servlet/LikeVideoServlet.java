package projectYT.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projectYT.dao.LikeDAO;
import projectYT.dao.UserDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.Video;

public class LikeVideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		Video video = VideoDAO.getVideo(videoId);
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		String status = request.getParameter("status");
		System.out.println(status+" status");
		boolean likeOrDislike = false;
		if(status.equals("liked")) likeOrDislike= true;
		Date likeDate = new Date();
		Like likeExists = LikeDAO.videoLikedByUser(videoId, loggedInUser.getUserName());
		if(likeExists==null) {
			int likeId =LikeDAO.getLikeId();
			Like newLike = new Like(likeId, likeOrDislike, UserDAO.dateToStringForWrite(likeDate), video, null, loggedInUser);
			LikeDAO.addLikeDislike(newLike);
			LikeDAO.addVideoLikeDislike(newLike.getId(),video.getId());
			if(likeOrDislike==false) {
				int tempDis = video.getNumberOfDislikes();
				video.setNumberOfDislikes(tempDis+1);
			}else {
				int tempLikes = video.getNumberOfLikes();
				video.setNumberOfLikes(tempLikes+1);
			}
			VideoDAO.updateVideo(video);
		}
		else {
			if(likeOrDislike==false) {
				int tempDis = video.getNumberOfDislikes();
				video.setNumberOfDislikes(tempDis+1);
				int tempLikes = video.getNumberOfLikes();
				video.setNumberOfLikes(tempLikes-1);
			}else {
				int tempDis = video.getNumberOfDislikes();
				video.setNumberOfDislikes(tempDis-1);
				int tempLikes = video.getNumberOfLikes();
				video.setNumberOfLikes(tempLikes+1);
			}
			likeExists.setLikeOrDislike(likeOrDislike);
			LikeDAO.updateLike(likeExists);
			VideoDAO.updateVideo(video);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

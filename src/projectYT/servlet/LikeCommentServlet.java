package projectYT.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import projectYT.dao.CommentDAO;
import projectYT.dao.LikeDAO;
import projectYT.dao.UserDAO;
import projectYT.model.Comment;
import projectYT.model.Like;
import projectYT.model.User;

public class LikeCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentId = Integer.parseInt(request.getParameter("commentId"));
		Comment comment = CommentDAO.getCommentForId(commentId);
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		User owner = UserDAO.getUserByName(loggedInUser.getUserName());
		String status = request.getParameter("status");
		System.out.println(status+" status");
		boolean likeOrDislike = false;
		if(status.equals("liked")) likeOrDislike= true;
		Date likeDate = new Date();
		Like likeExists = LikeDAO.commentLikedByUser(commentId, loggedInUser.getUserName());
		System.out.println(likeExists);
		if(likeExists==null) {
			int likeId =LikeDAO.getLikeId();
			Like newLike = new Like(likeId, likeOrDislike, UserDAO.dateToStringForWrite(likeDate), null, comment, owner);
			LikeDAO.addLikeDislike(newLike);
			LikeDAO.addCommentLikeDislike(newLike.getId(),comment.getId());
			if(likeOrDislike==false) {
				int tempDis = comment.getDislikeNumber();
				comment.setDislikeNumber(tempDis+1);
			}else {
				int tempLikes = comment.getLikeNumber();
				comment.setLikeNumber(tempLikes+1);
			}
			CommentDAO.updateComment(comment);
		}else {
			if(likeOrDislike==false) {
				int tempDis = comment.getDislikeNumber();
				comment.setDislikeNumber(tempDis+1);
				int tempLikes = comment.getLikeNumber();
				comment.setLikeNumber(tempLikes-1);
			}else {
				int tempDis = comment.getDislikeNumber();
				comment.setDislikeNumber(tempDis-1);
				int tempLikes = comment.getLikeNumber();
				comment.setLikeNumber(tempLikes+1);
			}
			likeExists.setLikeOrDislike(likeOrDislike);
			LikeDAO.updateLike(likeExists);
			CommentDAO.updateComment(comment);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

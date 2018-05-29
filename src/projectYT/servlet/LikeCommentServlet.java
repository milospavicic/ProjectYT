package projectYT.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		
		String status = request.getParameter("status");
		boolean likeOrDislike = false;
		if(status.equals("liked")) likeOrDislike= true;
		
		
		Like likeExists = LikeDAO.commentLikedByUser(commentId, loggedInUser.getUserName());
		String returnStatus = "";
		if(loggedInUser!=null) {
			if(likeExists==null) {
				Date likeDate = new Date();
				int likeId =LikeDAO.getLikeId();
				Like newLike = new Like(likeId, likeOrDislike, UserDAO.dateToStringForWrite(likeDate), null, comment, loggedInUser,false);
				LikeDAO.addLikeDislike(newLike);
				System.out.println("fail?");
				System.out.println(newLike.getId()+"  "+comment.getId());
				LikeDAO.addCommentLikeDislike(newLike.getId(),comment.getId());
				if(likeOrDislike==false) {
					int tempDis = comment.getDislikeNumber();
					comment.setDislikeNumber(tempDis+1);
					returnStatus = "dislike";
				}else {
					int tempLikes = comment.getLikeNumber();
					comment.setLikeNumber(tempLikes+1);
					returnStatus = "like";
				}
				CommentDAO.updateComment(comment);
			}else {
				if(likeOrDislike==false) {
					if(likeExists.isLikeOrDislike()==true) {
						int tempDis = comment.getDislikeNumber();
						comment.setDislikeNumber(tempDis+1);
						int tempLikes = comment.getLikeNumber();
						comment.setLikeNumber(tempLikes-1);
						returnStatus = "dislike";
					}else{
						int tempDis = comment.getDislikeNumber();
						comment.setDislikeNumber(tempDis-1);
						returnStatus = "neutral";
					}
				}else {
					if(likeExists.isLikeOrDislike()==true) {
						int tempDis = comment.getDislikeNumber();
						comment.setDislikeNumber(tempDis-1);
						int tempLikes = comment.getLikeNumber();
						comment.setLikeNumber(tempLikes+1);
						returnStatus = "like";
					}else {
						int tempLikes = comment.getLikeNumber();
						comment.setLikeNumber(tempLikes-1);
						returnStatus = "neutral";
					}
					
				}
				likeExists.setLikeOrDislike(likeOrDislike);
				LikeDAO.updateLike(likeExists);
				CommentDAO.updateComment(comment);
			}
			System.out.println("returnStatus: "+returnStatus);
			Map<String, Object> data = new HashMap<>();
			data.put("comment", comment);
			data.put("status", returnStatus);
			
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(data);
			System.out.println("Zavrseno ucitavanje video: " +jsonData);

			response.setContentType("application/json");
			response.getWriter().write(jsonData);
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

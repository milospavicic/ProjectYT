package projectYT.servlet;

import java.io.IOException;
import java.util.ArrayList;
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
import projectYT.dao.VideoDAO;
import projectYT.model.Comment;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.User.UserType;
import projectYT.model.Video;
import projectYT.tools.DateConverter;

public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("CommentsServlet loading");
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		String columnName = request.getParameter("columnName");
		String ascDes = request.getParameter("ascDes");
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		ArrayList<Comment> comments = CommentDAO.orderComments(videoId, columnName, ascDes);
		ArrayList<Like> likes = new ArrayList<Like>();
		if (loggedInUser != null)
			likes = LikeDAO.commentLikesOnVideoPage(videoId, loggedInUser.getUserName());
		System.out.println(likes.size());
		Map<String, Object> data = new HashMap<>();
		data.put("comments", comments);
		data.put("likes", likes);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje komentara: " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		String status = request.getParameter("status");
		String endStatus = "";
		switch (status) {
		case "new":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					String commentText = request.getParameter("commentText");
					if(commentText.equals("")) {
						return;
					}
					int videoId = Integer.parseInt(request.getParameter("videoId"));
					Video video = VideoDAO.getVideo(videoId);
					int newId = CommentDAO.getCommentId();
					Date newDate = new Date();
					String datePosted = DateConverter.dateToStringForWrite(newDate);
					Comment newCommet = new Comment(newId, commentText, datePosted, loggedInUser, video, 0, 0, false);
					CommentDAO.addComment(newCommet);
					endStatus="newSuccess";
				}
			}catch (Exception e) {
				endStatus="newFailed";
			}
			break;
		case "edit":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					String commentText = request.getParameter("commentText");
					int commentId = Integer.parseInt(request.getParameter("commentId"));
					Comment commentForEdit = CommentDAO.getCommentForId(commentId);
					commentForEdit.setText(commentText);
					CommentDAO.updateComment(commentForEdit);
					endStatus="editSuccess";
				}
			}catch (Exception e) {
				endStatus="editFailed";
			}
			break;
		case "delete":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					int commentId = Integer.parseInt(request.getParameter("commentId"));
					if(loggedInUser.getUserType() == UserType.ADMIN) {
						if(CommentDAO.checkIfDeletable(commentId)) {
							CommentDAO.deleteCommentAdmin(commentId);
						}else {
							Comment commentForDelete = CommentDAO.getCommentForId(commentId);
							commentForDelete.setDeleted(true);
							CommentDAO.updateComment(commentForDelete);
						}
					}else {
						Comment commentForDelete = CommentDAO.getCommentForId(commentId);
						commentForDelete.setDeleted(true);
						CommentDAO.updateComment(commentForDelete);
					}
					endStatus="deleteSuccess";
				}
			}catch (Exception e) {
				endStatus="deleteFailed";
			}
			break;
		default:
			break;
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

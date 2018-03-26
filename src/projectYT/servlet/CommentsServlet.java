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
import projectYT.dao.UserDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Comment;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.Video;


public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("CommentsServlet loading");
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		String columnName = request.getParameter("columnName");
		String ascDes = request.getParameter("ascDes");
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		ArrayList<Comment> comments = CommentDAO.orderComments(videoId, columnName, ascDes);
		ArrayList<Like> likes = new ArrayList<Like>();
		if(loggedInUser!=null)
			likes = LikeDAO.commentLikesOnVideoPage(videoId, loggedInUser.getUserName());
		Map<String, Object> data = new HashMap<>();
		data.put("comments", comments);
		data.put("likes", likes);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje komentara: " +jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int videoId = Integer.parseInt(request.getParameter("videoId"));
		Video video = VideoDAO.getVideo(videoId);
		String commentText = request.getParameter("commentText");
		String loggedInUser = request.getParameter("loggedInUser");
		User user = UserDAO.getUserByName(loggedInUser);
		int newId = CommentDAO.getCommentId();
		Date newDate = new Date();
		String datePosted = UserDAO.dateToStringForWrite(newDate);
		Comment newCommet = new Comment(newId, commentText, datePosted, user, video, 0, 0, false);
		CommentDAO.addComment(newCommet);
	}

}

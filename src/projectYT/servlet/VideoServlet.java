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

import projectYT.dao.LikeDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.Like;
import projectYT.model.User;
import projectYT.model.User.UserType;
import projectYT.model.Video;
import projectYT.model.Video.Visibility;
import projectYT.tools.DateConverter;

public class VideoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");

		int videoId = Integer.parseInt(request.getParameter("videoId"));
		Video video = VideoDAO.getVideo(videoId);
		if(video != null) {
			System.out.println(video + " videoooo by id " + videoId);
			int tempViews = video.getViews();
			video.setViews(tempViews + 1);
			VideoDAO.updateVideo(video);
		}


		ArrayList<Video> recommended = new ArrayList<>();
		Like likedVideo = null;
		if (loggedInUser != null) {
			likedVideo = LikeDAO.videoLikedByUser(videoId, loggedInUser.getUserName());
			if (loggedInUser.getUserType() == UserType.ADMIN) {
				recommended = VideoDAO.getRecommended(true,videoId);
			} else {
				recommended = VideoDAO.getRecommended(false,videoId);
			}
		} else {
			recommended = VideoDAO.getRecommended(false,videoId);
		}

		Map<String, Object> data = new HashMap<>();
		data.put("video", video);
		data.put("loggedInUser", loggedInUser);
		data.put("likedVideo", likedVideo);
		data.put("recommended", recommended);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("loggedInUser");
		
		String status = request.getParameter("status");
		String endStatus = "";
		int newId = 0;
		
		int videoId = 0;
		switch (status) {
		case "edit":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					videoId = Integer.parseInt(request.getParameter("videoId"));
					Video videoForEdit = VideoDAO.getVideo(videoId);
					String desc = request.getParameter("desc");
					String picurl = request.getParameter("picurl");
					int visib = Integer.parseInt(request.getParameter("visib"));
					int comm = Integer.parseInt(request.getParameter("comm"));
					int rating = Integer.parseInt(request.getParameter("rating"));

					videoForEdit.setDescription(desc);
					videoForEdit.setPictureUrl(picurl);
					if (visib == 1)
						videoForEdit.setVisibility(Visibility.PUBLIC);
					else if (visib == 2)
						videoForEdit.setVisibility(Visibility.PRIVATE);
					else
						videoForEdit.setVisibility(Visibility.UNLISTED);

					if (comm == 1)
						videoForEdit.setCommentsEnabled(true);
					else
						videoForEdit.setCommentsEnabled(false);

					if (rating == 1)
						videoForEdit.setRatingEnabled(true);
					else
						videoForEdit.setRatingEnabled(false);

					VideoDAO.updateVideo(videoForEdit);
					endStatus = "editSuccess";
				}
			}catch (Exception e) {
				endStatus = "editFailed";
			}

			break;
		case "block":
			try {

				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					videoId = Integer.parseInt(request.getParameter("videoId"));
					Video videoForBlock = VideoDAO.getVideo(videoId);
					videoForBlock.setBlocked(true);
					VideoDAO.updateVideo(videoForBlock);
					endStatus = "blockSuccess";
				}
			}catch (Exception e) {
				endStatus = "blockFailed";
			}
			break;
		case "unblock":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					videoId = Integer.parseInt(request.getParameter("videoId"));
					Video videoForUnblock = VideoDAO.getVideo(videoId);
					videoForUnblock.setBlocked(false);
					VideoDAO.updateVideo(videoForUnblock);
					endStatus = "unblockSuccess";
				}
			}catch (Exception e) {
				endStatus = "unblockFailed";
			}
			break;
		case "delete":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {

					videoId = Integer.parseInt(request.getParameter("videoId"));
					if(loggedInUser.getUserType() == UserType.ADMIN) {
						if(VideoDAO.checkIfDeletableLikeCondition(videoId) && VideoDAO.checkIfDeletableCommentCondition(videoId)) {
							VideoDAO.deleteVideoAdmin(videoId);
						}else {
							Video videoForDelete = VideoDAO.getVideo(videoId);
							videoForDelete.setDeleted(true);
							VideoDAO.updateVideo(videoForDelete);
						}
					}else {
						Video videoForDelete = VideoDAO.getVideo(videoId);
						videoForDelete.setDeleted(true);
						VideoDAO.updateVideo(videoForDelete);
					}
					endStatus = "deleteSuccess";
				}
			}catch (Exception e) {
				endStatus = "deleteFailed";
			}
			break;
		case "newVideo":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					String newVideoUrl = request.getParameter("videoUrl");
					String newTitle = request.getParameter("title");
					String newDesc = request.getParameter("desc");
					String newPicUrl = request.getParameter("picurl");
					String lod = request.getParameter("lod");
					if(lod.equals("true")) {
						newPicUrl = "pictures/noimage.jpg";
					}
					int newVisib = Integer.parseInt(request.getParameter("visib"));
					int newComm = Integer.parseInt(request.getParameter("comm"));
					int newRating = Integer.parseInt(request.getParameter("rating"));
					Date newDate = new Date();
					String newDateForBase = DateConverter.dateToStringForWrite(newDate);
					if(newVideoUrl.equals("")||newTitle.equals("")) {
						return;
					}
					Video newVideo = new Video(0, newVideoUrl, newPicUrl, newTitle, newDesc, null, false, false, false,
							0, 0, 0, newDateForBase, loggedInUser, false);
					if (newVisib == 1)
						newVideo.setVisibility(Visibility.PUBLIC);
					else if (newVisib == 2)
						newVideo.setVisibility(Visibility.PRIVATE);
					else
						newVideo.setVisibility(Visibility.UNLISTED);

					if (newComm == 1)
						newVideo.setCommentsEnabled(true);
					else
						newVideo.setCommentsEnabled(false);

					if (newRating == 1)
						newVideo.setRatingEnabled(true);
					else
						newVideo.setRatingEnabled(false);
					newId = VideoDAO.newVideo(newVideo);
					System.out.println("newId: " + newId);
					endStatus = "addSuccess";
				}
			}catch (Exception e) {
				endStatus = "addFailed";
			}
			break;
		}
		Map<String, Object> data = new HashMap<>();
		data.put("videoId", newId);
		data.put("endStatus", endStatus);
		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Zavrseno ucitavanje video: " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}
}

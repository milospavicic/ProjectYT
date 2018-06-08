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

import projectYT.dao.UserDAO;
import projectYT.dao.VideoDAO;
import projectYT.model.User;
import projectYT.model.User.UserType;
import projectYT.tools.UserLogCheck;
import projectYT.model.Video;

public class ChannelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String channelName = request.getParameter("channelName");
		User channel = UserDAO.getUserByName(channelName);

		User loggedInUser = UserLogCheck.findCurrentUser(request);

		boolean followThisChannel = false;
		if (loggedInUser != null) {
			ArrayList<String> list = UserDAO.userSubs(loggedInUser.getUserName());
			for (String string : list) {
				if ((channelName).equals(string)) {
					followThisChannel = true;
					break;
				}
			}
		}

		String status = request.getParameter("status");

		ArrayList<Video> videos = new ArrayList<Video>();
		ArrayList<User> following = new ArrayList<User>();
		ArrayList<Video> likedVideos = new ArrayList<Video>();

		switch (status) {
		case "homepage":
			int order = Integer.parseInt(request.getParameter("orderBy"));
			String orderBy = "";
			System.out.println(order + " order");
			switch (order) {
			case 1:
				orderBy = "ORDER BY datePosted DESC";
				break;
			case 2:
				orderBy = "ORDER BY datePosted ASC";
				break;
			case 3:
				orderBy = "ORDER BY views DESC";
				break;
			case 4:
				orderBy = "ORDER BY views ASC";
				break;

			}

			if (loggedInUser != null) {
				if (channelName.equals(loggedInUser.getUserName())
						|| (loggedInUser.getUserType() == UserType.ADMIN && loggedInUser.getBlocked() == false)) {
					videos = VideoDAO.getVideosForUser(channelName, true, orderBy);
					System.out.println("option1");
				} else {
					videos = VideoDAO.getVideosForUser(channelName, false, orderBy);
					System.out.println("option2");
				}
			} else {
				videos = VideoDAO.getVideosForUser(channelName, false, orderBy);
				System.out.println("option3");
			}
			break;

		case "following":
			following = UserDAO.userFollowing(channelName);
			break;
		case "likes":
			if (loggedInUser != null) {
				if (channelName.equals(loggedInUser.getUserName()) || loggedInUser.getUserType() == UserType.ADMIN) {
					likedVideos = VideoDAO.getVideosLikedByUser(channelName, true);
				} else {
					likedVideos = VideoDAO.getVideosLikedByUser(channelName, false);
				}
			} else {
				likedVideos = VideoDAO.getVideosLikedByUser(channelName, false);
			}
			break;
		}

		Map<String, Object> data = new HashMap<>();
		data.put("loggedInUser", loggedInUser);
		data.put("videos", videos);
		data.put("likes", likedVideos);
		data.put("channel", channel);
		data.put("following", following);
		data.put("followThisChannel", followThisChannel);

		ObjectMapper mapper = new ObjectMapper();
		String jsonData = mapper.writeValueAsString(data);
		System.out.println("Channel servlet: " + jsonData);

		response.setContentType("application/json");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = UserLogCheck.findCurrentUser(request);
		String channelName = request.getParameter("channelName");
		User channel = UserDAO.getUserByName(channelName);
		System.out.println(channel);

		String status = request.getParameter("status");
		System.out.println(status + " - Status");
		String endStatus = "";
		switch (status) {
		case "edit":
			try {

				String basicProfileUrl = "pictures/noprofileimage.png";

				String firstName = request.getParameter("firstName");
				String lastName = request.getParameter("lastName");
				String password = request.getParameter("password");
				String email = request.getParameter("email");
				String profileUrl = request.getParameter("profileUrl");
				String channelDescription = request.getParameter("channelDescription");
				String lol = request.getParameter("lol");
				String newFile = request.getParameter("newFile");
				System.out.println(lol + " - lol");

				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					if (loggedInUser.getUserType() == UserType.ADMIN) {
						int userType = Integer.parseInt(request.getParameter("userType"));
						channel.setFirstName(firstName);
						channel.setLastName(lastName);
						channel.setPassword(password);
						channel.setEmail(email);
						channel.setProfileUrl(profileUrl);
						if (lol.equals("true")) {
							channel.setLol(true);
							if (newFile.equals("true")) {
								session.setAttribute("channel", channel);
							}
						} else {
							channel.setLol(false);
							if (profileUrl.equals("")) {
								channel.setProfileUrl(basicProfileUrl);
							} else {
								channel.setProfileUrl(profileUrl);
							}
						}
						if (channelDescription == "") {
							channel.setChannelDescription(null);
						} else {
							channel.setChannelDescription(channelDescription);
						}
						if (userType == 1) {
							channel.setUserType(UserType.USER);
						} else {
							channel.setUserType(UserType.ADMIN);
						}
						UserDAO.update(channel);
					} else {
						channel.setFirstName(firstName);
						channel.setLastName(lastName);
						channel.setPassword(password);
						channel.setEmail(email);
						channel.setProfileUrl(profileUrl);
						if (channel.isLol() == true && lol.equals("true")) {
							session.setAttribute("channel", channel);
						} else if (channel.isLol() != true && lol.equals("true")) {
							channel.setLol(true);
							session.setAttribute("channel", channel);
						} else {
							channel.setLol(false);
							if (profileUrl.equals("")) {
								channel.setProfileUrl(basicProfileUrl);
							}
						}
						if (channelDescription == "") {
							channel.setChannelDescription(null);
						} else {
							channel.setChannelDescription(channelDescription);
						}
						UserDAO.update(channel);
					}
					endStatus = "editSuccess";
				}
			} catch (Exception e) {
				endStatus = "editFailed";
			}
			break;
		case "delete":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					if(loggedInUser.getUserType() == UserType.ADMIN) {
						if(UserDAO.checkIfDeletableCommentedVideos(channelName) && UserDAO.checkIfDeletableHasVideos(channelName) &&
								UserDAO.checkIfDeletableLikedSomething(channelName) && UserDAO.checkIfDeletableFollowing(channelName)) {
							UserDAO.deleteUserAdmin(channelName);
							
						}else {
							channel.setDeleted(true);
							UserDAO.update(channel);
						}
					}else {
						channel.setDeleted(true);
						UserDAO.update(channel);
					}
					if(loggedInUser.getUserName().equals(channelName)) {
						request.getSession().invalidate();
						loggedInUser = null;
					}
					endStatus = "deleteSuccess";
				}
			} catch (Exception e) {
				endStatus = "deleteFailed";
			}
			break;
		case "block":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					channel.setBlocked(true);
					UserDAO.update(channel);
					endStatus = "blockSuccess";
				}
			} catch (Exception e) {
				endStatus = "blockFailed";
			}
			break;
		case "unblock":
			try {
				if (loggedInUser != null && loggedInUser.getBlocked() != true) {
					channel.setBlocked(false);
					UserDAO.update(channel);
					endStatus = "unblockSuccess";
				}
			} catch (Exception e) {
				endStatus = "unblockFailed";
			}
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

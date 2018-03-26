package projectYT.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import projectYT.model.User;
import projectYT.model.Video;
import projectYT.model.Video.Visibility;

public class VideoDAO {

	public static ArrayList<Video> publicVideos() {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE visibility = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=UserDAO.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.getUserByName(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	public static ArrayList<Video> allVideos() {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE (visibility = ? OR visibility = ?)  AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setString(2, "PRIVATE");
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=UserDAO.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.getUserByName(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	public static ArrayList<Video> getRecommended() {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE (visibility = ? OR visibility = ?)  AND deleted = ? ORDER BY RAND() LIMIT 6";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setString(2, "PRIVATE");
			pstmt.setBoolean(3, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=UserDAO.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.getUserByName(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				videos.add(newVideo);
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return videos;
	}
	public static Video getVideo(int id) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String videoUrl = rset.getString(index++);
				String pictureUrl = rset.getString(index++);
				String videoName = rset.getString(index++);
				String description = rset.getString(index++);
				Visibility visibility = Visibility.valueOf(rset.getString(index++));
				boolean blocked = rset.getBoolean(index++);
				boolean commentsEnabled = rset.getBoolean(index++);
				boolean ratingEnabled = rset.getBoolean(index++);
				int numberOfLikes = rset.getInt(index++);
				int numberOfDislikes = rset.getInt(index++);
				int views = rset.getInt(index++);
				Date d = rset.getDate(index++);
				String datePosted=UserDAO.dateToString(d);
				String user = rset.getString(index++);
				User owner = UserDAO.getUserByName(user);
				boolean deleted = rset.getBoolean(index++);
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);;
				return newVideo;
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return null;
	}
	public static int getVideoCountForUser(String userName) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM video WHERE owner LIKE ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 1;
				int videoCount = rset.getInt(index++);
				return videoCount;
			}

		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
			try {
				rset.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return 0;
	}
	public static boolean updateVideo(Video video) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE video SET videoName = ?, description = ?, visibility = ?, commentsEnabled = ?, ratingEnabled = ?, blocked = ?, deleted = ?,views = ?, numberOfLikes = ?, numberOfDislikes = ?  WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, video.getVideoName());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isCommentsEnabled());
			pstmt.setBoolean(index++, video.isRatingEnabled());
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setBoolean(index++, video.isDeleted());
			pstmt.setInt(index++, video.getViews());
			pstmt.setInt(index++, video.getNumberOfLikes());
			pstmt.setInt(index++, video.getNumberOfDislikes());
			pstmt.setInt(index++, video.getId());

			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException ex1) {
				ex1.printStackTrace();
			}
		}
		return false;
	}
}

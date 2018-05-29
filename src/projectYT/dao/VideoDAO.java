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

	public static int getVideoId() {
		Connection conn = ConnectionMenager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM video";
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
		
			if (rset.next()) {
				id=rset.getInt(1);
				
			}
			id++;
			return id;
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
	public static ArrayList<Video> publicVideos() {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE visibility = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "PUBLIC");
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			pstmt.setBoolean(5, false);
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
			String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE V.deleted = ? AND U.deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
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
	public static ArrayList<Video> getRecommended(boolean userType) {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			if(userType==false) {
				String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE visibility = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ? ORDER BY RAND() LIMIT 6";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "PUBLIC");
				pstmt.setBoolean(2, false);
				pstmt.setBoolean(3, false);
				pstmt.setBoolean(4, false);
				pstmt.setBoolean(5, false);
			}else {
				String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE V.deleted = ? AND U.deleted = ? ORDER BY RAND() LIMIT 6";
				pstmt = conn.prepareStatement(query);
				pstmt.setBoolean(1, false);
				pstmt.setBoolean(2, false);
			}
			
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
			String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE id = ? AND V.deleted = ? AND U.deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
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
				Video newVideo = new Video(id, videoUrl, pictureUrl, videoName, description, visibility, blocked, commentsEnabled, ratingEnabled, numberOfLikes, numberOfDislikes, views, datePosted, owner, deleted);
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
	public static ArrayList<Video> getVideosForUser(String userName, boolean queryType, String orderBy){
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			if(queryType==false) {
				String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE visibility = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ? AND owner = ? "+orderBy;
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "PUBLIC");
				pstmt.setBoolean(2, false);
				pstmt.setBoolean(3, false);
				pstmt.setBoolean(4, false);
				pstmt.setBoolean(5, false);
				pstmt.setString(6, userName);
			}else {
				String query = "SELECT * FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE V.deleted = ? AND U.deleted = ? AND owner = ? "+orderBy;
				pstmt = conn.prepareStatement(query);
				pstmt.setBoolean(1, false);
				pstmt.setBoolean(2, false);
				pstmt.setString(3, userName);
			}
			
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
			return videos;
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
	
	public static ArrayList<Video> getVideosLikedByUser(String userName,boolean queryType){
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Video> videos = new ArrayList<Video>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			if(queryType==false) {
				String query = "SELECT v.* FROM likedislikevideo AS A INNER JOIN likedislike AS B ON A.likeId=B.id INNER JOIN video as V ON A.videoId=V.id INNER JOIN users AS U ON V.owner=U.userName WHERE V.visibility = ? AND  V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ?  AND B.owner=? ";
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, "PUBLIC");
				pstmt.setBoolean(2, false);
				pstmt.setBoolean(3, false);
				pstmt.setBoolean(4, false);
				pstmt.setBoolean(5, false);
				pstmt.setString(6, userName);
			}else {
				String query = "SELECT v.* FROM likedislikevideo AS A INNER JOIN likedislike AS B ON A.likeId=B.id INNER JOIN video as V ON A.videoId=V.id INNER JOIN users AS U ON V.owner=U.userName WHERE V.deleted = ? AND U.deleted = ? AND B.owner=?";
				pstmt = conn.prepareStatement(query);
				pstmt.setBoolean(1, false);
				pstmt.setBoolean(2, false);
				pstmt.setString(3, userName);
			}
			
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
			return videos;
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
	
	public static ArrayList<Video> search(String parameter,boolean userType,String orderBy,boolean vnC,boolean onC,boolean vC,boolean dC, boolean cC){
		ArrayList<Video> videos = new ArrayList<>();
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			if(userType==false) {
				String query = "SELECT DISTINCT V.* FROM video AS V INNER JOIN users AS U ON V.owner=U.userName LEFT OUTER JOIN comment AS C ON V.id=C.videoId WHERE visibility = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ? ";
				
				if(vnC==true) {
					query += " AND videoName LIKE ? ";
					System.out.println("and videoName");
				}
				if(onC==true) {
					if(vnC==true) {
						query += " OR V.owner LIKE ? ";
						System.out.println("OR owner");
					}
					else {
						query += " AND V.owner LIKE ? ";
						System.out.println("and owner");
					}
				}
				if(vC==true) {
					if(onC==false && vnC==false){
						query += " AND views LIKE ? ";
						System.out.println("and views");
					}else {
						query += " OR views LIKE ? ";
						System.out.println("OR views");
					}
				}
				if(dC==true) {
					if(onC==false && vnC==false && vC==false){
						query += " AND V.datePosted LIKE ? ";
						System.out.println("and datePosted");
					}else {
						query += " OR V.datePosted LIKE ? ";
						System.out.println("OR datePosted");
					}
				}
				if(cC==true) {
					if(onC==false && vnC==false && vC==false && dC==false){
						query += " AND C.text LIKE ? ";
						System.out.println("and text");
					}else {
						query += " OR C.text LIKE ? ";
						System.out.println("OR text");
					}
				}
				query += orderBy;
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "PUBLIC");
				pstmt.setBoolean(index++, false);
				pstmt.setBoolean(index++, false);
				pstmt.setBoolean(index++, false);
				pstmt.setBoolean(index++, false);
				if(vnC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(onC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(vC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(dC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(cC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
			}else {
				String query = "SELECT DISTINCT V.* FROM video AS V INNER JOIN users AS U ON V.owner=U.userName LEFT OUTER JOIN comment AS C ON V.id=C.videoId WHERE V.deleted = ? AND U.deleted = ?";
				if(vnC==true) {
					query += " AND videoName LIKE ? ";
					System.out.println("and videoName");
				}
				if(onC==true) {
					if(vnC==true) {
						query += " OR V.owner LIKE ? ";
						System.out.println("OR owner");
					}
					else {
						query += " AND V.owner LIKE ? ";
						System.out.println("and owner");
					}
				}
				if(vC==true) {
					if(onC==false && vnC==false){
						query += " AND views LIKE ? ";
						System.out.println("and views");
					}else {
						query += " OR views LIKE ? ";
						System.out.println("OR views");
					}
				}
				if(dC==true) {
					if(onC==false && vnC==false && vC==false){
						query += " AND V.datePosted LIKE ? ";
						System.out.println("and datePosted");
					}else {
						query += " OR V.datePosted LIKE ? ";
						System.out.println("OR datePosted");
					}
				}
				if(cC==true) {
					if(onC==false && vnC==false && vC==false && dC==false){
						query += " AND C.text LIKE ? ";
						System.out.println("and text");
					}else {
						query += " OR C.text LIKE ? ";
						System.out.println("OR text");
					}
				}
				
				query += orderBy;
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setBoolean(index++, false);
				pstmt.setBoolean(index++, false);
				if(vnC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(onC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(vC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(dC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
				if(cC==true) {
					pstmt.setString(index++, "%"+parameter+"%");
				}
			}
			System.out.println(pstmt);
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
			return videos;
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
			String query = "SELECT COUNT(*) FROM video AS V INNER JOIN users AS U ON V.owner=U.userName WHERE owner = ? AND V.deleted = ? AND V.blocked = ? AND U.deleted = ? AND U.blocked = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2, false);
			pstmt.setBoolean(3, false);
			pstmt.setBoolean(4, false);
			pstmt.setBoolean(5, false);
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
	public static boolean newVideo(Video video){
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO video(videoUrl,pictureUrl,videoName,description,visibility,blocked,commentsEnabled,ratingEnabled,numberOfLikes,numberOfDislikes,views,datePosted,owner,deleted)"
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, video.getVideoUrl());
			pstmt.setString(index++, video.getPictureUrl());
			pstmt.setString(index++, video.getVideoName());
			pstmt.setString(index++, video.getDescription());
			pstmt.setString(index++, video.getVisibility().toString());
			pstmt.setBoolean(index++, video.isBlocked());
			pstmt.setBoolean(index++, video.isCommentsEnabled());
			pstmt.setBoolean(index++, video.isRatingEnabled());
			pstmt.setInt(index++, video.getNumberOfLikes());
			pstmt.setInt(index++, video.getNumberOfDislikes());
			pstmt.setInt(index++, video.getViews());
			Date myDate=UserDAO.stringToDateForWrite(video.getDatePosted());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++,date);
			pstmt.setString(index++, video.getOwner().getUserName());
			pstmt.setBoolean(index++, video.isDeleted());
	
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

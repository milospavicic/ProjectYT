package projectYT.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import projectYT.model.Comment;
import projectYT.model.User;
import projectYT.model.Video;

public class CommentDAO {
	public static int getCommentId() {
		Connection conn = ConnectionMenager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM comment";
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
	
	public static Comment getCommentForId(int id) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comment WHERE id = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String text = rset.getString(index++);
				String owner = rset.getString(index++);
				int videoId=rset.getInt(index++);
				Date d= rset.getDate(index++);
				int likeNumber=rset.getInt(index++);
				int dislikeNumber=rset.getInt(index++);
				boolean deleted=rset.getBoolean(index++);
				User user = UserDAO.getUserByName(owner);
				Video video=VideoDAO.getVideo(videoId);
				String datePosted=UserDAO.dateToString(d);
				return new Comment(id, text, datePosted, user, video, likeNumber, dislikeNumber, deleted);
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
	
	public static ArrayList<Comment> getComments(int videosId) {
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Comment> comments = new ArrayList<Comment>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comment WHERE videoId = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videosId);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String text = rset.getString(index++);
				String owner = rset.getString(index++);
				int videoId=rset.getInt(index++);
				Date d= rset.getDate(index++);
				int likeNumber=rset.getInt(index++);
				int dislikeNumber=rset.getInt(index++);
				boolean deleted=rset.getBoolean(index++);
				User user = UserDAO.getUserByName(owner);
				Video video=VideoDAO.getVideo(videoId);
				String datePosted=UserDAO.dateToString(d);
				
				if(user == null || video == null) {
					continue;
				}
				else {
					Comment newComment=new Comment(id, text, datePosted, user, video, likeNumber, dislikeNumber, deleted);
					comments.add(newComment);
				}
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
		return comments;
	}
	public static ArrayList<Comment> orderComments(int videosId,String columnName,String ascDes) {
		
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Comment> comments = new ArrayList<Comment>();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM comment WHERE videoId = ? AND deleted = ? ORDER BY "+ columnName+" "+ascDes ;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, videosId);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				String text = rset.getString(index++);
				String owner = rset.getString(index++);
				int videoId=rset.getInt(index++);
				Date d= rset.getDate(index++);
				int likeNumber=rset.getInt(index++);
				int dislikeNumber=rset.getInt(index++);
				boolean deleted=rset.getBoolean(index++);
				User user = UserDAO.getUserByName(owner);
				Video video=VideoDAO.getVideo(videoId);
				String datePosted=UserDAO.dateToString(d);
				if(user == null || video == null) {
					continue;
				}
				else {
					Comment newComment=new Comment(id, text, datePosted, user, video, likeNumber, dislikeNumber, deleted);
					comments.add(newComment);
				}
				
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
		return comments;
	}
	
	public static boolean addComment(Comment comment) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO comment (text, owner, videoId, datePosted, likeNumber, dislikeNumber, deleted) VALUES (?, ?, ?, ?, ?, ?, ? )";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, comment.getText());
			pstmt.setString(index++, comment.getUser().getUserName());
			pstmt.setInt(index++, comment.getVideo().getId());
			Date myDate=UserDAO.stringToDateForWrite(comment.getDatePosted());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setInt(index++, comment.getLikeNumber());
			pstmt.setInt(index++, comment.getDislikeNumber());
			pstmt.setBoolean(index++, comment.isDeleted());
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
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

	public static boolean updateComment(Comment comment) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE comment SET likeNumber =?, dislikeNumber = ?, deleted = ?, datePosted = ?, text = ? WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, comment.getLikeNumber());
			pstmt.setInt(2, comment.getDislikeNumber());
			pstmt.setBoolean(3, comment.isDeleted());
			Date d1 =UserDAO.stringToDate(comment.getDatePosted());
			String dd=UserDAO.dateToStringForWrite(d1);
			Date d=UserDAO.stringToDateForWrite(dd);
			java.sql.Date date=new java.sql.Date(d.getTime());
			pstmt.setDate(4, date);
			pstmt.setString(5, comment.getText());
			pstmt.setInt(6, comment.getId());
		
			return pstmt.executeUpdate() == 1;
		} catch (SQLException ex) {
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

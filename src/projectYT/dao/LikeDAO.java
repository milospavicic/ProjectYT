package projectYT.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import projectYT.model.Like;

public class LikeDAO {

	public static int getLikeId() {
		Connection conn = ConnectionMenager.getConnection();
		int id=0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT MAX(id) FROM likeDislike";
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
	public static Like videoLikedByUser(int videoId,String user) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM projectyt.likedislikevideo JOIN projectyt.likedislike on likeDislikevideo.likeId = likeDislike.id WHERE owner=? AND videoId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, user);
			pstmt.setInt(2, videoId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				int videosId=rset.getInt(index++);
				int likeId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				String date=UserDAO.dateToString(d);
				Like likedVideo = new Like(likeId, isLike, date, VideoDAO.getVideo(videosId), null, UserDAO.getUserByName(owner));
				return likedVideo;
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
	public static boolean addLikeDislike(Like likeDislike) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO likeDislike(liked,likeDate,owner) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, likeDislike.isLikeOrDislike());
			Date myDate=UserDAO.stringToDateForWrite(likeDislike.getLikeDate());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(2, date);
			pstmt.setString(3, likeDislike.getOwner().getUserName());
			
			return pstmt.executeUpdate() == 1;


		}  catch (Exception ex) {
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
	public static boolean addVideoLikeDislike(int likeId, int videoId) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			
			String query="INSERT INTO likeDislikeVideo(likeId,videoId) VALUES(?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, likeId);
			pstmt.setInt(2, videoId);
			 return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
			try {conn.rollback();} catch (SQLException ex1) {ex1.printStackTrace();}
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}

			try {conn.setAutoCommit(true);} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	public static boolean updateLike(Like l) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likeDislike SET liked = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, l.isLikeOrDislike());
			pstmt.setInt(2, l.getId());
			
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
	public static Like commentLikedByUser(int commentId, String loggedInUser) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM projectyt.likedislikecomment JOIN projectyt.likedislike on likeDislikecomment.likeId = likeDislike.id WHERE owner=? AND commentId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loggedInUser);
			pstmt.setInt(2, commentId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				int commentsId=rset.getInt(index++);
				int likeId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				String date=UserDAO.dateToString(d);
				Like likedComment = new Like(likeId, isLike, date, null, CommentDAO.getCommentForId(commentsId), UserDAO.getUserByName(owner));
				return likedComment;
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
	public static boolean addCommentLikeDislike(int id, int id2) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			
			String query="INSERT INTO likeDislikeComment(likeId,commentId) VALUES(?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, id2);
			 return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
			try {conn.rollback();} catch (SQLException ex1) {ex1.printStackTrace();}
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}

			try {conn.setAutoCommit(true);} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return false;
	}
	public static ArrayList<Like> commentLikesOnVideoPage(int videoId, String userName){
		Connection conn = ConnectionMenager.getConnection();
		ArrayList<Like> likedComments = new ArrayList<Like>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT likeId,commentId,liked,likeDate,projectyt.likedislike.owner FROM projectyt.likedislikecomment JOIN projectyt.likedislike on likeDislikecomment.likeId = likeDislike.id JOIN projectyt.comment on comment.id = commentId WHERE projectyt.likedislike.owner =?  AND projectyt.comment.videoId =? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setInt(2, videoId);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int likeId=rset.getInt(index++);
				int commentsId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				String date=UserDAO.dateToString(d);
				Like likedComment = new Like(likeId, isLike, date, null, CommentDAO.getCommentForId(commentsId), UserDAO.getUserByName(owner));
				likedComments.add(likedComment);
			}
			return likedComments;
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
}

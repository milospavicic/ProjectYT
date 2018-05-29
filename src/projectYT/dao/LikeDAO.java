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
			String query = "SELECT likedislike.* FROM projectyt.likedislikevideo JOIN projectyt.likedislike on likeDislikevideo.likeId = likeDislike.id WHERE likeDislikevideo.deleted = ? AND likeDislike.deleted = ? AND owner=? AND videoId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setBoolean(2, false);
			pstmt.setString(3, user);
			pstmt.setInt(4, videoId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 1;
				int likeId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);
				String date=UserDAO.dateToString(d);
				
				Like likedVideo = new Like(likeId, isLike, date, VideoDAO.getVideo(videoId), null, UserDAO.getUserByName(owner),deleted);
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
			String query = "INSERT INTO likeDislike(liked,likeDate,owner,deleted) VALUES(?, ?, ?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, likeDislike.isLikeOrDislike());
			Date myDate=UserDAO.stringToDateForWrite(likeDislike.getLikeDate());
			java.sql.Date date=new java.sql.Date(myDate.getTime());
			pstmt.setDate(2, date);
			pstmt.setString(3, likeDislike.getOwner().getUserName());
			pstmt.setBoolean(4, likeDislike.isDeleted());
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
		System.out.println("likeId: "+ likeId+" videoId: "+videoId);
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query="INSERT INTO likeDislikeVideo(likeId,videoId,deleted) VALUES(?, ?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, likeId);
			pstmt.setInt(2, videoId);
			pstmt.setBoolean(3, false);
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
	public static void deleteLikeVideoComplete(int videoId,int likeId) {
		deleteVideoLike(videoId,likeId);
		deleteLike(likeId);
	}
	public static void deleteLikeCommentComplete(int videoId,int likeId) {
		deleteCommentLike(videoId,likeId);
		deleteLike(likeId);
	}
	public static boolean deleteLike(int likeId) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likeDislike SET deleted = ? WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, likeId);
			
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
	public static boolean deleteVideoLike(int videoId, int likeId) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likedislikevideo SET deleted = ? WHERE likeId=? AND videoID = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, likeId);
			pstmt.setInt(3, videoId);
			
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
	public static boolean deleteCommentLike(int videoId, int likeId) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE likedislikecomment SET deleted = ? WHERE likeId=? AND commentId = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, true);
			pstmt.setInt(2, likeId);
			pstmt.setInt(3, videoId);
			
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
			String query = "SELECT * FROM projectyt.likedislikecomment JOIN projectyt.likedislike on likeDislikecomment.likeId = likeDislike.id WHERE likeDislikecomment.deleted = ? AND likeDislike.deleted=? AND owner=? AND commentId=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setBoolean(2, false);
			pstmt.setString(3, loggedInUser);
			pstmt.setInt(4, commentId);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				int commentsId=rset.getInt(index++);
				int likeId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);
				String date=UserDAO.dateToString(d);
				Like likedComment = new Like(likeId, isLike, date, null, CommentDAO.getCommentForId(commentsId), UserDAO.getUserByName(owner),deleted);
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
			
			String query="INSERT INTO likeDislikeComment(likeId,commentId,deleted) VALUES(?, ?, ?)";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, id2);
			pstmt.setBoolean(3, false);
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
			String query = "SELECT likedislike.*,likedislikecomment.commentId FROM projectyt.likedislikecomment JOIN projectyt.likedislike on likeDislikecomment.likeId = likeDislike.id JOIN projectyt.comment on comment.id = commentId WHERE likeDislikecomment.deleted = ? AND likeDislike.deleted=? AND projectyt.likedislike.owner =?  AND projectyt.comment.videoId =? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setBoolean(2, false);
			pstmt.setString(3, userName);
			pstmt.setInt(4, videoId);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				int index = 1;
				int likeId=rset.getInt(index++);
				boolean isLike=rset.getBoolean(index++);
				Date d=rset.getDate(index++);
				String owner=rset.getString(index++);
				boolean deleted = rset.getBoolean(index++);
				int commentsId=rset.getInt(index++);
				String date=UserDAO.dateToString(d);
				Like likedComment = new Like(likeId, isLike, date, null, CommentDAO.getCommentForId(commentsId), UserDAO.getUserByName(owner),deleted);
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

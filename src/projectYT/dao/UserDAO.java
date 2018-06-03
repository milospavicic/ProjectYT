package projectYT.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import projectYT.model.User;
import projectYT.model.User.UserType;

public class UserDAO {
	public static User usernameTaken(String userName) {

		Connection conn = ConnectionMenager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE userName = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				UserType userType = UserType.valueOf(rset.getString(index++));
				Date date= rset.getDate(index++);
				String registrationDate = dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				String profileUrl = rset.getString(index++);
				boolean lol = rset.getBoolean(index++);
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
				pstmt.close();
				rset.close();
				return newUser;
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	
	public static User getUserByName(String userName) {

		Connection conn = ConnectionMenager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE userName = ? AND deleted = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			pstmt.setBoolean(2, false);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				int index = 2;
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				UserType userType = UserType.valueOf(rset.getString(index++));
				Date date= rset.getDate(index++);
				String registrationDate = dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				String profileUrl = rset.getString(index++);
				boolean lol = rset.getBoolean(index++);
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
				pstmt.close();
				rset.close();
				
				newUser.setSubscriberNames(getSubsUserName(userName));
				//newUser.setSubscribers(findSubscribers(newUser.getSubscriberNames()));
				newUser.subsNumber = getSubsNumber(userName);
				newUser.videosCount = VideoDAO.getVideoCountForUser(userName);
				return newUser;
			}
		} catch (SQLException ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	public static ArrayList<User> getAll(String orderBy) {
		Connection conn = ConnectionMenager.getConnection();
		
		ArrayList<User> users=new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE deleted = ?" + orderBy;
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String userName = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				UserType userType = UserType.valueOf(rset.getString(index++));
				Date date= rset.getDate(index++);
				String registrationDate = dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				String profileUrl = rset.getString(index++);
				boolean lol = rset.getBoolean(index++);
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
				
				users.add(newUser);
			}
			for (User user : users) {
				//System.out.println(user.getUserName());
				user.setSubscriberNames(getSubsUserName(user.getUserName()));
				//System.out.println(user.getSubscriberNames());
				//user.setSubscribers(findSubscribers(user.getSubscriberNames()));
				user.subsNumber = getSubsNumber(user.getUserName());
				user.videosCount = VideoDAO.getVideoCountForUser(user.getUserName());
			}
			return users;

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
	public static ArrayList<User> getTopSixChannels() {
		Connection conn = ConnectionMenager.getConnection();
		
		ArrayList<User> users=new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT DISTINCT(s.mainUser), COUNT(s.mainUser) AS broj, u.deleted FROM subscribe AS s INNER JOIN users AS u ON s.mainUser=u.userName\r\n" + 
					"WHERE u.deleted = ?\r\n" + 
					"GROUP BY s.mainUser\r\n" + 
					"ORDER BY broj DESC";
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				if(users.size()==6)
					return users;
				int index = 1;
				String userName = rset.getString(index++);
				User newUser = getUserByName(userName);
				users.add(newUser);
			}
			return users;

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
	public static ArrayList<User> userFollowing(String mainUserName) {
		Connection conn = ConnectionMenager.getConnection();
		
		ArrayList<User> users=new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM subscribe WHERE subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mainUserName);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String userName = rset.getString(index++);
				users.add(UserDAO.getUserByName(userName));
			}
			return users;

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
	public static ArrayList<User> searchUsers(String parameter,String orderBy) {
		Connection conn = ConnectionMenager.getConnection();
		
		ArrayList<User> users=new ArrayList<User>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM users WHERE deleted = ? AND (userName LIKE ? OR firstName LIKE ? OR lastName LIKE ? OR email LIKE ? OR userType LIKE ?)" + orderBy;
			pstmt = conn.prepareStatement(query);
			pstmt.setBoolean(1, false);
			pstmt.setString(2, "%"+parameter+"%");
			pstmt.setString(3, "%"+parameter+"%");
			pstmt.setString(4, "%"+parameter+"%");
			pstmt.setString(5, "%"+parameter+"%");
			pstmt.setString(6, "%"+parameter+"%");
			System.out.println(pstmt);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				String userName = rset.getString(index++);
				String password = rset.getString(index++);
				String firstName = rset.getString(index++);
				String lastName = rset.getString(index++);
				String email = rset.getString(index++);
				String channelDescription = rset.getString(index++);
				UserType userType = UserType.valueOf(rset.getString(index++));
				Date date= rset.getDate(index++);
				String registrationDate = dateToString(date);
				boolean blocked = rset.getBoolean(index++);
				boolean deleted = rset.getBoolean(index++);
				String profileUrl = rset.getString(index++);
				boolean lol = rset.getBoolean(index++);
				User newUser = new User(userName, password, firstName, lastName, email, channelDescription, registrationDate, blocked, null, null, null, userType,deleted,profileUrl,lol);
				
				users.add(newUser);
			}
			for (User user : users) {
				user.setSubscriberNames(getSubsUserName(user.getUserName()));
				user.subsNumber = getSubsNumber(user.getUserName());
				user.videosCount = VideoDAO.getVideoCountForUser(user.getUserName());
			}
			return users;

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
	
	public static boolean addUser(User user) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (userName, password, firstName, lastName, email, channelDescription, userType, registrationDate, blocked,deleted,profileUrl,lol) VALUES (?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getUserName());
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getChannelDescription());
			pstmt.setString(index++, user.getUserType().toString());
			Date tempDate= stringToDateForWrite(user.getRegistrationDate());
			java.sql.Date date=new java.sql.Date(tempDate.getTime());
			pstmt.setDate(index++, date);
			pstmt.setBoolean(index++, user.getBlocked());
			pstmt.setBoolean(index++, user.getDeleted());
			pstmt.setString(index++, user.getProfileUrl());
			pstmt.setBoolean(index++, user.isLol());
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
	public static ArrayList<String> getSubsUserName(String userName) {
		ArrayList<String> subscriberNames=new ArrayList<String>();
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT * FROM subscribe WHERE mainUser = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery(); 
			while (rset.next()) {
				int index2 = 2;
				String subscriber = rset.getString(index2++);
				//System.out.println("Pretrazivanje, user= "+userName+", sub= "+subscriber);
				subscriberNames.add(subscriber);
			}

			return subscriberNames;
			
		} catch (Exception ex) {
			System.out.println("Greska u SQL upitu!");
			ex.printStackTrace();
		} finally {
			try {pstmt.close();} catch (SQLException ex1) {ex1.printStackTrace();}
			try {rset.close();} catch (SQLException ex1) {ex1.printStackTrace();}
		}
		return null;
	}
	public static ArrayList<User> findSubscribers(ArrayList<String> subscribersUserName) {
		ArrayList<User> list = new ArrayList<User>();
		if (subscribersUserName.isEmpty()) {
			return null;
		} else {
			for (String userName : subscribersUserName) {
				list.add(getUserByName(userName));
			}
			return list;
		}
	}
	public static int getSubsNumber(String userName) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM subscribe WHERE mainUser = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userName);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				int index = 1;
				int subs = rset.getInt(index);
				return subs;
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
	public static boolean addSubs(String channel, String subs) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO subscribe(mainUser,subscriber) VALUES(?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, channel);
			pstmt.setString(2, subs);
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
	public static int checkSub(String channel, String subs) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM subscribe WHERE mainUser = ? AND subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, channel);
			pstmt.setString(2, subs);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				int index = 1;
				int temp = rset.getInt(index++);
				if(temp==0) return 0;
				else return 1;
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
		}
		return 0;
	}
	public static ArrayList<String> userSubs(String channel) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<String> users = new ArrayList<String>();
		try {
			String query = "SELECT * FROM subscribe WHERE subscriber = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, channel);
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int index = 1;
				String subsName = rset.getString(index);
				users.add(subsName);
			}
			return users;
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
		return null;
	}
	public static boolean deleteSubs(String channel, String subs) {
		Connection conn = ConnectionMenager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM subscribe WHERE mainUser=? AND subscriber= ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, channel);
			pstmt.setString(2, subs);
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
	public static boolean update(User user) {
		Connection conn = ConnectionMenager.getConnection();
		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE users SET password = ?, firstName = ?, lastName = ?, email = ?, channelDescription = ?, profileUrl = ?, blocked = ?, deleted = ?, userType = ?, lol = ?  WHERE userName = ?";
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getFirstName());
			pstmt.setString(index++, user.getLastName());
			pstmt.setString(index++, user.getEmail());
			pstmt.setString(index++, user.getChannelDescription());
			pstmt.setString(index++, user.getProfileUrl());
			pstmt.setBoolean(index++, user.getBlocked());
			pstmt.setBoolean(index++,user.getDeleted());
			pstmt.setString(index++, user.getUserType().toString());

			pstmt.setBoolean(index++, user.isLol());
			pstmt.setString(index++, user.getUserName());

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
	public static String dateToString(Date date) {
		SimpleDateFormat formatvr = new SimpleDateFormat("dd.MM.yyyy");
		String datum;
		datum = formatvr.format(date);
		return datum;
	}
	public static Date stringToDate(String datum) {

		try {
			DateFormat formatvr = new SimpleDateFormat("dd.MM.yyyy");

			return formatvr.parse(datum);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
	public static String dateToStringForWrite(Date date) {
		SimpleDateFormat formatvr = new SimpleDateFormat("yyyy-MM-dd");
		String datum;
		datum = formatvr.format(date);
		return datum;
	}
	public static Date stringToDateForWrite(String datum) {

		try {
			DateFormat formatvr = new SimpleDateFormat("yyyy-MM-dd");

			return formatvr.parse(datum);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
}

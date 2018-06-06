package projectYT.model;

import java.util.ArrayList;

public class User {
	
	public enum UserType {USER, ADMIN};
	
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String channelDescription;
	private String registrationDate;
	private Boolean blocked;
	private ArrayList<User> subscribers;
	private ArrayList<Video> likedVideos;
	private ArrayList<Comment> likedComments;
	private UserType userType;
	private Boolean deleted;
	private String profileUrl;
	public int subsNumber;
	public int videosCount;
	public ArrayList<String> subscriberNames;
	public boolean lol;
	
	public User(String userName, String password, String firstName, String lastName, String email,
			String channelDescription, String registrationDate, Boolean blocked, ArrayList<User> subscribers,
			ArrayList<Video> likedVideos, ArrayList<Comment> likedComments, UserType userType, Boolean deleted,String profileUrl,boolean lol) {
		super();
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.channelDescription = channelDescription;
		this.registrationDate = registrationDate;
		this.blocked = blocked;
		this.subscribers = subscribers;
		this.likedVideos = likedVideos;
		this.likedComments = likedComments;
		this.userType = userType;
		this.deleted = deleted;
		this.profileUrl = profileUrl;
		this.lol = lol;
	}
	public  User() {
		
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getChannelDescription() {
		return channelDescription;
	}
	public void setChannelDescription(String channelDescription) {
		this.channelDescription = channelDescription;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	public Boolean getBlocked() {
		return blocked;
	}
	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
	}
	public ArrayList<User> getSubscribers() {
		return subscribers;
	}
	public void setSubscribers(ArrayList<User> subscribers) {
		this.subscribers = subscribers;
	}
	public ArrayList<Video> getLikedVideos() {
		return likedVideos;
	}
	public void setLikedVideos(ArrayList<Video> likedVideos) {
		this.likedVideos = likedVideos;
	}
	public ArrayList<Comment> getLikedComments() {
		return likedComments;
	}
	public void setLikedComments(ArrayList<Comment> likedComments) {
		this.likedComments = likedComments;
	}
	public UserType getUserType() {
		return userType;
	}
	public void setUserType(UserType userType) {
		this.userType = userType;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public String getProfileUrl() {
		return profileUrl;
	}
	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
	public int getSubsNumber() {
		return subsNumber;
	}
	public void setSubsNumber(int subsNumber) {
		this.subsNumber = subsNumber;
	}
	public ArrayList<String> getSubscriberNames() {
		return subscriberNames;
	}
	public void setSubscriberNames(ArrayList<String> subscriberNames) {
		this.subscriberNames = subscriberNames;
	}
	public int getVideosCount() {
		return videosCount;
	}
	public void setVideosCount(int videosCount) {
		this.videosCount = videosCount;
	}
	@Override
	public String toString() {
		return "User [userName=" + userName + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + ", userType=" + userType + "]";
	}
	public boolean isLol() {
		return lol;
	}
	public void setLol(boolean lol) {
		this.lol = lol;
	}
	
}

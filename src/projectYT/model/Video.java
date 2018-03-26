package projectYT.model;

import java.util.Date;

public class Video {

	public enum Visibility{PUBLIC, UNLISTED, PRIVATE};
	
	private int id;
	private String videoUrl;
	private String pictureUrl;
	private String videoName;
	private String description;
	private Visibility visibility;
	private boolean blocked;
	private boolean commentsEnabled;
	private boolean ratingEnabled;
	private int numberOfLikes;
	private int numberOfDislikes;
	private int views;
	private String datePosted;
	private User owner;
	private boolean deleted;
	
	public Video(int id, String videoUrl, String pictureUrl, String videoName, String description,
			Visibility visibility, boolean blocked, boolean commentsEnabled, boolean ratingEnabled, int numberOfLikes,
			int numberOfDislikes, int views, String datePosted, User owner, boolean deleted) {
		super();
		this.id = id;
		this.videoUrl = videoUrl;
		this.pictureUrl = pictureUrl;
		this.videoName = videoName;
		this.description = description;
		this.visibility = visibility;
		this.blocked = blocked;
		this.commentsEnabled = commentsEnabled;
		this.ratingEnabled = ratingEnabled;
		this.numberOfLikes = numberOfLikes;
		this.numberOfDislikes = numberOfDislikes;
		this.views = views;
		this.datePosted = datePosted;
		this.owner = owner;
		this.deleted = deleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Visibility getVisibility() {
		return visibility;
	}
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
	public boolean isCommentsEnabled() {
		return commentsEnabled;
	}
	public void setCommentsEnabled(boolean commentsEnabled) {
		this.commentsEnabled = commentsEnabled;
	}
	public boolean isRatingEnabled() {
		return ratingEnabled;
	}
	public void setRatingEnabled(boolean ratingEnabled) {
		this.ratingEnabled = ratingEnabled;
	}
	public int getNumberOfLikes() {
		return numberOfLikes;
	}
	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}
	public int getNumberOfDislikes() {
		return numberOfDislikes;
	}
	public void setNumberOfDislikes(int numberOfDislikes) {
		this.numberOfDislikes = numberOfDislikes;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}

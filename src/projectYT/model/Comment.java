package projectYT.model;

public class Comment {
	
	public int id;
	public String text;
	public String datePosted;
	public User user;
	public Video video;
	private int likeNumber;
	private int dislikeNumber;
	private boolean deleted;
	
	public Comment(int id, String text, String datePosted, User user, Video video, int likeNumber, int dislikeNumber,
			boolean deleted) {
		super();
		this.id = id;
		this.text = text;
		this.datePosted = datePosted;
		this.user = user;
		this.video = video;
		this.likeNumber = likeNumber;
		this.dislikeNumber = dislikeNumber;
		this.deleted = deleted;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(String datePosted) {
		this.datePosted = datePosted;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Video getVideo() {
		return video;
	}
	public void setVideo(Video video) {
		this.video = video;
	}
	public int getLikeNumber() {
		return likeNumber;
	}
	public void setLikeNumber(int likeNumber) {
		this.likeNumber = likeNumber;
	}
	public int getDislikeNumber() {
		return dislikeNumber;
	}
	public void setDislikeNumber(int dislikeNumber) {
		this.dislikeNumber = dislikeNumber;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
}

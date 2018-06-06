package projectYT.model;


public class Like {
	
	private int id;
	private boolean likeOrDislike;
	private String likeDate;
	private Video video;
	private Comment comment;
	private User owner;
	private boolean deleted;
	
	public Like(int id, boolean likeOrDislike, String likeDate, Video video, Comment comment, User owner,boolean deleted) {
		super();
		this.id = id;
		this.likeOrDislike = likeOrDislike;
		this.likeDate = likeDate;
		this.video = video;
		this.comment = comment;
		this.owner = owner;
		this.deleted = deleted;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLikeOrDislike() {
		return likeOrDislike;
	}

	public void setLikeOrDislike(boolean likeOrDislike) {
		this.likeOrDislike = likeOrDislike;
	}

	public String getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(String likeDate) {
		this.likeDate = likeDate;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
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

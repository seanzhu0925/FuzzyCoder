package cse308;

public class Announcement {
	public int announcement_id;
	public String announcement_name;
	public String announcement_content;
	public String announcement_course;
	public String announcement_date;
	
	public Announcement(int id, String name, String content, String course){
		announcement_id = id;
		announcement_name = name;
		announcement_content = content;
		announcement_course = course;
	}
	public int getAnnouncement_id() {
		return announcement_id;
	}
	public void setAnnouncement_id(int announcement_id) {
		this.announcement_id = announcement_id;
	}
	public String getAnnouncement_name() {
		return announcement_name;
	}
	public void setAnnouncement_name(String announcement_name) {
		this.announcement_name = announcement_name;
	}
	public String getAnnouncement_content() {
		return announcement_content;
	}
	public void setAnnouncement_content(String announcement_content) {
		this.announcement_content = announcement_content;
	}
	public String getAnnouncement_course() {
		return announcement_course;
	}
	public void setAnnouncement_course(String announcement_course) {
		this.announcement_course = announcement_course;
	}
	public String getAnnouncement_date() {
		return announcement_date;
	}
	public void setAnnouncement_date(String announcement_date) {
		this.announcement_date = announcement_date;
	}

}

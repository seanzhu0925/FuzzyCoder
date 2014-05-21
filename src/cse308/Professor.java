package cse308;

public class Professor {
	public String pro_id;
	public String pro_fname;
	public String pro_lname;
	public String pro_email;
	public String[] course_history;
	public String rating;
	public Professor(String pro_id, String pro_fname, String pro_lname, String pro_email){
		this.setPro_id(pro_id);
		this.setPro_fname(pro_fname);
		this.setPro_lname(pro_lname);
		this.setPro_email(pro_email);
	}
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	public String getPro_fname() {
		return pro_fname;
	}
	public void setPro_fname(String pro_fname) {
		this.pro_fname = pro_fname;
	}
	public String getPro_lname() {
		return pro_lname;
	}
	public void setPro_lname(String pro_lname) {
		this.pro_lname = pro_lname;
	}
	public String getPro_email() {
		return pro_email;
	}
	public void setPro_email(String pro_email) {
		this.pro_email = pro_email;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
}
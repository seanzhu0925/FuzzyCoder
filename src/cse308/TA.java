package cse308;

public class TA {
	public String ta_id;
	public String ta_fname;
	public String ta_lname;
	public String ta_email;
	public String[] course_history;
	public String rating;
	
	public TA (String ta_id, String ta_fname, String ta_lname, String ta_email) {
		this.setTa_email(ta_email);
		this.setTa_fname(ta_fname);
		this.setTa_lname(ta_lname);
		this.setTa_id(ta_id);
	}

	public String getTa_id() {
		return ta_id;
	}

	public void setTa_id(String ta_id) {
		this.ta_id = ta_id;
	}

	public String getTa_fname() {
		return ta_fname;
	}

	public void setTa_fname(String ta_fname) {
		this.ta_fname = ta_fname;
	}

	public String getTa_lname() {
		return ta_lname;
	}

	public void setTa_lname(String ta_lname) {
		this.ta_lname = ta_lname;
	}

	public String getTa_email() {
		return ta_email;
	}

	public void setTa_email(String ta_email) {
		this.ta_email = ta_email;
	}

	public String[] getCourse_history() {
		return course_history;
	}

	public void setCourse_history(String[] course_history) {
		this.course_history = course_history;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
	
}

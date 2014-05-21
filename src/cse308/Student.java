package cse308;

public class Student {
	public String stu_id;
	public String stu_fname;
	public String stu_lname;
	public String stu_email;
	public String[] course_history;
	public String gpa;
	public String password;

	public Student(String stu_id, String stu_fname, String stu_lname, String stu_email){
		this.stu_id = stu_id;
		this.stu_fname = stu_fname;
		this.stu_lname = stu_lname;
		this.stu_email = stu_email;
	}
	public String getStu_id() {
		return stu_id;
	}
	public void setStu_id(String stu_id) {
		this.stu_id = stu_id;
	}
	public String getStu_fname() {
		return stu_fname;
	}
	public void setStu_fname(String stu_fname) {
		this.stu_fname = stu_fname;
	}
	public String getStu_lname() {
		return stu_lname;
	}
	public void setStu_lname(String stu_lname) {
		this.stu_lname = stu_lname;
	}
	public String getStu_email() {
		return stu_email;
	}
	public void setStu_email(String stu_email) {
		this.stu_email = stu_email;
	}
	public String[] getCourse_history() {
		return course_history;
	}
	public void setCourse_history(String[] course_history) {
		this.course_history = course_history;
	}
	public String getGpa() {
		return gpa;
	}
	public void setGpa(String gpa) {
		this.gpa = gpa;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

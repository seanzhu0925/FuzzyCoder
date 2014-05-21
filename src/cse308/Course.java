package cse308;

import java.util.ArrayList;

public class Course {
	public String course_id;
	public String course_name;
	public String semester;
	public String professor;
	public String capacity;
	public String description;
	public ArrayList<Student> student = new ArrayList<Student>();
	public Course(String course_id, String course_name, String semester, String professor, String capacity, String description){
		this.course_id = course_id;
		this.course_name = course_name;
		this.semester = semester;
		this.professor = professor;
		this.capacity = capacity;
		this.description = description;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getCourse_name() {
		return course_name;
	}
	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Student> getStudent() {
		return student;
	}
	public void setStudent(ArrayList<Student> student) {
		this.student = student;
	}
}

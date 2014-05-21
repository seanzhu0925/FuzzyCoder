package cse308;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.QueryResultList;
@ManagedBean(name = "admin")
@SessionScoped
public class AdminBean implements Serializable  {

	private static final long serialVersionUID = 1L;
	private String id;
	private String fname;
	private String lname;
	private String email;
	private String password;
	private String[] course_history;
	private String capacity;
	private String professor;
	private String semester;
	private String description;
	private String courseName;
	private ArrayList<Student> studentList = new ArrayList<Student>();
	private ArrayList<Course> courseList = new ArrayList<Course>();
	private ArrayList<Professor> professorList = new ArrayList<Professor>();
	private ArrayList<TA> taList = new ArrayList<TA>();
	private ArrayList<Course> user_courseList = new ArrayList<Course>();
	private String editStudentId;
	private String editStudentFirstName;
	private String editStudentLastName;
	private String editStudentEmail;
	private String editStudentPassword;
	private String editProfessorId;
	private String editProfessorFirstName;
	private String editProfessorLastName;
	private String editProfessorEmail;
	private String editProfessorPassword;
	private String editCourseId;
	private String editCourseName;
	private String editCourseSemester;
	private String editCourseProfessor;
	private String editCourseCapacity;
	private String editCourseDescription;
	private int c;
	
	public String addStudent() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		FacesMessage msg;
		Entity user = new Entity("User", this.email);
		user.setProperty("id", this.id);
		user.setProperty("firstName", this.fname);
		user.setProperty("lastName", this.lname);
		user.setProperty("email", this.email);
		user.setProperty("password", this.getpassword());
		user.setProperty("role", "Student");
		if(checkId(this.id, "Student") || checkEmail(this.email)) {
				msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Id '" + id + " ' "+ " or Email address '" + email
							+ "' are already in the datastore!  ",
					"Please enter another user id and email!");
				context.addMessage(null, msg);
		} else {
				datastore.put(user);
				try {
					listStudent();
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "admin_students";
		}
		return null;
	}
	public String addProfessor() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		FacesMessage msg;
		Entity user = new Entity("User", this.email);
		user.setProperty("id", this.id);
		user.setProperty("firstName", this.fname);
		user.setProperty("lastName", this.lname);
		user.setProperty("email", this.email);
		user.setProperty("password", this.getpassword());
		user.setProperty("role", "Professor");
		if(checkId(this.id, "Professor") || checkEmail(this.email)) {
				msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Id '" + id + " ' "+ " or Email address '" + email
							+ "' are already in the datastore!  ",
					"Please enter another user id and email!");
				context.addMessage(null, msg);
		} else {
				datastore.put(user);
				try {
					listProfessor();
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "admin_professors";
		}
		return null;
	}
	public String addTA() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		FacesMessage msg;
		Entity user = new Entity("User", this.email);
		user.setProperty("id", this.id);
		user.setProperty("firstName", this.fname);
		user.setProperty("lastName", this.lname);
		user.setProperty("email", this.email);
		user.setProperty("password", this.getpassword());
		user.setProperty("role", "TA");
		if(checkId(this.id, "TA") || checkEmail(this.email)) {
				msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Id '" + id + " ' "+ " or Email address '" + email
							+ "' are already in the datastore!  ",
					"Please enter another user id and email!");
				context.addMessage(null, msg);
		} else {
				datastore.put(user);
				try {
					listStudent();
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "admin_tas";
		}
		return null;
	}
	public String addCourse() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		FacesMessage msg;
		Entity course = new Entity("Course", this.id);
		course.setProperty("id", this.id);
		course.setProperty("name", this.lname);
		course.setProperty("semester", this.semester);
		course.setProperty("professor", this.professor);
		course.setProperty("capacity", this.capacity);
		course.setProperty("description", this.description);
		if(checkId(this.id) ) {
				msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Id '" + id + "' are already in the datastore!  ",
					"Please enter another course id!");
				context.addMessage(null, msg);
		} else {
				datastore.put(course);
				try {
					listCourse();
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "admin_courses";
		}
		return null;
	}
	
	public String deleteStudent(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String email = (String)params.get("email");
	    Key e = KeyFactory.createKey("User", email);
	    
		for(Student student:studentList){
			if(student.stu_email.equals(email)){
				studentList.remove(student);
				datastore.delete(e);
				try {
					listStudent();
				} catch (EntityNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "admin_students";
			}
		}
		return null;
	}
	public String deleteProfessor(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String email = (String)params.get("email");
	    Key e = KeyFactory.createKey("User", email);
	    
		for(Professor professor:getProfessorList()){
			if(professor.pro_email.equals(email)){
				professorList.remove(professor);
				datastore.delete(e);
				try {
					listProfessor();
				} catch (EntityNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "admin_professors";
			}
		}
		return null;
	}
	public String deleteTA(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String email = (String)params.get("email");
	    Key e = KeyFactory.createKey("User", email);
	    
		for(TA ta:getTaList()){
			if(ta.ta_email.equals(email)){
				taList.remove(ta);
				datastore.delete(e);
				try {
					listTa();
				} catch (EntityNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "admin_tas";
			}
		}
		return null;
	}
	public String deleteCourse(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String id = (String)params.get("id");
	    Key e = KeyFactory.createKey("Course", id);
	    
		for(Course course:getCourseList()){
			if(course.course_id.equals(id)){
				courseList.remove(course);
				datastore.delete(e);
				try {
					listCourse();
				} catch (EntityNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "admin_courses";
			}
		}
		return null;
	}
	public String editCourse(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		Transaction txn = datastore.beginTransaction();
		try{
			
	    String id = (String)params.get("id_for_edit_student");
	    if(id==null){
	    	return "admin_courses";
	    }
	    Key e = KeyFactory.createKey("User", id);
	    Entity en = null;

			 en = datastore.get(e);

		editCourseId = en.getProperty("course_id").toString();
		editCourseName = en.getProperty("course_name").toString();
		editCourseSemester = en.getProperty("semester").toString();
		editCourseProfessor = en.getProperty("professor").toString();
		editCourseCapacity = en.getProperty("capacity").toString();
		editCourseDescription = en.getProperty("description").toString();
		txn.commit();
		}catch (ConcurrentModificationException e) {
	        
	    } 
		catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}finally {
	        if (txn.isActive()) {
	            txn.rollback();
	        }
	    }
		
		return "admin_edit_course.xhtml";
	}
	public String editStudent(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		Transaction txn = datastore.beginTransaction();
		try{
			
	    String email = (String)params.get("email_for_edit_student");
	    if(email==null){
	    	return "admin_students";
	    }
	    Key e = KeyFactory.createKey("User", email);
	    Entity en = null;

			 en = datastore.get(e);

		editStudentId = en.getProperty("id").toString();
		editStudentFirstName = en.getProperty("firstName").toString();
		editStudentLastName = en.getProperty("lastName").toString();
		editStudentEmail = en.getProperty("email").toString();
		editStudentPassword = en.getProperty("password").toString();
		txn.commit();
		}catch (ConcurrentModificationException e) {
	        
	    } 
		catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}finally {
	        if (txn.isActive()) {
	            txn.rollback();
	        }
	    }
		
		return "admin_edit_student.xhtml";
	}
	
	public String editProfessor(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		Transaction txn = datastore.beginTransaction();
		try{
	    String email = (String)params.get("email_for_edit_professor");
	    if(email==null){
	    	return "admin_professors";
	    }
	    Key e = KeyFactory.createKey("User", email);
	    Entity en = null;

			 en = datastore.get(e);

		editProfessorId = en.getProperty("id").toString();
		editProfessorFirstName = en.getProperty("firstName").toString();
		editProfessorLastName = en.getProperty("lastName").toString();
		editProfessorEmail = en.getProperty("email").toString();
		editProfessorPassword = en.getProperty("password").toString();
		txn.commit();
		}catch (ConcurrentModificationException e) {
	        
	    } 
		catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}finally {
	        if (txn.isActive()) {
	            txn.rollback();
	        }
	    }
		
		return "admin_edit_professor.xhtml";
	}
	public String editTA(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		Transaction txn = datastore.beginTransaction();
		try{
	    String email = (String)params.get("email_for_edit_TA");
	    if(email==null){
	    	return "admin_tas";
	    }
	    Key e = KeyFactory.createKey("User", email);
	    Entity en = null;

			 en = datastore.get(e);

		editProfessorId = en.getProperty("id").toString();
		editProfessorFirstName = en.getProperty("firstName").toString();
		editProfessorLastName = en.getProperty("lastName").toString();
		editProfessorEmail = en.getProperty("email").toString();
		editProfessorPassword = en.getProperty("password").toString();
		txn.commit();
		}catch (ConcurrentModificationException e) {
	        
	    } 
		catch (EntityNotFoundException e1) {
			e1.printStackTrace();
		}finally {
	        if (txn.isActive()) {
	            txn.rollback();
	        }
	    }
		
		return "admin_edit_ta.xhtml";
	}
	public String saveEditStudent(){
		@SuppressWarnings("unused")
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		@SuppressWarnings("unused")
		FacesMessage msg;
		Entity user = new Entity("User", this.editStudentEmail);
		user.setProperty("id", this.editStudentId);
		user.setProperty("firstName", this.editStudentFirstName);
		user.setProperty("lastName", this.editStudentLastName);
		user.setProperty("email", this.editStudentEmail);
		user.setProperty("password", this.editStudentPassword);
		user.setProperty("role", "student");
		for(Student st: studentList){
			if(st.getStu_email().equalsIgnoreCase(this.editStudentEmail))
			st.setStu_id(this.editStudentId);
			st.setStu_fname(this.editStudentFirstName);
			st.setStu_lname(this.editStudentLastName);
			st.setPassword(this.editStudentPassword);
		}
				datastore.put(user);
				try {
					listStudent();
				} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}
				return "admin_students";
	}
	public String saveEditProfessor(){
		
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();		
		Entity user = new Entity("User", this.editProfessorEmail);
		user.setProperty("id", this.editProfessorId);
		user.setProperty("firstName", this.editProfessorFirstName);
		user.setProperty("lastName", this.editProfessorLastName);
		user.setProperty("email", this.editProfessorEmail);
		user.setProperty("password", this.editProfessorPassword);
		user.setProperty("role", "professor");
		for(Student st: studentList){
			if(st.getStu_email().equalsIgnoreCase(this.editProfessorEmail))
			st.setStu_id(this.editProfessorId);
			st.setStu_fname(this.editProfessorFirstName);
			st.setStu_lname(this.editProfessorLastName);
			st.setPassword(this.editProfessorPassword);
		}
				datastore.put(user);
				try {
					listProfessor();
				} catch (EntityNotFoundException e) {
					e.printStackTrace();
				}
				return "admin_professors";
	}
	public boolean checkId(String id) {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter idFilter = new FilterPredicate("id", FilterOperator.EQUAL,
				id);
		
		Query q = new Query("Course").setFilter(idFilter);
		PreparedQuery pq = datastore.prepare(q);
		re = pq.countEntities(FetchOptions.Builder.withDefaults());
		if (re == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkId(String id, String role) {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter idFilter = new FilterPredicate("id", FilterOperator.EQUAL,
				id);
		Filter roleFilter = new FilterPredicate("role", FilterOperator.EQUAL, role);
		
		Filter comFilter = CompositeFilterOperator.and(idFilter,
				roleFilter);
		Query q = new Query("User").setFilter(comFilter);
		PreparedQuery pq = datastore.prepare(q);
		re = pq.countEntities(FetchOptions.Builder.withDefaults());
		if (re == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean checkEmail(String email) {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter emailFilter = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Query q = new Query("User").setFilter(emailFilter);
		PreparedQuery pq = datastore.prepare(q);
		re = pq.countEntities(FetchOptions.Builder.withDefaults());
		if (re == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public void listStudent() throws EntityNotFoundException{
		
		int pageSize = 30;
	    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
				Query q = new Query("User").addFilter("role", Query.FilterOperator.EQUAL, "Student");
		//.addFilter("role", Query.FilterOperator.EQUAL, "Student");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		studentList = new ArrayList<Student>();
		for(Entity entity: results){
			
			id = (String) entity.getProperty("id");
			fname = (String) entity.getProperty("firstName");
			lname = (String) entity.getProperty("lastName");
			email = (String) entity.getProperty("email");
			studentList.add(new Student(id, fname, lname, email));
		}
	}
	@SuppressWarnings("deprecation")
	public void listTa() throws EntityNotFoundException{
		
		int pageSize = 30;
	    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
				Query q = new Query("User").addFilter("role", Query.FilterOperator.EQUAL, "TA");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		taList = new ArrayList<TA>();
		for(Entity entity: results){
			
			id = (String) entity.getProperty("id");
			fname = (String) entity.getProperty("firstName");
			lname = (String) entity.getProperty("lastName");
			email = (String) entity.getProperty("email");
			taList.add(new TA(id, fname, lname, email));
		}
	}
	@SuppressWarnings("deprecation")
	public void listProfessor() throws EntityNotFoundException{
		
		int pageSize = 30;
	    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
				Query q = new Query("User").addFilter("role", Query.FilterOperator.EQUAL, "Professor");
		//.addFilter("role", Query.FilterOperator.EQUAL, "Student");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		professorList = new ArrayList<Professor>();
		for(Entity entity: results){
			
			id = (String) entity.getProperty("id");
			fname = (String) entity.getProperty("firstName");
			lname = (String) entity.getProperty("lastName");
			email = (String) entity.getProperty("email");
			professorList.add(new Professor(id, fname, lname, email));
		}
	}
	public void listCourse() throws EntityNotFoundException{
		
		int pageSize = 30;
	    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
				Query q = new Query("Course");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);	
		courseList = new ArrayList<Course>();
		for(Entity entity: results){
			
			id = (String) entity.getProperty("id");
			lname = (String) entity.getProperty("name");
			semester = (String) entity.getProperty("semester");
			professor = (String) entity.getProperty("professor");
			//capacity = (String) entity.getProperty("capacity");
			description = (String) entity.getProperty("description");
			courseList.add(new Course(id, lname, semester, professor, capacity, description));
		}
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getfname() {
		return fname;
	}
	public void setfname(String fname) {
		this.fname = fname;
	}
	public String getlname() {
		return lname;
	}
	public void setlname(String lname) {
		this.lname = lname;
	}
	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email = email;
	}
	public String[] getCourse_history() {
		return course_history;
	}
	public void setCourse_history(String[] course_history) {
		this.course_history = course_history;
	}
	public String getpassword() {
		return password;
	}
	public void setpassword(String password) {
		this.password = password;
	}
	public ArrayList<TA> getTaList() {
		return taList;
	}
	public void setTaList(ArrayList<TA> taList) {
		this.taList = taList;
	}
	public ArrayList<Student> getStudentList(){
		try {
			listStudent();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return studentList;
	}
	public ArrayList<Professor> getProfessorList() {
		try {
			listProfessor();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return professorList;
	}
	public ArrayList<Course> getCourseList() {
		try {
			listCourse();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;
	}
	public void setProfessorList(ArrayList<Professor> professorList) {
		this.professorList = professorList;
	}
	public void setStudentList(ArrayList<Student> studentList) {
		this.studentList = studentList;
	}
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ArrayList<Course> getUser_courseList() {
		return user_courseList;
	}
	public void setUser_courseList(ArrayList<Course> user_courseList) {
		this.user_courseList = user_courseList;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getEditStudentId() {
		return editStudentId;
	}
	public void setEditStudentId(String editStudentId) {
		this.editStudentId = editStudentId;
	}
	public String getEditStudentFirstName() {
		return editStudentFirstName;
	}
	public void setEditStudentFirstName(String editStudentFirstName) {
		this.editStudentFirstName = editStudentFirstName;
	}
	public String getEditStudentLastName() {
		return editStudentLastName;
	}
	public void setEditStudentLastName(String editStudentLastName) {
		this.editStudentLastName = editStudentLastName;
	}
	public String getEditStudentEmail() {
		return editStudentEmail;
	}
	public void setEditStudentEmail(String editStudentEmail) {
		this.editStudentEmail = editStudentEmail;
	}
	public String getEditStudentPassword() {
		return editStudentPassword;
	}
	public void setEditStudentPassword(String editStudentPassword) {
		this.editStudentPassword = editStudentPassword;
	}
	public String getEditProfessorId() {
		return editProfessorId;
	}
	public void setEditProfessorId(String editProfessorId) {
		this.editProfessorId = editProfessorId;
	}
	public String getEditProfessorFirstName() {
		return editProfessorFirstName;
	}
	public void setEditProfessorFirstName(String editProfessorFirstName) {
		this.editProfessorFirstName = editProfessorFirstName;
	}
	public String getEditProfessorLastName() {
		return editProfessorLastName;
	}
	public void setEditProfessorLastName(String editProfessorLastName) {
		this.editProfessorLastName = editProfessorLastName;
	}
	public String getEditProfessorEmail() {
		return editProfessorEmail;
	}
	public void setEditProfessorEmail(String editProfessorEmail) {
		this.editProfessorEmail = editProfessorEmail;
	}
	public String getEditProfessorPassword() {
		return editProfessorPassword;
	}
	public void setEditProfessorPassword(String editProfessorPassword) {
		this.editProfessorPassword = editProfessorPassword;
	}
	public String getEditCourseId() {
		return editCourseId;
	}
	public void setEditCourseId(String editCourseId) {
		this.editCourseId = editCourseId;
	}
	public String getEditCourseName() {
		return editCourseName;
	}
	public void setEditCourseName(String editCourseName) {
		this.editCourseName = editCourseName;
	}
	public String getEditCourseSemester() {
		return editCourseSemester;
	}
	public void setEditCourseSemester(String editCourseSemester) {
		this.editCourseSemester = editCourseSemester;
	}
	public String getEditCourseProfessor() {
		return editCourseProfessor;
	}
	public void setEditCourseProfessor(String editCourseProfessor) {
		this.editCourseProfessor = editCourseProfessor;
	}
	public String getEditCourseCapacity() {
		return editCourseCapacity;
	}
	public void setEditCourseCapacity(String editCourseCapacity) {
		this.editCourseCapacity = editCourseCapacity;
	}
	public String getEditCourseDescription() {
		return editCourseDescription;
	}
	public void setEditCourseDescription(String editCourseDescription) {
		this.editCourseDescription = editCourseDescription;
	}
	
}

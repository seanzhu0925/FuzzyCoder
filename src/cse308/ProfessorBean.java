package cse308;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
@ManagedBean(name = "prof")
@SessionScoped
public class ProfessorBean extends UserBean implements Serializable{
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private String professorName;
	private String courseName;
	private String announcement_name;
	private String title;
	private String content;
	private String contentA;
	private String contentB;
	private String contentC;
	private String contentD;
	private String contentE;
	private String answer;
	private String score;
	private String announcement_content;
	private Date announcement_date;
	private int announcement_id = 100;
	private ArrayList<String> courseList = new ArrayList<String>();
	private String type;
	private ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
	private ArrayList<Question> multichoiceList = new ArrayList<Question>();
	private ArrayList<Question> shortanswerList = new ArrayList<Question>();
	
	public String addAnnouncement() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity announcement = new Entity("Announcement", this.announcement_name);
		//Entity announcement = new Entity("Announcement", this.getAnnouncement_id());
		//setAnnouncement_id(getAnnouncement_id() + 1);
		//announcement.setProperty("id", this.announcement_id);
		announcement.setProperty("name", this.getAnnouncement_name());
		announcement.setProperty("content", this.getAnnouncement_content());
		announcement.setProperty("date", this.getAnnouncement_date());
		announcement.setProperty("course", this.courseName);
		
		datastore.put(announcement);
				try {
					listAnnouncement();
				} catch (EntityNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return "professor_announcement";
	}
	
	public String addQuestion() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity question = new Entity("Question", this.title);
		//Entity announcement = new Entity("Announcement", this.getAnnouncement_id());
		//setAnnouncement_id(getAnnouncement_id() + 1);
		//announcement.setProperty("id", this.announcement_id);
		question.setProperty("type", this.type);
		question.setProperty("title", this.title);
		if(type.equals("multichoice")){
			question.setProperty("contentA", this.contentA);
			question.setProperty("contentB", this.contentB);
			question.setProperty("contentC", this.contentC);
			question.setProperty("contentD", this.contentD);
			question.setProperty("contentE", this.contentE);
		}
		question.setProperty("course", this.courseName);
		question.setProperty("answer", this.answer);
		question.setProperty("score", this.score);
		datastore.put(question);
				
				return "add_exam";
	}
	
	public String deleteAnnouncement(){
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String name = (String)params.get("name");
	    Key e = KeyFactory.createKey("Announcement", name);
	    
		for(Announcement announcement:getAnnouncementList()){
			if(announcement.announcement_name.equals(name)){
				getAnnouncementList().remove(announcement);
				datastore.delete(e);
				try {
					listAnnouncement();
				} catch (EntityNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return "professor_announcement";
			}
		}
		return null;
	}
	
@SuppressWarnings("deprecation")
public void listAnnouncement() throws EntityNotFoundException{
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		int pageSize = 30;
	    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		
				
		Query q = new Query("Announcement").addFilter("course", Query.FilterOperator.EQUAL, courseName);
		//.addFilter("role", Query.FilterOperator.EQUAL, "Student");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		announcementList = new ArrayList<Announcement>();
		for(Entity entity: results){
			 
			//setAnnouncement_id((int) entity.getProperty("id"));
			setAnnouncement_name((String) entity.getProperty("name"));
			setAnnouncement_content((String) entity.getProperty("content"));
			courseName = (String) entity.getProperty("course");
			announcementList.add(new Announcement(announcement_id, announcement_name, announcement_content, courseName));
		}
	}

public void listCourse() throws EntityNotFoundException{
	
	
	UserBean ub = (UserBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
//		UserBean ub = UserBean.getUserBean();
		
		String loginEmail;
		loginEmail= ub.getUser_email();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key e = KeyFactory.createKey("User", loginEmail);
		 
		 Entity prf = null;
		 prf = datastore.get(e);
		 professorName= prf.getProperty("firstName").toString();
		 
		
	
	int pageSize = 30;
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
	
	Filter profFilter = new FilterPredicate("professor", FilterOperator.EQUAL,professorName);
	Query q = new Query("Course").setFilter(profFilter);
	PreparedQuery pq = datastore.prepare(q);
	QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);	
	courseList = new ArrayList<String>();
	for(Entity entity: results){
		String crs;
		
		crs = (String) entity.getProperty("name");
		courseList.add(crs);
	}
}

@SuppressWarnings("deprecation")
public void listQuestion() throws EntityNotFoundException{
	
	int pageSize = 30;
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
	DatastoreService datastore = DatastoreServiceFactory
			.getDatastoreService();
	Query q = new Query("Question").addFilter("type", Query.FilterOperator.EQUAL, "multichoice");
	//.addFilter("role", Query.FilterOperator.EQUAL, "Student");
	PreparedQuery pq = datastore.prepare(q);
	QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
	multichoiceList = new ArrayList<Question>();
	for(Entity entity: results){
		
		type = (String) entity.getProperty("type");
		title = (String) entity.getProperty("title");
		answer = (String) entity.getProperty("answer");
		score = (String) entity.getProperty("score");
		contentA = (String) entity.getProperty("contentA");
		contentB = (String) entity.getProperty("contentB");
		contentC = (String) entity.getProperty("contentC");
		contentD = (String) entity.getProperty("contentD");
		contentE = (String) entity.getProperty("contentE");
		multichoiceList.add(new Question(type, title, answer, score, contentA, contentB, contentC, contentD, contentE));
	}
	Query q2 = new Query("Question").addFilter("type", Query.FilterOperator.EQUAL, "shortanswer");
	PreparedQuery pq2 = datastore.prepare(q2);
	QueryResultList<Entity> results2 = pq2.asQueryResultList(fetchOptions);
	shortanswerList = new ArrayList<Question>();
	for(Entity entity: results2){
		type = (String) entity.getProperty("type");
		title = (String) entity.getProperty("title");
		answer = (String) entity.getProperty("answer");
		score = (String) entity.getProperty("score");
		shortanswerList.add(new Question(type, title, answer, score));
	}
}
	public String setCourse(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String course = (String)params.get("course");
		courseName = course;
	    
		return "professor_index";
	}
	public String setType(){
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	    String t = (String)params.get("type");
		type = t;
	    if(type.equals("multichoice"))
	    	return "add_multi";
	    else if(type.equals("shortanswer"))
	    	return "add_short";
	    else if(type.equals("coding"))
	    	return "add_coding";
	    else if(type.equals("matching"))
	    	return "add_matching";
	    else
	    	return "add_problem";
	}
	public ArrayList<String> getCourseList() {
		try {
			listCourse();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return courseList;
	}

	public String getProfessorName() {
		return professorName;
	}

	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}

	public void setCourseList(ArrayList<String> courseList) {
		this.courseList = courseList;
	}

	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
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

	public Date getAnnouncement_date() {
		return announcement_date;
	}

	public void setAnnouncement_date(Date announcement_date) {
		this.announcement_date = announcement_date;
	}

	public int getAnnouncement_id() {
		return announcement_id;
	}

	public void setAnnouncement_id(int announcement_id) {
		this.announcement_id = announcement_id;
	}

	public ArrayList<Announcement> getAnnouncementList() {
		try {
			listAnnouncement();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return announcementList;
	}

	public void setAnnouncementList(ArrayList<Announcement> announcementList) {
		this.announcementList = announcementList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getContentA() {
		return contentA;
	}

	public void setContentA(String contentA) {
		this.contentA = contentA;
	}

	public String getContentB() {
		return contentB;
	}

	public void setContentB(String contentB) {
		this.contentB = contentB;
	}

	public String getContentC() {
		return contentC;
	}

	public void setContentC(String contentC) {
		this.contentC = contentC;
	}

	public String getContentD() {
		return contentD;
	}

	public void setContentD(String contentD) {
		this.contentD = contentD;
	}

	public String getContentE() {
		return contentE;
	}

	public void setContentE(String contentE) {
		this.contentE = contentE;
	}

	public ArrayList<Question> getMultichoiceList() {
		try {
			listQuestion();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return multichoiceList;
	}

	public void setMultichoiceList(ArrayList<Question> questionList) {
		this.multichoiceList = questionList;
	}

	public ArrayList<Question> getShortanswerList() {
		try {
			listQuestion();
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shortanswerList;
	}

	public void setShortanswerList(ArrayList<Question> shortanswerList) {
		this.shortanswerList = shortanswerList;
	}
}
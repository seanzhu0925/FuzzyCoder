package cse308;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
@ManagedBean(name = "user")
@SessionScoped
public class UserBean implements Serializable{
	/**
	 * 
	 */
	public static UserBean ub = null;
	private static final long serialVersionUID = 1L;
	private String user_email;
	private String user_fname;
	private String user_lname;
	private String user_password;
	private String user_role;
	
	public UserBean(){
		this.user_email = "";
		this.user_fname = "";
		this.user_lname = "";
		this.user_password = "";
		this.user_role = "";
	}
	
	public static UserBean getUserBean(){
		
		if (ub == null){
			ub = new UserBean();
		}
		return ub;
	}
	public String login() throws EntityNotFoundException{
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg;
		if(user_email.length()==0){
		msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email '"+ this.user_email + " ' " + " or password '"+ this.user_password + "' do not exist in our records","Please try another user email and password!");
		context.addMessage(null, msg);	
		return null;
		}
		try{
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key=KeyFactory.createKey("User", user_email);
		Entity user=datastore.get(key);
		
		if(user.getProperty("password").toString().equals(user_password)){
			String role= user.getProperty("role").toString().toLowerCase();
			switch(role){
			case "student":
				return "student/student_index.xhtml";
			case "professor":
				return "professor/professor_index.xhtml";
			case "ta":
				return "ta/ta_index.xhtml";
			case "admin":
				return "admin/admin_index.xhtml";
			default: 
				return "index";
					
			}
			
		}
		else
			return"index";
		}catch(EntityNotFoundException e){
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email '"+ this.user_email + " ' " + " or password '"+ this.user_password + "' do not match in our records","Please try another user email and password!");
			context.addMessage(null, msg);
			return null;
		}	
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_role() throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key=KeyFactory.createKey("User", user_email);
		
		Entity user= datastore.get(key);
		user_role = (String) user.getProperty("role");
		return user_role;
	}
	public void setUser_role(String user_role) {
		this.user_role = user_role;
	}
	public String getUser_fname() throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key=KeyFactory.createKey("User", user_email);
		
		Entity user= datastore.get(key);
		user_fname = (String) user.getProperty("firstName");
		return user_fname;
	}
	public void setUser_fname(String user_fname) {
		this.user_fname = user_fname;
	}
	public String getUser_lname() throws EntityNotFoundException {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Key key=KeyFactory.createKey("User", user_email);
		
		Entity user= datastore.get(key);
		user_lname = (String) user.getProperty("lastName");
		return user_lname;
	}
	public void setUser_lname(String user_lname) {
		this.user_lname = user_lname;
	}
	

}

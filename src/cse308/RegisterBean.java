package cse308;

import java.io.Serializable;
import java.util.ArrayList;

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
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@ManagedBean(name = "register")
@SessionScoped
public class RegisterBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String user_id;
	private String user_fname;
	private String user_lname;
	private String user_email;
	private String user_password;
	private String user_reenterpassword;
	private String role;
	private ArrayList<studentInfo> users = new ArrayList<>();
	private studentInfo sInfo;
	public String adduser() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		FacesMessage msg;
	//	Key enKey = KeyFactory.createKey("User",this.user_email);
		Entity user = new Entity("User", this.user_email);
		user.setProperty("id", this.user_id);
		user.setProperty("firstName", this.user_fname);
		user.setProperty("lastName", this.user_lname);
		user.setProperty("email", this.user_email);
		user.setProperty("password", this.user_password);
		user.setProperty("reenterpassword", this.user_reenterpassword);
		user.setProperty("role", "Student");
		if(checkId(this.getuser_id()) || checkEmail(this.getuser_email())) {
				msg = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Id '" + user_id + " ' "+ " or Email address '" + user_email
							+ "' are already in the datastore!  ",
					"Please enter another user id and email!");
				context.addMessage(null, msg);
		} else {
			if (verifyUser(this.getuser_id(), this.getuser_email())) {
				if (!user_password.equals(user_reenterpassword)) {
					msg = new FacesMessage(
							FacesMessage.SEVERITY_ERROR, "The password you entered does not match",
							" Please try again!");
					context.addMessage(null, msg);
					return null;
				}
				users.add(sInfo = new studentInfo(this.user_id, this.user_fname, this.user_lname, this.user_email, this.role));
				datastore.put(user);
				return "index";
			}
		}
		return null;
	}

	public boolean verifyUser(String userName, String email) {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter userIdFilter = new FilterPredicate("id", FilterOperator.EQUAL,
				userName);
		Filter emailFilter = new FilterPredicate("email", FilterOperator.EQUAL,
				email);
		Filter comFilter = CompositeFilterOperator.and(userIdFilter,
				emailFilter);
		Query q = new Query("User").setFilter(comFilter);
		PreparedQuery pq = datastore.prepare(q);
		re = pq.countEntities(FetchOptions.Builder.withDefaults());
		if (re == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String login() {
		FacesContext context = FacesContext.getCurrentInstance();
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		if (loginVerify()) {
			Filter emailFilter =
					  new FilterPredicate("email", FilterOperator.EQUAL, user_email);
					Query q = new Query("User").setFilter(emailFilter);
			PreparedQuery pq = datastore.prepare(q);
			Entity result = pq.asSingleEntity();
			String roleHa = (String) result.getProperty("role");
			if(roleHa.equals("Student"))
				return "student_index";
			else if(roleHa.equals("Professor"))
				return "professor_index";
			else 
				return null;
		}
		else {
			FacesMessage message = new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "User Email '" + user_email + " ' or User Password " 
							+ " is not right!",
					"Please enter a correct user email and password!");
			context.addMessage(null, message);
			return null;
		}
	}

	public boolean loginVerify() {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter emailFilter = new FilterPredicate("email", FilterOperator.EQUAL,
				user_email);
		Filter userpasswordFilter = new FilterPredicate("password",
				FilterOperator.EQUAL, user_password);
		Filter comFilter = CompositeFilterOperator.and(emailFilter,
				userpasswordFilter);
		Query q = new Query("User").setFilter(comFilter);
		PreparedQuery pq = datastore.prepare(q);
		re = pq.countEntities(FetchOptions.Builder.withDefaults());
		if (re == 1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean checkId(String id) {
		int re;
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Filter idFilter = new FilterPredicate("id", FilterOperator.EQUAL,
				id);
		Query q = new Query("User").setFilter(idFilter);
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
	
	@SuppressWarnings("unused")
	private Entity getEntity(String key)
	{
	DatastoreService datastore =
	    DatastoreServiceFactory.getDatastoreService();
	    // Get a key that matches this entity:
	    Key enKey = KeyFactory.createKey("User", key);
	    try {
	        return datastore.get(enKey);
	    } catch (EntityNotFoundException e) {
	        // Entity does not exist in DB:
	        return null;
	    }
	}
	
	public String logout() {
		return "index";
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getuser_reenterpassword() {
		return user_reenterpassword;
	}

	public void setuser_reenterpassword(String user_reenterpassword) {
		this.user_reenterpassword = user_reenterpassword;
	}

	public String getuser_lname() {
		return user_lname;
	}

	public void setuser_lname(String user_lname) {
		this.user_lname = user_lname;
	}

	public String getuser_id() {
		return user_id;
	}

	public void setuser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getuser_fname() {
		return user_fname;
	}

	public void setuser_fname(String user_fname) {
		this.user_fname = user_fname;
	}

	public String getuser_email() {
		return user_email;
	}

	public void setuser_email(String user_email) {
		this.user_email = user_email;
	}

	public String getuser_password() {
		return user_password;
	}

	public void setuser_password(String user_password) {
		this.user_password = user_password;
	}

	public studentInfo getsInfo() {
		return sInfo;
	}

	public void setsInfo(studentInfo sInfo) {
		this.sInfo = sInfo;
	}

	public ArrayList<studentInfo> getUsers() {
		return users;
	}

	public void setUsers(ArrayList<studentInfo> users) {
		this.users = users;
	}
	
	public class studentInfo {	
		
		private String id;
		private String fname;
		private String lname;
		private String email;
		private String role;
		
		public studentInfo(String id, String fname, String lname, String email, String role) {
			this.id = id;
			this.fname = fname;
			this.lname = lname;
			this.email = email;
			this.role = role;
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getFname() {
			return fname;
		}
		public void setFname(String fname) {
			this.fname = fname;
		}
		public String getLname() {
			return lname;
		}
		public void setLname(String lname) {
			this.lname = lname;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
	}
}

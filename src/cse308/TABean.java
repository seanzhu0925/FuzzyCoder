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

@ManagedBean(name = "ta")
@SessionScoped
public class TABean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String courseName;
	private String announcement_name;
	private String announcement_content;
	private Date announcement_date;
	private int announcement_id = 100;
	public ArrayList<Announcement> announcementList = new ArrayList<Announcement>();
	
	public String addAnnouncement() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Entity announcement = new Entity("Announcement",
				this.getAnnouncement_id());
		setAnnouncement_id(getAnnouncement_id() + 1);
		announcement.setProperty("name", this.getAnnouncement_name());
		announcement.setProperty("content", this.getAnnouncement_content());
		announcement.setProperty("date", this.getAnnouncement_date());
		announcement.setProperty("course", this.courseName);
		datastore.put(announcement);
		return "ta_announcement";
	}
	
	public String deleteAnnouncement() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext()
				.getRequestParameterMap();
		int id = Integer.parseInt(params.get("id"));
		Key e = KeyFactory.createKey("Announcement", id);

		for (Announcement announcement : announcementList) {
			if (announcement.announcement_id == id) {
				announcementList.remove(announcement);
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
	
	public void listAnnouncement() throws EntityNotFoundException {
		int pageSize = 30;
		FetchOptions fetchOptions = FetchOptions.Builder.withLimit(pageSize);
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();
		Query q = new Query("Announcement");
		PreparedQuery pq = datastore.prepare(q);
		QueryResultList<Entity> results = pq.asQueryResultList(fetchOptions);
		announcementList = new ArrayList<Announcement>();
		for (Entity entity : results) {
			setAnnouncement_id((int) entity.getProperty("id"));
			setAnnouncement_name((String) entity.getProperty("name"));
			setAnnouncement_content((String) entity.getProperty("content"));
			courseName = (String) entity.getProperty("course");
			announcementList.add(new Announcement(getAnnouncement_id(),
					getAnnouncement_name(), getAnnouncement_content(),
					courseName));
		}
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
		return announcementList;
	}

	public void setAnnouncementList(ArrayList<Announcement> announcementList) {
		this.announcementList = announcementList;
	}
	
}

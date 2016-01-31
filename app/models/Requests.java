package models;

import java.util.ArrayList;
import java.util.List;

import play.data.format.Formats;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class Requests extends Model {

	@Id
	public long id;

	public String description;

	public long routeId;

	@ManyToOne
	public RequestsNotification notification;
	
	//public static long lastId;
	
	// default(empty) constructor
	public Requests() {
	}
	
	
	public Requests(String desc, long routeId){
		this.description=desc;
		this.routeId=routeId;
			}

	public static Requests saveToDB() {
		Requests rs = new Requests();
		rs.save();
		return rs;
	}
	
	public static Requests saveToDB(String desc, long routeId) {
		Requests rs = new Requests(desc, routeId);
		rs.save();
		return rs;
	}

	
	/**
	 * Finder for Requests object
	 */
	public static Finder<Long, Requests> find = new Finder<Long, Requests>(
			Requests.class);

	
	public static Requests findById(long id) {
		return find.byId(id);
	}
	
	
	public static List<Requests> getAll() {
		List<Requests> allRequests = new ArrayList<Requests>();
		allRequests = find.all();
		return allRequests;
	}
	
	
	public static long getLastId(){
		long lastId=-99999999;
		for(Requests rs:Requests.find.all()){
			if(rs.id>lastId){
				lastId=rs.id;
			}
		}
		return lastId;
	}
	
}

package models;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class RequestsNotification extends Model{

	@Id
	public long id;
	
	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
	public List<Requests> requestsList;
	
	public RequestsNotification(){
		this.requestsList=new ArrayList<Requests>();
	}
	
	public RequestsNotification(List<Requests> requests){
		this.requestsList=requests;
	}
	
	
	public static RequestsNotification saveToDB(List<Requests> requests){
		RequestsNotification rn=new RequestsNotification(requests);
		rn.save();
		return rn;
	}
	
	public static RequestsNotification saveToDB(){
		RequestsNotification rn=new RequestsNotification();
		rn.save();
		return rn;
	}
	
	/**
	 * Finder for RequestsNotification object
	 */
	public static Finder<Long, RequestsNotification> find = new Finder<Long, RequestsNotification>(
			RequestsNotification.class);

	
	public static RequestsNotification findById(long id){
		return find.byId(id);
	}
	
	public static boolean alreadyExists(Requests rs){
		boolean exists=false;
		for(RequestsNotification rn:RequestsNotification.find.all()){
			for(Requests rnRs:rn.requestsList){
				if(rs.id==rnRs.id){
					exists=true;
					break;
				}
				
			}
		}
		return exists;
	}
	
	public static List<RequestsNotification> getAll(){
		return find.all();
	}
}

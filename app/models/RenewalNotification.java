package models;

import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
public class RenewalNotification extends Model {

	@Id
	public Long id;
	
	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
	public List<VehicleRegistration> registrations;
	
	@OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    public List<Insurance> insurances;
	
	
	public RenewalNotification() {
	this.registrations=new ArrayList<VehicleRegistration>();
	this.insurances=new ArrayList<Insurance>();
	
	}


	public static RenewalNotification saveToDB() {
		RenewalNotification rn = new RenewalNotification();
		rn.save();
		return rn;
	}

	
	/**
	 * Finder for RenewalNotification object
	 */
	public static Finder<Long, RenewalNotification> find = new Finder<Long, RenewalNotification>(
			RenewalNotification.class);

	public static void deleteRenewalNotification(long id) {
		RenewalNotification rn = find.byId(id);
		rn.delete();
	}
	
public static int size(RenewalNotification rn){
	int size=0;
	size=rn.registrations.size()+rn.insurances.size();
	return size;
}
	public static List<RenewalNotification> getNonEmptyNotifications(){
		List<RenewalNotification> allNotifications=RenewalNotification.find.all();
		List<RenewalNotification> nonEmptyNotifications=new ArrayList<RenewalNotification>();
		for(RenewalNotification rn:allNotifications){
			if(rn.insurances.size()>0 || rn.registrations.size()>0){
				nonEmptyNotifications.add(rn);
			}
		}
		return nonEmptyNotifications;
	}
	
	
	public static int numberOfNENotifications(){
		return RenewalNotification.getNonEmptyNotifications().size();
	}
}

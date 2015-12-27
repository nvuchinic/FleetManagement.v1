package models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class NotificationSettings extends Model{
	
	/*@Id
	public long id;
//	private static  NotificationSettings instance = new NotificationSettings();
	//public boolean isNotifOn;
	public int threshold;
	//public boolean emailNotif=false;
	public String timeUnit;
	public boolean registrationNotificationOn;
	public boolean inspectionNotificationOn;
	public boolean insuranceNotificationOn;
	
	private static volatile NotificationSettings _instance = null;
	 
    private  NotificationSettings() {
		// TODO Auto-generated constructor stub
	}  	
    

    public static NotificationSettings getInstance() { 
          if(_instance == null){
                synchronized(NotificationSettings.class) {
                 if (_instance == null) {
                       _instance = new NotificationSettings();
                       _instance.registrationNotificationOn=false;
                       _instance.inspectionNotificationOn=false;
                       _instance.insuranceNotificationOn=false;
                       _instance.threshold=14;
                       _instance.timeUnit="day(s)";
                 }
           } 
           } 
           return _instance;
    }
	   
	
	
	public static void saveToDB(NotificationSettings ns){
		ns.save();
		
	}*/
	
	@Id
	public long id;
	public String name;
	//public boolean isNotifOn;
	public int threshold;
	public String timeUnit;
	public boolean registrationNotificationOn;
	public boolean inspectionNotificationOn;
	public boolean insuranceNotificationOn;
	
	public NotificationSettings() {
		this.name="singleton";
		this.threshold=14;
		this.timeUnit="day(s)";
		this.registrationNotificationOn=false;
		this.inspectionNotificationOn=false;
		this.insuranceNotificationOn=false;
	}


	public static NotificationSettings saveToDB() {
		NotificationSettings ns=new NotificationSettings();
		ns.save();
		return ns;
	}

	/**
	 * Finder for NotificationSettings object
	 */
	public static Finder<Long, NotificationSettings> find = new Finder<Long, NotificationSettings>(
			NotificationSettings.class);

	
	public static synchronized NotificationSettings findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}

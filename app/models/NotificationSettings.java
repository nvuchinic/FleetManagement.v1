package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class NotificationSettings extends Model{

	private static NotificationSettings instance = null;
	public boolean isOn=true;
	public int threshold;
	public boolean emailNotif=false;
	public String timeUnit;
	
	   private NotificationSettings() {
	      // Exists only to defeat instantiation.
	   }
	   public static NotificationSettings getInstance() {
	      if(instance == null) {
	         instance = new NotificationSettings();
	         instance.threshold=14;
	         instance.timeUnit="day(s)";
	      }
	      return instance;
	   }
	   
	   
	   public static NotificationSettings saveToDB(NotificationSettings ns){
		    ns.save();
		    return ns;
	   }
	   
	
}

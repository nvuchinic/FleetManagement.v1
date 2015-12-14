package models;

import javax.persistence.*;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;


/**
 *  helps connect RenewalNotification  and Insurance.
 */
@Entity
@Embeddable
public class NotificationsInsurances extends Model{
	
	public int insuranceId;

	public int notificationId;

	}


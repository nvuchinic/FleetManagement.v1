package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

/**
 *  helps connect RenewalNotification  and VehicleRegistration.
 */
@Entity
@Embeddable
public class NotificationsRegistrations extends Model{

	public int registrationId;

	public int notificationId;

}

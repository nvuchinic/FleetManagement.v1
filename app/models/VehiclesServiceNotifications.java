package models;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import javax.persistence.*;

@Entity
@Embeddable
public class VehiclesServiceNotifications extends Model{
	
	public int vehicle_id;

	public int serviceNotificationSettings_id;
}

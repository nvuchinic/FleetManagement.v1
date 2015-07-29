package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
//import com.avaje.ebean.Model;
import javax.persistence.*;



@Entity
public class Train extends Vehicle{
	
public int numOfWagons;


public Train(String licenseNo, long latitude, long longitude, int numOfWagons){
	super(licenseNo, latitude, longitude);
	this.numOfWagons=numOfWagons;
}

public static Train saveToDB(String licenseNo, long latitude, long longitude, int numOfWagons){
	Train t=new Train(licenseNo, latitude, longitude, numOfWagons);
	t.save();
	return t;
}

}

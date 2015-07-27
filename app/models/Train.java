package models;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

//import com.avaje.ebean.Model;
import javax.persistence.*;

import play.data.validation.Constraints.Required;

@Entity
public class Train extends Vehicle{
public int numOfWagons;


public Train(int numOfWagons){
	this.numOfWagons=numOfWagons;
}
}

package models;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import javax.persistence.*;

@Entity
public class RoutePoint extends Model{

	@Id
	public long id;
	
	public double lat;
	
	public double longt;
	
	public String address;
	
	public String rType;
	
	@ManyToOne
	public Route rpRoute;
	
	//default constructor
	public RoutePoint(){
		
	}
	
	
public RoutePoint(double lat, double longt, String address){
		this.lat=lat;
		this.longt=longt;
		this.address=address;
	}


public RoutePoint(double lat, double longt, String address, Route r){
	this.lat=lat;
	this.longt=longt;
	this.address=address;
	this.rpRoute=r;
}


public static RoutePoint saveToDB(double lat, double longt, String address){
	RoutePoint rp=new RoutePoint(lat, longt, address);
	rp.save();
	return rp;
}

public static RoutePoint saveToDB(double lat, double longt, String address, Route r){
	RoutePoint rp=new RoutePoint(lat, longt, address, r);
	rp.save();
	return rp;
}

}

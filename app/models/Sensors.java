//package models;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.avaje.ebean.Model;
//import com.avaje.ebean.Model.Finder;
//
//import javax.persistence.*;
//
//@Entity
//public class Sensors extends Model{
//
//	@Id
//	public long id;
//	
//	public int accelerometer;
//	
//	public int ambientTemperature;
//	
//	public int gravity;
//	
//	public int gyroscope;
//	
//	public int light;
//	
//	public int linAcceleration;
//	
//	public int magneticField;
//	
//	public int orientation;
//	
//	public int pressure;
//	
//	public int proximity;
//	
//	public int relativeHumidity;
//	
//	public int rotationVector;
//	
//	public int temperature;
//	
//	public double latitude;
//	
//	public double longitude;
//	
//	@OneToOne
//	public Vehicle vehicle;
//	
//	public Sensors(int acc, int ambTemp,int gravity,int gyro, int light,int linAcceler, int magField, int orient, 
//			int pressure, int prox, int relHumidity,int rotVector, int temp, int lat, int longt){
//		this.accelerometer=acc;
//		this.ambientTemperature=ambTemp;
//		this.gravity=gravity;
//		this.gyroscope=gyro;
//		this.light=light;
//		this.linAcceleration=linAcceler;
//		this.magneticField=magField;
//		this.orientation=orient;
//		this.pressure=pressure;
//		this.proximity=prox;
//		this.relativeHumidity=relHumidity;
//		this.rotationVector=rotVector;
//		this.temperature=temp;
//		this.latitude=lat;
//		this.longitude=longt;
//	}
//	
//	public static Sensors saveToDB(int acc, int ambTemp,int gravity,int gyro, int light,int linAcceler, int magField, int orient, 
//			int pressure, int prox, int relHumidity,int rotVector, int temp, int lat, int longt){
//		Sensors s=new Sensors(acc, ambTemp,gravity,gyro, light, linAcceler, magField, orient, pressure, prox, relHumidity, rotVector, temp, lat,longt);
//		s.save();
//		return s;
//	}
//	
//	/**
//	 * Finder for Sensors object
//	 */
//	public static Finder<Long, Sensors> find = new Finder<Long, Sensors>(
//			Sensors.class);
//
//	/**
//	 * Method which finds Sensors object in DB by numberTO
//	 * 
//	 * @param vid
//	 *            of Sensors object
//	 * @return Sensors object
//	 */
//	public static Sensors findById(long id) {
//		return find.byId(id);
//	}
//
//	/**
//	 * Method for deleting Sensors object
//	 * 
//	 * @param id
//	 *            of sensors object
//	 */
//	public static void deleteSensors(long id) {
//		Sensors s = find.byId(id);
//		s.delete();
//	}
//
//	public static List<Sensors> listOfSensors() {
//		List<Sensors> allSensors = new ArrayList<Sensors>();
//		allSensors = find.all();
//		return allSensors;
//
//	}
//}

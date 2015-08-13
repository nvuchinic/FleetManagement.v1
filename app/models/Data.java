/*package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

*//**
 * Data model
 * @author Emir Imamović
 *
 * @param <T>
 *//*

@Entity
@Table(name = "data")
public class Data<T> extends Model {
	
	@Id
	public long id;
	
	
	public Date timestamp;
	
	public T type;
	
	public String name;
	
	public T value;

	*//**
	 * @param timestamp
	 * @param type
	 * @param name
	 * @param value
	 *//*
	public Data(String name, Date timestamp, T type, T value) {
		super();
		this.timestamp = timestamp;
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	*//**
	 * Finder for Data object
	 *//*
	public static Finder<Long, Data> find = new Finder<>(Data.class);
	
	*//**
	 * Method for creating Data object
	 * @param name
	 * @param timestamp
	 * @param type
	 * @param value
	 * @return id if new Data object
	 *//*
	public long createData(String name, Date timestamp, T type, T value) {
		Data<T> d = new Data<T>(name,timestamp, type, value);
		return d.id;		
	}
	
	*//**
	 * Method for deleting Data object
	 * @param id of data object
	 *//*
	public static void deleteData(long id) {
		Data<?> d = find.byId(id);
		d.delete();
	}
	
	*//**
	 * Method which finds Data object in DB by Id
	 * @param id of Data object
	 * @return Data object

	 *//*
	public static Data findById(long id) {

	 */
	//public static Data<?> findById(long id) {
//
//		return find.byId(id);
//	}
//	
//	*//**
//	 * Method which finds Data object in DB by name
//	 * @param name of Data
//	 * @return Data object
//<<<<<<< HEAD
//	 *//*
//	public static Data findByName(String name) {
//=
//	 */
//	public static Data<?> findByName(String name) {
//
//		return find.where().eq("name", name).findUnique();
//	}
//	
//	*//**
//	 * Method which finds Data object in DB by type
//	 * @param type of Data
//	 * @return Data object
//<
//	 *//*
//	public Data findByType(T type) {
//=
//	 */
//	public Data<?> findByType(T type) {
//
//		return find.where().eq("type", type).findUnique();
//	}
//	
//	*//**
//	 * Method which finds List of Data objects
//	 * @return list of Data objects
//	 *//*
//	public static List<Data> listOfDatas() {
//		return find.findList();
//	}
//}
//*/
//*/
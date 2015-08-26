package models;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "task")
public class Task extends Model {
	
	@Id
	public long id;
	
	public String name;
	
	public Date dateTime;
	
	public String description;
	
	@ManyToOne
	public WorkOrder workOrder;

	/**
	 * Finder for Route object
	 */
	public static Finder<Long, Task> find = new Finder<Long, Task>(Task.class);
	
	/**
	 * @param name
	 * @param dateTime
	 * @param description
	 */
	public Task(String name, Date dateTime, String description, WorkOrder workOrder) {
		super();
		this.name = name;
		this.dateTime = dateTime;
		this.description = description;
		this.workOrder = workOrder;
	}
	
	/**
	 * @param name
	 */
	public Task(String name) {
		super();
		this.name = name;
		this.dateTime = new java.sql.Date(new java.util.Date().getTime());
		this.description = description;
	}
	
	/**
	 * @param name
	 * @param dateTime
	 * @param description
	 */
	public Task(String name, Date dateTime, String description) {
		super();
		this.name = name;
		this.dateTime = dateTime;
		this.description = description;
	}
	
	/**
	 * Method for creating and saving Task object in DB
	 * @param name
	 * @param dateTime
	 * @param description
	 * @return id of new Task object
	 */
	public static long createTask(String name, Date dateTime, String description, WorkOrder workOrder) {
		Task t = new Task(name, dateTime, description, workOrder);
		t.save();
		return t.id;
	}
	
	/**
	 * Method for creating and saving Task object in DB
	 * @param name
	 * @param description
	 * @return id of new Task object
	 */
	public static long createTask(String name, Date dateTime, String description) {
		Task t = new Task(name, dateTime, description);
		t.save();
		return t.id;
	}
	
	/**
	 * Method which finds certain Task object by id
	 * @param id
	 * @return Task object
	 */
	public static Task findById(long id) {
		Task t = find.byId(id);
		return t;
	}
	
	/**
	 * Method which finds certain Task object by name
	 * @param name
	 * @return Task object
	 */
	public static Task findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
	
	/**
	 * Method for deleting Task
	 * @param id
	 */
	public static void deleteTask(long id) {
		Task t = find.byId(id);
		if(t != null)
		t.delete();
	}
	
	/**
	 * Method which finds all tasks in DB
	 * @return
	 */
	public static List<Task> tasksList() {
		return find.all();
	}
}

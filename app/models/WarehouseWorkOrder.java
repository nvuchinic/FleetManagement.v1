package models;

import javax.persistence.*;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "warehouseWorkOrder")
public class WarehouseWorkOrder extends Model {

	@Id
	public long id;
	
	public String content;
	
	public String location;

	/**
	 * @param content
	 * @param location
	 */
	public WarehouseWorkOrder(String content, String location) {
		super();
		this.content = content;
		this.location = location;
	}
	
	public static long createWarehouseWorkOrder(String content, String location) {
		WarehouseWorkOrder wwo = new WarehouseWorkOrder(content, location);
		wwo.save();
		return wwo.id;
	}
	
	/**
	 * finder for Client object
	 */
	public static Finder<Long, WarehouseWorkOrder> find = new Finder<Long, WarehouseWorkOrder>(WarehouseWorkOrder.class);
}

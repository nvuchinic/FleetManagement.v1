package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import models.Fleet;
import models.Owner;

import models.Type;
import models.Vehicle;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

import play.Logger;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

/**
 * Controller for Vehicle model
 * @author Emir Imamović
 *
 */
public class VehicleController extends Controller {

	static Form<Vehicle> vehicleForm = Form.form(Vehicle.class);

	/**
	 * Finder for Vehicle class
	 */
	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(Long.class,
			Vehicle.class);
	
	/**
	 * Renders the 'add vehicle' page
	 * @return
	 */
	public Result addVehicleView() {
		return ok(addVehicleForm.render());
	}

	/**
	 * Finds vehicle using id and shows it 
	 * @param id - Vehicle id
	 * @return redirect to the vehicle profile
	 */
	public Result showVehicle(long id) {
		Vehicle v = Vehicle.findById(id);
		if (v == null) {
			Logger.error("error", "Vehicle null at showVehicle()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}	
		return ok(showVehicle.render(v));
	}

	/**
	 * Delete Vehicle using id
	 * @param id - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteVehicle(long id) {
		try {
			Vehicle v = Vehicle.findById(id);
			Logger.info("Deleted vehicle: \"" + v.typev.name + "\"");
			Vehicle.deleteVehicle(id);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}

	/**
	 * Renders the view of a Vehicle. Method receives the id of the vehicle and
	 * finds the vehicle by id and send's the vehicle to the view.
	 * @param id long
	 * @return Result render VehicleView
	 */
	public Result editVehicleView(long id) {
		Vehicle v = Vehicle.findById(id);
		// Exception handling.
		if (v == null) {
			flash("error", "Vehicle is not exists");
			return redirect("/");
		}
		Form<Vehicle> form = Form.form(Vehicle.class).fill(v);
		return ok(editVehicleView.render(v));

	}

	/**
	 * Edit Vehicle Method receives an id, finds the specific vehicle and renders
	 * the edit View for the vehicle. If any error occurs, the view is rendered
	 * again.
	 * @param id of vehicle
	 * @return Result render the vehicle edit view
	 */
	public Result editVehicle(long id) {
		Form<Vehicle> form = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			v.vid = vehicleForm.bindFromRequest().data().get("vid");

			String ownerName = vehicleForm.bindFromRequest().data().get("ownerName");
				
			String ownerEmail = vehicleForm.bindFromRequest().data().get("ownerEmail");
			
			HashMap<String, String> description = new HashMap<String, String>();
			
			String fleetName = vehicleForm.bindFromRequest().field("fleetName").value();
			
			Fleet f;
			 if(fleetName != null && Fleet.findByName(fleetName) == null) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			} if(fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				f = new Fleet();
				f.name = "";
				f.save();
			}
			
			Type t;
			String newType= vehicleForm.bindFromRequest().field("newType").value();
			String type = vehicleForm.bindFromRequest().field("typeName").value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.description = description;
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return ok(editVehicleView.render(v));
				}
				t = new Type(newType, description);
				t.save();
			}
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			
			v.typev = t;
			
			v.owner = o;
			
			v.fleet = f;
			
			f.numOfVehicles = f.vehicles.size();
			Vehicle.listOfUnnusedVehicles().remove(v);
			f.save();
			
			v.save();
			
			Logger.info(session("name") + " updated vehice: " + v.id);
			flash("success", v.vid + " successfully updated!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}
	
	/**
	 * First checks if the vehicle form has errors. Creates a new vehicle or
	 * renders the view again if any error occurs.
	 * @return redirect to create vehicle view
	 * @throws ParseException
	 */
	public Result addVehicle() {

		Form<Vehicle> addVehicleForm = Form.form(Vehicle.class).bindFromRequest();
		
		if (addVehicleForm.hasErrors() || addVehicleForm.hasGlobalErrors()) {
			Logger.debug("Error at adding vehicle");
			flash("error", "Error at vehicle form!");
			return redirect("/addVehicle");
		}

		try{	
			
			String vid = addVehicleForm.bindFromRequest().get().vid;
			String ownerName = addVehicleForm.bindFromRequest().data().get("ownerName");
			String ownerEmail = addVehicleForm.bindFromRequest().data().get("ownerEmail");
			String chassis = addVehicleForm.bindFromRequest().field("chassis").value();
			if(chassis.isEmpty())
				chassis = "";
			String engineNumber = addVehicleForm.bindFromRequest().field("engineNumber").value();
			if(engineNumber.isEmpty())
				engineNumber = "";
			String cCm = addVehicleForm.bindFromRequest().field("cCm").value();
			if(cCm.isEmpty())
				cCm = "";
			String vehicleBrand = addVehicleForm.bindFromRequest().field("vehicleBrand").value();
			if(vehicleBrand.isEmpty())
				vehicleBrand = "";
			String model = addVehicleForm.bindFromRequest().field("model").value();
			if(model.isEmpty())
				model = "";
			String color = addVehicleForm.bindFromRequest().field("color").value();
			if(color.isEmpty())
				color = "";
			String fuelType = addVehicleForm.bindFromRequest().field("fuelType").value();
			if(fuelType.isEmpty())
				fuelType = "";
			String tankage = addVehicleForm.bindFromRequest().field("tankage").value();
			if(tankage.isEmpty())
				tankage = "";
			String currentMileage = addVehicleForm.bindFromRequest().field("currentMileage").value();
			if(currentMileage.isEmpty())
				currentMileage = "";
			String productionYear = addVehicleForm.bindFromRequest().field("productionYear").value();
			if(productionYear.isEmpty())
				productionYear = "";
			String productionState = addVehicleForm.bindFromRequest().field("productionState").value();
			if(productionState.isEmpty())
				productionState = "";
			HashMap<String, String> description = new HashMap<String, String>();
			HashMap<String, String> tm = new HashMap<String, String>();
			
				 tm.put("Chassis", chassis);
				 tm.put("Engine Number" , engineNumber);
				 tm.put("cCm" , cCm);
				 tm.put("Vehicle Brand", vehicleBrand);
				 tm.put("Model", model);
				 tm.put("Color", color);
				 tm.put("Fuel Type", fuelType);
				 tm.put("Tankage", tankage);
				 tm.put("Current Mileage", currentMileage);
				 tm.put("Production Year", productionYear);
				 tm.put("Production State", productionState);
				description.putAll(tm);
			
			if(vid.isEmpty()) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");
				
			}
			if(Vehicle.findByVid(vid) != null) {
				flash("error", "Vehicle with that vid already exists");
				return redirect("/addVehicle");
			}
			
			Type t;
			String newType= vehicleForm.bindFromRequest().field("newType").value();
			String type = vehicleForm.bindFromRequest().field("typeName").value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.description = description;
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return redirect("/addVehicle");
				}
				t = new Type(newType, description);
				t.description = description;
				t.save();
			}
			
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				 o = new Owner(ownerName, ownerEmail);
				 o.save();
			} 
				o = Owner.findByName(ownerName);
				if(vid.equals(null)) {
					flash("error", "Empty vehicle ID!");
					return redirect("/addVehicle");
				}
		
				long id = Vehicle.createVehicle(vid, o, t);
				Vehicle v = Vehicle.findById(id);
				v.values = Vehicle.treeMapToList(description);
				v.save();
				Logger.info(session("name") + " created vehicle ");
				flash("success",  "Vehicle successfully added!");
				System.out.println(t.description.values() + " ////////////////////////////////////////////");
				return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
			
		}catch(Exception e){
		flash("error", "Error at adding vehicle");
		Logger.error("Error at addVehicle: " + e.getMessage(), e);
		return redirect("/addVehicle");
	   }
	}
	
	public Result listVehicles() {
		if(Vehicle.listOfVehicles() == null)
			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}
	
}

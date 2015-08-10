package controllers;

import java.util.ArrayList;
import java.util.Date;

import models.Driver;
import models.Fleet;
import models.Owner;
import models.TruckC;
import models.Type;
import models.Vehicle;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
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
	 * Renders the 'create truckC' page
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
			return redirect("/");
		} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return redirect("/");
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
		DynamicForm updateVehicleForm = Form.form().bindFromRequest();
		Form<Vehicle> form = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			v.vid = vehicleForm.bindFromRequest().data().get("vid");

			String ownerName = vehicleForm.bindFromRequest().data().get("ownerName");
				
			String ownerEmail = vehicleForm.bindFromRequest().data().get("ownerEmail");
			
			String typeName = vehicleForm.bindFromRequest().data().get("typeName");
			
			String description = vehicleForm.bindFromRequest().data().get("typeDescription");
			
			String fleetName = vehicleForm.bindFromRequest().data().get("fleetName");
			
			Fleet f;
			if(Fleet.findByName(fleetName) == null) {
				f = new Fleet("", 0);
				f.save();
			} else {
				f = Fleet.findByName(fleetName);
				f.save();
			}
			
			Type t;
			if(Type.findByName(typeName) == null) {
				t = new Type(typeName, description);
				t.save();
			} else {
				t = Type.findByName(typeName);
				t.description = description;
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
			
			v.save();
			
			Logger.info(session("name") + " updated vehice: " + v.id);
			flash("success", v.vid + " successfully updated!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return redirect("/");
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
			String name = addVehicleForm.bindFromRequest().get().name;
			String ownerName = addVehicleForm.bindFromRequest().data().get("ownerName");
			String ownerEmail = addVehicleForm.bindFromRequest().data().get("ownerEmail");
			String  typeName= addVehicleForm.bindFromRequest().data().get("typeName");
			String typeDescription = addVehicleForm.bindFromRequest().data().get("typeDescription");
			
			String fleetName = addVehicleForm.bindFromRequest().data().get("fleetName");
			
			Fleet f;
			if(Fleet.findByName(fleetName) == null) {
				f = new Fleet("", 0);
				f.save();
			} else {
			
			f = Fleet.findByName(fleetName);
			}
			Type t;
			if(Type.findByName(typeName) == null) {
			 t = new Type(typeName, typeDescription);
			 t.save();
			}
				t = Type.findByName(typeName);
			
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				 o = new Owner(ownerName, ownerEmail);
				 o.save();
			} 
				o = Owner.findByName(ownerName);
			
		
				Vehicle.createVehicle(vid,name, o, t, f);

				Logger.info(session("name") + " created vehicle ");
				flash("success",  "Vehicle successfully added!");
				return redirect("/allVehicles");
			
		}catch(Exception e){
		flash("error", "Error at adding vehicle afasdfasdffsadfasdf");
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

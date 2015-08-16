package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Driver;
import models.Fleet;
import models.Owner;
//import models.TruckC;
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
 * 
 * @author Emir Imamović
 *
 */
public class VehicleController extends Controller {

	static Form<Vehicle> vehicleForm = Form.form(Vehicle.class);

	/**
	 * Finder for Vehicle class
	 */
	// public static Finder<Long, Vehicle> find = new Finder<Long,
	// Vehicle>(Long.class,
	// Vehicle.class);
	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(
			Vehicle.class);

	/**
	 * Renders the 'create truckC' page
	 * 
	 * @return
	 */
	public Result addVehicleView() {
		return ok(addVehicleForm.render());
	}

	/**
	 * Finds vehicle using id and shows it
	 * 
	 * @param id
	 *            - Vehicle id
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
	 * 
	 * @param id
	 *            - Vehicle id (long)
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
	 * 
	 * @param id
	 *            long
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
	 * Edit Vehicle Method receives an id, finds the specific vehicle and
	 * renders the edit View for the vehicle. If any error occurs, the view is
	 * rendered again.
	 * 
	 * @param id
	 *            of vehicle
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
			String name=vehicleForm.bindFromRequest().get().name;
			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			String typeName = vehicleForm.bindFromRequest().data()
					.get("typeName");

			String description = vehicleForm.bindFromRequest().data()
					.get("typeDescription");

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();

			Fleet f;
			if (fleetName != null && Fleet.findByName(fleetName) == null) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			}
			if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				f = new Fleet();
				f.name = "";
				f.save();
			}

			Type t;
			String newType = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return ok(editVehicleView.render(v));
				} else if (Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
					t = new Type(newType, description);
					t.save();
				}
			}

			Owner o;
			if (Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			v.typev = t;
			v.name=name;
			v.owner = o;
			v.fleet = f;
			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
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

	/*public Result editVehicle(long id) {
		Form<Vehicle> form = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			v.vid = vehicleForm.bindFromRequest().data().get("vid");
			String name=vehicleForm.bindFromRequest().get().name;
			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			String typeName = vehicleForm.bindFromRequest().data()
					.get("typeName");

			String description = vehicleForm.bindFromRequest().data()
					.get("typeDescription");

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();

			Fleet f;
			if (fleetName != null && Fleet.findByName(fleetName) == null) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			}
			if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				f = new Fleet();
				f.name = "";
				f.save();
			}

			Type t;
			String newType = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return ok(editVehicleView.render(v));
				} else if (Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
					t = new Type(newType, description);
					t.save();
				}
			}

			Owner o;
			if (Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			v.typev = t;
			v.name=name;
			v.owner = o;
			v.fleet = f;
			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
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
	}*/

	public Result addToFleetView(long id){
		Vehicle v=Vehicle.findById(id);
		return ok(addToFleetView.render(v));

	}
	
	public Result addToFleet(long id) {
		Form<Vehicle> form = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

		//	v.vid = vehicleForm.bindFromRequest().data().get("vid");
			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();

			Fleet f;
			/*if (fleetName != null && Fleet.findByName(fleetName) == null) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			}*/
			if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				f = new Fleet();
				f.name = "";
				f.save();
			}
			v.fleet = f;
			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
			f.save();
			v.save();

			Logger.info(session("name") + " added vehicle: " + v.id+" to fleet");
			flash("success", v.vid + " successfully added to fleet!");
			return ok(showFleet.render(f));
		} catch (Exception e) {
			flash("error", "Error at adding vehicle to fleet");
			Logger.error("Error at addingVehicleToFleet: " + e.getMessage(), e);
			return ok(showVehicle.render(v));
		}
	}

	
	/**
	 * First checks if the vehicle form has errors. Creates a new vehicle or
	 * renders the view again if any error occurs.
	 * 
	 * @return redirect to create vehicle view
	 * @throws ParseException
	 */
	public Result addVehicle() {

		Form<Vehicle> addVehicleForm = Form.form(Vehicle.class)
				.bindFromRequest();

		if (addVehicleForm.hasErrors() || addVehicleForm.hasGlobalErrors()) {
			Logger.debug("Error at adding vehicle");
			flash("error", "Error at vehicle form!");
			return redirect("/addVehicle");
		}

		try {

			String vid = addVehicleForm.bindFromRequest().get().vid;
			String name=vehicleForm.bindFromRequest().get().name;

			String ownerName = addVehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = addVehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			String typeDescription = addVehicleForm.bindFromRequest().data()
					.get("typeDescription");
			if (vid.isEmpty()) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");

			}
			if (Vehicle.findByVid(vid) != null) {
				flash("error", "Vehicle with that vid already exists");
				return redirect("/addVehicle");
			}

			Type t;
			String newType = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return redirect("/addVehicle");
				} else if (Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
					t = new Type(newType, typeDescription);
					t.save();
				}
			}

			Owner o;
			if (Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			}
			o = Owner.findByName(ownerName);
			if (vid.equals(null)) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");
			}

			Vehicle.createVehicle(vid,name, o, t);

			Logger.info(session("name") + " created vehicle ");
			flash("success", "Vehicle successfully added!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));

		} catch (Exception e) {
			flash("error", "Error at adding vehicle");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}

	public Result listVehicles() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.find.all();
		// flash("addVehicleForMaintenance",
		// "For adding Vehicle Maintenance choose vehicle");
		return ok(listAllVehicles.render(allVehicles));
	}

}

package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import models.Description;
import models.Fleet;
import models.Owner;
import models.Type;
import models.Vehicle;
import play.db.ebean.Model;
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
		Vehicle v = Vehicle.find.byId(id);

		if (v == null) {
			Logger.error("error", "Vehicle null at showVehicle()");
			flash("error", "Something went wrong!");
			return ok(showVehicle.render(v));
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
			Logger.info("Deleted vehicle: \"" + v.typev + "\"");
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
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			v.vid = vehicleForm.bindFromRequest().data().get("vid");
			String name = vehicleForm.bindFromRequest().get().name;

			String typeName = vehicleForm.bindFromRequest().data()
					.get("typeName");

			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");

			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();

			Fleet f;

			if (fleetName != null && Fleet.findByName(fleetName) == null
					&& !fleetName.isEmpty()) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			} else if (fleetName != null && Fleet.findByName(fleetName) != null) {
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
				} else if(Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
				t = new Type(newType);
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
			
			v.name = name;

			v.owner = o;
			v.fleet = f;

			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
			f.save();
			
			v.save();
			
			List<Description> description = new ArrayList<Description>();
			List<Description> desc = Vehicle.findByType(t).get(0).description;
			for (int j = 0; j < desc.size(); j++) {

				String value = vehicleForm.bindFromRequest()
						.field(desc.get(j).propertyName).value();
				if (value != null) {
					Description d = Description.findById(Description.createDescription(desc.get(j).propertyName, value));
					description.add(d);
				
				}
				if(value == null) {
					Description d = Description.findById(Description.createDescription(desc.get(j).propertyName, desc.get(j).propertyValue));
					description.add(d);
				}
			}
			
			String count = dynamicForm.bindFromRequest().get("counter");

			if (count == "0") {
				String pn = dynamicForm.bindFromRequest().get("propertyName0");
				String pv = dynamicForm.bindFromRequest().get("propertyValue0");
				if(!pn.isEmpty() && !pv.isEmpty()) {
					Description d = Description.findById(Description
							.createDescription(pn, pv));
					description.add(d);
				}
			} else {
				int num = Integer.parseInt(count);
				for (int i = 0; i <= num; i++) {
					String pn = dynamicForm.bindFromRequest().get(
							"propertyName" + i);
					String pv = dynamicForm.bindFromRequest().get(
							"propertyValue" + i);
					if(!pn.isEmpty() && !pv.isEmpty()) {
						Description d = Description.findById(Description
								.createDescription(pn, pv));
				
						description.add(d);
						}
					}
				}
			
			v.description = description;
			v.save();
			
			Logger.info(session("name") + " updated vehicle: " + v.id);
			System.out.println(v.description.size() + "///////////////");
			flash("success", v.typev.name + " " + v.description.get(0).propertyValue + " " + v.description.get(0).propertyValue + " successfully updated!");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}

	public Result addToFleetView(long id) {
		Vehicle v = Vehicle.findById(id);
		return ok(addToFleetView.render(v));

	}

	public Result removeFromFleet(long id) {
		Vehicle v = Vehicle.findById(id);
		v.fleet = null;
		v.isAsigned = false;
		v.save();
		flash("success", "Vehicle successfully removed from fleet");

		return redirect("/allVehicles");
	}

	public Result addToFleet(long id) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();
			String t = vehicleForm.bindFromRequest().field("t").value();
			Fleet f;
			if (Fleet.findByName(fleetName) == null) {
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
			v.fleet = f;
			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
			f.save();
			v.save();

			Logger.info(session("name") + " added vehicle: " + v.id
					+ " to fleet");
			flash("success", v.vid + " successfully added to fleet!");
			List<Vehicle> allVehicles = new ArrayList<Vehicle>();
			allVehicles = Vehicle.find.all();
			System.out.println("/////////////////////" + t
					+ "//////////////////////////");
			return ok(listAllVehicles.render(allVehicles));
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

		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();

//		if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
//			Logger.debug("Error at adding vehicle");
//			flash("error", "Error at vehicle form!");
//			return redirect("/addVehicle");
//		}

		try {
			boolean isLinkable = vehicleForm.bindFromRequest().get().isLinkable;
			String vid = vehicleForm.bindFromRequest().get().vid;
			String name = vehicleForm.bindFromRequest().get().name;
			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			if (vid.isEmpty()) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");

			}
			if (Vehicle.findByVid(vid) != null) {
				flash("error", "Vehicle with that vid already exists");
				return redirect("/addVehicle");
			}

			Type t;
			String newType= vehicleForm.bindFromRequest().field("newType").value();
			String type = vehicleForm.bindFromRequest().field("typeName").value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return redirect("/addVehicle");
				} else if(Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
					
				} else {
				t = new Type(newType);
				t.save();
				}
			}

			t.save();

		

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

			Vehicle v = Vehicle
					.findById(Vehicle.createVehicle(vid, name, o, t));
			v.isLinkable = isLinkable;
			v.description = Vehicle.findByType(t).get(0).description;
			t.vehicles.add(v);
			t.save();
			v.save();
			
			
			Logger.info(session("name") + " created vehicle ");
			System.out.println(v.description.size()
					+ "////////////////////////");
			return ok(editVehicleView.render(v));

		} catch (Exception e) {
			flash("error", "Error at adding vehicle");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}

	public Result listVehicles() {
		if (Vehicle.listOfVehicles() == null) {

			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		}
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}

}

package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import models.Fleet;
import models.Owner;
import models.Type;
import models.Vehicle;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class FleetController extends Controller {

	static Form<Fleet> fleetForm = Form.form(Fleet.class);

	/**
	 * Finder for Fleet class
	 */
	//public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(
	//		Long.class, Fleet.class);
	public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(Fleet.class);

	/**
	 * Renders the 'add fleet' page
	 * 
	 * @return
	 */
	public Result addFleetView() {
		return ok(addFleetForm.render());
	}

	/**
	 * Finds fleet using id and shows it
	 * 
	 * @param id
	 *            - Fleet id
	 * @return redirect to the fleet view
	 */
	public Result showFleet(long id) {
		Fleet f=new Fleet();
		f = Fleet.find.byId(id);
		if (f == null) {
			Logger.error("error", "Fleet null at showFleet()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		//for debugging
		System.out.println("FLOTA SADRZI "+f.vehicles.size()+" VOZILA");
		return ok(showFleet.render(f));
	}

	/**
	 * Delete Fleet using id
	 * 
	 * @param id
	 *            - Fleet id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteFleet(long id) {
		try {
			Fleet f = Fleet.findById(id);
			Logger.info("Deleted fleet: \"" + f.name + "\"");
			Fleet.deleteFleet(id);
			return ok(listAllFleets.render(Fleet.listOfFleets()));
		} catch (Exception e) {
			flash("error", "Error at delete Fleet!");
			Logger.error("Error at delete Fleet: " + e.getMessage());
			return ok(listAllFleets.render(Fleet.listOfFleets()));
		}
	}

	/**
	 * Renders the view of a Fleet. Method receives the id of the fleet and
	 * finds the fleet by id and send the fleet to the view.
	 * 
	 * @param id
	 *            long
	 * @return Result render Fleet View
	 */
	public Result editFleetView(long id) {
		Fleet f = Fleet.findById(id);
		// Exception handling.
		if (f == null) {
			flash("fleetEditError", "Fleet doesn't exist");
			return redirect("/");
		}
		Form<Fleet> form = Form.form(Fleet.class).fill(f);
		return ok(editFleetView.render(f));

	}

	/**
	 * Edit Fleet Method receives an id, finds the specific fleet and renders
	 * the edit View for the fleet. If any error occurs, the view is rendered
	 * again.
	 * 
	 * @param id
	 *            of fleet
	 * @return Result render the fleet edit view
	 */
	public Result editFleet(long id) {
		Form<Fleet> form = Form.form(Fleet.class).bindFromRequest();
		Fleet f = Fleet.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Fleet update error");
				flash("error", "Error in fleet form");
				return ok(editFleetView.render(f));
			}
			f.name = fleetForm.bindFromRequest().get().name;
			f.departure = fleetForm.bindFromRequest().get().departure;
			f.arrival = fleetForm.bindFromRequest().get().arrival;
			f.pickupPlace = fleetForm.bindFromRequest().get().pickupPlace;
			f.returnPlace = fleetForm.bindFromRequest().get().returnPlace;
			
			f.save();
			Logger.info(session("name") + " updated fleet: " + f.name);
			flash("success", f.name + " successfully updated!");
			return ok(listAllFleets.render(Fleet.listOfFleets()));
		} catch (Exception e) {
			flash("error", "Error at editing fleet");
			Logger.error("Error at updateFleet: " + e.getMessage(), e);
			return ok(editFleetView.render(f));
		}
	}

	/**
	 * First checks if the fleet form has errors. Creates a new fleet or renders
	 * the view again if any error occurs.
	 * 
	 * @return redirect to create fleet view
	 * @throws ParseException
	 */
	public Result addFleet() {

		Form<Fleet> addFleetForm = Form.form(Fleet.class).bindFromRequest();

		if (addFleetForm.hasErrors() || addFleetForm.hasGlobalErrors()) {
			Logger.debug("Error at adding fleet");
			flash("error", "Error at fleet form!");
			return redirect("/addFleet");
		}

		try {


			String name = addFleetForm.bindFromRequest().field("name").value();

			long numOfVehicles = 0;
			
			if(Fleet.findByName(name) != null) {
				Logger.debug("Error at adding fleet");
				flash("error", "Fleet with that name already exists!");
				return redirect("/addFleet");
				
			}
			Date departure = fleetForm.bindFromRequest().get().departure;
			Date arrival = fleetForm.bindFromRequest().get().arrival;
			java.sql.Date sqlDate = new java.sql.Date(departure.getTime());
			java.sql.Date sqlDate1 = new java.sql.Date(arrival.getTime());
			String pickupPlace = fleetForm.bindFromRequest().field("pickupPlace").value();
			String returnPlace = fleetForm.bindFromRequest().field("returnPlace").value();
			
			Fleet.createFleet(name, numOfVehicles, sqlDate, sqlDate1, pickupPlace, returnPlace);

			Logger.info(session("name") + " created fleet ");
			flash("success", "Fleet successfully added!");
			return ok(listAllFleets.render(Fleet.listOfFleets()));

		} catch (Exception e) {
			flash("error", "Error at adding fleet");
			Logger.error("Error at addFleet: " + e.getMessage(), e);
			return redirect("/addFleet");
		}
	}

	public Result listFleets() {
		if (Fleet.listOfFleets() == null)
			return ok(listAllFleets.render(new ArrayList<Fleet>()));
		return ok(listAllFleets.render(Fleet.listOfFleets()));
	}

	/**
	 * Delete Vehicle using id
	 * 
	 * @param id
	 *            - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteVehicle(long vid, long fid) {
		try {
			Vehicle v = Vehicle.findById(vid);
			Fleet f = Fleet.findById(fid);
			f.vehicles.remove(v);
			v.isAsigned = false;
			v.fleet = null;
			v.save();
			f.save();
			Logger.info("Deleted vehicle: \"" + v.typev.name + "\"");
			return ok(editFleetView.render(f));
			} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}

	public Result addVehicles(long id) {

		Fleet f = Fleet.findById(id);
		try {
			if (fleetForm.hasErrors() || fleetForm.hasGlobalErrors()) {
				Logger.info("Fleet update error");
				flash("error", "Error in fleet form");
				return ok(editFleetView.render(f));
			}

			
			Vehicle v = null;
			if (fleetForm.bindFromRequest().data().get("vehicleID") == null) {
				return ok(editFleetView.render(f));
			}
			
			String t = fleetForm.bindFromRequest().field("t").value();
			
			String[] vids = t.split(",");
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			String vi = null;
			for(int i = 0; i < vids.length; i++) {
				vi = vids[i];
				
			if (Vehicle.findByVid(vi) != null) {
				v = Vehicle.findByVid(vi);
				
				vehicles.add(v);
				}
			
			if (v.fleet != null) {
				Logger.info("Fleet update error");
				flash("error", "Vehicle is already in fleet!");
				return ok(editFleetView.render(f));
			}
			f.vehicles.addAll(vehicles);
			f.numOfVehicles = f.vehicles.size();
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.save();
		    }
			Logger.info(session("name") + " updated fleet: " + f.name);
			flash("success", f.name + " successfully updated!");
			return ok(editFleetView.render(f));

		} catch (Exception e) {
			flash("error", "Error at adding vehicles.That vehicle does not exists!");
			Logger.error("Error at updateFleet: " + e.getMessage(), e);
			return ok(editFleetView.render(f));
		}

	}

}

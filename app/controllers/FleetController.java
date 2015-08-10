package controllers;

import java.util.ArrayList;
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
	public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(Long.class,
			Fleet.class);
	
	
	/**
	 * Renders the 'add fleet' page
	 * @return
	 */
	public Result addFleetView() {
		return ok(addFleetForm.render());
	}

	/**
	 * Finds fleet using id and shows it 
	 * @param id - Fleet id
	 * @return redirect to the fleet view
	 */
	public Result showFleet(long id) {
		Fleet f = Fleet.find.byId(id);
		if (f == null) {
			Logger.error("error", "Fleet null at showFleet()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		return ok(showFleet.render(f));
	}

	/**
	 * Delete Fleet using id
	 * @param id - Fleet id (long)
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
	 * @param id long
	 * @return Result render Fleet View
	 */
	public Result editFleetView(long id) {
		Fleet f = Fleet.findById(id);
		// Exception handling.
		if (f == null) {
			flash("error", "Fleet is not exists");
			return redirect("/");
		}
		Form<Fleet> form = Form.form(Fleet.class).fill(f);
		return ok(editFleetView.render(f));

	}

	/**
	 * Edit Fleet Method receives an id, finds the specific fleet and renders
	 * the edit View for the fleet. If any error occurs, the view is rendered
	 * again.
	 * @param id of fleet
	 * @return Result render the fleet edit view
	 */
	public Result editFleet(long id) {
		DynamicForm updateFleetForm = Form.form().bindFromRequest();
		Form<Fleet> form = Form.form(Fleet.class).bindFromRequest();
		Fleet f = Fleet.findById(id);
		try {
			if (form.hasErrors() || form.hasGlobalErrors()) {
				Logger.info("Fleet update error");
				flash("error", "Error in fleet form");
				return ok(editFleetView.render(f));
			}

			f.name = fleetForm.bindFromRequest().get().name;

			
			
			String vid = fleetForm.bindFromRequest().data().get("vehicleID");
			
			Vehicle v = null;
			 if(Vehicle.findByVid(vid) != null) {
				v = Vehicle.findByVid(vid);
				v.save();
			}
			 if(Vehicle.findByVid(vid).fleet != null) {
					Logger.info("Fleet update error");
					flash("error", "Vehicle can not be in two fleets in same time!");
					return ok(editFleetView.render(f));
				}
			f.vehicles.add(v);
			f.numOfVehicles = f.vehicles.size();
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
	 * First checks if the fleet form has errors. Creates a new fleet or
	 * renders the view again if any error occurs.
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

		try{	
			
			String name = addFleetForm.bindFromRequest().get().name;
			long numOfVehicles = 0;
			
		
				Fleet.createFleet(name, numOfVehicles);

				Logger.info(session("name") + " created fleet ");
				flash("success",  "Fleet successfully added!");
				return redirect("/");
			
		}catch(Exception e){
		flash("error", "Error at adding fleet");
		Logger.error("Error at addFleet: " + e.getMessage(), e);
		return redirect("/addFleet");
	   }
	}
	
	public Result listFleets() {
		if(Fleet.listOfFleets() == null)
			return ok(listAllFleets.render(new ArrayList<Fleet>()));
		return ok(listAllFleets.render(Fleet.listOfFleets()));
	}
	
	
	public Result addVehicles(long id) {
		Fleet f = Fleet.findById(id);
        Form<Fleet> addFleetForm = Form.form(Fleet.class).bindFromRequest();
		
		if (addFleetForm.hasErrors() || addFleetForm.hasGlobalErrors()) {
			Logger.info("Fleet update error");
			flash("error", "Error in fleet form");
			return ok(editFleetView.render(f));
		}
		try{	
			
			String vehicleId = addFleetForm.bindFromRequest().field("vehicleID").value();			
			Vehicle v;
			if(Vehicle.findByVid(vehicleId) == null) {
				flash("error", "Vehicle is not exists");
				return ok(editFleetView.render(f));
			} else {
				v = Vehicle.findByVid(vehicleId);
			}
				f.vehicles.add(v);
				f.save();
				Logger.info(session("name") + " added vehicle ");
				flash("success",  "Vehicle successfully added!");
				return ok(editFleetView.render(f));
			
		}catch(Exception e){
		flash("error", "Error at adding vehicle");
		Logger.error("Error at addVehicle: " + e.getMessage(), e);
		return ok(editFleetView.render(f));
	   }
	}
	

	/**
	 * Delete Vehicle using id
	 * @param id - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteVehicle(long vid, long fid) {
		try {
			Vehicle v = Vehicle.findById(vid);
			Fleet f = Fleet.findById(fid);
			f.vehicles.remove(v);
			v.fleet = null;
			v.save();
			f.save();
			Logger.info("Deleted vehicle: \"" + v.typev.name + "\"");
			return ok(listAllFleets.render(Fleet.listOfFleets()));
		} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}
}

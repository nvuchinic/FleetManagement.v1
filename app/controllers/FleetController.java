package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import com.avaje.ebean.Model.Finder;

import models.Fleet;
import models.Owner;
import models.TravelOrder;
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
	// public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(
	// Long.class, Fleet.class);
	public static Finder<Long, Fleet> find = new Finder<Long, Fleet>(
			Fleet.class);

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
		Fleet f = new Fleet();
		f = Fleet.find.byId(id);
		if (f == null) {
			Logger.error("error", "Fleet null at showFleet()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		// for debugging
		System.out.println("FLOTA SADRZI " + f.vehicles.size() + " VOZILA");
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
		DynamicForm dynamicFleetForm = Form.form().bindFromRequest();

		Fleet f = Fleet.findById(id);
		if (form.hasErrors() || form.hasGlobalErrors()) {
			Logger.info("Fleet update error");
			flash("error", "Error in fleet form");
			return ok(editFleetView.render(f));

		}
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		java.util.Date utilDate2 = new java.util.Date();
		String stringDate2;
		Date startDate;
		Date returnDate = null;

		try {
			stringDate = dynamicFleetForm.get("departureD");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			startDate = new java.sql.Date(utilDate.getTime());
			stringDate2 = dynamicFleetForm.get("arrivalD");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			utilDate2 = format2.parse(stringDate2);
			returnDate = new java.sql.Date(utilDate2.getTime());
			f.name = form.bindFromRequest().get().name;
			f.departure = startDate;
			f.arrival = returnDate;
			f.pickupPlace = form.bindFromRequest().get().pickupPlace;
			f.returnPlace = form.bindFromRequest().get().returnPlace;

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
		DynamicForm dynamicFleetForm = Form.form().bindFromRequest();
		if (fleetForm.hasErrors() || fleetForm.hasGlobalErrors()) {
			Logger.debug("Error at adding fleet");
			flash("error", "Error at fleet form!");
			return redirect("/addFleet");
		}

		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		java.util.Date utilDate2 = new java.util.Date();
		String stringDate2;
		Date startDate;
		Date returnDate = null;
		try {

			String name = addFleetForm.bindFromRequest().get().name;
			long numOfVehicles = 0;

			if (Fleet.findByName(name) != null) {
				Logger.debug("Error at adding fleet");
				flash("error", "Fleet with that name already exists!");
				return redirect("/addFleet");

			}
			stringDate = dynamicFleetForm.get("departureD");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			startDate = new java.sql.Date(utilDate.getTime());
			stringDate2 = dynamicFleetForm.get("arrivalD");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			utilDate2 = format2.parse(stringDate2);
			returnDate = new java.sql.Date(utilDate2.getTime());
			String pickupPlace = addFleetForm.bindFromRequest().get().pickupPlace;
			String returnPlace = addFleetForm.bindFromRequest().get().returnPlace;

			Fleet.createFleet(name, numOfVehicles, startDate, returnDate,
					pickupPlace, returnPlace);

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
			Logger.info("Deleted vehicle: \"" + v.typev + "\"");
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

			if (fleetForm.bindFromRequest().data().get("vehicleID") == null) {
				return ok(editFleetView.render(f));
			}

			String t = fleetForm.bindFromRequest().field("t").value();

			int num = 0;
			String[] vids = t.split(",");
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			String vi = null;

			for (int i = 0; i < vids.length; i++) {
				vi = vids[i];
				num = Integer.parseInt(fleetForm.bindFromRequest().field(vi)
						.value());

				System.out.println("/////////////" + num + vi);

				Type type = Type.findByName(vi);

				System.out.println("Broj vozila koja nisu u floti: "
						+ Vehicle.nonAsignedVehicles(type).size());

				List<Vehicle> vs = Vehicle.findListByType(type);

					if (!Vehicle.findListByType(type).isEmpty()) {

					for (int m = 0; m < num; m++) {
						if (vs.get(m).isAsigned == false) {
							vehicles.add(vs.get(m));
							vs.get(m).isAsigned = true;
							vs.get(m).fleet = f;
							vs.get(m).save();
						}
						System.out.println(vs.get(m).isAsigned + "/////"
								+ vehicles.size());
					}
				}
				f.vehicles.addAll(vehicles);
				f.numOfVehicles = f.vehicles.size();
				f.save();
				vehicles.clear();
			}
			System.out.println("/////////////" + num + vi);
			Logger.info(session("name") + " updated fleet: " + f.name);
			flash("success", f.name + " successfully updated!");
			return ok(editFleetView.render(f));

		} catch (Exception e) {
			flash("error",
					"Error at adding vehicles.That vehicle does not exists!"
							+ f.vehicles.size());
			Logger.error("Error at updateFleet: " + e.getMessage(), e);
			return ok(editFleetView.render(f));
		}

	}

}

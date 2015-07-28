package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Train;
import models.Truck;
import models.Vehicle;
import views.html.*;
import play.Logger;
import play.Play;
import play.data.Form;




//import play.db.ebean.Model.Finder;
import com.avaje.ebean.Model.*;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.i18n.Messages;

@SuppressWarnings("deprecation")
public class VehicleController extends Controller {
	@SuppressWarnings("deprecation")
	static Form<Vehicle> newVehicleForm = new Form<Vehicle>(Vehicle.class);
	@SuppressWarnings("deprecation")
	public static Finder<Integer, Vehicle> findVehicle = new Finder<Integer, Vehicle>(
			Integer.class, Vehicle.class);

	public Result showVehicle(int id) {
		Vehicle v = VehicleController.findVehicle.byId(id);
		return ok(showvehicle.render(v));

	}

	public Result addVehicle() {
		// User u = SessionHelper.getCurrentUser(ctx());

		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		
		return ok(addVehicleForm.render());
	}

	public Result removeVehicle(int id) {
		// User u = SessionHelper.getCurrentUser(ctx());

		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		Vehicle v = findVehicle.byId(id);
		v.delete();
		return redirect("/allvehicles");

	}

	public Result createVehicle() {
		// User u = SessionHelper.getCurrentUser(ctx());
		String licenseNo;
		String make;
		String model;
		String year;
		try {
			licenseNo = newVehicleForm.bindFromRequest().get().licenseNo;

		} catch (IllegalStateException e) {
			flash("add_vehicle_null_field",
					Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addvehicle");
		}

		Vehicle v = Vehicle.saveToDB(licenseNo, 1, 1);
		System.out.println("Vehicle added: " + 1 + " " + 1);
		return redirect("/allvehicles");
	}

	public Result listVehicles() {
		List<Truck> allTruckList = Truck.allTrucks();
		List<Train> allTrainList = Train.allTrains();
		List<Vehicle> merged = new ArrayList<Vehicle>();
		merged.addAll(allTruckList);
		merged.addAll(allTrainList);
		return ok(listAllVehicles.render(merged));
	}
}

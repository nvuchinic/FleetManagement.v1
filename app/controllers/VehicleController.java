package controllers;

import java.util.List;

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
public static Finder<Integer, Vehicle> findVehicle = new Finder<Integer, Vehicle>(Integer.class, Vehicle.class);

public Result showVehicle(int id) {
	Vehicle v = VehicleController.findVehicle.byId(id);
		return ok(showvehicle.render(v));
	
}

public Result addVehicle() {
	//User u = SessionHelper.getCurrentUser(ctx());
	
	
	//User currentUser = SessionHelper.getCurrentUser(ctx());
	// Unregistred user check
	/*if (currentUser == null) {
		return redirect(routes.Application.index());
	}*/
	List<Vehicle> allVehiclesList = findVehicle.all();
	return ok(addVehicleForm.render());
}

public Result createVehicle() {
	//User u = SessionHelper.getCurrentUser(ctx());
	String make;
	String model;
	String year;;
	try {
		make = newVehicleForm.bindFromRequest().get().make;
		model = newVehicleForm.bindFromRequest().get().model;
		year = newVehicleForm.bindFromRequest().get().year;
		
	} catch(IllegalStateException e) {
		flash("add_vehicle_null_field", Messages.get("Please fill all the fileds in the form!"));
		return redirect("/addvehicle");
	}
	
	Vehicle v = Vehicle.saveToDB(make, model, year);
System.out.println("Vehicle added: "+v.make+" "+v.model+" "+v.year);
	return redirect("/");
}
}

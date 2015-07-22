package controllers;

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

public class VehicleController extends Controller {
@SuppressWarnings("deprecation")
static Form<Vehicle> newVehicle = new Form<Vehicle>(Vehicle.class);
@SuppressWarnings("deprecation")
public static Finder<Integer, Vehicle> findVehicle = new Finder<Integer, Vehicle>(Integer.class, Vehicle.class);

public  Result showVehicle(int id) {
	Vehicle v = VehicleController.findVehicle.byId(id);
		return ok(showvehicle.render(v));
	
}
}

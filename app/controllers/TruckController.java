package controllers;

import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.Play;
import play.data.Form;
import play.db.ebean.*;



//import play.db.ebean.Model.Finder;
import com.avaje.ebean.Model.*;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.i18n.Messages;

@SuppressWarnings("deprecation")
public class TruckController extends Controller {
@SuppressWarnings("deprecation")
static Form<Truck> newTruckForm = new Form<Truck>(Truck.class);
@SuppressWarnings("deprecation")
public static Finder<Integer, Truck> findTruck= new Finder<Integer, Truck>(Integer.class, Truck.class);

public Result showTruck(int id) {
	Truck t = TruckController.findTruck.byId(id);
		return ok(showTruck.render(t));
	}

public Result addTruck() {
	//User u = SessionHelper.getCurrentUser(ctx());
	//User currentUser = SessionHelper.getCurrentUser(ctx());
	// Unregistred user check
	/*if (currentUser == null) {
		return redirect(routes.Application.index());
	}*/
	List<Truck> allTrucks = findTruck.all();
	return ok(addTruckForm.render());
}

public Result removeTruck(int id) {
	//User u = SessionHelper.getCurrentUser(ctx());
	
	
	//User currentUser = SessionHelper.getCurrentUser(ctx());
	// Unregistred user check
	/*if (currentUser == null) {
		return redirect(routes.Application.index());
	}*/
	Truck t=findTruck.byId(id);
	t.delete();
	return redirect("/alltrucks");
	}

public Result createTruck() {
	//User u = SessionHelper.getCurrentUser(ctx());
	long latitude=0;
	long longitude=0;
	String licenseNo;
	String make;
	String model;
	String year;
	int numOfContainers;
	try {
		licenseNo = newTruckForm.bindFromRequest().get().licenseNo;
		make = newTruckForm.bindFromRequest().get().make;
		model = newTruckForm.bindFromRequest().get().model;
		year = newTruckForm.bindFromRequest().get().year;
		numOfContainers = newTruckForm.bindFromRequest().get().numOfContainers;
		
	} catch(IllegalStateException e) {
		flash("add_truck_null_field", Messages.get("Please fill all the fileds in the form!"));
		return redirect("/addtruck");
	}
	
	Truck t = Truck.saveToDB(licenseNo,latitude, longitude,make, model, year, numOfContainers);
System.out.println("Vehicle added: "+t.make+" "+t.model+" "+t.year);
	return redirect("/alltrucks");
}

public Result listTrucks(){
	List<Truck> allTrucks = findTruck.all();
	return ok(listAllTrucks.render(allTrucks));
}
}

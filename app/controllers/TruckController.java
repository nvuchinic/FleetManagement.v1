package controllers;

import helpers.AdminFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;
import views.html.*;
import play.Logger;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.db.ebean.*;

//import play.db.ebean.Model.Finder;
import com.avaje.ebean.Model.*;

import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.i18n.Messages;
import play.api.mvc.*;

@SuppressWarnings("deprecation")
public class TruckController extends Controller {
	@SuppressWarnings("deprecation")
	static Form<Truck> newTruckForm = new Form<Truck>(Truck.class);
	@SuppressWarnings("deprecation")
	public static Finder<Integer, Truck> findTruck = new Finder<Integer, Truck>(
			Integer.class, Truck.class);

	public Result showTruck(int id) {
		Truck t = TruckController.findTruck.byId(id);
		return ok(showTruck.render(t));
	}

	public Result addTruck() {
		// User u = SessionHelper.getCurrentUser(ctx());
		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		List<Truck> allTrucks = findTruck.all();
		return ok(addTruckForm.render());
	}

	public Result removeTruck(int id) {
		// User u = SessionHelper.getCurrentUser(ctx());

		// User currentUser = SessionHelper.getCurrentUser(ctx());
		// Unregistred user check
		/*
		 * if (currentUser == null) { return
		 * redirect(routes.Application.index()); }
		 */
		Truck t = findTruck.byId(id);
		t.delete();
		return redirect("/alltrucks");
	}

	public Result createTruck() {
		// User u = SessionHelper.getCurrentUser(ctx());
		double latitude = 0;
		double longitude = 0;
		String licenseNo;
		String make;
		String model;
		String year;
		int numOfContainers;
		String status;
		double mileage;
		if (newTruckForm.hasErrors() || newTruckForm.hasGlobalErrors()) {
			return badRequest(addTruckForm.render()); // provjeriti
		}
		try {
			make = newTruckForm.bindFromRequest().get().make;
			model = newTruckForm.bindFromRequest().get().model;
			year = newTruckForm.bindFromRequest().get().year;
			numOfContainers = newTruckForm.bindFromRequest().get().numOfContainers;
			status =newTruckForm.bindFromRequest().get().status;
			if(numOfContainers < 0) {
				flash("error", "Truck can not have less then one container!");
				Logger.error("Error at truck registration:");
				return badRequest(addTruckForm.render());
			}
			
		} catch (IllegalStateException e) {
			flash("add_truck_null_field",
					Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addtruck");
		}

		System.out.println("Vehicle added: ");
		flash("success", " successfully added!");
		Logger.info(session("name") + " added truck: ");
		return ok(listAllTrucks.render(new ArrayList<Truck>()));
	}

	public Result listTrucks() {
		List<Truck> allTrucks = findTruck.all();
		return ok(listAllTrucks.render(allTrucks));
	}

	@Security.Authenticated(AdminFilter.class)
	public Result editTruckView(long id) {
		Truck truckToUpdate = new Truck();
		if (truckToUpdate != null) {
			Form<Truck> truckForm = Form.form(Truck.class).fill(truckToUpdate);
			return ok(editTruckView.render(truckToUpdate));
		} else {
			Logger.debug("In truck edit");
			flash("error", "error");
			return redirect("/");
		}
	}
	
	@Security.Authenticated(AdminFilter.class)
	public Result adminUpdateTruck(long id) {

		Form<Truck> updateForm = Form.form(Truck.class).bindFromRequest();
		Truck truck = new Truck();
		if (updateForm.hasErrors() || updateForm.hasGlobalErrors()) {
			flash("error", "An error has occurred, please try again.");
			return redirect("/editTruckView/" + truck.id);
		}
		try {
			
			String make = updateForm.get().make;
			String year = updateForm.get().year;
			String status = updateForm.get().status;
			int numOfContainers = updateForm.get().numOfContainers;
			String model = updateForm.get().model;

			
			if(numOfContainers < 0) {
				flash("error", "Truck can not have less then one container!");
				Logger.error("Error at truck update:");
				return redirect("/editTruckView/" + id);
			}
			

			truck.make = make;
			truck.year = year;
			truck.model = model;
			truck.numOfContainers = numOfContainers;
			truck.status = status;
	
			truck.save();
			flash("success",  " successfully updated!");
			Logger.info(session("name") + " updated truck: ");
			return ok(listAllTrucks.render(new ArrayList<Truck>()));
		} catch (Exception e) {
			flash("error", "An error has occurred in updating truck!");
			Logger.error("Error at adminUpdateTruck: " + " ", e);
			return redirect("/editTruckView/" + truck.id);
		}
	}
	
	public Result listOfEmployees() {
		return ok(employeeList.render(Employee.all()));	
	}
	
	
}

package controllers;

import helpers.AdminFilter;

import java.text.SimpleDateFormat;
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
		long latitude = 0;
		long longitude = 0;
		String licenseNo;
		String make;
		String model;
		String year;
		int numOfContainers;
		String status;
		if (newTruckForm.hasErrors() || newTruckForm.hasGlobalErrors()) {
			return badRequest(addTruckForm.render()); // provjeriti
		}
		try {
			licenseNo = newTruckForm.bindFromRequest().get().licenseNo;
			make = newTruckForm.bindFromRequest().get().make;
			model = newTruckForm.bindFromRequest().get().model;
			year = newTruckForm.bindFromRequest().get().year;
			numOfContainers = newTruckForm.bindFromRequest().get().numOfContainers;
			status =newTruckForm.bindFromRequest().get().status;

			if(Truck.findByLicenceNo(licenseNo) != null) {
				flash("error", "Error at truck registration");
				Logger.error("Error at registration: licenseNo already exists in DB!");
				return badRequest(addTruckForm.render());
				
			}
		} catch (IllegalStateException e) {
			flash("add_truck_null_field",
					Messages.get("Please fill all the fileds in the form!"));
			return redirect("/addtruck");
		}

		Truck t = Truck.saveToDB(licenseNo, latitude, longitude, make, model,
				year, numOfContainers, status);
		System.out.println("Vehicle added: " + t.make + " " + t.model + " "
				+ t.year);
		return redirect("/alltrucks");
	}

	public Result listTrucks() {
		List<Truck> allTrucks = findTruck.all();
		return ok(listAllTrucks.render(allTrucks));
	}

	@Security.Authenticated(AdminFilter.class)
	public Result editTruckView(long id) {
		Truck truckToUpdate = Truck.findById(id);
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
		Truck truck = Truck.findById(id);
		if (updateForm.hasErrors() || updateForm.hasGlobalErrors()) {
			return redirect("/editTruckView/" + truck.id);
		}
		try {
			String licenseNo = updateForm.get().licenseNo;
			long longitude = updateForm.get().longitude;
			long latitude = updateForm.get().latitude;
			String make = updateForm.get().make;
			String year = updateForm.get().year;
			String status = updateForm.get().status;
			int numOfContainers = updateForm.get().numOfContainers;
			String model = updateForm.get().model;
			if(Truck.findByLicenceNo(licenseNo) != null && !Truck.findByLicenceNo(licenseNo).equals(truck)) {
				Logger.error("Error at truck update: licenseNo already exists in DB!");
				return redirect("/editTruckView/" + id);	
			}
			truck.licenseNo = licenseNo;
			truck.latitude = latitude;
			truck.longitude = longitude;
			truck.make = make;
			truck.year = year;
			truck.model = model;
			truck.numOfContainers = numOfContainers;
			truck.status = status;
			truck.save();
			//flash("success", truck.licenseNo + " updatedSuccessfully");
			//Logger.info(session("name") + " updated user: " + cUser.name);
			return ok(listAllTrucks.render(Truck.find.all()));
		} catch (Exception e) {
			flash("error", "error");
			Logger.error("Error at adminUpdateTruck: " + " ", e);
			return redirect("/editTruckView/" + truck.id);
		}
	}
	
	public Result listOfEmployees() {
		return ok(employeeList.render(Employee.all()));	
	}
	
	
}

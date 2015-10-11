package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class MaintenanceController extends Controller {

	static Form<Maintenance> maintenanceOrderForm = Form
			.form(Maintenance.class);

	public static Finder<Long, Maintenance> find = new Finder<Long, Maintenance>(
			Maintenance.class);

	/**
	 * Renders the 'add Maintenance' form view
	 * 
	 * @return
	 */
	public Result addMaintenanceView(Long id) {
		List<Service> allServices = new ArrayList<Service>();
		allServices = Service.findS.all();
		Vehicle v = Vehicle.findById(id);
		return ok(addMaintenanceForm.render(v, allServices));
	}

	/**
	 * Finds Maintenance object using id and shows it
	 * 
	 * @param id
	 *            - Maintenance id
	 * @return
	 */
	public Result showMaintenance(long id) {
		Maintenance mnt = Maintenance.findById(id);
		if (mnt == null) {
			Logger.error("error", "Maintenance null()");
			flash("maintenanceNullError", "MAINTENANCE IS NULL!!!");
			return redirect("/");
		}
		// mServices=mnt.services;
		// for debbuging
		System.out.println("BROJ SERVISA ODRZAVANJA U METODI ShowMaintenance:"
				+ mnt.services.size());

		System.out
				.println("ISPISUJEM SERVISE ODRZAVANJA U  METODI SHOWMAINTENANCE:");
		for (Service s : mnt.services) {
			System.out.println(s.stype);
		}
		return ok(showMaintenance.render(mnt));
	}

	// @SuppressWarnings("unused")
	public Result chooseCar() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.find.all();
		flash("addVehicleForMaintenance",
				"For adding Vehicle Maintenance choose vehicle");
		return ok(listAllVehicles.render(allVehicles));
	}

	/**
	 * First checks if the form for adding Vehicle Registration has errors.
	 * Creates a new vehicle registration or renders the view again if any error
	 * occurs.
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addMaintenance(long id) {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		if (v == null) {
			flash("VehicleNull", "VEHICLE IS NULL AT ADDING MAINTENANCE!");
			return redirect("/");
		}
		/*
		 * if (addTravelOrderForm.hasErrors() ||
		 * addTravelOrderForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Travel Order"); flash("error",
		 * "Error at Travel Order form!"); return redirect("/addTravelOrder"); }
		 */
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		String serviceType;
		// Service service;
		try {
			stringDate = dynamicMaintenanceForm.get("dateM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
			System.out
					.println("UNESENI DATUM KOD KREIRANJA MAINTENANCE OBJEKTA: "
							+ mDate);
			Maintenance mn = Maintenance.saveToDB(v, mDate);
			String t = addMaintenanceForm.bindFromRequest().field("t").value();
			String[] servIds = t.split(",");
			List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("ISPISUJEM NIZ ID STRINGOVA U ADD_MAINTENANCE METODI:"
								+ servStrId);

				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				v.maintenances.add(mn);
				v.save();
				System.out.println("BROJ ODABRANIH USLUGA ODRZAVANJA: "
						+ mn.services.size());
				Logger.info(session("name") + " created maintenance ");
				flash("addMaintenanceSuccess",
						"Maintenance successfully added!");
			}
			return ok(showMaintenance.render(mn));

			// return redirect("/allmaintenances");
		} catch (Exception e) {
			flash("addMaintenanceError", "Error at adding maintenance ");
			Logger.error("Adding maintenance error: " + e.getMessage(), e);
			return redirect("/addmaintenanceview/" + v.id);
		}
	}

	public Result addMoreServicesView(Long id) {
		Maintenance mn = Maintenance.findById(id);
		if (mn == null) {
			System.out.println("MAINTENANCE NULL AT ADDING MORE SERVICES");
			return redirect("/allmaintenances");
		}
		List<Service> allServices = new ArrayList<Service>();
		allServices = Service.findS.all();
		return ok(addMoreServicesForm.render(mn.vehicle, allServices));
	}

	/**
	 * Finds Vehicle Registration object using id and then deletes it from
	 * database
	 * 
	 * @param id
	 *            - Vehicle registration id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteMaintenance(long id) {
		try {
			Maintenance mn = Maintenance.findById(id);
			Logger.info("Maintenance deleted: \"" + mn.id);
			Maintenance.deleteMaintenance(id);
			return redirect("/allmaintenances");
		} catch (Exception e) {
			flash("deleteMaintenanceError", "Error at deleting maintenance!");
			Logger.error("Error at deleting maintenance: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view for editing Vehicle registration object.
	 * 
	 * @param id
	 *            long
	 * @return Result
	 */
	public Result editMaintenanceView(long id) {
		Maintenance mn = Maintenance.findById(id);
		// Exception handling.
		if (mn == null) {
			flash("MaintenanceNull", "Maintenance record doesn't exist");
			return redirect("/");
		}
		// Form<TravelOrder> travelOrderForm =
		// Form.form(TravelOrder.class).fill(to);
		return ok(editMaintenanceView.render(mn));

	}

	/**
	 * Method receives an id, finds the specific Vehicle registration object and
	 * updates its information with data collected from editVRegistration form
	 * again.
	 * 
	 * @param id
	 *            of Vehicle Registration object
	 * @return Result
	 */
	public Result editMaintenance(long id) {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> maintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		Maintenance mn = Maintenance.findById(id);
		String serviceType;
		Service service;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		try {
			if (maintenanceForm.hasErrors()
					|| maintenanceForm.hasGlobalErrors()) {
				Logger.info("Maintenance update error");
				flash("error", "Error in maintenance update form");
				return ok(editMaintenanceView.render(mn));
			}
			serviceType = maintenanceForm.bindFromRequest().get().serviceType;
			if (serviceType == null) {
				serviceType = mn.serviceType;
			}
			stringDate = dynamicMaintenanceForm.get("dateM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
			service = Service.findByType(serviceType);
			mn.services.add(service);
			mn.mDate = mDate;
			mn.save();
			List<Service> mServices = new ArrayList<Service>();
			for (Service s : mn.services) {
				mServices.add(s);
			}
			System.out.println("Ispisujem servise odrzavanja");
			for (Service s : mServices) {
				System.out.println(s.stype);
			}
			Logger.info(session("name") + " updated vehicle registration: "
					+ mn.id);
			flash("maintenanceUpdateSuccess",
					"Maintenance successfully updated!");
			return ok(showMaintenance.render(mn));
		} catch (Exception e) {
			flash("maintenanceUpdateError", "Error at editing maintenance");
			Logger.error("Error at updating maintenance: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	public Result listMaintenances() {
		List<Maintenance> allMaintenances = Maintenance.listOfMaintenances();
		if (allMaintenances != null) {
			return ok(listAllMaintenances.render(allMaintenances));
		} else {
			flash("listMaintenancesError",
					"No maintenances records in database!");
			return redirect("/");
		}
	}

}

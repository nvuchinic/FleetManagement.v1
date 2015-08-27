package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class WorkOrderController extends Controller {

	/**
	 * Form for creating/editing WorkOrder object
	 */
	static Form<WorkOrder> workOrderForm = Form.form(WorkOrder.class);

	/**
	 * Finder for WorkOrder object
	 */

	public static Finder<Long, WorkOrder> find = new Finder<Long, WorkOrder>(
			WorkOrder.class);

	/**
	 * Renders the 'add WorkOrder' page
	 * 
	 * @return
	 */
	public Result addWorkOrderView() {
		List<Driver> allDrivers = Driver.find.all();
		List<Vehicle> allVehicles = Vehicle.find.all();
		List<Driver> availableDrivers = new ArrayList<Driver>();
		for (Driver d : allDrivers) {
			if (d.engagedd == false) {
				availableDrivers.add(d);
			}
		}
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.engagedd == false) {
				availableVehicles.add(v);
			}
		}
		if ((availableDrivers.size() == 0) || (availableVehicles.size() == 0)) {
			flash("NoVehiclesOrDrivers",
					"Cannot create new Work Order! No available vehicles and drivers");
			return redirect("/");
		}
		return ok(addWorkOrderForm.render(availableDrivers, availableVehicles));
	}

	public Result chooseCar() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.find.all();
		flash("info", "For adding Work Order choose vehicle");
		return ok(listAllVehicles.render(allVehicles));
	}

	/**
	 * Finds WorkOrder object by id and shows it
	 * 
	 * @param id
	 *            - WorkOrder object id
	 * @return
	 */
	public Result showWorkOrder(long id) {
		WorkOrder wo = WorkOrder.findById(id);
		if (wo == null) {
			Logger.error("error", "WORKORDER NULL");
			flash("error", "WORKORDER NULL!");
			return redirect("/");
		}
		return ok(showWorkOrder.render(wo));
	}

	/**
	 * Finds WorkOrder object by ID passed as parameter, and then deletes it
	 * from database
	 * 
	 * @param id
	 *            - Work Order object id
	 * @return redirect to index after delete
	 */
	public Result deleteWorkOrder(long id) {
		try {
			WorkOrder wo = WorkOrder.findById(id);
			Logger.info("WorkOrder deleted: \"" + wo.id + "\"");
			WorkOrder.deleteWorkOrder(id);
			return redirect("/allworkorders");
		} catch (Exception e) {
			flash("error", "Error at deleting WorkOrder!");
			Logger.error("ERROR AT DELETING WORKORDER: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * first finds WorkOrder object by ID, and then sends it to the rendered
	 * template view for editing
	 * 
	 * @param id
	 *            long
	 * @return
	 */
	public Result editWorkOrderView(long id) {
		WorkOrder wo = WorkOrder.findById(id);
		// Exception handling.
		if (wo == null) {
			Logger.error("ERROR AT RENDERING WORKORDER EDITING VIEW  ");
			flash("error", "WORKORDER NULL");
			return ok(editWorkOrderView.render(wo, Driver.availableDrivers(),
					Vehicle.availableVehicles()));
		}

		if ((Driver.availableDrivers().size() == 0)
				|| (Vehicle.availableVehicles().size() == 0)) {
			flash("error",
					"Cannot create new Work Order! No available vehicles and drivers");
			return redirect("/");
		}

		return ok(editWorkOrderView.render(wo, Driver.availableDrivers(),
				Vehicle.availableVehicles()));

	}

	/**
	 * Finds WorkOrder object by ID, then takes and binds data from
	 * editWorkOrder view, and updates WorkOrder object with them
	 * 
	 * @param id
	 *            ID of the WorkOrder object
	 * @return Result
	 */
	public Result editWorkOrder(long id) {
		// DynamicForm updateTravelorderForm = Form.form().bindFromRequest();
		List<Driver> allDrivers = Driver.find.all();
		List<Vehicle> allVehicles = Vehicle.find.all();
		List<Driver> availableDrivers = new ArrayList<Driver>();
		for (Driver d : allDrivers) {
			if (d.engagedd == false) {
				availableDrivers.add(d);
			}
		}
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.engagedd == false) {
				availableVehicles.add(v);
			}
		}
		if ((availableDrivers.size() == 0) || (availableVehicles.size() == 0)) {
			flash("error",
					"Cannot create new Work Order! No available vehicles and drivers");
			return redirect("/");
		}
		Form<WorkOrder> workOrderform = Form.form(WorkOrder.class)
				.bindFromRequest();
		WorkOrder wo = WorkOrder.findById(id);
		long woNumber;
		Date woDate;
		String driverName;
		String vehicleName;
		String description;
		String statusWo;
		try {
			if (workOrderform.hasErrors() || workOrderform.hasGlobalErrors()) {
				Logger.info("WORKORDER EDIT FORM ERROR");
				flash("error", "WORKORDER EDIT FORM ERROR");
				return ok(editWorkOrderView.render(wo, availableDrivers,
						availableVehicles));
			}
			statusWo = workOrderForm.bindFromRequest().get().statusWo;
			description = workOrderForm.bindFromRequest().get().description;
			woDate = workOrderForm.bindFromRequest().get().woDate;
			driverName = workOrderForm.bindFromRequest().get().driverName;
			vehicleName = workOrderForm.bindFromRequest().get().vehicleName;
			Vehicle v = Vehicle.findByName(vehicleName);
			if (v == null) {
				flash("error", "VEHICLE IS NULL!");
				return redirect("/");
			}
			Driver d = Driver.findByDriverName(driverName);
			if (d == null) {
				flash("error", "DRIVER IS NULL!");
				return redirect("/");
			}

			wo.statusWo = statusWo;
			wo.description = description;
			wo.woDate = woDate;
			if (d != wo.driver) {
				wo.driver.engagedd = false;
				wo.driver.save();
				wo.driver = d;
				d.engagedd = true;
				d.save();
			}

			wo.vehicle = v;
			wo.save();
			v.engagedd = true;
			v.save();
			Logger.info(session("name") + " EDITED WORK ORDER: " + wo.id);
			List<WorkOrder> allWorkOrders = WorkOrder.find.all();
			flash("success", "WORK ORDER SUCCESSFULLY EDITED!");
			return ok(listAllWorkOrders.render(allWorkOrders));
		} catch (Exception e) {
			flash("error", "Error at editing TravelOrder");
			Logger.error("ERROR EDITING WORK ORDER: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	/**
	 * Creates the form for binding data from AddWorkOrderForm view, and uses
	 * that data for creating new WorkOrder object
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addWorkOrder() {
		// DynamicForm dynamicWorkOrderform = Form.form().bindFromRequest();
		Form<WorkOrder> addWorkOrderForm = Form.form(WorkOrder.class)
				.bindFromRequest();
		/*
		 * if (addTravelOrderForm.hasErrors() ||
		 * addTravelOrderForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Travel Order"); flash("error",
		 * "Error at Travel Order form!"); return redirect("/addTravelOrder"); }
		 */
		long woNumber;
		Date woDate;
		String driverName;
		String vehicleName;
		String description;
		String statusWo;
		try {
			statusWo = workOrderForm.bindFromRequest().get().statusWo;
			description = workOrderForm.bindFromRequest().get().description;
			woDate = workOrderForm.bindFromRequest().get().woDate;
			driverName = workOrderForm.bindFromRequest().get().driverName;
			vehicleName = workOrderForm.bindFromRequest().get().vehicleName;
			Vehicle v = Vehicle.findByName(vehicleName);
			if (v == null) {
				flash("error", "VEHICLE IS NULL!");
				return redirect("/");
			}
			Driver d = Driver.findByDriverName(driverName);
			if (d == null) {
				flash("error", "DRIVER IS NULL!");
				return redirect("/");
			}

			WorkOrder wo = WorkOrder.saveToDB(woDate, d, v, description,
					statusWo, new ArrayList<Task>());
			d.engagedd = true;
			d.save();
			v.engagedd = true;
			v.save();
			Logger.info(session("name") + " CREATED WORK ORDER ");
			if (wo != null) {
				flash("success", "WORK ORDER SUCCESSFULLY ADDED!");
				return redirect("/allworkorders");
			} else {
				flash("error", "ERROR ADDING WORK ORDER ");
				return redirect("/addworkorderview");

			}
		} catch (Exception e) {
			flash("error", "ERROR ADDING WORK ORDER ");
			Logger.error("ERROR ADDING WORK ORDER: " + e.getMessage(), e);
			return redirect("/addworkorderview");
		}
	}

	public Result listWorkOrders() {
		List<WorkOrder> allWorkOrders = WorkOrder.listOfWorkOrders();
		if (allWorkOrders != null) {
			return ok(listAllWorkOrders.render(allWorkOrders));
		} else {
			flash("error", "NO WORK ORDERS IN DATABASE!");
			return redirect("/");
		}
	}

	public Result addTasks(long id) {
		
				WorkOrder wo = WorkOrder.findById(id);
				try {
					if (workOrderForm.hasErrors() || workOrderForm.hasGlobalErrors()) {
						Logger.info("Error in adding tasks into the work order");
						flash("error", "Error in fleet form");
						return ok(editWorkOrderView.render(wo, Driver.availableDrivers(), Vehicle.availableVehicles()));
					}
					
					String t = workOrderForm.bindFromRequest().field("t").value();
					String[] task = t.split(",");
					String vi = null;
					for(int i = 0; i < task.length; i++) {
						vi = task[i];	
					if (Task.findByName(vi) != null) {
						Task tt = Task.findByName(vi);
						wo.tasks.add(tt);
						tt.workOrder = wo;
						wo.save();
						tt.save();
						}
					}

						
					Logger.info(session("name") + " updated workOrder: ");
					flash("success", "Tasks successfully added!");
					return ok(editWorkOrderView.render(wo, Driver.availableDrivers(), Vehicle.availableVehicles()));

				} catch (Exception e) {
					flash("error", "Error at adding tasks.");
					Logger.error("Error at adding tasks into the work order: " + e.getMessage(), e);
					return ok(editWorkOrderView.render(wo, Driver.availableDrivers(), Vehicle.availableVehicles()));
				}

			}
}

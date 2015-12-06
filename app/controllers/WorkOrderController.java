package controllers;

import java.util.ArrayList;
import java.sql.Date;
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
		List<Client> allClients = Client.find.all();
		List<Employee> allEmployees = Employee.find.all();
		List<Vehicle> allVehicles = Vehicle.find.all();
		List<Employee> availableDrivers = new ArrayList<Employee>();
		for (Employee emp : allEmployees) {
			if (emp.isDriver == true) {
				if(emp.isEngaged==false){
					availableDrivers.add(emp);
				}
			}
		}
		if (availableDrivers.size() == 0) {
			flash("error",
					"Cannot create new Work Order! No available drivers");
			return redirect("/");
		}
		if (Vehicle.availableVehicles().size() == 0) {
			flash("error",
					"Cannot create new Work Order! No available vehicles");
			return redirect("/");
		}
		return ok(addWorkOrderForm.render(availableDrivers,
				Vehicle.availableVehicles(), allClients));
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
			if(WorkOrder.findById(id)==null){
				flash("error", "WORK ORDER IS NULL");
				return redirect("/allworkorders");

			}
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
			Logger.error("ERROR AT RENDERING WORK ORDER EDITING VIEW  ");
			flash("error", "WORKORDER NULL");
			return ok(showWorkOrder.render(wo));
		}

		if ((Driver.availableDrivers().size() == 0)
				|| (Vehicle.availableVehicles().size() == 0)) {
			flash("error",
					"Cannot create new Work Order! No available vehicles and drivers");
			return redirect("/");
		}
		List<Employee> allEmployees = Employee.find.all();
		List<Employee> availableDrivers = new ArrayList<Employee>();
		for (Employee emp : allEmployees) {
			if (emp.isDriver == true) {
				if(emp.isEngaged==false){
					availableDrivers.add(emp);
				}
			}
		}
		return ok(editWorkOrderView.render(wo, availableDrivers,
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
		DynamicForm dynamicWorkOrderForm = Form.form().bindFromRequest();
		if ((Driver.availableDrivers().size() == 0)
				|| (Vehicle.availableVehicles().size() == 0)) {
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
		String clName;
		try {
			if (workOrderform.hasErrors() || workOrderform.hasGlobalErrors()) {
				Logger.info("WORK ORDER EDIT FORM ERROR");
				flash("error", "WORKORDER EDIT FORM ERROR");
				return ok(showWorkOrder.render(wo));
			}
			driverName = dynamicWorkOrderForm.get("driverName");
			statusWo = workOrderForm.bindFromRequest().get().statusWo;
			description = workOrderForm.bindFromRequest().get().description;
			//driverName = workOrderForm.bindFromRequest().get().driverName;
			clName = dynamicWorkOrderForm.get("clName");
			Client cl = Client.findByName(clName);
			if (cl == null) {
				flash("error", "Client is null!");
				return badRequest(showWorkOrder.render(wo));
			}
			vehicleName = workOrderForm.bindFromRequest().get().vehicleName;
			Vehicle v = Vehicle.findByName(vehicleName);
			if (v == null) {
				flash("error", "Vehicle is null!");
				return badRequest(showWorkOrder.render(wo));
			}
			if (Employee.findByName(driverName)== null) {
				flash("error", "Driver is null!");
				return badRequest(showWorkOrder.render(wo));
			}
			Employee driver = Employee.findByName(driverName);
			wo.client = cl;
			wo.statusWo = statusWo;
			wo.description = description;
			if (driver != wo.driver) {
				wo.driver.isEngaged = false;
				wo.driver.save();
				wo.driver = driver;
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
		DynamicForm dynamicWorkOrderForm = Form.form().bindFromRequest();
		Form<WorkOrder> addWorkOrderForm = Form.form(WorkOrder.class)
				.bindFromRequest();
		if (addWorkOrderForm.hasErrors() || addWorkOrderForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Work Order");
			flash("error", "Error at Work Order form!");
			return redirect("/addworkorderview");
		}
		long woNumber;
		Date woDate;
		String driverName;
		String vehicleName;
		String description;
		String statusWo;
		String clName;
		try {
			statusWo = workOrderForm.bindFromRequest().get().statusWo;
			description = workOrderForm.bindFromRequest().get().description;
			driverName = dynamicWorkOrderForm.get("driverName");
			vehicleName = workOrderForm.bindFromRequest().get().vehicleName;
			clName = dynamicWorkOrderForm.get("clName");
			Client cl = Client.findByName(clName);
			if (cl == null) {
				flash("error", "Client is null!");
				return redirect("/addworkorderview");
			}
			Vehicle v = Vehicle.findByName(vehicleName);
			if (v == null) {
				flash("error", "Vehicle is null!");
			}
			Employee driver = Employee.findByName(driverName);
			if ((Employee.findByName(driverName)) == null) {
				flash("error", "DRIVER IS NULL!");
				return redirect("/addworkorderview");
			}

			WorkOrder wo = WorkOrder.saveToDB(driver, v, description, statusWo,
					new ArrayList<Task>(), cl);
			cl.wOrders.add(wo);
			cl.save();

			v.engagedd = true;
			v.save();
			if (wo != null) {
				Logger.info(session("name") + " CREATED WORK ORDER ");
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
				return ok(showWorkOrder.render(wo));
			}
			String t = workOrderForm.bindFromRequest().field("t").value();
			String[] task = t.split(",");
			String vi = null;
			for (int i = 0; i < task.length; i++) {
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
			return ok(showWorkOrder.render(wo));
		} catch (Exception e) {
			flash("error", "Error at adding tasks.");
			Logger.error(
					"Error at adding tasks into the work order: "
							+ e.getMessage(), e);
			return ok(showWorkOrder.render(wo));
		}

	}
}

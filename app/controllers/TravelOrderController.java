package controllers;

import java.sql.Date;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import models.*;
import models.Route;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TravelOrderController extends Controller {
	/**
	 * Form for creating/editing TravelOrder object
	 */
	static Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class);

	/**
	 * Finder for TravelOrder object
	 */
	public static Finder<Long, TravelOrder> findTO = new Finder<Long, TravelOrder>(
			TravelOrder.class);

	/**
	 * Renders the 'add TravelOrder' page
	 * 
	 * @return
	 */
	public Result addTravelOrderView() {
		List<Route> allRoutes = new ArrayList<Route>();
		allRoutes = Route.find.all();
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
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.engagedd == false) {
				availableVehicles.add(v);
			}
		}
		if ((availableDrivers.size() == 0) ) {
			flash("error",
					"CANNOT CREATE TRAVEL ORDER! NO AVAILABLE  DRIVERS");
			return redirect("/");
		}
		if ((availableVehicles.size() == 0) ) {
			flash("error",
					"CANNOT CREATE TRAVEL ORDER! NO AVAILABLE  VEHICLES");
			return redirect("/");
		}
		return ok(addTravelOrderForm.render(availableDrivers,
				availableVehicles, allRoutes));
	}

	/**
	 * Creates new TravelOrder object using values from post request(collected through form)
	 * @return
	 */
	public Result addTravelOrder() {
		Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class)
				.bindFromRequest();
		DynamicForm dynamicTravelOrderForm = Form.form().bindFromRequest();
		if (travelOrderForm.hasErrors() || travelOrderForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Travel Order");
			flash("error", "Error at Travel Order form!");
			return redirect("/addtravelorderview");
		}
		String destination;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		java.util.Date utilDate2 = new java.util.Date();
		String stringDate2;
		Date startDate;
		Date returnDate = null;
		String selectedVehicle = null;
		String driverName;
		String rtName;
		try {
			long numberTO = TravelOrder.numberTo();
			String name = travelOrderForm.bindFromRequest().get().name;
			String reason = travelOrderForm.bindFromRequest().get().reason;
			destination = travelOrderForm.bindFromRequest().get().destination;
			rtName = dynamicTravelOrderForm.get("rtName");
			Route rt = Route.findByName(rtName);
			if (rt == null) {
				System.out.println("ROUTE IS NULL!!!///////////////");
			}
			stringDate = dynamicTravelOrderForm.get("dateS");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			startDate = new java.sql.Date(utilDate.getTime());
			stringDate2 = dynamicTravelOrderForm.get("dateR");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			utilDate2 = format2.parse(stringDate2);
			returnDate = new java.sql.Date(utilDate2.getTime());
			selectedVehicle = travelOrderForm.bindFromRequest()
					.field("vehicleName").value();
			Vehicle v = Vehicle.findByName(selectedVehicle);
			if (v == null) {
				flash("VehicleIsNull", "Vehicle is null!");
				return redirect("/");
			}
			driverName = travelOrderForm.bindFromRequest().field("firstName")
					.value();
						if (Employee.findByName(driverName)== null) {
				flash("error", "DRIVER IS NULL");
				return redirect("/alltravelorders");
			}
			Employee driver = Employee.findByName(driverName);
			TravelOrder.saveTravelOrderToDB(numberTO, name, reason,
					destination, startDate, returnDate, driver, v, rt);
			driver.isEngaged = true;
			driver.save();
			v.engagedd = true;
			v.save();
			//Logger.info(session("name") + " created Travel Order ");
			flash("success", "TRAVEL ORDER SUCCESSFULLY ADDED!");
			return ok(listAllTravelOrders.render(TravelOrder
					.listOfTravelOrders()));
		} catch (Exception e) {
			flash("error", "Error at adding Travel Order ");
			Logger.error("Adding Travel order error: " + e.getMessage(), e);
			return redirect("/addtravelorderview");
		}
	}
	public Result chooseCar() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.find.all();
		flash("addVehicleForTravelOrder",
				"For adding Travel Order choose vehicle");
		return ok(listAllVehicles.render(allVehicles));
	}

	/**
	 * Finds TravelOrder object by it's ID number passed as argument,
	 * then shows it in view
	 * @param id- TravelOrder object ID number
	 * @return
	 */
	public Result showTravelOrder(long id) {
		TravelOrder to = TravelOrder.findById(id);
		if (to == null) {
			Logger.error("error", "Travelorder null()");
			flash("error", "Travel Order null!");
			return redirect("/");
		}
		return ok(showTravelOrder.render(to));
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Result deleteTravelOrder(long id) {
		try {
			if(TravelOrder.findById(id)==null){
				flash("error", "NO TRAVEL ORDER WITH THAT ID!");
				return redirect("/alltravelorders");
			}
			TravelOrder to = TravelOrder.findById(id);
			TravelOrder.deleteTravelOrder(id);
			flash("success", "TRAVEL ORDER SUCCESSFULLY DELETED!");
			return redirect("/alltravelorders");
		} catch (Exception e) {
			flash("error", "Error at deleting travelOrder!");
			Logger.error("Error at deleting travelOrder: " + e.getMessage());
			return redirect("/");
		}
	}

	
	/**
	 * Renders the view for editing Travelorder object
	 * @param id-ID number of the TravelOrder object
	 * @return
	 */
	public Result editTravelOrderView(long id) {
				// Exception handling.
		if (TravelOrder.findById(id) == null) {
			flash("error", "TRAVEL ORDER IS NULL");
			return redirect("/alltravelorders");
		}
		TravelOrder to = TravelOrder.findById(id);
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
		List<Vehicle> availableVehicles = new ArrayList<Vehicle>();
		for (Vehicle v : allVehicles) {
			if (v.engagedd == false) {
				availableVehicles.add(v);
			}
		}
		List<Route> allRoutes = Route.find.all();
		return ok(editTravelOrderView.render(to, allRoutes, availableDrivers, availableVehicles));
	}

	/**
	 * Method receives an id, finds the specific TravelOrder object and renders
	 * the edit View for the TravelOrder. If any error occurs, the view is
	 * rendered again.
	 * @param id-ID number of TravelOrder object
	 * @return Result render the vehicle edit view
	 */
	public Result editTravelOrder(long id) {
		TravelOrder to = TravelOrder.findById(id);
		DynamicForm dynamicTravelOrderForm = Form.form().bindFromRequest();
		Form<TravelOrder> travelOrderform = Form.form(TravelOrder.class)
				.bindFromRequest();
		if (travelOrderform.hasErrors() || travelOrderform.hasGlobalErrors()) {
			Logger.info("TravelOrder update error");
			flash("error", "Error in travelOrder form");
			List<Route> allRoutes = new ArrayList<Route>();
			allRoutes = Route.find.all();
			return redirect("/alltravelorders");
		}
		String driverName;
		long numberTO;
		String destination;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		java.util.Date utilDate2 = new java.util.Date();
		String stringDate2;
		Date startDate;
		Date returnDate;
		String name = travelOrderForm.bindFromRequest().get().name;
		String reason = travelOrderForm.bindFromRequest().field("reason")
				.value();
		String rtName;
		rtName = dynamicTravelOrderForm.get("rtName");
		Route rt = Route.findByName(rtName);
		if (rt == null) {
			System.out.println("ROUTE IS NULL!!!///////////////");
		}
		try {
			driverName = dynamicTravelOrderForm.get("driverName");
			Employee driver=Employee.findByName(driverName);
			numberTO = travelOrderForm.bindFromRequest().get().numberTO;
			destination = travelOrderForm.bindFromRequest().get().destination;
			stringDate = dynamicTravelOrderForm.get("dateS");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			startDate = new java.sql.Date(utilDate.getTime());
			stringDate2 = dynamicTravelOrderForm.get("dateR");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			utilDate2 = format2.parse(stringDate2);
			returnDate = new java.sql.Date(utilDate2.getTime());
			to.driver=driver;
			to.name = name;
			to.reason = reason;
			to.destination = destination;
			to.startDate = startDate;
			to.returnDate = returnDate;
			to.route = rt;
			to.save();
			Logger.info(session("name") + " updated travelOrder: " + to.id);
			flash("success", "Travel Order successfully updated!");
			return ok(listAllTravelOrders.render(TravelOrder
					.listOfTravelOrders()));
		} catch (Exception e) {
			flash("error", "Error at editing TravelOrder");
			Logger.error("Error at updateTravelOrder: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	
	

	public Result listTravelOrders() {
		List<TravelOrder> allTravelOrders = TravelOrder.listOfTravelOrders();
		if (allTravelOrders != null) {
			return ok(listAllTravelOrders.render(allTravelOrders));
		} else {
			flash("listTOerror", "No Travel Orders in database!");
			return redirect("/");
		}
	}

}

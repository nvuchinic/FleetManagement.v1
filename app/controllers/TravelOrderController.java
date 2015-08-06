package controllers;

import java.util.ArrayList;
import java.util.Date;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class TravelOrderController extends Controller{
/**
 * Form for creating/editing TravelOrder object
 */
	static Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class);
	
	/**
	 * Finder for TravelOrder object
	 */
	public static Finder<Long, TravelOrder> findTO = new Finder<Long, TravelOrder>(Long.class,
			TravelOrder.class);
	
	/**
	 * Renders the 'add TravelOrder' page
	 * @return
	 */
	public Result addTravelOrderFormView() {
		return ok(addTravelOrderForm.render());
	}
	
	/**
	 * Finds TravelOrder object using id and shows it 
	 * @param id - TravelOrder id
	 * @return 
	 */
	public Result showTravelOrder(long id) {
		TravelOrder to = TravelOrder.findById(id);
		if (to == null) {
			Logger.error("error", "Travelorder null()");
			flash("error", "Something went wrong!");
			return redirect("/");
		}
		return ok(showTravelOrder.render(to));
	}
	
	/**
	 * Delete Vehicle using id
	 * @param id - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteTravelOrder(long id) {
		try {
			TravelOrder to= TravelOrder.findById(id);
			Logger.info("TravelOrder deleted: \"" + to.numberTO + "\"");
			TravelOrder.deleteTravelOrder(id);
			return redirect("/");
		} catch (Exception e) {
			flash("error", "Error at deleting travelOrder!");
			Logger.error("Error at deleting travelOrder: " + e.getMessage());
			return redirect("/");
		}
	}
	
	/**
	 * Renders the view of a Vehicle. Method receives the id of the vehicle and
	 * finds the vehicle by id and send's the vehicle to the view.
	 * @param id long
	 * @return Result render VehicleView
	 */
	public Result editTravelOrderView(long id) {
		TravelOrder to = TravelOrder.findById(id);
		// Exception handling.
		if (to == null) {
			flash("error", "TravelOrder doesn't exist");
			return redirect("/");
		}
		Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class).fill(v);
		return ok(editTravelOrderView.render(to));

	}
	
	/**
	 *  Method receives an id, finds the specific TravelOrder object and renders
	 * the edit View for the TravelOrder. If any error occurs, the view is rendered
	 * again.
	 * @param id of vehicle
	 * @return Result render the vehicle edit view
	 */
	public Result editTravelOrder(long id) {
		DynamicForm updateTravelorderForm = Form.form().bindFromRequest();
		Form<TravelOrder> travelOrderform = Form.form(TravelOrder.class).bindFromRequest();
		TravelOrder to  = TravelOrder.findById(id);
		try {
			if (travelOrderform.hasErrors() || travelOrderform.hasGlobalErrors()) {
				Logger.info("TravelOrder update error");
				flash("error", "Error in travelOrder form");
				return ok(editTravelOrderView.render(v));
			}

			
String ownerName = travelOrderForm.bindFromRequest().get().numberTO;
				
			String ownerEmail = vehicleForm.bindFromRequest().data().get("ownerEmail");
			
			
			String typeName = vehicleForm.bindFromRequest().data().get("typeName");
			
			String description = vehicleForm.bindFromRequest().data().get("typeDescription");
			
			String fleetName = vehicleForm.bindFromRequest().data().get("fleetName");
			
			Fleet f;
			if(Fleet.findByName(fleetName) == null) {
				f = new Fleet("", 0);
				f.save();
			} else {
				f = Fleet.findByName(fleetName);
				f.save();
			}
			
			Type t;
			if(Type.findByName(typeName) == null) {
				t = new Type(typeName, description);
				t.save();
			} else {
				t = Type.findByName(typeName);
				t.description = description;
				t.save();
			}
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			
			v.typev = t;
			
			v.owner = o;
			
			v.fleet = f;
			
			v.save();
			
			Logger.info(session("name") + " updated vehice: " + v.id);
			flash("success", v.vid + " successfully updated!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	/**
	 * First checks if the vehicle form has errors. Creates a new vehicle or
	 * renders the view again if any error occurs.
	 * @return redirect to create vehicle view
	 * @throws ParseException
	 */
	public Result addVehicle() {

		Form<Vehicle> addVehicleForm = Form.form(Vehicle.class).bindFromRequest();
		
		if (addVehicleForm.hasErrors() || addVehicleForm.hasGlobalErrors()) {
			Logger.debug("Error at adding vehicle");
			flash("error", "Error at vehicle form!");
			return redirect("/addVehicle");
		}

		try{	
			
			String vid = addVehicleForm.bindFromRequest().get().vid;
			String ownerName = addVehicleForm.bindFromRequest().data().get("ownerName");
			String ownerEmail = addVehicleForm.bindFromRequest().data().get("ownerEmail");
			String  typeName= addVehicleForm.bindFromRequest().data().get("typeName");
			String typeDescription = addVehicleForm.bindFromRequest().data().get("typeDescription");
			
			String fleetName = addVehicleForm.bindFromRequest().data().get("fleetName");
			
			Fleet f;
			if(Fleet.findByName(fleetName) == null) {
				f = new Fleet("", 0);
				f.save();
			} else {
			
			f = Fleet.findByName(fleetName);
			}
			Type t;
			if(Type.findByName(typeName) == null) {
			 t = new Type(typeName, typeDescription);
			 t.save();
			}
				t = Type.findByName(typeName);
			
			
			Owner o;
			if(Owner.findByName(ownerName) == null) {
				 o = new Owner(ownerName, ownerEmail);
				 o.save();
			} 
				o = Owner.findByName(ownerName);
			
		
				Vehicle.createVehicle(vid, o, t, f);

				Logger.info(session("name") + " created vehicle ");
				flash("success",  "Vehicle successfully added!");
				return redirect("/");
			
		}catch(Exception e){
		flash("error", "Error at adding vehicle afasdfasdffsadfasdf");
		Logger.error("Error at addVehicle: " + e.getMessage(), e);
		return redirect("/addVehicle");
	   }
	}
	
	public Result listVehicles() {
		if(Vehicle.listOfVehicles() == null)
			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}
	
}

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


public class VRegistrationController extends Controller{

	/**
	 * Form for creating/editing VehicleRegistration object
	 */
		static Form<VehicleRegistration> vehicleRegistrationForm = Form.form(VehicleRegistration.class);
		
		/**
		 * Finder for VehicleRegistration object
		 */
		public static Finder<Long, VehicleRegistration> find = new Finder<Long, VehicleRegistration>(Long.class,
				VehicleRegistration.class);
		
		public Result listUnregisteredVehicles() {
			List<Vehicle> allVehicles=Vehicle.find.all();
			List<Vehicle> unregVehicles=new ArrayList<Vehicle>();
			for(Vehicle v:allVehicles){
				if(v.isRegistered==false){
					unregVehicles.add(v);
				}
			}
			if(unregVehicles.size()==0){
				flash("NoUnregisteredVehicles", "All vehicles are registered");
				return redirect("/allVehicles");
			}
			return ok(listUnregisteredVehiclesForm.render(unregVehicles));
		}
		
		/**
		 * Renders the form view for creating VehicleRegistration object
		 * @return
		 */
		public Result addVRegistrationView(long id) {
			Vehicle v=Vehicle.findById(id);
			if(v.isRegistered==true){
				flash("VehicleRegistered",  "Vehicle is already registered!");
				return redirect("/showvehicle/"+v.id);
			}
			return ok(addVRegistrationForm.render(v));
		}
		
		/**
		 * First checks if the  form for adding Vehicle Registration has errors. 
		 * Creates a new vehicle registration or
		 * renders the view again if any error occurs.
		 * @return 
		 * @throws ParseException
		 */
		public Result addVRegistration(long id) {
		    DynamicForm dynamicVRegistrationForm = Form.form().bindFromRequest();
		   Form<VehicleRegistration> addVRegistrationForm = Form.form(VehicleRegistration.class).bindFromRequest();
		   Vehicle v=Vehicle.findById(id);
		   if(v==null){
				flash("VehicleNull",  "Vehicle doesn't exists!");
				return redirect("/");}
			/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
				Logger.debug("Error at adding Travel Order");
				flash("error", "Error at Travel Order form!");
				return redirect("/addTravelOrder");
			}*/
		   String regNo;
			Date regDate;
			Date expireDate;
			try{	
				regNo = addVRegistrationForm.bindFromRequest().get().regNo;
				//regDate = addVRegistrationForm.bindFromRequest().get().regDate;
				expireDate = addVRegistrationForm.bindFromRequest().get().expirationDate;
			
				VehicleRegistration vr= VehicleRegistration.saveToDB(regNo, v, expireDate);
				v.isRegistered=true;
				v.save();
				Logger.info(session("name") + " created vehicle registration ");
				if(vr!=null){
					flash("addVehicleRegistrationSuccess",  "Vehicle Registration successfully added!");
				return redirect("/allvregistrations");
				}
				else{
					flash("addVRegistrationError", "Vehicle is null ");
					return redirect("/");

				}
			}catch(Exception e){
			flash("addVehicleRegistrationError", "Error at adding vehicle registration ");
			Logger.error("Adding vehicle registration error: " + e.getMessage(), e);
			return redirect("/addvregistrationview/"+id);
		   }
		}
		
		/**
		 * Finds VehicleRegistration object using id and shows it 
		 * @param id - VehicleRegistration id
		 * @return 
		 */
		public Result showVRegistration(long id) {
			VehicleRegistration vr = VehicleRegistration.findById(id);
			if (vr == null) {
				Logger.error("error", "Vehicle Registration null()");
				flash("error", "There isn't such  Vehicle Registration!");
				return redirect("/");
			}
			return ok(showVRegistration.render(vr));
		}
		
		/**
		 * Finds Vehicle Registration object using id
		 * and then deletes it from database 
		 * @param id - Vehicle registration id (long)
		 * @return redirect to index after delete
		 */
		public Result deleteVRegistration(long id) {
			try {
				VehicleRegistration vr= VehicleRegistration.findById(id);
				Logger.info("VehicleRegistration deleted: \"" + vr.id);
				VehicleRegistration.deleteVehicleRegistration(id);
				return redirect("/allvehicleregistrations");
			} catch (Exception e) {
				flash("deleteVRegistrationError", "Error at deleting vehicle registration!");
				Logger.error("Error at deleting vehicle registration: " + e.getMessage());
				return redirect("/");
			}
		}
		
		/**
		 * Renders the view for editing Vehicle registration object.
		 *  @param id long
		 * @return Result 
		 */
		public Result editVRegistrationView(long id) {
			VehicleRegistration vr = VehicleRegistration.findById(id);
			// Exception handling.
			if (vr == null) {
				flash("VRegistrationNull", "Vehicle registration doesn't exist");
				return redirect("/");
			}
			//Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class).fill(to);
			return ok(editVRegistrationView.render(vr));

		}
		
		/**
		 *  Method receives an id, finds the specific Vehicle registration object 
		 *  and updates its information with data collected from editVRegistration form
		 * again.
		 * @param id of Vehicle Registration object
		 * @return Result 
		 */
		public Result editVRegistration(long id) {
			//DynamicForm updateTravelorderForm = Form.form().bindFromRequest();
			Form<VehicleRegistration> vRegistrationForm = Form.form(VehicleRegistration.class).bindFromRequest();
			VehicleRegistration vr  = VehicleRegistration.findById(id);
			String regNo;
			Date regDate;
			Date expireDate;
			try {
				if (vRegistrationForm.hasErrors() || vRegistrationForm.hasGlobalErrors()) {
					Logger.info("Vehicle Registration update error");
					flash("error", "Error in vehicle registration update form");
					return ok(editVRegistrationView.render(vr));
				}
				regNo = vRegistrationForm.bindFromRequest().get().regNo;
				//regDate = vRegistrationForm.bindFromRequest().get().regDate;
				expireDate = vRegistrationForm.bindFromRequest().get().expirationDate;
				
				vr.regNo=regNo;
			//	vr.regDate=regDate;
				vr.expirationDate=expireDate;
				vr.save();
				Logger.info(session("name") + " updated vehicle registration: " + vr.id);
				flash("vehicleRegistrationUpdateSuccess",   "Vehicle registration successfully updated!");
				return ok(showVRegistration.render(vr));			} 
				catch (Exception e) {
				flash("error", "Error at editing Vehicle Registration");
				Logger.error("Error at updating VehicleRegistration: " + e.getMessage(), e);
				return redirect("/");
			}
		}
		
		
		
		public Result listVRegistrations() {
			List<VehicleRegistration> allVRegistrations=VehicleRegistration.listOfVRegistrations();
			if(allVRegistrations!=null){
			return ok(listAllVRegistrations.render(allVRegistrations));
			}
			else{
				flash("listVRegistrationsError", "No Vehicle registrations in database!");
					return redirect("/");
			}
		}
}

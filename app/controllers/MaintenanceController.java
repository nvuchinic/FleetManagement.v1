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

public class MaintenanceController extends Controller{

	/**
	 * Form for creating/editing Maintenance object
	 */
		static Form<Maintenance> travelOrderForm = Form.form(Maintenance.class);
		
		/**
		 * Finder for Maintenance object
		 */
		public static Finder<Long, Maintenance> findTO = new Finder<Long, Maintenance>(Long.class,
				Maintenance.class);
		
		/**
		 * Renders the 'add Maintenance' form view
		 * @return
		 */
		public Result addCarView() {
			List<Vehicle> allVehicles=Vehicle.find.all();
			//List<Service> allServices=new ArrayList<Service>();
			return ok(addCarForm.render(allVehicles));
		}
		
		/**
		 * Finds Maintenance object using id and shows it 
		 * @param id - Maintenance id
		 * @return 
		 */
		public Result showMaintenance(long id) {
		Maintenance mnt = Maintenance.findById(id);
			if (mnt == null) {
				Logger.error("error", "Maintenance null()");
				flash("error", "Maintenance null!");
				return redirect("/");
			}
			return ok(showMaintenance.render(mnt));
		}
		
		public Result chooseCar() {
			List<Service> allServices=new ArrayList<Service>();
			allServices=Service.find.all();
			   DynamicForm dynamicAddCarForm= Form.form().bindFromRequest();
			 //  Form<Maintenance> addCarForm = Form.form(TravelOrder.class).bindFromRequest();
				/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
					Logger.debug("Error at adding Travel Order");
					flash("error", "Error at Travel Order form!");
					return redirect("/addTravelOrder");
				}*/
				String vehicleName;
				try{	
					//DynamicForm requestData = Form.form().bindFromRequest();
					vehicleName=  dynamicAddCarForm.get("vehicleName");
					Vehicle v=Vehicle.findByName(vehicleName);
					if(v==null){
						flash("VehicleIsNull",  "Vehicle is null!");
						return redirect("/");
						}
					
					Logger.info(session("name") + " chosen vehicle ");
					if(v!=null){
						flash("vehicleChooseSuccess",  "Vehicle successfully chosen!");
						return ok(addMaintenanceForm.render(v,allServices));
					}
					else{
						flash("error", "Error at choosing Vehicle ");
						return redirect("/allmaintenances");

					}
				}catch(Exception e){
				flash("error", "Error at choosing Vehicle ");
				Logger.error("Choosing Vehicle error: " + e.getMessage(), e);
				return redirect("/addcarview");
			   }
			}
		}
		
		
}

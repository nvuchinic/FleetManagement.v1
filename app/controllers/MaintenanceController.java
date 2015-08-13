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
		public Result addMaintenanceView(Long id) {
			List<Service> allServices=new ArrayList<Service>();
			allServices=Service.find.all();
			Vehicle v=Vehicle.findById(id);
			return ok(addMaintenanceForm.render(v,allServices));
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
			List<Service> mServices=new ArrayList<Service>();
			for(Service s:mnt.services){
				mServices.add(s);
			}
			//for debbuging
			System.out.println("Ispisujem servise odrzavanja");
			for(Service s:mServices){
				System.out.println(s.stype);
			}
			return ok(showMaintenance.render(mnt, mServices));
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
		
		/**
		 * First checks if the  form for adding Vehicle Registration has errors. 
		 * Creates a new vehicle registration or
		 * renders the view again if any error occurs.
		 * @return 
		 * @throws ParseException
		 */
		public Result addMaintenance(long id) {
		    DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		   Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class).bindFromRequest();
		   Vehicle v=Vehicle.findById(id);
		   if(v==null){
				flash("VehicleNull",  "Vehicle doesn't exists!");
				return redirect("/");}
			/*if (addTravelOrderForm.hasErrors() || addTravelOrderForm.hasGlobalErrors()) {
				Logger.debug("Error at adding Travel Order");
				flash("error", "Error at Travel Order form!");
				return redirect("/addTravelOrder");
			}*/
		   String serviceType=null;
			Date mDate;
			Service service=new Service();
			try{	
				serviceType = addMaintenanceForm.bindFromRequest().get().serviceType;
				mDate=addMaintenanceForm.bindFromRequest().get().mDate;
				//regNo = vRegistrationForm.bindFromRequest().get().regNo;
				service=Service.findByType(serviceType);
				Maintenance mn=Maintenance.saveToDB(v, service, mDate);
				v.maintenances.add(mn);
				v.save();
				Logger.info(session("name") + " created maintenance ");
				if(mn!=null){
					flash("addMaintenanceSuccess",  "Maintenance successfully added!");
				return redirect("/allmaintenances");
				}
				else{
					flash("addMaintenanceError", "Error at adding maintenance ");
					return redirect("/addmaintenanceview");

				}
			}catch(Exception e){
			flash("addMaintenanceError", "Error at adding maintenance ");
			Logger.error("Adding maintenance error: " + e.getMessage(), e);
			return redirect("/addmaintenanceview/"+v.id);
		   }
		}
		
		
		
		/**
		 * Finds Vehicle Registration object using id
		 * and then deletes it from database 
		 * @param id - Vehicle registration id (long)
		 * @return redirect to index after delete
		 */
		public Result deleteMaintenance(long id) {
			try {
				Maintenance mn= Maintenance.findById(id);
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
		 *  @param id long
		 * @return Result 
		 */
		public Result editMaintenanceView(long id) {
			Maintenance mn= Maintenance.findById(id);
			// Exception handling.
			if (mn == null) {
				flash("MaintenanceNull", "Maintenance record doesn't exist");
				return redirect("/");
			}
			//Form<TravelOrder> travelOrderForm = Form.form(TravelOrder.class).fill(to);
			return ok(editMaintenanceView.render(mn));

		}
		
		/**
		 *  Method receives an id, finds the specific Vehicle registration object 
		 *  and updates its information with data collected from editVRegistration form
		 * again.
		 * @param id of Vehicle Registration object
		 * @return Result 
		 */
		public Result editMaintenance(long id) {
			DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
			Form<Maintenance> maintenanceForm = Form.form(Maintenance.class).bindFromRequest();
			Maintenance mn  = Maintenance.findById(id);
			String serviceType;
			Date mDate;
			Service service;
			try {
				if (maintenanceForm.hasErrors() || maintenanceForm.hasGlobalErrors()) {
					Logger.info("Maintenance update error");
					flash("error", "Error in maintenance update form");
					return ok(editMaintenanceView.render(mn));
				}
				serviceType = maintenanceForm.bindFromRequest().get().serviceType;
				mDate=maintenanceForm.bindFromRequest().get().mDate;
				//regNo = vRegistrationForm.bindFromRequest().get().regNo;
				service=Service.findByType(serviceType);
				
				mn.services.add(service);
				mn.mDate=mDate;
				mn.save();
				List<Service> mServices=new ArrayList<Service>();
				for(Service s:mn.services){
					mServices.add(s);
				}
				System.out.println("Ispisujem servise odrzavanja");
				for(Service s:mServices){
					System.out.println(s.stype);
				}
				Logger.info(session("name") + " updated vehicle registration: " + mn.id);
				flash("maintenanceUpdateSuccess",   "Maintenance successfully updated!");
				return ok(showMaintenance.render(mn,mServices));			} 
				catch (Exception e) {
				flash("maintenanceUpdateError", "Error at editing maintenance");
				Logger.error("Error at updating maintenance: " + e.getMessage(), e);
				return redirect("/");
			}
		}
		
		
		
		public Result listMaintenances() {
			List<Maintenance> allMaintenances=Maintenance.listOfMaintenances();
			if(allMaintenances!=null){
			return ok(listAllMaintenances.render(allMaintenances));
			}
			else{
				flash("listMaintenancesError", "No maintenances records in database!");
					return redirect("/");
			}
		}
		
		}
		
		


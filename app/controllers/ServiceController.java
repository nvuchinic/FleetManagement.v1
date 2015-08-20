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

public class ServiceController extends Controller {

	static Form<Service> serviceForm = Form.form(Service.class);

	/**
	 * Finder for Vehicle class
	 */
	//public static Finder<Long, Service> find = new Finder<Long, Service>(Long.class,
		//	Service.class);
	public static Finder<Long, Service> find = new Finder<>(Service.class);
	
	/**
	 * Renders the form for creating new Service object
	 * @return
	 */
	public Result addServiceView() {
		return ok(addServiceForm.render());
	}
	
	/**
	 * Finds vehicle using id and shows it 
	 * @param id - Vehicle id
	 * @return redirect to the vehicle profile
	 */
	public Result showService(long id) {
		Service srv = Service.findById(id);
		if (srv == null) {
			Logger.error("error", "Service null at showService()");
			flash("serviceDetailsError", "Service is null!");
			return redirect("/");
		}
		return ok(showService.render(srv));
	}
	
	/**
	 * Finds Service object based on passed parameter,
	 * then removes it from database
	 * @param id - Service id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteService(long id) {
		try {
			Service srv = Service.findById(id);
			Logger.info("Deleted service: \"" + srv.stype + "\"");
			Service.deleteService(id);
			return redirect("/allservices");
		} catch (Exception e) {
			flash("deletingServiceError", "Deleting service error!");
			Logger.error("Error at deleting Service: " + e.getMessage());
			return redirect("/showservice");
		}
	}
	
	
	/**
	 * Renders the view of a Service object. Method receives the id of the service and
	 * finds the vehicle by id and send's the Service object to the view.
	 * @param id long
	 * @return Result render form for editing Service object
	 */
	public Result editServiceView(long id) {
		Service srv = Service.findById(id);
		// Exception handling.
		if (srv == null) {
			flash("error", "Service does not exist");
			return redirect("/");
		}
		return ok(editServiceView.render(srv));

	}
	
	/**
	 *  Method receives an id, finds the specific Service object,
	 *  then takes data from submitted form and updates Service object with them
	 * @param id of Service object
	 * @return Result render the vehicle edit view
	 */
	public Result editService(long id) {
		DynamicForm updateServiceForm = Form.form().bindFromRequest();
		Form<Service> serviceForm = Form.form(Service.class).bindFromRequest();
		Service srv = Service.findById(id);
		try {
			if (serviceForm.hasErrors() || serviceForm.hasGlobalErrors()) {
				Logger.info("Service update error");
				flash("error", "Error in updateService form");
				return ok(editServiceView.render(srv));
			}

			String serviceType = serviceForm.bindFromRequest().get().stype;
				
			String description = serviceForm.bindFromRequest().get().description;
			srv.stype = serviceType;
			srv.description = description;
			srv.save();
			
			Logger.info(session("name") + " updated service: " + srv.id);
			flash("serviceUpdateSuccess", srv.id + " successfully updated!");
			return ok(listAllServices.render(Service.listOfServices()));
		} catch (Exception e) {
			flash("error", "Error at editing service");
			Logger.error("Error at updateService: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	/**
	 * First checks if the service adding form has errors. Creates a new Service object or
	 * renders the view again if any error occurs.
	 * @return 
	 * @throws ParseException
	 */
	public Result addService() {

		Form<Service> addServiceForm = Form.form(Service.class).bindFromRequest();
		
		if (addServiceForm.hasErrors() || addServiceForm.hasGlobalErrors()) {
			Logger.debug("Error at adding service");
			flash("error", "Error at service form!");
			return redirect("/addservice");
		}

		try{	
			
			String stype = addServiceForm.bindFromRequest().get().stype;
			String description = addServiceForm.bindFromRequest().get().description;
			Service.saveToDB(stype, description);

				Logger.info(session("name") + " created service ");
				flash("addingServiceSuccess",  "Service successfully added!");
				return redirect("/allservices");
			
		}catch(Exception e){
		flash("error", "Error at adding service");
		Logger.error("Error at addingService: " + e.getMessage(), e);
		return redirect("/addservice");
	   }
	}
	
	public Result listServices() {
		List<Service> allServices=Service.listOfServices();
		if(allServices!=null){
		return ok(listAllServices.render(allServices));
		}
		else{
			flash("listServicesError", "No services in database!");
				return redirect("/");
		}
	}
}

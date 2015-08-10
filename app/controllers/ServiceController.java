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
/*
public class ServiceController extends Controller {

	static Form<Service> serviceForm = Form.form(Service.class);

	*//**
	 * Finder for Vehicle class
	 *//*
	public static Finder<Long, Service> find = new Finder<Long, Service>(Long.class,
			Service.class);
	
	*//**
	 * Renders the form for creating new Service object
	 * @return
	 *//*
	public Result addServiceView() {
		return ok(addServiceForm.render());
	}
	
	*//**
	 * Finds vehicle using id and shows it 
	 * @param id - Vehicle id
	 * @return redirect to the vehicle profile
	 *//*
	public Result showService(long id) {
		Service srv = Service.findById(id);
		if (srv == null) {
			Logger.error("error", "Service null at showService()");
			flash("serviceDetailsError", "Service is null!");
			return redirect("/");
		}
		return ok(showService.render(srv));
	}
	
	*//**
	 * Finds Service object based on passed parameter,
	 * then removes it from database
	 * @param id - Service id (long)
	 * @return redirect to index after delete
	 *//*
	public Result deleteService(long id) {
		try {
			Service srv = Service.findById(id);
			Logger.info("Deleted service: \"" + srv.type + "\"");
			Service.deleteService(id);
			return redirect("/allservices");
		} catch (Exception e) {
			flash("deletingServiceError", "Deleting service error!");
			Logger.error("Error at deleting Service: " + e.getMessage());
			return redirect("/showservice");
		}
	}
}
*/
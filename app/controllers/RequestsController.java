package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class RequestsController extends Controller{

	/**
	 * Finder for Requests object
	 */
	public static Finder<Long, Requests> find = new Finder<Long, Requests>(
			Requests.class);
	
	public Result addRequestView() {
		return ok(addRequestForm.render());
	}
	
	
	public Result addRequest() {
		DynamicForm dynamicRequestForm = Form.form().bindFromRequest();
		Form<Requests> addRequestForm = Form.form(Requests.class).bindFromRequest();
		/*
		 * if (addRequestForm.hasErrors() || addRequestForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Request"); flash("error",
		 * "ERROR ADDING REQUEST!");
		 *  return redirect("/allclients"); }
		 */
		String description=null;
		String routeIdString=null;
		long routeId;
		
		try {
			description = addRequestForm.bindFromRequest().get().description;
			routeIdString = dynamicRequestForm.get("routeId");
			routeId=Long.parseLong(routeIdString);
			Requests rs = Requests.saveToDB(description, routeId);
			System.out.println("REQUESTS ADDED SUCCESSFULLY///////////////////////");
			Logger.info("REQUESTS ADDED SUCCESSFULLY///////////////////////");
			flash("success", "REQUESTS SUCCESSFULLY ADDED");
			return ok(showRequests.render(rs));
		} catch (Exception e) {
			flash("error", "ERROR AT ADDING REQUESTS ");
			Logger.error("ADDING REQUESTS ERROR: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
	public Result showRequests(long id) {
		Requests rs = Requests.findById(id);
		return ok(showRequests.render(rs));
	}
	
}

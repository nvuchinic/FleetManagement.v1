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

public class RouteController extends Controller{

	public static Finder<Long, Route> find = new Finder<>(Route.class);
	
	/**
	 * Generates view(form) for adding new Route object
	 * @return
	 */
	public Result addRouteView() {
		return ok(addRouteForm.render());
	}
	
	/**
	 * 
	 * Creates a new insurance object or
	 * renders the view again if any error occurs.
	 * @return 
	 * @throws ParseException
	 */
	public Result addRoute() {
	//   DynamicForm dynamicRouteForm = Form.form().bindFromRequest();
	   Form<Route> addRouteForm = Form.form(Route.class).bindFromRequest();
	   //Vehicle v=Vehicle.findById(id);
	  
		/*if (addRouteForm.hasErrors() || addRouteForm.hasGlobalErrors()) {
			Logger.debug("Error at adding Route");
			flash("error", "Error at Route adding  form!");
			return redirect("/addInsurance");
		}*/
	  String startPoint;
	  String endPoint;
		try{	
			startPoint = addRouteForm.bindFromRequest().get().startPoint;
			endPoint = addRouteForm.bindFromRequest().get().endPoint;
			Route r= Route.saveToDB(startPoint, endPoint);
			System.out.println("ROUTE ADDED SUCCESSFULLY///////////////////////");
			Logger.info("ROUTE ADDED SUCCESSFULLY///////////////////////");
			return redirect("/allroutes");
		}catch(Exception e){
		flash("addRouteError", "ERROR AT ADDING ROUTE ");
		Logger.error("ADDING ROUTE ERROR: " + e.getMessage(), e);
		return redirect("/addrouteview");
	   }
	}
	
	/**
	 * Finds Route object based on passed ID parameter
	 *  and shows it in view
	 * @param id - Route object ID
	 * @return 
	 */
	public Result showRoute(long id) {
		Route rt = Route.findById(id);
		if (rt == null) {
			Logger.error("error", "ROUTE IS NULL");
			flash("error", "NO SUCH ROUTE IN DATABASE!!!");
			return redirect("/allroutes");
		}
		return ok(showRoute.render(rt));
	}
	
	/**
	 * Finds Route object using passed ID parameter
	 * and then removes it from database 
	 * @param id - Route object ID
	 * @return 
	 */
	public Result deleteRoute(long id) {
		try {
			Route rt= Route.findById(id);
			Logger.info("ROUTE DELETED: \"" + rt.id);
			Route.deleteRoute(id);
			return redirect("/allroutes");
		} catch (Exception e) {
			flash("deleteRoute	Error", "ERROR DELETING ROUTE!");
			Logger.error("ERROR DELETING ROUTE: " + e.getMessage());
			return redirect("/");
		}
	}
	
	/**
	 * Renders the view for editing Route object.
	 *  @param Route object ID number
	 * @return 
	 */
	public Result editRouteView(long id) {
		Route rt = Route.findById(id);
		// Exception handling.
		if (rt == null) {
			flash("routeNull", "NO SUCH ROUTE IN DATABASE");
			return redirect("/allroutes");
		}
		return ok(editRouteView.render(rt));

	}
	
	/**
	 *  finds the specific Route object using passed ID parameter,
	 *  and updates it with information collected from editInsurance view form
	 * again.
	 * @param id ID number of Route object
	 * @return Result 
	 */
	public Result editRoute(long id) {
		   DynamicForm dynamicRouteForm = Form.form().bindFromRequest();
		Form<Route> routeForm = Form.form(Route.class).bindFromRequest();
		Route rt  = Route.findById(id);
		 String startPoint;
		 String endPoint;
			
		try {
//			if (routeForm.hasErrors() || routeForm.hasGlobalErrors()) {
//				Logger.info("ROUTE UPDATE ERROR");
//				flash("error", "UPDATE ROUTE ERROR");
//				return ok(editRouteView.render(rt));
//			}
			startPoint = routeForm.bindFromRequest().get().startPoint;
			if(startPoint==null){
				startPoint=rt.startPoint;
			}
			endPoint = routeForm.bindFromRequest().get().endPoint;

			rt.startPoint=startPoint;
			rt.endPoint=endPoint;
			rt.save();
			Logger.info("ROUTE UPDATED");
			flash("ROUTEUpdateSuccess",   "ROUTE UPDATED SUCCESSFULLY!");
			return ok(showRoute.render(rt));		
			} 
			catch (Exception e) {
			flash("error", "ERROR UPDATING ROUTE");
			Logger.error("ERROR UPDATING ROUTE: " + e.getMessage(), e);
			return redirect("/allroutes");
		}
	}
	
	
	
	public Result listRoutes() {
		List<Route> allRoutes=Route.listOfRoutes();
		if(allRoutes!=null){
		return ok(listAllRoutes.render(allRoutes));
		}
		else{
			flash("listInsurancesError", "NO ROUTE RECORDS IN DATABASE!");
				return redirect("/");
		}
	}
}
	


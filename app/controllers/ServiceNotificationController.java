package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.*;

public class ServiceNotificationController extends Controller{

	public static Finder<Long, ServiceNotification> find = new Finder<Long, ServiceNotification>(
			ServiceNotification.class);

	
	public Result listAllServiceNotifications() {
		List<ServiceNotification> allSNotifications=ServiceNotification.find.all();
		return ok(listAllSNotificationsView.render(allSNotifications));
	}
	
	
//	public Result listNonEmptySNotifications() {
//		List<ServiceNotification> nonEmptySNotifications=ServiceNotification.getNonEmptySNotifications();
//		return ok(listAllSNotificationsView.render(nonEmptySNotifications));
//	}
	
	
	public Result showServiceNotification(long id) {
		ServiceNotification sn = ServiceNotification.find.byId(id);
		return ok(showServiceNotification.render(sn));
	}

	
	/**
	 * Finds ServiceNotification object by it's ID  number 
	 * passed as parameter and then removes it from database
	 * @param id-ID number of ServiceNotification object
	 * @return
	 */
	public Result deleteServiceNotification(long id) {
		try {
			ServiceNotification sn  = ServiceNotification.find.byId(id);
			RenewalNotification.deleteRenewalNotification(id);
			Logger.info("SERVICE NOTIFICATION DELETED: \"" + sn.id);
			return redirect("/allservicenotifications");
		} catch (Exception e) {
			flash("error", "ERROR DELETING SERVICE NOTIFICATION!");
			Logger.error("ERROR DELETING SERVICE NOTIFICATION: " + e.getMessage());
			return redirect("/");
		}
	}

	
	public  Result noOfServiceNotifications() {
	      int no = ServiceNotification.find.all().size();
	      System.out.println("/////////////PRINTING NO OF SERVICE NOTIFICATIONS "+no);
	      return ok(ajax_serviceNotificationsNumber.render(no));
	  }

	
//	public  Result javascriptRoutes()
//	{
//	    response().setContentType("text/javascript");
//	    return ok(Routes.javascriptRouter("javascriptRoutes", 
//	    		controllers.routes.javascript.RenewalNotificationController.noOfNotifications()
//    		));
//	}
	
	
//	public Result listRenewalNotifications() {
//		List<RenewalNotification> allRenewalNotifications = RenewalNotification.find.all();
//		if (allRenewalNotifications != null) {
//			return ok(listAllRenewalNotifications.render(allRenewalNotifications));
//		} else {
//			flash("error", "NO RENEWAL NOTIFICATION RECORDS IN DATABASE!");
//			return redirect("/");
//		}
//	}
}

package controllers;

import java.util.List;
import models.*;
import models.Route;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Result;
import views.html.*;

import play.mvc.*;

import com.avaje.ebean.Model.Finder;

public class RenewalNotificationController extends Controller{

	public static Finder<Long, RenewalNotification> find = new Finder<Long, RenewalNotification>(
			RenewalNotification.class);

	
	public Result showAllNotifications() {
		List<RenewalNotification> allNotifications=RenewalNotification.find.all();
		return ok(showAllNotificationsView.render(allNotifications));
	}

	
	public Result listAllNotifications() {
		List<RenewalNotification> allNotifications=RenewalNotification.find.all();
		return ok(listAllNotificationsView.render(allNotifications));
	}
	
	
	public Result listNonEmptyNotifications() {
		List<RenewalNotification> nonEmptyNotifications=RenewalNotification.getNonEmptyNotifications();
		return ok(listAllNotificationsView.render(nonEmptyNotifications));
	}
	
	
	public Result showRenewalNotification(long id) {
		RenewalNotification rn = RenewalNotification.find.byId(id);
		return ok(showRenewalNotification.render(rn));
	}

	
	/**
	 * Finds RenewalNotification object by it's ID  number 
	 * passed as parameter and then removes it from database
	 * @param id-ID number od RenewalNotification object
	 * @return
	 */
	public Result deleteRenewalNotification(long id) {
		try {
			RenewalNotification rn  = RenewalNotification.find.byId(id);
			Logger.info("RENEWAL NOTIFICATION DELETED: \"" + rn.id);
			RenewalNotification.deleteRenewalNotification(id);
			return redirect("/");
		} catch (Exception e) {
			flash("deleteRoute	Error", "ERROR DELETING ROUTE!");
			Logger.error("ERROR DELETING ROUTE: " + e.getMessage());
			return redirect("/");
		}
	}

	public  Result noOfNotifications() {
	      int no = RenewalNotification.numberOfNENotifications();
	      System.out.println("/////////////PRINTING NO OF NE NOTIFICATIONS "+no);
	      return ok(ajax_result2.render(no));
	  }

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

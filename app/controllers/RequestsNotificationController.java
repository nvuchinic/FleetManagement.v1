package controllers;

import java.util.List;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import models.*;
import controllers.*;

import com.avaje.ebean.Model.Finder;

import play.*;
import play.mvc.*;
import play.Logger;
//import play.api.Routes;
//import play.Routes;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
//import router.Routes;
import views.html.*;


public class RequestsNotificationController extends Controller{

	public static Finder<Long, RequestsNotification> find = new Finder<Long, RequestsNotification>(
			RequestsNotification.class);

	
	public Result showAllNotifications() {
		List<RequestsNotification> allNotifications=RequestsNotification.find.all();
		return ok(showAllRequestsNotificationsView.render(allNotifications));
	}
	
	public Result showNotification(long id) {
		RequestsNotification rn = RequestsNotification.find.byId(id);
		return ok(showRequestsNotification.render(rn));
	}
	
	public  Result noOfRequestsNotifications() {
	      int no = RequestsNotification.find.all().size();
	      System.out.println("/////////////PRINTING NO OF REQUESTS NOTIFICATIONS "+no);
	      return ok(ajax_requestsNotificationsNumber.render(no));
	  }
}

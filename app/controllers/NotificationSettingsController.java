package controllers;

import java.util.List;
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

public class NotificationSettingsController extends Controller{

	/**
	 * finder for NotificationSettings Object object
	 */
	public static Finder<Long, NotificationSettings> find = new Finder<Long, NotificationSettings>(
			NotificationSettings.class);

	/**
	 * Generates view(form) for creating NotificationSettings object
	 * @return
	 */
	public Result addNotificationSettingsView() {
		if(NotificationSettings.getInstance()==null){
			return ok(addNotificationSettingsForm.render());
		}
		return ok(showNotificationSettings.render(NotificationSettings.getInstance()));
	}

	/**
	 * 
	 * Creates a NotificationSettings object using values from request
	 * (collected through form)
	 * 
	 * @return
	 * @throws ParseException
	 */
	public Result addNotificationSetting() {
		DynamicForm addNotificationSettingsDynamicForm = Form.form().bindFromRequest();
		Form<NotificationSettings> addNotificationSettingsForm = Form.form(NotificationSettings.class).bindFromRequest();
		/*
		 * if (addNotificationSettingsForm.hasErrors() || addNotificationSettingsForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR AT NOTIFICATION SETTINGS CREATING FORM"); flash("error",
		 * "ERROR AT NOTIFICATION SETTINGS CREATING FORM"); return redirect("/allclients");
		 *  }
		 */
		boolean isOn;
		boolean emailNotif;
		int threshold;
		String timeUnit=null;
		try {
			//isOn = addNotificationSettingsDynamicForm.get("isOn");
			isOn=addNotificationSettingsForm.bindFromRequest().get().isOn;
			System.out.println("PRINTING NOTIFICATION STATUS:"+isOn);
			emailNotif=addNotificationSettingsForm.bindFromRequest().get().emailNotif;
			System.out.println("EMAIL NOTIFICATION STATUS:"+emailNotif);
			threshold=addNotificationSettingsForm.bindFromRequest().get().threshold;
			System.out.println("PRINTING THRESHOLD:"+threshold);
			timeUnit = addNotificationSettingsDynamicForm.get("timeUnit");
			NotificationSettings ns=NotificationSettings.getInstance();
			ns.isOn=isOn;
			ns.emailNotif=emailNotif;
			ns.threshold=threshold;
			ns.timeUnit=timeUnit;
			NotificationSettings.saveToDB(ns);
			Logger.info("MODEL ADDED SUCCESSFULLY///////////////////////");
			flash("success", "NOTIFICATION SETTINGS SAVED SUCCESSFULLY");
			return ok(showNotificationSettings.render(ns));
		} catch (Exception e) {
			flash("error", "ERROR SAVING NOTIFICATION SETTINGS ");
			Logger.error("ERROR SAVING NOTIFICATION SETTINGS: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	public Result editNotificationSettingsView(){
		if(NotificationSettings.getInstance()==null){
			return ok(addNotificationSettingsForm.render());
		}
		return ok(editNotificationSettingsView.render(NotificationSettings.getInstance()));
	}
	
	
	public Result editNotificationSetting() {
		DynamicForm addNotificationSettingsDynamicForm = Form.form().bindFromRequest();
		Form<NotificationSettings> addNotificationSettingsForm = Form.form(NotificationSettings.class).bindFromRequest();
		/*
		 * if (addNotificationSettingsForm.hasErrors() || addNotificationSettingsForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR AT NOTIFICATION SETTINGS CREATING FORM"); flash("error",
		 * "ERROR AT NOTIFICATION SETTINGS CREATING FORM"); return redirect("/allclients");
		 *  }
		 */
		boolean isOn;
		boolean emailNotif;
		int threshold;
		String timeUnit=null;
		try {
			//isOn = addNotificationSettingsDynamicForm.get("isOn");
			isOn=addNotificationSettingsForm.bindFromRequest().get().isOn;
			System.out.println("PRINTING NOTIFICATION STATUS:"+isOn);
			emailNotif=addNotificationSettingsForm.bindFromRequest().get().emailNotif;
			System.out.println("EMAIL NOTIFICATION STATUS:"+emailNotif);
			threshold=addNotificationSettingsForm.bindFromRequest().get().threshold;
			System.out.println("PRINTING THRESHOLD:"+threshold);
			timeUnit = addNotificationSettingsDynamicForm.get("timeUnit");
			NotificationSettings ns=NotificationSettings.getInstance();
			ns.isOn=isOn;
			ns.emailNotif=emailNotif;
			ns.threshold=threshold;
			ns.timeUnit=timeUnit;
			Logger.info("MODEL ADDED SUCCESSFULLY///////////////////////");
			flash("success", "NOTIFICATION SETTINGS SAVED SUCCESSFULLY");
			return ok(showNotificationSettings.render(ns));
		} catch (Exception e) {
			flash("error", "ERROR SAVING NOTIFICATION SETTINGS ");
			Logger.error("ERROR SAVING NOTIFICATION SETTINGS: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	

	
	

	
}

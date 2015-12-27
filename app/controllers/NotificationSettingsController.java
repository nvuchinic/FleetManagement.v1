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
		
		return TODO;
	}

	
	public Result showNotificationSettings() {
		String name="singleton";
		NotificationSettings ns =NotificationSettings.findByName(name);
		return ok(showNotificationSettings.render(ns));
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
		boolean isOn=false;
		int threshold;
		String timeUnit=null;
		String isRegistrationToString=null;
		String isInspectionToString=null;
		String isInsuranceToString=null;
		try {
			isRegistrationToString=addNotificationSettingsDynamicForm.get("registration");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD REGISTRATION:"+isRegistrationToString);
			isInspectionToString=addNotificationSettingsDynamicForm.get("inspection");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD INSPECTION:"+isInspectionToString);
			isInsuranceToString=addNotificationSettingsDynamicForm.get("insurance");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD INSURANCE:"+isInsuranceToString);
			//isOn = addNotificationSettingsDynamicForm.get("isOn");
			//isOn=addNotificationSettingsForm.bindFromRequest().get().isOn;
			System.out.println("PRINTING NOTIFICATION STATUS:"+isOn);
			//emailNotif=addNotificationSettingsForm.bindFromRequest().get().emailNotif;
			//System.out.println("EMAIL NOTIFICATION STATUS:"+emailNotif);
			threshold=addNotificationSettingsForm.bindFromRequest().get().threshold;
			System.out.println("PRINTING THRESHOLD:"+threshold);
			timeUnit = addNotificationSettingsDynamicForm.get("timeUnit");
			String name="singleton";
			NotificationSettings ns=NotificationSettings.findByName(name);
			//ns.isNotifOn=isOn;
			//ns.emailNotif=emailNotif;
			ns.threshold=threshold;
			ns.timeUnit=timeUnit;
			if(isRegistrationToString.equalsIgnoreCase("registration")){
				ns.registrationNotificationOn=true;
			}else{
				ns.registrationNotificationOn=false;
			}
			if(isInspectionToString.equalsIgnoreCase("inspection")){
				ns.inspectionNotificationOn=true;
			}else{
				ns.inspectionNotificationOn=false;
			}
			if(isInsuranceToString.equalsIgnoreCase("insurance")){
				ns.insuranceNotificationOn=true;
			}else{
				ns.registrationNotificationOn=false;
			}
			ns.save();
			Logger.info("MODEL ADDED SUCCESSFULLY///////////////////////");
			flash("success", "NOTIFICATION SETTINGS SAVED SUCCESSFULLY");
			return redirect("/shownotificationsettings");
		} catch (Exception e) {
			flash("error", "ERROR SAVING NOTIFICATION SETTINGS ");
			Logger.error("ERROR SAVING NOTIFICATION SETTINGS: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	public Result editNotificationSettingsView(){
		//if(NotificationSettings.getInstance()==null){
			//return ok(addNotificationSettingsForm.render());
		//}
		String name="singleton";
		NotificationSettings ns=NotificationSettings.findByName(name);
		//ns.save();
		return ok(editNotificationSettingsView.render(ns));
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
		boolean isOn=false;
		boolean emailNotif;
		int threshold;
		String timeUnit=null;
		String isOnString=null;
		String isRegistrationToString=null;
		String isInspectionToString=null;
		String isInsuranceToString=null;
		try {
			isRegistrationToString=addNotificationSettingsDynamicForm.get("registration");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD REGISTRATION:"+isRegistrationToString);
			isInspectionToString=addNotificationSettingsDynamicForm.get("inspection");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD INSPECTION:"+isInspectionToString);
			isInsuranceToString=addNotificationSettingsDynamicForm.get("insurance");
			System.out.println("PRINTING VALUE OF CHECKBOX FIELD INSURANCE:"+isInsuranceToString);
			//isOn = addNotificationSettingsDynamicForm.get("isOn");
//			isOnString=addNotificationSettingsDynamicForm.get("isNotifOn");
//			if(isOnString==null){
//				isOn=false;
//			}
//			else{
//				isOn=true;
//			}
			//System.out.println("PRINTING NOTIFICATION STATUS:"+isOnString);
			//emailNotif=addNotificationSettingsForm.bindFromRequest().get().emailNotif;
			//System.out.println("EMAIL NOTIFICATION STATUS:"+emailNotif);
			threshold=addNotificationSettingsForm.bindFromRequest().get().threshold;
			System.out.println("PRINTING THRESHOLD:"+threshold);
			timeUnit = addNotificationSettingsDynamicForm.get("timeUnit");
			String name="singleton";
			NotificationSettings ns=NotificationSettings.findByName(name);
			if(isRegistrationToString!=null){
				ns.registrationNotificationOn=true;
			}else{
				ns.registrationNotificationOn=false;
			}
			if(isInspectionToString!=null){
				ns.inspectionNotificationOn=true;
			}else{
				ns.inspectionNotificationOn=false;
			}
			if(isInsuranceToString!=null){
				ns.insuranceNotificationOn=true;
			}else{
				ns.insuranceNotificationOn=false;
			}
			//NotificationSettings.saveToDB(ns);
			//ns.isNotifOn=isOn;
			//ns.emailNotif=emailNotif;
			ns.threshold=threshold;
			ns.timeUnit=timeUnit;
			//ns.update();
			ns.update();
			ns.save();
			Logger.info("NOTIFICATION SETTINGS UPDATED SUCCESSFULLY///////////////////////");
			flash("success", "NOTIFICATION SETTINGS SAVED SUCCESSFULLY");
			return ok(showNotificationSettings.render(ns));
		} catch (Exception e) {
			flash("error", "ERROR SAVING NOTIFICATION SETTINGS ");
			Logger.error("ERROR SAVING NOTIFICATION SETTINGS: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	

	
	

	
}

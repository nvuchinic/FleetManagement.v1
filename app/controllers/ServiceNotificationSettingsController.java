package controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;
import models.*;
import com.avaje.ebean.Model.Finder;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


public class ServiceNotificationSettingsController extends Controller{

	final String TIME_INTERVAL_TYPE="Time";
	final String METER_INTERVAL_TYPE="Meter";
	
	/**
	 * Finder for ServiceNotificationSettings object
	 */
	public static Finder<Long, ServiceNotificationSettings> find = new Finder<Long, ServiceNotificationSettings>(
			ServiceNotificationSettings.class);
	
	/**
	 * Generates view(form) for adding new ServiceNotificationSettings object
	 * @return
	 */
	public Result addServiceNotificationSettingsView() {
		return ok(addServiceNotificationSettingsForm.render());
	}
	
	
	/**
	 * Creates a new ServiceNotificationSettings object using the values from the request
	 * @return
	 * @throws ParseException
	 */
	public Result addServiceNotificationSettings() {
		DynamicForm dynamicServiceNSForm = Form.form().bindFromRequest();
		Form<ServiceNotificationSettings> addServiceNSForm = Form.form(ServiceNotificationSettings.class).bindFromRequest();
		/*
		 * if (addServiceNSForm.hasErrors() || addServiceNSForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR AT ADDING SERVICE NOTIFICATION SETTINGS"); flash("error",
		 * "ERROR AT ADDING SERVICE NOTIFICATION SETTINGS!"); return redirect("/allservicenotificationsettings"); }
		 */
		String intervalType=null, 
				timeIntervalToString=null,
				meterIntervalToString=null, 
				timeThresholdToString,
				meterThresholdToString,
				meterIntervalUnit=null,
				timeIntervalUnit=null,
				meterThresholdUnit=null, 
				timeThresholdUnit=null,
				intervalUnit=null,
				thresholdUnit=null;
		int	intervalSize=0,
				timeInterval=0, 
				meterInterval=0,
				meterThreshold=0,
				timeThreshold=0,
				thresholdSize=0;
			try {
			intervalType=dynamicServiceNSForm.bindFromRequest().data().get("intervalType");
			System.out.println("PRINTING INTERVAL TYPE IN addServiceNotificationSettings METHOD:"+intervalType);
			if((intervalType.isEmpty()) || intervalType==null){
				flash("error", "YOU MUST SELECT INTERVAL TYPE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			timeIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("timeInterval");
			System.out.println("PRINTING TIME INTERVAL STRING IN addServiceNotificationSettings METHOD:"+timeIntervalToString);
			if(timeIntervalToString!=null && !(timeIntervalToString.isEmpty()) ){
			timeInterval=Integer.parseInt(timeIntervalToString);
			}
			meterIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("meterInterval");
			System.out.println("PRINTING TIME INTERVAL STRING IN addServiceNotificationSettings METHOD:"+timeIntervalToString);
			if(meterIntervalToString!=null && !(meterIntervalToString.isEmpty())){
			meterInterval=Integer.parseInt(meterIntervalToString);
			}
			if((timeIntervalToString==null || timeIntervalToString.isEmpty()) && (meterIntervalToString==null || meterIntervalToString.isEmpty())){
				flash("error", "YOU MUST PROVIDE EITHER METER INTERVAL OR TIME INTERVAL!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(!(timeIntervalToString.isEmpty()) && !(meterIntervalToString.isEmpty())){
				flash("error", "YOU CAN PROVIDE ONLY ONE VALUE, TIME INTERVAL OR METER INTERVAL, NOT BOTH!");
				return ok(addServiceNotificationSettingsForm.render());
			}
		meterIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterIntervalUnit");
		System.out.println("//////////////////PRINTING METER INTERVAL UNIT: "+meterIntervalUnit);
		timeIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeIntervalUnit");
		System.out.println("//////////////////PRINTING TIME INTERVAL UNIT: "+timeIntervalUnit);
		meterThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterThresholdUnit");
		System.out.println("//////////////////PRINTING METER THRESHOLD UNIT: "+meterThresholdUnit);
		timeThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeThresholdUnit");
		System.out.println("//////////////////PRINTING TIME THRESHOLD UNIT: "+timeThresholdUnit);
		
		if((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (!(meterIntervalToString.isEmpty()) ))){
	flash("error","IF YOU SELECT TIME INTERVAL TYPE YOU MUST PROVIDE VALUE FOR TIME INTERVAL!");
	return ok(addServiceNotificationSettingsForm.render());
		}
		
		if((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (!(timeIntervalToString.isEmpty()) ))){
			flash("error","IF YOU SELECT METER INTERVAL TYPE YOU MUST PROVIDE VALUE FOR METER INTERVAL!");
	return ok(addServiceNotificationSettingsForm.render());
		}

			if((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (!(timeIntervalToString.isEmpty()) && timeIntervalToString!=null))){
				intervalSize=timeInterval;
				intervalUnit=timeIntervalUnit;
			}
		
		if((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (!(meterIntervalToString.isEmpty()) && meterIntervalToString!=null))){
			intervalSize=meterInterval;
			intervalUnit=meterIntervalUnit;
		}
			 meterThresholdToString = dynamicServiceNSForm.bindFromRequest().data().get("meterThreshold");
				System.out.println("/////////////PRINTING METER THRESHOLD STRING IN METHOD addServiceNotificationSettings: "+meterThresholdToString);

			 if(meterThresholdToString!=null && !(meterThresholdToString.isEmpty())){
			meterThreshold=Integer.parseInt(meterThresholdToString);
			}
			System.out.println("PRINTING METER THRESHOLD IN METHOD addServiceNotificationSettings: "+meterThreshold);
			timeThresholdToString = dynamicServiceNSForm.bindFromRequest().data().get("timeThreshold");
			System.out.println("/////////////PRINTING TIME THRESHOLD STRING IN METHOD addServiceNotificationSettings: "+timeThresholdToString);

		if(timeThresholdToString!=null && !(timeThresholdToString.isEmpty())){
			timeThreshold=Integer.parseInt(timeThresholdToString);
			}
			System.out.println("PRINTING TIME THRESHOLD IN METHOD addServiceNotificationSettings: "+timeThreshold);
			
			if((!(meterThresholdToString.isEmpty())) && (!(timeThresholdToString.isEmpty()))){
				flash("error", "YOU CAN PROVIDE ONLY ONE VALUE, TIME THRESHOLD OR METER THRESHOLD, NOT BOTH!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if((meterThresholdToString.isEmpty() || meterThresholdToString==null) && (timeThresholdToString.isEmpty()||timeThresholdToString==null)){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(!(meterThresholdToString.isEmpty()) && meterThresholdToString!=null){
			thresholdSize=meterThreshold;
			thresholdUnit=meterThresholdUnit;
			}
			if(!(timeThresholdToString.isEmpty()) && timeThresholdToString!=null){
				thresholdSize=timeThreshold;
				thresholdUnit=timeThresholdUnit;
				}
			if(((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (meterThresholdToString.isEmpty() || meterThresholdToString==null)))){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (timeThresholdToString.isEmpty() || timeThresholdToString==null)))){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			ServiceNotificationSettings sns=ServiceNotificationSettings.saveToDB(intervalType, intervalSize, intervalUnit, thresholdSize,thresholdUnit);
			String serviceIDsAreaToString= addServiceNSForm.bindFromRequest().field("serviceIDsArea").value();
			if(serviceIDsAreaToString.isEmpty() || serviceIDsAreaToString==null){
				flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE SERVICE NOTIFICATION! ");
					return redirect("/addservicenotificationsettingsview");
				}
			String[] servIds = serviceIDsAreaToString.split(",");
			List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("PRINTING ARRAY OF SERVICE  ID STRINGS:"
								+ servStrId);
				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("SELECTED SERVICE : "+service.stype);
				sns.services.add(service);
				sns.save();
				service.notificationSettings=sns;
				service.hasNotification=true;
				service.save();
				System.out.println("NUMBER OF SERVICES SELECTED: "
						+ sns.services.size());
							}
			flash("success", "SERVICE NOTIFICATION SETTING SUCCESSFULLY ADDED");
			return ok(showServiceNotificationSettings.render(sns));
		} catch (Exception e) {
			flash("error", "ERROR  ADDING SERVICE NOTIFICATION SETTING ");
			Logger.error("ERROR ADDING SERVICE NOTIFICATION SETTING : " + e.getMessage(), e);
			return redirect("/addservicenotificationsettingsview");
		}
	}
	

	/**
	 * Finds ServiceNotificationSettings object using passed ID number as parameter 
	 * and displays it in view
	 * @param id- ServiceNotificationSettings object ID
	 * @return
	 */
	public Result showServiceNotificationSettings(long id) {
		if (ServiceNotificationSettings.find.byId(id) == null) {
			Logger.error("error", "ServiceNotificationSettings IS NULL");
			flash("error", "NO SUCH SERVICE NOTIFICATION SETTINGS RECORD IN DATABASE!!!");
			return redirect("/allservicenotificationsettings");
		}
		ServiceNotificationSettings sns = ServiceNotificationSettings.findById(id);
				return ok(showServiceNotificationSettings.render(sns));
	}

	
	/**
	 * Finds ServiceNotificationSettings object using passed ID number as parameter,
	 *  and then removes it from database
	 * @param id- ServiceNotificationSettings object ID
	 * @return
	 */
	public Result deleteServiceNotificationSettings(long id) {
		if (ServiceNotificationSettings.find.byId(id) == null) {
			Logger.error("ServiceNotificationSettings IS NULL");
			flash("error", "NO SUCH ServiceNotificationSettings OBJECT IN DATABASE!!!");
			return redirect("/allservicenotificationsettings");
		}
		ServiceNotificationSettings sns = ServiceNotificationSettings.find.byId(id);
		ServiceNotificationSettings.deleteServiceNotificationSettings(id);
		flash("success", " ServiceNotificationSettings OBJECT SUCCESSFULLY DELETED!");
			Logger.info("ServiceNotificationSettings DELETED: " + sns.id);
			return redirect("/allservicenotificationsettings");
			}

	
	/**
	 * Renders the view for editing ServiceNotificationSettings object.
	 * @param id- ServiceNotificationSettings object ID number
	 * @return
	 */
	public Result editServiceNotificationSettingsView(long id) {
		if (ServiceNotificationSettings.find.byId(id) == null) {
			flash("error", "NO SUCH ServiceNotificationSettings OBJECT IN DATABASE");
			return redirect("/allservicenotificationsettings");
		}
		ServiceNotificationSettings sns = ServiceNotificationSettings.find.byId(id);
		return ok(editServiceNotificationSettingsView.render(sns));
	}

	
	/**
	 * Finds the specific ServiceNotificationSettings object using passed ID number as parameter,
	 *  and then updates it with data from request (collected through editServiceNotificationSettings View form)
	 * @param id - ID number of ServiceNotificationSettings object
	 * @return Result
	 */
	public Result editServiceNotificationSettings(long id) {
		DynamicForm dynamicServiceNSForm = Form.form().bindFromRequest();
		Form<ServiceNotificationSettings> addServiceNSForm = Form.form(ServiceNotificationSettings.class).bindFromRequest();
		ServiceNotificationSettings sns = ServiceNotificationSettings.find.byId(id);
		List<Service> oldServicesList=sns.services;
		ArrayList<Service> oldServicesArrayList=new ArrayList<Service>(oldServicesList);
		for(Service old_srv: oldServicesArrayList){
			sns.services.remove(old_srv);
			sns.save();
			old_srv.hasNotification=false;
			old_srv.notificationSettings=null;
			old_srv.save();
		}
		String intervalType=null, 
				timeIntervalToString=null,
				meterIntervalToString=null, 
				timeThresholdToString,
				meterThresholdToString,
				meterIntervalUnit=null,
				timeIntervalUnit=null,
				meterThresholdUnit=null, 
				timeThresholdUnit=null,
				intervalUnit=null,
				thresholdUnit=null;
		int	intervalSize=0,
				timeInterval=0, 
				meterInterval=0,
				meterThreshold=0,
				timeThreshold=0,
				thresholdSize=0;
			try {
			intervalType=dynamicServiceNSForm.bindFromRequest().data().get("intervalType");
			System.out.println("PRINTING INTERVAL TYPE IN addServiceNotificationSettings METHOD:"+intervalType);
			if((intervalType.isEmpty()) || intervalType==null){
				flash("error", "YOU MUST SELECT INTERVAL TYPE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			timeIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("timeInterval");
			System.out.println("PRINTING TIME INTERVAL STRING IN addServiceNotificationSettings METHOD:"+timeIntervalToString);
			if(timeIntervalToString!=null && !(timeIntervalToString.isEmpty()) ){
			timeInterval=Integer.parseInt(timeIntervalToString);
			}
			meterIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("meterInterval");
			System.out.println("PRINTING TIME INTERVAL STRING IN addServiceNotificationSettings METHOD:"+timeIntervalToString);
			if(meterIntervalToString!=null && !(meterIntervalToString.isEmpty())){
			meterInterval=Integer.parseInt(meterIntervalToString);
			}
			if((timeIntervalToString==null || timeIntervalToString.isEmpty()) && (meterIntervalToString==null || meterIntervalToString.isEmpty())){
				flash("error", "YOU MUST PROVIDE EITHER METER INTERVAL OR TIME INTERVAL!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(!(timeIntervalToString.isEmpty()) && !(meterIntervalToString.isEmpty())){
				flash("error", "YOU CAN PROVIDE ONLY ONE VALUE, TIME INTERVAL OR METER INTERVAL, NOT BOTH!");
				return ok(addServiceNotificationSettingsForm.render());
			}
		meterIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterIntervalUnit");
		System.out.println("//////////////////PRINTING METER INTERVAL UNIT: "+meterIntervalUnit);
		timeIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeIntervalUnit");
		System.out.println("//////////////////PRINTING TIME INTERVAL UNIT: "+timeIntervalUnit);
		meterThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterThresholdUnit");
		System.out.println("//////////////////PRINTING METER THRESHOLD UNIT: "+meterThresholdUnit);
		timeThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeThresholdUnit");
		System.out.println("//////////////////PRINTING TIME THRESHOLD UNIT: "+timeThresholdUnit);
		
		if((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (!(meterIntervalToString.isEmpty()) ))){
	flash("error","IF YOU SELECT TIME INTERVAL TYPE YOU MUST PROVIDE VALUE FOR TIME INTERVAL!");
	return ok(addServiceNotificationSettingsForm.render());
		}
		
		if((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (!(timeIntervalToString.isEmpty()) ))){
			flash("error","IF YOU SELECT METER INTERVAL TYPE YOU MUST PROVIDE VALUE FOR METER INTERVAL!");
	return ok(addServiceNotificationSettingsForm.render());
		}

			if((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (!(timeIntervalToString.isEmpty()) && timeIntervalToString!=null))){
				intervalSize=timeInterval;
				intervalUnit=timeIntervalUnit;
			}
		
		if((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (!(meterIntervalToString.isEmpty()) && meterIntervalToString!=null))){
			intervalSize=meterInterval;
			intervalUnit=meterIntervalUnit;
		}
			 meterThresholdToString = dynamicServiceNSForm.bindFromRequest().data().get("meterThreshold");
				System.out.println("/////////////PRINTING METER THRESHOLD STRING IN METHOD addServiceNotificationSettings: "+meterThresholdToString);

			 if(meterThresholdToString!=null && !(meterThresholdToString.isEmpty())){
			meterThreshold=Integer.parseInt(meterThresholdToString);
			}
			System.out.println("PRINTING METER THRESHOLD IN METHOD addServiceNotificationSettings: "+meterThreshold);
			timeThresholdToString = dynamicServiceNSForm.bindFromRequest().data().get("timeThreshold");
			System.out.println("/////////////PRINTING TIME THRESHOLD STRING IN METHOD addServiceNotificationSettings: "+timeThresholdToString);

		if(timeThresholdToString!=null && !(timeThresholdToString.isEmpty())){
			timeThreshold=Integer.parseInt(timeThresholdToString);
			}
			System.out.println("PRINTING TIME THRESHOLD IN METHOD addServiceNotificationSettings: "+timeThreshold);
			
			if((!(meterThresholdToString.isEmpty())) && (!(timeThresholdToString.isEmpty()))){
				flash("error", "YOU CAN PROVIDE ONLY ONE VALUE, TIME THRESHOLD OR METER THRESHOLD, NOT BOTH!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if((meterThresholdToString.isEmpty() || meterThresholdToString==null) && (timeThresholdToString.isEmpty()||timeThresholdToString==null)){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(!(meterThresholdToString.isEmpty()) && meterThresholdToString!=null){
			thresholdSize=meterThreshold;
			thresholdUnit=meterThresholdUnit;
			}
			if(!(timeThresholdToString.isEmpty()) && timeThresholdToString!=null){
				thresholdSize=timeThreshold;
				thresholdUnit=timeThresholdUnit;
				}
			if(((intervalType.equalsIgnoreCase(METER_INTERVAL_TYPE) && (meterThresholdToString.isEmpty() || meterThresholdToString==null)))){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			if(((intervalType.equalsIgnoreCase(TIME_INTERVAL_TYPE) && (timeThresholdToString.isEmpty() || timeThresholdToString==null)))){
				flash("error", "YOU MUST PROVIDE THRESHOLD VALUE!");
				return ok(addServiceNotificationSettingsForm.render());
			}
			String serviceIDsAreaToString= addServiceNSForm.bindFromRequest().field("serviceIDsArea").value();
			if(serviceIDsAreaToString.isEmpty() || serviceIDsAreaToString==null){
				flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE SERVICE NOTIFICATION! ");
					return redirect("/addservicenotificationsettingsview");
				}
			sns.intervalType=intervalType;
			sns.intervalSize=intervalSize;
			sns.intervalUnit=intervalUnit;
			sns.thresholdSize=thresholdSize;
			sns.thresholdUnit=thresholdUnit;
			sns.save();
			String[] servIds = serviceIDsAreaToString.split(",");
			//List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("PRINTING ARRAY OF SERVICE  ID STRINGS:"
								+ servStrId);
				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("SELECTED SERVICE : "+service.stype);
			//	sns.services=new ArrayList<Service>();
				sns.services.add(service);
				sns.save();
				service.notificationSettings=sns;
				service.hasNotification=true;
				service.save();
				System.out.println("NUMBER OF SERVICES SELECTED: "
						+ sns.services.size());
							}
//			for(Service old_srv : oldServicesArrayList){
//				if(Service.hasStillNotification(old_srv.id, sns.id)==false){
//						old_srv.hasNotification=false;
//						old_srv.notificationSettings=null;
//						old_srv.save();
//						sns.services.remove(old_srv);
//						sns.save();
//									}
//			}
			flash("success", "SERVICE NOTIFICATION SETTING SUCCESSFULLY UPDATED");
			return ok(showServiceNotificationSettings.render(sns));
		} catch (Exception e) {
			flash("error", "ERROR  EDITING SERVICE NOTIFICATION SETTING ");
			Logger.error("ERROR EDITING SERVICE NOTIFICATION SETTING : " + e.getMessage(), e);
			return redirect("/addservicenotificationsettingsview");
		}
	}
	

	public Result listAllServiceNotificationSettings() {
		List<ServiceNotificationSettings> allServiceNotificationSettings =new ArrayList<ServiceNotificationSettings>();
		allServiceNotificationSettings=	ServiceNotificationSettings.getAll();
		return ok(listAllServiceNotificationSettings.render(allServiceNotificationSettings));
			}
}

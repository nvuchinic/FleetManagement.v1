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
		if(Vehicle.listOfVehicles().size()==0){
			flash("error", "CANNOT CREATE SERVICE NOTIFICATION SETTINGS, NO VEHICLES IN DATABASE!");
			return ok(listAllServiceNotificationSettings.render(ServiceNotificationSettings.getAll()));
		}
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
		List<VehicleServiceNotificationSettingsMileage> snsMileages=new ArrayList<VehicleServiceNotificationSettingsMileage>();
		String timeIntervalToString=null,
				meterIntervalToString=null, 
				timeThresholdToString,
				meterThresholdToString,
				meterIntervalUnit=null,
				timeIntervalUnit=null,
				meterThresholdUnit=null, 
				timeThresholdUnit=null,
				intervalUnit=null,
				thresholdUnit=null,
				serviceName=null;
		int	intervalSize=0,
				timeInterval=0, 
				meterInterval=0,
				meterThreshold=0,
				timeThreshold=0,
				thresholdSize=0;
			try {
				java.util.Date javaNowDate = new java.util.Date();
		        java.sql.Date sqlNowDate = new java.sql.Date(javaNowDate.getTime());
				List<Vehicle> vehicles = new ArrayList<Vehicle>();
				String vehicleIDsAreaToString= addServiceNSForm.bindFromRequest().field("vehicleIds").value();
				if(vehicleIDsAreaToString.isEmpty() || vehicleIDsAreaToString==null){
					flash("error", "YOU MUST SELECT AT LEAST ONE VEHICLE TO CREATE SERVICE NOTIFICATION! ");
					return ok(addServiceNotificationSettingsForm.render());				
					}
//				String[] vhclsIds = vehicleIDsAreaToString.split(",");
//				String vhclStrId = null;
//				for (int i = 0; i < vhclsIds.length; i++) {
//					vhclStrId = vhclsIds[i];
//					System.out
//							.println("PRINTING ARRAY OF VEHICLE  ID STRINGS:"
//									+ vhclStrId);
//					long vhclId = Long.parseLong(vhclStrId);
//					Vehicle v=Vehicle.findById(vhclId);
//					vehicles.add(v);
//					System.out.println("PRINTING VEHICLE SERVICE NOTIFICATION SETTINGS MILEAGE NUMBER BEFORE:"+VehicleServiceNotificationSettingsMileage.find.all().size());
//					VehicleServiceNotificationSettingsMileage snsMileage=VehicleServiceNotificationSettingsMileage.saveToDB(v.id,v.odometer);
//					System.out.println("PRINTING VEHICLE SERVICE NOTIFICATION SETTINGS MILEAGE NUMBER AFTER:"+VehicleServiceNotificationSettingsMileage.find.all().size());
//					snsMileage.date=sqlNowDate;
//					snsMileage.save();
//					snsMileages.add(snsMileage);
//				}
			serviceName=dynamicServiceNSForm.bindFromRequest().data().get("serviceName");
			System.out.println("PRINTING SERVICE NAME  IN addServiceNotificationSettings METHOD:"+serviceName);
			if(serviceName.isEmpty() || serviceName==null){
				flash("error", "YOU MUST SELECT  SERVICE TO CREATE SERVICE NOTIFICATION! ");
				return ok(addServiceNotificationSettingsForm.render());				
				}
			timeIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("timeInterval");
			System.out.println("PRINTING TIME INTERVAL STRING IN addServiceNotificationSettings METHOD:"+timeIntervalToString);
			if(timeIntervalToString!=null && !(timeIntervalToString.isEmpty()) ){
			timeInterval=Integer.parseInt(timeIntervalToString);
			}
			meterIntervalToString=dynamicServiceNSForm.bindFromRequest().data().get("meterInterval");
			System.out.println("PRINTING METER INTERVAL STRING IN addServiceNotificationSettings METHOD:"+meterIntervalToString);
			if(meterIntervalToString!=null && !(meterIntervalToString.isEmpty())){
			meterInterval=Integer.parseInt(meterIntervalToString);
			}
			if((timeIntervalToString==null || timeIntervalToString.isEmpty()) && (meterIntervalToString==null || meterIntervalToString.isEmpty())){
				flash("error", "YOU MUST PROVIDE AT LEAST ONE VALUE, METER INTERVAL OR TIME INTERVAL!");
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
			
			if(!(meterThresholdToString.isEmpty()) && meterThresholdToString!=null){
			thresholdSize=meterThreshold;
			thresholdUnit=meterThresholdUnit;
			}
			if(!(timeThresholdToString.isEmpty()) && timeThresholdToString!=null){
				thresholdSize=timeThreshold;
				thresholdUnit=timeThresholdUnit;
				}
			ServiceNotificationSettings sns=ServiceNotificationSettings.saveToDB(meterInterval,meterIntervalUnit,  timeInterval,  timeIntervalUnit,  meterThreshold, meterThresholdUnit, timeThreshold,  timeThresholdUnit);
			sns.vehicles=vehicles;
			//sns.snsMileages=snsMileages;
			sns.save();
			String[] vhclsIds = vehicleIDsAreaToString.split(",");
			String vhclStrId = null;
			for (int i = 0; i < vhclsIds.length; i++) {
				vhclStrId = vhclsIds[i];
				System.out
						.println("PRINTING ARRAY OF VEHICLE  ID STRINGS:"
								+ vhclStrId);
				long vhclId = Long.parseLong(vhclStrId);
				Vehicle v=Vehicle.findById(vhclId);
				vehicles.add(v);
				System.out.println("PRINTING VEHICLE SERVICE NOTIFICATION SETTINGS MILEAGE NUMBER BEFORE:"+VehicleServiceNotificationSettingsMileage.find.all().size());
				VehicleServiceNotificationSettingsMileage snsMileage=VehicleServiceNotificationSettingsMileage.saveToDB(v.id,v.odometer);
				System.out.println("PRINTING VEHICLE SERVICE NOTIFICATION SETTINGS MILEAGE NUMBER AFTER:"+VehicleServiceNotificationSettingsMileage.find.all().size());
				snsMileage.date=sqlNowDate;
				snsMileage.save();
				sns.snsMileages.add(snsMileage);
				sns.save();
				snsMileage.sns=sns;
				snsMileage.save();
				//snsMileages.add(snsMileage);
			}
			Service service=Service.findByType(serviceName);
				sns.service=service;
				sns.save();
				service.serviceNotifications.add(sns);
				service.hasNotification=true;
				service.save();
//				for(Vehicle v:vehicles){
//					sns.vehicles.add(v);
//					sns.save();
//					v.serviceNotificationsSettings.add(sns);
//					v.save();
//				}
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
		String vIDs="";
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
		//String vehiclesIDs="";
		DynamicForm dynamicServiceNSForm = Form.form().bindFromRequest();
		Form<ServiceNotificationSettings> addServiceNSForm = Form.form(ServiceNotificationSettings.class).bindFromRequest();
		ServiceNotificationSettings sns = ServiceNotificationSettings.find.byId(id);
		Service  oldService=sns.service;
		oldService.serviceNotifications.remove(sns);
		oldService.hasNotification=false;
		oldService.save();
		sns.service=null;
		sns.save();
		List<VehicleServiceNotificationSettingsMileage> snsMileages=new ArrayList<VehicleServiceNotificationSettingsMileage>();
		String timeIntervalToString=null,
				meterIntervalToString=null, 
				timeThresholdToString,
				meterThresholdToString,
				meterIntervalUnit=null,
				timeIntervalUnit=null,
				meterThresholdUnit=null, 
				timeThresholdUnit=null,
				intervalUnit=null,
				thresholdUnit=null,
				serviceName;
		int	intervalSize=0,
				timeInterval=0, 
				meterInterval=0,
				meterThreshold=0,
				timeThreshold=0,
				thresholdSize=0;
			try {
			List<Vehicle> vehicles = new ArrayList<Vehicle>();
			String vehicleIDsAreaToString= addServiceNSForm.bindFromRequest().field("vehicleIds").value();
			if(vehicleIDsAreaToString.isEmpty() || vehicleIDsAreaToString==null){
				flash("error", "YOU MUST SELECT AT LEAST ONE VEHICLE TO CREATE SERVICE NOTIFICATION! ");
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
				flash("error", "YOU MUST PROVIDE AT LEAST ONE VALUE, METER INTERVAL OR TIME INTERVAL!");
				return ok(editServiceNotificationSettingsView.render(sns));	
			}
		serviceName=dynamicServiceNSForm.bindFromRequest().data().get("serviceName");
		System.out.println("//////////////////PRINTING SERVICE NAME INTERVAL UNIT: "+serviceName);
		if(serviceName.isEmpty() || serviceName==null){
			flash("error", "YOU MUST SELECT A SERVICE TO CREATE SERVICE NOTIFICATION! ");
				return redirect("/addservicenotificationsettingsview");
			}
		meterIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterIntervalUnit");
		System.out.println("//////////////////PRINTING METER INTERVAL UNIT: "+meterIntervalUnit);
		timeIntervalUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeIntervalUnit");
		System.out.println("//////////////////PRINTING TIME INTERVAL UNIT: "+timeIntervalUnit);
		meterThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("meterThresholdUnit");
		System.out.println("//////////////////PRINTING METER THRESHOLD UNIT: "+meterThresholdUnit);
		timeThresholdUnit=dynamicServiceNSForm.bindFromRequest().data().get("timeThresholdUnit");
		System.out.println("//////////////////PRINTING TIME THRESHOLD UNIT: "+timeThresholdUnit);
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
			
			if((meterThresholdToString.isEmpty() || meterThresholdToString==null) && (timeThresholdToString.isEmpty()||timeThresholdToString==null)){
				meterThreshold=0;
				timeThreshold=0;
			}
			
			if(!(timeThresholdToString.isEmpty()) && timeThresholdToString!=null){
				thresholdSize=timeThreshold;
				thresholdUnit=timeThresholdUnit;
				}
			Service service=Service.findByType(serviceName);
			sns.meterIntervalSize=meterInterval;
			sns.meterIntervalUnit=meterIntervalUnit;
			sns.timeIntervalSize=timeInterval;
			sns.timeIntervalUnit=timeIntervalUnit;
			sns.meterThresholdSize=meterThreshold;
			sns.timeThresholdSize=timeThreshold;
			sns.meterThresholdUnit=meterThresholdUnit;
			sns.timeThresholdUnit=timeThresholdUnit;
			sns.save();
				sns.service=service;
				sns.save();
				service.serviceNotifications.add(sns);
				service.hasNotification=true;
				service.save();	
				String[] vhclsIds = vehicleIDsAreaToString.split(",");
				String vhclStrId = null;
				for (int i = 0; i < vhclsIds.length; i++) {
					vhclStrId = vhclsIds[i];
					System.out
							.println("PRINTING ARRAY OF VEHICLE  ID STRINGS:"
									+ vhclStrId);
					long vhclId = Long.parseLong(vhclStrId);
					Vehicle v=Vehicle.findById(vhclId);
					vehicles.add(v);
					VehicleServiceNotificationSettingsMileage snsMileage=VehicleServiceNotificationSettingsMileage.saveToDB(v.id,v.odometer);
					snsMileages.add(snsMileage);
				}
				sns.vehicles=vehicles;
				sns.snsMileages=snsMileages;
				sns.save();
			flash("success", "SERVICE NOTIFICATION SETTING SUCCESSFULLY UPDATED");
			return ok(showServiceNotificationSettings.render(sns));
		} catch (Exception e) {
			flash("error", "ERROR  EDITING SERVICE NOTIFICATION SETTING ");
			Logger.error("ERROR EDITING SERVICE NOTIFICATION SETTING : " + e.getMessage(), e);
			return redirect("/addservicenotificationsettingsview");
		}
	}
	
	
	public Result addVehiclesForServiceNotificationView(){
		return ok(addVehiclesForServiceNotification.render());
		}
	
	
		
	
	
	public Result addVehiclesForServiceNotification(){
		DynamicForm dynamicServiceNSForm = Form.form().bindFromRequest();
		Form<ServiceNotificationSettings> addServiceNSForm = Form.form(ServiceNotificationSettings.class).bindFromRequest();
		String vehicleIDsAreaToString= addServiceNSForm.bindFromRequest().field("vehicleIds").value();
		if(vehicleIDsAreaToString.isEmpty() || vehicleIDsAreaToString==null){
			flash("error", "YOU MUST SELECT AT LEAST ONE VEHICLE TO CREATE SERVICE NOTIFICATION! ");
			return ok(addVehiclesForServiceNotification.render());
			}
		List<Long> vehicleIDsList = new ArrayList<Long>();
		String[] vhclsIds = vehicleIDsAreaToString.split(",");
		String vhclStrId = null;
		for (int i = 0; i < vhclsIds.length; i++) {
			vhclStrId = vhclsIds[i];
			System.out
					.println("PRINTING ARRAY OF VEHICLE  ID STRINGS:"
							+ vhclStrId);
			long vhclId = Long.parseLong(vhclStrId);
			vehicleIDsList.add(vhclId);
		}
		//flash("success", "ADD ADDITIONAL INFO FOR SERVICE NOTIFICATION");
			return ok(addServiceNotificationSettingsForm.render());
		}

	
	public Result editVehiclesForServiceNotificationView(long id){
		ServiceNotificationSettings sns=ServiceNotificationSettings.findById(id);
	return ok(editVehiclesForServiceNotificationForm.render(sns));	
	}
	
	
	public Result editVehiclesForServiceNotification(long snsID){
		DynamicForm editvehiclesForServiceNotificationForm = Form.form().bindFromRequest();
		ServiceNotificationSettings sns=ServiceNotificationSettings.findById(snsID);
		String vehiclesIDs=	editvehiclesForServiceNotificationForm.bindFromRequest().field("vehicleIds").value();
	return ok(editServiceNotificationSettingsView.render(sns));	
	}
	
	
	public Result listAllServiceNotificationSettings() {
		List<ServiceNotificationSettings> allServiceNotificationSettings = new ArrayList<ServiceNotificationSettings>();
		allServiceNotificationSettings=	ServiceNotificationSettings.getAll();
		return ok(listAllServiceNotificationSettings.render(allServiceNotificationSettings));
			}
}

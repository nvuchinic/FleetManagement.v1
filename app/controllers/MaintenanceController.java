package controllers;

import java.util.ArrayList;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import models.*;

import com.avaje.ebean.Model.Finder;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class MaintenanceController extends Controller {

	static Form<Maintenance> maintenanceOrderForm = Form
			.form(Maintenance.class);

	public static Finder<Long, Maintenance> find = new Finder<Long, Maintenance>(
			Maintenance.class);

	/**
	 * Renders the 'add Maintenance' form view
	 * @return
	 */
	public Result addMaintenanceViewFromIssue(Long id) {
		Vehicle v = Vehicle.findById(id);
		System.out.println("THIS CAR HAS "+Issue.unresolvedIssuesNo(v)+" UNRESOLVED ISSUES");
		List<Service> allServices = new ArrayList<Service>();
		allServices = Service.findS.all();
			return ok(addMaintenanceFromIssueForm.render(v, allServices));
	}

	
	public Result addMaintenanceViewFromServiceNotification(Long vid, Long srvID, Long snID) {
		Vehicle v = Vehicle.findById(vid);
		Service	srv=Service.findById(srvID);	
		ServiceNotification sn=ServiceNotification.findById(snID);
			return ok(addMaintenanceFromServiceNotificationForm.render(v, srv, sn));
	}
	
	
	/**
	 * Renders the 'add Maintenance' form view
	 * @return
	 */
	public Result addMaintenanceView() {
		List<Service> allServices = new ArrayList<Service>();
		allServices = Service.findS.all();
		if(allServices.size()==0){
			flash("error", "CANNOT CREATE MAINTENANCE, NO SERVICES DEFINED!!!");
			return redirect("/");
		}
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.find.all();
		if(allVehicles.size()==0){
			flash("error", "CANNOT CREATE MAINTENANCE, NO AVAILABLE VEHICLES!!!");
			return redirect("/");
		}
		return ok(addMaintenanceForm.render(allVehicles, allServices));
	}
	/**
	 * Finds Maintenance object by it's ID number and shows it in view
	 * @param id - Maintenance object ID
	 * @return
	 */
	public Result showMaintenance(long id) {
		Maintenance mnt = Maintenance.findById(id);
		if (mnt == null) {
			Logger.error("error", "Maintenance null()");
			flash("error", "MAINTENANCE IS NULL!!!");
			return redirect("/");
		}
		// mServices=mnt.services;
		// for debbuging
		System.out.println("BROJ SERVISA ODRZAVANJA U METODI ShowMaintenance:"
				+ mnt.services.size());
		System.out
				.println("ISPISUJEM SERVISE ODRZAVANJA U  METODI SHOWMAINTENANCE:");
		for (Service s : mnt.services) {
			System.out.println(s.stype);
		}
		return ok(showMaintenance.render(mnt));
	}
	

	// @SuppressWarnings("unused")
	public Result chooseCar() {
		List<Vehicle> allVehicles = new ArrayList<Vehicle>();
		allVehicles = Vehicle.find.all();
		flash("addVehicleForMaintenance",
				"For adding Vehicle Maintenance choose vehicle");
		return ok(listAllVehicles.render(allVehicles));
	}

	
	/**
	 * Creates new Maintenance object using values from request(collected through form)
	 * @return
	 * @throws ParseException
	 */
	public Result addMaintenanceFromIssue(long id) {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		if (v == null) {
			flash("error", "VEHICLE IS NULL AT ADDING MAINTENANCE!");
			return redirect("/");
		}
		/*
		 * if (addTravelOrderForm.hasErrors() ||
		 * addTravelOrderForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Travel Order"); flash("error",
		 * "Error at Travel Order form!"); return redirect("/addTravelOrder"); }
		 */
		int odometer=0;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		String serviceType;
		// Service service;
		try {
			String odometerToString=dynamicMaintenanceForm.get("odometer");
			if(odometerToString.isEmpty() || odometerToString==null){
				flash("error","ERROR, YOU MUST PROVIDE ODOMETER VALUE!");
				return redirect("/addmaintenanceview");
			}
			odometer=Integer.parseInt(odometerToString);
			//odometer=addMaintenanceForm.bindFromRequest().get().odometer;
			System.out.println("PRINTING ODOMETER VALUE: "+odometer);
			stringDate = dynamicMaintenanceForm.get("dateM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
			System.out
					.println("UNESENI DATUM KOD KREIRANJA MAINTENANCE OBJEKTA: "
							+ mDate);
			Maintenance mn = Maintenance.saveToDB(v, mDate);
			mn.odometer=odometer;
			mn.save();
			Vehicle thisVehicle=mn.vehicle;
			thisVehicle.odometer=odometer;
			thisVehicle.save();
			String t = addMaintenanceForm.bindFromRequest().field("t").value();
			if(t.isEmpty() || t==null){
				flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE MAINTENANCE! ");
					return redirect("/addmaintenanceviewfromissue/"+id);
				}
			String[] servIds = t.split(",");
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
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				v.maintenances.add(mn);
				v.save();
				System.out.println("BROJ ODABRANIH USLUGA ODRZAVANJA: "
						+ mn.services.size());
							}
			String issues = addMaintenanceForm.bindFromRequest().field("t2").value();
			String[] issueIds = issues.split(",");
			List<Issue> issuesList= new ArrayList<Issue>();
			String issueStrId = null;
			for (int i = 0; i < issueIds.length; i++) {
				issueStrId = issueIds[i];
				System.out
						.println("ISPISUJEM NIZ ISSUE ID STRINGOVA U ADD_MAINTENANCE METODI:"
								+ issueStrId);
				long issueId = Long.parseLong(issueStrId);
				Issue is = Issue.findById(issueId);
				is.status="resolved";
				is.save();
				}
			flash("success","MAINTENANCE SUCCESSFULLY ADDED!");
				return redirect("/showmaintenance/"+mn.id);
		} catch (Exception e) {
			flash("error", "ERROR ADDING MAINTENANCE ");
			Logger.error("ERROR ADDING MAINTENANCE: " + e.getMessage(), e);
			return redirect("/allmaintenances");
		}
	}

	
	/**
	 * Creates new Maintenance object using values from request(collected through form)
	 * @return
	 * @throws ParseException
	 */
	public Result addMaintenanceFromServiceNotification(long vId, long srvId, long srvNotifID) {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		Vehicle notifiedVehicle = Vehicle.findById(vId);
		Service service=Service.findById(srvId);
		ServiceNotification resolvingSN=ServiceNotification.findById(srvNotifID);
		ServiceNotificationSettings wantedSns=ServiceNotificationSettings.findByServiceAndVehicle(vId, srvId);
		VehicleServiceNotificationSettingsMileage wantedVsnm=VehicleServiceNotificationSettingsMileage.findByServiceAndVehicle(vId, wantedSns);

		/*
		 * if (addTravelOrderForm.hasErrors() ||
		 * addTravelOrderForm.hasGlobalErrors()) {
		 * Logger.debug("Error at adding Travel Order"); flash("error",
		 * "Error at Travel Order form!"); return redirect("/addTravelOrder"); }
		 */
		int odometer=0;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		String serviceType;
		try {

			String odometerToString=dynamicMaintenanceForm.get("odometer");
			if(odometerToString.isEmpty() || odometerToString==null){
				flash("error","ERROR, YOU MUST PROVIDE ODOMETER VALUE!");
				return redirect("/addmaintenanceview");
			}
			odometer=Integer.parseInt(odometerToString);
			//odometer=addMaintenanceForm.bindFromRequest().get().odometer;
			System.out.println("PRINTING ODOMETER VALUE: "+odometer);
			stringDate = dynamicMaintenanceForm.get("dateM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
			System.out
					.println("UNESENI DATUM KOD KREIRANJA MAINTENANCE OBJEKTA: "
							+ mDate);
			Maintenance mn = Maintenance.saveToDB(notifiedVehicle, mDate);
			mn.odometer=odometer;
			mn.save();
			Vehicle thisVehicle=mn.vehicle;
			thisVehicle.odometer=odometer;
			thisVehicle.save();
//			String t = addMaintenanceForm.bindFromRequest().field("t").value();
//			if(t.isEmpty() || t==null){
//				flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE MAINTENANCE! ");
//					return redirect("/");
//				}
//			String[] servIds = t.split(",");
//			List<Service> mServices = new ArrayList<Service>();
//			String servStrId = null;
//			for (int i = 0; i < servIds.length; i++) {
//				servStrId = servIds[i];
//				System.out
//						.println("PRINTING ARRAY OF SERVICE  ID STRINGS:"
//								+ servStrId);
//				long servId = Long.parseLong(servStrId);
//				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				notifiedVehicle.maintenances.add(mn);
				notifiedVehicle.save();
				System.out.println("BROJ ODABRANIH USLUGA ODRZAVANJA: "
						+ mn.services.size());
							//}

			Service resolvingService=resolvingSN.serviceForSN;
			for(Service mnSrv: mn.services){
				if(mnSrv.id==resolvingService.id){
					ServiceNotification.deleteServiceNotification(resolvingSN.id);
					break;
				}
			}
			System.out.println("PRINTING NUMBER OF SERVICE NOTIFICATION AFTER REMOVING: "+ServiceNotification.getAll().size());
			wantedVsnm.mileage=odometer;
			wantedVsnm.date=mDate;
			wantedVsnm.sns=wantedSns;
			wantedVsnm.save();
			//			for(Service s:mn.services){
//				if(Service.existsNotification(s)==true){
//					List<ServiceNotificationSettings> thisServiceSns=ServiceNotificationSettings.findByService(s);
//					for(ServiceNotificationSettings sns:thisServiceSns){
//						for(Vehicle v:sns.vehicles){
//						if(v.id==notifiedVehicle.id){
//							ServiceNotificationSettings wantedSns=sns;
//							for(VehicleServiceNotificationSettingsMileage vsnMileage:sns.snsMileages){
//								if(vsnMileage.vid==notifiedVehicle.id){
//									VehicleServiceNotificationSettingsMileage wantedVsnMileage=vsnMileage;
//									wantedVsnMileage.mileage=odometer;
//									wantedVsnMileage.save();
//								}
//							}
//						}	
//						}
//					}
//				}
//			}
			flash("success","MAINTENANCE SUCCESSFULLY ADDED!");
				return redirect("/showmaintenance/"+mn.id);
		} catch (Exception e) {
			flash("error", "ERROR ADDING MAINTENANCE ");
			Logger.error("ERROR ADDING MAINTENANCE: " + e.getMessage(), e);
			return redirect("/allmaintenances");
		}
	}

	
	/**
	 * Creates new Maintenance object using values from request(collected through form)
	 * @return
	 * @throws ParseException
	 */
	public Result addMaintenance() {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		/*
		 * if (addMaintenanceForm.hasErrors() ||
		 * addMaintenanceForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR ADDING MAINTENANCE	"); flash("error",
		 * "ERROR ADDING MAINTENANCE!"); return redirect("/addmaintenanceview"); }
		 */
		SimpleDateFormat format=null, format2=null;
		int odometer=0;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		String serviceType;
		String vehicleName;
		String newService;
		 Service newServiceType=null;
		 String odometerToString=null;
		try {
			odometerToString=dynamicMaintenanceForm.get("odometer");
			if(odometerToString.isEmpty() || odometerToString==null){
				flash("error","ERROR, YOU MUST PROVIDE ODOMETER VALUE!");
				return redirect("/addmaintenanceview");
			}
			odometer=Integer.parseInt(odometerToString);
//			 newService = addMaintenanceForm.bindFromRequest()
//					.field("newService").value();
//			 System.out.println("PRINTING NEW SELECTED SERVICE:"+ newService);
//			 if(!(newService.isEmpty())){
//				 if(Service.alreadyExists(newService)==true){
//						flash("error", "SERVICE WITH THAT NAME ALREADY EXISTS! ");
//					 return redirect("/addmaintenanceview");
//				 }
//				 newServiceType=Service.createService(newService);
//			 }
			 String vehicleId=dynamicMaintenanceForm.get("vehicleId");
			 Vehicle v=Vehicle.findByVid(vehicleId);
			 stringDate = dynamicMaintenanceForm.get("dateM");
				System.out.println("PRINTING DATE :"+stringDate);
				if(stringDate.contains("-")){
					format = new SimpleDateFormat("yyyy-MM-dd");
				}
				else{
					format = new SimpleDateFormat("MM/dd/yyy");
				}
				System.out.println("PRINTING MAINTENANCE DATE: "+stringDate);
				utilDate = format.parse(stringDate);
				mDate = new java.sql.Date(utilDate.getTime());
			System.out
					.println("UNESENI DATUM KOD KREIRANJA MAINTENANCE OBJEKTA: "
							+ mDate);
			Maintenance mn = Maintenance.saveToDB(v, mDate);
			mn.odometer=odometer;
			mn.save();

			Vehicle thisVehicle=mn.vehicle;
			thisVehicle.odometer=odometer;
			thisVehicle.save();
			//mn.services.add(newServiceType);
			String t = addMaintenanceForm.bindFromRequest().field("t").value();
			if(t.isEmpty() || t==null){
			flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE MAINTENANCE! ");
				return redirect("/addmaintenanceview");
			}
			String[] servIds = t.split(",");
			List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("PRINTING SERVICE ID-S IN ADD_MAINTENANCE METHOD:"
								+ servStrId);
				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				v.maintenances.add(mn);
				v.save();
				System.out.println("BROJ ODABRANIH USLUGA ODRZAVANJA: "
						+ mn.services.size());
							}
						flash("success","MAINTENANCE SUCCESSFULLY ADDED!");
						return ok(showMaintenance.render(mn));
		} catch (Exception e) {
			flash("error", "ERROR ADDING MAINTENANCE ");
			Logger.error("ERROR ADDING MAINTENANCE: " + e.getMessage(), e);
			return redirect("/allmaintenances");
		}
	}
	
	
	/**
	 * Creates new Maintenance object using values from request(collected through form)
	 * @return
	 * @throws ParseException
	 */
	public Result addMaintenanceForServiceNotification() {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> addMaintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		/*
		 * if (addMaintenanceForm.hasErrors() ||
		 * addMaintenanceForm.hasGlobalErrors()) {
		 * Logger.debug("ERROR ADDING MAINTENANCE	"); flash("error",
		 * "ERROR ADDING MAINTENANCE!"); return redirect("/addmaintenanceview"); }
		 */
		int odometer=0;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		String serviceType;
		String vehicleName;
		String newService;
		 Service newServiceType=null;
		 String odometerToString=null;
		try {
			odometerToString=dynamicMaintenanceForm.get("odometer");
			if(odometerToString.isEmpty() || odometerToString==null){
				flash("error","ERROR, YOU MUST PROVIDE ODOMETER VALUE!");
				return redirect("/addmaintenanceview");
			}
			odometer=Integer.parseInt(odometerToString);
//			 newService = addMaintenanceForm.bindFromRequest()
//					.field("newService").value();
//			 System.out.println("PRINTING NEW SELECTED SERVICE:"+ newService);
//			 if(!(newService.isEmpty())){
//				 if(Service.alreadyExists(newService)==true){
//						flash("error", "SERVICE WITH THAT NAME ALREADY EXISTS! ");
//					 return redirect("/addmaintenanceview");
//				 }
//				 newServiceType=Service.createService(newService);
//			 }
			 String vehicleId=dynamicMaintenanceForm.get("vehicleId");
			 Vehicle v=Vehicle.findByVid(vehicleId);
			stringDate = dynamicMaintenanceForm.get("dateM");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
			System.out
					.println("UNESENI DATUM KOD KREIRANJA MAINTENANCE OBJEKTA: "
							+ mDate);
			Maintenance mn = Maintenance.saveToDB(v, mDate);
			mn.odometer=odometer;
			mn.save();

			Vehicle thisVehicle=mn.vehicle;
			thisVehicle.odometer=odometer;
			thisVehicle.save();
			//mn.services.add(newServiceType);
			String t = addMaintenanceForm.bindFromRequest().field("t").value();
			if(t.isEmpty() || t==null){
			flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE MAINTENANCE! ");
				return redirect("/addmaintenanceview");
			}
			String[] servIds = t.split(",");
			List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("PRINTING SERVICE ID-S IN ADD_MAINTENANCE METHOD:"
								+ servStrId);
				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				v.maintenances.add(mn);
				v.save();
				System.out.println("BROJ ODABRANIH USLUGA ODRZAVANJA: "		+ mn.services.size());
				}
			for(Service srv:mn.services){
				if(Service.existsNotification(srv)){
					
				}
			}
				flash("success","MAINTENANCE SUCCESSFULLY ADDED!");
				return ok(showMaintenance.render(mn));
		} catch (Exception e) {
			flash("error", "ERROR ADDING MAINTENANCE ");
			Logger.error("ERROR ADDING MAINTENANCE: " + e.getMessage(), e);
			return redirect("/allmaintenances");
		}
	}
	
	
	
	public Result addMoreServicesView(Long id) {
		Maintenance mn = Maintenance.findById(id);
		if (mn == null) {
			System.out.println("MAINTENANCE NULL AT ADDING MORE SERVICES");
			return redirect("/allmaintenances");
		}
		List<Service> allServices = new ArrayList<Service>();
		allServices = Service.findS.all();
		return ok(addMoreServicesForm.render(mn.vehicle, allServices));
	}

	/**
	 * Finds Vehicle Registration object using id and then deletes it from
	 * database
	 * 
	 * @param id
	 *            - Vehicle registration id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteMaintenance(long id) {
		try {
			Maintenance mn = Maintenance.findById(id);
			Logger.info("Maintenance deleted: \"" + mn.id);
			Maintenance.deleteMaintenance(id);
			return redirect("/allmaintenances");
		} catch (Exception e) {
			flash("deleteMaintenanceError", "Error at deleting maintenance!");
			Logger.error("Error at deleting maintenance: " + e.getMessage());
			return redirect("/");
		}
	}

	/**
	 * Renders the view for editing Vehicle registration object.
	 * 
	 * @param id
	 *            long
	 * @return Result
	 */
	public Result editMaintenanceView(long id) {
		Maintenance mn = Maintenance.findById(id);
		// Exception handling.
		if (mn == null) {
			flash("MaintenanceNull", "Maintenance record doesn't exist");
			return redirect("/");
		}
		// Form<TravelOrder> travelOrderForm =
		// Form.form(TravelOrder.class).fill(to);
		return ok(editMaintenanceView.render(mn));

	}

	/**
	 * Method receives an id, finds the specific Vehicle registration object and
	 * updates its information with data collected from editVRegistration form
	 * again.
	 * 
	 * @param id
	 *            of Vehicle Registration object
	 * @return Result
	 */
	public Result editMaintenance(long id) {
		DynamicForm dynamicMaintenanceForm = Form.form().bindFromRequest();
		Form<Maintenance> maintenanceForm = Form.form(Maintenance.class)
				.bindFromRequest();
		Maintenance mn = Maintenance.findById(id);
//		String serviceType;
//		Service service;
		java.util.Date utilDate = new java.util.Date();
		String stringDate;
		Date mDate;
		int odometer;
		for(Service s:mn.services){
			mn.services=null;
			mn.save();
			s.maintenances.remove(mn);
			s.save();
			
		}
		try {
//			if (maintenanceForm.hasErrors()
//					|| maintenanceForm.hasGlobalErrors()) {
//				Logger.info("Maintenance update error");
//				flash("error", "Error in maintenance update form");
//				return ok(editMaintenanceView.render(mn));
//			}
//			serviceType = maintenanceForm.bindFromRequest().get().serviceType;
//			if (serviceType == null) {
//				serviceType = mn.serviceType;
//			}
			SimpleDateFormat format=null, format2=null;
			String odometerToString=dynamicMaintenanceForm.get("odometer");
			if(odometerToString.isEmpty() || odometerToString==null){
				flash("error","ERROR, YOU MUST PROVIDE ODOMETER VALUE!");
				return redirect("/editmaintenanceview/"+id);
			}
			String t = maintenanceForm.bindFromRequest().field("t").value();
			System.out.println("PRINTING VALUE OF T STRING IN EDIT MAINTENANCE :"+t);
			if(t.isEmpty() || t==null){
			flash("error", "YOU MUST SELECT AT LEAST ONE SERVICE TO CREATE MAINTENANCE! ");
				return redirect("/editmaintenanceview/"+id);
			}
			String[] servIds = t.split(",");
			List<Service> mServices = new ArrayList<Service>();
			String servStrId = null;
			for (int i = 0; i < servIds.length; i++) {
				servStrId = servIds[i];
				System.out
						.println("PRINTING SERVICE ID-S IN ADD_MAINTENANCE METHOD:"
								+ servStrId);
				long servId = Long.parseLong(servStrId);
				Service service = Service.findById(servId);
				// service=Service.findByType(serviceType);
				// System.out.println("ODABRANI SERVIS ZA ODRZAVANJE: "+service.stype);
				mn.services.add(service);
				mn.save();
			//	service.maintenances.add(mn);
				service.isChosen = true;
				service.save();
				}
			odometer=Integer.parseInt(odometerToString);
			//odometer=maintenanceForm.bindFromRequest().get().odometer;
			stringDate = dynamicMaintenanceForm.get("dateM");
			System.out.println("PRINTING DATE :"+stringDate);
			if(stringDate.contains("-")){
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			else{
				format = new SimpleDateFormat("MM/dd/yyy");
			}
			System.out.println("PRINTING MAINTENANCE DATE: "+stringDate);
			utilDate = format.parse(stringDate);
			mDate = new java.sql.Date(utilDate.getTime());
//			stringDate = dynamicMaintenanceForm.get("dateM");
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			utilDate = format.parse(stringDate);
//			mDate = new java.sql.Date(utilDate.getTime());
//			service = Service.findByType(serviceType);
//			mn.services.add(service);
			mn.mDate = mDate;
			mn.odometer=odometer;
			mn.save();
			String vehicleId=dynamicMaintenanceForm.get("vehicleId");
			 Vehicle v=Vehicle.findByVid(vehicleId);
			 mn.vehicle=v;
			 mn.save();
			 v.maintenances.add(mn);
			 v.save();
//			Vehicle thisVehicle=mn.vehicle;
//			thisVehicle.odometer=odometer;
//			thisVehicle.save();
//			List<Service> mServices = new ArrayList<Service>();
//			for (Service s : mn.services) {
//				mServices.add(s);
//			}
//			System.out.println("Ispisujem servise odrzavanja");
//			for (Service s : mServices) {
//				System.out.println(s.stype);
//			}
			Logger.info(session("name") + " updated vehicle registration: "
					+ mn.id);
			flash("success",
					"MAINTENANCE SUCCESSFULLY UPDATED!");
			return ok(showMaintenance.render(mn));
		} catch (Exception e) {
			flash("error", "EROR EDITING MAINTENANCE");
			Logger.error("Error at updating maintenance: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	public Result listMaintenances() {
		List<Maintenance> allMaintenances = Maintenance.listOfMaintenances();
		if (allMaintenances != null) {
			return ok(listAllMaintenances.render(allMaintenances));
		} else {
			flash("listMaintenancesError",
					"No maintenances records in database!");
			return redirect("/");
		}
	}

}

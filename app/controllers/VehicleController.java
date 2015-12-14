package controllers;

import helpers.AdminFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import models.*;
import static play.mvc.Http.MultipartFormData;
import play.db.ebean.Model;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import play.api.Play;

import com.google.common.io.Files;

/**
 * Controller for Vehicle model
 * 
 * @author Emir Imamović
 *
 */
public class VehicleController extends Controller {

	static Form<Vehicle> vehicleForm = Form.form(Vehicle.class);

	/**
	 * Finder for Vehicle class
	 */

	public static Finder<Long, Vehicle> find = new Finder<Long, Vehicle>(
			Vehicle.class);

	/**
	 * Renders the view(form) for creating new Vehicle object
	 * @return
	 */
//	public Result addVehicleView() {
//		return ok(addVehicleForm.render());
//	}

	public Result addVehicleView2() {
		List<VehicleBrand> brands=new ArrayList<VehicleBrand>();
		List<VehicleModel> models =new ArrayList<>();
		String type="", brand="", model="", vid="";
		return ok(addVehicleForm3.render(brands, models,type, brand, vid, model));
	}
	
	/**
	 * Finds Vehicle object by it's ID number and displays it's info
	 * @param id - Vehicle id
	 * @return 
	 */
	public Result showVehicle(long id) {
				if (Vehicle.find.byId(id) == null) {
			Logger.error("error", "VEHICLE NULL");
			flash("error", "VEHICLE IS NULL!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
		Vehicle v = Vehicle.find.byId(id);
		return ok(showVehicle.render(v));
	}

	/**
	 *First finds Vehicle object by it's ID number passed as argument,
	 *then deletes it from database
	 *@param id - Vehicle object ID 
	 * @return 
	 */
	// @Security.Authenticated(AdminFilter.class)
	public Result deleteVehicle(long id) {
		try {
			Vehicle v = Vehicle.findById(id);
			Logger.info("Deleted vehicle: \"" + v.typev.name + "\"");
			Vehicle.deleteVehicle(id);
			flash("success", "VEHICLE SUCCESSFULLY DELETED!");
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}

	/**
	 *First finds  Vehicle object by it's ID number passed as argument,
	 *then renders the view for editing that vehicle
	* @param id -ID number of Vehicle object
	 * @return Result render VehicleView
	 */
	public Result editVehicleView(long id) {
				// Exception handling.
		if (Vehicle.findById(id) == null) {
			flash("error", "VEHICLE IS NULL");
			return redirect("/");
		}
		Vehicle v = Vehicle.findById(id);
		return ok(editVehicleView.render(v));

	}

//	public  Result picture(long id) {
//		final Vehicle v = Vehicle.findById(id);
//		if(v == null) {
//			return notFound();
//			}
//		return ok(v.picture);
//		}

	
	/**
	 * finds the Vehicle object by it's ID number, and then updates values of it's properties
	 * using data from request collected through form
	 * @param id
	 * @return
	 */
	public Result editVehicle(long id) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
//			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
//				Logger.info("Vehicle update error");
//				flash("error", "Error in vehicle form");
//				return ok(editVehicleView.render(v));
//			}
//			play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
//		    play.mvc.Http.MultipartFormData.FilePart picture = body.getFile("picture");
//		    if (picture == null) {
//		  flash("error", "Missing picture file");
//		        return badRequest();
//		    } 
//		    String fileName = picture.getFilename();
//	        String contentType = picture.getContentType();
//	        java.io.File pictureFile = picture.getFile();
//	        try {
//	        	v.picture = Files.toByteArray(pictureFile);
//	        	} catch (IOException e) {
//	        	return internalServerError("Error reading file upload");
//	        	}

			String frontTireSize = dynamicForm.bindFromRequest().get(
					"frontTireSize1");
			String rearTireSize = dynamicForm.bindFromRequest().get(
					"rearTireSize1");
			String frontTirePressure = dynamicForm.bindFromRequest().get(
					"frontTirePressure1");
			String rearTirePressure = dynamicForm.bindFromRequest().get(
					"rearTirePressure1");

			Tires tires = Tires.find.byId(Tires.createTires(frontTireSize,
					rearTireSize, frontTirePressure, rearTirePressure));

			String engineSerialNumber = dynamicForm.bindFromRequest().get(
					"engineSerialNumber1");
			String chassisNumber = dynamicForm.bindFromRequest().get(
					"chassisNumber1");
			String cylinderVolume = dynamicForm.bindFromRequest().get(
					"cylinderVolume1");
			String fuelConsumption = dynamicForm.bindFromRequest().get(
					"fuelConsumption1");
			String loadingLimit = dynamicForm.bindFromRequest().get(
					"loadingLimit1");
			String fuelTank = dynamicForm.bindFromRequest().get("fuelTank1");
			String enginePower = dynamicForm.bindFromRequest().get(
					"enginePower1");
			String torque = dynamicForm.bindFromRequest().get("torque1");
			String netWeight = dynamicForm.bindFromRequest().get("netWeight1");
			String loadedWeight = dynamicForm.bindFromRequest().get(
					"loadedWeight1");
			String trunkCapacity = dynamicForm.bindFromRequest().get(
					"trunkCapacity1");
			String numOfCylinders = dynamicForm.bindFromRequest().get(
					"numOfCylinders1");
			if (v.technicalInfo == null) {
				TechnicalInfo techInfo = TechnicalInfo.find.byId(TechnicalInfo
						.createTechnicalInfo(engineSerialNumber, chassisNumber,
								cylinderVolume, fuelConsumption, loadingLimit,
								fuelTank, enginePower, torque, numOfCylinders,
								netWeight, loadedWeight, trunkCapacity, tires));
				v.technicalInfo = techInfo;
				v.save();
			} else {
				v.technicalInfo.engineSerialNumber = engineSerialNumber;
				v.technicalInfo.chassisNumber = chassisNumber;
				v.technicalInfo.cylinderVolume = cylinderVolume;
				v.technicalInfo.fuelConsumption = fuelConsumption;
				v.technicalInfo.loadingLimit = loadingLimit;
				v.technicalInfo.fuelTank = fuelTank;
				v.technicalInfo.enginePower = enginePower;
				v.technicalInfo.torque = torque;
				v.technicalInfo.numOfCylinders = numOfCylinders;
				v.technicalInfo.netWeight = netWeight;
				v.technicalInfo.loadedWeight = loadedWeight;
				v.technicalInfo.trunkCapacity = trunkCapacity;
				v.technicalInfo.tires = tires;
				v.technicalInfo.save();
				v.save();
			}
			v.vid = vehicleForm.bindFromRequest().data().get("vid");
			String name = vehicleForm.bindFromRequest().get().name;
			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");
			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();
			Fleet f;
			if (fleetName != null && Fleet.findByName(fleetName) == null
					&& !fleetName.isEmpty()) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			} else if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				v.fleet = null;
				v.isAsigned = false;
				v.save();
				f = v.fleet;
			}
			Owner o;
			if (Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			} else {
				o = Owner.findByName(ownerName);
				o.save();
			}
			String registrationNo = dynamicForm.bindFromRequest().get(
					"registrationNo1");
			String certificateNo = dynamicForm.bindFromRequest().get(
					"certificateNo1");
			String city = dynamicForm.bindFromRequest().get("city1");
			String trailerLoadingLimit = dynamicForm.bindFromRequest().get(
					"trailerLoadingLimit1");
			java.util.Date utilDate1 = new java.util.Date();
			java.util.Date utilDate2 = new java.util.Date();
			String stringDate1;
			String stringDate2;
			Date regDate = null;
			Date expirDate = null;
			stringDate1 = dynamicForm.bindFromRequest()
					.get("registrationDate1");
			if (!stringDate1.isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				utilDate1 = format.parse(stringDate1);
				regDate = new java.sql.Date(utilDate1.getTime());
			}
			stringDate2 = dynamicForm.bindFromRequest()
					.get("registrationDate1");
			if (!stringDate2.isEmpty()) {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				utilDate2 = format2.parse(stringDate2);
				expirDate = new java.sql.Date(utilDate2.getTime());
			}
			if (v.vRegistration == null) {
				VehicleRegistration vr = VehicleRegistration.saveToDB(
						registrationNo, certificateNo, city, regDate,
						expirDate, trailerLoadingLimit, v);
				v.vRegistration = vr;
				v.save();
			} else {
				v.vRegistration.certificateNo = certificateNo;
				v.vRegistration.regNo = registrationNo;
				v.vRegistration.city = city;
				v.vRegistration.registrationDate = regDate;
				v.vRegistration.expirationDate = expirDate;
				v.vRegistration.registrationHolder = o;
				v.vRegistration.trailerLoadingLimit = trailerLoadingLimit;
				v.isRegistered = true;
				v.vRegistration.save();
				v.save();
			}
			if (v.vRegistration != null) {
				v.isRegistered = true;
				v.save();
			} else {
				v.vRegistration = null;
				v.isRegistered = false;
				v.save();
			}
			String warrantyDetails = dynamicForm.bindFromRequest().get(
					"warrantyDetails1");
			String warrantyKmLimit = dynamicForm.bindFromRequest().get(
					"warrantyKmLimit1");
			String vehicleCardNumber = dynamicForm.bindFromRequest().get(
					"vehicleCardNumber1");
			String typeOfCard = dynamicForm.bindFromRequest()
					.get("typeOfCard1");
			java.util.Date utilDatew1 = new java.util.Date();
			java.util.Date utilDatew2 = new java.util.Date();
			java.util.Date utilDatew3 = new java.util.Date();
			String stringDatew1;
			String stringDatew2;
			String stringDatew3;
			Date commencementWarrantyDate = null;
			Date expiryWarrantyDate = null;
			Date cardIssueDate = null;
			stringDatew1 = dynamicForm.bindFromRequest().get(
					"commencementWarrantyDate1");
			if (!stringDatew1.isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew1 = format.parse(stringDatew1);
				commencementWarrantyDate = new java.sql.Date(
						utilDatew1.getTime());
			}
			stringDatew2 = dynamicForm.bindFromRequest().get(
					"expiryWarrantyDate1");
			if (!stringDatew2.isEmpty()) {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew2 = format2.parse(stringDatew2);
				expiryWarrantyDate = new java.sql.Date(utilDatew2.getTime());
			}
			stringDatew3 = dynamicForm.bindFromRequest().get("cardIssueDate1");
			if (!stringDatew3.isEmpty()) {
				SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew3 = format3.parse(stringDatew3);
				cardIssueDate = new java.sql.Date(utilDatew3.getTime());
			}

			if (v.vehicleWarranty == null) {
				VehicleWarranty vw = VehicleWarranty.find.byId(VehicleWarranty
						.createVehicleWarranty(v, warrantyDetails,
								commencementWarrantyDate, expiryWarrantyDate,
								warrantyKmLimit, vehicleCardNumber, typeOfCard,
								cardIssueDate));
				v.vehicleWarranty = vw;
				v.save();
			} else {
				v.vehicleWarranty.warrantyDetails = warrantyDetails;
				v.vehicleWarranty.commencementWarrantyDate = commencementWarrantyDate;
				v.vehicleWarranty.expiryWarrantyDate = expiryWarrantyDate;
				v.vehicleWarranty.warrantyKmLimit = warrantyKmLimit;
				v.vehicleWarranty.vehicleCardNumber = vehicleCardNumber;
				v.vehicleWarranty.typeOfCard = typeOfCard;
				v.vehicleWarranty.cardIssueDate = cardIssueDate;
				v.vehicleWarranty.save();
				v.save();
			}
			if (v.vRegistration == null)
				v.isRegistered = false;
			v.name = name;
			v.owner = o;
			v.fleet = f;
			if (v.fleet != null) {
				f.numOfVehicles = f.vehicles.size();
				v.isAsigned = true;
				f.save();
			}
			v.save();
			List<Description> descriptions = new ArrayList<Description>();
			if (v.typev != null) {
				List<Description> desc = Vehicle.findByType(v.typev).get(0).description;
				for (int j = 0; j < desc.size(); j++) {

					String value = vehicleForm.bindFromRequest()
							.field(desc.get(j).propertyName).value();
					if (value != null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										value));
						descriptions.add(d);
					}
					if (value == null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										desc.get(j).propertyValue));
						descriptions.add(d);
					}
				}
			}
			String count = dynamicForm.bindFromRequest().get("counter");

			if (count == "0") {
				String pn = dynamicForm.bindFromRequest().get("propertyName0");
				String pv = dynamicForm.bindFromRequest().get("propertyValue0");
				if (!pn.isEmpty() && !pv.isEmpty()) {
					Description d = Description.findById(Description
							.createDescription(pn, pv));
					descriptions.add(d);
				}
			} else {
				int num = Integer.parseInt(count);
				for (int i = 0; i <= num; i++) {
					String pn = dynamicForm.bindFromRequest().get(
							"propertyName" + i);
					String pv = dynamicForm.bindFromRequest().get(
							"propertyValue" + i);
					if (!pn.isEmpty() && !pv.isEmpty()) {
						Description d = Description.findById(Description
								.createDescription(pn, pv));

						descriptions.add(d);
					}
				}
			}
			v.description = descriptions;
			v.save();
			if(v.vehicleBrand==null){
				flash("error", "ERROR, YOU MUST PROVIDE BRAND");
				return redirect("/editVehicle/"+v.id);
			}
			if(v.vehicleModel==null){
				flash("error", "ERROR, YOU MUST PROVIDE MODEL");
				return redirect("/editVehicle/"+v.id);
			}
			Logger.info(session("name") + " updated vehicle: " + v.id);
			flash("success", "VEHICLE SUCCESSFULLY UPDATED!");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}


	public Result getMoreVehicleInfoView(String vid, String typeName, String brand, String model){
		if(vid.isEmpty() || vid==null){
			List<VehicleBrand> brands=new ArrayList<VehicleBrand>();
			List<VehicleModel> models =new ArrayList<>();
			return ok(addVehicleForm3.render(brands, models,typeName, brand, vid, model));
		}
		if(typeName.isEmpty() || typeName==null){
			List<VehicleBrand> brands=new ArrayList<VehicleBrand>();
			List<VehicleModel> models =new ArrayList<>();
			return ok(addVehicleForm3.render(brands, models,typeName, brand, vid, model));
		}
		return ok(addVehicleMoreForm2.render(vid, typeName, brand, model));
	}
	
	
	public Result createVehicle(String vid, String typeName, String brand, String model){
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Type t=Type.findByName(typeName);
		Vehicle v = Vehicle.saveToDB(vid, t);
		VehicleBrand vb=VehicleBrand.findByName(brand);
		VehicleModel vm=VehicleModel.findByName(model);
		v.vehicleBrand=vb;
		v.vehicleModel=vm;
		v.save();
		//Owner o=v.owner;
		try {
//			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
//				Logger.info("Vehicle update error");
//				flash("error", "Error in vehicle form");
//				return ok(editVehicleView.render(v));
//			}
//			play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
//		    play.mvc.Http.MultipartFormData.FilePart picture = body.getFile("picture");
//		    if (picture == null) {
//		  flash("error", "Missing picture file");
//		        return badRequest();
//		    } 
//		    String fileName = picture.getFilename();
//	        String contentType = picture.getContentType();
//	        java.io.File pictureFile = picture.getFile();
//	        try {
//	        	v.picture = Files.toByteArray(pictureFile);
//	        	} catch (IOException e) {
//	        	return internalServerError("Error reading file upload");
//	        	}
			String frontTireSize = dynamicForm.bindFromRequest().get(
					"frontTireSize1");
			String rearTireSize = dynamicForm.bindFromRequest().get(
					"rearTireSize1");
			String frontTirePressure = dynamicForm.bindFromRequest().get(
					"frontTirePressure1");
			String rearTirePressure = dynamicForm.bindFromRequest().get(
					"rearTirePressure1");

			Tires tires = Tires.find.byId(Tires.createTires(frontTireSize,
					rearTireSize, frontTirePressure, rearTirePressure));

			String engineSerialNumber = dynamicForm.bindFromRequest().get(
					"engineSerialNumber1");
			String chassisNumber = dynamicForm.bindFromRequest().get(
					"chassisNumber1");
			String cylinderVolume = dynamicForm.bindFromRequest().get(
					"cylinderVolume1");
			String fuelConsumption = dynamicForm.bindFromRequest().get(
					"fuelConsumption1");
			String loadingLimit = dynamicForm.bindFromRequest().get(
					"loadingLimit1");
			String fuelTank = dynamicForm.bindFromRequest().get("fuelTank1");
			String enginePower = dynamicForm.bindFromRequest().get(
					"enginePower1");
			String torque = dynamicForm.bindFromRequest().get("torque1");
			String netWeight = dynamicForm.bindFromRequest().get("netWeight1");
			String loadedWeight = dynamicForm.bindFromRequest().get(
					"loadedWeight1");
			String trunkCapacity = dynamicForm.bindFromRequest().get(
					"trunkCapacity1");
			String numOfCylinders = dynamicForm.bindFromRequest().get(
					"numOfCylinders1");
			if (v.technicalInfo == null) {
				TechnicalInfo techInfo = TechnicalInfo.find.byId(TechnicalInfo
						.createTechnicalInfo(engineSerialNumber, chassisNumber,
								cylinderVolume, fuelConsumption, loadingLimit,
								fuelTank, enginePower, torque, numOfCylinders,
								netWeight, loadedWeight, trunkCapacity, tires));
				v.technicalInfo = techInfo;
				v.save();
			} else {
				v.technicalInfo.engineSerialNumber = engineSerialNumber;
				v.technicalInfo.chassisNumber = chassisNumber;
				v.technicalInfo.cylinderVolume = cylinderVolume;
				v.technicalInfo.fuelConsumption = fuelConsumption;
				v.technicalInfo.loadingLimit = loadingLimit;
				v.technicalInfo.fuelTank = fuelTank;
				v.technicalInfo.enginePower = enginePower;
				v.technicalInfo.torque = torque;
				v.technicalInfo.numOfCylinders = numOfCylinders;
				v.technicalInfo.netWeight = netWeight;
				v.technicalInfo.loadedWeight = loadedWeight;
				v.technicalInfo.trunkCapacity = trunkCapacity;
				v.technicalInfo.tires = tires;
				v.technicalInfo.save();
				v.save();
			}
//			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
//					.value();
//			Fleet f;
//			if (fleetName != null && Fleet.findByName(fleetName) == null
//					&& !fleetName.isEmpty()) {
//				Logger.info("Vehicle update error");
//				flash("error", "Fleet does not exists!");
//				return ok(editVehicleView.render(v));
//			} else if (fleetName != null && Fleet.findByName(fleetName) != null) {
//				f = Fleet.findByName(fleetName);
//				f.save();
//			} else {
//				v.fleet = null;
//				v.isAsigned = false;
//				v.save();
//				f = v.fleet;
//			}
			String registrationNo = dynamicForm.bindFromRequest().get(
					"registrationNo1");
			String certificateNo = dynamicForm.bindFromRequest().get(
					"certificateNo1");
			String city = dynamicForm.bindFromRequest().get("city1");
			String trailerLoadingLimit = dynamicForm.bindFromRequest().get(
					"trailerLoadingLimit1");
			java.util.Date utilDate1 = new java.util.Date();
			java.util.Date utilDate2 = new java.util.Date();
			String registrationDateToString=null;
			String expiryDateToString=null;
			Date registrationDate = null;
			Date expiryDate = null;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
			registrationDateToString = dynamicForm.bindFromRequest()
					.get("registrationDate");
			System.out.println("////////////////////PRINTING REGISTRATION DATE AS STRING "+registrationDateToString);
			if (!(registrationDateToString.isEmpty())) {
				utilDate1 = format.parse(registrationDateToString);
				registrationDate = new java.sql.Date(utilDate1.getTime());
			}
			expiryDateToString = dynamicForm.bindFromRequest()
					.get("expirationDate");
			System.out.println("//////////////PRINTING EXPIRATION REGISRTATION DATE AS STRING "+expiryDateToString);
			if (!(expiryDateToString.isEmpty())) {
				utilDate2 = format2.parse(expiryDateToString);
				expiryDate = new java.sql.Date(utilDate2.getTime());
			}
		//	System.out.println("////////////////PRINTING REGISTRATION EXPIRY DATE:"+expirDate.toString());
//			Owner o=null;
//			if(o==null){
//				VehicleRegistration vr = VehicleRegistration.saveToDB(
//						registrationNo, certificateNo, city, regDate,
//						expirDate, trailerLoadingLimit, v);
//				v.vRegistration = vr;
//				v.save();
//			}
			if (v.vRegistration == null) {
				VehicleRegistration vr = VehicleRegistration.saveToDB(
						registrationNo, certificateNo, city, registrationDate,
						expiryDate, trailerLoadingLimit, v);
				v.vRegistration = vr;
				v.save();
			} 
			String warrantyDetails = dynamicForm.bindFromRequest().get(
					"warrantyDetails1");
			String warrantyKmLimit = dynamicForm.bindFromRequest().get(
					"warrantyKmLimit1");
			String vehicleCardNumber = dynamicForm.bindFromRequest().get(
					"vehicleCardNumber1");
			String typeOfCard = dynamicForm.bindFromRequest()
					.get("typeOfCard1");
			java.util.Date utilDatew1 = new java.util.Date();
			java.util.Date utilDatew2 = new java.util.Date();
			java.util.Date utilDatew3 = new java.util.Date();
			String stringDatew1;
			String stringDatew2;
			String stringDatew3;
			Date commencementWarrantyDate = null;
			Date expiryWarrantyDate = null;
			Date cardIssueDate = null;
			stringDatew1 = dynamicForm.bindFromRequest().get(
					"commencementWarrantyDate1");
			if (!stringDatew1.isEmpty()) {
				SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew1 = format4.parse(stringDatew1);
				commencementWarrantyDate = new java.sql.Date(
						utilDatew1.getTime());
			}
			stringDatew2 = dynamicForm.bindFromRequest().get(
					"expiryWarrantyDate1");
			if (!stringDatew2.isEmpty()) {
				SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew2 = format3.parse(stringDatew2);
				expiryWarrantyDate = new java.sql.Date(utilDatew2.getTime());
			}
			stringDatew3 = dynamicForm.bindFromRequest().get("cardIssueDate1");
			if (!stringDatew3.isEmpty()) {
				SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew3 = format3.parse(stringDatew3);
				cardIssueDate = new java.sql.Date(utilDatew3.getTime());
			}
			if (v.vehicleWarranty == null) {
				VehicleWarranty vw = VehicleWarranty.find.byId(VehicleWarranty
						.createVehicleWarranty(v, warrantyDetails,
								commencementWarrantyDate, expiryWarrantyDate,
								warrantyKmLimit, vehicleCardNumber, typeOfCard,
								cardIssueDate));
				v.vehicleWarranty = vw;
				v.save();
			} else {
				v.vehicleWarranty.warrantyDetails = warrantyDetails;
				v.vehicleWarranty.commencementWarrantyDate = commencementWarrantyDate;
				v.vehicleWarranty.expiryWarrantyDate = expiryWarrantyDate;
				v.vehicleWarranty.warrantyKmLimit = warrantyKmLimit;
				v.vehicleWarranty.vehicleCardNumber = vehicleCardNumber;
				v.vehicleWarranty.typeOfCard = typeOfCard;
				v.vehicleWarranty.cardIssueDate = cardIssueDate;
				v.vehicleWarranty.save();
				v.save();
			}
			if (v.vRegistration == null)
				v.isRegistered = false;
		//	v.name = name;
			//v.owner = o;
//			v.fleet = f;
//			if (v.fleet != null) {
//				f.numOfVehicles = f.vehicles.size();
//				v.isAsigned = true;
//				f.save();
//			}
			v.save();
			List<Description> descriptions = new ArrayList<Description>();
			if (v.typev != null) {
				List<Description> desc = Vehicle.findByType(v.typev).get(0).description;
				for (int j = 0; j < desc.size(); j++) {

					String value = vehicleForm.bindFromRequest()
							.field(desc.get(j).propertyName).value();
					if (value != null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										value));
						descriptions.add(d);
					}
					if (value == null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										desc.get(j).propertyValue));
						descriptions.add(d);
					}
				}
			}
			String count = dynamicForm.bindFromRequest().get("counter");
			if (count == "0") {
				String pn = dynamicForm.bindFromRequest().get("propertyName0");
				String pv = dynamicForm.bindFromRequest().get("propertyValue0");
				if (!pn.isEmpty() && !pv.isEmpty()) {
					Description d = Description.findById(Description
							.createDescription(pn, pv));
					descriptions.add(d);
				}
			} else {
				int num = Integer.parseInt(count);
				for (int i = 0; i <= num; i++) {
					String pn = dynamicForm.bindFromRequest().get(
							"propertyName" + i);
					String pv = dynamicForm.bindFromRequest().get(
							"propertyValue" + i);
					if (!pn.isEmpty() && !pv.isEmpty()) {
						Description d = Description.findById(Description
								.createDescription(pn, pv));

						descriptions.add(d);
					}
				}
			}
			v.description = descriptions;
			v.save();
//			if(v.vehicleBrand==null){
//				flash("error", "ERROR, YOU MUST PROVIDE BRAND");
//				return redirect("/editVehicle/"+v.id);
//			}
//			if(v.vehicleModel==null){
//				flash("error", "ERROR, YOU MUST PROVIDE MODEL");
//				return redirect("/editVehicle/"+v.id);
//			}
			//Logger.info(session("name") + " updated vehicle: " + v.id);
			flash("success", "VEHICLE SUCCESSFULLY CREATED!");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "ERROR ADDING VEHICLE");
			Logger.error("ERROR ADDING VEHICLE: " + e.getMessage(), e);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}
	
	public Result addVehicleMore(long id) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		Owner o=v.owner;
		try {
//			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
//				Logger.info("Vehicle update error");
//				flash("error", "Error in vehicle form");
//				return ok(editVehicleView.render(v));
//			}
//			play.mvc.Http.MultipartFormData body = request().body().asMultipartFormData();
//		    play.mvc.Http.MultipartFormData.FilePart picture = body.getFile("picture");
//		    if (picture == null) {
//		  flash("error", "Missing picture file");
//		        return badRequest();
//		    } 
//		    String fileName = picture.getFilename();
//	        String contentType = picture.getContentType();
//	        java.io.File pictureFile = picture.getFile();
//	        try {
//	        	v.picture = Files.toByteArray(pictureFile);
//	        	} catch (IOException e) {
//	        	return internalServerError("Error reading file upload");
//	        	}
			String frontTireSize = dynamicForm.bindFromRequest().get(
					"frontTireSize1");
			String rearTireSize = dynamicForm.bindFromRequest().get(
					"rearTireSize1");
			String frontTirePressure = dynamicForm.bindFromRequest().get(
					"frontTirePressure1");
			String rearTirePressure = dynamicForm.bindFromRequest().get(
					"rearTirePressure1");

			Tires tires = Tires.find.byId(Tires.createTires(frontTireSize,
					rearTireSize, frontTirePressure, rearTirePressure));

			String engineSerialNumber = dynamicForm.bindFromRequest().get(
					"engineSerialNumber1");
			String chassisNumber = dynamicForm.bindFromRequest().get(
					"chassisNumber1");
			String cylinderVolume = dynamicForm.bindFromRequest().get(
					"cylinderVolume1");
			String fuelConsumption = dynamicForm.bindFromRequest().get(
					"fuelConsumption1");
			String loadingLimit = dynamicForm.bindFromRequest().get(
					"loadingLimit1");
			String fuelTank = dynamicForm.bindFromRequest().get("fuelTank1");
			String enginePower = dynamicForm.bindFromRequest().get(
					"enginePower1");
			String torque = dynamicForm.bindFromRequest().get("torque1");
			String netWeight = dynamicForm.bindFromRequest().get("netWeight1");
			String loadedWeight = dynamicForm.bindFromRequest().get(
					"loadedWeight1");
			String trunkCapacity = dynamicForm.bindFromRequest().get(
					"trunkCapacity1");
			String numOfCylinders = dynamicForm.bindFromRequest().get(
					"numOfCylinders1");
			if (v.technicalInfo == null) {
				TechnicalInfo techInfo = TechnicalInfo.find.byId(TechnicalInfo
						.createTechnicalInfo(engineSerialNumber, chassisNumber,
								cylinderVolume, fuelConsumption, loadingLimit,
								fuelTank, enginePower, torque, numOfCylinders,
								netWeight, loadedWeight, trunkCapacity, tires));
				v.technicalInfo = techInfo;
				v.save();
			} else {
				v.technicalInfo.engineSerialNumber = engineSerialNumber;
				v.technicalInfo.chassisNumber = chassisNumber;
				v.technicalInfo.cylinderVolume = cylinderVolume;
				v.technicalInfo.fuelConsumption = fuelConsumption;
				v.technicalInfo.loadingLimit = loadingLimit;
				v.technicalInfo.fuelTank = fuelTank;
				v.technicalInfo.enginePower = enginePower;
				v.technicalInfo.torque = torque;
				v.technicalInfo.numOfCylinders = numOfCylinders;
				v.technicalInfo.netWeight = netWeight;
				v.technicalInfo.loadedWeight = loadedWeight;
				v.technicalInfo.trunkCapacity = trunkCapacity;
				v.technicalInfo.tires = tires;
				v.technicalInfo.save();
				v.save();
			}

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();
			Fleet f;
			if (fleetName != null && Fleet.findByName(fleetName) == null
					&& !fleetName.isEmpty()) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			} else if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				v.fleet = null;
				v.isAsigned = false;
				v.save();
				f = v.fleet;
			}
			String registrationNo = dynamicForm.bindFromRequest().get(
					"registrationNo1");
			String certificateNo = dynamicForm.bindFromRequest().get(
					"certificateNo1");
			String city = dynamicForm.bindFromRequest().get("city1");
			String trailerLoadingLimit = dynamicForm.bindFromRequest().get(
					"trailerLoadingLimit1");
			java.util.Date utilDate1 = new java.util.Date();
			java.util.Date utilDate2 = new java.util.Date();
			String stringDate1;
			String stringDate2;
			Date regDate = null;
			Date expirDate = null;
			stringDate1 = dynamicForm.bindFromRequest()
					.get("registrationDate1");
			if (!stringDate1.isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				utilDate1 = format.parse(stringDate1);
				regDate = new java.sql.Date(utilDate1.getTime());
			}
			stringDate2 = dynamicForm.bindFromRequest()
					.get("registrationDate1");
			if (!stringDate2.isEmpty()) {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				utilDate2 = format2.parse(stringDate2);
				expirDate = new java.sql.Date(utilDate2.getTime());
			}
			if (v.vRegistration == null) {
				VehicleRegistration vr = VehicleRegistration.saveToDB(
						registrationNo, certificateNo,  city, regDate,
						expirDate, trailerLoadingLimit, v);
				v.vRegistration = vr;
				v.save();
			} else {
				v.vRegistration.certificateNo = certificateNo;
				v.vRegistration.regNo = registrationNo;
				v.vRegistration.city = city;
				v.vRegistration.registrationDate = regDate;
				v.vRegistration.expirationDate = expirDate;
				if(v.owner!=null){
				v.vRegistration.registrationHolder = v.owner;
				}
				v.vRegistration.trailerLoadingLimit = trailerLoadingLimit;
				v.isRegistered = true;
				v.vRegistration.save();
				v.save();
			}
			if (v.vRegistration != null) {
				v.isRegistered = true;
				v.save();
			} else {
				v.vRegistration = null;
				v.isRegistered = false;
				v.save();
			}
			String warrantyDetails = dynamicForm.bindFromRequest().get(
					"warrantyDetails1");
			String warrantyKmLimit = dynamicForm.bindFromRequest().get(
					"warrantyKmLimit1");
			String vehicleCardNumber = dynamicForm.bindFromRequest().get(
					"vehicleCardNumber1");
			String typeOfCard = dynamicForm.bindFromRequest()
					.get("typeOfCard1");
			java.util.Date utilDatew1 = new java.util.Date();
			java.util.Date utilDatew2 = new java.util.Date();
			java.util.Date utilDatew3 = new java.util.Date();
			String stringDatew1;
			String stringDatew2;
			String stringDatew3;
			Date commencementWarrantyDate = null;
			Date expiryWarrantyDate = null;
			Date cardIssueDate = null;
			stringDatew1 = dynamicForm.bindFromRequest().get(
					"commencementWarrantyDate1");
			if (!stringDatew1.isEmpty()) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew1 = format.parse(stringDatew1);
				commencementWarrantyDate = new java.sql.Date(
						utilDatew1.getTime());
			}
			stringDatew2 = dynamicForm.bindFromRequest().get(
					"expiryWarrantyDate1");
			if (!stringDatew2.isEmpty()) {
				SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew2 = format2.parse(stringDatew2);
				expiryWarrantyDate = new java.sql.Date(utilDatew2.getTime());
			}
			stringDatew3 = dynamicForm.bindFromRequest().get("cardIssueDate1");
			if (!stringDatew3.isEmpty()) {
				SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
				utilDatew3 = format3.parse(stringDatew3);
				cardIssueDate = new java.sql.Date(utilDatew3.getTime());
			}
			if (v.vehicleWarranty == null) {
				VehicleWarranty vw = VehicleWarranty.find.byId(VehicleWarranty
						.createVehicleWarranty(v, warrantyDetails,
								commencementWarrantyDate, expiryWarrantyDate,
								warrantyKmLimit, vehicleCardNumber, typeOfCard,
								cardIssueDate));
				v.vehicleWarranty = vw;
				v.save();
			} else {
				v.vehicleWarranty.warrantyDetails = warrantyDetails;
				v.vehicleWarranty.commencementWarrantyDate = commencementWarrantyDate;
				v.vehicleWarranty.expiryWarrantyDate = expiryWarrantyDate;
				v.vehicleWarranty.warrantyKmLimit = warrantyKmLimit;
				v.vehicleWarranty.vehicleCardNumber = vehicleCardNumber;
				v.vehicleWarranty.typeOfCard = typeOfCard;
				v.vehicleWarranty.cardIssueDate = cardIssueDate;
				v.vehicleWarranty.save();
				v.save();
			}
			if (v.vRegistration == null)
				v.isRegistered = false;
		//	v.name = name;
			//v.owner = o;
			v.fleet = f;
			if (v.fleet != null) {
				f.numOfVehicles = f.vehicles.size();
				v.isAsigned = true;
				f.save();
			}
			v.save();
			List<Description> descriptions = new ArrayList<Description>();
			if (v.typev != null) {
				List<Description> desc = Vehicle.findByType(v.typev).get(0).description;
				for (int j = 0; j < desc.size(); j++) {

					String value = vehicleForm.bindFromRequest()
							.field(desc.get(j).propertyName).value();
					if (value != null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										value));
						descriptions.add(d);
					}
					if (value == null) {
						Description d = Description.findById(Description
								.createDescription(desc.get(j).propertyName,
										desc.get(j).propertyValue));
						descriptions.add(d);
					}
				}
			}
			String count = dynamicForm.bindFromRequest().get("counter");
			if (count == "0") {
				String pn = dynamicForm.bindFromRequest().get("propertyName0");
				String pv = dynamicForm.bindFromRequest().get("propertyValue0");
				if (!pn.isEmpty() && !pv.isEmpty()) {
					Description d = Description.findById(Description
							.createDescription(pn, pv));
					descriptions.add(d);
				}
			} else {
				int num = Integer.parseInt(count);
				for (int i = 0; i <= num; i++) {
					String pn = dynamicForm.bindFromRequest().get(
							"propertyName" + i);
					String pv = dynamicForm.bindFromRequest().get(
							"propertyValue" + i);
					if (!pn.isEmpty() && !pv.isEmpty()) {
						Description d = Description.findById(Description
								.createDescription(pn, pv));

						descriptions.add(d);
					}
				}
			}
			v.description = descriptions;
			v.save();
			if(v.vehicleBrand==null){
				flash("error", "ERROR, YOU MUST PROVIDE BRAND");
				return redirect("/editVehicle/"+v.id);
			}
			if(v.vehicleModel==null){
				flash("error", "ERROR, YOU MUST PROVIDE MODEL");
				return redirect("/editVehicle/"+v.id);
			}
			Logger.info(session("name") + " updated vehicle: " + v.id);
		//	flash("success", "VEHICLE SUCCESSFULLY CREATED!");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "Error at editing vehicle");
			Logger.error("Error at updateVehicle: " + e.getMessage(), e);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}
	
	
	public Result addToFleetView(long id) {
		Vehicle v = Vehicle.findById(id);
		return ok(addToFleetView.render(v));

	}

	
	public Result removeFromFleet(long id) {
		Vehicle v = Vehicle.findById(id);
		v.fleet = null;
		v.isAsigned = false;
		v.save();
		flash("success", "Vehicle successfully removed from fleet");

		return redirect("/allVehicles");
	}

	
	public Result addToFleet(long id) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

			String fleetName = vehicleForm.bindFromRequest().field("fleetName")
					.value();
			String t = vehicleForm.bindFromRequest().field("t").value();
			Fleet f;
			if (Fleet.findByName(fleetName) == null) {
				Logger.info("Vehicle update error");
				flash("error", "Fleet does not exists!");
				return ok(editVehicleView.render(v));
			}
			if (fleetName != null && Fleet.findByName(fleetName) != null) {
				f = Fleet.findByName(fleetName);
				f.save();
			} else {
				f = new Fleet();
				f.name = "";
				f.save();
			}
			v.fleet = f;
			f.numOfVehicles = f.vehicles.size();
			if (v.fleet != null)
				v.isAsigned = true;
			f.save();
			v.save();

			Logger.info(session("name") + " added vehicle: " + v.id
					+ " to fleet");
			flash("success", v.vid + " successfully added to fleet!");
			List<Vehicle> allVehicles = new ArrayList<Vehicle>();
			allVehicles = Vehicle.find.all();
			return ok(listAllVehicles.render(allVehicles));
		} catch (Exception e) {
			flash("error", "Error at adding vehicle to fleet");
			Logger.error("Error at addingVehicleToFleet: " + e.getMessage(), e);
			return ok(showVehicle.render(v));
		}
	}

	/**
	 * Creates the new Vehicle object using values from request
	 * (collected through form for adding vehicles)
	 * @return
	 */
	public Result addVehicle() {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		// if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
		// Logger.debug("Error at adding vehicle");
		// flash("error", "Error at vehicle form!");
		// return redirect("/addVehicle");
		// }

		try {
			boolean isLinkable = vehicleForm.bindFromRequest().get().isLinkable;
			String vid = vehicleForm.bindFromRequest().get().vid;
			String name = vehicleForm.bindFromRequest().get().name;
			String ownerName = vehicleForm.bindFromRequest().data()
					.get("ownerName");
			String ownerEmail = vehicleForm.bindFromRequest().data()
					.get("ownerEmail");

			if (vid.isEmpty()) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");
			}
			if (Vehicle.findByVid(vid) != null) {
				flash("error", "VEHICLE WITH THAT ID ALREADY EXISTS");
				return redirect("/addVehicle");
			}
			Type t;
			String newTypeTemp = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newTypeTemp.isEmpty()) {
					flash("error", "Empty type name");
					return redirect("/addVehicle");
				}
				String newType = newTypeTemp.toLowerCase();
				newType = Character.toUpperCase(newType.charAt(0))
						+ newType.substring(1);
				if (Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
					t = new Type(newType);
					t.save();
				}
			}
			t.save();
			Owner o;
			if (Owner.findByName(ownerName) == null) {
				o = new Owner(ownerName, ownerEmail);
				o.save();
			}
			o = Owner.findByName(ownerName);
			if (vid.equals(null)) {
				flash("error", "Empty vehicle ID!");
				return redirect("/addVehicle");
			}
			Vehicle v = Vehicle
					.findById(Vehicle.createVehicle(vid, name, o, t));
			v.isLinkable = isLinkable;
			v.description = Vehicle.findByType(t).get(0).description;
			v.isAsigned = false;
			t.vehicles.add(v);
			t.save();
			v.save();
			Logger.info(" VEHICLE CREATED: "+v.name);
			flash("success", "VEHICLE SUCCESSFULLY CREATED. ADD MORE INFO IF YOU LIKE");
			return ok(addVehicleMoreForm.render(v));
		} catch (Exception e) {
			flash("error", "ERROR ADDING VEHICLE");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}
	

	/**
	 * Creates the new Vehicle object using values from request
	 * (collected through form for adding vehicles)
	 * @return
	 */
	public Result addVehicle2(String typeName, String vid, String brandName, String modelName) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		// if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
		// Logger.debug("Error at adding vehicle");
		// flash("error", "Error at vehicle form!");
		// return redirect("/addVehicle");
		// }
		//String modelName=vehicleForm.bindFromRequest().field("modelName").value();
		System.out.println("PRINTING TYPENAME: "+typeName);
		System.out.println("PRINTING BRANDNAME: "+brandName);
		System.out.println("PRINTING MODELNAME: "+modelName);
		try {
		//	boolean isLinkable = vehicleForm.bindFromRequest().get().isLinkable;
			//String vid = vehicleForm.bindFromRequest().get().vid;
			if (vid.isEmpty() || vid==null) {
				List<VehicleBrand> typeBrands=VehicleBrand.findByTypeName(typeName);
				List<VehicleModel> brandModels=VehicleModel.findByBrandName(brandName);
				vid="";
				flash("error", "ERROR, YOU MUST ENTER VEHICLE ID!");
				return ok(addVehicleForm3.render(typeBrands,brandModels,typeName, brandName, vid,modelName ));
			}
			String name = vehicleForm.bindFromRequest().get().name;
					//	String ownerEmail = vehicleForm.bindFromRequest().data()
				//	.get("ownerEmail");
			
			if (Vehicle.findByVid(vid) != null) {
				flash("error", "VEHICLE WITH THAT ID ALREADY EXISTS");
				return redirect("/addVehicle2");
			}
			Type t=null;	
				if (typeName==null || typeName.isEmpty()) {
					flash("error", "ERROR, NO TYPE SELECTED");
					return redirect("/addVehicle2");
				}
				if (Type.findByName(typeName) != null) {
					t = Type.findByName(typeName);
					t.save();
							}
				Owner o=null;
				String ownerName = vehicleForm.bindFromRequest().data()
												.get("ownerName");
				System.out.println("PRINTING OWNER NAME: "+ownerName);
				
					o=Owner.saveToDB(ownerName);
				
			//			if (Owner.findByName(ownerName) == null) {
//				o = new Owner(ownerName, ownerEmail);
//				o.save();
//			}
//			o = Owner.findByName(ownerName);
//			
			//Vehicle v = Vehicle.saveToDB(vid, name,  t, o);
			Vehicle v = Vehicle.saveToDB(vid,t);
		//	v.isLinkable = isLinkable;
			v.description = Vehicle.findByType(t).get(0).description;
			v.isAsigned = false;
			t.vehicles.add(v);
			t.save();
			v.save();
			v.typev = t;
			v.save();
			VehicleBrand vBrand;
			vBrand = VehicleBrand.findByName(brandName);
			vBrand.save();
			v.vehicleBrand = vBrand;
			v.save();
			VehicleModel vm=null;
			vm = VehicleModel.findByName(modelName);
			vm.save();
			v.vehicleModel = vm;
			v.save();
			Logger.info(" VEHICLE CREATED: "+v.name);
			flash("success", "VEHICLE SUCCESSFULLY CREATED");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "ERROR ADDING VEHICLE");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}
	
	
	/**
	 * Creates the new Vehicle object using values from request
	 * (collected through form for adding vehicles)
	 * @return
	 *//*
	public Result addVehicle2(String typeName, String brandName) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		// if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
		// Logger.debug("Error at adding vehicle");
		// flash("error", "Error at vehicle form!");
		// return redirect("/addVehicle");
		// }
		String modelName=vehicleForm.bindFromRequest().field("modelName").value();
		System.out.println("PRINTING TYPENAME: "+typeName);
		System.out.println("PRINTING BRANDNAME: "+brandName);
		System.out.println("PRINTING MODELNAME: "+modelName);
		try {
		//	boolean isLinkable = vehicleForm.bindFromRequest().get().isLinkable;
			String vid = vehicleForm.bindFromRequest().get().vid;
			if (vid.isEmpty() || vid==null) {
				List<VehicleBrand> typeBrands=VehicleBrand.findByTypeName(typeName);
				List<VehicleModel> brandModels=VehicleModel.findByBrandName(brandName);
				flash("error", "ERROR, YOU MUST ENTER VEHICLE ID!");
				return ok(addVehicleForm3.render(typeBrands,brandModels,typeName, brandName ));
			}
			String name = vehicleForm.bindFromRequest().get().name;
					//	String ownerEmail = vehicleForm.bindFromRequest().data()
				//	.get("ownerEmail");
			if (vid.isEmpty()) {
				flash("error", "ERROR, YOU MUST PROVIDE VID!");
				return redirect("/addVehicle2");
			}
			if (Vehicle.findByVid(vid) != null) {
				flash("error", "VEHICLE WITH THAT ID ALREADY EXISTS");
				return redirect("/addVehicle2");
			}
			Type t=null;	
				if (typeName==null || typeName.isEmpty()) {
					flash("error", "ERROR, NO TYPE SELECTED");
					return redirect("/addVehicle2");
				}
				if (Type.findByName(typeName) != null) {
					t = Type.findByName(typeName);
					t.save();
							}
				Owner o=null;
				String ownerName = vehicleForm.bindFromRequest().data()
												.get("ownerName");
				System.out.println("PRINTING OWNER NAME: "+ownerName);
				
					o=Owner.saveToDB(ownerName);
				
			//			if (Owner.findByName(ownerName) == null) {
//				o = new Owner(ownerName, ownerEmail);
//				o.save();
//			}
//			o = Owner.findByName(ownerName);
//			
			Vehicle v = Vehicle.saveToDB(vid, name,  t, o);
		//	v.isLinkable = isLinkable;
			v.description = Vehicle.findByType(t).get(0).description;
			v.isAsigned = false;
			t.vehicles.add(v);
			t.save();
			v.save();
			v.typev = t;
			v.save();
			VehicleBrand vBrand;
			vBrand = VehicleBrand.findByName(brandName);
			vBrand.save();
			v.vehicleBrand = vBrand;
			v.save();
			VehicleModel vm=null;
			vm = VehicleModel.findByName(modelName);
			vm.save();
			v.vehicleModel = vm;
			v.save();
			Logger.info(" VEHICLE CREATED: "+v.name);
			flash("success", "VEHICLE SUCCESSFULLY CREATED");
			return ok(showVehicle.render(v));
		} catch (Exception e) {
			flash("error", "ERROR ADDING VEHICLE");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}*/
	
	
	public Result listVehicles() {
		if (Vehicle.listOfVehicles() == null) {
			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		}
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}

	
	public Result getType(long id) {
		Vehicle v = Vehicle.findById(id);
		Type t;
		String type = vehicleForm.bindFromRequest().field("typeName").value();
		t = Type.findByName(type);
		t.save();
		v.typev = t;
		v.save();
		return ok(editVehicleView.render(v));
	}

	
	public Result getType2() {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		String typeName = vehicleForm.bindFromRequest().field("typeName").value();
		System.out.println("PRINTING TYPE NAME IN getType METHOD:"+typeName);
		List<VehicleModel> allModels=new ArrayList<>();
		allModels=VehicleModel.find.all();
		List<VehicleBrand> allBrands=VehicleBrand.find.all();
		List<VehicleBrand> typeBrands=new ArrayList<>();
		for(VehicleBrand vb:allBrands){
			if(vb.typev.name.equalsIgnoreCase(typeName)){
				typeBrands.add(vb);
			}
		}
		String brandName="", modelName="", vid="";
		return ok(addVehicleForm3.render(allBrands, allModels, typeName, brandName, vid, modelName));
	}
	
	
	public Result getVidAndType() {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		String typeName = vehicleForm.bindFromRequest().field("typeName").value();
		String vid=null;
		vid= vehicleForm.bindFromRequest().field("vid").value();
		if(vid.isEmpty() || vid==null){
			List<VehicleBrand> brands=new ArrayList<VehicleBrand>();
			List<VehicleModel> models =new ArrayList<>();
			typeName=""; String brand=""; String model=""; vid="";
		flash("error", "YOU MUST PROVIDE VID NUMBER FIRST");
		return ok(addVehicleForm3.render(brands, models,typeName, brand, vid, model));
		}
		System.out.println("PRINTING TYPE NAME IN getType METHOD:"+typeName);
		System.out.println("PRINTING VID IN getType METHOD:"+vid);
		List<VehicleModel> allModels=new ArrayList<>();
		allModels=VehicleModel.find.all();
		List<VehicleBrand> allBrands=VehicleBrand.find.all();
		List<VehicleBrand> typeBrands=new ArrayList<>();
		for(VehicleBrand vb:allBrands){
			if(vb.typev.name.equalsIgnoreCase(typeName)){
				typeBrands.add(vb);
			}
		}
		String brandName="", modelName="";
		return ok(addVehicleForm3.render(allBrands, allModels, typeName, brandName,vid, modelName));
	}
	
	public Result getBrand(long id) {
		Vehicle v = Vehicle.findById(id);
		VehicleBrand b;
		String brand = vehicleForm.bindFromRequest().field("carBrand").value();
		b = VehicleBrand.findByName(brand);
		b.save();
		v.vehicleBrand = b;
		v.save();
		return ok(addVehicleMoreForm.render(v));
	}

	
	public Result getBrand2(String typeName, String vid) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		String brandName = vehicleForm.bindFromRequest().field("brandName").value();
		System.out.println("PRINTING BRANDNAME IN getBrand2 METHOD: "+brandName);
		List<VehicleModel> allModels=new ArrayList<>();
		allModels=VehicleModel.find.all();
		List<VehicleBrand> typeBrands=VehicleBrand.findByTypeName(typeName);
		List<VehicleModel> brandModels=VehicleModel.findByBrandName(brandName);
//		for(VehicleModel vm:allModels){
//			if(vm.vehicleBrand.name.equalsIgnoreCase(brandName)){
//				brandModels.add(vm);
//			}
//		}
		String modelName="";
		return ok(addVehicleForm3.render(typeBrands, brandModels, typeName, brandName, vid, modelName));
	}
	
	public Result getModel2(String typeName, String vid, String brandName) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		String modelName = vehicleForm.bindFromRequest().field("modelName").value();
		System.out.println("PRINTING BRANDNAME IN getBrand2 METHOD: "+brandName);
		List<VehicleModel> allModels=new ArrayList<>();
		allModels=VehicleModel.find.all();
		List<VehicleBrand> typeBrands=VehicleBrand.findByTypeName(typeName);
		List<VehicleModel> brandModels=VehicleModel.findByBrandName(brandName);
//		for(VehicleModel vm:allModels){
//			if(vm.vehicleBrand.name.equalsIgnoreCase(brandName)){
//				brandModels.add(vm);
//			}
//		}
		
		return ok(addVehicleForm3.render(typeBrands, brandModels, typeName, brandName, vid, modelName));
	}
	
	public Result getModel(long id) {
		Vehicle v = Vehicle.findById(id);
		VehicleModel m;
		String model = vehicleForm.bindFromRequest().field("model").value();
		m = VehicleModel.findByName(model);
		m.save();
		v.vehicleModel = m;
		v.save();
		return ok(addVehicleMoreForm.render(v));
	}

	
	public Result getNewType(long id) {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		Type t;
		String newTypeTemp = dynamicForm.bindFromRequest().get("newType");

		String newType = newTypeTemp.toLowerCase();
		newType = Character.toUpperCase(newType.charAt(0))
				+ newType.substring(1);
		if (Type.findByName(newType) != null) {
			t = Type.findByName(newType);
			t.save();
		} else {
			t = new Type(newType);
			t.save();
		}
		t.save();
		v.typev = t;
		v.save();
		return ok(editVehicleView.render(v));
	}

	
	public Result getNewBrand(long id) {
		Vehicle v = Vehicle.findById(id);
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		VehicleBrand vBrand;
		String newBrandName = dynamicForm.bindFromRequest().get("newBrand");
		String newBrand = newBrandName.toLowerCase();
		newBrand = Character.toUpperCase(newBrand.charAt(0))
				+ newBrand.substring(1);
		if (VehicleBrand.findByName(newBrand) != null) {
			flash("error", "ERROR, THAT BRAND ALREADY EXISTS!");
			return ok(addVehicleMoreForm.render(v));
		} else {
			vBrand = new VehicleBrand(newBrand);
			vBrand.save();
		}
		vBrand.typev = v.typev;
		vBrand.save();
		v.vehicleBrand = vBrand;
		v.save();
		return ok(addVehicleMoreForm.render(v));
	}

	
	public Result getNewModel(long id) {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		VehicleModel vModel;
		String newModelTemp = dynamicForm.bindFromRequest().get("newModel");
		String newModel = newModelTemp.toLowerCase();
		newModel = Character.toUpperCase(newModel.charAt(0))
				+ newModel.substring(1);
		if (VehicleModel.findByName(newModel) != null) {
			vModel = VehicleModel.findByName(newModel);
			vModel .save();
		} else {
			vModel  = new VehicleModel(newModel, v.vehicleBrand);
			vModel .save();
		}
		vModel.vehicleBrand = v.vehicleBrand;
		vModel.save();
		v.vehicleModel = vModel;
		v.save();
		return ok(addVehicleMoreForm.render(v));
	}

	
	public Result deleteVehicles() {
		//Map<String, String[]> map = request().body().asFormUrlEncoded();
	   // String[] checkedVal = map.get("vehicleID"); // get selected topics
//	    if(checkedVal==null){
//	    	System.out.println("LISTA STRING ID-OVA JE PRAZNA ////////////////////");
//	    }
	  //	    for ( String v : checkedVal) {
//	        Logger.info("VEHICLE ID " + v);
//	    }
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return redirect("/");
			}
			String t = dynamicForm.bindFromRequest().data().get("t");
			System.out.println("/////////////////////////////PRINTING STRING CONTAINING VEHICLE ID'S: "+t);
			String[] vids = t.split(",");
			String vi = null;
			for (int i = 0; i < vids.length; i++) {
				vi = vids[i];
				if (vi != null && !vi.isEmpty()) {
					Vehicle.deleteVehicle(Long.parseLong(vi));
				}
			}
			flash("success", "Checked vehicles successfully deleted!");
			return redirect("/");

		} catch (Exception e) {
			flash("error",
					"Error at deleting vehicles. Some vehicles may not exist!");
			Logger.error("Error at deleting vehicles: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	
	public Result changeVehiclesFleet() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return redirect("/");
			}
			String fleetName = dynamicForm.bindFromRequest().data()
					.get("fleetName");
			Fleet f = Fleet.findByName(fleetName);
			Vehicle v;
			String t = dynamicForm.bindFromRequest().data().get("t1");
			String[] vids = t.split(",");
			String vi = null;
			for (int i = 0; i < vids.length; i++) {
				vi = vids[i];
				if (vi != null && !vi.isEmpty()) {
					v = Vehicle.findById(Long.parseLong(vi));
					v.fleet = f;
					v.save();
					f.vehicles.add(v);
					f.save();
				}
			}
			flash("success", "Checked vehicles successfully updated!");
			return redirect("/");
		} catch (Exception e) {
			flash("error",
					"Error at updating vehicles.Some vehicles maybe does not exists!");
			Logger.error("Error at updating vehicles: " + e.getMessage(), e);
			return redirect("/");
		}
	}

	
	public Result changeVehiclesType() {
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return redirect("/");
			}
			String typeName = dynamicForm.bindFromRequest().data()
					.get("typeName");
			Type type = Type.findByName(typeName);
			Vehicle v;
			String t = dynamicForm.bindFromRequest().data().get("t2");
			String[] vids = t.split(",");
			String vi = null;
			for (int i = 0; i < vids.length; i++) {
				vi = vids[i];
				if (vi != null && !vi.isEmpty()) {
					v = Vehicle.findById(Long.parseLong(vi));
					v.typev = type;
					v.save();
					type.vehicles.add(v);
					type.save();
				}
			}
			flash("success", "Checked vehicles successfully updated!");
			return redirect("/");
		} catch (Exception e) {
			flash("error",
					"Error at updating vehicles.Some vehicles maybe does not exists!");
			Logger.error("Error at updating vehicles: " + e.getMessage(), e);
			return redirect("/");
		}
	}
}

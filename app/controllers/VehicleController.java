package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import com.avaje.ebean.Model.Finder;

import models.Description;
import models.Fleet;
import models.Owner;
import models.TechnicalInfo;
import models.Tires;
import models.Type;
import models.Vehicle;
import models.VehicleRegistration;
import models.VehicleWarranty;
import play.db.ebean.Model;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

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
	 * Renders the 'create truckC' page
	 * 
	 * @return
	 */
	public Result addVehicleView() {
		return ok(addVehicleForm.render());
	}

	/**
	 * Finds vehicle using id and shows it
	 * 
	 * @param id
	 *            - Vehicle id
	 * @return redirect to the vehicle profile
	 */
	public Result showVehicle(long id) {
		Vehicle v = Vehicle.find.byId(id);

		if (v == null) {
			Logger.error("error", "Vehicle null at showVehicle()");
			flash("error", "Something went wrong!");
			return ok(showVehicle.render(v));
		}
		return ok(showVehicle.render(v));
	}

	/**
	 * Delete Vehicle using id
	 * 
	 * @param id
	 *            - Vehicle id (long)
	 * @return redirect to index after delete
	 */
	public Result deleteVehicle(long id) {
		try {
			Vehicle v = Vehicle.findById(id);
			Logger.info("Deleted vehicle: \"" + v.typev + "\"");
			Vehicle.deleteVehicle(id);
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		} catch (Exception e) {
			flash("error", "Error at delete vehicle!");
			Logger.error("Error at delete Vehicle: " + e.getMessage());
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
	}

	/**
	 * Renders the view of a Vehicle. Method receives the id of the vehicle and
	 * finds the vehicle by id and send's the vehicle to the view.
	 * 
	 * @param id
	 *            long
	 * @return Result render VehicleView
	 */
	public Result editVehicleView(long id) {
		Vehicle v = Vehicle.findById(id);
		// Exception handling.
		if (v == null) {
			flash("error", "Vehicle is not exists");
			return redirect("/");
		}
		return ok(editVehicleView.render(v));

	}

	/**
	 * Edit Vehicle Method receives an id, finds the specific vehicle and
	 * renders the edit View for the vehicle. If any error occurs, the view is
	 * rendered again.
	 * 
	 * @param id
	 *            of vehicle
	 * @return Result render the vehicle edit view
	 */
	public Result editVehicle(long id) {
		Form<Vehicle> vehicleForm = Form.form(Vehicle.class).bindFromRequest();
		DynamicForm dynamicForm = Form.form().bindFromRequest();
		Vehicle v = Vehicle.findById(id);
		try {
			if (vehicleForm.hasErrors() || vehicleForm.hasGlobalErrors()) {
				Logger.info("Vehicle update error");
				flash("error", "Error in vehicle form");
				return ok(editVehicleView.render(v));
			}

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

			String typeName = vehicleForm.bindFromRequest().data()
					.get("typeName");

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

			Type t;
			String newType = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");

					return ok(editVehicleView.render(v));
				} else if (Type.findByName(newType) != null) {
					t = Type.findByName(newType);
					t.save();
				} else {
					t = new Type(newType);
					t.save();
				}
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
						registrationNo, certificateNo, o, city, regDate,
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

			v.typev = t;
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
			if (newType.isEmpty()) {
				List<Description> desc = Vehicle.findByType(t).get(0).description;
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

			Logger.info(session("name") + " updated vehicle: " + v.id);
			System.out.println(v.description.size() + "//"
					+ v.technicalInfo.engineSerialNumber + "//"
					+ v.vRegistration.regNo + "//"
					+ v.vehicleWarranty.warrantyDetails);
			flash("success", v.typev.name + " successfully updated!");
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
	 * First checks if the vehicle form has errors. Creates a new vehicle or
	 * renders the view again if any error occurs.
	 * 
	 * @return redirect to create vehicle view
	 * @throws ParseException
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
				flash("error", "Vehicle with that vid already exists");
				return redirect("/addVehicle");
			}

			Type t;
			String newType = vehicleForm.bindFromRequest().field("newType")
					.value();
			String type = vehicleForm.bindFromRequest().field("typeName")
					.value();
			if (!type.equals("New Type")) {
				t = Type.findByName(type);
				t.save();
			} else {
				if (newType.isEmpty()) {
					flash("error", "Empty type name");
					return redirect("/addVehicle");
				} else if (Type.findByName(newType) != null) {
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
			Logger.info(" created vehicle ");
			return ok(editVehicleView.render(v));

		} catch (Exception e) {
			flash("error", "Error at adding vehicle");
			Logger.error("Error at addVehicle: " + e.getMessage(), e);
			return redirect("/addVehicle");
		}
	}

	public Result listVehicles() {
		if (Vehicle.listOfVehicles() == null) {

			return ok(listAllVehicles.render(new ArrayList<Vehicle>()));
		}
		return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
	}

}

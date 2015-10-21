//import helpers.HashHelper;
//import models.*;
//import play.Application;
//import play.GlobalSettings;
//import models.*;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.sql.Date;
//
//public class Global extends GlobalSettings {
//
//	@Override
//	public void onStart(Application app) {
//loc

//		if (Admin.checkIfExists("admin") == false) {
//			Admin.createAdmin("admin", "Adminović", "admin",
//					HashHelper.createPassword("admin"), "", "Sarajevo", true,
//					true);
//		}
//
//		java.util.Date utilDate = new java.util.Date();
//		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
//
//		Client c1 = null;
//		Client c2 = null;
//		Client c3 = null;
//		Client c4 = null;
//		Client c5 = null;
//		Client c6 = null;
//		if (Client.findByName("Apple") == null) {
//			c1 = Client.saveToDB("Apple", "Company",
//					"George Washington Street", "099888777", "apple@gmail.com");
//		}
//
//		if (Client.findByName("Microsoft") == null) {
//			c2 = Client.saveToDB("Microsoft", "Company", "John Smith Street",
//					"099888666", "microsoft@gmail.com");
//		}
//
//		if (Client.findByName("Civil Safety") == null) {
//			c3 = Client.saveToDB("Civil Safety", "Government",
//					"Harry Truman Avenue 33", "222888777", "cs@gusa.gov.");
//		}
//		if (Client.findByName("US Airforce") == null) {
//			c4 = Client.saveToDB("US Airforce", "Government",
//					"John F. Kennedy St.", "099111777", "usaf@usa.gov");
//		}
//
//		if (Client.findByName("John Doe") == null) {
//			c5 = Client.saveToDB("John Doe", "Person", "Nowhere to be found",
//					"010101010101010", "johnDoe@gmail.com");
//		}
//
//		if (Client.findByName("Freddy Crueger") == null) {
//			c6 = Client.saveToDB("Freddy Crueger", "Person", "Elm Street",
//					"999999999", "freddyK@gmail.com");
//		}
//
//		Driver d1 = null;
//		Driver d2 = null;
//		Driver d3 = null;
//		Driver d4 = null;
//		if (Driver.findByDriverName("Tom Cruz") == null) {
//			long id = Driver.createDriver("Tom", "Cruz", "000333444",
//					"kralja tvrtka 12", "responsible,professional", sqlDate);
//			d1 = Driver.findById(id);
//			d1.save();
//		}
//		if (Driver.findByDriverName("Vin Diesel") == null) {
//			long id = Driver.createDriver("Vin", "Diesel", "000333445",
//					"kralja tvrtka 14", "responsible,professional", sqlDate);
//			d2 = Driver.find.byId(id);
//			d2.save();
//		}
//		if (Driver.findByDriverName("John Wayne") == null) {
//			long id = Driver.createDriver("John", "Wayne", "000433444",
//					"kralja tvrtka 15", "responsible,professional", sqlDate);
//			d3 = Driver.find.byId(id);
//			d3.save();
//		}
//		if (Driver.findByDriverName("Jason Statham") == null) {
//			long id = Driver.createDriver("Jason", "Statham", "007",
//					"Kralja Tvrtka 11", "responsible,professional", sqlDate);
//			d4 = Driver.find.byId(id);
//			d4.save();
//		}
//
//		if (Service.findByType("Oil change") == null) {
//			Service s1 = Service.findS.byId(Service.createService("Oil change",
//					"Oil change"));
//		}
//		if (Service.findByType("Brake check") == null) {
//			Service s2 = Service.findS.byId(Service.createService(
//					"Brake check", "Brake check"));
//		}
//		if (Service.findByType("Tire change") == null) {
//			Service s3 = Service.findS.byId(Service.createService(
//					"Tire change", "Tire change"));
//		}
//
//		Type carType = null, droneType = null, planeType = null, motorType = null, trainType = null, truckType = null, busType = null, trailerType = null, wagonType = null;
//
//		Owner o = null;
//		if (Owner.findByName("GlobalGPS") == null)
//			o = Owner.find
//					.byId(Owner.createOwner("GlobalGPS", "@globalgps.ba"));
//
//		if (Type.findByName("Car") == null) {
//			carType = Type.find.byId(Type.createType("Car"));
//			carType.save();
//		}
//		if (Type.findByName("Dron") == null) {
//			droneType = Type.find.byId(Type.createType("Dron"));
//			droneType.save();
//		}
//		if (Type.findByName("Bus") == null) {
//			busType = Type.find.byId(Type.createType("Bus"));
//			busType.save();
//		}
//		if (Type.findByName("Train") == null) {
//			trainType = Type.find.byId(Type.createType("Train"));
//			trainType.save();
//		}
//		if (Type.findByName("Truck") == null) {
//			truckType = Type.find.byId(Type.createType("Truck"));
//			truckType.save();
//		}
//		if (Type.findByName("Airplane") == null) {
//			planeType = Type.find.byId(Type.createType("Airplane"));
//			planeType.save();
//		}
//		if (Type.findByName("Motorcycle") == null) {
//			motorType = Type.find.byId(Type.createType("Motorcycle"));
//			motorType.save();
//		}
//		if (Type.findByName("Trailer") == null) {
//			trailerType = Type.find.byId(Type.createType("Trailer"));
//			trailerType.save();
//		}
//		if (Type.findByName("Wagon") == null) {
//			wagonType = Type.find.byId(Type.createType("Wagon"));
//			wagonType.save();
//		}
//
//		Description ds1 = null;
//		if (Description.findByName("Brand").isEmpty()) {
//			ds1 = Description.findById(Description.createDescription("Brand",
//					"Audi"));
//			ds1.save();
//		}
//		Description ds2 = null;
//		if (Description.findByName("Model").isEmpty()) {
//			ds2 = Description.findById(Description.createDescription("Model",
//					"A5"));
//			ds2.save();
//		}
//
//		Description ds5 = null;
//		if (Description.findByName("Production State").isEmpty()) {
//			ds5 = Description.findById(Description.createDescription(
//					"Production State", "Germany"));
//			ds5.save();
//		}
//		Description ds6 = null;
//		if (Description.findByName("Production Year").isEmpty()) {
//			ds6 = Description.findById(Description.createDescription(
//					"Production Year", "2015"));
//			ds6.save();
//		}
//		Description ds7 = null;
//		if (Description.findByName("Max Speed").isEmpty()) {
//			ds7 = Description.findById(Description.createDescription(
//					"Max Speed", "200 km/h"));
//			ds7.save();
//		}
//		Description ds9 = null;
//		if (Description.findByName("Color").isEmpty()) {
//			ds9 = Description.findById(Description.createDescription("Color",
//					"White"));
//			ds9.save();
//		}
//
//		Description ds10 = null;
//		if (Description.findByName("Shape").isEmpty()) {
//			ds10 = Description.findById(Description.createDescription("Shape",
//					"Coupe"));
//			ds10.save();
//		}
//		Description ds11 = null;
//		if (Description.findByName("Wheels").isEmpty()) {
//			ds11 = Description.findById(Description.createDescription("Wheels",
//					"4"));
//			ds11.save();
//		}
//
//		Description ds17 = null;
//		if (Description.findByName("Doors").isEmpty()) {
//			ds17 = Description.findById(Description.createDescription("Doors",
//					"5"));
//			ds17.save();
//		}
//		Description ds18 = null;
//		if (Description.findByName("Seats").isEmpty()) {
//			ds18 = Description.findById(Description.createDescription("Seats",
//					"4+1"));
//			ds18.save();
//		}
//
//		Description ds20 = null;
//		if (Description.findByName("Batery").isEmpty()) {
//			ds20 = Description.findById(Description.createDescription("Batery",
//					"15,2 V"));
//			ds20.save();
//		}
//		Description ds21 = null;
//		if (Description.findByName("Flight Duration").isEmpty()) {
//			ds21 = Description.findById(Description.createDescription(
//					"Flight Duration", "45 min"));
//			ds21.save();
//		}
//		Description ds22 = null;
//		if (Description.findByName("Camera").isEmpty()) {
//			ds22 = Description.findById(Description.createDescription("Camera",
//					"4k"));
//			ds22.save();
//		}
//
//		List<Description> description = new ArrayList<Description>();
//		description.add(ds1);
//		description.add(ds2);
//		description.add(ds5);
//		description.add(ds6);
//		description.add(ds7);
//		description.add(ds9);
//
//		List<Description> carDescription = new ArrayList<Description>();
//		List<Description> busDescription = new ArrayList<Description>();
//		List<Description> truckDescription = new ArrayList<Description>();
//		List<Description> trainDescription = new ArrayList<Description>();
//		List<Description> dronDescription = new ArrayList<Description>();
//		List<Description> trailerDescription = new ArrayList<Description>();
//		List<Description> wagonDescription = new ArrayList<Description>();
//		List<Description> planeDescription = new ArrayList<Description>();
//		List<Description> motorDescription = new ArrayList<Description>();
//
//		// trailerDescription.addAll(description);
//		trailerDescription.add(ds5);
//		trailerDescription.add(ds6);
//		trailerDescription.add(ds9);
//		trailerDescription.add(ds11);
//
//		// wagonDescription.addAll(description);
//		wagonDescription.add(ds5);
//		wagonDescription.add(ds6);
//		wagonDescription.add(ds9);
//		wagonDescription.add(ds11);
//		wagonDescription.add(ds17);
//		wagonDescription.add(ds18);
//
//		carDescription.addAll(description);
//		carDescription.add(ds10);
//		carDescription.add(ds17);
//		carDescription.add(ds18);
//
//		busDescription.addAll(description);
//		busDescription.add(ds11);
//		busDescription.add(ds17);
//		busDescription.add(ds18);
//
//		truckDescription.addAll(description);
//		truckDescription.add(ds11);
//
//		trainDescription.addAll(description);
//		trainDescription.add(ds18);
//		trainDescription.add(ds17);
//
//		dronDescription.addAll(description);
//		dronDescription.add(ds22);
//		dronDescription.add(ds21);
//		dronDescription.add(ds20);
//
//		planeDescription.addAll(description);
//		planeDescription.add(ds18);
//
//		motorDescription.addAll(description);
//
//		Fleet f = null;
//		Fleet f2 = null;
//		Fleet f3 = null;
//		Fleet f4 = null;
//
//		if (Fleet.findByName("Flota 1") == null)
//			f = Fleet.findById(Fleet.createFleet("Flota 1", 0, sqlDate,
//					sqlDate, "Sarajevo", "Tuzla"));
//		if (Fleet.findByName("Flota 2") == null)
//			f2 = Fleet.findById(Fleet.createFleet("Flota 2", 0, sqlDate,
//					sqlDate, "Prijedor", "Mostar"));
//		if (Fleet.findByName("Flota 3") == null)
//			f3 = Fleet.findById(Fleet.createFleet("Flota 3", 0, sqlDate,
//					sqlDate, "Zavidovići", "Travnik"));
//		if (Fleet.findByName("Flota 4") == null)
//			f4 = Fleet.findById(Fleet.createFleet("Flota 4", 0, sqlDate,
//					sqlDate, "Bihać", "Zvornik"));
//
//		if (Vehicle.findByVid("1") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("1", "car1", o,
//					carType, carDescription));
//			v.fleet = f;
//			v.isAsigned = true;
//			v.save();
//			f.numOfVehicles = f.vehicles.size();
//			f.save();
//		}
//		if (Vehicle.findByVid("2") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("2", "truck1",
//					o, truckType, truckDescription));
//			v.fleet = f2;
//			v.isAsigned = true;
//			v.save();
//			f2.numOfVehicles = f2.vehicles.size();
//			f2.save();
//		}
//		if (Vehicle.findByVid("3") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("3", "train1",
//					o, trainType, trainDescription));
//			v.fleet = f3;
//			v.isAsigned = true;
//			v.save();
//			f3.numOfVehicles = f3.vehicles.size();
//			f3.save();
//		}
//		if (Vehicle.findByVid("4") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("4", "bus1", o,
//					busType, busDescription));
//			v.fleet = f4;
//			v.isAsigned = true;
//			v.save();
//			f4.numOfVehicles = f4.vehicles.size();
//			f4.save();
//		}
//		if (Vehicle.findByVid("5") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("5", "car2", o,
//					carType, carDescription));
//			v.fleet = f;
//			v.isAsigned = true;
//			v.save();
//			f.numOfVehicles = f.vehicles.size();
//			f.save();
//		}
//		if (Vehicle.findByVid("6") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("6", "dron1", o,
//					droneType, dronDescription));
//			v.fleet = f2;
//			v.isAsigned = true;
//			v.save();
//			f2.numOfVehicles = f2.vehicles.size();
//			f2.save();
//		}
//		if (Vehicle.findByVid("7") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("7", "truck2",
//					o, truckType, truckDescription));
//			v.fleet = f3;
//			v.isAsigned = true;
//			v.save();
//			f3.numOfVehicles = f3.vehicles.size();
//			f3.save();
//		}
//		if (Vehicle.findByVid("8") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("8", "train2",
//					o, trainType, trainDescription));
//			v.fleet = f4;
//			v.isAsigned = true;
//			v.save();
//			f4.numOfVehicles = f4.vehicles.size();
//			f4.save();
//		}
//		if (Vehicle.findByVid("9") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("9", "bus2", o,
//					busType, busDescription));
//			v.fleet = f;
//			v.isAsigned = true;
//			v.save();
//			f.numOfVehicles = f.vehicles.size();
//			f.save();
//		}
//		if (Vehicle.findByVid("10") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("10", "dron2",
//					o, droneType, dronDescription));
//			v.fleet = f2;
//			v.isAsigned = true;
//			v.save();
//			f2.numOfVehicles = f2.vehicles.size();
//			f2.save();
//		}
//		if (Vehicle.findByVid("11") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("11", "car3", o,
//					carType, carDescription));
//			v.fleet = f3;
//			v.isAsigned = true;
//			v.save();
//			f3.numOfVehicles = f3.vehicles.size();
//			f3.save();
//		}
//		if (Vehicle.findByVid("12") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("12", "train3",
//					o, trainType, trainDescription));
//			v.fleet = f4;
//			v.isAsigned = true;
//			v.save();
//			f4.numOfVehicles = f4.vehicles.size();
//			f4.save();
//		}
//		if (Vehicle.findByVid("13") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("13", "bus3", o,
//					busType, busDescription));
//			v.fleet = f;
//			v.isAsigned = true;
//			v.save();
//			f.numOfVehicles = f.vehicles.size();
//			f.save();
//		}
//		if (Vehicle.findByVid("14") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("14", "dron3",
//					o, droneType, dronDescription));
//			v.fleet = f2;
//			v.isAsigned = true;
//			v.save();
//			f2.numOfVehicles = f2.vehicles.size();
//			f2.save();
//		}
//		if (Vehicle.findByVid("15") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("15", "truck3",
//					o, truckType, truckDescription));
//			v.fleet = null;
//			v.save();
//		}
//		if (Vehicle.findByVid("16") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("16",
//					"trailer1", o, trailerType, trailerDescription));
//			v.save();
//		}
//		if (Vehicle.findByVid("17") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("17",
//					"trailer2", o, trailerType, trailerDescription));
//			v.save();
//		}
//		if (Vehicle.findByVid("18") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("18", "wagon1",
//					o, wagonType, wagonDescription));
//			v.save();
//		}
//		if (Vehicle.findByVid("19") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("19", "wagon2",
//					o, wagonType, wagonDescription));
//			v.save();
//		}
//		if (Vehicle.findByVid("20") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("20", "wagon3",
//					o, wagonType, wagonDescription));
//			v.save();
//		}
//		if (Vehicle.findByVid("21") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("21", "plane1",
//					o, planeType, planeDescription));
//			v.fleet = null;
//			v.save();
//		}
//		if (Vehicle.findByVid("22") == null) {
//			Vehicle v = Vehicle.findById(Vehicle.createVehicle("22",
//					"motorcycle1", o, motorType, motorDescription));
//			v.fleet = null;
//			v.save();
//		}
//
//		String sp = "Sar";
//		String ep = "Sar";
//		models.Route r = null;
//		if (Route.findByName(sp + " - " + ep) == null) {
//			r = Route.saveToDB(sp, ep);
//		}
//		TravelOrder to = null;
//		if (TravelOrder.findByNumberTo(1) == null) {
//			to = TravelOrder.saveTravelOrderToDB(1, "Putovanje u bolje sutra",
//					"Dokundisalo 'vako", "Budućnost", sqlDate, sqlDate, d4,
//					Vehicle.findByVid("5"), r);
//			to.save();
//			d4.engagedd = true;
//			d4.save();
//			Vehicle v = Vehicle.findByVid("5");
//			v.engagedd = true;
//			v.save();
//		}
//		VehicleBrand b1 = null;
//		VehicleBrand b2 = null;
//		VehicleBrand b3 = null;
//		VehicleBrand b4 = null;
//		VehicleBrand b5 = null;
//		VehicleBrand b6 = null;
//		if(VehicleBrand.findByName("Audi") == null) {
//			b1 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("Audi"));
//			b1.typev = carType;
//			b1.save();
//		}
//		if(VehicleBrand.findByName("Bmw") == null) {
//			b2 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("Bmw"));
//			b2.typev = carType;
//			b2.save();
//		}
//		if(VehicleBrand.findByName("Mercedes") == null) {
//			b3 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("Mercedes"));
//			b3.typev = carType;
//			b3.save();
//		}
//		if(VehicleBrand.findByName("Man") == null) {
//			b4 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("Man"));
//			b4.typev = truckType;
//			b4.save();
//		}
//		if(VehicleBrand.findByName("VW") == null) {
//			b5 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("VW"));
//			b5.typev = carType;
//			b5.save();
//		}
//		if(VehicleBrand.findByName("Scania") == null) {
//			b6 = VehicleBrand.findById(VehicleBrand.createVehicleBrand("Scania"));
//			b6.typev = truckType;
//			b6.save();
//		}
//		VehicleModel m1 = null;
//		VehicleModel m2 = null;
//		VehicleModel m3 = null;
//		VehicleModel m4 = null;
//		VehicleModel m5 = null;
//		VehicleModel m6 = null;
//		if(VehicleModel.findByName("A3") == null) {
//			m1 = VehicleModel.findById(VehicleModel.createVehicleModel("A3", b1));
//			m1.save();
//			b1.vehicleModels.add(m1);
//		}
//		if(VehicleModel.findByName("Golf 2") == null) {
//			m2 = VehicleModel.findById(VehicleModel.createVehicleModel("Golf 2", b5));
//			m2.save();
//			b5.vehicleModels.add(m2);
//		}
//		if(VehicleModel.findByName("Cls 200") == null) {
//			m3 = VehicleModel.findById(VehicleModel.createVehicleModel("Cls 200", b3));
//			m3.save();
//			b3.vehicleModels.add(m3);
//		}
//		if(VehicleModel.findByName("Man model") == null) {
//			m4 = VehicleModel.findById(VehicleModel.createVehicleModel("Man model", b4));
//			m4.save();
//			b4.vehicleModels.add(m4);
//		}
//		if(VehicleModel.findByName("Bmw model") == null) {
//			m5 = VehicleModel.findById(VehicleModel.createVehicleModel("Bmw model", b2));
//			m5.save();
//			b2.vehicleModels.add(m5);
//		}
//		if(VehicleModel.findByName("Scania model") == null) {
//			m6 = VehicleModel.findById(VehicleModel.createVehicleModel("Scania model", b6));
//			m6.save();
//			b6.vehicleModels.add(m6);
//		}
//	}
//}

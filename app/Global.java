import helpers.HashHelper;
import models.Admin;
import models.Description;
import models.Driver;
import models.Fleet;
import models.Owner;
import models.Type;
import models.Vehicle;
import play.Application;
import play.GlobalSettings;
import models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;


public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {

		if (Admin.checkIfExists("admin") == false) {
			Admin.createAdmin("Admin", "Adminović", "admin",
					HashHelper.createPassword("admin"), "", "Sarajevo", true,
					true);
		}
		
		java.util.Date utilDate = new java.util.Date();
	    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		Driver d1 = null;
		Driver d2 = null;
		Driver d3 = null;
		Driver d4 = null;
		if(Driver.findByName("Tom") == null) {
			long id = Driver.createDriver("Tom", "Cruz",
				"000333444", "kralja tvrtka 12", "responsible,professional", sqlDate);
		d1 = Driver.findById(id);
		d1.save();
		}
		if(Driver.findByName("Vin") == null) {
		long id = Driver.createDriver("Vin", "Diesel",
				"000333445", "kralja tvrtka 14", "responsible,professional", sqlDate);
		d2 = Driver.find.byId(id);
		d2.save();
		}
		if(Driver.findByName("John") == null) {
		long id = Driver.createDriver("John", "Wayne",
				"000433444", "kralja tvrtka 15", "responsible,professional", sqlDate);
		d3 = Driver.find.byId(id);
		d3.save();
		}
		if(Driver.findByName("Jason") == null) {
			long id = Driver.createDriver("Jason", "Statham",
					"007", "Kralja Tvrtka 11", "responsible,professional", sqlDate);
		d4 = Driver.find.byId(id);
		d4.save();
		}	
		
		if (Service.findByType("Oil change") == null) {
			Service s1 = Service.findS.byId(Service.createService("Oil change",
					"Oil change"));
		}
		if (Service.findByType("Brake check") == null) {
			Service s2 = Service.findS.byId(Service.createService("Brake check",
					"Brake check"));
		}
		if (Service.findByType("Tire change") == null) {
			Service s3 = Service.findS.byId(Service.createService("Tire change",
					"Tire change"));
		}

		Description desc1 = null;
		if (Description.findByChassisNum("001") == null) {
			desc1 = Description.find.byId(Description.createDescription("001",
					"001", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc2 = null;
		if (Description.findByChassisNum("002") == null) {
			desc2 = Description.find.byId(Description.createDescription("002",
					"002", "750", "Mercedes", "Citaro", "Red",
					"Articulated bus", "Diesel", "270", "70 000", "05.05.2010",
					"Germany"));
		}
		Description desc3 = null;
		if (Description.findByChassisNum("003") == null) {
			desc3 = Description.find.byId(Description.createDescription("003",
					"003", "800", "Scania", "Touring", "White", "Normal bus",
					"Diesel", "320", "90 000", "05.05.2005", "Sweden"));
		}
		Description desc4 = null;
		if (Description.findByChassisNum("004") == null) {
			desc4 = Description.find.byId(Description.createDescription("004",
					"004", "850", "Volvo", "9900", "Black", "Normal bus",
					"Diesel", "300", "100 000", "05.05.2007", "Sweden"));
		}
		Description desc5 = null;
		if (Description.findByChassisNum("005") == null) {
			desc5 = Description.find.byId(Description.createDescription("005",
					"005", "950", "Scania", "Streamline", "White", "Tow Truck",
					"Diesel", "300", "70 000", "05.05.2007", "Sweden"));
		}
		Description desc6 = null;
		if (Description.findByChassisNum("006") == null) {
			desc6 = Description.find.byId(Description.createDescription("006",
					"006", "950", "Mercedes", "Actros", "White", "Tow Truck",
					"Diesel", "300", "40 000", "05.05.2007", "Germany"));
		}
		Description desc7 = null;
		if (Description.findByChassisNum("007") == null) {
			desc7 = Description.find.byId(Description.createDescription("007",
					"007", "950", "MAN", "TGX", "Black", "Tow Truck", "Diesel",
					"300", "50 000", "05.05.2007", "Germany"));
		}
		Description desc8 = null;
		if (Description.findByChassisNum("008") == null) {
			desc8 = Description.find.byId(Description.createDescription("008",
					"008", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc9 = null;
		if (Description.findByChassisNum("009") == null) {
			desc9 = Description.find.byId(Description.createDescription("009",
					"009", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc10 = null;
		if (Description.findByChassisNum("010") == null) {
			desc10 = Description.find.byId(Description.createDescription("010",
					"010", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc11 = null;
		if (Description.findByChassisNum("011") == null) {
			desc11 = Description.find.byId(Description.createDescription("011",
					"011", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc12 = null;
		if (Description.findByChassisNum("012") == null) {
			desc12 = Description.find.byId(Description.createDescription("012",
					"012", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc13 = null;
		if (Description.findByChassisNum("013") == null) {
			desc13 = Description.find.byId(Description.createDescription("013",
					"013", "150", "Fiat", "Punto", "White", "Limousine",
					"Gasoline", "30", "70 000", "05.05.2007", "Italy"));
		}
		Description desc14 = null;
		if (Description.findByChassisNum("014") == null) {
			desc14 = Description.find.byId(Description.createDescription("014",
					"014", "260", "Audi", "A5", "Black", "Limousine", "Diesel",
					"30", "90 000", "05.05.20012", "Germany"));
		}
		Description desc15 = null;
		if (Description.findByChassisNum("015") == null) {
			desc15 = Description.find.byId(Description.createDescription("015",
					"015", "250", "BMW", "x5", "White", "Limousine", "Diesel",
					"50", "40 000", "05.05.2013", "Germany"));
		}

		Type carType = null, planeType = null, trainType = null, truckType = null, busType = null;
		Owner o = null;
		if (Owner.findByName("GlobalGPS") == null)
			o = Owner.find
					.byId(Owner.createOwner("GlobalGPS", "@globalgps.ba"));
		if (Type.findByName("Car") == null) {
			carType = Type.find.byId(Type.createType("Car", desc14));

			carType.save();
		}
		if (Type.findByName("Aeroplane") == null) {

			planeType = Type.find.byId(Type.createType("Aeroplane", desc1));

			planeType.save();
		}
		if (Type.findByName("Bus") == null) {

			busType = Type.find.byId(Type.createType("Bus", desc2));

			busType.save();
		}
		if (Type.findByName("Train") == null) {

			trainType = Type.find.byId(Type.createType("Train", desc1));

			trainType.save();
		}
		if (Type.findByName("Truck") == null) {

			truckType = Type.find.byId(Type.createType("Truck", desc7));

			trainType.save();
		}
		Fleet f = null;
		Fleet f2 = null;
		Fleet f3 = null;
		Fleet f4 = null;
	
		if (Fleet.findByName("Flota 1") == null)
			f = Fleet.findById(Fleet.createFleet("Flota 1", 0, sqlDate,
					sqlDate, "Sarajevo", "Tuzla"));
		if (Fleet.findByName("Flota 2") == null)
			f2 = Fleet.findById(Fleet.createFleet("Flota 2", 0, sqlDate,
					sqlDate, "Prijedor", "Mostar"));
		if (Fleet.findByName("Flota 3") == null)
			f3 = Fleet.findById(Fleet.createFleet("Flota 3", 0, sqlDate,
					sqlDate, "Zavidovići", "Travnik"));
		if (Fleet.findByName("Flota 4") == null)
			f4 = Fleet.findById(Fleet.createFleet("Flota 4", 0, sqlDate,
					sqlDate, "Bihać", "Zvornik"));

		if (Vehicle.findByVid("1") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("1", "car1", o,
					carType));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("2") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("2", "truck1",
					o, truckType));
			v.fleet = f2;
			v.typev.description = desc5;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("3") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("3", "train1",
					o, trainType));
			v.fleet = f3;

			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("4") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("4", "bus1", o,
					busType));
			v.fleet = f4;
			v.typev.description = desc2;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("5") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("5", "car2", o,
					carType));
			v.fleet = f;
			v.typev.description = desc14;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("6") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("6", "plane1",
					o, planeType));
			v.fleet = f2;

			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("7") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("7", "truck2",
					o, truckType));
			v.fleet = f3;
			v.typev.description = desc6;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("8") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("8", "train2",
					o, trainType));
			v.fleet = f4;

			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("9") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("9", "bus2", o,
					busType));
			v.fleet = f;
			v.typev.description = desc3;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("10") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("10", "plane2",
					o, planeType));
			v.fleet = f2;

			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("11") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("11", "car3", o,
					carType));
			v.fleet = f3;
			v.typev.description = desc15;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("12") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("12", "train3",
					o, trainType));
			v.fleet = f4;

			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("13") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("13", "bus3", o,
					busType));
			v.fleet = f;
			v.typev.description = desc4;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("14") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("14", "plane3",
					o, planeType));
			v.fleet = f2;

			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("15") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("15", "truck3",
					o, truckType));
			v.typev.description = desc7;
			v.save();

		}
		TravelOrder to = null;
		if(TravelOrder.findByNumberTo(1) == null) {
		 to = TravelOrder.saveTravelOrderToDB(1, "Putovanje u bolje sutra",
		 "Dokundisalo 'vako", "Budućnost", sqlDate, sqlDate, d4, Vehicle.findByVid("5"));
		to.save();
		d4.engagedd = true;
		d4.save();
		Vehicle v = Vehicle.findByVid("5");
		v.engagedd = true;
		v.save();
		}
	}
}
	


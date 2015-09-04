import helpers.HashHelper;
import models.*;
import play.Application;
import play.GlobalSettings;
import models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
		
	    Client c1;
	    Client c2;
	    Client c3;
	    Client c4;
	    Client c5;
	    Client c6;
	    if(Client.findByName("Apple") == null) {
			c1 = Client.saveToDB("Apple","Company", "George Washington Street","099888777","apple@gmail.com");
			}
	    
		if(Client.findByName("Microsoft") == null) {
				c1 = Client.saveToDB("Microsoft","Company", "John Smith Street","099888666","microsoft@gmail.com");	
				}
			
		if(Client.findByName("Civil Safety") == null) {
					c1 = Client.saveToDB("Civil Safety","Government", "Harry Truman Avenue 33","222888777","cs@gusa.gov.");		
		}
		if(Client.findByName("US Airforce") == null) {
						c1 = Client.saveToDB("US Airforce","Government", "John F. Kennedy St.","099111777","usaf@usa.gov");
						}
				
		if(Client.findByName("John Doe") == null) {
							c1 = Client.saveToDB("John Doe","Person", "Nowhere to be found","010101010101010","johnDoe@gmail.com");
							}
							
		if(Client.findByName("Freddy Crueger") == null) {
								c1 = Client.saveToDB("Freddy Crueger","Person", "Elm Street","999999999","freddyK@gmail.com");
								}
								
								
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
		
		
		Owner o = null;
		if (Owner.findByName("GlobalGPS") == null)
			o = Owner.find
					.byId(Owner.createOwner("GlobalGPS", "@globalgps.ba"));
		
		
		Description ds1 = null;
		if(Description.findByName("Brand").isEmpty()) {
			ds1 = Description.findById(Description.createDescription("Brand", "Audi"));
			ds1.save();
		}
		Description ds2 = null;
		if(Description.findByName("Model").isEmpty()) {
			ds2 = Description.findById(Description.createDescription("Model", "A5"));
			ds2.save();
		}
		Description ds3 = null;
		if(Description.findByName("cCm").isEmpty()) {
			ds3 = Description.findById(Description.createDescription("cCm", "5000"));
			ds3.save();
		}
		Description ds4 = null;
		if(Description.findByName("Fuel").isEmpty()) {
			ds4 = Description.findById(Description.createDescription("Fuel", "Diesel"));
			ds4.save();
		}
		Description ds5 = null;
		if(Description.findByName("Production State").isEmpty()) {
			ds5 = Description.findById(Description.createDescription("Production State", "Germany"));
			ds5.save();
		}
		Description ds6 = null;
		if(Description.findByName("Engine Number").isEmpty()) {
			ds6 = Description.findById(Description.createDescription("Engine Number", "123"));
			ds6.save();
		}
		Description ds7 = null;
		if(Description.findByName("Chassis Num").isEmpty()) {
			ds7 = Description.findById(Description.createDescription("ID Num", "123"));
			ds7.save();
		}
		Description ds8 = null;
		if(Description.findByName("Production Year").isEmpty()) {
			ds8 = Description.findById(Description.createDescription("Production Year", "2015"));
			ds8.save();
		}
		
		List<Description> description = new ArrayList<Description>();
		description.add(ds1);
		description.add(ds2);
		description.add(ds3);
		description.add(ds4);
		description.add(ds5);
		description.add(ds6);
		description.add(ds7);
		description.add(ds8);
//		Description ds9 = null;
//		if(Description.findByName("Shape") == null) {
//			ds9 = Description.findById(Description.createDescription("Shape", "Coupe", types));
//			ds9.save();
//		}
//		Description ds10 = null;
//		if(Description.findByName("Wheels") == null) {
//			ds10 = Description.findById(Description.createDescription("Wheels", "4", types));
//			ds10.save();
//		}

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
		
		Type carType = null, planeType = null, trainType = null, truckType = null, busType = null;
		if (Type.findByName("Car") == null) {
			carType = Type.find.byId(Type.createType("Car"));
			carType.save();
		}
		if (Type.findByName("Aeroplane") == null) {

			planeType = Type.find.byId(Type.createType("Aeroplane"));
			planeType.save();
		}
		if (Type.findByName("Bus") == null) {

			busType = Type.find.byId(Type.createType("Bus"));
			busType.save();
		}
		if (Type.findByName("Train") == null) {

			trainType = Type.find.byId(Type.createType("Train"));
			trainType.save();
		}
		if (Type.findByName("Truck") == null) {

			truckType = Type.find.byId(Type.createType("Truck"));
			trainType.save();
		}
		
		if (Vehicle.findByVid("1") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("1", "car1", o,
					carType, description));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("2") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("2", "truck1",
					o, truckType, description));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("3") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("3", "train1",
					o, trainType, description));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("4") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("4", "bus1", o,
					busType, description));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("5") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("5", "car2", o,
					carType, description));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("6") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("6", "plane1",
					o, planeType, description));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("7") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("7", "truck2",
					o, truckType, description));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("8") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("8", "train2",
					o, trainType, description));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("9") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("9", "bus2", o,
					busType, description));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("10") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("10", "plane2",
					o, planeType, description));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("11") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("11", "car3", o,
					carType, description));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("12") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("12", "train3",
					o, trainType, description));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("13") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("13", "bus3", o,
					busType, description));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("14") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("14", "plane3",
					o, planeType, description));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("15") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("15", "truck3",
					o, truckType, description));
			v.save();

		}
		String sp = "Sar";
		String ep = "Sar";
		models.Route r = null;
		if(Route.findByName(sp + " - " + ep) == null) {
			r = Route.saveToDB(sp, ep);
		}
		TravelOrder to = null;
		if(TravelOrder.findByNumberTo(1) == null) {
		 to = TravelOrder.saveTravelOrderToDB(1, "Putovanje u bolje sutra",
		 "Dokundisalo 'vako", "Budućnost", sqlDate, sqlDate, d4, Vehicle.findByVid("5"), r);
		to.save();
		d4.engagedd = true;
		d4.save();
		Vehicle v = Vehicle.findByVid("5");
		v.engagedd = true;
		v.save();
		}
	}
}
	


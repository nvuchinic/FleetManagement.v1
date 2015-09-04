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
				c2 = Client.saveToDB("Microsoft","Company", "John Smith Street","099888666","microsoft@gmail.com");	
				}
			
		if(Client.findByName("Civil Safety") == null) {
					c3 = Client.saveToDB("Civil Safety","Government", "Harry Truman Avenue 33","222888777","cs@gusa.gov.");		
		}
		if(Client.findByName("US Airforce") == null) {
						c4 = Client.saveToDB("US Airforce","Government", "John F. Kennedy St.","099111777","usaf@usa.gov");
						}
				
		if(Client.findByName("John Doe") == null) {
							c5 = Client.saveToDB("John Doe","Person", "Nowhere to be found","010101010101010","johnDoe@gmail.com");
							}
							
		if(Client.findByName("Freddy Crueger") == null) {
								c6 = Client.saveToDB("Freddy Crueger","Person", "Elm Street","999999999","freddyK@gmail.com");
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
		if(Description.findByName("Power").isEmpty()) {
			ds3 = Description.findById(Description.createDescription("Power", "5000"));
			ds3.save();
		}
		Description ds4 = null;
		if(Description.findByName("Engine").isEmpty()) {
			ds4 = Description.findById(Description.createDescription("Engine", "Heat engine"));
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
		Description ds9 = null;
		if(Description.findByName("Shape") == null) {
			ds9 = Description.findById(Description.createDescription("Shape", "Coupe"));
			ds9.save();
		}
		Description ds10 = null;
		if(Description.findByName("Gross Vehicle Weight") == null) {
			ds10 = Description.findById(Description.createDescription("Gross Vehicle Weight", "10 000 kg"));
			ds10.save();
		}
		Description ds11 = null;
		if(Description.findByName("Capacity") == null) {
			ds11 = Description.findById(Description.createDescription("Capacity", "25 000"));
			ds11.save();
		}
		Description ds12 = null;
		if(Description.findByName("Mass") == null) {
			ds12 = Description.findById(Description.createDescription("Mass", "5000 kg"));
			ds12.save();
		}
		
		Description ds13 = null;
		if(Description.findByName("Max Speed") == null) {
			ds13 = Description.findById(Description.createDescription("Max Speed", "180 km/h"));
			ds13.save();
		}
		Description ds14 = null;
		if(Description.findByName("Doors") == null) {
			ds14 = Description.findById(Description.createDescription("Doors", "5"));
			ds14.save();
		}
		Description ds15 = null;
		if(Description.findByName("Seets") == null) {
			ds15 = Description.findById(Description.createDescription("Seets", "4 + 1"));
			ds15.save();
		}
		Description ds16 = null;
		if(Description.findByName("Batery") == null) {
			ds16 = Description.findById(Description.createDescription("Batery", "4 + 1"));
			ds16.save();
		}
		Description ds17 = null;
		if(Description.findByName("Camera") == null) {
			ds17 = Description.findById(Description.createDescription("Camera", "4k"));
			ds17.save();
		}
		Description ds18 = null;
		if(Description.findByName("Flight Duration") == null) {
			ds18 = Description.findById(Description.createDescription("Flight Duration", "1 h"));
			ds18.save();
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
		
		Type carType = null, planeType = null, trainType = null, truckType = null, busType = null;
		if (Type.findByName("Car") == null) {
			carType = Type.find.byId(Type.createType("Car"));
			carType.save();
		}
		if (Type.findByName("Dron") == null) {

			planeType = Type.find.byId(Type.createType("Dron"));
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
		
		List<Description> truckDescription = new ArrayList<Description>();
		List<Description> carDescription = new ArrayList<Description>();
		List<Description> busDescription = new ArrayList<Description>();
		List<Description> trainDescription = new ArrayList<Description>();
		List<Description> planeDescription = new ArrayList<Description>();
		
		carDescription.add(ds1);
		carDescription.add(ds2);
		carDescription.add(ds3);
		carDescription.add(ds4);
		carDescription.add(ds5);
		carDescription.add(ds6);
		carDescription.add(ds7);
		carDescription.add(ds8);
		carDescription.add(ds9);
		carDescription.add(ds13);
		carDescription.add(ds14);
		carDescription.add(ds15);
		
		busDescription = carDescription;
		busDescription.remove(ds9);
		
		truckDescription.add(ds1);
		truckDescription.add(ds2);
		truckDescription.add(ds3);
		truckDescription.add(ds4);
		truckDescription.add(ds5);
		truckDescription.add(ds6);
		truckDescription.add(ds7);
		truckDescription.add(ds8);
		truckDescription.add(ds13);
		truckDescription.add(ds10);
		truckDescription.add(ds11);
		truckDescription.add(ds12);
		
		planeDescription.add(ds1);
		planeDescription.add(ds2);
		planeDescription.add(ds13);
		planeDescription.add(ds16);
		planeDescription.add(ds17);
		planeDescription.add(ds18);
		
		trainDescription.add(ds1);
		trainDescription.add(ds2);
		trainDescription.add(ds3);
		trainDescription.add(ds5);
		trainDescription.add(ds8);
		trainDescription.add(ds10);
		trainDescription.add(ds11);
		trainDescription.add(ds13);
		
		if (Vehicle.findByVid("1") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("1", "car1", o,
					carType, carDescription));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("2") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("2", "truck1",
					o, truckType, truckDescription));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("3") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("3", "train1",
					o, trainType, trainDescription));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("4") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("4", "bus1", o,
					busType, busDescription));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("5") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("5", "car2", o,
					carType, carDescription));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("6") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("6", "dron1",
					o, planeType, planeDescription));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("7") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("7", "truck2",
					o, truckType, truckDescription));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("8") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("8", "train2",
					o, trainType, trainDescription));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("9") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("9", "bus2", o,
					busType, trainDescription));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("10") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("10", "dron2",
					o, planeType, planeDescription));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("11") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("11", "car3", o,
					carType, carDescription));
			v.fleet = f3;
			v.isAsigned = true;
			v.save();
			f3.numOfVehicles = f3.vehicles.size();
			f3.save();
		}
		if (Vehicle.findByVid("12") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("12", "train3",
					o, trainType, trainDescription));
			v.fleet = f4;
			v.isAsigned = true;
			v.save();
			f4.numOfVehicles = f4.vehicles.size();
			f4.save();
		}
		if (Vehicle.findByVid("13") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("13", "bus3", o,
					busType, busDescription));
			v.fleet = f;
			v.isAsigned = true;
			v.save();
			f.numOfVehicles = f.vehicles.size();
			f.save();
		}
		if (Vehicle.findByVid("14") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("14", "dron3",
					o, planeType, planeDescription));
			v.fleet = f2;
			v.isAsigned = true;
			v.save();
			f2.numOfVehicles = f2.vehicles.size();
			f2.save();
		}
		if (Vehicle.findByVid("15") == null) {
			Vehicle v = Vehicle.findById(Vehicle.createVehicle("15", "truck3",
					o, truckType, truckDescription));
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
	
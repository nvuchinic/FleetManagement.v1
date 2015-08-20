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
import java.util.Date;

public class Global extends GlobalSettings {
	
	

	@Override
	public void onStart(Application app) {
	if ( Admin.checkIfExists("admin") == false) {
		Admin.createAdmin("Admin", "Adminović", "admin", HashHelper.createPassword("admin"), "", "Sarajevo",
				true, true);
		}
	 String valuee="25/04/1980";
	 Date dob=null;
     try {
		 dob =new SimpleDateFormat("dd/MM/yyyy").parse(valuee);
	} catch (ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // Driver saveToDB(String name, String surname, String phoneNumber,
 		//	String adress, String description, String gender, Date dob)

     Driver d1=Driver.find.byId(Driver.saveToDB("Tom", "Cruz", "000333444", "kralja tvrtka 12", "responsible,professional", "m", dob));
     Driver d2=Driver.find.byId(Driver.saveToDB("Tom", "Hanks", "000333445", "kralja tvrtka 14", "responsible,professional", "m", dob));
     Driver d3=Driver.find.byId(Driver.saveToDB("John", "Wayne", "000433444", "kralja tvrtka 15", "responsible,professional", "m", dob));

     if(Service.findByType("Oil change")==null){
     Service s1 = Service.find.byId(Service.createService("Oil change", "Oil change"));
     }
     if(Service.findByType("Brake check")==null){
    	 Service s2 = Service.find.byId(Service.createService("Brake check", "Brake check"));
     }
     if(Service.findByType("Tire change")==null){
    	 Service s3 = Service.find.byId(Service.createService("Tire change", "Tire change"));
     }
     
     Description desc1 = null;
     if(Description.findByChassisNum("001") == null){
     desc1 = Description.find.byId(Description.createDescription("001", "001", "150", "Fiat", "Punto", "White",
    		 "Limousine", "Gasoline", "30", "70 000", "05.05.2007", "Italy"));
     }
     
     Type carType=null,planeType=null,trainType=null,truckType=null,busType=null;
     Owner o = null;
     if(Owner.findByName("GlobalGPS") == null)
     o = Owner.find.byId(Owner.createOwner("GlobalGPS", "@globalgps.ba"));
     if(Type.findByName("Car")==null){
     carType = Type.find.byId(Type.createType("Car", desc1));
     
     carType.save();
     }
     if(Type.findByName("Aeroplane")==null){

	 planeType = Type.find.byId(Type.createType("Aeroplane", desc1));
	 
	 planeType.save();
	}
     if(Type.findByName("Bus")==null){

	 busType = Type.find.byId(Type.createType("Bus", desc1));
	 
	 busType.save();
     }
     if(Type.findByName("Train")==null){

	 trainType = Type.find.byId(Type.createType("Train", desc1));
	 
	 trainType.save();
     }
     if(Type.findByName("Truck")==null){

	 truckType = Type.find.byId(Type.createType("Truck", desc1));
	
	 trainType.save();
	}
     Fleet f = null;
     Fleet f2 = null;
     Fleet f3 = null;
     Fleet f4 = null;
    if(Fleet.findByName("Flota 1") == null)
	f = Fleet.findById(Fleet.createFleet("Flota 1", 0, new Date(), new Date(), "Sarajevo", "Tuzla"));
    if(Fleet.findByName("Flota 2") == null)
	f2 = Fleet.findById(Fleet.createFleet("Flota 2", 0, new Date(), new Date(), "Prijedor", "Mostar"));
    if(Fleet.findByName("Flota 3") == null)
	f3 = Fleet.findById(Fleet.createFleet("Flota 3", 0, new Date(), new Date(), "Zavidovići", "Travnik"));
    if(Fleet.findByName("Flota 4") == null)
	f4 = Fleet.findById(Fleet.createFleet("Flota 4", 0, new Date(), new Date(), "Bihać", "Zvornik"));
	
	
	if(Vehicle.findByVid("1") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("1","car1", o, carType));
		v.fleet = f;
		v.isAsigned = true;
		v.save();
		f.numOfVehicles = f.vehicles.size();
		f.save();
	}
	if(Vehicle.findByVid("2") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("2","truck1", o, truckType));
		v.fleet = f2;
		v.isAsigned = true;
		v.save();
		f2.numOfVehicles = f2.vehicles.size();
		f2.save();
	}
	if(Vehicle.findByVid("3") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("3","train1", o, trainType));
		v.fleet = f3;

		v.isAsigned = true;
		v.save();
		f3.numOfVehicles = f3.vehicles.size();
		f3.save();
	}
	if(Vehicle.findByVid("4") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("4","bus1", o, busType));
		v.fleet = f4;
	

		v.isAsigned = true;
		v.save();
		f4.numOfVehicles = f4.vehicles.size();
		f4.save();
	}
	if(Vehicle.findByVid("5") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("5","car2", o, carType));
		v.fleet = f;

		v.isAsigned = true;
		v.save();
		f.numOfVehicles = f.vehicles.size();
		f.save();
	}
	if(Vehicle.findByVid("6") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("6","plane1", o, planeType));
		v.fleet = f2;

		v.isAsigned = true;
		v.save();
		f2.numOfVehicles = f2.vehicles.size();
		f2.save();
	}
	if(Vehicle.findByVid("7") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("7","truck2", o, truckType));
		v.fleet = f3;

		v.isAsigned = true;
		v.save();
		f3.numOfVehicles = f3.vehicles.size();
		f3.save();
	}
	if(Vehicle.findByVid("8") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("8","train2", o, trainType));
		v.fleet = f4;

		v.isAsigned = true;
		v.save();
		f4.numOfVehicles = f4.vehicles.size();
		f4.save();
	}
	if(Vehicle.findByVid("9") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("9","bus2", o, busType));
		v.fleet = f;

		v.isAsigned = true;
		v.save();
		f.numOfVehicles = f.vehicles.size();
		f.save();
	}
	if(Vehicle.findByVid("10") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("10","plane2", o, planeType));
		v.fleet = f2;

		v.isAsigned = true;
		v.save();
		f2.numOfVehicles = f2.vehicles.size();
		f2.save();
	}
	if(Vehicle.findByVid("11") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("11","car3", o, carType));
		v.fleet = f3;

		v.isAsigned = true;
		v.save();
		f3.numOfVehicles = f3.vehicles.size();
		f3.save();
	}
	if(Vehicle.findByVid("12") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("12","train3", o, trainType));
		v.fleet = f4;

		v.isAsigned = true;
		v.save();
		f4.numOfVehicles = f4.vehicles.size();
		f4.save();
	}
	if(Vehicle.findByVid("13") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("13","bus3", o, busType));
		v.fleet = f;

		v.isAsigned = true;
		v.save();
		f.numOfVehicles = f.vehicles.size();
		f.save();
	}
	if(Vehicle.findByVid("14") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("14","plane3", o, planeType));
		v.fleet = f2;

		v.isAsigned = true;
		v.save();
		f2.numOfVehicles = f2.vehicles.size();
		f2.save();
	}
	if(Vehicle.findByVid("15") == null) {
		Vehicle.findById(Vehicle.createVehicle("15","truck3", o, truckType));


	}
}
	
}

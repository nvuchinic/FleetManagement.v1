import helpers.HashHelper;

import models.Admin;
import models.Fleet;
import models.Owner;
import models.Type;
import models.Vehicle;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	
	

	@Override
	public void onStart(Application app) {
	if ( Admin.checkIfExists("admin") == false) {
		Admin.createAdmin("Admin", "Adminović", "admin", HashHelper.createPassword("admin"), "", "Sarajevo",
				true, true);
		}
	Owner o = Owner.find.byId(Owner.createOwner("GlobalGPS", "@globalgps.ba"));
	Type t = Type.find.byId(Type.createType("Auto", "description"));
	Type ty = Type.find.byId(Type.createType("Avion", "description"));
	Type typ = Type.find.byId(Type.createType("Bus", "description"));
	Type type = Type.find.byId(Type.createType("Voz", "description"));
	Type typev = Type.find.byId(Type.createType("Kamion", "description"));
	Fleet f = Fleet.findById(Fleet.createFleet("Flota 1", 0));
	Fleet f2 = Fleet.findById(Fleet.createFleet("Flota 2", 0));
	Fleet f3 = Fleet.findById(Fleet.createFleet("Flota 3", 0));
	Fleet f4 = Fleet.findById(Fleet.createFleet("Flota 4", 0));

	if(Vehicle.findByVid("1") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("1", o, t));
		v.fleet = f;
		f.numOfVehicles = f.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f.save();
		v.save();
	}
	if(Vehicle.findByVid("2") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("2", o, typev));
		v.fleet = f2;
		f2.numOfVehicles = f2.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f2.save();
		v.save();
	}
	if(Vehicle.findByVid("3") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("3", o, type));
		v.fleet = f3;
		f3.numOfVehicles = f3.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f3.save();
		v.save();
	}
	if(Vehicle.findByVid("4") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("4", o, typ));
		v.fleet = f4;
		f4.numOfVehicles = f4.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f4.save();
		v.save();
	}
	if(Vehicle.findByVid("5") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("5", o, t));
		v.fleet = f;
		f.numOfVehicles = f.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f.save();
		v.save();
	}
	if(Vehicle.findByVid("6") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("6", o, ty));
		v.fleet = f2;
		f2.numOfVehicles = f2.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f2.save();
		v.save();
	}
	if(Vehicle.findByVid("7") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("7", o, typev));
		v.fleet = f3;
		f3.numOfVehicles = f3.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f3.save();
		v.save();
	}
	if(Vehicle.findByVid("8") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("8", o, type));
		v.fleet = f4;
		f4.numOfVehicles = f4.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f4.save();
		v.save();
	}
	if(Vehicle.findByVid("9") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("9", o, typ));
		v.fleet = f;
		f.numOfVehicles = f.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f.save();
		v.save();
	}
	if(Vehicle.findByVid("10") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("10", o, ty));
		v.fleet = f2;
		f2.numOfVehicles = f2.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f2.save();
		v.save();
	}
	if(Vehicle.findByVid("11") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("11", o, t));
		v.fleet = f3;
		f3.numOfVehicles = f3.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f3.save();
		v.save();
	}
	if(Vehicle.findByVid("12") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("12", o, type));
		v.fleet = f4;
		f4.numOfVehicles = f4.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f4.save();
		v.save();
	}
	if(Vehicle.findByVid("13") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("13", o, typ));
		v.fleet = f;
		f.numOfVehicles = f.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f.save();
		v.save();
	}
	if(Vehicle.findByVid("14") == null) {
		Vehicle v = Vehicle.findById(Vehicle.createVehicle("14", o, ty));
		v.fleet = f2;
		f2.numOfVehicles = f2.vehicles.size();
		Vehicle.listOfUnnusedVehicles().remove(v);
		f2.save();
		v.save();
	}
	if(Vehicle.findByVid("15") == null) {
		Vehicle.createVehicle("15", o, typev);
	}
}
	
}

import helpers.HashHelper;

import java.util.Date;

import models.Admin;
import models.SuperUser;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	

	@Override
	public void onStart(Application app) {
	if ( Admin.checkIfExists("admin") == false) {
		Admin.createAdmin("Admin", "Adminović", "admin", HashHelper.createPassword("admin"), "", "Sarajevo",
				true, true);
		}
	}
}

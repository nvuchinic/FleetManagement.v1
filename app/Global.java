import helpers.HashHelper;

import java.util.Date;

import models.Admin;
import models.SuperUser;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	

	@Override
	public void onStart(Application app) {
	if ( Admin.checkIfExists("emir92@outlook.com") == false) {
		Admin.createAdmin("Admin", "Adminović", "emir92@outlook.com", HashHelper.createPassword("opacupa"), "Olimpijska31", "Sarajevo",
				true, true);
		}
	}
}

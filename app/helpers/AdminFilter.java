package helpers;

import models.Admin;
import models.SuperUser;
import play.Logger;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * This class is a controller filter and ensures that only a user with ADMIN privilegies
 * can perform certain actions.
 * @author Emir
 *
 */
public class AdminFilter extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		if (!ctx.session().containsKey("name"))
			return null;
		String name = ctx.session().get("name");
		Admin admin = Admin.findByName(name);
		if (admin != null) {
			if (admin.isAdmin == true) {
				return admin.name;
			} else
				return null;
		}
		return null;
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		Logger.error("Login To Complete");
		return redirect("/loginToComplete");
	}


}

package helpers;

import models.Admin;
import models.SuperUser;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import play.*;

/**
 * This class is a controller filter and ensures that only a user with ADMIN
 * privilegies can perform certain actions.
 * 
 * @author Emir
 *
 */
public class AdminFilter extends Security.Authenticator {
	
	 class Nesto extends Controller {
		public String getUsername() {
			String username = request().getHeader("Auth-User");
			return username;
		}
	}

	@Override
	public String getUsername(Http.Context ctx) {
		Nesto n = new Nesto();
		String auth=ctx.request().getHeader("User-Auth");
		String username = n.getUsername();
			Admin user = Admin.findByName(username);
			if (user != null) {
				return user.name;
			}		
		return null;
	}

	@Override
	public Result onUnauthorized(Http.Context context) {
		return redirect("http://tiimiss.globalgps.ba/");
		//return super.onUnauthorized(context);
	}

}

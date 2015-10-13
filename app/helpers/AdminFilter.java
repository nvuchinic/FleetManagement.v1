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
	
//	 class Nesto extends Controller {
//		public String getUsername() {
//			String username = request().getHeader("Auth-User");
//			return username;
//		}
//	}
//
//	@Override
//	public String getUsername(Http.Context ctx) {
//		Nesto n = new Nesto();
//		String username = n.getUsername();
//			Admin user = Admin.findByName(username);
//			if (user != null) {
//				return user.name;
//			}		
//		return null;
//	}
//
//	@Override
//	public Result onUnauthorized(Http.Context context) {
//		return super.onUnauthorized(context);
//	}

	@Override
	public String getUsername(Context ctx) {
		String userType = ctx.request().getHeader("User-Group");
		//String [] userGroupToArray=userType.split(":",2);
		//String admin=userGroupToArray[1];
		System.out.println("/////////////ISPISUJEM USER GROUP HEADER: "+userType);
		if(userType.contains("admin"))
		return "admin";
		return null;	
	}

	/**
	 * Redirects unauthorized users to the specified page
	 */
	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect("/");
	}
}

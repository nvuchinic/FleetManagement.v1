package controllers;

//import javax.xml.xpath.XPath;

import org.w3c.dom.Document;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.XPath;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class XmlController extends Controller {

	public Result sayHello() {
		Document dom = request().body().asXml();
		if (dom == null) {
			return badRequest("Expecting Xml data");
		} else {
			String name = XPath.selectText("//name", dom);
			if (name == null) {
				return badRequest("Missing parameter [name]");
			} else {
				return ok("Hello " + name);
			}
		}
	}
}

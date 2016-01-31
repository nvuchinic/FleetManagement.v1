package controllers;

//import java.io.*;
//import java.net.*;
//import com.sun.net.httpserver.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.List;

import com.avaje.ebean.Model.Finder;

import java.sql.Date;

import models.*;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.export;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import views.*;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
//import TextColumnBuilder;
import play.mvc.Controller;
import play.mvc.Result;
import play.twirl.api.Content;
import views.html.listAllVehicles;

public class ReportController extends Controller {

	public Result listAllVehicles() {
		System.out.println("///////////// BROJ VOZILA :" +Vehicle.listOfVehicles().size());
if(Vehicle.listOfVehicles().size()==0){
	return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
}
else{
		//JasperReportBuilder myReport = null;
		Connection connection = null;
		
		//for connection to H2 database
//		try {
//			Class.forName("org.h2.Driver");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		try {
//			connection = DriverManager.getConnection("jdbc:h2:mem:play");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
		//for connection to MySQL database
		String pass = null,user=null;
		String stackTrace=null;
		String stackTrace2=null;
		try {
			 user = "root";
			pass = "globalgps";
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			stackTrace=e.getStackTrace().toString();
			return ok(stackTrace);
			
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:mysql://192.168.0.3:3306/tiimiss_fleet_management",user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
			stackTrace2=e.getStackTrace().toString();
			return ok(stackTrace2);
		}
		//
		net.sf.dynamicreports.report.builder.column.TextColumnBuilder<String> nameColumn = col
				.column("Name", "name", type.stringType())
				.setHorizontalAlignment(HorizontalAlignment.CENTER);
		;

		net.sf.dynamicreports.report.builder.column.TextColumnBuilder<Long> idColumn = col
				.column("ID", "id", type.longType()).setHorizontalAlignment(
						HorizontalAlignment.CENTER);
		;
		net.sf.dynamicreports.report.builder.column.TextColumnBuilder<Integer> rowNumberColumn = col
				.reportRowNumberColumn("No.")

				// sets the fixed width of a column, width = 2 * character width

				.setFixedColumns(2)

				.setHorizontalAlignment(HorizontalAlignment.CENTER);

		net.sf.dynamicreports.report.builder.style.StyleBuilder boldStyle = stl
				.style().bold();

		net.sf.dynamicreports.report.builder.style.StyleBuilder boldCenteredStyle = stl
				.style(boldStyle)

				.setHorizontalAlignment(HorizontalAlignment.CENTER);

		net.sf.dynamicreports.report.builder.style.StyleBuilder columnTitleStyle = stl
				.style(boldCenteredStyle)

				.setBorder(stl.pen1Point())

				.setBackgroundColor(Color.LIGHT_GRAY);

		JasperHtmlExporterBuilder htmlExporter = export
				.htmlExporter("/home/nera/workspace/FleetManagement.v1/public/reports/vehicleReport22.jrhtml");
		// String jasperPrint;
		JasperReportBuilder report = DynamicReports.report();

		try {
			report().setColumnTitleStyle(columnTitleStyle)
					.highlightDetailEvenRows()
					.columns(rowNumberColumn, idColumn, nameColumn)

					.title(cmp.text("All Vehicles").setStyle(boldCenteredStyle))
					// shows report title

					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// shows number of page at page footer

					.setDataSource("SELECT * FROM vehicle", connection)
					//.toHtml(htmlExporter)

					.show(false);

		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return redirect("/");
	}
	}
	
	public Result getMainReportView() {
		if(Vehicle.listOfVehicles().size()==0){
			return ok(listAllVehicles.render(Vehicle.listOfVehicles()));
		}
		List<Vehicle> allVehicles=new ArrayList<Vehicle>();
		allVehicles=Vehicle.listOfVehicles();
		System.out.println("/////////// BROJ VOZILA:"+allVehicles.size());
		return ok(maintenanceReportView.render(allVehicles));
	}
	
	public Result thisCarMaintenances() {
		List<Maintenance> thisMaintenances=new ArrayList<Maintenance>();
		List<Maintenance> allMaintenances=new ArrayList<Maintenance>();
		allMaintenances=Maintenance.listOfMaintenances();
		DynamicForm dynamicMaintenanceReportForm = Form.form().bindFromRequest();
		String selectedVehicle = null;
		String startDateString=null;
		String endDateString = null;
		java.util.Date utilDate1 = new java.util.Date();
		java.util.Date utilDate2 = new java.util.Date();
		String pattern="yyyy-MM-dd";
		Date startDate=null;
		Date endDate=null;
		
		java.util.Date utilDate = new java.util.Date();

		try {
			SimpleDateFormat format=null, format2=null;
			String vid = dynamicMaintenanceReportForm.get("vid");
			Vehicle v = Vehicle.findByVid(vid);
			startDateString = dynamicMaintenanceReportForm.get("startDate");
			System.out.println("PRINTING ISSUE DATE:"+startDateString);
			if(startDateString.contains("-")){
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			else{
				format = new SimpleDateFormat("MM/dd/yyy");
			}
			System.out.println("PRINTING START DATE: "+startDateString);
			utilDate1 = format.parse(startDateString);
			startDate = new java.sql.Date(utilDate1.getTime());
			endDateString = dynamicMaintenanceReportForm.get("startDate");
			System.out.println("PRINTING ISSUE DATE:"+endDateString);
			if(endDateString.contains("-")){
				format = new SimpleDateFormat("yyyy-MM-dd");
			}
			else{
				format = new SimpleDateFormat("MM/dd/yyy");
			}
			System.out.println("PRINTING START DATE: "+endDateString);
			utilDate2 = format.parse(endDateString);
			startDate = new java.sql.Date(utilDate2.getTime());
			for(Maintenance m:allMaintenances){
				if(m.mDate.after(startDate) && m.mDate.before(endDate)){
					thisMaintenances.add(m);
				}
			}
			return ok(listAllMaintenances.render(thisMaintenances));
			//return ok(listAllMaintenances.render(thisMaintenances));
		} catch (Exception e) {
			flash("error", "Error at Maintenance Report ");
			Logger.error("Maintenance Report error: " + e.getMessage(), e);
			return redirect("/");
		}
	}
	
}

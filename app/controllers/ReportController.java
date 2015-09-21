package controllers;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import models.*;
import reports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.oasis.StyleBuilder;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ReportController extends Controller{

	public Result listAllVehicles() throws IOException{
		InputStream is =null;
		JasperReportBuilder myReport=null;
		File vehRepFile=null;
		  Connection connection = null;
		  try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	         try {
				connection = DriverManager.getConnection("jdbc:h2:mem:play");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	vehRepFile=new File("/home/nera/workspace/FleetManagement.v1/public/reports/","vehicleReport.html");
		 net.sf.dynamicreports.report.builder.style.StyleBuilder boldStyle         = stl.style().bold(); 
		  
		  net.sf.dynamicreports.report.builder.style.StyleBuilder boldCenteredStyle = stl.style(boldStyle)
		  
		                                      .setHorizontalAlignment(HorizontalAlignment.CENTER);
		  
		  net.sf.dynamicreports.report.builder.style.StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
		  
		                                      .setBorder(stl.pen1Point())
		  
		                                      .setBackgroundColor(Color.LIGHT_GRAY);

	      try {
	    	  JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("/home/nera/workspace/FleetManagement.v1/public/reports/vehicleReport4.jrhtml");
	    	 // String jasperPrint;
	    	  

	    	  report()
	           .columns(

	            col.column("Vehicle ID",       "id",      type.longType()),

	            col.column("Vehicle Name",   "name",  type.stringType()))

	          //  col.column("Type", "typev", type.stringType()))

	            .title(cmp.text("All Vehicles"))//shows report title

	            .pageFooter(cmp.pageXofY())//shows number of page at page footer

	           .setDataSource("SELECT * FROM vehicle", connection)
	                .toHtml(htmlExporter)

	         .setColumnTitleStyle(columnTitleStyle) 
	         .highlightDetailEvenRows() 
	         .title(cmp.text("Getting started").setStyle(boldCenteredStyle))
	         .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
	         .show();
	    	  
	      } catch (DRException e) {

	          e.printStackTrace();

	       }
	     // JasperPrint reportPrint=null;
		
			try {
				is= new FileInputStream("/home/nera/workspace/FleetManagement.v1/public/reports/vehicleReport4.jrhtml");
				JasperViewer.viewReport(is,false,false);
			} catch (JRException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	      
	     // reportViewer.setVisible(true);
		is.close();
		return ok();
	}
}

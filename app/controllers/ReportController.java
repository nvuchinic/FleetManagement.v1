package controllers;

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JRExporter;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import net.sf.jasperreports.engine.export.JRHtmlExporter;
//import net.sf.jasperreports.engine.export.oasis.StyleBuilder;
//import net.sf.jasperreports.engine.xml.JRXmlLoader;
//import net.sf.jasperreports.view.JasperViewer;
//import org.apache.poi.ss.usermodel*;
//import com.lowagie.text.*;
import com.avaje.ebean.Model.Finder;
//import net.sf.jasperreports.engine.JRException;
//import net.sf.jasperreports.engine.JasperCompileManager;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.export.JRPdfExporter;
//import net.sf.jasperreports.export.ExporterInput;
//import net.sf.jasperreports.export.OutputStreamExporterOutput;
//import net.sf.jasperreports.export.SimpleExporterInput;
//import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
//import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ReportController extends Controller{

	public Result listAllVehicles() {
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
	vehRepFile=new File("/home/nera/workspace/FleetManagement.v1/public/reports/","vehicleReport22.html");
		 net.sf.dynamicreports.report.builder.style.StyleBuilder boldStyle         = stl.style().bold(); 
		  
		  net.sf.dynamicreports.report.builder.style.StyleBuilder boldCenteredStyle = stl.style(boldStyle)
		  
		                                      .setHorizontalAlignment(HorizontalAlignment.CENTER);
		  
		  net.sf.dynamicreports.report.builder.style.StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
		  
		                                      .setBorder(stl.pen1Point())
		  
		                                      .setBackgroundColor(Color.LIGHT_GRAY);

	       JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("/home/nera/workspace/FleetManagement.v1/public/reports/vehicleReport22.jrhtml");
		 // String jasperPrint;
		  JasperReportBuilder report = DynamicReports.report();

		  try {
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
			.show(false);

		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
			    //show the report

			  
		return redirect("/");
	}
		
		

//		public Result listAllVehicles() {
//			String reportSrcFile = "/home/nera/workspace/FleetManagement.v1/public/reports/VehicleReport.jrxml";
//	         JasperReport jasperReport=null;
//	        // First, compile jrxml file.
//	        try {
//				 jasperReport =    JasperCompileManager.compileReport(reportSrcFile);
//			} catch (JRException e) {
//				System.out.println("GRESKA KOD KOMPAJLIRANJA JRXML FILE-A: "+e);
//				e.printStackTrace();
//			}
//	        Connection conn = null;
//			  try {
//				Class.forName("org.h2.Driver");
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	
//		         try {
//					conn= DriverManager.getConnection("jdbc:h2:mem:play");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		         Map<String, Object> parameters = new HashMap<String, Object>();
//		         
//		         try {
//					JasperPrint print = JasperFillManager.fillReport(jasperReport,
//					         parameters, conn);
//				} catch (JRException e) {
//					System.out.println("GRESKA KOD PUNJENJA IZVJESTAJA PODACIMA"+e +"/////////////////");
//					e.printStackTrace();
//				}
//		 
//			return ok();
//		}
}


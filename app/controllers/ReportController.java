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

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.component.TextFieldBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
//import TextColumnBuilder;

import com.avaje.ebean.Model.Finder;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class ReportController extends Controller{

	public Result listAllVehicles() {
		//InputStream is =null;
		JasperReportBuilder myReport=null;
		//File vehRepFile=null;
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
	         net.sf.dynamicreports.report.builder.column.TextColumnBuilder<String>     nameColumn      = col.column("Name",       "name",      type.stringType())
	        		 .setHorizontalAlignment(HorizontalAlignment.CENTER);;
	         
	         net.sf.dynamicreports.report.builder.column.TextColumnBuilder<Long>   idColumn  = col.column("ID",   "id",  type.longType())
	        		 .setHorizontalAlignment(HorizontalAlignment.CENTER);;
	         net.sf.dynamicreports.report.builder.column.TextColumnBuilder<Integer>    rowNumberColumn = col.reportRowNumberColumn("No.")
	        		 
	        		                                                           //sets the fixed width of a column, width = 2 * character width
	        		 
	        		                                                          .setFixedColumns(2)
	        		 
	        		                                                          .setHorizontalAlignment(HorizontalAlignment.CENTER);

	         
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
			 .setColumnTitleStyle(columnTitleStyle) 
			 .highlightDetailEvenRows()
			   .columns(
					   rowNumberColumn, idColumn, nameColumn)

					.title(cmp.text("All Vehicles"))//shows report title

			    .pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))//shows number of page at page footer

			   .setDataSource("SELECT * FROM vehicle", connection)
			        .toHtml(htmlExporter)

			 
			.show(false);

		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
			 return redirect("/");
	}
		
		


}


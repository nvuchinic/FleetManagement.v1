package reports;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.export.oasis.StyleBuilder;


public class VehicleReport {
  private Connection connection;

 

   public VehicleReport() {
      try {

         Class.forName("org.h2.Driver");

         connection = DriverManager.getConnection("jdbc:h2:mem:play");
         build();
      } catch (SQLException e) {
         e.printStackTrace();

      } catch (ClassNotFoundException e) {

         e.printStackTrace();

      }

   }

  private void build() {
	  net.sf.dynamicreports.report.builder.style.StyleBuilder boldStyle         = stl.style().bold(); 
	  
	  net.sf.dynamicreports.report.builder.style.StyleBuilder boldCenteredStyle = stl.style(boldStyle)
	  
	                                      .setHorizontalAlignment(HorizontalAlignment.CENTER);
	  
	  net.sf.dynamicreports.report.builder.style.StyleBuilder columnTitleStyle  = stl.style(boldCenteredStyle)
	  
	                                      .setBorder(stl.pen1Point())
	  
	                                      .setBackgroundColor(Color.LIGHT_GRAY);

      try {
    	  JasperHtmlExporterBuilder htmlExporter = export.htmlExporter("/home/nera/workspace/FleetManagement.v1/public/reports/vehicleReport.html");

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

   }

 public static void main(String[] args) {

      new VehicleReport();

   }

}
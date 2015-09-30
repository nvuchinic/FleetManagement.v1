package controllers;

//import java.io.*;
//import java.net.*;
//import com.sun.net.httpserver.*;
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

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;
//import TextColumnBuilder;
import play.mvc.Controller;
import play.mvc.Result;

public class ReportController extends Controller {

	public Result listAllVehicles() {

		JasperReportBuilder myReport = null;
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
					.toHtml(htmlExporter)

					.show(false);

		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return redirect("/");
	}

}

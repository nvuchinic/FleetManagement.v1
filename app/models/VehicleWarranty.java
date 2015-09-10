package models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.avaje.ebean.Model;
import com.avaje.ebean.Model.Finder;

@Entity
@Table(name = "vehicleWarranty")
public class VehicleWarranty extends Model {

	@Id
	public long id;

	@OneToOne
	public Vehicle vehicle;

	public String warrantyDetails;

	public Date commencementWarrantyDate;

	public Date expiryWarrantyDate;

	public String warrantyKmLimit;

	public String vehicleCardNumber;

	public String typeOfCard;

	public Date cardIssueDate;

	/**
	 * @param vehicle
	 * @param warrantyDetails
	 * @param commencementWarrantyDate
	 * @param expiryWarrantyDate
	 * @param warrantyKmLimit
	 * @param vehicleCardNumber
	 * @param typeOfCard
	 * @param cardIssueDate
	 */
	public VehicleWarranty(Vehicle vehicle, String warrantyDetails,
			Date commencementWarrantyDate, Date expiryWarrantyDate,
			String warrantyKmLimit, String vehicleCardNumber,
			String typeOfCard, Date cardIssueDate) {
		super();
		this.vehicle = vehicle;
		this.warrantyDetails = warrantyDetails;
		this.commencementWarrantyDate = commencementWarrantyDate;
		this.expiryWarrantyDate = expiryWarrantyDate;
		this.warrantyKmLimit = warrantyKmLimit;
		this.vehicleCardNumber = vehicleCardNumber;
		this.typeOfCard = typeOfCard;
		this.cardIssueDate = cardIssueDate;
	}

	public static long createVehicleWarranty(Vehicle vehicle,
			String warrantyDetails, Date commencementWarrantyDate,
			Date expiryWarrantyDate, String warrantyKmLimit,
			String vehicleCardNumber, String typeOfCard, Date cardIssueDate) {
		VehicleWarranty vw = new VehicleWarranty(vehicle, warrantyDetails,
				commencementWarrantyDate, expiryWarrantyDate, warrantyKmLimit,
				vehicleCardNumber, typeOfCard, cardIssueDate);
		vw.save();
		return vw.id;
	}
	
	/**
	 * Finder for VehicleWarranty object
	 */
	public static Finder<Long, VehicleWarranty> find = new Finder<Long, VehicleWarranty>(
			VehicleWarranty.class);
}

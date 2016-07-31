package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_BESS_APPLICANTDETAILS database table.
 * 
 */
@Entity
@Table(name="EX_BESS_APPLICANTDETAILS")
@NamedQuery(name="ExBessApplicantdetail.findAll", query="SELECT e FROM ExBessApplicantdetail e")
public class ExBessApplicantdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String applicantCompany;

	@Lob
	private String applicantFirstName;

	@Lob
	private String applicantLastName;

	@Lob
	private String country;

	@Lob
	private String NMINumber;

	@Lob
	private String phoneNumber;

	@Lob
	private String postCode;

	@Lob
	private String primaryMeterNumber;

	@Lob
	private String relationShipToProperty;

	@Lob
	private String relationShipToPropertyText;

	@Lob
	private String state;

	@Lob
	private String streetName;

	@Lob
	private String streetNumber;

	@Lob
	private String suburb;

	@Lob
	private String unitNumber;

	public ExBessApplicantdetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getApplicantCompany() {
		return this.applicantCompany;
	}

	public void setApplicantCompany(String applicantCompany) {
		this.applicantCompany = applicantCompany;
	}

	public String getApplicantFirstName() {
		return this.applicantFirstName;
	}

	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}

	public String getApplicantLastName() {
		return this.applicantLastName;
	}

	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNMINumber() {
		return this.NMINumber;
	}

	public void setNMINumber(String NMINumber) {
		this.NMINumber = NMINumber;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPostCode() {
		return this.postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getPrimaryMeterNumber() {
		return this.primaryMeterNumber;
	}

	public void setPrimaryMeterNumber(String primaryMeterNumber) {
		this.primaryMeterNumber = primaryMeterNumber;
	}

	public String getRelationShipToProperty() {
		return this.relationShipToProperty;
	}

	public void setRelationShipToProperty(String relationShipToProperty) {
		this.relationShipToProperty = relationShipToProperty;
	}

	public String getRelationShipToPropertyText() {
		return this.relationShipToPropertyText;
	}

	public void setRelationShipToPropertyText(String relationShipToPropertyText) {
		this.relationShipToPropertyText = relationShipToPropertyText;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getStreetNumber() {
		return this.streetNumber;
	}

	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}

	public String getSuburb() {
		return this.suburb;
	}

	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}

	public String getUnitNumber() {
		return this.unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

}
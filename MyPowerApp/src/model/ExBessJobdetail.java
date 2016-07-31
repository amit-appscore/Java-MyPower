package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_BESS_JOBDETAILS database table.
 * 
 */
@Entity
@Table(name="EX_BESS_JOBDETAILS")
@NamedQuery(name="ExBessJobdetail.findAll", query="SELECT e FROM ExBessJobdetail e")
public class ExBessJobdetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String additionalNotes;

	private int applicantID;

	@Lob
	private String batteryBarcode;

	private int batteryEqID;

	@Lob
	private byte[] batteryNamePlateImage;

	@Lob
	private String DCRSerialNumber;

	@Lob
	private String installationDate;

	@Lob
	private byte[] installationImage;

	private int installerID;

	@Lob
	private String inverterBarcode;

	private int inverterEqID;

	@Lob
	private byte[] inverterNamePlateImage;

	@Lob
	private String KWCapacityPV;

	@Lob
	private String latitude;

	@Lob
	private byte[] lineDiagramImage;

	@Lob
	private String lineDiagramType;

	@Lob
	private String locationOfBatteryInstallation;

	@Lob
	private String longitude;

	@Lob
	private String maxCapacity;

	@Lob
	private String maxChargeRate;

	@Lob
	private String maxDischargeRate;

	@Lob
	private String networkType;

	public ExBessJobdetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdditionalNotes() {
		return this.additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public int getApplicantID() {
		return this.applicantID;
	}

	public void setApplicantID(int applicantID) {
		this.applicantID = applicantID;
	}

	public String getBatteryBarcode() {
		return this.batteryBarcode;
	}

	public void setBatteryBarcode(String batteryBarcode) {
		this.batteryBarcode = batteryBarcode;
	}

	public int getBatteryEqID() {
		return this.batteryEqID;
	}

	public void setBatteryEqID(int batteryEqID) {
		this.batteryEqID = batteryEqID;
	}

	public byte[] getBatteryNamePlateImage() {
		return this.batteryNamePlateImage;
	}

	public void setBatteryNamePlateImage(byte[] batteryNamePlateImage) {
		this.batteryNamePlateImage = batteryNamePlateImage;
	}

	public String getDCRSerialNumber() {
		return this.DCRSerialNumber;
	}

	public void setDCRSerialNumber(String DCRSerialNumber) {
		this.DCRSerialNumber = DCRSerialNumber;
	}

	public String getInstallationDate() {
		return this.installationDate;
	}

	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}

	public byte[] getInstallationImage() {
		return this.installationImage;
	}

	public void setInstallationImage(byte[] installationImage) {
		this.installationImage = installationImage;
	}

	public int getInstallerID() {
		return this.installerID;
	}

	public void setInstallerID(int installerID) {
		this.installerID = installerID;
	}

	public String getInverterBarcode() {
		return this.inverterBarcode;
	}

	public void setInverterBarcode(String inverterBarcode) {
		this.inverterBarcode = inverterBarcode;
	}

	public int getInverterEqID() {
		return this.inverterEqID;
	}

	public void setInverterEqID(int inverterEqID) {
		this.inverterEqID = inverterEqID;
	}

	public byte[] getInverterNamePlateImage() {
		return this.inverterNamePlateImage;
	}

	public void setInverterNamePlateImage(byte[] inverterNamePlateImage) {
		this.inverterNamePlateImage = inverterNamePlateImage;
	}

	public String getKWCapacityPV() {
		return this.KWCapacityPV;
	}

	public void setKWCapacityPV(String KWCapacityPV) {
		this.KWCapacityPV = KWCapacityPV;
	}

	public String getLatitude() {
		return this.latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public byte[] getLineDiagramImage() {
		return this.lineDiagramImage;
	}

	public void setLineDiagramImage(byte[] lineDiagramImage) {
		this.lineDiagramImage = lineDiagramImage;
	}

	public String getLineDiagramType() {
		return this.lineDiagramType;
	}

	public void setLineDiagramType(String lineDiagramType) {
		this.lineDiagramType = lineDiagramType;
	}

	public String getLocationOfBatteryInstallation() {
		return this.locationOfBatteryInstallation;
	}

	public void setLocationOfBatteryInstallation(String locationOfBatteryInstallation) {
		this.locationOfBatteryInstallation = locationOfBatteryInstallation;
	}

	public String getLongitude() {
		return this.longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMaxCapacity() {
		return this.maxCapacity;
	}

	public void setMaxCapacity(String maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public String getMaxChargeRate() {
		return this.maxChargeRate;
	}

	public void setMaxChargeRate(String maxChargeRate) {
		this.maxChargeRate = maxChargeRate;
	}

	public String getMaxDischargeRate() {
		return this.maxDischargeRate;
	}

	public void setMaxDischargeRate(String maxDischargeRate) {
		this.maxDischargeRate = maxDischargeRate;
	}

	public String getNetworkType() {
		return this.networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

}
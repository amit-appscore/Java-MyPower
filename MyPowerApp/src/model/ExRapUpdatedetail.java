package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ex_rap_updatedetails database table.
 * 
 */
@Entity
@Table(name="ex_rap_updatedetails")
@NamedQuery(name="ExRapUpdatedetail.findAll", query="SELECT e FROM ExRapUpdatedetail e")
public class ExRapUpdatedetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String dangerToMeterReadersRadioBtnValue;

	private String dogAtYourPropertyRadioBtnValue;

	private String dogBreed;

	private String dogDetailsAddAccessInformation;

	private String dogPermanentLocationRadioBtnValue;

	private String meterAccessAddAccessInformation;

	private String meterAccessEnergexLockRadioBtnValue;

	private String meterAccessPinCode;

	private Timestamp timeStamp;

	private String updateDetailsMapAddress;

	private String updateDetailsMapLatitude;

	private String updateDetailsMapLongitude;

	private String updateDetailsType;

	@Lob
	private String userName;

	public ExRapUpdatedetail() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDangerToMeterReadersRadioBtnValue() {
		return this.dangerToMeterReadersRadioBtnValue;
	}

	public void setDangerToMeterReadersRadioBtnValue(String dangerToMeterReadersRadioBtnValue) {
		this.dangerToMeterReadersRadioBtnValue = dangerToMeterReadersRadioBtnValue;
	}

	public String getDogAtYourPropertyRadioBtnValue() {
		return this.dogAtYourPropertyRadioBtnValue;
	}

	public void setDogAtYourPropertyRadioBtnValue(String dogAtYourPropertyRadioBtnValue) {
		this.dogAtYourPropertyRadioBtnValue = dogAtYourPropertyRadioBtnValue;
	}

	public String getDogBreed() {
		return this.dogBreed;
	}

	public void setDogBreed(String dogBreed) {
		this.dogBreed = dogBreed;
	}

	public String getDogDetailsAddAccessInformation() {
		return this.dogDetailsAddAccessInformation;
	}

	public void setDogDetailsAddAccessInformation(String dogDetailsAddAccessInformation) {
		this.dogDetailsAddAccessInformation = dogDetailsAddAccessInformation;
	}

	public String getDogPermanentLocationRadioBtnValue() {
		return this.dogPermanentLocationRadioBtnValue;
	}

	public void setDogPermanentLocationRadioBtnValue(String dogPermanentLocationRadioBtnValue) {
		this.dogPermanentLocationRadioBtnValue = dogPermanentLocationRadioBtnValue;
	}

	public String getMeterAccessAddAccessInformation() {
		return this.meterAccessAddAccessInformation;
	}

	public void setMeterAccessAddAccessInformation(String meterAccessAddAccessInformation) {
		this.meterAccessAddAccessInformation = meterAccessAddAccessInformation;
	}

	public String getMeterAccessEnergexLockRadioBtnValue() {
		return this.meterAccessEnergexLockRadioBtnValue;
	}

	public void setMeterAccessEnergexLockRadioBtnValue(String meterAccessEnergexLockRadioBtnValue) {
		this.meterAccessEnergexLockRadioBtnValue = meterAccessEnergexLockRadioBtnValue;
	}

	public String getMeterAccessPinCode() {
		return this.meterAccessPinCode;
	}

	public void setMeterAccessPinCode(String meterAccessPinCode) {
		this.meterAccessPinCode = meterAccessPinCode;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUpdateDetailsMapAddress() {
		return this.updateDetailsMapAddress;
	}

	public void setUpdateDetailsMapAddress(String updateDetailsMapAddress) {
		this.updateDetailsMapAddress = updateDetailsMapAddress;
	}

	public String getUpdateDetailsMapLatitude() {
		return this.updateDetailsMapLatitude;
	}

	public void setUpdateDetailsMapLatitude(String updateDetailsMapLatitude) {
		this.updateDetailsMapLatitude = updateDetailsMapLatitude;
	}

	public String getUpdateDetailsMapLongitude() {
		return this.updateDetailsMapLongitude;
	}

	public void setUpdateDetailsMapLongitude(String updateDetailsMapLongitude) {
		this.updateDetailsMapLongitude = updateDetailsMapLongitude;
	}

	public String getUpdateDetailsType() {
		return this.updateDetailsType;
	}

	public void setUpdateDetailsType(String updateDetailsType) {
		this.updateDetailsType = updateDetailsType;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
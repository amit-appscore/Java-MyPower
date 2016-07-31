package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ex_rap_faultystreetlights database table.
 * 
 */
@Entity
@Table(name="ex_rap_faultystreetlights")
@NamedQuery(name="ExRapFaultystreetlight.findAll", query="SELECT e FROM ExRapFaultystreetlight e")
public class ExRapFaultystreetlight implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String faultyStreetLightType;

	private String fslightsAddInfo;

	private String fslightsAffectedRadioBtnValue;

	@Lob
	private byte[] fslightsFirstPhoto;

	private String fslightsMapAddress;

	private String fslightsMapLatitude;

	private String fslightsMapLongitude;

	@Lob
	private byte[] fslightsSecondPhoto;

	@Lob
	private byte[] fslightsThirdPhoto;

	private Timestamp timeStamp;

	@Lob
	private String userName;

	public ExRapFaultystreetlight() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFaultyStreetLightType() {
		return this.faultyStreetLightType;
	}

	public void setFaultyStreetLightType(String faultyStreetLightType) {
		this.faultyStreetLightType = faultyStreetLightType;
	}

	public String getFslightsAddInfo() {
		return this.fslightsAddInfo;
	}

	public void setFslightsAddInfo(String fslightsAddInfo) {
		this.fslightsAddInfo = fslightsAddInfo;
	}

	public String getFslightsAffectedRadioBtnValue() {
		return this.fslightsAffectedRadioBtnValue;
	}

	public void setFslightsAffectedRadioBtnValue(String fslightsAffectedRadioBtnValue) {
		this.fslightsAffectedRadioBtnValue = fslightsAffectedRadioBtnValue;
	}

	public byte[] getFslightsFirstPhoto() {
		return this.fslightsFirstPhoto;
	}

	public void setFslightsFirstPhoto(byte[] fslightsFirstPhoto) {
		this.fslightsFirstPhoto = fslightsFirstPhoto;
	}

	public String getFslightsMapAddress() {
		return this.fslightsMapAddress;
	}

	public void setFslightsMapAddress(String fslightsMapAddress) {
		this.fslightsMapAddress = fslightsMapAddress;
	}

	public String getFslightsMapLatitude() {
		return this.fslightsMapLatitude;
	}

	public void setFslightsMapLatitude(String fslightsMapLatitude) {
		this.fslightsMapLatitude = fslightsMapLatitude;
	}

	public String getFslightsMapLongitude() {
		return this.fslightsMapLongitude;
	}

	public void setFslightsMapLongitude(String fslightsMapLongitude) {
		this.fslightsMapLongitude = fslightsMapLongitude;
	}

	public byte[] getFslightsSecondPhoto() {
		return this.fslightsSecondPhoto;
	}

	public void setFslightsSecondPhoto(byte[] fslightsSecondPhoto) {
		this.fslightsSecondPhoto = fslightsSecondPhoto;
	}

	public byte[] getFslightsThirdPhoto() {
		return this.fslightsThirdPhoto;
	}

	public void setFslightsThirdPhoto(byte[] fslightsThirdPhoto) {
		this.fslightsThirdPhoto = fslightsThirdPhoto;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
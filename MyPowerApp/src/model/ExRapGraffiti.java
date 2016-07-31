package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ex_rap_graffiti database table.
 * 
 */
@Entity
@Table(name="ex_rap_graffiti")
@NamedQuery(name="ExRapGraffiti.findAll", query="SELECT e FROM ExRapGraffiti e")
public class ExRapGraffiti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String graffitiAddInfo;

	@Lob
	private byte[] graffitiFirstPhoto;

	private String graffitiLabelRadioBtnValue;

	private String graffitiMapAddress;

	private String graffitiMapLatitude;

	private String graffitiMapLongitude;

	@Lob
	private byte[] graffitiSecondPhoto;

	@Lob
	private byte[] graffitiThirdPhoto;

	@Lob
	private String graffitiType;

	private Timestamp timeStamp;

	@Lob
	private String userName;

	public ExRapGraffiti() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGraffitiAddInfo() {
		return this.graffitiAddInfo;
	}

	public void setGraffitiAddInfo(String graffitiAddInfo) {
		this.graffitiAddInfo = graffitiAddInfo;
	}

	public byte[] getGraffitiFirstPhoto() {
		return this.graffitiFirstPhoto;
	}

	public void setGraffitiFirstPhoto(byte[] graffitiFirstPhoto) {
		this.graffitiFirstPhoto = graffitiFirstPhoto;
	}

	public String getGraffitiLabelRadioBtnValue() {
		return this.graffitiLabelRadioBtnValue;
	}

	public void setGraffitiLabelRadioBtnValue(String graffitiLabelRadioBtnValue) {
		this.graffitiLabelRadioBtnValue = graffitiLabelRadioBtnValue;
	}

	public String getGraffitiMapAddress() {
		return this.graffitiMapAddress;
	}

	public void setGraffitiMapAddress(String graffitiMapAddress) {
		this.graffitiMapAddress = graffitiMapAddress;
	}

	public String getGraffitiMapLatitude() {
		return this.graffitiMapLatitude;
	}

	public void setGraffitiMapLatitude(String graffitiMapLatitude) {
		this.graffitiMapLatitude = graffitiMapLatitude;
	}

	public String getGraffitiMapLongitude() {
		return this.graffitiMapLongitude;
	}

	public void setGraffitiMapLongitude(String graffitiMapLongitude) {
		this.graffitiMapLongitude = graffitiMapLongitude;
	}

	public byte[] getGraffitiSecondPhoto() {
		return this.graffitiSecondPhoto;
	}

	public void setGraffitiSecondPhoto(byte[] graffitiSecondPhoto) {
		this.graffitiSecondPhoto = graffitiSecondPhoto;
	}

	public byte[] getGraffitiThirdPhoto() {
		return this.graffitiThirdPhoto;
	}

	public void setGraffitiThirdPhoto(byte[] graffitiThirdPhoto) {
		this.graffitiThirdPhoto = graffitiThirdPhoto;
	}

	public String getGraffitiType() {
		return this.graffitiType;
	}

	public void setGraffitiType(String graffitiType) {
		this.graffitiType = graffitiType;
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
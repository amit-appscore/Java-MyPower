package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ex_rap_treetrimming database table.
 * 
 */
@Entity
@Table(name="ex_rap_treetrimming")
@NamedQuery(name="ExRapTreetrimming.findAll", query="SELECT e FROM ExRapTreetrimming e")
public class ExRapTreetrimming implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private Timestamp timeStamp;

	private String treeTrimmingAddAccessInfo;

	@Lob
	private byte[] treeTrimmingFirstPhoto;

	private String treeTrimmingMapAddress;

	private String treeTrimmingMapLatitude;

	private String treeTrimmingMapLongitude;

	@Lob
	private byte[] treeTrimmingSecondPhoto;

	@Lob
	private byte[] treeTrimmingThirdPhoto;

	@Lob
	private String treeTrimmingType;

	private String ttcloseToPowerlinesAffectedRadioBtnValue;

	private String ttcloseToPowerlinesDistanceRadioBtnValue;

	@Lob
	private String userName;

	public ExRapTreetrimming() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTreeTrimmingAddAccessInfo() {
		return this.treeTrimmingAddAccessInfo;
	}

	public void setTreeTrimmingAddAccessInfo(String treeTrimmingAddAccessInfo) {
		this.treeTrimmingAddAccessInfo = treeTrimmingAddAccessInfo;
	}

	public byte[] getTreeTrimmingFirstPhoto() {
		return this.treeTrimmingFirstPhoto;
	}

	public void setTreeTrimmingFirstPhoto(byte[] treeTrimmingFirstPhoto) {
		this.treeTrimmingFirstPhoto = treeTrimmingFirstPhoto;
	}

	public String getTreeTrimmingMapAddress() {
		return this.treeTrimmingMapAddress;
	}

	public void setTreeTrimmingMapAddress(String treeTrimmingMapAddress) {
		this.treeTrimmingMapAddress = treeTrimmingMapAddress;
	}

	public String getTreeTrimmingMapLatitude() {
		return this.treeTrimmingMapLatitude;
	}

	public void setTreeTrimmingMapLatitude(String treeTrimmingMapLatitude) {
		this.treeTrimmingMapLatitude = treeTrimmingMapLatitude;
	}

	public String getTreeTrimmingMapLongitude() {
		return this.treeTrimmingMapLongitude;
	}

	public void setTreeTrimmingMapLongitude(String treeTrimmingMapLongitude) {
		this.treeTrimmingMapLongitude = treeTrimmingMapLongitude;
	}

	public byte[] getTreeTrimmingSecondPhoto() {
		return this.treeTrimmingSecondPhoto;
	}

	public void setTreeTrimmingSecondPhoto(byte[] treeTrimmingSecondPhoto) {
		this.treeTrimmingSecondPhoto = treeTrimmingSecondPhoto;
	}

	public byte[] getTreeTrimmingThirdPhoto() {
		return this.treeTrimmingThirdPhoto;
	}

	public void setTreeTrimmingThirdPhoto(byte[] treeTrimmingThirdPhoto) {
		this.treeTrimmingThirdPhoto = treeTrimmingThirdPhoto;
	}

	public String getTreeTrimmingType() {
		return this.treeTrimmingType;
	}

	public void setTreeTrimmingType(String treeTrimmingType) {
		this.treeTrimmingType = treeTrimmingType;
	}

	public String getTtcloseToPowerlinesAffectedRadioBtnValue() {
		return this.ttcloseToPowerlinesAffectedRadioBtnValue;
	}

	public void setTtcloseToPowerlinesAffectedRadioBtnValue(String ttcloseToPowerlinesAffectedRadioBtnValue) {
		this.ttcloseToPowerlinesAffectedRadioBtnValue = ttcloseToPowerlinesAffectedRadioBtnValue;
	}

	public String getTtcloseToPowerlinesDistanceRadioBtnValue() {
		return this.ttcloseToPowerlinesDistanceRadioBtnValue;
	}

	public void setTtcloseToPowerlinesDistanceRadioBtnValue(String ttcloseToPowerlinesDistanceRadioBtnValue) {
		this.ttcloseToPowerlinesDistanceRadioBtnValue = ttcloseToPowerlinesDistanceRadioBtnValue;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
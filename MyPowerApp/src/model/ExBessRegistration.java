package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the EX_BESS_REGISTRATIONS database table.
 * 
 */
@Entity
@Table(name="EX_BESS_REGISTRATIONS")
@NamedQuery(name="ExBessRegistration.findAll", query="SELECT e FROM ExBessRegistration e")
public class ExBessRegistration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String emailID;

	@Lob
	private String firstName;

	@Lob
	private String installerLicenseNumber;

	@Lob
	private String lastName;

	@Lob
	private String password;

	@Lob
	private String passwordChangedTime;

	@Lob
	private String phoneNumber;

	public ExBessRegistration() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmailID() {
		return this.emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getInstallerLicenseNumber() {
		return this.installerLicenseNumber;
	}

	public void setInstallerLicenseNumber(String installerLicenseNumber) {
		this.installerLicenseNumber = installerLicenseNumber;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordChangedTime() {
		return this.passwordChangedTime;
	}

	public void setPasswordChangedTime(String passwordChangedTime) {
		this.passwordChangedTime = passwordChangedTime;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
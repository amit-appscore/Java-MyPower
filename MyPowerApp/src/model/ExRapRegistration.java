package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the ex_rap_registrations database table.
 * 
 */
@Entity
@Table(name="EX_RAP_REGISTRATIONS")
@NamedQuery(name="ExRapRegistration.findAll", query="SELECT e FROM ExRapRegistration e")
public class ExRapRegistration implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private String emailID;

	@Lob
	private String firstName;

	@Lob
	private String lastName;

	@Lob
	private String loginAvailable;

	@Lob
	private String password;


	@Lob
	private String phoneNumber;

	@Lob
	private String postCode;

	@Lob
	private String socialLogin;

	private Timestamp timeStamp;

	@Lob
	private String userName;

	public ExRapRegistration() {
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

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLoginAvailable() {
		return this.loginAvailable;
	}

	public void setLoginAvailable(String loginAvailable) {
		this.loginAvailable = loginAvailable;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getSocialLogin() {
		return this.socialLogin;
	}

	public void setSocialLogin(String socialLogin) {
		this.socialLogin = socialLogin;
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
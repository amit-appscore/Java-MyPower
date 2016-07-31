package com.sparq.MyPowerApp;

import javax.persistence.Id;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URLDecoder;

import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.io.*;
import java.net.*;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import com.sparq.configuration.ConfigReader;
import com.sparq.emailfunctions.EmailFunction;
import com.sparq.utils.RapEmail;
import com.sparq.utils.UnZip;

import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Path("/")
public class RESTService {
	
	private class SMTPAuthenticator extends Authenticator {

		private PasswordAuthentication authentication;

		public SMTPAuthenticator(String login, String password) {
			authentication = new PasswordAuthentication(login, password);
		}

		protected PasswordAuthentication getPasswordAuthentication() {
			return authentication;
		}
	}

	private Logger log = Logger.getLogger(RESTService.class.getName());
	private final String PERSISTENCE_UNIT_NAME = "BatteryAppService";
	private EntityManagerFactory factory;

	private boolean debugState = false;

	@SuppressWarnings("static-access")
	@POST
	@Path("/turnOnDebug")
	public javax.ws.rs.core.Response turnOnDebug(boolean state) {
		String result = "";

		debugState = state;

		return javax.ws.rs.core.Response.ok().entity(result).build();
	}

	
	@SuppressWarnings("static-access")
	@POST
	@Path("/processRAPAppPostData")
	public javax.ws.rs.core.Response processRAPAppPostData(String inputPostJson) {
		
		try {
			String result = "";
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();

			//log.info("Inside processPostInput::processPostInput::::"+ inputPostJson);
			log.info("Inside processRapAppPostInput::processPostInput:::: Start");
			// Tokenize
			StringTokenizer st = new StringTokenizer(inputPostJson, "=");
			String key = st.nextToken();
			String str = st.nextToken();
			//log.info("Tokenizer::::" + key + " Val" + str);
			// URL decoding
			// String inputJson = new String(str.getBytes("UTF-8"),"ASCII");

			URLDecoder urlDecoder = new URLDecoder();
			String inputJson = urlDecoder.decode(str, "ASCII");
			//log.info("URL decoding::::" + inputJson);
			log.info("Inside processRapAppPostInput::processPostInput:::: Decoding Start");
			JSONObject jsonInput = null;
			try {
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Image"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				log.info("processRapAppPostInput::valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				log.info("processRapAppPostInput::Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}

			String operation = "";

			try {
				operation = (String) jsonInput.get("Operation");

				log.info("Inside processRapAppPostInput::Operation ******* "
						+ operation);
			} catch (JSONException e) {
				log.info("Inside processRapAppPostInput::JSONException"
						+ e.getStackTrace());
			} catch (Exception e) {
				log.info("Inside processRapAppPostInput::Exception"
						+ e.getStackTrace());
			} finally {
				log.info("Inside processRapAppPostInput::processRules");
			}

			switch (operation) {
			case "SocialLogin":
				log.info("Inside processRapAppPostInput:: Social Networking Registration Start");
				String usrName = (String) jsonInput.get("UserName");
				String firstName = (String) jsonInput.get("FirstName");
				String lastName = (String) jsonInput.get("LastName");
				String emailAddress = (String) jsonInput.get("EmailAddress");
				//String password = (String) jsonInput.get("Password");
				String postCode = (String) jsonInput
						.get("PostCode");
				String phoneNumber = (String) jsonInput.get("MobileNumber");
				String socialLogin = (String) jsonInput.get("socialLogin");
				String loginAvailable = (String) jsonInput.get("LoginAvailable");
				String timeStamp = (String) jsonInput.get("timeStamp");

				// If User Name Empty Success Code - 0
				if(usrName.isEmpty()){
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"Empty UserName.\"}");
					break;
				}
					

				Query qQ = null;
				try {

					qQ = em.createQuery("SELECT u FROM ExRapRegistration u where u.userName =:userName");
					qQ.setParameter("userName", usrName);
					List<ExRapRegistration> userList1 = qQ.getResultList();
					if (userList1.size() > 0) {
						
						for (ExRapRegistration user1 : userList1) {
							// log.info(user.getFirstName());
							result = new String(
									"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"userName\": \""
											+ user1.getUserName()
											+ "\", \"password\":\"" + "" + "\", \"firstName\":\""
											+ user1.getFirstName()
											+ "\", \"lastName\":\""
											+ user1.getLastName()
											+ "\", \"postCode\":\""
											+ user1.getPostCode()										
											+ "\", \"emailAddress\":\""
											+ user1.getEmailID()
											+ "\", \"mobileNumber\":\""
											+ user1.getPhoneNumber()
											+ "\"}}");
							break;
						}
						//result = new String(
						//		"{\"successCode\":\"1\",\"successMessage\":\"Social Networking Login Successfully done!!.\",\"result\":{\"UserName\":\""+ usrName +"\",}}");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processRapAppPostInput::Exception"
							+ e.getMessage());
				}
				// Create new user

				ExRapRegistration user = new ExRapRegistration();
				user.setUserName(usrName);
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmailID(emailAddress);
				//user.setPassword(password);
				user.setPostCode(postCode);
				user.setPhoneNumber(phoneNumber);
				user.setSocialLogin(socialLogin);
				user.setLoginAvailable(loginAvailable);//timeStamp
				user.setTimeStamp(Timestamp.valueOf(timeStamp));
				em.getTransaction().begin();
				em.persist(user);
				em.getTransaction().commit();

				em.close();

				result = new String(
						"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"userName\": \""
								+ user.getUserName()
								+ "\", \"password\":\"" + "" + "\", \"firstName\":\""
								+ user.getFirstName()
								+ "\", \"lastName\":\""
								+ user.getLastName()
								+ "\", \"postCode\":\""
								+ user.getPostCode()										
								+ "\", \"emailAddress\":\""
								+ user.getEmailID()
								+ "\", \"mobileNumber\":\""
								+ user.getPhoneNumber()
								+ "\"}}");
				//result = new String(
				//		"{\"successCode\":\"1\",\"successMessage\":\"Social Networking Registration Successfully Done\", \"result\":{\"userName\": \"" + usrName +"\"}}");
				log.info("Inside processRapAppPostInput:: Social Networking Registration End");
				break;

			case "Login":
				log.info("Inside processRapAppPostInput:: Login Start");
				usrName = (String) jsonInput.get("UserName");
				String password = (String) jsonInput.get("Password");
				// String timeStampForPasswordChange =
				// (String)jsonInput.get("timeStampForPasswordChange");

				String queryString = "SELECT u FROM ExRapRegistration u where u.userName =:userName and u.password =:password";
				Query q = em.createQuery(queryString);
				q.setParameter("userName", usrName);
				q.setParameter("password", password);
				List<ExRapRegistration> userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExRapRegistration user1 : userList) {
						// log.info(user.getFirstName());
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"userName\": \""
										+ user1.getUserName()
										+ "\", \"password\":\"****\", \"firstName\":\""
										+ user1.getFirstName()
										+ "\", \"lastName\":\""
										+ user1.getLastName()
										+ "\", \"postCode\":\""
										+ user1.getPostCode()										
										+ "\", \"emailAddress\":\""
										+ user1.getEmailID()
										+ "\", \"mobileNumber\":\""
										+ user1.getPhoneNumber()
										+ "\"}}");
						break;
					}

					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"Login Failed due to invalid username / password\"}");
				}
				log.info("Inside processRapAppPostInput:: Login End");
				break;
			case "Registration":
				log.info("Inside processRapAppPostInput:: Registration Start");
				usrName = (String) jsonInput.get("UserName");
				firstName = (String) jsonInput.get("FirstName");
				lastName = (String) jsonInput.get("LastName");
				emailAddress = (String) jsonInput.get("EmailAddress");
				password = (String) jsonInput.get("Password");
				postCode = (String) jsonInput
						.get("PostCode");
				phoneNumber = (String) jsonInput.get("MobileNumber");
				timeStamp = (String) jsonInput.get("timeStamp");

				boolean user_error = false;	
				boolean email_error = false;
				try {

					q = em.createQuery("SELECT u FROM ExRapRegistration u where u.userName =:userName");
					q.setParameter("userName", usrName);
					List<ExRapRegistration> userList1 = q.getResultList();
					if (userList1.size() > 0) {
						result = new String(
								"{\"successCode\":\"3\",\"successMessage\":\"Registration failed due to User name already registered.\"}");
						//break;
						user_error = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processRapAppPostInput::Exception"
							+ e.getMessage());
				}
				// If Email ID already exists - 4
				try {
					q = em.createQuery("SELECT u FROM ExRapRegistration u where u.emailID =:emailAddress");
					q.setParameter("emailAddress", emailAddress);
					List<ExRapRegistration> userList2 = q.getResultList();
					if (userList2.size() > 0) {
						result = new String(
								"{\"successCode\":\"4\",\"successMessage\":\"Registration failed due to Email Address already in use.\"}");
						//break;
						email_error = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processRapAppPostInput::Exception"
							+ e.getMessage());
				}
				if(user_error && email_error){
					result = new String(
							"{\"successCode\":\"4\",\"successMessage\":\"Registration failed due to Username and Email Address already in use.\"}");
					break;
				}
				
				if(email_error || user_error) break;
				
				// Create new user

				ExRapRegistration user2 = new ExRapRegistration();
				user2.setUserName(usrName);
				user2.setFirstName(firstName);
				user2.setLastName(lastName);
				user2.setEmailID(emailAddress);
				user2.setPassword(password);
				user2.setPostCode(postCode);
				user2.setPhoneNumber(phoneNumber);
				user2.setSocialLogin("");
				user2.setLoginAvailable("");
				user2.setTimeStamp(Timestamp.valueOf(timeStamp));
				em.getTransaction().begin();
				em.persist(user2);
				em.getTransaction().commit();

				em.close();

				result = new String(
						"{\"successCode\":\"1\",\"successMessage\":\"Registration Successfully Done\", \"result\":{\"userName\": \"" + usrName +"\"}}");
				log.info("Inside processRapAppPostInput:: Registration End");
				break;
				
			case "Logout":
				log.info("Inside processRapAppPostInput:: Logout Start");
				
				usrName = (String) jsonInput.get("UserName");
				emailAddress = (String) jsonInput.get("EmailAddress");
				socialLogin = (String) jsonInput.get("SocialLogin");
				
				try {

					q = em.createQuery("SELECT u FROM ExRapRegistration u where u.userName =:userName and u.emailID =:emailAddress and u.socialLogin =:socialLogin");
					q.setParameter("userName", usrName);
					q.setParameter("emailAddress", emailAddress);
					q.setParameter("socialLogin", socialLogin);
					List<ExRapRegistration> userList1 = q.getResultList();
					if (userList1.size() > 0) {
						ExRapRegistration u1 = userList1.get(0);
						em.getTransaction().begin();
						em.remove(u1);
						em.getTransaction().commit();
						em.close();
						/*Query q4 = em.createQuery("delete FROM ExRapRegistration where userName =:userName");
						q4.setParameter("userName", usrName);
						q4.executeUpdate();*/
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Logout successfull!\"}");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processRapAppPostInput::Exception"
							+ e.getMessage());
				}
				
				log.info("Inside processRapAppPostInput:: Logout End");
				break;
				
			case "ForgotPin":
				log.info("Inside processRapAppPostInput:: ForgotPin Start");
				String usrEmailAddr = (String) jsonInput.get("EmailAddress");
				queryString = "SELECT u FROM ExRapRegistration u where u.emailID =:usrEmailAddr";
				q = em.createQuery(queryString);
				q.setParameter("usrEmailAddr", usrEmailAddr);
				userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExRapRegistration user1 : userList) {
						
						//Email the message ...
						RapEmail rapEmail = new RapEmail();
						File input = null;
						
						if(ConfigReader.OsUtils.isWindows())
								input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Forgotinfo\\PinContent.htm");
						else
							input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Forgotinfo/PinContent.htm");
						
						String pinBody = FileUtils.readFileToString(input);
						pinBody = pinBody.replace("xxxxxx", user1.getPassword());
						
						// Email the user the Password...
						rapEmail.generateAndSendEmail(new String[]{user1.getEmailID()},"Forgotten PIN request",new String[]{pinBody});
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Password email sent Successfully\"}");
						break;
					}
					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"invalid username\"}");
				}
				log.info("Inside processRapAppPostInput:: Forgot Password End");
				break;
			case "ForgotUserName":
				log.info("Inside processRapAppPostInput:: ForgotUsername Start");
				usrEmailAddr = (String) jsonInput.get("EmailAddress");
				queryString = "SELECT u FROM ExRapRegistration u where u.emailID =:userEmailID";
				q = em.createQuery(queryString);
				q.setParameter("userEmailID", usrEmailAddr);
				userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExRapRegistration user1 : userList) {
						//Email the message ...
						RapEmail rapEmail = new RapEmail();
						// Email the user the the UserName...
						File input = null;
						
						if(ConfigReader.OsUtils.isWindows())
								input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Forgotinfo\\UsernameContent.htm");
						else
							input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Forgotinfo/UsernameContent.htm");
						
						String userNameBody = FileUtils.readFileToString(input);
						userNameBody = userNameBody.replace("xxxxxx", user1.getUserName());
						rapEmail.generateAndSendEmail(new String[]{user1.getEmailID()},"Forgotten Username request",new String[]{userNameBody});
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Username email sent Successfully\"}");
						break;
					}
					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"invalid email address\"}");
				}
				log.info("Inside processRapAppPostInput:: Forgot Username End");
				break;
			case "ForgotPassword":
				log.info("Inside processRapAppPostInput:: ForgetPassword Start");
				usrName = (String) jsonInput.get("UserName");
				String newPassword = (String) jsonInput.get("NewPassword");
				String confirmPassword = (String) jsonInput
						.get("ConfirmPassword");
				String timeStampForPasswordChange = (String) jsonInput
						.get("TimeStampForPasswordChange");

				if (!newPassword.equals(confirmPassword)) {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"New password and confirm password values did not match\"}");
					break;
				}

				queryString = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
				q = em.createQuery(queryString);
				q.setParameter("userName", usrName);
				userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExRapRegistration user1 : userList) {
						if(!newPassword.isEmpty()){
							user1.setPassword(newPassword);
							em.getTransaction().begin();
							em.merge(user1);
							em.getTransaction().commit();
							em.close();
						}
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Password changed Successfully\"}");
						break;
					}
					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"invalid username\"}");
				}
				log.info("Inside processRapAppPostInput:: Forget Password End");
				break;
			case "Settings":
				log.info("Inside processRapAppPostInput:: Settings Start");
				usrName = (String) jsonInput.get("UserName");
				//String newUsrName = (String) jsonInput.get("NewUserName");
				firstName = (String) jsonInput.get("FirstName");
				lastName = (String) jsonInput.get("LastName");
				emailAddress = (String) jsonInput.get("EmailAddress");
				password = (String) jsonInput.get("Password");
				postCode = (String) jsonInput
						.get("PostCode");
				phoneNumber = (String) jsonInput.get("MobileNumber");
				timeStamp = (String) jsonInput.get("timeStamp");

				queryString = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
				q = em.createQuery(queryString);
				q.setParameter("userName", usrName);
				userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExRapRegistration user1 : userList) {
						//user1.setUserName(newUsrName);
						user1.setFirstName(firstName);
						user1.setLastName(lastName);
						user1.setEmailID(emailAddress);
						if(!password.isEmpty())
							user1.setPassword(password);
						user1.setPostCode(postCode);
						user1.setPhoneNumber(phoneNumber);
						user1.setTimeStamp(Timestamp.valueOf(timeStamp));
						em.getTransaction().begin();
						em.merge(user1);
						em.getTransaction().commit();
						em.close();
						
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Settings changed successfully\",\"result\":{\"userName\": \""
										+ user1.getUserName()
										+ "\", \"password\":\"" + "" + "\", \"firstName\":\""
										+ user1.getFirstName()
										+ "\", \"lastName\":\""
										+ user1.getLastName()
										+ "\", \"postCode\":\""
										+ user1.getPostCode()										
										+ "\", \"emailAddress\":\""
										+ user1.getEmailID()
										+ "\", \"mobileNumber\":\""
										+ user1.getPhoneNumber()
										+ "\"}}");
						//result = new String(
						//		"{\"successCode\":\"1\",\"successMessage\":\"Settings changed Successfully\"}");
						break;
					}
					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"invalid username\"}");
				}
				log.info("Inside processRapAppPostInput:: Settings End");
				break;
			default:
				log.info("Inside processPostInput:: Default Start");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Invalid Operation. Supported Operations are [Login,Registration,ForgotPassword and RAP]\"}");
				log.info("Inside processPostInput:: Default End");
				break;
			}

			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	@SuppressWarnings("static-access")
	@POST
	@Path("/postRapAppBackgroundImage")
	public javax.ws.rs.core.Response postBackgroundImage(String inputPostJson) {
		
		try {
			String result = "";
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();
			
			
			log.info("Inside postBackgroundImage:::: Start");
			// Tokenize
			/*StringTokenizer st = new StringTokenizer(inputPostJson, "=");
			String key = st.nextToken();
			String str = st.nextToken();*/
			
			//URLDecoder urlDecoder = new URLDecoder();
			//String inputJson = urlDecoder.decode(str, "ASCII");
			String inputJson = inputPostJson;
			//log.info("URL decoding::::" + inputJson);
			log.info("Inside postBackgroundImage::postBackgroundImage:::: Decoding Start");
			
			
			JSONObject jsonInput = null;
			try {
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Image"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				log.info("valid json string::::");
			} catch (JSONException e) {
				
				log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}

			String operation = "";

			try {
				operation = (String) jsonInput.get("Operation");

				log.info("Inside postBackgroundImage::Operation ******* "
						+ operation);
			} catch (JSONException e) {
				log.info("Inside postBackgroundImage::JSONException"
						+ e.getStackTrace());
			} catch (Exception e) {
				log.info("Inside postBackgroundImage::Exception"
						+ e.getStackTrace());
			} finally {
				log.info("Inside postBackgroundImage::processRules");
			}
			
			switch (operation) {
			case "SetRapAppBkgImage":
				log.info("Inside postBackgroundImage:: Get Image Start");

				String imageData = (String) jsonInput.get("ImageData");
				
				// Decode data
				byte[] decodedData = Base64.decodeBase64(imageData);
				
				String queryString = "SELECT u FROM ExRapBackgroundimage u where u.id =:imageId ";
				Query q = em.createQuery(queryString);
				q.setParameter("imageId", 1);
				List<ExRapBackgroundimage> images = q.getResultList();
				if (images.size() > 0) {
					for (ExRapBackgroundimage imageRow : images) {
						imageRow.setBackgroundImage(imageData.getBytes());
						imageRow.setVersion(imageRow.getVersion()+1);
						em.getTransaction().begin();
						em.merge(imageRow);
						em.getTransaction().commit();
						em.close();
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"ImageVersion\": \""
										+ imageRow.getVersion()
										+ "\"}}");
						break;
					}

					break;
				}
				default:
					break;
			}
			log.info("Inside postBackgroundImage:::: End" + result);
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
	@SuppressWarnings("static-access")
	@GET
	@Path("/testDB")	
	public Response dbTestClient(){
		java.sql.Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Properties props = new Properties();
		//System.setProperty("javax.net.ssl.keyStore","D:/CTO/BatteryApp/DBScript/keystore");
		System.setProperty("javax.net.ssl.keyStore","usr/share/tomcat7/webapps/BatteryAppService/keystore");
		System.setProperty("javax.net.ssl.keyStorePassword","abcdef");
		//System.setProperty("javax.net.ssl.trustStore","D:/CTO/BatteryApp/DBScript/truststore");
		System.setProperty("javax.net.ssl.trustStore","usr/share/tomcat7/webapps/BatteryAppService/truststore");
		System.setProperty("javax.net.ssl.trustStorePassword","abcdef");
		props.setProperty("ssl", "true");
		props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		//props.setProperty("user", "sparquser");
		//props.setProperty("password", "xgt6mnWx235ngrbad");
		props.setProperty("user", "root");
		props.setProperty("password", "wipro@123");
		try {
			//conn = DriverManager.getConnection("jdbc:mysql://54.66.188.15:3306/sparq?verifyServerCertificate=false&useSSL=true&requireSSL=true&", props);
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sparq", props);
			String retStr = "Connection Status" + conn.toString();
			
			conn.close();
			return javax.ws.rs.core.Response.ok().entity(retStr).build();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@SuppressWarnings("static-access")
	@GET
	@Path("/getRapAppBackgroundImage")
	public javax.ws.rs.core.Response getBackgroundImage() {
		byte[] imgData = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String result = "";
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();

			log.info("Inside getBackgroundImage:::: Start");
			String queryString = "SELECT u FROM ExRapBackgroundimage u where u.id =:imageId ";
			Query q = em.createQuery(queryString);
			q.setParameter("imageId", 1);
			List<ExRapBackgroundimage> images = q.getResultList();
			if (images.size() > 0) {
				for (ExRapBackgroundimage imageRow : images) {
					imgData = imageRow.getBackgroundImage() ;
					
					log.info("IMAGEROW DATA ....." + imageRow.getBackgroundImage() + "LENGTH::::" + imageRow.getBackgroundImage().length + "IMG DATA" + imgData );
					/*result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"ImageVersion\": \""
									+ imageRow.getVersion()
									+ "\","
									+ "\"" + "ImageData:" 
									+ "\"" + is.
									+ "\"}}");*/
					
					
					break;
				}

			}
			log.info("Inside getBackgroundImage:::: End");
			////"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"ImageData\": \"dksgdksgfsfwqoiwqgouwqgiuqwiqfqfqbf\"}}
			String strImgData = "{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"ImageData\": \"" + new String(imgData) + "\"}}";
					
			//return javax.ws.rs.core.Response.ok().entity(imgData).build();
			return javax.ws.rs.core.Response.ok().entity(strImgData).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}


	@SuppressWarnings("static-access")
	@GET
	@Path("/getVersionOfRapAppBackgroundImage")
	public javax.ws.rs.core.Response getVersionOfBkgImage() {
		
		try {
			String result = "";
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();

			log.info("Inside getVersionOfBkgImage:::: Start");
			
			String queryString = "SELECT u FROM ExRapBackgroundimage u where u.id =:imageId ";
			Query q = em.createQuery(queryString);
			q.setParameter("imageId", 1);
			List<ExRapBackgroundimage> images = q.getResultList();
			if (images.size() > 0) {
				for (ExRapBackgroundimage imageRow : images) {
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"ImageVersion\": \""
									+ imageRow.getVersion()
									+ "\"}}");
					break;
				}

			}
			log.info("Inside getVersionOfBkgImage:::: End..." + result);
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

		
	/**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }

	@GET
	@Path("/processInput/{inputJson}")
	public javax.ws.rs.core.Response processGetData(
			@PathParam("inputJson") String inputJson) {

		try {

			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
			EntityManager em = factory.createEntityManager();

			log.info("Inside processPostInput::processInput" + inputJson);

			JSONObject jsonInput = new JSONObject(inputJson);

			String operation = "";

			try {
				operation = (String) jsonInput.get("Operation");

				log.info("Inside processPostInput::Operation ******* "
						+ operation);
			} catch (JSONException e) {
				log.info("Inside processPostInput::JSONException"
						+ e.getStackTrace());
			} catch (Exception e) {
				log.info("Inside processPostInput::Exception"
						+ e.getStackTrace());
			} finally {
				log.info("Inside processPostInput::processRules");
			}
			String result = "";

			switch (operation) {
			case "Login":
				String userName = (String) jsonInput.get("UserName");
				String password = (String) jsonInput.get("Password");
				// String timeStampForPasswordChange =
				// (String)jsonInput.get("timeStampForPasswordChange");

				String queryString = "SELECT u FROM ExBessRegistration u where u.emailID =:userName and u.password =:password";
				Query q = em.createQuery(queryString);
				q.setParameter("userName", userName);
				q.setParameter("password", password);
				List<ExBessRegistration> userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExBessRegistration user : userList) {
						// log.info(user.getFirstName());
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"userName\": \""
										+ user.getEmailID()
										+ "\", \"password\":\"****\", \"firstName\":\""
										+ user.getFirstName()
										+ "\", \"lastName\":\""
										+ user.getLastName()
										+ "\", \"installersElectricalLicenceNumber\":\""
										+ user.getInstallerLicenseNumber()
										+ "\"}}");
						break;
					}

					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"Login Failed due to invalid username / password\"}");
				}

				break;
			case "Registration":

				String firstName = (String) jsonInput.get("FirstName");
				String lastName = (String) jsonInput.get("LastName");
				String emailAddress = (String) jsonInput.get("EmailAddress");
				password = (String) jsonInput.get("Password");
				String installersElectricalLicenceNumber = (String) jsonInput
						.get("InstallersElectricalLicenceNumber");
				String phoneNumber = (String) jsonInput.get("PhoneNumber");

				// If User Name exists Success Code - 2

				// If Lincense Number already exists - 3
				try {

					q = em.createQuery("SELECT u FROM ExBessRegistration u where u.installerLicenseNumber =:number");
					q.setParameter("number", installersElectricalLicenceNumber);
					userList = q.getResultList();
					if (userList.size() > 0) {
						result = new String(
								"{\"successCode\":\"3\",\"successMessage\":\"Registration failed due to Installer License number already in use.\"}");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processPostInput::Exception"
							+ e.getMessage());
				}
				// If Email ID already exists - 4
				try {
					q = em.createQuery("SELECT u FROM ExBessRegistration u where u.emailID =:emailAddress");
					q.setParameter("emailAddress", emailAddress);
					userList = q.getResultList();
					if (userList.size() > 0) {
						result = new String(
								"{\"successCode\":\"4\",\"successMessage\":\"Registration failed due to Email Address already in use.\"}");
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
					log.info("Inside processPostInput::Exception"
							+ e.getMessage());
				}
				// Create new user

				ExBessRegistration user = new ExBessRegistration();
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmailID(emailAddress);
				user.setPassword(password);
				user.setInstallerLicenseNumber(installersElectricalLicenceNumber);
				user.setPhoneNumber(phoneNumber);
				em.getTransaction().begin();
				em.persist(user);
				em.getTransaction().commit();

				em.close();

				result = new String(
						"{\"successCode\":\"1\",\"successMessage\":\"Registration Successfully Done\"}");

				break;
			case "ForgotPassword":
				userName = (String) jsonInput.get("UserName");
				String newPassword = (String) jsonInput.get("NewPassword");
				String confirmPassword = (String) jsonInput
						.get("ConfirmPassword");
				String timeStampForPasswordChange = (String) jsonInput
						.get("TimeStampForPasswordChange");

				if (!newPassword.equals(confirmPassword)) {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"New password and confirm password values did not match\"}");
					break;
				}

				queryString = "SELECT u FROM ExBessRegistration u where u.emailID =:userName";
				q = em.createQuery(queryString);
				q.setParameter("userName", userName);
				userList = q.getResultList();
				if (userList.size() > 0) {

					for (ExBessRegistration user1 : userList) {
						user1.setPassword(newPassword);
						user1.setPasswordChangedTime(timeStampForPasswordChange);
						em.getTransaction().begin();
						em.merge(user1);
						em.getTransaction().commit();
						em.close();
						result = new String(
								"{\"successCode\":\"1\",\"successMessage\":\"Password changed Successfully\"}");
						break;
					}
					break;
				} else {
					result = new String(
							"{\"successCode\":\"0\",\"successMessage\":\"invalid username\"}");
				}

				break;
			case "SetupJob":

				userName = (String) jsonInput.get("UserName");
				int userID = 0;
				int applicantID = 0;

				queryString = "SELECT u FROM ExBessRegistration u where u.emailID =:userName";
				q = em.createQuery(queryString);
				q.setParameter("userName", userName);
				userList = q.getResultList();
				if (userList.size() > 0) {
					for (ExBessRegistration user2 : userList) {
						userID = user2.getId();
						break;
					}

				} else {
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Job could not be created. Invalid Installer ID\"}");
					break;
				}

				String nmiNumber = (String) jsonInput.get("NMINumber");
				String unitNumber = (String) jsonInput.get("UnitNumber");
				String streetNumber = (String) jsonInput.get("StreetNumber");
				String streetName = (String) jsonInput.get("StreetName");
				String suburb = (String) jsonInput.get("Suburb");
				String postCode = (String) jsonInput.get("PostCode");
				String primaryMeterNumber = (String) jsonInput
						.get("PrimaryMeterNumber");
				String applicantFirstName = (String) jsonInput
						.get("ApplicantFirstName");
				String applicantLastName = (String) jsonInput
						.get("ApplicantLastName");
				String applicantCompany = (String) jsonInput
						.get("ApplicantCompany");
				String relationshipToProperty = (String) jsonInput
						.get("RelationshipToProperty");
				String relationshipToPropertyText = (String) jsonInput
						.get("RelationshipToPropertyText");

				// Battery and Inverter already installed at this Property with
				// Primary Meter number :***

				queryString = "SELECT u FROM ExBessApplicantdetail u where u.primaryMeterNumber =:meterNumber";
				q = em.createQuery(queryString);
				q.setParameter("meterNumber", primaryMeterNumber);
				userList = q.getResultList();
				if (userList.size() > 0) {
					result = new String(
							"{\"successCode\":\"2\",\"successMessage\":\"Battery and Inverter already installed at this Property with Primary Meter number "
									+ primaryMeterNumber + "\"}");
					break;
				}

				queryString = "SELECT u FROM ExBessApplicantdetail u where u.NMINumber =:NMINumber";
				q = em.createQuery(queryString);
				q.setParameter("NMINumber", nmiNumber);
				userList = q.getResultList();
				if (userList.size() > 0) {
					result = new String(
							"{\"successCode\":\"3\",\"successMessage\":\"Battery and Inverter already installed at this Property with NMI number "
									+ nmiNumber + "\"}");
					break;
				}

				// Create new user

				ExBessApplicantdetail applicant = new ExBessApplicantdetail();
				applicant.setApplicantCompany(applicantCompany);
				applicant.setApplicantFirstName(applicantFirstName);
				applicant.setApplicantLastName(applicantLastName);
				// applicant.setCountry(country);
				applicant.setNMINumber(nmiNumber);
				// applicant.setPhoneNumber(phoneNumber);
				applicant.setPostCode(postCode);
				applicant.setPrimaryMeterNumber(primaryMeterNumber);
				applicant.setRelationShipToProperty(relationshipToProperty);
				applicant
						.setRelationShipToPropertyText(relationshipToPropertyText);
				// applicant.setState(state);
				applicant.setStreetName(streetName);
				applicant.setStreetNumber(streetNumber);
				applicant.setSuburb(suburb);
				applicant.setUnitNumber(unitNumber);
				em.getTransaction().begin();
				em.persist(applicant);
				em.getTransaction().commit();
				em.close();

				// Get Applicant's ID
				queryString = "SELECT u FROM ExBessApplicantdetail u where u.primaryMeterNumber =:meterNumber";
				q = em.createQuery(queryString);
				q.setParameter("meterNumber", primaryMeterNumber);
				List<ExBessApplicantdetail> userList2 = q.getResultList();
				if (userList2.size() > 0) {

					for (ExBessApplicantdetail user2 : userList2) {
						applicantID = user2.getId();
						break;
					}
				}

				String locationOfBatteryInstallation = (String) jsonInput
						.get("LocationOfBatteryInstallation");
				String batteryBarcode = (String) jsonInput
						.get("BatteryBarcode");
				String batteryNameplateImage = (String) jsonInput
						.get("BatteryNameplateImage");
				String maxChargeRate = (String) jsonInput.get("MaxChargeRate");
				String storageCapacity = (String) jsonInput
						.get("StorageCapacity");
				String dcrSerialNumber = (String) jsonInput
						.get("DCRSerialNumber");
				String InverterBarcode = (String) jsonInput
						.get("InverterBarcode");
				String inverterNameplateImage = (String) jsonInput
						.get("InverterNameplateImage");
				String installationDate = (String) jsonInput
						.get("InstallationDate");
				String additionalNotes = (String) jsonInput
						.get("AdditionalNotes");
				String inverterImage = (String) jsonInput.get("InverterImage");
				String lineDiagramType = (String) jsonInput
						.get("LineDiagramType");
				String lineDiagramImage = (String) jsonInput
						.get("LineDiagramImage");
				String networkType = (String) jsonInput.get("NetworkType");
				String installersFirstName = (String) jsonInput
						.get("InstallersFirstName");
				String installersLastName = (String) jsonInput
						.get("InstallersLastName");
				installersElectricalLicenceNumber = (String) jsonInput
						.get("InstallersElectricalLicenceNumber");
				String installersUsername = (String) jsonInput
						.get("InstallersUsername");
				String BatteryBrand = (String) jsonInput.get("BatteryBrand");
				String BatteryModel = (String) jsonInput.get("BatteryModel");
				String InverterBrand = (String) jsonInput.get("InverterBrand");
				String InverterModel = (String) jsonInput.get("InverterModel");

				ExBessJobdetail job = new ExBessJobdetail();
				job.setInstallerID(userID);
				job.setApplicantID(applicantID);
				job.setAdditionalNotes(additionalNotes);
				job.setBatteryBarcode(batteryBarcode);

				job.setBatteryNamePlateImage(batteryNameplateImage.getBytes());
				job.setDCRSerialNumber(dcrSerialNumber);
				job.setInstallationDate(installationDate);
				job.setInverterBarcode(InverterBarcode);

				job.setInverterNamePlateImage(inverterNameplateImage.getBytes());
				job.setLineDiagramImage(lineDiagramImage.getBytes());
				job.setLineDiagramType(lineDiagramType);
				job.setLocationOfBatteryInstallation(locationOfBatteryInstallation);
				job.setMaxChargeRate(maxChargeRate);
				job.setNetworkType(networkType);
				job.setMaxCapacity(storageCapacity);

				// Send Email ... in another thread
				// generateAndSendEmail();

				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Form submitted successfully\"}");

				break;
			default:
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Invalid Operation. Supported Operations are [Login,Registration,ForgotPassword and SetupJob]\"}");
				break;
			}

			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	@POST
	@Path("/delete")
	public javax.ws.rs.core.Response deleteUser(String inputJson) {

		try {
			log.debug("Deleting user details");
			// NLUProcess nlu = new NLUProcess();
			// nlu.deleteUser(inputJson);
			return javax.ws.rs.core.Response.ok().entity("User Deleted")
					.build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in deleting the user" + e.getMessage());
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getTypefromID(String type){
		String result = "";
		
		String inputJson = "{\"dogDetails\":\"Update your dog details\",\"meterAccess\":\"Update your meter access\",\"ttCloseToPowerlines\":\"Trees close to powerlines\",\"ttDebrisRemoval\":\"Debris removal\",\"streetLightsOut\":\"Flickering or street lights out\",\"24HrsADay\":\"Lights on 24 hours a day\",\"lightsDamaged\":\"Dirty, broken or damaged lights\",\"poleDamaged\":\"Street light pole damaged/leaning\",\"otherStreetLightIssues\":\"Other street light issues\",\"eBuildings\":\"On Energex buildings\",\"ePublicProperty\":\"On Energex equipment on public property\",\"ePrivateProperty\":\"On Energex equipment on private property\"}";
		
		JSONObject jsonInput = null;
		try {
			log.info("valid json string::::" + inputJson);
			jsonInput = new JSONObject(inputJson);
			
			result = (String) jsonInput.get(type);
			log.info(type+":"+jsonInput.get(type));
		    	
	
			log.info("valid json string::::");
		} catch (JSONException e) {
			//log.info("invalid json string::::" + inputJson);
			log.info("Invalid json string::::");
			
		}
		
		return result;
	}

	@SuppressWarnings("static-access")
	@POST
	@Path("/rapAppTreeTrimming")
	public javax.ws.rs.core.Response rapAppTreetrimming(String inputPostJson) {
		log.info("Inside rapAppTreeTrimming:::: Start..." );
		// Tokenize
		StringTokenizer st = new StringTokenizer(inputPostJson, "=");
		String key = st.nextToken();
		String str = st.nextToken();
		/*while(st.hasMoreTokens()){
			str += st.nextToken();
		}*/
		//log.info("Tokenizer::::" + key + " Val" + str);
		// URL decoding
		// String inputJson = new String(str.getBytes("UTF-8"),"ASCII");

		URLDecoder urlDecoder = new URLDecoder();
		String inputJson="";
		try {
			inputJson = urlDecoder.decode(str, "ASCII");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String result = "";
			//***********************************Process Input ****************************
			JSONObject jsonInput = null;
			try {
				log.info("valid json string::::" + inputJson);
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Photo"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
		
				log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();
			
			ExRapTreetrimming treeTrimming = new ExRapTreetrimming();
			treeTrimming = JsonToJavaHelper.convertTreetrimming(inputJson);
			//Current Time
			//java.util.Date date = new java.util.Date();
			//treeTrimming.setTimeStamp(new Timestamp(date.getTime()));
			
			// Retrieve the User ...
			
			String queryString2 = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
			Query q2 = em.createQuery(queryString2);
			q2.setParameter("userName", treeTrimming.getUserName());
			List<ExRapRegistration> userList = q2.getResultList();
			if (userList.size() > 0) {
				for (ExRapRegistration user : userList) {
					
					// Change the user ID
					//treeTrimming.setUserId(user.getId());
					// Save the record ....
					em.getTransaction().begin();
					em.persist(treeTrimming);
					em.getTransaction().commit();

					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					/*String sTreeTrimFP = new String(treeTrimming.getTreeTrimmingFirstPhoto());
					String sTreeTrimSP = new String(treeTrimming.getTreeTrimmingSecondPhoto());
					String sTreeTrimTP = new String(treeTrimming.getTreeTrimmingThirdPhoto());*/
					
					String sTreeTrimFP = (String) jsonInput.get("treeTrimmingFirstPhoto");
					String sTreeTrimSP = (String) jsonInput.get("treeTrimmingSecondPhoto");  
					String sTreeTrimTP = (String) jsonInput.get("treeTrimmingThirdPhoto");
					
					if(!sTreeTrimFP.isEmpty()){
						log.info("First Photo Byte Size:::"+treeTrimming.getTreeTrimmingFirstPhoto().length);
						log.info("First Photo Size:::" + sTreeTrimFP.length());
						firstPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sTreeTrimFP,user.getId()+"FirstPhoto.png");
						firstPhotoMBPSize = "FirstPhoto (" + Integer.toString(sTreeTrimFP.length()/1024) + "kB)";
					}
					
					if(!sTreeTrimSP.isEmpty()){
						secondPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sTreeTrimSP,user.getId()+"SecondPhoto.png");
						secondPhotoMBPSize = "SecondPhoto (" + Integer.toString(sTreeTrimSP.length()/1024) + "kB)";
					}
					
					if(!sTreeTrimTP.isEmpty()){
						thirdPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sTreeTrimTP,user.getId()+"ThirdPhoto.png");
						thirdPhotoMBPSize = "ThirdPhoto (" + Integer.toString(sTreeTrimTP.length()/1024) + "kB)";
					}
					
					String Type = getTypefromID(treeTrimming.getTreeTrimmingType());
					
					//rapEmail.generateAndSendEmail(user, "Tree Trimming",Type, treeTrimming.getTreeTrimmingMapAddress(), treeTrimming.getTreeTrimmingMapLatitude(), treeTrimming.getTreeTrimmingMapLongitude(), 
					//		treeTrimming.getTreeTrimmingMapAddress(), treeTrimming.getTreeTrimmingAddAccessInfo(), user.getEmailID(), firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{user.getEmailID(),""},
								"Tree Trimming - " + Type,
								new String[]{ef.treeTrimmingCustomerCopy(treeTrimming,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),
											ef.treeTrimmingEnergexCopy(treeTrimming, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
								firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//Energex Copy
					//rapEmail.generateAndSendEmail("","Tree Trimming - " + Type,ef.treeTrimmingEnergexCopy(treeTrimming, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppTreeTrimming\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
					break;
				}

			} else {
				result = new String(
						"{\"successCode\":\"2\",\"successMessage\":\"invalid username\"}");
			}
		
			em.close();
			//
			
					
			log.info("Inside rapAppTreeTrimming:::: End..." );
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("static-access")
	@POST
	@Path("/rapAppGraffiti")
	public javax.ws.rs.core.Response rapAppGraffiti(String inputPostJson) {
		log.info("Inside rapAppGraffiti:::: Start..." );
		// Tokenize
		StringTokenizer st = new StringTokenizer(inputPostJson, "=");
		String key = st.nextToken();
		String str = st.nextToken();
		//log.info("Tokenizer::::" + key + " Val" + str);
		// URL decoding
		// String inputJson = new String(str.getBytes("UTF-8"),"ASCII");

		URLDecoder urlDecoder = new URLDecoder();
		String inputJson="";
		try {
			inputJson = urlDecoder.decode(str, "ASCII");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String result = "";
			//***********************************Process Input ****************************
			JSONObject jsonInput = null;
			try {
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Photo"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();
			
			ExRapGraffiti graffiti = new ExRapGraffiti();
			graffiti = JsonToJavaHelper.convertGraffiti(inputJson);
			//Current Time
			//java.util.Date date = new java.util.Date();
			//graffiti.setTimeStamp(new Timestamp(date.getTime()));
			
			// Retrieve the User ...
			
			String queryString2 = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
			Query q2 = em.createQuery(queryString2);
			q2.setParameter("userName", graffiti.getUserName());
			List<ExRapRegistration> userList = q2.getResultList();
			if (userList.size() > 0) {
				for (ExRapRegistration user : userList) {
					
					// Change the user ID
					
					//graffiti.setUserID(user.getId());
					// Save the record ....
					em.getTransaction().begin();
					em.persist(graffiti);
					em.getTransaction().commit();

					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					
					String sGraffitiFP = (String) jsonInput.get("graffitiFirstPhoto");
					String sGraffitiSP = (String) jsonInput.get("graffitiSecondPhoto");  
					String sGraffitiTP = (String) jsonInput.get("graffitiThirdPhoto");
					
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					if(!sGraffitiFP.isEmpty()){
						firstPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sGraffitiFP,user.getId()+"FirstPhoto.png");
						firstPhotoMBPSize = "FirstPhoto (" + Integer.toString(sGraffitiFP.length()/1024) + "kB)";
					}
					
					if(!sGraffitiSP.isEmpty()){
						secondPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sGraffitiSP,user.getId()+"SecondPhoto.png");
						secondPhotoMBPSize = "SecondPhoto (" + Integer.toString(sGraffitiSP.length()/1024) + "kB)";
					}
					
					if(!sGraffitiTP.isEmpty()){
						thirdPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sGraffitiTP,user.getId()+"ThirdPhoto.png");
						thirdPhotoMBPSize = "ThirdPhoto (" + Integer.toString(sGraffitiTP.length()/1024) + "kB)";
					}
					
					String Type = getTypefromID(graffiti.getGraffitiType());
					
					//rapEmail.generateAndSendEmail(user, "Graffiti",Type, graffiti.getGraffitiMapAddress(), graffiti.getGraffitiMapLatitude(), graffiti.getGraffitiMapLongitude(), 
					//		graffiti.getGraffitiMapAddress(), graffiti.getGraffitiAddInfo(), user.getEmailID(), firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{user.getEmailID(),""}
						,"Graffiti - " + Type,
						new String[]{ef.graffitiCustomerCopy(graffiti,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),
								ef.graffitiEnergexCopy(graffiti, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
						firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//Energex Copy
					//rapEmail.generateAndSendEmail("","Graffiti - " + Type,ef.graffitiEnergexCopy(graffiti, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
				
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppGraffiti\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
					break;
				}

			} else {
				result = new String(
						"{\"successCode\":\"2\",\"successMessage\":\"invalid username\"}");
			}
		
			em.close();
			//
			
					
			log.info("Inside rapAppGraffiti:::: End..." );
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}


	}

	@SuppressWarnings("static-access")
	@POST
	@Path("/rapAppUpdatedetails")
	public javax.ws.rs.core.Response rapAppUpdatedetails(String inputPostJson) {
		
		log.info("Inside rapAppUpdatedetails:::: Start..." );
		
		// Tokenize
		StringTokenizer st = new StringTokenizer(inputPostJson, "=");
		String key = st.nextToken();
		String str = st.nextToken();
		//log.info("Tokenizer::::" + key + " Val" + str);
		// URL decoding
		// String inputJson = new String(str.getBytes("UTF-8"),"ASCII");

		URLDecoder urlDecoder = new URLDecoder();
		String inputJson="";
		try {
			inputJson = urlDecoder.decode(str, "ASCII");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String result = "";
			//***********************************Process Input ****************************
			JSONObject jsonInput = null;
			String UserName = "";
			try {
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Photo"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    		if(keyName.contains("userName")){
			    			UserName = (String) jsonInput.get(keyName);
			    			
			    		}
			    	}
			    }
				
				log.info("valid json string::::" + inputJson);
				log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();
			
			ExRapUpdatedetail updateDetail = new ExRapUpdatedetail();
			updateDetail = JsonToJavaHelper.convertUpdateDetails(inputJson);
			//Current Time
			//java.util.Date date = new java.util.Date();
			//updateDetail.setTimeStamp(new Timestamp(date.getTime()));
			// Retrieve the User ...
			log.info(updateDetail.toString());
			
			log.info(updateDetail.getUserName());
			
			String queryString2 = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
			Query q2 = em.createQuery(queryString2);
			q2.setParameter("userName", updateDetail.getUserName());
			List<ExRapRegistration> userList = q2.getResultList();
			if (userList.size() > 0) {
				for (ExRapRegistration user : userList) {
					
					
					log.info("EMAIL ID - Update Details :::::::: " + user.getEmailID());
					
					// Change the user ID
					//updateDetail.setUserId(user.getId());
					// Save the record ....
					em.getTransaction().begin();
					em.persist(updateDetail);
					em.getTransaction().commit();

					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					
					String Type = getTypefromID(updateDetail.getUpdateDetailsType());
					
					//rapEmail.generateAndSendEmail(user,"Update Details", Type, updateDetail.getUpdateDetailsMapAddress(), updateDetail.getUpdateDetailsMapLatitude(), updateDetail.getUpdateDetailsMapLongitude(), 
					//		updateDetail.getUpdateDetailsMapAddress(),updateDetail.getUpdateDetailsType(), user.getEmailID(), null,null,null);
					
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{user.getEmailID(),""}
						,"Update Details - " + Type,
						new String[]{ef.updateDetailsCustomer(updateDetail),
								ef.updateDetailsEnergex(updateDetail, user)});
					
					//Energex Copy
					//rapEmail.generateAndSendEmail("","Update Details - " + Type,ef.updateDetailsEnergex(updateDetail, user));
					
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppUpdatedetails\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
					break;
				}

			} else {
				result = new String(
						"{\"successCode\":\"2\",\"successMessage\":\"invalid username\"}");
			}
		
			em.close();
			//
			
					
			log.info("Inside rapAppUpdatedetails:::: End..." );
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}

	@SuppressWarnings("static-access")
	@POST
	@Path("/rapAppFaultyStreetLights")
	public javax.ws.rs.core.Response rapAppFaultystreetlights(String inputPostJson) {
		
		log.info("Inside rapAppFaultyStreetLights:::: Start..." );
		// Tokenize
		StringTokenizer st = new StringTokenizer(inputPostJson, "=");
		String key = st.nextToken();
		String str = st.nextToken();
		//log.info("Tokenizer::::" + key + " Val" + str);
		// URL decoding
		// String inputJson = new String(str.getBytes("UTF-8"),"ASCII");

		URLDecoder urlDecoder = new URLDecoder();
		String inputJson="";
		try {
			inputJson = urlDecoder.decode(str, "ASCII");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String result = "";
			//***********************************Process Input ****************************
			JSONObject jsonInput = null;
			try {
				jsonInput = new JSONObject(inputJson);
				Set<String> keys = jsonInput.keySet();
				
				Iterator iter = keys.iterator();

			    while (iter.hasNext()) {
			    	String keyName = (String) iter.next();
			    	if(!keyName.contains("Photo"))// Don;t Print Images
			    	{
			    		log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			factory = Persistence
					.createEntityManagerFactory(PERSISTENCE_UNIT_NAME,ConfigReader.getJPAProperties());
			EntityManager em = factory.createEntityManager();
			
			ExRapFaultystreetlight faultyStreetLight = new ExRapFaultystreetlight();
			faultyStreetLight = JsonToJavaHelper.convertFaultystreetLights(inputJson);
			//Current Time
			//java.util.Date date = new java.util.Date();
			//faultyStreetLight.setTimeStamp(new Timestamp(date.getTime()));
			// Retrieve the User ...
			
			String queryString2 = "SELECT u FROM ExRapRegistration u where u.userName =:userName";
			Query q2 = em.createQuery(queryString2);
			q2.setParameter("userName", faultyStreetLight.getUserName());
			List<ExRapRegistration> userList = q2.getResultList();
			if (userList.size() > 0) {
				for (ExRapRegistration user : userList) {
					
					// Change the user ID
					//faultyStreetLight.setUserId(user.getId());
					// Save the record ....
					em.getTransaction().begin();
					em.persist(faultyStreetLight);
					em.getTransaction().commit();

					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					String sFaultySLFP = (String) jsonInput.get("fslightsFirstPhoto");
					String sFaultySLSP = (String) jsonInput.get("fslightsSecondPhoto");  
					String sFaultySLTP = (String) jsonInput.get("fslightsThirdPhoto");
					
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					if(!sFaultySLFP.isEmpty()){
						firstPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sFaultySLFP,user.getId()+"FirstPhoto.png");
						firstPhotoMBPSize = "FirstPhoto (" + Integer.toString(sFaultySLFP.length() / 1024) + "kB)";
					}
					
					if(!sFaultySLSP.isEmpty()){
						secondPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sFaultySLSP,user.getId()+"SecondPhoto.png");
						secondPhotoMBPSize = "SecondPhoto (" + Integer.toString(sFaultySLSP.length() / 1024) + "kB)";
					}
					
					if(!sFaultySLTP.isEmpty()){
						thirdPhotoMBP = rapEmail.converBase64DataToImageFileAndAttach(sFaultySLTP,user.getId()+"ThirdPhoto.png");
						thirdPhotoMBPSize = "ThirdPhoto (" + Integer.toString(sFaultySLTP.length() / 1024) + "kB)";
					}
					
					String Type = getTypefromID(faultyStreetLight.getFaultyStreetLightType());
					
					//rapEmail.generateAndSendEmail(user, "Faulty Street Lights",Type, faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsMapLatitude(), faultyStreetLight.getFslightsMapLongitude(), 
					//		faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsAddInfo(), user.getEmailID(), firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{user.getEmailID(),""},
								"Faulty Streetlights - " + Type,
								new String[]{ef.faultyStreetLightCustomerCopy(faultyStreetLight,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),
								ef.faultyStreetLightEnergexCopy(faultyStreetLight, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
								firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//Energex Copy
					//rapEmail.generateAndSendEmail("","Faulty Streetlights - " + Type,ef.faultyStreetLightEnergexCopy(faultyStreetLight, user,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppFaultyStreetLights\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
					break;
				}

			} else {
				result = new String(
						"{\"successCode\":\"2\",\"successMessage\":\"invalid username\"}");
			}
		
			em.close();
			//
			
					
			log.info("Inside rapAppFaultyStreetLights:::: End..." );
			return javax.ws.rs.core.Response.ok().entity(result).build();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			if (debugState) {
				// Send Debug logs

			}
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() {
		log.debug("Inside REST GET request....");
		return "<html> " + "<title>" + "REST service" + "</title>"
				+ "<body><h1>" + "REST service is up and running"
				+ "</body></h1>" + "</html> ";
	}
}

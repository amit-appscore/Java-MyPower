package com.sparq.MyPowerApp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sparq.emailfunctions.EmailFunction;
import com.sparq.utils.RapEmail;

import model.ExRapFaultystreetlight;
import model.ExRapGraffiti;
import model.ExRapRegistration;
import model.ExRapUpdatedetail;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.xml.bind.DatatypeConverter;

public class RESTServiceClient {
	
	public static void dbTestClient(){
		java.sql.Connection conn = null;

		Properties props = new Properties();
		System.setProperty("javax.net.ssl.keyStore","D:/CTO/BatteryApp/DBScript/keystore");
		System.setProperty("javax.net.ssl.keyStorePassword","abcdef");
		System.setProperty("javax.net.ssl.trustStore","D:/CTO/BatteryApp/DBScript/truststore");
		System.setProperty("javax.net.ssl.trustStorePassword","abcdef");
		props.setProperty("ssl", "true");
		props.setProperty("sslfactory", "org.postgresql.ssl.NonValidatingFactory");
		props.setProperty("user", "sparquser");
		props.setProperty("password", "xgt6mnWx235ngrbad");
		try {
			conn = DriverManager.getConnection("jdbc:mysql://54.66.188.15:3306/sparq?verifyServerCertificate=false&useSSL=true&requireSSL=true&", props);
			System.out.println("Connection Status" + conn.toString());
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
		
		/*MysqlDataSource mysqlDS = new MysqlDataSource();

		mysqlDS.setUseSSL(true);
		mysqlDS.setRequireSSL(true);
		mysqlDS.setVerifyServerCertificate(true);
		mysqlDS.setLogger("com.mysql.jdbc.log.StandardLogger");

		mysqlDS.setTrustCertificateKeyStoreType("JKS");
		mysqlDS.setTrustCertificateKeyStoreUrl("d:/CTO/BatteryApp/DBScript/truststore");
		mysqlDS.setTrustCertificateKeyStorePassword("abcdef");

		mysqlDS.setClientCertificateKeyStoreType("JKS");
		mysqlDS.setClientCertificateKeyStoreUrl("d:/CTO/BatteryApp/DBScript/keystore");
		mysqlDS.setClientCertificateKeyStorePassword("abcdef");

		mysqlDS.setServerName("54.66.188.15");
		mysqlDS.setPort(3066);
		mysqlDS.setDatabaseName("sparq");

		// Create a connection object
		try {System.out.println("Connection Status Before");
			java.sql.Connection c = mysqlDS.getConnection("sparquser", "xgt6mnWx235ngrbad");
			System.out.println("Connection Status After" + c.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		

	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException, ParseException {
		Logger log = LoggerFactory.getLogger(RESTServiceClient.class);
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {

			//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			String fechaStr = "2016-02-16 10:49:29.10000";  
			//java.util.Date fechaNueva = format.parse(fechaStr);
			SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			System.out.println(outputformat.format(inputformat.parse(fechaStr)));
			//int tama�o = fechaStr.length()-5;
			//fechaStr = fechaStr.substring(0, tama�o); //I get a string without the miliseconds
			//System.out.println(format.format(fechaNueva)); // Prints 2013-10-10 10:49:29
			
			//dbTestClient();
			
			//HttpPost postRequest = new HttpPost(
			//		"http://localhost:8080/BatteryAppService/rest/processInput");
			
			//Login Test
			//StringEntity userEntity = new StringEntity("InputJson={\"UserName\":\"ambi@wipro.com\",\"Password\":\"ambi\",\"Operation\":\"Login\",\"OfflinePwdSync\":\"Yes\"}");
			
			//Settings App1 Test
			//StringEntity userEntity = new StringEntity("InputJson={\"EmailAddress\":\"ambi@wipro.com\",\"First\":\"ambi\",\"Operation\":\"Login\",\"OfflinePwdSync\":\"Yes\"}");
			
		
			//SetupJobTest
		//	String newJson = "{"Operation":"SetupJob","ApplicantFirstName":"pol","ApplicantLastName":"jj","ApplicantCompany":"gvb","RelationshipToProperty":"Property Owner","RelationshipToPropertyText":"null ","UnitNumber":"jj","streetNumber":"hh","StreetName":"ghh","Suburb":"hj","PostCode":"999","PrimaryMeterNumber":"99","NMINumber":"abc","LocationOfBatteryInstallation":"ghn","BatteryBarcode":"abc","BatteryNameplateImage":"","MaxChargeRate":"99","MaxCapacity":"5555","DcrSerialNumber":"fvhhh","InverterBarcode":"abc","InverterNameplateImage":"abc","InstallationDate":"16/11/2015","AdditionalNotes":"chubby\n","InstalllationImage":"","LineDiagramType":"Option 2","LineDiagramImage":"abc","NetworkType":"rbg2","InstallersFirstName":"pol","InstallersLastName":"jj","InstallersElectricalLicenceNumber":"","UserName":"ambi@wipro.com","MaxDischargerRate":"abc","KWCapacity":"abc" }";
			//StringEntity userEntity = new StringEntity( "InputJson={"+ ""Operation":"SetupJob","ApplicantFirstName":"pol","ApplicantLastName":"jj","ApplicantCompany":"gvb","RelationshipToProperty":"Property Owner","RelationshipToPropertyText":"abc","UnitNumber":"jj","streetNumber":"hh","StreetName":"ghh","Suburb":"hj","PostCode":"999","PrimaryMeterNumber":"99","NMINumber":"abc","LocationOfBatteryInstallation":"ghn","BatteryBarcode":"abc","BatteryNameplateImage":"","MaxChargeRate":"99","MaxCapacity":"5555","DcrSerialNumber":"fvhhh"," + " "InverterBarcode" : "abc","AdditionalNotes":"chubby","InstalllationImage":"","LineDiagramType":"Option 2","LineDiagramImage":"abc","NetworkType":"abc2","UserName":"ambi@wipro.com""+ "}" );
			
			//Registration Test
			/*StringEntity userEntity = new StringEntity("Input={"
					+ "\"Operation\":\"Registration\","
					+ "\"FirstName\":\"ambikesh\","
					+ "\"LastName\":\"singh\","
					+ "\"EmailAddress\":\"ambikesh@wipro.com\","
					+ "\"Password\":\"@wipro\","
					+ "\"InstallersElectricalLicenceNumber\":\"11223355\""
					+ ",\"PhoneNumber\":\"555666"
					+ "\"}");*/
			//Forgot Password Test
			// Update Details contact up App2 test
			StringEntity userEntity = new StringEntity("input={\"timeStamp\":\"2016-02-24 17:03:00\",\"userName\":\"bhanu\",\"id\":0,\"dangerToMeterReadersRadioBtnValue\":\"Yes\",\"dogAtYourPropertyRadioBtnValue\":\"Yes\",\"dogDetailsAddAccessInformation\":\"add info\",\"dogPermanentLocationRadioBtnValue\":\"No\",\"meterAccessAddAccessInformation\":\"accexx info\",\"meterAccessEnergexLockRadioBtnValue\":\"Yes\",\"meterAccessPinCode\":\"abcd\",\"updateDetailsType\":\"meterAccess\",\"updateDetailsMapLatitude\":\"18.5990681\",\"updateDetailsMapAddress\":\"\",\"updateDetailsMapLongitude\":\"73.7192774\",\"dogBreed\":\"lab\"}");
			
			//String userInput = "{\"timeStamp\":\"2016-02-16 10:35:00.000\",\"userName\":\"bhanu\",\"id\":0,\"dangerToMeterReadersRadioBtnValue\":\"Yes\",\"dogAtYourPropertyRadioBtnValue\":\"Yes\",\"dogDetailsAddAccessInformation\":\"add info\",\"dogPermanentLocationRadioBtnValue\":\"No\",\"meterAccessAddAccessInformation\":\"accexx info\",\"meterAccessEnergexLockRadioBtnValue\":\"Yes\",\"meterAccessPinCode\":\"abcd\",\"updateDetailsType\":\"meterAccess\",\"updateDetailsMapLatitude\":\"18.5990681\",\"updateDetailsMapAddress\":\"\",\"updateDetailsMapLongitude\":\"73.7192774\",\"dogBreed\":\"lab\"}";
			
			/*try {
				testUserDetails(userInput);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// Tree trimming test
			//{\"userName\":\"Pankajt\",\"id\":0,\"treeTrimmingType\":\"ttDebrisRemoval\",\"treeTrimmingMapAddress\":\"\",\"treeTrimmingSecondPhoto\":\"\",\"treeTrimmingFirstPhoto\":\"\",\"treeTrimmingMapLongitude\":\"73.71948\",\"treeTrimmingMapLatitude\":\"18.5991262\",\"treeTrimmingThirdPhoto\":\"\",\"ttcloseToPowerlinesAffectedRadioBtnValue\":\"\",\"ttcloseToPowerlinesDistanceRadioBtnValue\":\"\",\"treeTrimmingAddAccessInfo\":\"additional access\"} 
			//StringEntity userEntity = new StringEntity("input={\"userName\":\"bhanu\",\"id\":0,\"treeTrimmingType\":\"ttDebrisRemoval\",\"treeTrimmingMapAddress\":\"\",\"treeTrimmingSecondPhoto\":\"\",\"treeTrimmingFirstPhoto\":\"\",\"treeTrimmingMapLongitude\":\"73.71948\",\"treeTrimmingMapLatitude\":\"18.5991262\",\"treeTrimmingThirdPhoto\":\"\",\"ttcloseToPowerlinesAffectedRadioBtnValue\":\"\",\"ttcloseToPowerlinesDistanceRadioBtnValue\":\"\",\"treeTrimmingAddAccessInfo\":\"additional access\"}");
			
			// Graffiti test
			//StringEntity userEntity = new StringEntity("input={\"userName\":\"Pankajt\",\"id\":0,\"graffitiThirdPhoto\":\"\",\"graffitiSecondPhoto\":\"\",\"graffitiLabelRadioBtnValue\":\"Transformer\",\"graffitiMapLatitude\":\"18.5991262\",\"graffitiAddInfo\":\"addnl info bhanu\",\"graffitiMapAddress\":\"address bhanu\",\"graffitiFirstPhoto\":\"\",\"graffitiMapLongitude\":\"73.71948\",\"graffitiType\":\"ePrivateProperty\"}");
			
			//String userString = new String("{\"userName\":\"Pankajt\",\"id\":0,\"dangerToMeterReadersRadioBtnValue\":\"Yes\",\"dogAtYourPropertyRadioBtnValue\":\"Yes\",\"dogDetailsAddAccessInformation\":\"add info\",\"dogPermanentLocationRadioBtnValue\":\"No\",\"meterAccessAddAccessInformation\":\"\",\"meterAccessEnergexLockRadioBtnValue\":\"\",\"meterAccessPinCode\":\"\",\"updateDetailsType\":\"dogDetails\",\"updateDetailsMapLatitude\":\"18.5990681\",\"updateDetailsMapAddress\":\"\",\"updateDetailsMapLongitude\":\"73.7192774\",\"dogBreed\":\"lab\"}");
			
			
			// Faulty Street Lights test
			//{\"userName\":\"Pankajt\",\"id\":0,\"fsLightsAffectedRadioBtnValue\":\"Single\",\"fslightsMapLatitude\":\"18.5991243\",\"fslightsMapAddress\":\"\",\"fslightsSecondPhoto\":\"\",\"fslightsAddInfo\":\"\",\"fslightsThirdPhoto\":\"\",\"fslightsMapLongitude\":\"73.7194793\",\"fslightsFirstPhoto\":\"\",\"faultyStreetLightType\":\"24HrsADay\"}     
			//StringEntity userEntity = new StringEntity("input={\"userName\":\"Pankajt\",\"id\":0,\"fslightsAffectedRadioBtnValue\":\"Double\",\"fslightsMapLatitude\":\"18.5991243\",\"fslightsMapAddress\":\"\",\"fslightsSecondPhoto\":\"\",\"fslightsAddInfo\":\"\",\"fslightsThirdPhoto\":\"\",\"fslightsMapLongitude\":\"73.7194793\",\"fslightsFirstPhoto\":\"\",\"faultyStreetLightType\":\"24HrsADay\"}");
			//String userString = new String("{\"userName\":\"Pankajt\",\"id\":0,\"fslightsAffectedRadioBtnValue\":\"Double\",\"fslightsMapLatitude\":\"18.5991243\",\"fslightsMapAddress\":\"\",\"fslightsSecondPhoto\":\"\",\"fslightsAddInfo\":\"\",\"fslightsThirdPhoto\":\"\",\"fslightsMapLongitude\":\"73.7194793\",\"fslightsFirstPhoto\":\"\",\"faultyStreetLightType\":\"24HrsADay\"}");
			
			
			
			
			
			/*try {
				testUserDetails(userString);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// Testing RAP APP background Image
			//getVersionOfRapAppBackgroundImage
			//getRapAppBackgroundImage
			//postRapAppBackgroundImage
			
			//
			//StringEntity userEntity = new StringEntity("InputJson={\"Operation\":\"SetRapAppBkgImage\",\"ImageData\":\"0xghftrysaskjbzcbzbcabsdfhafbabfbzckbcazkbkjcfab\"}");
			
			//HttpGet postRequest = new HttpGet(
			//		"http://localhost:8080/BatteryAppService/rest/getRapAppBackgroundImage");
			//HttpGet postRequest = new HttpGet(
			//		"http://sparq-dev.konycloud.com/BatteryAppService/rest/processPostInput");
			//postRequest.addHeader("content-type", "text/plain");
			//https://sparq-dev.konycloud.com/MyTestApp/rest/
			/*HttpPut postRequest = new HttpPut(
					"http://sparq-dev.konycloud.com/MyTestApp/rest/processInput");*/
			//HttpPost postRequest = new HttpPost(
			//		"http://sparq-dev.konycloud.com/BatteryAppService/rest/processPostInput");
			
			HttpPost postRequest = new HttpPost(
					"http://sparq.konycloud.com/MyPowerApp/rest/rapAppUpdatedetails");//rapAppUpdatedetails//rapAppFaultyStreetLights//rapAppTreeTrimming//rapAppGraffiti
			postRequest.addHeader("Accept", "text/plain");
			postRequest.setEntity(userEntity);
			
			HttpResponse response = httpClient.execute(postRequest);

			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : "
						+ statusCode);
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(response.getEntity().getContent())));

			String output;
			
			
			while ((output = br.readLine()) != null) {
				
				FileOutputStream fos2 = new FileOutputStream(new File("D:/upload/encodedStrfromDBReceived.jpg")); 
			    fos2.write(output.getBytes()); 
			    fos2.close();
			    System.out.println("DATA::::" + output);
				URLDecoder urlDecoder = new URLDecoder();
				System.out.println("length of output::::" + output.length());
				String urlDecodedData = urlDecoder.decode(output, "ASCII");
				urlDecodedData = urlDecodedData.replaceAll(" ", "+");
				System.out.println("length of urlDecodedData::::" + urlDecodedData.length());
				
				byte[] decodedData = Base64.decodeBase64(urlDecodedData.getBytes());
				System.out.println("length of decodedData::::" + decodedData.length);
				 FileOutputStream fos = new FileOutputStream(new File("D:/upload/abc5.jpg")); 
				    fos.write(decodedData); 
				    fos.close();
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public static void testGraffiti(String inputJson) throws AddressException, MessagingException, Exception{


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
			    		//log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				//log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				//log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				//return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			
			
			ExRapGraffiti graffiti = new ExRapGraffiti();
			graffiti = JsonToJavaHelper.convertGraffiti(inputJson);
			
			// Retrieve the User ...
			
			
					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					
					String sGraffitiFP = (String) jsonInput.get("graffitiFirstPhoto");
					String sGraffitiSP = (String) jsonInput.get("graffitiSecondPhoto");  
					String sGraffitiTP = (String) jsonInput.get("graffitiThirdPhoto");
					
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
		           
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					
					//rapEmail.generateAndSendEmail(null, "Graffiti",graffiti.getGraffitiType(), graffiti.getGraffitiMapAddress(), graffiti.getGraffitiMapLatitude(), graffiti.getGraffitiMapLongitude(), 
					//		graffiti.getGraffitiMapAddress(), graffiti.getGraffitiAddInfo(), "", firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{""},
							"Graffiti - " + graffiti.getGraffitiType(),
							new String[]{ef.graffitiCustomerCopy(graffiti,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
							firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//rapEmail.generateAndSendEmail("","Graffiti - " + graffiti.getGraffitiType(),ef.graffitiEnergexCopy(graffiti, null,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
				
	}
	
	public static void testFS(String inputJson) throws AddressException, MessagingException, Exception{

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
			    		//log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				//log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				//log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				
			}
			//**********************************End **************************************
			
			ExRapFaultystreetlight faultyStreetLight = new ExRapFaultystreetlight();
			faultyStreetLight = JsonToJavaHelper.convertFaultystreetLights(inputJson);
			
			// Retrieve the User ...
			
			
					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					String sFaultySLFP = (String) jsonInput.get("fslightsFirstPhoto");
					String sFaultySLSP = (String) jsonInput.get("fslightsSecondPhoto");  
					String sFaultySLTP = (String) jsonInput.get("fslightsThirdPhoto");
					
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
		           
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					//rapEmail.generateAndSendEmail(null, "Faulty Street Lights",null, faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsMapLatitude(), faultyStreetLight.getFslightsMapLongitude(), 
					//		faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsAddInfo(),"", firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					EmailFunction ef = new EmailFunction();
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{""},
							"Graffiti - " + faultyStreetLight.getFaultyStreetLightType(),
							new String[]{ef.faultyStreetLightCustomerCopy(faultyStreetLight,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
							firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//rapEmail.generateAndSendEmail("","Graffiti - " + faultyStreetLight.getFaultyStreetLightType(),ef.faultyStreetLightEnergexCopy(faultyStreetLight, null,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppTreeTrimming\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
					
				
	
			
	}
	
	public static void testFaultyStreetlights(String inputJson) throws AddressException, MessagingException, Exception{


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
			    		//log.info(keyName+":"+jsonInput.get(keyName));
			    	}
			    }
				
				//log.info("valid json string::::" + inputJson);
				//log.info("valid json string::::");
			} catch (JSONException e) {
				//log.info("invalid json string::::" + inputJson);
				//log.info("Invalid json string::::");
				result = new String(
						"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
				//return javax.ws.rs.core.Response.ok().entity(result).build();
			}
			//**********************************End **************************************
			
			
			ExRapFaultystreetlight faultyStreetLight = new ExRapFaultystreetlight();
			faultyStreetLight = JsonToJavaHelper.convertFaultystreetLights(inputJson);
			
			// Retrieve the User ...
			
			

					//Email the message ...
					RapEmail rapEmail = new RapEmail();
					// Converting a Base64 String into Image byte array
					String sFaultySLFP = (String) jsonInput.get("fslightsFirstPhoto");
					String sFaultySLSP = (String) jsonInput.get("fslightsSecondPhoto");  
					String sFaultySLTP = (String) jsonInput.get("fslightsThirdPhoto");
					
					MimeBodyPart firstPhotoMBP = null,secondPhotoMBP = null,thirdPhotoMBP = null;
		           
					
					String firstPhotoMBPSize = "No File Uploaded",secondPhotoMBPSize = "No File Uploaded",thirdPhotoMBPSize = "No File Uploaded";
					
					
					//rapEmail.generateAndSendEmail(user, "Faulty Street Lights",Type, faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsMapLatitude(), faultyStreetLight.getFslightsMapLongitude(), 
					//		faultyStreetLight.getFslightsMapAddress(), faultyStreetLight.getFslightsAddInfo(), user.getEmailID(), firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					EmailFunction ef = new EmailFunction();
					String Type = ef.getTypefromID(faultyStreetLight.getFaultyStreetLightType());
					//Customer Copy
					rapEmail.generateAndSendEmail(new String[]{""},
							"Faulty Streetlights - " + Type,
							new String[]{ef.faultyStreetLightCustomerCopy(faultyStreetLight,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize)},
							firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					//rapEmail.generateAndSendEmail("","Faulty Streetlights - " + Type,ef.faultyStreetLightEnergexCopy(faultyStreetLight, null,firstPhotoMBPSize,secondPhotoMBPSize,thirdPhotoMBPSize),firstPhotoMBP,secondPhotoMBP,thirdPhotoMBP);
					
					
					result = new String(
							"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppTreeTrimming\": \""
									//+ imageRow.getVersion()
									+ "Saved\"}}");
			
		
		
}
	
	public static void testUserDetails(String inputJson) throws AddressException, MessagingException, Exception{

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
		    		//log.info(keyName+":"+jsonInput.get(keyName));
		    		if(keyName.contains("userName")){
		    			UserName = (String) jsonInput.get(keyName);
		    			
		    		}
		    	}
		    }
			
			//log.info("valid json string::::" + inputJson);
			//log.info("valid json string::::");
		} catch (JSONException e) {
			//log.info("invalid json string::::" + inputJson);
			//log.info("Invalid json string::::");
			result = new String(
					"{\"successCode\":\"0\",\"successMessage\":\"Partial JSON input .... probaly truncated\"}");
			//return javax.ws.rs.core.Response.ok().entity(result).build();
		}
		//**********************************End **************************************
		
		
		ExRapUpdatedetail updateDetail = new ExRapUpdatedetail();
		updateDetail = JsonToJavaHelper.convertUpdateDetails(inputJson);
		
		// Retrieve the User ...
		

				//Email the message ...
				RapEmail rapEmail = new RapEmail();
				// Converting a Base64 String into Image byte array
				
				
				
				//rapEmail.generateAndSendEmail(user,"Update Details", Type, updateDetail.getUpdateDetailsMapAddress(), updateDetail.getUpdateDetailsMapLatitude(), updateDetail.getUpdateDetailsMapLongitude(), 
				//		updateDetail.getUpdateDetailsMapAddress(),updateDetail.getUpdateDetailsType(), user.getEmailID(), null,null,null);
				
				EmailFunction ef = new EmailFunction();
				String Type = ef.getTypefromID(updateDetail.getUpdateDetailsType());
				//Customer Copy
				rapEmail.generateAndSendEmail(new String[]{""},"Update Details - " + Type,new String[]{ef.updateDetailsCustomer(updateDetail)});
				
				//rapEmail.generateAndSendEmail("","Update Details - " + Type,ef.updateDetailsEnergex(updateDetail, null));
				
				
				result = new String(
						"{\"successCode\":\"1\",\"successMessage\":\"Success\",\"result\":{\"rapAppUpdatedetails\": \""
								//+ imageRow.getVersion()
								+ "Saved\"}}");
		
		
	
	
	}
	
}

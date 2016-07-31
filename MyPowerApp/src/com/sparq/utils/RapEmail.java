package com.sparq.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.sparq.MyPowerApp.RESTService;

import model.ExBessApplicantdetail;
import model.ExBessEquipment;
import model.ExBessJobdetail;
import model.ExBessRegistration;
import model.ExRapRegistration;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.xml.bind.DatatypeConverter;
public class RapEmail {

	private static final class OsUtils
	{
	   private static String OS = null;
	   public static String getOsName()
	   {
	      if(OS == null) { OS = System.getProperty("os.name"); }
	      return OS;
	   }
	   public static boolean isWindows()
	   {
	      return getOsName().startsWith("Windows");
	   }

	   //public static boolean isUnix() // and so on
	}

	 private String getCurrentDir () {

		   String workingDir = System.getProperty("user.dir");
		   log.info("Current working directory : " + workingDir);
		   return workingDir;
		   
	   }
	 
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

	static Properties mailServerProperties;
	static Session getMailSession;
	static MimeMessage generateMailMessage;

	public static void initMailPreperties() 
			{
		// Step1
				System.out.println("\n 1st ===> setup Mail Server Properties..");
				mailServerProperties = System.getProperties();
				/*mailServerProperties.put("mail.smtp.port", "587");
				mailServerProperties.put("mail.smtp.auth", "true");
				mailServerProperties.put("mail.smtp.starttls.enable", "true");*/

				mailServerProperties.put("mail.smtp.port", "25");
				mailServerProperties.put("mail.transport.protocol", "smtps");
				mailServerProperties.put("mail.smtp.auth", "true");
				mailServerProperties.put("mail.smtp.starttls.enable", "true");
				mailServerProperties.put("mail.smtp.starttls.required", "true");
				//mailServerProperties.put("mail.smtp.from", "energex.mypower@gmail.com");
				
				System.out
						.println("Mail Server Properties have been setup successfully..");

				// Step2
				System.out.println("\n\n 2nd ===> get Mail Session..");
				getMailSession = Session.getDefaultInstance(mailServerProperties, null);
				
		
	}
	public static void initMessage(String userEmailId)
			throws AddressException, MessagingException{
		generateMailMessage = new MimeMessage(getMailSession);
		
		if(!userEmailId.isEmpty()){
			generateMailMessage.addRecipient(Message.RecipientType.TO,
				new InternetAddress(userEmailId.toLowerCase()));
		}else{
			generateMailMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress("mypowerapp@energex.com.au"));
		}
		/*generateMailMessage.addRecipient(Message.RecipientType.CC,
				new InternetAddress("sawani.bade@gmail.com"));
		generateMailMessage.addRecipient(Message.RecipientType.CC,
				new InternetAddress("ramonamcdonald@energex.com.au"));*/
		/*generateMailMessage.addRecipient(Message.RecipientType.BCC,
				new InternetAddress("energex.mypower@gmail.com"));
		//generateMailMessage.addRecipient(Message.RecipientType.BCC,
				//new InternetAddress("susanlee@energex.com.au"));*/
		generateMailMessage.addRecipient(Message.RecipientType.BCC,
				new InternetAddress("energex.mypower@gmail.com"));
		generateMailMessage.addRecipient(Message.RecipientType.BCC,
				new InternetAddress("herve.fraval@appscore.com.au"));
		
	}
	public static void generateAndSendEmail(ExRapRegistration user,String contactFormType,String Type,String Location,String Lat, String Lon, 
			String Address,String detail,String[] userEmailIds,
			MimeBodyPart firstPhoto, MimeBodyPart secondPhoto, MimeBodyPart thirdPhoto
			) throws AddressException, MessagingException {
		initMailPreperties();
		String userEmailId;
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		//transport.connect("smtp.gmail.com", "energex.mypower", "zgxxvxtulkqhsjfy");
		transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIAJLVYLRRVUUGOGFSQ", "AhTqG3AkdU2XjPmxd2AaIAdlPXW6oeg8QNuqVDn+vkcK");
		
		for (int i = 0; i < userEmailIds.length; i++) {
			userEmailId = userEmailIds[i];
		
			initMessage(userEmailId);
			//generateMailMessage.
			Multipart mimeMultipart = new MimeMultipart();
			// /////////////////Email Subject //////////////////////////
			// Battery installation submission
			generateMailMessage
					.setSubject("Report a problem - "+ contactFormType +" submission");
	
			// Add Email Body .....
			String emailBody = createEmailMessage(user,contactFormType,Type,Location,Lat, Lon,Address,detail);
			MimeBodyPart emailContent = new MimeBodyPart();
			emailContent.setContent(emailBody,"text/html");
	        mimeMultipart.addBodyPart(emailContent);
	        
	        // Add attachments ...
	        if(firstPhoto!=null)mimeMultipart.addBodyPart(firstPhoto);
	        if(secondPhoto!=null)mimeMultipart.addBodyPart(secondPhoto);
	        if(thirdPhoto!=null)mimeMultipart.addBodyPart(thirdPhoto);
	       
			generateMailMessage.setContent(mimeMultipart);
			//generateMailMessage.setFrom(new InternetAddress("no-reply@mypower.energex.com.au"));
			generateMailMessage.setFrom(new InternetAddress("no-reply@mypower.energex.com.au"));
			System.out.println("Mail Session has been created successfully..");
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			transport.sendMessage(generateMailMessage,
					generateMailMessage.getAllRecipients());
		}
		// Step3
		transport.close();
		
	}
	
	MimeBodyPart emailAttachment(String bytes, String fileName){
		//
		
        MimeBodyPart imageBodyPart = new MimeBodyPart();
        try {
        	DataSource dataSource = new ByteArrayDataSource(bytes, "image/jpeg");
			imageBodyPart.setDataHandler(new DataHandler(dataSource));
			imageBodyPart.setFileName(fileName);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			imageBodyPart = null;
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			imageBodyPart = null;
			e.printStackTrace();
		}
        
        return imageBodyPart;
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
    
    public byte[] decodeImageByteArray(byte[] imageDataString) {
    	return Base64.decodeBase64(imageDataString);
    }
	public MimeBodyPart converBase64DataToImageFileAndAttach(String imageDataString,String fileName){
		// create the second message part
        MimeBodyPart mbp2 = new MimeBodyPart();
        String filePath = "";
        FileOutputStream fop = null;
	    try {            
	    	
	        byte[] imageByteArray = decodeImage(imageDataString);
	        
	        filePath = "/usr/share/tomcat7/webapps/MyPowerApp/";
	        //filePath = "D:/Upload/";
	        
	        File fileDir = new File(filePath+File.separator+"tmpDir2");
	        fileDir.mkdir();
	        filePath = fileDir.getCanonicalPath();
	        log.info("Canonical Path : "+filePath);
	        filePath  = fileDir.getAbsolutePath();
	        log.info("Absolute Path : "+filePath);
	        
	        // Save the imagedata to file ...
	        log.info("Saving image file START:::: " + filePath+fileName);
	        
	        // get the content in bytes
	        /*fop = new FileOutputStream(filePath+fileName);
 			byte[] contentInBytes = imageDataString.getBytes();
 			fop.write(contentInBytes);
 			fop.flush();
 			fop.close();*/
	        
	        log.info("Saving image file END::::" + filePath+ fileName);
	        // Write a image byte array into file system
	        FileOutputStream imageOutFile = new FileOutputStream(filePath+fileName);
	        
	        imageOutFile.write(imageByteArray);
	
	        imageOutFile.close();
	        
	        FileDataSource fds = new FileDataSource(filePath+fileName);
	        try {
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	        System.out.println("Image Successfully Manipulated!");
	    } catch (FileNotFoundException e) {
	        System.out.println("Image not found" + e);
	    } catch (IOException ioe) {
	        System.out.println("Exception while reading the Image " + ioe);
	    }
	    
	    return mbp2;
	    
	}
	
	public static void generateAndSendEmail( String[] userEmailIds,String subject,String[] bodies,
			MimeBodyPart firstPhoto, MimeBodyPart secondPhoto, MimeBodyPart thirdPhoto		
			) throws AddressException, MessagingException {
		initMailPreperties();
		String userEmailId;
		String body;
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		//transport.connect("smtp.gmail.com", "energex.mypower", "zgxxvxtulkqhsjfy");
		transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIAJLVYLRRVUUGOGFSQ", "AhTqG3AkdU2XjPmxd2AaIAdlPXW6oeg8QNuqVDn+vkcK");
		
		for (int i = 0; i < userEmailIds.length; i++) {
			userEmailId = userEmailIds[i];
			body = bodies[i];
			initMessage(userEmailId);
			
			//generateMailMessage.
			Multipart mimeMultipart = new MimeMultipart();
			// /////////////////Email Subject //////////////////////////
			// Battery installation submission
			generateMailMessage
					.setSubject(subject);
	
			// Add Email Body .....
			//String emailBody = "";
			MimeBodyPart emailContent = new MimeBodyPart();
			emailContent.setContent(body,"text/html");
	        mimeMultipart.addBodyPart(emailContent);
	
	        // Add attachments ...
	        if(firstPhoto!=null)mimeMultipart.addBodyPart(firstPhoto);
	        if(secondPhoto!=null)mimeMultipart.addBodyPart(secondPhoto);
	        if(thirdPhoto!=null)mimeMultipart.addBodyPart(thirdPhoto);
	        
	        generateMailMessage.setContent(mimeMultipart);
	        //generateMailMessage.setFrom(new InternetAddress("no-reply@mypower.energex.com.au"));
	        generateMailMessage.setFrom(new InternetAddress("no-reply@mypower.energex.com.au"));
			System.out.println("Mail Session has been created successfully..");
			
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			transport.sendMessage(generateMailMessage,
					generateMailMessage.getAllRecipients());
		}
		
		transport.close();
		
	}
	
	public static void generateAndSendEmail( String[] userEmailIds,String subject,String[] bodies			
			) throws AddressException, MessagingException {
		initMailPreperties();
		String userEmailId;
		String body;
		System.out.println("generateAndSendEmail 3");
		Transport transport = getMailSession.getTransport("smtp");

		// Enter your correct gmail UserID and Password
		// if you have 2FA enabled then provide App Specific Password
		//transport.connect("smtp.gmail.com", "energex.mypower", "zgxxvxtulkqhsjfy");
		transport.connect("email-smtp.us-east-1.amazonaws.com", "AKIAJLVYLRRVUUGOGFSQ", "AhTqG3AkdU2XjPmxd2AaIAdlPXW6oeg8QNuqVDn+vkcK");
		
		for (int i = 0; i < userEmailIds.length; i++) {
			userEmailId = userEmailIds[i];
			body = bodies[i];
			initMessage(userEmailId);
			//generateMailMessage.
			Multipart mimeMultipart = new MimeMultipart();
			// /////////////////Email Subject //////////////////////////
			// Battery installation submission
			generateMailMessage
					.setSubject(subject);
	
			// Add Email Body .....
			//String emailBody = "";
			MimeBodyPart emailContent = new MimeBodyPart();
			emailContent.setContent(body,"text/html");
	        mimeMultipart.addBodyPart(emailContent);
	
	        generateMailMessage.setContent(mimeMultipart);
	        generateMailMessage.setFrom(new InternetAddress("no-reply@mypower.energex.com.au"));
	        
			System.out.println("Mail Session has been created successfully..");
			
			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			transport.sendMessage(generateMailMessage,
					generateMailMessage.getAllRecipients());
		}
		
		transport.close();
		
	}

	
	private void DeleteImageDirectory(){
        
        // Delete the file
        log.info("Deleting Image Directory START");
        
        File directory = new File("/usr/share/tomcat7/webapps/MyPowerApp/tmpDir2/");
        
    	//make sure directory exists
    	if(!directory.exists()){
 
           log.info("Directory does not exist.");
           
        }else{
 
           try{
        	   
               delete(directory);
        	
           }catch(IOException e){
               e.printStackTrace();
               
           }
        }
	    
        log.info("Deleting Image Directory END");
	}

	public void delete(File file)
	    	throws IOException{
	 
	    	if(file.isDirectory()){
	 
	    		//directory is empty, then delete it
	    		if(file.list().length==0){
	    			
	    		   file.delete();
	    		   log.info("Directory has no files. Directory is deleted : " 
	                                                 + file.getAbsolutePath());
	    			
	    		}else{// There are few files in the directory.
	    			
	    		   //list all the directory contents
	        	   String files[] = file.list();
	     
	        	   for (String temp : files) {
	        	      //construct the file structure
	        	      File fileDelete = new File(file, temp);
	        		 
	        	      //recursive delete
	        	     delete(fileDelete);
	        	   }
	        		
	        	   //check the directory again, if empty then delete it
	        	   if(file.list().length==0){
	           	     file.delete();
	        	     log.info("Directory is deleted : " 
	                                                  + file.getAbsolutePath());
	        	   }
	    		}
	    		
	    	}else{
	    		//if file, then delete it
	    		file.delete();
	    		log.info("File is deleted : " + file.getAbsolutePath());
	    	}
	    }
/*	
	private MimeBodyPart converImageToBase64DataAndAttach(){
		String imageDataString = null;
		
		File file = new File("d:\\cto\\download.jpg");
		// create the second message part
        MimeBodyPart mbp2 = new MimeBodyPart();
	    try {            
	        // Reading a Image file from file system
	        FileInputStream imageInFile = new FileInputStream(file);
	        byte imageData[] = new byte[(int) file.length()];
	        imageInFile.read(imageData);
	
	        // Converting Image byte array into Base64 String
	        imageDataString = encodeImage(imageData);
	
	        // Converting a Base64 String into Image byte array
	        byte[] imageByteArray = decodeImage(imageDataString);
	
	        // Write a image byte array into file system
	        FileOutputStream imageOutFile = new FileOutputStream(
	                "d:\\cto\\download-after-convert.jpg");
	        
	     

	        FileDataSource fds = new FileDataSource("d:\\cto\\download-after-convert.jpg");
	        try {
				mbp2.setDataHandler(new DataHandler(fds));
				mbp2.setFileName(fds.getName());
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	        
	        //emailAttachment(imageDataString, "ImageToBase64Attachment.jpg");
	        
	        imageOutFile.write(imageByteArray);
	
	        imageInFile.close();
	        imageOutFile.close();
	
	        System.out.println("Image Successfully Manipulated!");
	    } catch (FileNotFoundException e) {
	        System.out.println("Image not found" + e);
	    } catch (IOException ioe) {
	        System.out.println("Exception while reading the Image " + ioe);
	    }
	    //return emailAttachment(imageDataString, "ImageToBase64Attachment.jpg");
	    return mbp2;
	    
	}

	private MimeBodyPart convertImageToByteArrayAndAttach(){
		String imageDataString = null;
		 byte[] imageByteArray = null;
		File file = new File("d:/cto/download.jpg");
    	
	    try {            
	        // Reading a Image file from file system
	        FileInputStream imageInFile = new FileInputStream(file);
	        byte imageData[] = new byte[(int) file.length()];
	        imageInFile.read(imageData);
	
	        // Converting Image byte array into Base64 String
	        imageDataString = encodeImage(imageData);
	
	        // Converting a Base64 String into Image byte array
	        imageByteArray = decodeImage(imageDataString);
	        
	        // Write a image byte array into file system
	        FileOutputStream imageOutFile = new FileOutputStream(
	                "d:/cto/download.jpg-after-convert.jpg");
	
	        imageOutFile.write(imageByteArray);
	
	        imageInFile.close();
	        imageOutFile.close();
	
	        System.out.println("Image Successfully Manipulated!");
	    } catch (FileNotFoundException e) {
	        System.out.println("Image not found" + e);
	        return null;
	    } catch (IOException ioe) {
	        System.out.println("Exception while reading the Image " + ioe);
	        return null;
	    }
	    
	    try {
			String imageDataStr = new String(imageByteArray,"UTF-8");
			return emailAttachment(imageDataStr, "ImageToBase64Attachment.jpg");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	   
	}
	*/
	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(byte[] imageByteArray) {
	    return Base64.encodeBase64URLSafeString(imageByteArray);
	}
	
	
	public static String createEmailMessage(ExRapRegistration user,String contactFormType,String Type,String Location,String Lat, String Lon, String Address,String detail){
		String emailBody = 
				"<p>The following was entered into the Report a Problem - "+ contactFormType+": "+ Type
				
				+ "</p>"
				+ "<p>&nbsp;</p>"
				
				+ "<p>Type</p>"
				+ "<table width=\"400\" cellspacing=\"0\" cellpadding=\"5\" border=\"1\">"
				+ "<tr><td>Type </td><td>"
				+ Type
				+ "</td></tr>"
				+ "</table>"

				+ "<p>&nbsp;</p>"
				+ "<p>Location</p>"
				+ "<table width=\"400\" cellspacing=\"0\" cellpadding=\"5\" border=\"1\">"
				+ "<tr><td>Latitude </td><td> "
				+ Lat
				+ "</td></tr>"
				+ "<tr><td>Longitude </td><td> "
				+ Lon
				+ "</td></tr>"
				+ "<tr><td>Location ( closest ) </td><td>"
				+ Address
				+ "</td></tr>"
				+ "</table>"
				+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>"
				+ "<p>Details</p>"
				+ "<table width=\"400\" cellspacing=\"0\" cellpadding=\"5\" border=\"1\">"
				+ "<tr><td>Details </td><td> " + detail
				+ "</td></tr>" 
				+ "</table>"
				
				+ "<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>"
				+ "<p>User Details</p>"
				+ "<table width=\"400\" cellspacing=\"0\" cellpadding=\"5\" border=\"1\">"
				+ "<tr><td>Email ID </td><td> "
				+ user.getEmailID() + "</td></tr>"
				+ "<tr><td>First Name </td><td> "
				+ user.getFirstName() + "</td></tr>"
				+ "<tr><td>Last Name </td><td> "
				+ user.getLastName() + "</td></tr>"
				+ "<tr><td>Phone </td><td>" 
				+ user.getPhoneNumber()
				+ "</td></tr>" + "<tr><td>Post Code </td><td>"
				+ user.getPostCode() + "</p>"
				+ "</table>"
				+ "<p>&nbsp;</p>" + "<p>Kind regards,</p>"
				+ "<p>Energex Trial Team.&nbsp;</p>";
		return emailBody;
	}
	
}

package com.sparq.MyPowerApp;
import model.ExRapBackgroundimage;

import org.apache.catalina.util.URLEncoder;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.sparq.configuration.ConfigReader;
import com.sparq.utils.AppZip;


/**
 * A Java servlet that handles file upload from client.
 * @author www.codejava.net
 */
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "upload";
	private static final int THRESHOLD_SIZE 	= 1024 * 0; 	// 3KB
	private static final int MAX_FILE_SIZE 		= 1024 * 512 * 4; // 1MB
	private static final int MAX_REQUEST_SIZE 	= 1024 * 1024 * 5; // 5MB
	
	private Logger log = Logger.getLogger(RESTService.class.getName());
	private final String PERSISTENCE_UNIT_NAME = "BatteryAppService";
	private EntityManagerFactory factory;

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
	
	private byte[] encodeFileToBase64Binary(File originalFile)
			throws IOException {

		byte[] encodedBase64 = null;
        String urlDecodedData = "";
        
        
        
        try {
        	 
            FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
            
            byte[] bytes = new byte[(int)originalFile.length()];
            System.out.println("FILE UPLOAD .......... originalFile length: "+ bytes.length);
            fileInputStreamReader.read(bytes);
            
            encodedBase64 = Base64.encodeBase64(bytes);
            
            //replace all +es with spaces
            urlDecodedData = new String(encodedBase64);
            //urlDecodedData.replaceAll("+", " ");
            //encodedBase64 = bytes;
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //return encodedBase64;
        return urlDecodedData.getBytes();
	}
	 private String getCurrentDir () {

		   String workingDir = System.getProperty("user.dir");
		   log.info("Current working directory : " + workingDir);
		   return workingDir;
		   
	   }
	
	
public String postBackgroundImage(String inputPostJson) {
		
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
				return result;
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
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception in Service" + e.getMessage());
			
			throw new WebApplicationException(e,
					javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR);
		}

	}
	
private boolean saveImageDatatoDBDirect(byte[] base64String){
	
	
	DefaultHttpClient httpClient = new DefaultHttpClient();
	
	boolean retVal = false;
	//
	StringEntity userEntity = null;
	String str = "";
	try {
		str = "{\"Operation\":\"SetRapAppBkgImage\",\"ImageData\":\"" + new String(base64String) + "\"}";
		userEntity = new StringEntity(str);
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	String output = "";
	
	output = postBackgroundImage(str);

			
	if(!output.contains("Partial JSON input")){
		retVal = true;
	}
	
	return retVal;
}

	private boolean saveImageDatatoDB(byte[] base64String){
		
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		
		boolean retVal = false;
		try {

				
			// Testing RAP APP background Image
			//getVersionOfRapAppBackgroundImage
			//getRapAppBackgroundImage
			//postRapAppBackgroundImage
			
			//
			String str = "";
			StringEntity userEntity = null;
			try {
				str = "{\"Operation\":\"SetRapAppBkgImage\",\"ImageData\":\"" + new String(base64String) + "\"}";
				userEntity = new StringEntity(str);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//HttpGet postRequest = new HttpGet(
			//		"http://localhost:8080/BatteryAppService/rest/postRapAppBackgroundImage");
			//postRequest.addHeader("content-type", "text/plain");
			//https://sparq-dev.konycloud.com/MyTestApp/rest/
			/*HttpPut postRequest = new HttpPut(
					"http://sparq-dev.konycloud.com/MyTestApp/rest/processInput");*/
			HttpPost postRequest = new HttpPost(
					//"http://sparq.konycloud.com/MyPowerApp/rest/postRapAppBackgroundImage");
					"http://localhost/MyPowerApp/rest/postRapAppBackgroundImage");
			//HttpPost postRequest = new HttpPost(
			//		"http://sparq-dev.konycloud.com/BatteryAppService/rest/postRapAppBackgroundImage");
			//postRequest.addHeader("content-type", "text/plain");
			//postRequest.setEntity(userEntity);
			
			HttpResponse response = null;
			try {
				response = httpClient.execute(postRequest);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// verify the valid error code first
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				throw new RuntimeException("Failed with HTTP error code : "	+ statusCode);
				
			}
			BufferedReader br = null;
			try {
				br = new BufferedReader(new InputStreamReader(
						(response.getEntity().getContent())));
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			String output;
			
			try {
				while ((output = br.readLine()) != null) {
					System.out.println(output);
					
					if(!output.contains("Partial JSON input")){
						retVal = true;
					}

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return retVal;
	}
	
	/**
	 * handles file upload via HTTP POST method
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// checks if the request actually contains upload file
		System.out.println("FILE UPLOAD .......... START");
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Request does not contain upload data");
			writer.flush();
			return;
		}
		
		// configures upload settings
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(MAX_REQUEST_SIZE);
		
		// constructs the directory path to store upload file
		String uploadPath = getServletContext().getRealPath("")
			+ File.separator + UPLOAD_DIRECTORY;
		
		
		// creates the directory if it does not exist
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		
		try {
			// parses the request's content to extract file data
			List formItems = upload.parseRequest(request);
			Iterator iter = formItems.iterator();
			
			// iterates over form's fields
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();
				// processes only fields that are not form fields
				if (!item.isFormField()) {
					String fileName = new File(item.getName()).getName();

					String filePath = uploadPath + File.separator + fileName;
					
					if( item.getSize() <= 716800){
						File storeFile = new File(filePath);
						
						item.write(storeFile);
						
						//System.out.println("FILE UPLOAD .......... READ FILE: "+ storeFile);
						byte[] encodedString = encodeFileToBase64Binary(storeFile);
						
						System.out.println("FILE UPLOAD .......... encodedString length: "+ encodedString.length);
						
						byte[] decodedData = Base64.decodeBase64(encodedString);
						System.out.println("FILE UPLOAD .......... decodedString length: "+ decodedData.length);
						 /*FileOutputStream fos = new FileOutputStream(new File("D:/upload/abc4.jpg")); 
						    fos.write(decodedData); 
						    fos.close();*/
						
						//System.out.println("FILE UPLOAD .......... ENCODED FILE: "+ new String(encodedString));
						   /* FileOutputStream fos2 = new FileOutputStream(new File("D:/upload/endocedImageBeforeSending.jpg")); 
						    fos2.write(encodedString); 
						    fos2.close();*/
						boolean val = saveImageDatatoDBDirect(encodedString);
						System.out.println("FILE UPLOAD .......... saveImageDatatoDB VAL " + val);
						if(val){
							request.setAttribute("message", "Upload has been done successfully!");
						}else{
							request.setAttribute("message", "There was an error saving this file");
						}
						
						System.out.println("FILE UPLOAD .......... FILE SAVED TO DB ");
					}else{
						request.setAttribute("message", "Please upload PNG images below 700KB in size!");
					}
				}
			}
			
		} catch (Exception ex) {
			request.setAttribute("message", "There was an error: " + ex.getMessage());
		}
		getServletContext().getRequestDispatcher("/upload.jsp").forward(request, response);
	}
}

package com.sparq.configuration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.sparq.MyPowerApp.RESTService;


public class ConfigReader {
	
	    static XMLConfiguration config;	    
	    static Logger log = Logger.getLogger( ConfigReader.class.getName());
	   
		public static final class OsUtils
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

		 public static String getCurrentDir () {

			   String workingDir = System.getProperty("user.dir");
			   log.info("Current working directory : " + workingDir);
			   return workingDir;
			   
		   }
		 
	    public static Map <String, String> getJPAProperties(){
			Map <String, String> properties = new HashMap<String,String>();
			String currDir = getCurrentDir();
			log.info("CURRENT DIR DIR:::::"+currDir+ ":::: OS NAME::::"+OsUtils.getOsName());
			
			//properties.put("javax.persistence.jdbc.url", "jdbc:mysql://sparq-dev.cxss2btawhxj.ap-southeast-2.rds.amazonaws.com:3306/sparq?verifyServerCertificate=false&useSSL=true&requireSSL=true&");
			properties.put("javax.persistence.jdbc.url", "jdbc:mysql://sparq-prod.cxss2btawhxj.ap-southeast-2.rds.amazonaws.com:3306/sparq?verifyServerCertificate=false&useSSL=true&requireSSL=true&");
		    properties.put("javax.persistence.jdbc.user", "sparquser");
		    properties.put("javax.persistence.jdbc.password", "xgt6mnWx235ngrbad");
		    //properties.put("javax.persistence.jdbc.url", "jdbc:mysql://localhost:3306/sparq");
		    //properties.put("javax.persistence.jdbc.user", "root");
		    //properties.put("javax.persistence.jdbc.password", "wipro@123");
		    properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
		    properties.put("eclipselink.connection-pool.default.initial", "20");
		    properties.put("eclipselink.connection-pool.default.min", "0");
		    properties.put("eclipselink.connection-pool.default.max", "50");
		    properties.put("eclipselink.logging.level", "INFO");
		    properties.put("eclipselink.id-validation", "NONE");
		    properties.put("eclipselink.sharedCache.mode", "None");
		    properties.put("eclipselink.jdbc.cache-statements", "false");
		    properties.put("eclipselink.query-results-cache", "false");
		    properties.put("eclipselink.cache.shared.default", "false");
		    properties.put("eclipselink.logging.exceptions", "false");
		    properties.put("eclipselink.weaving", "static");
		    properties.put("eclipselink.jdbc.batch-writing", "JDBC");
		    properties.put("eclipselink.jdbc.batch-writing.size", "1000");
		    
		    if(OsUtils.isWindows()){
		    	System.setProperty("javax.net.ssl.keyStore","D:/CTO/BatteryApp/DBScript/keystore");
		    }else{
		    	System.setProperty("javax.net.ssl.keyStore","usr/share/tomcat7/webapps/MyPowerApp/keystore");
		    }
			System.setProperty("javax.net.ssl.keyStorePassword","abcdef");
			if(OsUtils.isWindows()){
				System.setProperty("javax.net.ssl.trustStore","D:/CTO/BatteryApp/DBScript/truststore");
			}else{
				System.setProperty("javax.net.ssl.trustStore","usr/share/tomcat7/webapps/MyPowerApp/truststore");
			}
			System.setProperty("javax.net.ssl.trustStorePassword","abcdef");
			
			return properties;
		}
		
		public static String  getParameter(String key){
			
			if  ( config == null ){
				
				try{
					
					URL url  = Thread.currentThread().getContextClassLoader().getResource("/config.xml");
					
					if( url == null ) {
						
						url  = Thread.currentThread().getContextClassLoader().getResource("config.xml");
					}
				    config = new XMLConfiguration(url);
				}
				catch( ConfigurationException cex )
				{
				    // something went wrong, e.g. the file was not found
					cex.printStackTrace();
				}
		   }
			
		// logger.log(Level.INFO,"Parameter Key="+key+"="+config.getString(key));	
	        return  config.getString(key);
	   
		}	
		
		public static String []  getParameters(String key){
			
			if  ( config == null ){
				
				try{
					
					URL url  = Thread.currentThread().getContextClassLoader().getResource("/config.xml");
					
					if( url == null ) {
						
						url  = Thread.currentThread().getContextClassLoader().getResource("config.xml");
					}
				    config = new XMLConfiguration(url );
				}
				catch( ConfigurationException cex )
				{
				    // something went wrong, e.g. the file was not found
					cex.printStackTrace();
				}
		   }
			
		// logger.log(Level.INFO,"Parameter Key="+key+"="+config.getString(key));	
	        return  config.getStringArray(key);
	   
		}	

}


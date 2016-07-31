package com.sparq.emailfunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.ExRapFaultystreetlight;
import model.ExRapGraffiti;
import model.ExRapRegistration;
import model.ExRapTreetrimming;
import model.ExRapUpdatedetail;


public class EmailFunction {
	
	public String getTypefromID(String type){
		String result = "";
		
		String inputJson = "{\"dogDetails\":\"Update your dog details\",\"meterAccess\":\"Update your meter access\",\"ttCloseToPowerlines\":\"Trees close to powerlines\",\"ttDebrisRemoval\":\"Debris removal\",\"streetLightsOut\":\"Flickering or street lights out\",\"24HrsADay\":\"Lights on 24 hours a day\",\"lightsDamaged\":\"Dirty, broken or damaged lights\",\"poleDamaged\":\"Street light pole damaged/leaning\",\"otherStreetLightIssues\":\"Other street light issues\",\"eBuildings\":\"On Energex buildings\",\"ePublicProperty\":\"On Energex equipment on public property\",\"ePrivateProperty\":\"On Energex equipment on private property\"}";
		
		JSONObject jsonInput = null;
		try {
			//log.info("valid json string::::" + inputJson);
			jsonInput = new JSONObject(inputJson);
			
			result = (String) jsonInput.get(type);
			//log.info(type+":"+jsonInput.get(type));
		    	
	
			//log.info("valid json string::::");
		} catch (JSONException e) {
			//log.info("invalid json string::::" + inputJson);
			//log.info("Invalid json string::::");
			
		}
		
		return result;
	}
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
	
	public String faultyStreetLightEnergexCopy(ExRapFaultystreetlight obj,ExRapRegistration obj2,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception
	{
		File input = null;
		
		if(OsUtils.isWindows())
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\FaultyStreetlights\\table1.htm");
		else
			input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/FaultyStreetlights/table1.htm");
		
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getFaultyStreetLightType()));
			}
			else if (e.text().contains("Latitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsMapLatitude());
			}
			else if (e.text().contains("Longitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsMapLongitude());
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Lights affected"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsAffectedRadioBtnValue());
			}
			else if (e.text().contains("Additional Information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsAddInfo());
			}
			else if (e.text().contains("Email"))
			{
				i++;
				e = tds.get(i);
				if(obj2!=null)
				e.getElementsByTag("p").get(0).text(obj2.getEmailID());
			}
			else if (e.text().contains("First name"))
			{
				i++;
				e = tds.get(i);
				if(obj2!=null)e.getElementsByTag("p").get(0).text(obj2.getFirstName());
			}
			else if (e.text().contains("Last name"))
			{
				i++;
				e = tds.get(i);
				if(obj2!=null)e.getElementsByTag("p").get(0).text(obj2.getLastName());
			}
			else if (e.text().contains("Phone"))
			{
				i++;
				e = tds.get(i);
				if(obj2!=null)e.getElementsByTag("p").get(0).text(obj2.getPhoneNumber());
			}
			else if (e.text().contains("Postcode"))
			{
				i++;
				e = tds.get(i);
				if(obj2!=null)e.getElementsByTag("p").get(0).text(obj2.getPostCode());
			}
			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		/*final File f = new File("C:\\Users\\pr294252\\Desktop\\table2.htm");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(doc.outerHtml());
		
		bw.close();*/
		
		String finOne=doc.outerHtml().toString();
		return finOne;
	} 
	public String faultyStreetLightCustomerCopy(ExRapFaultystreetlight obj,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception{
		File input = null;
		
		if(OsUtils.isWindows())
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\FaultyStreetlights\\table2.htm");
		else
			input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/FaultyStreetlights/table2.htm");
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getFaultyStreetLightType()));
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Lights affected"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsAffectedRadioBtnValue());
			}
			else if (e.text().contains("Additional Information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getFslightsAddInfo());
			}

			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		//		final File f = new File("C:\\Users\\pr294252\\Desktop\\table_two_modified.htm");
		//		if (!f.exists())
		//			f.createNewFile();
		//		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//
		//		bw.write(doc.outerHtml());
		//
		//		bw.close();
		String finTwo=doc.outerHtml().toString();
		return finTwo;	
	}
	public String updateDetailsCustomer(ExRapUpdatedetail obj) throws Exception{
		File input = null;
		
		if(OsUtils.isWindows()){
			if(obj.getUpdateDetailsType().contains("dogDetails"))
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\UpdateDetails\\table4.htm");
			else
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\UpdateDetails\\table6.htm");
		}
		else{
			if(obj.getUpdateDetailsType().contains("dogDetails"))
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/UpdateDetails/table4.htm");
			else
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/UpdateDetails/table6.htm");
		}
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getUpdateDetailsType()));
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getUpdateDetailsMapAddress());
			}
			else if (e.text().contains("Is there a dog at your property?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogAtYourPropertyRadioBtnValue());
			}
			else if (e.text().contains("Would it pose a danger to our meter readers?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDangerToMeterReadersRadioBtnValue());
			}
			else if (e.text().contains("Is the dog permanently located away from the meter?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogPermanentLocationRadioBtnValue());
			}
			else if (e.text().contains("Breed of dog"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogBreed());
			}
			else if (e.text().contains("Do you have an Energex lock?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessEnergexLockRadioBtnValue());
			}
			else if (e.text().contains("Energex lock PIN"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessPinCode());
			}
			else if (e.text().contains("Additional access information") && obj.getUpdateDetailsType().contains("dogDetails"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogDetailsAddAccessInformation());
			}
			else if (e.text().contains("Additional access information") && obj.getUpdateDetailsType().contains("meterAccess"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessAddAccessInformation());
			}

			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}
		String finThree=doc.outerHtml().toString();
		//System.out.print(finThree);
		return finThree;
	}
	public String updateDetailsEnergex(ExRapUpdatedetail obj,ExRapRegistration obj2) throws Exception{
		File input = null;
		
		if(OsUtils.isWindows()){
			if(obj.getUpdateDetailsType().contains("dogDetails"))
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\UpdateDetails\\table3.htm");
			else
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\UpdateDetails\\table5.htm");
		}
		else{
			if(obj.getUpdateDetailsType().contains("dogDetails"))
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/UpdateDetails/table3.htm");
			else
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/UpdateDetails/table5.htm");
		}
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getUpdateDetailsType()));
			}
			else if (e.text().contains("Latitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getUpdateDetailsMapLatitude());
			}
			else if (e.text().contains("Longitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getUpdateDetailsMapLongitude());
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getUpdateDetailsMapAddress());
			}
			else if (e.text().contains("Is there a dog at your property?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogAtYourPropertyRadioBtnValue());
			}
			else if (e.text().contains("Would it pose a danger to our meter readers?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDangerToMeterReadersRadioBtnValue());
			}
			else if (e.text().contains("Is the dog permanently located away from the meter?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogPermanentLocationRadioBtnValue());
			}
			else if (e.text().contains("Breed of dog"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogBreed());
			}
			else if (e.text().contains("Do you have an Energex lock?"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessEnergexLockRadioBtnValue());
			}
			else if (e.text().contains("Energex lock PIN"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessPinCode());
			}
			else if (e.text().contains("Additional access information") && obj.getUpdateDetailsType().contains("dogDetails"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getDogDetailsAddAccessInformation());
			}
			else if (e.text().contains("Additional access information") && obj.getUpdateDetailsType().contains("meterAccess"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getMeterAccessAddAccessInformation());
			}
			else if (e.text().contains("Email"))
			{
				i++;
				e = tds.get(i);
				//System.out.println("obj2.getEmailID()" + obj2.getEmailID());
				if(obj2 != null)
					e.getElementsByTag("p").get(0).text(obj2.getEmailID());			
				
			}
			else if (e.text().contains("First name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getFirstName());
			}
			else if (e.text().contains("Last name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getLastName());
			}
			else if (e.text().contains("Phone"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPhoneNumber());
			}
			else if (e.text().contains("Postcode"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPostCode());
			}
			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}
		String finThree=doc.outerHtml().toString();
		//System.out.print(finThree);
		return finThree;
	}
	
	public String treeTrimmingEnergexCopy(ExRapTreetrimming obj,ExRapRegistration obj2,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception
	{
		File input = null;
		
		if(OsUtils.isWindows()){
			if(obj.getTreeTrimmingType().contains("ttDebrisRemoval"))
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Treetrimming\\table3.htm");
			else
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Treetrimming\\table6.htm");
		}
		else{
			if(obj.getTreeTrimmingType().contains("ttDebrisRemoval"))
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Treetrimming/table3.htm");
			else
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Treetrimming/table6.htm");
		}
		
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getTreeTrimmingType()));
			}
			else if (e.text().contains("Latitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingMapLatitude() );
			}
			else if (e.text().contains("Longitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingMapLongitude());
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Powerlines affected"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTtcloseToPowerlinesAffectedRadioBtnValue());
			}
			else if (e.text().contains("Distance from tree to powerline"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTtcloseToPowerlinesDistanceRadioBtnValue());
			}			else if (e.text().contains("Additional information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingAddAccessInfo());
			}
			else if (e.text().contains("Email"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getEmailID());
			}
			else if (e.text().contains("First name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getFirstName());
			}
			else if (e.text().contains("Last name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getLastName());
			}
			else if (e.text().contains("Phone"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPhoneNumber());
			}
			else if (e.text().contains("Postcode"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPostCode());
			}
			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		/*final File f = new File("C:\\Users\\pr294252\\Desktop\\table2.htm");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(doc.outerHtml());
		
		bw.close();*/
		
		String finOne=doc.outerHtml().toString();
		return finOne;
	} 
	
	public String treeTrimmingCustomerCopy(ExRapTreetrimming obj,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception{
		File input = null;
		
		if(OsUtils.isWindows()){
			if(obj.getTreeTrimmingType().contains("ttCloseToPowerlines"))
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Treetrimming\\table4.htm");
			else
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Treetrimming\\table5.htm");
		}
		else{
			if(obj.getTreeTrimmingType().contains("ttCloseToPowerlines"))
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Treetrimming/table4.htm");
			else
				input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Treetrimming/table5.htm");
		}
			
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getTreeTrimmingType()));
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Powerlines affected"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTtcloseToPowerlinesAffectedRadioBtnValue());
			}
			else if (e.text().contains("Distance from tree to powerline"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTtcloseToPowerlinesDistanceRadioBtnValue());
			}			
			else if (e.text().contains("Additional information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getTreeTrimmingAddAccessInfo());
			}

			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		//		final File f = new File("C:\\Users\\pr294252\\Desktop\\table_two_modified.htm");
		//		if (!f.exists())
		//			f.createNewFile();
		//		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//
		//		bw.write(doc.outerHtml());
		//
		//		bw.close();
		String finTwo=doc.outerHtml().toString();
		return finTwo;	
	}
	
	public String graffitiEnergexCopy(ExRapGraffiti obj,ExRapRegistration obj2,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception
	{
		File input = null;
		
		if(OsUtils.isWindows())
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Graffiti\\table1.htm");
		else
			input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Graffiti/table1.htm");
		
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getGraffitiType()));
			}
			else if (e.text().contains("Latitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiMapLatitude() );
			}
			else if (e.text().contains("Longitude"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiMapLongitude());
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Equipment"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiLabelRadioBtnValue());
			}
			else if (e.text().contains("Additional Information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiAddInfo());
			}
			else if (e.text().contains("Email"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)
				e.getElementsByTag("p").get(0).text(obj2.getEmailID());
			}
			else if (e.text().contains("First name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getFirstName());
			}
			else if (e.text().contains("Last name"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getLastName());
			}
			else if (e.text().contains("Phone"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPhoneNumber());
			}
			else if (e.text().contains("Postcode"))
			{
				i++;
				e = tds.get(i);
				if(obj2 != null)e.getElementsByTag("p").get(0).text(obj2.getPostCode());
			}
			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		/*final File f = new File("C:\\Users\\pr294252\\Desktop\\table2.htm");
		if (!f.exists())
			f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		bw.write(doc.outerHtml());bw.close();*/
		String finOne=doc.outerHtml().toString();
		
		return finOne;
	} 
	public String graffitiCustomerCopy(ExRapGraffiti obj,String ImageSize_1, String ImageSize_2,String ImageSize_3) throws Exception{
		File input = null;
		
		if(OsUtils.isWindows())
				input = new File("C:\\Users\\pr294252\\Desktop\\EmailTemplates\\Graffiti\\table2.htm");
		else
			input = new File("/usr/share/tomcat7/webapps/MyPowerApp/EmailTemplates/Graffiti/table2.htm");
		
		Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
		Elements tds = doc.getElementsByTag("td");
		int s = tds.size();
		for (int i = 0;i < s;i++)
		{
			Element e = tds.get(i);
			if (e.text().contains("Type"))
			{
				i++;
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(getTypefromID(obj.getGraffitiType()));
			}
			else if (e.text().contains("Address (closest)"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiMapAddress());
			}
			else if (e.text().contains("Image 1"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_1);
			}
			else if (e.text().contains("Image 2"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_2);
			}
			else if (e.text().contains("Image 3"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(ImageSize_3);
			}
			else if (e.text().contains("Equipment"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiLabelRadioBtnValue());
			}
			else if (e.text().contains("Additional Information"))
			{
				i++;
				e = tds.get(i);
				e.getElementsByTag("p").get(0).text(obj.getGraffitiAddInfo());
			}

			else if (e.text().contains("Submission date"))
			{
				i++;
				e = tds.get(i);
				if(obj.getTimeStamp() == null){
					Date date = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
					String formattedDate = sdf.format(date);
					e.getElementsByTag("p").get(0).text(formattedDate);
				}else{
					SimpleDateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					SimpleDateFormat outputformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
					e.getElementsByTag("p").get(0).text(outputformat.format(inputformat.parse(obj.getTimeStamp().toString())));
				}
			}
		}

		//		final File f = new File("C:\\Users\\pr294252\\Desktop\\table_two_modified.htm");
		//		if (!f.exists())
		//			f.createNewFile();
		//		BufferedWriter bw = new BufferedWriter(new FileWriter(f));
		//
		//		bw.write(doc.outerHtml());
		//
		//		bw.close();
		String finTwo=doc.outerHtml().toString();
		return finTwo;	
	}
}
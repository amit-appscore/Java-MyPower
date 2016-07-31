package com.sparq.MyPowerApp;

import java.io.File;
import java.io.IOException;
import java.util.Date;
 
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import model.ExRapFaultystreetlight;
import model.ExRapGraffiti;
import model.ExRapTreetrimming;
import model.ExRapUpdatedetail;
 
public class JavaToJsonHelper
{

	public static String  convertUpdatedetails(ExRapUpdatedetail updateDetails)
   {
	   String outPutJson = "";
	   
      @SuppressWarnings("deprecation")
      
      ObjectMapper mapper = new ObjectMapper();
      try
      {
    	  outPutJson = mapper.writeValueAsString(updateDetails);
      } catch (JsonGenerationException e)
      {
         e.printStackTrace();
      } catch (JsonMappingException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      }
      return outPutJson;
   }
	
	public static String  convertTreetrimming(ExRapTreetrimming treeTrimming)
	   {
		   String outPutJson = "";
		   
	      @SuppressWarnings("deprecation")
	      
	      ObjectMapper mapper = new ObjectMapper();
	      try
	      {
	    	  outPutJson = mapper.writeValueAsString(treeTrimming);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return outPutJson;
	   }
	
	public static String  convertGraffiti(ExRapGraffiti graffiti)
	   {
		   String outPutJson = "";
		   
	      @SuppressWarnings("deprecation")
	      
	      ObjectMapper mapper = new ObjectMapper();
	      try
	      {
	    	  outPutJson = mapper.writeValueAsString(graffiti);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return outPutJson;
	   }
	
	public static String  convertFaultystreetlights(ExRapFaultystreetlight faultyStreetLights)
	   {
		   String outPutJson = "";
		   
	      @SuppressWarnings("deprecation")
	      
	      ObjectMapper mapper = new ObjectMapper();
	      try
	      {
	    	  outPutJson = mapper.writeValueAsString(faultyStreetLights);
	      } catch (JsonGenerationException e)
	      {
	         e.printStackTrace();
	      } catch (JsonMappingException e)
	      {
	         e.printStackTrace();
	      } catch (IOException e)
	      {
	         e.printStackTrace();
	      }
	      return outPutJson;
	   }
}
 
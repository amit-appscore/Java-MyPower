package com.sparq.MyPowerApp;

import java.io.File;
import java.io.IOException;

import model.ExRapFaultystreetlight;
import model.ExRapGraffiti;
import model.ExRapTreetrimming;
import model.ExRapUpdatedetail;
 
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
 
public class JsonToJavaHelper
{
   public static ExRapUpdatedetail convertUpdateDetails(String inputJson)
   {
	  ExRapUpdatedetail updateDetails = null;
	   
      ObjectMapper mapper = new ObjectMapper();
      try
      {
    	  updateDetails =  mapper.readValue(inputJson, ExRapUpdatedetail.class);
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
      System.out.println(updateDetails);
      return updateDetails;
   }
   
   public static ExRapGraffiti convertGraffiti(String inputJson)
   {
	  ExRapGraffiti graffiti = null;
	   
      ObjectMapper mapper = new ObjectMapper();
      try
      {
    	  graffiti =  mapper.readValue(inputJson, ExRapGraffiti.class);
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
      System.out.println(graffiti);
      return graffiti;
   }
   
   public static ExRapTreetrimming convertTreetrimming(String inputJson)
   {
	  ExRapTreetrimming treeTrimming = null;
	   
      ObjectMapper mapper = new ObjectMapper();
      try
      {
    	  treeTrimming =  mapper.readValue(inputJson, ExRapTreetrimming.class);
    	  //mapper.writeValue(new File("c://temp/treeTrimming.json"), treeTrimming);
      } catch (JsonGenerationException e)
      {
         e.printStackTrace();
      } catch (JsonMappingException e)
      {
         e.printStackTrace();
      } catch (IOException e)
      {
         e.printStackTrace();
      } catch(Exception e){
    	  e.printStackTrace();
      }
      System.out.println("Get First Photo Size"+treeTrimming.getUserName());
      return treeTrimming;
   }
   
   public static ExRapFaultystreetlight convertFaultystreetLights(String inputJson)
   {
	  ExRapFaultystreetlight faultyStreetLights = null;
	   
      ObjectMapper mapper = new ObjectMapper();
      try
      {
    	  faultyStreetLights =  mapper.readValue(inputJson, ExRapFaultystreetlight.class);
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
      System.out.println(faultyStreetLights);
      return faultyStreetLights;
   }
}
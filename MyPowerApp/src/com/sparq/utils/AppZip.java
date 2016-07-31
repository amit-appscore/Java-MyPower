package com.sparq.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class AppZip
{
    List<String> fileList;
    private String OUTPUT_ZIP_FILE = "";//"C:\\MyFile.zip";
    private String SOURCE_FOLDER = "";//"C:\\testzip";
	
    /**
	 * @return the oUTPUT_ZIP_FILE
	 */
	public String getOUTPUT_ZIP_FILE() {
		return OUTPUT_ZIP_FILE;
	}

	/**
	 * @param oUTPUT_ZIP_FILE the oUTPUT_ZIP_FILE to set
	 */
	public void setOUTPUT_ZIP_FILE(String oUTPUT_ZIP_FILE) {
		OUTPUT_ZIP_FILE = oUTPUT_ZIP_FILE;
	}

	/**
	 * @return the sOURCE_FOLDER
	 */
	public String getSOURCE_FOLDER() {
		return SOURCE_FOLDER;
	}

	/**
	 * @param sOURCE_FOLDER the sOURCE_FOLDER to set
	 */
	public void setSOURCE_FOLDER(String sOURCE_FOLDER) {
		SOURCE_FOLDER = sOURCE_FOLDER;
	}

	public AppZip(){
	fileList = new ArrayList<String>();
    }
	
   /* public static void main( String[] args )
    {
    	AppZip appZip = new AppZip();
    	appZip.generateFileList(new File(SOURCE_FOLDER));
    	appZip.zipIt(OUTPUT_ZIP_FILE);
    }*/
    
    /**
     * Zip it
     * @param zipFile output ZIP file location
     */
    public void zipIt(String zipFile){

     byte[] buffer = new byte[1024];
    	
     try{
    		
    	FileOutputStream fos = new FileOutputStream(zipFile);
    	ZipOutputStream zos = new ZipOutputStream(fos);
    		
    	System.out.println("Output to Zip : " + zipFile);
    		
    	for(String file : this.fileList){
    			
    		System.out.println("File Added : " + file);
    		ZipEntry ze= new ZipEntry(file);
        	zos.putNextEntry(ze);
               
        	FileInputStream in = 
                       new FileInputStream(SOURCE_FOLDER + File.separator + file);
       	   
        	int len;
        	while ((len = in.read(buffer)) > 0) {
        		zos.write(buffer, 0, len);
        	}
               
        	in.close();
    	}
    		
    	zos.closeEntry();
    	//remember close it
    	zos.close();
          
    	System.out.println("Done");
    }catch(IOException ex){
       ex.printStackTrace();   
    }
   }
    
    /**
     * Traverse a directory and get all files,
     * and add the file into fileList  
     * @param node file or directory
     */
    public void generateFileList(File node){

    	//add file only
	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}
		
	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}
 
    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
}
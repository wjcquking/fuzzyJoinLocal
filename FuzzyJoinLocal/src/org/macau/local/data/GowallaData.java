package org.macau.local.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**********************************************
 * 
 * @author mb25428
 * 
 * This class convert the Gowalla Data to the normal data
 * 
 * The basic Information
 * Record Attribute:user,time,lat,lon,locationID
 * relation:User1, user2
 *
 * The Convert form
 * ID:locationID:lat:lon:time:textual
 ********************************************/
public class GowallaData {

	public static final File gRecordFile = new File("D:\\360Downloads\\data\\gowalla\\Gowalla_totalCheckins.txt");
	public static final File rRelationFile = new File("D:\\360Downloads\\data\\gowalla\\Gowalla_edges.txt");
	public static final File gConvertFile = new File("D:\\360Downloads\\data\\gowalla\\Gowalla.txt");
	public static final File gStatisFile = new File("D:\\360Downloads\\data\\gowalla\\Gowalla_statis.txt");
	
	
	public static final File bRecordFile = new File("D:\\360Downloads\\data\\brightkite\\Brightkite_totalCheckins.txt");
	public static final File bRelationFile = new File("D:\\360Downloads\\data\\brightkite\\Brightkite_edges.txt");
	public static final File bConvertFile = new File("D:\\360Downloads\\data\\brightkite\\Brightkite.txt");
	public static final File bStatisFile = new File("D:\\360Downloads\\data\\brightkite\\Brightkite_statis.txt");
	
	public static final long threshold = 1* 1000 * 60 * 60 *24;
	
	public static void writeToFile(File readFile,File edgeFile, File writeFile){
		
        BufferedReader reader = null;
        
        
        try {
            System.out.println("Read one line");
            
            
            
            reader = new BufferedReader(new FileReader(readFile));
            String tempString = null;
            
            
            if(!writeFile.exists()){
            	writeFile.createNewFile();
            }
            
            FileWriter writer = new FileWriter(writeFile);
            
           int i = 0;
            
            while ((tempString = reader.readLine()) != null) {
            	String[] values = tempString.split("\\s+");

            	if(values.length > 2){
	        		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	        		String dateStr = "";
	        		try {
	//        			System.out.println(tempString);
	        			String a = values[1].replace('T', ' ');
	        			String b = a.replace("Z", "");
	//        			System.out.println(b);
	        			dateStr = format1.parse(b).getTime() + "";
	//        			System.out.println(dateStr);
	        			
	        		} catch (Exception e) {
	        			// TODO Auto-generated catch block
	        			System.out.println(i + " " + tempString);
	        			e.printStackTrace();
	        		}   
	        		
	        		String result =i++ + ":"+ values[4] + ":" + values[2] + ":" + values[3] + ":"+ dateStr + "\n";
	        		writer.write(result);
            	}
            }
            
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	public static void getStatistic(File readFile,File writeFile){
		BufferedReader reader = null;
	    
		Map<Integer,Integer> countMap = new HashMap<Integer,Integer>();
	        
        try {
            System.out.println("Read one line");
            
            
            
            reader = new BufferedReader(new FileReader(readFile));
            String tempString = null;
            
            
            if(!writeFile.exists()){
            	writeFile.createNewFile();
            }
            
            FileWriter writer = new FileWriter(writeFile);
            
           int i = 0;
            
            while ((tempString = reader.readLine()) != null) {
            	String[] values = tempString.split(":");
            	
            	long date = Long.parseLong(values[4]);
            	
            	int tag = (int) (date/threshold);
            	
            	if(countMap.containsKey(tag)){
            		countMap.put(tag, countMap.get(tag)+1);
            	}else{
            		countMap.put(tag, 1);
            	}
            	

        		
            	
            }
            
            for(Map.Entry<Integer, Integer> entry: countMap.entrySet())
            {
            	writer.write(entry.getKey() + " " + entry.getValue() + "\n");
//            	System.out.println(entry.getKey() + " " + entry.getValue());
            }
            
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	public static void main(String[] args){
		
//		writeToFile(gRecordFile,rRelationFile,gConvertFile);
		getStatistic(gConvertFile,gStatisFile);
		
//		writeToFile(bRecordFile,bRelationFile,bConvertFile);
//		getStatistic(bConvertFile,bStatisFile);
		System.out.println("OK");
		
		String [][] a = new String[1][];
		
	}
}

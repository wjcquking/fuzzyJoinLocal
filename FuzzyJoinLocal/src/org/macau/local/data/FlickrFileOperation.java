package org.macau.local.data;

/**
 * author: wangjian
 * description: Get the Data from YYY about the Flickr
 * 
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FlickrFileOperation {
	
	
	
	public static int DataNumber = 796427;
	public final static String separator = ":";
	
	
	public final static String city = "Paris";
	
	public final static String rawRecordPath = "D:/360Downloads/data/"+city+"/"+city+"DataProBuf.data";
	public final static String rawTextualPath = "D:/360Downloads/data/"+city+"/"+city+"RawPhtToTags.txt";
	public final static String outputPath = "D:/360Downloads/data/"+city+"/"+city+".data";
	
	
	
	
	public static String[] readFileByLines(String fileName) {
		
        File file = new File(fileName);
        BufferedReader reader = null;
        String[] records = new String[DataNumber];
        
        try {
            System.out.println("Read one line");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            String[] timestamp = null;
            String[] location = null;
            String[] locationID = null;
            String[] pictureID = null;
            // One line one time until the null
            while ((tempString = reader.readLine()) != null) {
                // The line Number
                System.out.println("line " + line  + " : " + tempString.split(";").length);

                switch(line){
                //line 2: the timestamp of the picture
                case 2:
                	timestamp = tempString.split(";");
                	break;
                case 4:
                	location = tempString.split(";");
                case 5:
                	locationID = tempString.split(";");
                case 8:
                	pictureID = tempString.split(";");
                }

                line++;
            }
            
            for(int i = 0; i < pictureID.length;i++){
            	records[i] = pictureID[i] + separator+ locationID[i] + separator +location[Integer.parseInt(locationID[i])*2] + separator+ location[Integer.parseInt(locationID[i])*2+1];
            	records[i] += separator+ timestamp[i];
            }
            
            reader.close();
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
        return records;
    }
	
	
	/**
	 * 
	 * @param fileName
	 * @return
	 * the Data form: 
	 * 1725
	 * 0;82;231;368;2479
	 */
	
	public static String[] readTextualByLines(String fileName) {
		
        File file = new File(fileName);
        BufferedReader reader = null;
        
        String[] textual = new String[DataNumber];
        try {
            System.out.println("Read one line");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;

            
            int locationNumber = 0;
            
            // One line one time until the null
            while ((tempString = reader.readLine()) != null) {
            	
                if(line % 2 == 1){
                	
                	locationNumber = Integer.parseInt(tempString);

                }else if(line % 2 == 0 ){
                	
                	textual[locationNumber] = tempString;
              
                }
                
                line++;
            }
            
            reader.close();
            
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
        return textual;
    }
	
	public static void readRecord(){
		
	}
	
	
	public static void readTextual(){
		
	}
	
	
	public static void createData(int number, int startPoint){
		
		System.out.println("Get The file of Paris");
		String[] records = readFileByLines(rawRecordPath);
		
		System.out.println("Get the textual information of Paris");
		String[] textual = readTextualByLines(rawTextualPath);
		
		System.out.println(records.length);
		System.out.println(textual.length);
		
//		String outputPath ="D:\\Data\\Flickr\\paris.even.data";
		try
		{

			FileWriter writer = new FileWriter(outputPath);
			
			for(int i = startPoint; i < number;i+=1){
				
				writer.write(records[i] + separator + textual[i] +"\n");;
				
			}
			
			//close the write
			writer.close();
		}
		
		catch(IOException iox)
		{
			System.out.println("Problemwriting" + outputPath);
		}
		
	}
	public static void main(String[] args){
		createData(DataNumber,0);
		
		
//		System.out.println("Get The file of Paris");
//		String path = "C:/Users/mb25428/Dropbox/06-hadoop/01-Data/Flickr/InitData/InitData/ParisDataProBuf.data";
//		String[] records = readFileByLines(path);
//		
//		System.out.println("Get the textual information of Paris");
//		String textualPath = "C:/Users/mb25428/Dropbox/06-hadoop/01-Data/Flickr/Tag/ParisRawPhtToTags.txt";
//		String[] textual = readTextualByLines(textualPath);
//		
//		System.out.println(records.length);
//		System.out.println(textual.length);
//		
//		String outputPath ="D:\\paris.even.10000.data";
//		try
//		{
//
//			FileWriter writer = new FileWriter(outputPath);
//			
//			for(int i = 0; i < 10000;i+=2){
//				writer.write(records[i] + FlickrDataLocalUtil.separator + textual[i] +"\n");;
//			}
//			
//			//close the write
//			writer.close();
//		}
//		
//		catch(IOException iox)
//		{
//			System.out.println("Problemwriting" + outputPath);
//		}
	}
}

package org.macau.local.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*********************************************************************
 * 
 * @author mb25428
 * 
 * This partition method is used for the computation Cost
 * Because the real execution time is related to the computation time not the data skew
 * So We Change our objective function to get the computation Cost
 *
 *
 *
 * Create User : mb25428
 * Create Date : 2015-01-28
 * Last Modify User : mb25428
 * Last Modify Date : 2015-01-28
 * 
 */
public class MinMaxComputationPartition {
	public static final File file = new File("D:\\360Downloads\\count\\count_7_rs\\part-r-00000");
	
	
	//The R File Address
	public static final File rFile = new File("D:\\360Downloads\\count\\count_7_r\\part-r-00000");
	public static final File sFile = new File("D:\\360Downloads\\count\\count_7_s\\part-r-00000");
	
	
	public static final int rangeCount = 3000;
	//The Size of the sequence
	public static final int arrayCount = 597;
	public static int N = arrayCount;
	
	public static int[] countArray = new int[arrayCount];
	public static int[] idArray = new int[arrayCount];
	
	
	//For the computation balance
	public static int[] rCountArray = new int[rangeCount];
	public static int[] sCountArray = new int[rangeCount];
	
	
	public static final int idPosition = 0;
	public static final int countPosition = 1;
	
	
	//The Partition Number
	public static final int k = 25;
	
	public static int loop = 0;
	
	public static boolean[][][] tagArray = new boolean[arrayCount][arrayCount][k];
	
	
	//Use two dimension array to record the result
	public static boolean[][] resultTagArray = new boolean[N][k+1];
	//record the result of P(i,k)
	public static int[][] resultArray = new int[N][k+1];
	//record the bound point
	public static int[][] boundPointArray = new int[N][k+1];
	
	public static String[] boundArray = new String[k-1];
	
	public static int[] boundIdArray = new int[k-1];
	
	
	public static int resultCount = 0;
	
	
	public static void readDataToArray(File myFile, int[] myCountArray){
		BufferedReader reader = null;
        
        try {
            System.out.println("Read one line");
            
            reader = new BufferedReader(new FileReader(myFile));
            String tempString = null;
            
            String[] stringArray = new String[arrayCount];
            
            int line = 0; 
            int sum = 0;
            
            while ((tempString = reader.readLine()) != null) {
            	String[] values = tempString.split("\\s+");
            	
            	int count = Integer.parseInt(values[countPosition]);
            	myCountArray[Integer.parseInt(values[idPosition])] = count;
            	
            	line++;
            	sum += count;
            	
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
	}
	
	/**************************************************************
	 * 
	 * Dynamic problem programming
	 * 
	 * 
	 * Create User: mb25428
	 * Create Date: 2015-01-12
	 * 
	 * Last Modify User: mb25428
	 * Last Modify Date: 2015-01-28
	 * 
	 *************************************************************/
	public static void optimalPartition(){
		
		readDataToArray(rFile,rCountArray);
		readDataToArray(sFile,sCountArray);
        
        try {
            System.out.println("Read one line");
            
            String tempString = null;
            
            
            
            String fileAddress = "D:\\360Downloads\\count\\count_7_rs\\part-r-00000" ;
            
            File outputFile = new File(fileAddress);
            
            if(!outputFile.exists()){
            	outputFile.createNewFile();
            }
            FileWriter writer = new FileWriter(fileAddress);
            
           
            String[] stringArray = new String[arrayCount];
            
            int line = 0; 
            int sum = 0;
            
            for(int i = 0;i < rangeCount;i++){
            	int result = 0;
            	result += rCountArray[i]*sCountArray[i];
            	if(i> 0){
            		result += rCountArray[i] * sCountArray[i-1];
            	}
            	if(i < rangeCount-1){
            		result += rCountArray[i] * sCountArray[i+1];
            	}
            	
            	if(result != 0){
            		System.out.println(i + " " + result);
            		writer.append(i + " " + result + "\n");
            	}
            }
            
            
            
            
            
//            countArray = new int[]{1,2,3,4,5,6,7};
            
            
            
//            newPartition(0,N-1,k,1);

//            System.out.println("The Result " + minMaxPartition(0,N-1,k));
            
            
//            System.out.println("The Result " + minMaxPartitionNK2WithReplication(0,k));
            
         
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	
        }
        
        BufferedReader reader = null;
        
        try {
            System.out.println("Read one line");
            
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            
            String[] stringArray = new String[arrayCount];
            
            int line = 0; 
            int sum = 0;
            
            while ((tempString = reader.readLine()) != null) {
            	String[] values = tempString.split("\\s+");
            	
            	int count = Integer.parseInt(values[countPosition]);
            	countArray[line] = count;
            	stringArray[line]= tempString;
            	idArray[line] = Integer.parseInt(values[idPosition]);
            	
            	line++;
            	sum += count;
            	
            }
//            countArray = new int[]{1,2,3,4,5,6,7};
            
            
            
//            newPartition(0,N-1,k,1);

//            System.out.println("The Result " + minMaxPartition(0,N-1,k));
            
//            System.out.println("The Result " + minMaxPartitionNK2WithReplication(0,k));
            
         
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
        System.out.println("The Result " + minMaxPartitionNK2(0,k));
        
	}
	
	
	
	/*********************************************
	 * 
	 * @param value1
	 * @param value2
	 * @return The minimum value
	 * 
	 **********************************************/
	public static int min(int value1, int value2){
		return value1 < value2 ? value1 : value2;
	}
	
	
	/*********************************************
	 * 
	 * @param value1
	 * @param value2
	 * @return The maximum value
	 * 
	 **********************************************/
	public static int max(int value1,int value2){
		return value1 > value2 ? value1 :value2;
	}
	
	
	
	/*************************************************************************
	 * 
	 * The NK algorithm which can output the boundary point
	 * 
	 * Output Format: The partition ID: the start Point
	 * for example, the first partition  1: 0
	 * 
	 * 
	 * @param startPoint
	 * @param partitionNumber
	 * @return
	 ************************************************************************/
	
	public static int minMaxPartitionNK2(int startPoint, int partitionNumber){
		
//		System.out.println(startPoint + "   "+ partitionNumber);
		int result = -1;
		
		if(partitionNumber == 1){

			if(!resultTagArray[startPoint][partitionNumber]){
				
				result = 0;
				for(int j = startPoint;j <= N-1;j++){
					result+= countArray[j];
				}
				resultArray[startPoint][partitionNumber]= result;
				resultTagArray[startPoint][partitionNumber] = true;
				
			}else{
				
				result = resultArray[startPoint][partitionNumber];
				
			}
//			System.out.println("Result1 " + result);
			return result;
		}else{
		
			int first = 0;
			int partitionID = -1;
			for(int i = startPoint; i < N - partitionNumber+1;i++){
				
				if(!resultTagArray[i+1][partitionNumber-1]){
					int rest = minMaxPartitionNK2(i+1,partitionNumber-1);
					first += countArray[i];			
					
					int temp = max(first,rest);
					if(result == -1){
						result = max(first,rest);
					}
					
					if(partitionID == -1){
						partitionID = startPoint;
					}
					
					if(result > temp){
						partitionID = i+1;
					}
					result = min(result,temp);
					
					
				}else{
					
					
//					System.out.println((i+1) + " temp " + (partitionNumber-1));
					int tempResult = resultArray[i+1][partitionNumber-1];
					first += countArray[i];	
					int temp = max(first,tempResult);
					if(result == -1){
						result = max(first,tempResult);
					}
					
					//There should be i+1 not startPoint
					if(partitionID == -1){
						partitionID = i+1;
					}
					
					if(result > temp){
						partitionID = i+1;
					}
					result = min(result,temp);
				}
//				System.out.println(partitionID + "    Pab " + result);
			}
//			System.out.println(startPoint + "  " + partitionNumber);
			resultArray[startPoint][partitionNumber] = result;
			resultTagArray[startPoint][partitionNumber] = true;
			boundPointArray[startPoint][partitionNumber] = partitionID;
//			System.out.println(startPoint + " "+ partitionNumber + "  "+ partitionID + "    Result2 " + result);
			
			if(startPoint == 0 && partitionNumber == k){
				printResult();
			}
			return result;
		}
		
		
		
	}
	
	/**********************************************************************************
	 * 
	 * The NK algorithm with replication in the bound point
	 * 
	 * NOTE: The Replication is just occurred in the first Point of every partition
	 * The first partition is no replication, the rest partition copy the last element
	 * of previous partition
	 * 
	 * 
	 * @param startPoint
	 * @param partitionNumber
	 * @return the minimum of all the maximum
	 */
	
	public static int minMaxPartitionNK2WithReplication(int startPoint, int partitionNumber){
		
//		System.out.println(startPoint + "   "+ partitionNumber);
		
		int replication = 0;
		
		int result = -1;
		
		if(partitionNumber == 1){

			if(!resultTagArray[startPoint][partitionNumber]){
				
				result = 0;
				
				for(int j = startPoint == 0 ? startPoint : startPoint-1;j <= N-1;j++){
					result+= countArray[j];
				}
				resultArray[startPoint][partitionNumber]= result;
				resultTagArray[startPoint][partitionNumber] = true;
				
			}else{
				
				result = resultArray[startPoint][partitionNumber];
				
			}
//			System.out.println(startPoint + "   " + partitionNumber  + " Result1 " + result);
			return result;
			
			
		}else{
		
			int first = 0;
			if(startPoint != 0){
				first += countArray[startPoint-1];
			}
			int partitionID = -1;
			for(int i = startPoint; i < N - partitionNumber+1;i++){
				
				if(!resultTagArray[i+1][partitionNumber-1]){
					int rest = minMaxPartitionNK2WithReplication(i+1,partitionNumber-1);
					first += countArray[i];			
					
//					System.out.println("first " + first + " rest " + rest);
					int temp = max(first,rest);
					if(result == -1){
						result = max(first,rest);
					}
					
					if(partitionID == -1){
						partitionID = startPoint;
					}
					
					if(result > temp){
						partitionID = i+1;
					}
					result = min(result,temp);
					
					
				}else{
					
					
//					System.out.println((i+1) + " temp " + (partitionNumber-1));
					int tempResult = resultArray[i+1][partitionNumber-1];
					first += countArray[i];	
					int temp = max(first,tempResult);
					if(result == -1){
						result = max(first,tempResult);
					}
					
					//There should be i+1 not startPoint
					if(partitionID == -1){
						partitionID = i+1;
					}
					
					if(result > temp){
						partitionID = i+1;
					}
					result = min(result,temp);
				}
			}
//			System.out.println(startPoint + "  " + partitionNumber);
			resultArray[startPoint][partitionNumber] = result;
			resultTagArray[startPoint][partitionNumber] = true;
			boundPointArray[startPoint][partitionNumber] = partitionID;
//			System.out.println(startPoint + " "+ partitionNumber + "  "+ partitionID + "    Result2 " + result);
			
			if(startPoint == 0 && partitionNumber == k){
				printResult();
			}
			return result;
		}
		
		
		
	}


	/*****************************************************************************************
	 * 
	 * Print the result in the following format
	 * 
	 * Partition ID : Start Point ID : Point Value
	 * 
	 * Partition ID: Start From 1
	 * Start Point ID: Start From 0
	 * Point Value : the Point Value
	 * 
	 * NOTE:The Point is the start Point of every Partition, the start Point of the First 
	 * Partition is 0
	 * 
	 ***************************************************************************************/
	public static void printResult(){
		
		System.out.println(1 + "  Line " + 0 + "  Point " + idArray[0] + " value " + countArray[0]);
		int point = boundPointArray[0][k];
		
		boundIdArray[2-2] = idArray[point];
		
		System.out.println(2 + "  Line " + point + "  Point " + idArray[point] + " value " + countArray[point]);
		
		for(int l = k; l > 2;l--){
			point = boundPointArray[point][l-1];
			boundIdArray[k-l+3-2] = idArray[point];
			System.out.println( (k-l+3) + "  Line " + point+ "  Point " + idArray[point] + " value " + countArray[point]);
		}
	}

	/*******************************************************************************
	 * 
	 * 
	 * output the form that is suited for the MapReduce program partition
	 * 
	 * 
	 *****************************************************************************/
	public static void printlnPartitionPoint(){
		int i = 0;
		String result = "";
		for(int id : boundIdArray){
			System.out.println(i++ + " " +id);
			result += "{" + id+ "," + 2114 +"," + 112+ "},";
			
		}
		System.out.println(result);
	}
	
	
	public static void main(String[] args){
		
		System.out.println("find the minimum maximum value");
//		GreedyPartition();
//		LocalOptimalPartition();
		optimalPartition();
		printlnPartitionPoint();
		
		
	}
}

package plh211_project_1;

import java.util.Random;
import java.io.IOException;

public class Main {
	
	static int dataPageSize = 512;
	static int N = 100000;
	static int totalFilePages = N/dataPageSize*4;

	public static void main(String[] args) throws IOException {
		String file_name = "my_file.bin";
		
		FileManager file = new FileManager(512);
		
		//Create File that has 10^7 Numbers with random values from 1 to 10^7
		file.CreateRandomFile(file_name);
		file.CloseFile(file_name);
		
		//Print File - Requires to uncomment the printbuffer from ReadBlock and ReadNextBlock at FileManager
		file.OpenFile(file_name);
		file.PrintFile();
		file.CloseFile(file_name);
		
		//Print the 1st Page only (Has 3 Values - Position / Number of Pages / Integer Value in bits
		file.OpenFile(file_name);
		file.ReadBlock(0);
		file.CloseFile(file_name);
		
		//Serial Search 10000 random keys
		file.OpenFile(file_name);
		int cost = 0,SearchKey, nKeys = 1000 ;
		FileSearch search = new FileSearch();
		Random random = new Random();
		
		for(int i=0;i<nKeys;i++) {			
			SearchKey = random.nextInt(N);			
			cost += search.SerialSearch(file, SearchKey);			
		}
		file.CloseFile(file_name);		
		System.out.println("Average disk accesses for the serial search method : "+ cost/nKeys);
		System.out.println("Number of pages: "+file.getFileHandler().getNumOfPages());
		
		
				
		
		
		
		

	}


}



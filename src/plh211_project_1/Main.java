package plh211_project_1;

import java.io.*;
import java.io.OutputStream;
import java.util.Random;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class Main {
	
	static final String file_name = "my_file";
	static int numOfSearches=10000;
	static int total_cost=0;


	public static void main(String[] args) throws IOException {
		
		FileManager file = new FileManager(512);
		FileSearch search = new FileSearch();
		
		//file.CreateFile(file_name);
		file.OpenFile(file_name);
		//file.FillFile(file_name);
		//file.PrintFile();
		//file.ReadBlock(512);
		//file.CloseFile(file_name);		
		//search
		//file.OpenFile(file_name);
		Random rand = new Random();
		int random,i=0;
		
		do {
			//System.out.println("\nsearching page" +i);
			random = rand.nextInt(file.getMax() - file.getMin() + 1) + file.getMin();
			total_cost = total_cost + search.SerialSearch(random);
			i++;
		}while (i<=numOfSearches);
		
		
		
		//total_cost = total_cost / numOfSearches;
		System.out.println("\ncost=" +total_cost);		
		file.CloseFile(file_name);
		
				
		
		
		
		

	}


}



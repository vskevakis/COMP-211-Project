package plh211_project_1;

import java.io.IOException;

public class FileSearch {
	private int pages;
	private double cost;
	
	
	public double SerialSearch(FileManager file, int key) throws IOException {		
		this.cost = 0;		
		boolean flag = false;		
		int[] IntArray;
		file.ReadBlock(0);		
		this.pages = file.bufferToIntArray(file.getBuffer())[1];
		
		for (int k = 0 ;k< this.pages;k++) {			
			file.ReadNextBlock();			
			this.cost++;
			IntArray = file.bufferToIntArray(file.getBuffer());		
			
			for(int i=0;i<IntArray.length;i++) {				
				if(IntArray[i] == key) {					
					flag = true;
					break;
				}
			}
			
			if(flag==true) 
				break;			
		}		
		return this.cost;		
	}	
}

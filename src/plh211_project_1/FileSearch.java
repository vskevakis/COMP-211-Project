package plh211_project_1;

import java.io.IOException;

public class FileSearch {
	private int cost, pages;
	
	
	public int SerialSearch(FileManager file, int key) throws IOException {		
		System.out.println("Searching for Key : "+key);		
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
					System.out.println("Key Found in Page : "+k);
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

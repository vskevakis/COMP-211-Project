package plh211_project_1;

import java.io.IOException;

public class BufferPoolSearch {

	private int cost;
	private int Pages;
	
	public int PoolSearch(FileManager file, int searchKey, PoolManager poolManager) throws IOException {
		
		this.cost = 0;
		
		boolean flag = false;
		
		file.ReadBlock(0);
		
		this.Pages = file.bufferToIntArray(file.getBuffer())[1];
		
		for (int k = 0 ;k< this.Pages;k++) {
			
			byte[] pool = poolManager.SearchPool(k);
			
			
			if(pool!=null){
				
				flag = searchSubArray(searchKey,file.bufferToIntArray(pool));
				
			}else{
			
				file.ReadNextBlock();
			
				this.cost++;
			
				flag = searchSubArray(searchKey,file.bufferToIntArray(file.getBuffer()));

			}

			if(flag==true) { 
				poolManager.InsertPool(k, file.getBuffer());
				break;
			}
				
		}
		
		return this.cost;
		
	}
	
	public boolean searchSubArray(int searchKey, int[] iArray){	
		for(int i=0;i<iArray.length;i++) {
			if(iArray[i] == searchKey) 
				return true;
		}
		return false;
	}
	
	
}

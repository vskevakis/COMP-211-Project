package plh211_project_1;

import java.util.ArrayList;
import java.util.List;

public class PoolManager {

	List<Page> BufferPool;
	int K;

	public PoolManager(int k){
		this.K = k;
	}
	
	
	public void CreatePool() {
		BufferPool = new ArrayList<Page>(K);
	}
	
	
	public byte[] SearchPool(int page){
		
		for(Page p : BufferPool)
			if(p.getId()==page) 
				return p.getBuffer();
		return null;
		
	}
	
	public void InsertPool(int page, byte[] buffer) {
	
		Page tempPage = new Page(page,buffer);
		
		if(BufferPool.contains(tempPage))
			return;
		
		if(BufferPool.size() > K)
			BufferPool.remove(BufferPool.size() - 1);
		
		BufferPool.add(0, tempPage);
	
	}
	
	public void DeletePool(int page) {
		
		for(Page p : BufferPool) 
				if(p.getId()==page) 
					BufferPool.remove(p);
			
	}
	
	public void FreePool() { BufferPool.clear();}
	
	
}

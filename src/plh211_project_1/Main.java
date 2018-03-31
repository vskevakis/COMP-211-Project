package plh211_project_1;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Main {
	static int dataPageSize = 512;
	static int N = 10000000;
	static int totalFilePages = N/dataPageSize*4;
	
	public static void main(String[] args) throws IOException{

		// Number of keys | Page Size in Bytes | Name of the File | Number of searches
		runDemo(N,dataPageSize,"my_file",10000);
		
	}

	public static void runDemo(int N, int dataPageSize, String FileName,int numSearches) throws IOException {
		
		FileManager file = new FileManager(dataPageSize);

		file.CreateRandomFile(FileName);

		file.OpenFile(FileName);
		
		// Number of Keys | FileManager
		double avgSerialSearchCost = SerialSearchDemo(N,numSearches,file);
		
		System.out.println("Average Serial Search cost : "+avgSerialSearchCost );
		
		// Number of Keys | FileManager | Buffer Size(K)
		double avgBufferSearch1K = BufferSearchDemo(N,dataPageSize,numSearches,file,50);
		
		System.out.println("Average Buffer Pool Search cost (K=1.000) : "+avgBufferSearch1K+ " an "+((avgSerialSearchCost - avgBufferSearch1K) / avgSerialSearchCost * 100)+"% decrease in cost");
		
		double avgBufferSearch10K = BufferSearchDemo(N,dataPageSize,numSearches,file,70);
		
		System.out.println("Average Buffer Pool Search cost (K=10.000) : "+avgBufferSearch10K+ " an "+((avgSerialSearchCost - avgBufferSearch10K) / avgSerialSearchCost * 100)+"% decrease in cost");
		
	}
	
	public static double SerialSearchDemo(int N,int nKeys, FileManager fm) throws IOException {
		
		double cost = 0;
		
		FileSearch fs = new FileSearch();
		
		for(int i=0;i<nKeys;i++)
			cost += fs.SerialSearch(fm, ThreadLocalRandom.current().nextInt(0, N + 1));
		
		return cost/(double)nKeys;
		
	}
	
	public static double BufferSearchDemo(int N, int BytePageSize, int nKeys, FileManager fm, int K) throws IOException {
		
		double cost = 0; 
		
		PoolManager pM = new PoolManager(K);
		
		initBufferPoolWRndPages(BytePageSize,fm,pM,K);
		
		BufferPoolSearch bps = new BufferPoolSearch();
		
		for(int i=0;i<nKeys;i++)
			cost += bps.PoolSearch(fm, ThreadLocalRandom.current().nextInt(0, N + 1), pM);
			
		return cost/(double)nKeys;
		
	}
	
	public static void CreateRandomFile(int N, int BytePageSize, FileManager fm, String FileName) throws IOException {
		
		fm.CreateFile(FileName);
		
		fm.OpenFile(FileName);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(bos);
		
		for (int i = 0; i < (N/(BytePageSize/4)); i++) {
			for (int j = 0; j < (BytePageSize/4); j++) {
				out.writeInt(ThreadLocalRandom.current().nextInt(0, N + 1));
			}

			byte[] buf = bos.toByteArray();
			fm.AppendBlock("file_name", buf);
			
			bos.reset();

		} 
		
		out.close();
		bos.close();
		
		fm.CloseFile(FileName);
		
	}
	/*
	public static void PrintFile(int N, int BytePageSize, FileManager fm){

		System.out.println("File Handle : ");
		fm.ReadBlock(0);
		fm.printBuffer();
		
		for (int k = 0 ;k<(N/(BytePageSize/4));k++) {
			System.out.println("\nPage : "+k);
			fm.ReadNextBlock();
			fm.printBuffer();
		}
	
	}
	*/
	
	
	public static void initBufferPoolWRndPages(int BytePageSize, FileManager fm, PoolManager pm, int K) {
		
		pm.CreatePool();
		
		int pages = fm.getFileHandler().getNumOfPages();
		
		int rndPage;
		
		while(pm.BufferPool.size()<K) {
		
			rndPage = ThreadLocalRandom.current().nextInt(1,pages);
			
			fm.ReadBlock(rndPage*BytePageSize);
			
			pm.InsertPool(rndPage, fm.getBuffer());
			
		}
		
		
	}
	
}


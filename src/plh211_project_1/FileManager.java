package plh211_project_1;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Random;

public class FileManager {
	
	private static int max=1000000;
	private static int min=1;
	static int dataPageSize=512;
	static int totalFilePages = max/dataPageSize*4;
	int pos = 0;
	String file_name = "my_file";
	private byte[] buffer;
	
	FileHandle fileHandle = new FileHandle(1, 1, 32, file_name);
	RandomAccessFile myFile;
	
	//RandomAccessFile myFile = new RandomAccessFile (file_name, "rw"); //creating my file

	
    public FileManager(int buf_size) throws IOException {
    	this.buffer = new byte[buf_size];
    	this.dataPageSize = buf_size;
    }
	
	
	public int CreateFile(String file_name) {
		try {
			myFile = fileHandle.getFile();
			myFile.setLength(0);//Clearing file if it exists
	
			//Updating my FileHandle
			fileHandle.setPosition(0);
			fileHandle.setNumOfPages(0);
			fileHandle.setDataType(32);
			fileHandle.setFile_name(file_name);
			
	    	//RandomAccessFile myFile = new RandomAccessFile (file_name, "rw"); //Creating Random Access File with name my_file and read/write perms
	    	ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize) ;
			DataOutputStream out = new DataOutputStream(bos);
			out.writeInt(fileHandle.getPosition()); //Page position (because file position would need to be type long and I want to use integers only.
			out.writeInt(fileHandle.getNumOfPages()); //NumOfPages is 0 at the begining
			out.writeInt(fileHandle.getDataType()); //I Dont think this info will be necceserry 
			out.close();
			
			
			byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes
	
	    	
	    	//Writing to buffer    	
	    	myFile.seek(0);
	    	myFile.write(buf);
		}
	    catch (IOException e) {
	    	return 0;
	    }
		return 1;
	}	
	
    public int OpenFile(String file_name) {
    	try {
	    	myFile = fileHandle.getFile();
	    	myFile.seek(0);
			fileHandle.setPosition(myFile.readInt());
			fileHandle.setNumOfPages(myFile.readInt());
			fileHandle.setDataType(myFile.readInt());
    	}
    	catch (IOException e){
    		return 0;
    	}	
		return fileHandle.getNumOfPages();
    }
    
	
	//Reading the page from the position
	public int ReadBlock(int position) {
		//System.out.println("\nReading Block "+ (position) +"\n");
		try {

			fileHandle.setPosition((position-1)*512+1); //finding the 1st int of the page
			fileHandle.getFile().read(this.buffer);
			
			//printBuffer(this.buffer);			
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}
	
	//Reading the page from the position +1
	public int ReadNextBlock() throws IOException {
		//System.out.println("\nReading Block "+ (fileHandle.getPosition()+1) +"\n");
		try {

			fileHandle.setPosition((fileHandle.getPosition())*512+1); //finding the 1st int of the page
			fileHandle.getFile().read(this.buffer);
			
			printBuffer(this.buffer);			
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}
	
	//Reading the page from the position -1 
	public byte[] ReadPrevBlock(String file_name, int position) throws IOException {
		byte[] buf3 = new byte[dataPageSize];
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		
		int page= position / dataPageSize; //finding the page the given position is
		int page_position = dataPageSize * page + 1; //finding the 1st int of the page
		
		myFile.seek(page_position-1);
		myFile.read(buf3);
		myFile.close();
		
		return buf3;
	}
		
	public void WriteBlock(String file_name, int position,byte[] buffer) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position);
		myFile.write(buffer);
		
		int page= position / dataPageSize; //finding the page the given position is
		int page_position = dataPageSize * page + 1; //finding the 1st int of the page
		int numOfPages = file_name.length() / dataPageSize;
		
		if (page_position >= numOfPages ) {
			if (page == numOfPages + 1 ) {	
				myFile.seek(position);
				myFile.write(buffer);
				
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize) ;
				DataOutputStream out = new DataOutputStream(bos);
				out.writeInt(position); //Pointer's new position
				out.writeInt(numOfPages+1); //NumOfPages ++
				out.close();
				
				byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes
				
		    	//Writing new Position and numOfPages to the first page of the file    	
		    	myFile.seek(0);
		    	myFile.write(buf);
		    	myFile.close();
			}
			else {
				System.out.println("Position is beyond num of pages");
			}
		}
		else {
			myFile.seek(position);
			myFile.write(buffer);
		}
	}
	
	public void WriteNextBlock(String file_name, int position,byte[] buffer) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position);
		myFile.write(buffer);
		
		int page= (position / dataPageSize) + 1; //finding the page the given position is
		int page_position = dataPageSize * page + 1; //finding the 1st int of the page
		int numOfPages = file_name.length() / dataPageSize;
		
		if (page_position >= numOfPages ) {
			if (page == numOfPages + 1 ) {	
				myFile.seek(position);
				myFile.write(buffer);
				
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize) ;
				DataOutputStream out = new DataOutputStream(bos);
				out.writeInt(position); //Pointer's new position
				out.writeInt(numOfPages+1); //NumOfPages ++
				out.close();
				
				byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes
				
		    	//Writing new Position and numOfPages to the first page of the file    	
		    	myFile.seek(0);
		    	myFile.write(buf);
		    	myFile.close();
			}
			else {
				System.out.println("Position is beyond num of pages");
			}
		}
		else {
			myFile.seek(position);
			myFile.write(buffer);
		}
	}
	
	public void AppendBlock(String file_name ,byte[] buffer) throws IOException {
		myFile = fileHandle.getFile();
		int numOfPages = fileHandle.getNumOfPages();
		int position = numOfPages*dataPageSize+dataPageSize;
		myFile.seek(position);
		myFile.write(buffer);
		fileHandle.setNumOfPages(numOfPages+1); //NumOfPages ++
		//fileHandle.setPosition();
		
		/*int page = position / dataPageSize; //finding the page the given position is
		int page_position = dataPageSize * page + 1; //finding the 1st int of the page
		//int numOfPages = file_name.length() / Main.DataPageSize;

		myFile.seek(position);
		myFile.write(buffer);
		*/
		/*
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize) ;
		DataOutputStream out = new DataOutputStream(bos);
		out.writeInt(position); //Pointer's new position
		out.writeInt(numOfPages); //NumOfPages ++
		out.close();
		
		byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes
		
    	//Writing new Position and numOfPages to the first page of the file    	
    	myFile.seek(0);
    	myFile.write(buf);
    	*/
    	
	}
	
	public void DeleteBlock(String file_name, int position,byte[] buf) throws IOException {
		myFile.seek(position+1);
		myFile.write(buf);
		if (position+1 > (file_name.length() / dataPageSize) ) {
			if (position+1 == (file_name.length() / dataPageSize) + 1 ) {
				//numOfPages++;
				//pos= position;
			}
			else {
				System.out.println("Position is beyond num of pages");
			}
			
		}
	}	
	
	
    //Closing the File its not ready yet
    public void CloseFile(String file_name) throws IOException {  
    	myFile = fileHandle.getFile();
    	int position = fileHandle.getPosition();
    	int numOfPages = fileHandle.getNumOfPages();
    	myFile.seek(0);
    	myFile.writeInt(position);
    	myFile.writeInt(numOfPages);
    	myFile.close();
    }
	
	public void FillFile(String file_name) throws IOException {
		Random rand = new Random();
		int i,j;
		for (j=1;j<=totalFilePages;j++){
			ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize);
			DataOutputStream out = new DataOutputStream(bos);
			for (i=0;i<dataPageSize;i++){	
				out.writeInt(rand.nextInt(max - getMin() + 1) + getMin());
			}
			out.close();
			byte[] buf = bos.toByteArray();
			AppendBlock(file_name,buf);
		}
		
		
	}
	
	public void PrintFile() throws IOException{

		System.out.println("File Handle : ");
		ReadBlock(0);
		
		for (int k = 0 ;k<totalFilePages;k++) {
			System.out.println("\nPage : "+k);
			ReadNextBlock();
		}
	
	}


/*
	public void PrintFile(String FileName) throws IOException {
	
		int k=0;
		
		while (k < totalFilePages) { // DIABASMA KATHE SELIDAS
			// KSEXORISTA (SEIRIAKH
			// ANAZHTHSH)
	
			InputStream inputstream = new FileInputStream(FileName);
			inputstream.skip(k * dataPageSize);
			byte[] MainBuffer = new byte[dataPageSize]; // MAIN BUFFER
			int[] IntArray = new int[dataPageSize];
			inputstream.read(MainBuffer);
	
			byte[] extrabuffer = new byte[4];
	
			int c1 = 0, c2 = 0;
	
			System.out.println("\nPage : "+(k+1));
			
			while (c1 < dataPageSize) { // METARTOPH TOU MAIN BUFFER SE
				// PINAKA INT
	
				for (int i = 0; i < 4; i++) {
	
					extrabuffer[i] = MainBuffer[c1];
					c1++;
				}
	
				int x = ByteBuffer.wrap(extrabuffer).getInt();
				System.out.print(x+" ");
				IntArray[c2] = x;
				c2++;
	
			}
	
	
			k++;
			inputstream.close();
	
		}
		
	
	}
	*/
	public void printBuffer(byte[] buffer) {
		
		byte[] extrabuffer = new byte[4];
		
		int c1 = 0;
		
		
		while (c1 < dataPageSize) { 
	
			for (int i = 0; i < 4; i++) {
	
				extrabuffer[i] = buffer[c1];
				c1++;
			}
	
			int x = ByteBuffer.wrap(extrabuffer).getInt();
			System.out.print(x+" ");
	
		}
		
		
	}


	public int getMin() {
		return min;
	}


	public void setMin(int min) {
		FileManager.min = min;
	}
	
	public int getMax() {
		return max;
	}
	
	public void setMax(int max) {
		FileManager.max = max;
	}

}





package plh211_project_1;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Random;

public class FileManager {
	
	int dataPageSize;
	private byte[] buffer;
	private FileHandle fileHandle;

	
    public FileManager(int buf_size) throws IOException {
    	this.buffer = new byte[buf_size];
    	this.dataPageSize = buf_size;
    }
	
	
	public int CreateFile(String file_name) {
		try {			
			RandomAccessFile MyFile = new RandomAccessFile(file_name, "rw");
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(bos);

			MyFile.seek(0);

			out.writeInt(0);
			out.writeInt(0);
			out.writeInt(32);
			
			for (int j = 0; j < dataPageSize/4-3; j++) { 
				out.writeInt(0);
			}

			byte[] buf = bos.toByteArray(); 
			MyFile.write(buf);
			bos.reset();
			
			out.close();
			bos.close();
			MyFile.close();
			
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}
	
    public int OpenFile(String file_name) {
    	try {
	    	fileHandle = new FileHandle(file_name,"rw");
    	}
    	catch (IOException e){
    		return 0;
    	}	
		return 1;
    }
    
	
	//Reading the page from the position
	public int ReadBlock(int position) {
		try {
			fileHandle.setPosition(position); //finding the page
			fileHandle.getFile().read(this.buffer);
			
			//printBuffer(this.buffer); //Printing the Block
		} catch (IOException e) {
			return 0;
		}	
		return 1;
	}
	
	//Reading the page from the position +1
	public int ReadNextBlock() throws IOException {
		try {
			fileHandle.getFile().read(this.buffer);
			
			//printBuffer(this.buffer);			
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}
	
	//Reading the page from the position -1 
	public int ReadPrevBlock(String file_name, int position) throws IOException {
		try {
			fileHandle.setPosition((int)fileHandle.getFile().getFilePointer()-dataPageSize);
			fileHandle.getFile().read(this.buffer);
			
			//printBuffer(this.buffer);			
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}
		
	public int WriteBlock(int position,byte[] buffer) throws IOException {
		try {
			fileHandle.setPosition((position)*dataPageSize);
			fileHandle.getFile().write(buffer);
		} catch (IOException e) {
			return 0;
		}
		return 1;
	}
	
	
	public int WriteNextBlock(byte[] buffer) throws IOException {
		try {
			fileHandle.setPosition(fileHandle.getPosition()+dataPageSize);
			fileHandle.getFile().write(buffer);
			
			if(fileHandle.getPosition()>=fileHandle.getFile().length())
				fileHandle.setNumOfPages(fileHandle.getNumOfPages()+1);
			
		} catch (IOException e) {
			return 0;
		}
		return 1;
	}
	
	public int AppendBlock(String file_name ,byte[] buffer) {
		try {
			fileHandle.setPosition((fileHandle.getNumOfPages()+1)*dataPageSize);
			fileHandle.getFile().write(buffer);
			fileHandle.increaseNumOfPages();; //NumOfPages ++ 
		} catch (IOException e) {
			return 0;
		}
		return 1;
	}
	
	public int DeleteBlock(String file_name, int position,byte[] buf) throws IOException {
		try {
			byte[] LastPageBuffer = new byte[dataPageSize];
			
			fileHandle.setPosition(fileHandle.getNumOfPages()*dataPageSize);
			fileHandle.getFile().read(LastPageBuffer);
			
			fileHandle.setPosition(position*dataPageSize);
			fileHandle.getFile().write(LastPageBuffer);
			
			fileHandle.setNumOfPages(fileHandle.getNumOfPages()-1);
			
			printBuffer(LastPageBuffer);
		} catch (IOException e) {
			return 0;
		}		
		return 1;
	}	
	
	
    //Closing the File its not ready yet
	public int CloseFile(String Fname) {
		
        try {
        	int [] FHints = new int[]{fileHandle.getPosition(),fileHandle.getNumOfPages(),fileHandle.getDataType()};
    		
    		ByteBuffer byteBuffer = ByteBuffer.allocate(FHints.length * 4);        
            IntBuffer intBuffer = byteBuffer.asIntBuffer();
            intBuffer.put(FHints);
            byte[] FHBuffer = byteBuffer.array();
            
			fileHandle.setPosition(0);
			fileHandle.getFile().write(FHBuffer);
			fileHandle.getFile().close();
		} catch (IOException e) {
			return 0;
		}
		return 1;
	}
	
	public void CreateRandomFile(String file_name) throws IOException {
		System.out.println("Creating File...");
		CreateFile(file_name);
		OpenFile(file_name);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(dataPageSize);
		DataOutputStream out = new DataOutputStream(bos);
		
		Random rand = new Random();
		
		for (int j=1;j<=Main.totalFilePages;j++){
			
			for (int i=0;i<dataPageSize;i++){	
				out.writeInt(rand.nextInt(Main.N));
			}

			byte[] buf = bos.toByteArray();
			AppendBlock(file_name,buf);
			bos.reset();
		}
		System.out.println("File Created");
		
		out.close();
		bos.close();		
	}
	
	public void PrintFile() throws IOException{
		System.out.println("File Handle : ");
		ReadBlock(0);
		
		for (int k = 0 ;k<Main.totalFilePages;k++) {
			System.out.println("\nPage : "+k);
			ReadNextBlock();
		}	
	}
	
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
	
	public int[] bufferToIntArray(byte[] buffer) {
		int[] IntArray = new int[dataPageSize/4];
		byte[] extrabuffer = new byte[4];
		int c1 = 0, c2 = 0;

		while (c1 < dataPageSize) {
			for (int i = 0; i < 4; i++) {
				extrabuffer[i] = buffer[c1];
				c1++;
			}
			int x = ByteBuffer.wrap(extrabuffer).getInt();
			IntArray[c2] = x;
			c2++;
		}		
		return IntArray;		
	}
	
	public byte[] getBuffer() {
		return buffer;
	}
	
	public FileHandle getFileHandler() {
		return fileHandle;
	}

}





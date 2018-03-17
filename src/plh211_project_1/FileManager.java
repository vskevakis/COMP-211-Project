package plh211_project_1;

import java.io.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class FileManager {
	
	String file_name = Main.file_name;
	int pos = 0;
	int numOfPages = 0;
	String description ="";
    
    public FileManager() throws IOException {
    			
		

	
    }
    
    //Method to Fill the File with 10^7 integers (with values from 1 to 10^7)
    public void FillFile() throws IOException {
   	
    	
    }
    
    public void OpenFile(String file_name) throws IOException {
    	RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
    	System.out.println("The Number of Pages is:" );
    }
    
    //Closing the File
    public void CloseFile(String file_name) throws IOException {  
    	RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
    	myFile.close();
    }
	
	public void FileHandle() throws IOException {
		file_name = Main.file_name;
		pos = 0;
		numOfPages = 0;
		description ="";
		
		
	}
	
	
	public void CreateFile(String file_name) throws IOException {
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize) ;
		DataOutputStream out = new DataOutputStream(bos);
		out.writeInt(pos); //Pointer's position is 0 at the begining
		out.writeInt(numOfPages); //NumOfPages is 0 at the begining
		out.writeChars(file_name); //File Name is given
		out.writeChars(description);
		out.close();
		
		
		byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes

    	RandomAccessFile myFile = new RandomAccessFile (file_name, "rw"); //Creating Random Access File with name my_file and read/write perms
    	
    	//Writing to buffer    	
    	for (int i=1;i<=Main.DataPageSize;i=i++) {
    	myFile.seek(0);
    	myFile.write(buf);
    	myFile.close();
    	}	 
	
	}
	

	
	
	public void WritePage(int page,int[] array) throws IOException
	{
		 
	}
	
	
	
	
	
}





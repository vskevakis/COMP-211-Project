package plh211_project_1;

import java.io.*;
import java.io.OutputStream;
import java.util.Random;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class FileManager {
	
	int pos = 0;
	static int totalFilePages = 7813;
	String description ="ASD";
	static int max=10000000,min=1;
    
	
    public FileManager() throws IOException {
    			
		

	
    }
   
    
    //Takes file name and Prints 1. Pointer Position, 2.NumOfPages, 3.file_name, 4. File Description from the 1st page of the File
	public static void FileHandle(String file_name) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		
		byte[] buf1 = new byte[Main.DataPageSize];
		myFile.seek(0);
		myFile.read(buf1);
		
		ByteArrayInputStream bis= new ByteArrayInputStream(buf1);
		DataInputStream ois= new DataInputStream(bis);
		
		System.out.println("\nPointer Position = " + ois.readInt());
		System.out.println("\nNumber of Pages = " + ois.readInt());
		System.out.println("\nFile Name = " + ois.readChar());
		System.out.println("\nFile Description =" + ois.readChar());
		myFile.close();
	}
	
	
	public void CreateFile(String file_name) throws IOException {
    	RandomAccessFile myFile = new RandomAccessFile (file_name, "rw"); //Creating Random Access File with name my_file and read/write perms
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize) ;
		DataOutputStream out = new DataOutputStream(bos);
		out.writeInt(0); //Pointer's position is 0 at the begining
		out.writeInt(0); //NumOfPages is 0 at the begining
		out.writeChars(file_name); //File Name is given
		out.writeChars("Random Numbers from 1 to 10^7");
		out.close();
		
		
		byte[] buf = bos.toByteArray(); // Creates a newly allocated byte array. The BUFFER with size 512 bytes

    	
    	//Writing to buffer    	
    	myFile.seek(0);
    	myFile.write(buf);
    	myFile.close();
  
	
	}	
	
    public static int OpenFile(String file_name) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		byte[] buf1 = new byte[Main.DataPageSize];
		
		myFile.seek(0);
		myFile.read(buf1);
		
		ByteArrayInputStream bis= new ByteArrayInputStream(buf1);
		DataInputStream ois= new DataInputStream(bis);
		
		int position = ois.readInt(); //Position integer is the first int on the first page of the file
		//ois.skipBytes(1);
		int numOfPages = ois.readInt(); //Number of pages is the second integer on the first page of the file
		
		
		myFile.close();
		
		return numOfPages;
    }
    
	
	//Reading the page from the position
	public static byte[] ReadBlock(String file_name, int position) throws IOException {
		byte[] buf1 = new byte[Main.DataPageSize];
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		
		int page= position / Main.DataPageSize; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		
		myFile.seek(page_position);
		myFile.read(buf1);
		myFile.close();
		
		return buf1;
	}
	
	//Reading the page from the position +1
	public static byte[] ReadNextBlock(String file_name, int position) throws IOException {
		byte[] buf2 = new byte[Main.DataPageSize];
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		
		int page= position / Main.DataPageSize; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		
		myFile.seek(page_position+1);
		myFile.read(buf2);
		myFile.close();
		
		return buf2;
	}
	
	//Reading the page from the position -1 
	public static byte[] ReadPrevBlock(String file_name, int position) throws IOException {
		byte[] buf3 = new byte[Main.DataPageSize];
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		
		int page= position / Main.DataPageSize; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		
		myFile.seek(page_position-1);
		myFile.read(buf3);
		myFile.close();
		
		return buf3;
	}
		
	public static void WriteBlock(String file_name, int position,byte[] buffer) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position);
		myFile.write(buffer);
		
		int page= position / Main.DataPageSize; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		int numOfPages = file_name.length() / Main.DataPageSize;
		
		if (page_position >= numOfPages ) {
			if (page == numOfPages + 1 ) {	
				myFile.seek(position);
				myFile.write(buffer);
				
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize) ;
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
	
	public static void WriteNextBlock(String file_name, int position,byte[] buffer) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position);
		myFile.write(buffer);
		
		int page= (position / Main.DataPageSize) + 1; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		int numOfPages = file_name.length() / Main.DataPageSize;
		
		if (page_position >= numOfPages ) {
			if (page == numOfPages + 1 ) {	
				myFile.seek(position);
				myFile.write(buffer);
				
		    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize) ;
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
	
	public static void AppendBlock(String file_name ,byte[] buffer) throws IOException {
		int position = file_name.length();
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position);
		myFile.write(buffer);
		
		int page= position / Main.DataPageSize; //finding the page the given position is
		int page_position = Main.DataPageSize * page + 1; //finding the 1st int of the page
		int numOfPages = file_name.length() / Main.DataPageSize;

		myFile.seek(position);
		myFile.write(buffer);
		
    	ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize) ;
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
	
	public static void DeleteBlock(String file_name, int position,byte[] buf) throws IOException {
		RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
		myFile.seek(position+1);
		myFile.write(buf);
		if (position+1 > (file_name.length() / Main.DataPageSize) ) {
			if (position+1 == (file_name.length() / Main.DataPageSize) + 1 ) {
				//numOfPages++;
				//pos= position;
			}
			else {
				System.out.println("Position is beyond num of pages");
			}
			
		}
	}	
	
	
    //Closing the File
    public static void CloseFile(String file_name) throws IOException {  
    	RandomAccessFile myFile = new RandomAccessFile (file_name, "rw");
    	myFile.close();
    }
	
	public static void FillFile(String file_name) throws IOException {
		Random rand = new Random();
		int i,j;
		ByteArrayOutputStream bos = new ByteArrayOutputStream(Main.DataPageSize);
		DataOutputStream out = new DataOutputStream(bos);
		for (j=1;j<=totalFilePages;j++){	
			for (i=0;i<Main.DataPageSize;i++){	
				out.writeInt(rand.nextInt(max - min + 1) + min);
			}
			out.close();
			byte[] buf = bos.toByteArray();
			FileManager.AppendBlock(file_name,buf);
		}
		
		
	}
	
}





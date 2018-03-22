package plh211_project_1;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileHandle {
		
	private int position; //starting position
	private int numOfPages; //starting numofPages
	private int dataType; //Integer
	private String file_name;


	RandomAccessFile myFile; //My File
	

	public FileHandle(int position, int numOfPages, int dataType, String file_name) throws FileNotFoundException {
		this.position = position;
		this.numOfPages = numOfPages;
		this.dataType = dataType;
		this.file_name = file_name;
		this.myFile = new RandomAccessFile("my_file","rw");
	}

	
	public void printFileHandle(String file_name) throws IOException {
		System.out.println("\nFile Name =" + file_name);
		System.out.println("\nPage Position = " + position);
		System.out.println("\nNumber of Pages = " + numOfPages);
		System.out.println("\nData Type Value = " + dataType);
	}
	
	public int increaseNumOfPages(){
		this.numOfPages++;
		return numOfPages;
	}
	
	public RandomAccessFile getFile() {
		return myFile;
	}
	
	public int getPosition() {
		return position;
	}


	public void setPosition(int position) {
		this.position = position;
	}


	public int getNumOfPages() {
		return numOfPages;
	}


	public void setNumOfPages(int numOfPages) {
		this.numOfPages = numOfPages;
	}


	public int getDataType() {
		return dataType;
	}


	public void setDataType(int dataType) {
		this.dataType = dataType;
	}


	public String getFile_name() {
		return file_name;
	}


	public void setFile_name(String file_name) {
		this.file_name = file_name;
	}


	public RandomAccessFile getMyFile() {
		return myFile;
	}


	public void setMyFile(RandomAccessFile myFile) {
		this.myFile = myFile;
	}

	
}

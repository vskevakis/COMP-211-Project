package plh211_project_1;

import java.io.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class Main {
	
	static final String file_name = "my_file";
	static final int DataPageSize = 512; //My page size is 512 bytes



	public static void main(String[] args) throws IOException {
		
		FileManager file = new FileManager();
		
		file.CreateFile(file_name);
		file.FillFile(file_name);
		file.FileHandle(file_name);
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}



package plh211_project_1;

import java.io.*;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

public class Main {
	
	static final String file_name = "my_file";


	public static void main(String[] args) throws IOException {
		
		FileManager file = new FileManager();
		
		file.CreateFile(file_name);
		//file.OpenFile(file_name);
		file.FillFile(file_name);
		file.PrintFile(file_name);
		file.CloseFile(file_name);
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}

}



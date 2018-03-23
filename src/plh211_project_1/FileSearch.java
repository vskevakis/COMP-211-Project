package plh211_project_1;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class FileSearch {
	byte[] buffer;
	FileManager file;
	

	
	public FileSearch() throws IOException {
		this.file = new FileManager(512);

	}
	
	public int SerialSearch(int key) throws IOException{
		int search_cost=0, curKey=0, found=0, j,i=0;
		//FileManager file = new FileManager(512);
		do {
			search_cost++;
			buffer = intToBytes(file.ReadBlock(i));
			printBuffer(buffer);
			j=0;
			do {
				file.fileHandle.getFile().seek(j);
				curKey = file.fileHandle.getFile().readInt();
				j++;
			}while (curKey!=key && found == 0 && j < 512);
			i++;
		}while (curKey!=key && found==0 && i<file.fileHandle.getNumOfPages());
			
		return search_cost;

	}
	
public void printBuffer(byte[] buffer) {
		
		byte[] extrabuffer = new byte[4];
		
		int c1 = 0;
		
		
		while (c1 < 512) { 
	
			for (int i = 0; i < 4; i++) {
	
				extrabuffer[i] = buffer[c1];
				c1++;
			}
	
			int x = ByteBuffer.wrap(extrabuffer).getInt();
			System.out.print(x+" ");
	
		}
		
		
	}
	
	public byte[] intToBytes( final int i ) {
	    ByteBuffer bb = ByteBuffer.allocate(4); 
	    bb.putInt(i); 
	    return bb.array();
	}
	
	private byte[] intToByteArray ( final int i ) throws IOException {      
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    DataOutputStream dos = new DataOutputStream(bos);
	    dos.writeInt(i);
	    dos.flush();
	    return bos.toByteArray();
	}
	
/*	private int SearchBuffer(byte[] buffer, int key) throws IOException {
		int curKey;	
		for (int i=0;i==file.dataPageSize;i++) {
			curKey = file.fileHandle.getFile().readInt();
			if (curKey==key) {
				return 1;
			}
		}
		return 0;
	}
	*/
	
}

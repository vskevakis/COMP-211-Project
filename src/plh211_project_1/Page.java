package plh211_project_1;
/*
 * This Class is used to have a one dimension array list 
 * 
 */
public class Page {
	
	private int id;
    private byte[] buffer;

    public Page(int id, byte[] buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public int getId() {
        return id;
    }

    public byte[] getBuffer() {
        return buffer;
    }
    
}

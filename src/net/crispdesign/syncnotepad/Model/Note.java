package net.crispdesign.syncnotepad.Model;

public class Note {
	
	public String text;
    public long id;

	public Note() {
		text = "";
        id = -1;
	}

    public Note(long id, String text) {
        this.id = id;
        this.text = text;
    }

	@Override
	public String toString() {
		return id + ":" + this.text;
	}
}

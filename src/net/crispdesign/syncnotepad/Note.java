package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Note extends ActiveRecordBase {
	
	// public for AR-access
	public String noteText;

	public Note() {
	}

	public Note(Database db) {
		super(db);
	}

	@Override
	public String toString() {
		return this.getID() + ":" + this.noteText;
	}
}

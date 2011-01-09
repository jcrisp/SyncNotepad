package net.crispdesign.syncnotepad;

import java.util.List;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;

import android.content.Context;
import android.util.Log;

public class Db {
	private final static String tag = "Main";
	private final static String dbName = "notes.db";
	private final static int dbVersion = 2;

	private DatabaseBuilder builder = null;
	private ActiveRecordBase connection = null;

	public Db(Context context) {
		builder = new DatabaseBuilder(dbName);
		builder.addClass(Note.class);
		Database.setBuilder(builder);
		
		try { connection = ActiveRecordBase.open(context, dbName, dbVersion); } 
		catch(Exception e) { dbException(e); }
	}
	
	public List<Note> allNotes() {
		try { return connection.findAll(Note.class); } 
		catch(Exception e) { dbException(e); return null; }
	}
	
	public void save(Note note) {
		long id = -1;
		try { 
			id = note.save(); 
			if (id == -1) dbException(new ActiveRecordException("Returned -1 from save")); 
		} 
		catch(Exception e) { dbException(e); }
	}
	
	public void close() {
		if (connection != null) {
			connection.close();
		}
	}

	public Note newNote() {
		try { return connection.newEntity(Note.class); }
		catch(Exception e) { dbException(e); return null; }
	}
	
	public Note loadNote(long id) {
		try { return connection.findByID(Note.class, id); }
		catch(Exception e) { dbException(e); return null; }
	}

	private void dbException(Exception e) {
		throw new RuntimeException(e);
	}
}

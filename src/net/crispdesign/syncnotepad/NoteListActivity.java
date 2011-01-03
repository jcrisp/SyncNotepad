package net.crispdesign.syncnotepad;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

public class NoteListActivity extends ListActivity {

	private List<Note> notes;
	private Db db;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        db = new Db();
        try {
			db.connect(getApplicationContext());
            
        	Note n = db.connection().newEntity(Note.class);
            n.noteText = "Hello this is a note";
            n.save();
            
			notes = db.connection().findAll(Note.class);
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

        NoteArrayAdapter adapter = new NoteArrayAdapter(getApplicationContext(), 
        		R.layout.noteslist_item, notes);
        setListAdapter(adapter);
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	db.closeConnection();
    }
}
package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordException;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class NoteListActivity extends ListActivity {

	private List<Note> notes;
	private Db db;
	private NoteArrayAdapter listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);
		
		//setUpExceptionHandling();

		db = new Db(getApplicationContext());
		updateDisplayedNotes();

		hookEventHandlers();
	}
	
	private void updateDisplayedNotes() {
		notes = db.allNotes();
		listAdapter = new NoteArrayAdapter(getApplicationContext(),
				R.layout.note_list_item, notes);
		setListAdapter(listAdapter);
	}
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent(Intent.ACTION_EDIT, null, this, NoteEditActivity.class);
		intent.putExtra("id", id);
		startActivityForResult(intent, 0);
    }

	private void newNote() {
		Intent intent = new Intent(Intent.ACTION_INSERT, null, this, NoteEditActivity.class);
		startActivityForResult(intent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		updateDisplayedNotes();		
	}
	
    private void setUpExceptionHandling() {
    	Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			public void uncaughtException(Thread thread, Throwable e) {
				Toast.makeText(getApplicationContext(), e.toString() , Toast.LENGTH_LONG).show();
		}});
    }

	private void hookEventHandlers() {
		Button newButton = (Button) findViewById(R.id.new_note_button);
		newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				newNote();
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.close();
	}
}
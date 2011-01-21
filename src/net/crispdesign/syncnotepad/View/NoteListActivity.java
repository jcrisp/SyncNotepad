package net.crispdesign.syncnotepad.View;

import net.crispdesign.syncnotepad.DbAccess.NoteRepository;
import net.crispdesign.syncnotepad.Model.Note;
import net.crispdesign.syncnotepad.R;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class NoteListActivity extends ListActivity {

	private List<Note> notes;
	private NoteRepository noteRepository;
	private NoteArrayAdapter listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);
		
		noteRepository = new NoteRepository(getApplicationContext());
		updateDisplayedNotes();

		hookEventHandlers();
	}
	
	private void updateDisplayedNotes() {
		notes = noteRepository.all();
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
		noteRepository.close();
	}
}
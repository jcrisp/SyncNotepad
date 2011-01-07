package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordException;
import java.util.List;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class NoteListActivity extends ListActivity {

	private List<Note> notes;
	private Db db;
	private NoteArrayAdapter listAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_list);

		db = new Db();
		try {
			db.connect(getApplicationContext());
			notes = db.connection().findAll(Note.class);
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		listAdapter = new NoteArrayAdapter(getApplicationContext(),
				R.layout.note_list_item, notes);
		setListAdapter(listAdapter);

		Button newButton = (Button) findViewById(R.id.new_note_button);
		newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addNote();
			}
		});

	}
	
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		// TODO Auto-generated method stub
//		super.onActivityResult(requestCode, resultCode, data);
//		long noteId = data.getLongExtra("id", -1);
//		if (noteId > 0) {
//			try {
//				listAdapter.add(db.connection().findByID(Note.class, noteId));
//			} catch (ActiveRecordException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}

	private void addNote() {
		Intent intent = new Intent(this, NoteEditActivity.class);
		startActivity(intent);
//		try {
//			Note note = db.connection().newEntity(Note.class);
//			note.noteText = "Hello this is a note";
//			note.save();
//			listAdapter.add(note);
//		} catch (ActiveRecordException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.closeConnection();
	}
}
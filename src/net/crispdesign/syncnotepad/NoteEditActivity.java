package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordException;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Instrumentation.ActivityResult;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NoteEditActivity extends Activity {

	private Db db;
	private Note note;
	private EditText textBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);

		db = new Db();
		try {
			db.connect(getApplicationContext());
			
			long noteId = getIntent().getLongExtra("id", -1);
			
			if (noteId < 0) {
				note = db.connection().newEntity(Note.class);
			} else {
				note = db.connection().findByID(Note.class, noteId);
			}

		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		textBox = (EditText) findViewById(R.id.edit_note_text);
		textBox.setText(note.noteText);

		Button newButton = (Button) findViewById(R.id.done_edit_button);
		newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getIntent().putExtra("id", save());
				setResult(RESULT_OK, getIntent());
				finish();
			}
		});

	}

	private long save() {
		try {
			String noteText = textBox.getText().toString();
			if (noteText.trim().length() == 0) {
				return -1;
			}
			note.noteText = noteText;
			return note.save();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		db.closeConnection();
	}
}
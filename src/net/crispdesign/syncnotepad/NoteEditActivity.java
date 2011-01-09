package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordException;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
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

		db = new Db(this);
		if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
			note = db.newNote();
		} else {
			long id = getIntent().getLongExtra("id", -1);
			note = db.loadNote(id);
		}
		
		textBox = (EditText) findViewById(R.id.edit_note_text);
		textBox.setText(note.noteText);

		Button newButton = (Button) findViewById(R.id.done_edit_button);
		newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(save() ? RESULT_OK : RESULT_CANCELED, getIntent());
				finish();
			}
		});

	}

	private boolean save() {
		String noteText = textBox.getText().toString();
		if (noteText.trim().length() == 0) {
			return false;
		}
		note.noteText = noteText;
		db.save(note);
		return true;
	}

	// BUG IN AR - db is null after new then update?
//	@Override
//	protected void onDestroy() {
//		super.onDestroy();
//		db.close();
//	}
}
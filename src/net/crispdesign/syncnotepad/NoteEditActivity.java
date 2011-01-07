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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);

		db = new Db();
		try {
			db.connect(getApplicationContext());
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		Button newButton = (Button) findViewById(R.id.done_edit_button);
		newButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				getIntent().putExtra("id", save());
				finish();
			}
		});

	}

	private long save() {
		try {
			Note note = db.connection().newEntity(Note.class);
			EditText textBox = (EditText) findViewById(R.id.edit_note_text);
			note.noteText = textBox.getText().toString();
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
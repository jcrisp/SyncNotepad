package net.crispdesign.syncnotepad.View;

import net.crispdesign.syncnotepad.DbAccess.NoteRepository;
import net.crispdesign.syncnotepad.Model.Note;
import net.crispdesign.syncnotepad.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NoteEditActivity extends Activity {

	private NoteRepository noteRepository;
	private Note note;
	private EditText textBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note_edit);

		noteRepository = new NoteRepository(this);
		if (getIntent().getAction().equals(Intent.ACTION_INSERT)) {
			note = new Note();
		} else {
			long id = getIntent().getLongExtra("id", -1);
			note = noteRepository.load(id);
		}
        noteRepository.close();
		
		textBox = (EditText) findViewById(R.id.edit_note_text);
		textBox.setText(note.text);

        hookEventHandlers();
	}

    private void hookEventHandlers() {
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
		note.text = noteText;
		noteRepository.save(note);
		return true;
	}

}
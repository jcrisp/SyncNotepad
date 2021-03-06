package net.crispdesign.syncnotepad.View;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import net.crispdesign.syncnotepad.Model.Note;
import net.crispdesign.syncnotepad.R;

public class NoteArrayAdapter extends ArrayAdapter {
	private final List<Note> notes;

	public NoteArrayAdapter(Context context, int textViewResourceId, List<Note> list) {
		super(context, textViewResourceId, list);
		this.notes = list;
	}

	@Override
	public Note getItem(int index) {
		return (Note) this.notes.get(index);
	}
	
	@Override
	public long getItemId(int index) {
		return getItem(index).id;
	}

	@Override
	public int getCount() {
		return this.notes.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			row = inflater.inflate(R.layout.note_list_item, parent, false);

		}
		// Get reference to TextView holder
		TextView label = (TextView) row.findViewById(R.id.list_item_text);
		Note item = getItem(position);

		String text = item.text.toString();
		label.setText(firstLine(text));

		return row;
	}
	
	private String firstLine(String text) {
		String firstLine = text;
		int indexOfNewLine = text.indexOf("\n");
		if (indexOfNewLine > -1) {
			firstLine = text.substring(0, indexOfNewLine);
		}
		return firstLine;
	}
}

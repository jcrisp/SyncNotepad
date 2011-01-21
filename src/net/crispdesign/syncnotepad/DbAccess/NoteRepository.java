package net.crispdesign.syncnotepad.DbAccess;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import net.crispdesign.syncnotepad.Model.Note;

import android.content.Context;

public class NoteRepository {
    private Schema schema;

    public NoteRepository(Context context) {
        schema = new Schema(context);
    }

    public List<Note> all() {
        List<Note> result = new ArrayList<Note>();
        Cursor cursor = query(null, null);
        try {
            while (cursor.moveToNext()) {
                result.add(noteFromCursor(cursor));
            }
        } finally { cursor.close(); }
        return result;
    }

    public void save(Note note) {
        ContentValues values = new ContentValues();
        values.put("note", note.text);
        SQLiteDatabase db = schema.getWritableDatabase();
        if (note.id == -1) {
            db.insertOrThrow(Schema.NOTES_TABLE, null, values);
        } else {
            db.update(Schema.NOTES_TABLE, values, "id = ?", new String[]{String.valueOf(note.id)});
        }
    }

    public Note load(long id) {
        Cursor cursor = query("id = ?", new String[]{String.valueOf(id)});
        try {
            cursor.moveToFirst();
            return noteFromCursor(cursor);
        } finally { cursor.close(); }
    }

    public void close() {
        schema.close();
    }

    private Cursor query(String whereClause, String[] params) {
        return schema.getReadableDatabase().query(
                Schema.NOTES_TABLE, new String[]{"id", "note", "lastModified"},
                whereClause, params, null, null, null);
    }

    private Note noteFromCursor(Cursor cursor) {
        return new Note(cursor.getLong(0), cursor.getString(1));
    }
}

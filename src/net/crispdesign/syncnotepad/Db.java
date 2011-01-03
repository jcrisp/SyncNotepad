package net.crispdesign.syncnotepad;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.ActiveRecordException;
import org.kroz.activerecord.Database;
import org.kroz.activerecord.DatabaseBuilder;

import android.content.Context;
import android.util.Log;

public class Db {
	private final static String tag = "Main";
	private final static String dbName = "notes.db";
	private final static int dbVersion = 2;

	private DatabaseBuilder builder = null;
	private ActiveRecordBase _connection = null;

	public Db() {
	}

	public void connect(Context context) throws ActiveRecordException {
		if (_connection == null) {
			builder = new DatabaseBuilder(dbName);
			builder.addClass(Note.class);
			Database.setBuilder(builder);
			_connection = ActiveRecordBase.open(context, dbName, dbVersion);
		}
	}
	
	public ActiveRecordBase connection() {
		return _connection;
	}
	
	public void closeConnection() {
		if (_connection != null) {
			_connection.close();
		}
	}

	// public Database getDatabase() throws ActiveRecordException {
	// Database database = Database.createInstance(ctx, dbName, dbVersion);
	// database.open();
	// return database;
	// }

}

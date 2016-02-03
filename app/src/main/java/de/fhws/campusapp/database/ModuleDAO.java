package de.fhws.campusapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

import de.fhws.campusapp.entity.Module;

public class ModuleDAO extends SQLiteOpenHelper {

    private static ModuleDAO instance;

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_MODULE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + ModuleEntry.TABLE_NAME + " (" +
                    ModuleEntry._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    ModuleEntry.COLUMN_MODULE_ECTS + INTEGER_TYPE + COMMA_SEP +
                    ModuleEntry.COLUMN_MODULE_VISITED + INTEGER_TYPE + " )";

    private static final String SQL_DELETE_MODULE_ENTRIES =
            "DROP TABLE IF EXISTS " + ModuleEntry.TABLE_NAME;

    final SQLiteDatabase readDb;
    final SQLiteDatabase writeDb;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "campus_app";

    public static abstract class ModuleEntry implements BaseColumns {
        public static final String TABLE_NAME = "modules";
        public static final String COLUMN_MODULE_VISITED = "visited";
        public static final String COLUMN_MODULE_ECTS = "ects";
    }

    public static ModuleDAO getInstance( Context context ) {
        if( instance == null )
            instance = new ModuleDAO( context );

        return instance;
    }

    @Override
    public void onCreate( SQLiteDatabase db ) {
        db.execSQL( SQL_CREATE_MODULE_ENTRIES );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion ) {
        db.execSQL( SQL_DELETE_MODULE_ENTRIES );
        onCreate( db );
    }

    public long create( Module module ) {
        ContentValues values = new ContentValues();
        values.put( ModuleEntry._ID, module.getId() );
        values.put( ModuleEntry.COLUMN_MODULE_VISITED, module.isVisited() ? 1 : 0 );
        values.put( ModuleEntry.COLUMN_MODULE_ECTS, module.getEcts() );

        return writeDb.insert( ModuleEntry.TABLE_NAME, null, values );
    }

    public int update( ContentValues values, String selection, String[] selectionArgs ) {
        return writeDb.update( ModuleEntry.TABLE_NAME, values, selection, selectionArgs );
    }

    public List<Module> read( String selection, String[] selectionArgs ) {
        List<Module> modules = new ArrayList<>();

        String[] projection = {
                ModuleEntry._ID,
                ModuleEntry.COLUMN_MODULE_VISITED,
                ModuleEntry.COLUMN_MODULE_ECTS
        };

        Cursor c = readDb.query(
                ModuleEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null );

        if( c.moveToFirst() ) {
            do {
                Module currentModule = new Module();
                currentModule.setId(
                        c.getInt( c.getColumnIndexOrThrow( ModuleEntry._ID ) ) );
                currentModule.setEcts(
                        c.getInt( c.getColumnIndexOrThrow( ModuleEntry.COLUMN_MODULE_ECTS ) ) );
                currentModule.setVisited(
                        c.getInt( c.getColumnIndexOrThrow( ModuleEntry.COLUMN_MODULE_VISITED ) ) > 0 );
            } while ( c.moveToNext() );
        }
        c.close();
        return modules;
    }

    private ModuleDAO( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );

        this.readDb = getReadableDatabase();
        this.writeDb = getWritableDatabase();
    }


}

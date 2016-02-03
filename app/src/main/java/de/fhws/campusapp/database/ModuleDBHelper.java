package de.fhws.campusapp.database;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import de.fhws.campusapp.entity.Module;

public class ModuleDBHelper {

    private static ModuleDBHelper instance;
    private final ModuleDAO moduleDAO;

    public static ModuleDBHelper getInstance( Context context ) {
        if( instance == null )
            instance = new ModuleDBHelper( context );

        return instance;
    }

    public Module readModule( int moduleId ) {
        String selection = ModuleDAO.ModuleEntry._ID + " = ?";
        String[] selectionArgs = {Integer.toString( moduleId )};

        List<Module> modules = moduleDAO.read( selection, selectionArgs );

        return modules.size() == 0 ? null : modules.get( 0 );
    }

    public List<Module> readAllModules() {
        return moduleDAO.read( null, null );
    }

    public void createOrUpdate( Module module ) {
        String selection = ModuleDAO.ModuleEntry._ID + " = ?";
        String[] selectionArgs = {Long.toString( module.getId() )};
        List<Module> moduleList = moduleDAO.read( selection, selectionArgs );

        if( !moduleList.isEmpty() ) {
            updateMovieWatched( module );
        } else {
            createNewMovieEntry( module );
        }
    }

    private ModuleDBHelper( Context context ) {
        this.moduleDAO = ModuleDAO.getInstance( context );
    }

    private int updateMovieWatched( Module module ) {
        ContentValues values = new ContentValues();
        values.put( ModuleDAO.ModuleEntry.COLUMN_MODULE_VISITED, module.isVisited() ? 1 : 0 );
        values.put( ModuleDAO.ModuleEntry.COLUMN_MODULE_ECTS, module.getEcts() );
        String selection = ModuleDAO.ModuleEntry._ID + " LIKE ?";
        String[] where = {String.valueOf( module.getId() )};

        return moduleDAO.update( values, selection, where );
    }

    private long createNewMovieEntry( Module module ) {
        return moduleDAO.create( module );
    }
}

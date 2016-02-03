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

    public Module readModule( String moduleId ) {
        String selection = ModuleDAO.ModuleEntry.COLUMN_MODULE_LVID + " = ?";
        String[] selectionArgs = {moduleId};

        List<Module> modules = moduleDAO.read( selection, selectionArgs );

        return modules.size() == 0 ? null : modules.get( 0 );
    }

    public List<Module> readAllModules() {
        return moduleDAO.read( null, null );
    }

    public void createOrUpdate( Module module ) {
        Module existingModule = readModule( module.getLvid() );
        if( existingModule == null ) {
            updateModuleVisited( module );
        } else {
            createNewModule( module );
        }
    }

    private ModuleDBHelper( Context context ) {
        this.moduleDAO = ModuleDAO.getInstance( context );
    }

    private int updateModuleVisited( Module module ) {
        ContentValues values = new ContentValues();
        values.put( ModuleDAO.ModuleEntry.COLUMN_MODULE_VISITED, module.isVisited() ? 1 : 0 );
        values.put( ModuleDAO.ModuleEntry.COLUMN_MODULE_ECTS, module.getEcts() );
        values.put( ModuleDAO.ModuleEntry.COLUMN_MODULE_LVID, module.getLvid() );
        String selection = ModuleDAO.ModuleEntry.COLUMN_MODULE_LVID + " LIKE ?";
        String[] where = {module.getLvid()};

        return moduleDAO.update( values, selection, where );
    }

    private long createNewModule( Module module ) {
        return moduleDAO.create( module );
    }
}

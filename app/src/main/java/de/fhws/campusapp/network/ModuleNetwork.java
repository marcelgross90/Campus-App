package de.fhws.campusapp.network;

import android.content.Context;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhws.campusapp.database.ModuleDBHelper;
import de.fhws.campusapp.entity.Module;

public class ModuleNetwork extends BaseNetwork {

    private final Context context;
    private final ModuleDBHelper moduleDBHelper;

    private static HashMap<String, Module> moduleMap = new HashMap<String, Module>();

    public interface OnModulesFetchedListener {
        void onModulesFetched(List<Module> modules);
    }

    public ModuleNetwork(Context context) {
        super(NetworkSettings.API_URL_MODULES);
        this.context = context;
        this.moduleDBHelper = ModuleDBHelper.getInstance( context );
    }

    public void fetchAllModules(int size, int offset, final OnModulesFetchedListener listener) {
        fetchFilteredModules(null, null, null, size, offset, listener);
    }

    public void fetchFilteredModules(
            String program,
            String language,
            String level,
            int size,
            int offset,
            final OnModulesFetchedListener listener) {

        String url = createFilterUrl( program, language, level, size, offset );
        requestAsync(
                new Request(
                        url,
                        NetworkSettings.METHOD_GET,
                        new String[]{"Accept:application/json"},
                        null
                ),
                new OnResultListener() {
                    @Override
                    public void onResultListener( Response response ) {

                        listener.onModulesFetched(extractModulesFromResponse(response));
                    }
                } );

    }

    private List<Module> extractModulesFromResponse(Response response) {
        List<Module> modules = new ArrayList<>();
        if (successfulRequest( response.getCode() )) {
            Type listType = new TypeToken<List<Module>>() {
            }.getType();
            modules = gson.fromJson(response.getString(), listType);
            for(Module currentModule: modules){
                for ( Module currentDBModule : moduleDBHelper.readAllModules() ) {
                    if( currentModule.getLvid().equals( currentDBModule.getLvid() ) ) {
                        currentModule.setVisited( currentDBModule.isVisited() );
                        break;
                    }
                }

                moduleMap.put(currentModule.getLvid(), currentModule);
            }
        }
        return modules;
    }

    private String createFilterUrl(String program,
                                   String language,
                                   String level,
                                   int size,
                                   int offset) {

        String url = host + "/modules?size=" + size + "&offset=" + offset;

        if (program != null)
            url += "&program=" + program;
        if (language != null)
            url += "&lang=" + language;
        if (level != null) {
            url += "&level=" + level;
        }
        return url;
    }

    public static Module getByLvId(String lvId)
    {
        return moduleMap.get(lvId);
    }
}
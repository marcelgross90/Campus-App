package de.fhws.campusapp.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhws.campusapp.entity.Module;

public class ModuleNetwork implements NetworkConnectionHandler.OnResponseListener
{
    public interface OnModulesFetchedListener
    {
        void onModulesFetched(List<Module> modules);
    }

    private static HashMap<String, Module> moduleMap = new HashMap<>();
    private OnModulesFetchedListener listener;

    public static Module getByLvId(String lvId)
    {
        return moduleMap.get(lvId);
    }

    public ModuleNetwork( OnModulesFetchedListener listener )
    {
        this.listener = listener;
    }

    public void fetchModules( String program, String language, String level, int size, int offset )
    {
        String url = NetworkSettings.getModuleUrl(program, language, level, size, offset);

        Request request = new Request(
                url,
                NetworkSettings.METHOD_GET,
                new String[]{"Accept:application/json"},
                null );

        NetworkConnectionHandler.requestAsync(request, this);
    }

    @Override
    public void onSuccess(Response response)
    {
        listener.onModulesFetched(extractModulesFromResponse(response));
    }

    @Override
    public void onError() {}

    private List<Module> extractModulesFromResponse(Response response) {
        List<Module> modules = new ArrayList<>();
        if (NetworkSettings.successfulRequest(response.getCode())) {
            Type listType = new TypeToken<List<Module>>() {
            }.getType();
            modules = NetworkSettings.GSON.fromJson(response.getString(), listType);
            for(Module currentModule: modules){
                moduleMap.put(currentModule.getLvid(), currentModule);
            }
        }
        return modules;
    }
}
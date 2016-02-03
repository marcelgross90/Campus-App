package de.fhws.campusapp.network;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.fhws.campusapp.entity.Module;

public class ModuleNetworkConnectionHandler extends NetworkConnectionHandler {

    private static HashMap<String, Module> moduleMap = new HashMap<String, Module>();

    public interface FetchAllModulesListener {
        void fetchAllModules(List<Module> allModules);
    }

    public interface FetchFilteredModules {
        void fetchFilteredModules(List<Module> modules);
    }

    public void fetchAllModules(int size, int offset, final FetchAllModulesListener listener) {
        String url = "http://193.175.31.146:8080/fiwincoming/api/modules?size=" + size + "&offset=" + offset;
        requestAsync(
                new Request(
                        url,
                        NetworkSettings.METHOD_GET,
                        new String[]{"Accept:application/json"},
                        null
                ),
                new OnResponseListener() {
                    @Override
                    public void onSuccess(Response response) {

                        listener.fetchAllModules(extractModulesFromResponse(response));
                    }

                    @Override
                    public void onError() {}

                });
    }

    public void fetchFilteredModules(
            String program,
            String language,
            String level,
            int size,
            int offset,
            final FetchFilteredModules listener) {

        String url = createFilterUrl(program, language, level, size, offset);
        requestAsync(
                new Request(
                        url,
                        NetworkSettings.METHOD_GET,
                        new String[]{"Accept:application/json"},
                        null
                ),
                new OnResponseListener() {
                    @Override
                    public void onSuccess(Response response) {

                        listener.fetchFilteredModules(extractModulesFromResponse(response));
                    }

                    @Override
                    public void onError() {}

                });

    }

    private List<Module> extractModulesFromResponse(Response response) {
        List<Module> modules = new ArrayList<>();
        if (successfulRequest(response.getCode())) {
            Type listType = new TypeToken<List<Module>>() {
            }.getType();
            modules = gson.fromJson(response.getString(), listType);
            for(Module currentModule: modules){
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

        String url = "http://193.175.31.146:8080/fiwincoming/api/modules?size=" + size + "&offset=" + offset;

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
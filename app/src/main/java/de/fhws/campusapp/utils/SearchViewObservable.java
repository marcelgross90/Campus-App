package de.fhws.campusapp.utils;


import android.support.v7.widget.SearchView;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fhws.campusapp.R;

/**
 * Created by christianbraun on 03/02/16.
 */
public class SearchViewObservable {

    public interface OnQueryChangeListener {
        void onChange(String searchTerm);

        void onSubmit(String searchTerm);
    }

    private static HashMap<String, SearchView> searchViewsMap = new HashMap<>();
    private static HashMap<String, List<OnQueryChangeListener>> changeListener = new HashMap<>();


    public static void addSearchView(String key, SearchView view) {
        searchViewsMap.remove(key);
        searchViewsMap.put(key, view);
        attachViewToListener(key, view);
    }

    public static boolean subscribe(final String key, OnQueryChangeListener listener) {
        SearchView view = searchViewsMap.get(key);
        List<OnQueryChangeListener> queryListener = changeListener.get(key);
        if (view != null) {
            if (queryListener == null) {
                queryListener = new LinkedList<>();
                changeListener.put(key, queryListener);
            }
            queryListener.remove(listener);
            queryListener.add(listener);

            attachViewToListener(key, view);
            return true;
        }
        return false;
    }

    public static void unsubscribe(String key, OnQueryChangeListener listener) {
        List<OnQueryChangeListener> listeners = changeListener.get(key);

        if (listeners != null) {
            listeners.remove(listener);
        }
    }


    private static void attachViewToListener(final String key, SearchView view) {

        if(changeListener.get(key) != null){

            view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    List<OnQueryChangeListener> queryListener = changeListener.get(key);
                    for (OnQueryChangeListener currentListener : queryListener) {
                        currentListener.onSubmit(query);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    List<OnQueryChangeListener> queryListener = changeListener.get(key);
                    for (OnQueryChangeListener currentListener : queryListener) {
                        currentListener.onChange(newText);
                    }
                    return true;
                }
            });
        }
    }

}

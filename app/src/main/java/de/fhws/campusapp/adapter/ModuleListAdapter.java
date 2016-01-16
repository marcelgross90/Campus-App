package de.fhws.campusapp.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.network.ModuleNetwork;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder> {

    private List<Module> filteredModulesDataset;
    private List<Module> allModulesDataset;
    private ModuleNetwork moduleRestService;
    private OnCardClickListener listener;
    private Resources res;
    private String searchTerm;
    private String level;
    private Context context;

    public interface OnCardClickListener {
        public void onCardClick( Module module );
    }

    public ModuleListAdapter( Context context, OnCardClickListener listener, String level ) {
        moduleRestService = new ModuleNetwork();
        filteredModulesDataset = new LinkedList<>();
        allModulesDataset = new LinkedList<>();
        this.listener = listener;
        res = context.getResources();
        this.level = level;
        this.context = context;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String program = sharedPreferences.getString( "mychoice", Module.Program.BIN );
        downloadData( program, level );
        registerPrefChangeListener();
    }

    private void downloadData(String program, String level) {
        moduleRestService.fetchFilteredModules( program, null,
                level, 0, 0,
                new ModuleNetwork.FetchFilteredModules() {

            @Override
            public void fetchFilteredModules(List<Module> modules) {
                filteredModulesDataset = modules;
                notifyDataSetChanged();
                allModulesDataset.addAll(modules);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView moduleCard;
        public TextView moduleTitle;
        public TextView moduleEcts;
        private Module module;

        public ViewHolder( View itemView ) {
            super( itemView );
            moduleCard = (CardView) itemView.findViewById( R.id.module_card );
            moduleTitle = (TextView) itemView.findViewById( R.id.module_title_tv );
            moduleEcts = (TextView) itemView.findViewById( R.id.module_ects_tv );
        }

        public void assignData( final Module module ) {
            this.module = module;
            moduleTitle.setText( module.getLvnameGerman() );
            moduleEcts.setText( String.format( res.getString( R.string.ects ), module.getEcts() ) );
            moduleCard.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    listener.onCardClick( module );
                }
            } );
        }
    }

    @Override
    public ModuleListAdapter.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType ) {
        View moduleCard = LayoutInflater
                .from( parent.getContext() )
                .inflate( R.layout.card_modules, parent, false );
        ViewHolder vh = new ViewHolder( moduleCard );
        return vh;
    }

    @Override
    public void onBindViewHolder(ModuleListAdapter.ViewHolder holder, int position) {
        holder.asignData(filteredModulesDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredModulesDataset.size();
    }

    public void filter(String searchTerm){
        if (!searchTerm.isEmpty()) {

            for (Module currrentModule : allModulesDataset) {
                String lecturerName = currrentModule.getLvnameGerman().toLowerCase();
                int index = filteredModulesDataset.indexOf(currrentModule);
                if (!lecturerName.startsWith(searchTerm.toLowerCase())) {
                    if (index != -1) {
                        filteredModulesDataset.remove(index);
                        notifyItemRemoved(index);
                    }
                } else {
                    if (index == -1) {
                        filteredModulesDataset.add(currrentModule);
                        notifyItemInserted(filteredModulesDataset.size());

                    }
                }
            }
        } else {
            filteredModulesDataset = (List<Module>)((LinkedList<Module>) allModulesDataset).clone();
            notifyDataSetChanged();
        }
    }

    private void registerPrefChangeListener() {
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener(){
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                String program = prefs.getString( "mychoice", Module.Program.BIN );
                downloadData( program, level );
            }
        });
    }

}
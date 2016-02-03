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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

import de.fhws.campusapp.R;
import de.fhws.campusapp.database.ModuleDBHelper;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.network.ModuleNetwork;
import de.fhws.campusapp.receiver.NetworkChangeReceiver;
import de.fhws.campusapp.utils.SearchViewObservable;

public class ModuleListAdapter extends RecyclerView.Adapter<ModuleListAdapter.ViewHolder> implements NetworkChangeReceiver.NetworkListener, SearchViewObservable.OnQueryChangeListener {

    public static String oldSearchTerm;
    private String program;
    private List<Module> filteredModulesDataset;
    private List<Module> allModulesDataset;
    private ModuleNetwork moduleRestService;
    private OnCardClickListener listener;
    private Resources res;
    private Context context;
    private final ActivateProgressBar activateProgressBar;
    private final ModuleDBHelper moduleDBHelper;
    private String level;

    @Override
    public void onChange(String searchTerm) {
        filter(searchTerm);
    }

    @Override
    public void onSubmit(String searchTerm) {
        filter(searchTerm);
    }

    public interface OnCardClickListener {
        void onCardClick(Module module);
    }

    public interface ActivateProgressBar {
        void showProgressBar(boolean activate);
    }

    @Override
    public void networkAvailable() {
        downloadData();
    }

    @Override
    public void networkNotAvailable() {
        Toast.makeText(context, R.string.noInternet, Toast.LENGTH_SHORT).show();
    }

    public ModuleListAdapter(Context context, OnCardClickListener listener, String level, ActivateProgressBar activateProgressBar) {
        moduleDBHelper = ModuleDBHelper.getInstance( context );
        moduleRestService = new ModuleNetwork(context);
        filteredModulesDataset = new LinkedList<>();
        allModulesDataset = new LinkedList<>();
        this.listener = listener;
        this.context = context;
        this.activateProgressBar = activateProgressBar;
        res = context.getResources();
        this.level = level;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        program = sharedPreferences.getString( "mychoice", Module.Program.BIN );

        NetworkChangeReceiver.getInstance(this);
        downloadData();
    }

    private void downloadData() {
        activateProgressBar.showProgressBar(true);
        moduleRestService.fetchFilteredModules(program, null,
                level, 0, 0,
                new ModuleNetwork.FetchFilteredModules() {

                    @Override
                    public void fetchFilteredModules(List<Module> modules) {
                        allModulesDataset = modules;

                        filter(oldSearchTerm);
                        activateProgressBar.showProgressBar(false);
                    }
                });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView moduleCard;
        public TextView moduleTitle;
        public CheckBox moduleCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            moduleCard = (CardView) itemView.findViewById(R.id.module_card);
            moduleTitle = (TextView) itemView.findViewById(R.id.module_title_tv);
            moduleCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
        }

        public void assignData(final Module module) {
            moduleTitle.setText(module.getLvnameGerman());
            moduleCheckBox.setChecked( module.isVisited() );
            moduleCard.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick( View view ) {
                    listener.onCardClick( module );
                }
            } );
            moduleCheckBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged( CompoundButton buttonView, boolean isChecked ) {
                    module.setVisited( isChecked );
                    moduleDBHelper.createOrUpdate( module );
                }
            } );
        }
    }


    public void subscribeToSearchBar(){
        SearchViewObservable.subscribe("ModulesSearch", this);
    }

    public void unsubscribeToSearchBar(){
        SearchViewObservable.unsubscribe("ModuleSearch", this);
    }

    @Override
    public ModuleListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View moduleCard = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.card_modules, parent, false);
        ViewHolder vh = new ViewHolder(moduleCard);
        return vh;
    }

    @Override
    public void onBindViewHolder(ModuleListAdapter.ViewHolder holder, int position) {
        holder.assignData(filteredModulesDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredModulesDataset.size();
    }

    public void filter(String searchTerm) {

        if (searchTerm != null && !searchTerm.isEmpty()) {
            oldSearchTerm = searchTerm;
            for (Module currentModule : allModulesDataset) {
                String lecturerName = currentModule.getLvnameGerman().toLowerCase();
                int index = filteredModulesDataset.indexOf(currentModule);
                if (!lecturerName.startsWith(searchTerm.toLowerCase())) {
                    if (index != -1) {
                        filteredModulesDataset.remove(index);
                        notifyItemRemoved(index);
                    }
                } else {
                    if (index == -1) {
                        filteredModulesDataset.add(currentModule);
                        notifyItemInserted(filteredModulesDataset.size());

                    }
                }
            }
        } else {
            filteredModulesDataset.clear();
            filteredModulesDataset.addAll(allModulesDataset);
            oldSearchTerm = null;
            notifyDataSetChanged();
        }
    }

    public void programChanged(String program) {
        this.program = program;
        downloadData();
    }

    @Override
    public boolean equals(Object o) {
        return this.level == ((ModuleListAdapter)o).level;
    }
}
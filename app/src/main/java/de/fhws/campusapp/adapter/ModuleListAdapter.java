package de.fhws.campusapp.adapter;

import android.content.Context;
import android.content.res.Resources;
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

    private List<Module> moduleDataset;
    private ModuleNetwork moduleRestService;
    private OnCardClickListener listener;
    private Resources res;


    public interface OnCardClickListener{
        public void onCardClick(Module module);
    }

    public ModuleListAdapter(Context context, OnCardClickListener listener, String level, String program) {
        moduleRestService = new ModuleNetwork();
        moduleDataset = new LinkedList<>();
        this.listener = listener;
        res = context.getResources();

        moduleRestService.fetchFilteredModules(program, null,
                level, 0, 0,
                new ModuleNetwork.FetchFilteredModules() {
            @Override
            public void fetchFilteredModules(List<Module> modules) {
                moduleDataset = modules;
                notifyDataSetChanged();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public CardView moduleCard;
        public TextView moduleTitle;
        public TextView moduleEcts;
        private Module module;

        public ViewHolder(View itemView) {
            super(itemView);
            moduleCard = (CardView) itemView.findViewById(R.id.module_card);
            moduleTitle = (TextView) itemView.findViewById(R.id.module_title_tv);
            moduleEcts =(TextView) itemView.findViewById(R.id.module_ects_tv);
        }

        public void asignData(final Module module){
            this.module = module;
            moduleTitle.setText(module.getLvnameGerman());
            moduleEcts.setText(String.format(res.getString(R.string.ects), module.getEcts()));
            moduleCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   listener.onCardClick(module);
                }
            });
        }
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
        holder.asignData(moduleDataset.get(position));
    }

    @Override
    public int getItemCount() {
        return moduleDataset.size();
    }
}

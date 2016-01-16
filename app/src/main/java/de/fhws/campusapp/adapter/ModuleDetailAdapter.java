package de.fhws.campusapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Module;

public class ModuleDetailAdapter extends RecyclerView.Adapter
{
    private Module module;

    public ModuleDetailAdapter( Module module )
    {
        this.module = module;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType )
    {
        RecyclerView.ViewHolder result = null;

        if ( viewType == 0 )
        {
            int layout = R.layout.card_module_detail_general;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new ModuleDetailViewHolderGeneral( v );
        }
        else if ( viewType == 1 )
        {
            int layout = R.layout.card_module_detail_content;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new ModuleDetailViewHolderContent( v );
        }
        else if ( viewType == 2 )
        {
            int layout = R.layout.card_module_detail_effort;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new ModuleDetailViewHolderEffort( v );
        }
        else if ( viewType == 3 )
        {
            int layout = R.layout.card_module_detail_exam;
            View v = LayoutInflater.from( parent.getContext() ).inflate( layout, parent, false );
            result = new ModuleDetailViewHolderExam( v );
        }
        return result;
    }

    @Override
    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position )
    {
        if( position == 0 )
        {
            ModuleDetailViewHolderGeneral viewHolder = (ModuleDetailViewHolderGeneral) holder;
            viewHolder.displayLecturers( module.getLecturers() );
            viewHolder.displayStudy( module.getStudy() );
            viewHolder.displayYear( String.valueOf( module.getLevel() ) );
        }
        else if( position == 1 )
        {
            ModuleDetailViewHolderContent viewHolder = (ModuleDetailViewHolderContent) holder;
            viewHolder.displayLectureType( module.getTypeOfClass() );
            viewHolder.displayDescription(module.getContents());
            viewHolder.displayGoals( module.getGoals() );
        }
        else if( position == 2 )
        {
            ModuleDetailViewHolderEffort viewHolder = (ModuleDetailViewHolderEffort) holder;
            viewHolder.displayCredits( String.valueOf( module.getEcts() ) );
            viewHolder.displayHoursPerWeek( String.valueOf( module.getHoursLectures() ) );
            // TODO: Handle distribution
        }
        else if( position == 3 )
        {
            ModuleDetailViewHolderExam viewHolder = (ModuleDetailViewHolderExam) holder;
            viewHolder.displayModuleID( module.getLvid() );
            viewHolder.displayExamType( module.getTypeOfExam() );
            viewHolder.displayLiterature( module.getLiterature() );
        }
    }

    @Override
    public int getItemCount()
    {
        return 4;   // TODO: think about an array
    }

    @Override
    public int getItemViewType( int position )
    {
        return position;
    }
}
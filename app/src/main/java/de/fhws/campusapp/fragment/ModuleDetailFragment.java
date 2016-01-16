package de.fhws.campusapp.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.ModuleDetailAdapter;
import de.fhws.campusapp.entity.Module;
import de.fhws.campusapp.network.ModuleNetwork;

public class ModuleDetailFragment extends Fragment
{
    // components
    private RecyclerView recyclerView;

    // content
    private Module module;

    public ModuleDetailFragment()
    {
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState )
    {
        loadComponents(container);

        module = getFakeModule();



        recyclerView.setAdapter( new ModuleDetailAdapter( module ) );

        return inflater.inflate( R.layout.fragment_module_detail, container, false );
    }

    @Override
    public void setArguments( Bundle args )
    {
        super.setArguments(args);

        String moduleID = args.getString( "lvId" );

    }

    private Module getFakeModule()
    {
        Module fakeModule = new Module();
        fakeModule.setLvnameGerman("Einführung in die hohe Kunst der tiefen Schläge");
        fakeModule.setDozenten("Braun, Christian#Groß, Marcel#Therolf, Marvin");
        fakeModule.setBec(true);
        fakeModule.setBwi(true);
        fakeModule.setBin(true);
        fakeModule.setLevel(1);
        fakeModule.setTypeOfClass("Okultes Riual");
        fakeModule.setContents("Lorem ipsum... bitch!");
        fakeModule.setGoals("Der Weg ist das Ziel.");
        fakeModule.setEcts(10);
        fakeModule.setSws(2);
        fakeModule.setHoursLectures(10);
        fakeModule.setHoursSelfStudy(30);
        fakeModule.setLvid("MT100389");
        fakeModule.setTypeOfExam("Mutprobe");
        fakeModule.setLiterature( "The Holy Bible" );

        return fakeModule;
    }

    private void loadComponents( ViewGroup container )
    {
        recyclerView = (RecyclerView) container.findViewById( R.id.rvModuleDetails );
    }
}
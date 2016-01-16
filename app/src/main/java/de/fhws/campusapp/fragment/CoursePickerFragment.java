package de.fhws.campusapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import de.fhws.campusapp.R;


public class CoursePickerFragment extends DialogFragment {

    private boolean[] choice = new boolean[3];
    private RadioButton bin;
    private RadioButton win;
    private RadioButton ec;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate( R.layout.fragment_course_picker, container, false );
        getDialog().setTitle(R.string.card_caption_study);
        loadSavedPreferences();
        bin = (RadioButton) view.findViewById( R.id.bin );
        win = (RadioButton) view.findViewById( R.id.win );
        ec = (RadioButton) view.findViewById( R.id.ec );

        bin.setChecked( choice[0] );
        win.setChecked( choice[1] );
        ec.setChecked( choice[2] );

        final RadioGroup choiceGroup = (RadioGroup) view.findViewById( R.id.radioButtons );

        choiceGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged( RadioGroup group, int checkedId ) {

                switch ( checkedId ) {
                    case R.id.bin:
                        choice[0] = true;
                        choice[1] = false;
                        choice[2] = false;
                        break;
                    case R.id.win:
                        choice[0] = false;
                        choice[1] = true;
                        choice[2] = false;
                        break;
                    case R.id.ec:
                        choice[0] = false;
                        choice[1] = false;
                        choice[2] = true;
                        break;
                }


                savePreferences();
                dismiss();
            }
        } );

        return view;
    }


    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (int i = 0; i < choice.length; i++){
            choice[i] = sharedPreferences.getBoolean("choice" +i, true);
        }
    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i <  choice.length; i++){
            editor.putBoolean("choice" +i, choice[i]);
        }

        editor.apply();
    }
}

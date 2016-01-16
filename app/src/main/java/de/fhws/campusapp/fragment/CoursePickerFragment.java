package de.fhws.campusapp.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import de.fhws.campusapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoursePickerFragment extends DialogFragment {

    private RadioButton bin;
    private RadioButton win;
    private RadioButton ec;


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_course_picker, container, false );
        getDialog().setTitle("My Dialog Title");
        bin = (RadioButton) view.findViewById( R.id.bin );
        win = (RadioButton) view.findViewById( R.id.win );
        ec = (RadioButton) view.findViewById( R.id.ec );

        return view;
    }


    public void savePreferences(boolean[] moduleChoise) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < moduleChoise.length; i++){
            editor.putBoolean(getString( R.string.app_name ) +i, moduleChoise[i]);
        }

        editor.apply();
    }
}

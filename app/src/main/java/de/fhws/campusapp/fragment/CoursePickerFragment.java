package de.fhws.campusapp.fragment;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import de.fhws.campusapp.R;
import de.fhws.campusapp.entity.Module;


public class CoursePickerFragment extends DialogFragment {

    private String myChoice = "";
    private RadioButton bin;
    private RadioButton win;
    private RadioButton ec;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_course_picker, null);
        builder.setView(view);
        builder.setTitle(R.string.card_caption_study);
        loadSavedPreferences();
        bin = (RadioButton) view.findViewById( R.id.bin );
        win = (RadioButton) view.findViewById( R.id.win );
        ec = (RadioButton) view.findViewById( R.id.ec );


        switch ( myChoice ) {
            case Module.Program.BIN:
                bin.setChecked( true );
                break;
            case Module.Program.BWI:
                win.setChecked( true );
                break;
            case Module.Program.BEC:
                ec.setChecked( true );
                break;
        }

        final RadioGroup choiceGroup = (RadioGroup) view.findViewById( R.id.radioButtons );

        choiceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.bin:
                        myChoice = Module.Program.BIN;
                        break;
                    case R.id.win:
                        myChoice = Module.Program.BWI;
                        break;
                    case R.id.ec:
                        myChoice = Module.Program.BEC;
                        break;
                }

                savePreferences();
                dismiss();
            }
        });

        return builder.create();
    }

    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        myChoice = sharedPreferences.getString( "mychoice", Module.Program.BIN );

    }

    public void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences( getActivity() );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString( "mychoice", myChoice );

        editor.apply();
    }
}

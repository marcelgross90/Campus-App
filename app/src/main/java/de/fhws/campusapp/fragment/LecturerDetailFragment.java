package de.fhws.campusapp.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import de.fhws.campusapp.MainActivity;
import de.fhws.campusapp.R;
import de.fhws.campusapp.adapter.LecturerData;
import de.fhws.campusapp.entity.Lecturer;


public class LecturerDetailFragment extends Fragment implements View.OnClickListener  {

    private Lecturer lecturer;

    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        MainActivity.drawerToggle.setDrawerIndicatorEnabled( false );
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        final View view = inflater.inflate( R.layout.fragment_lecturer_detail, container, false );

        Bundle bundle = getArguments();
        final int position = bundle.getInt( "position" );
        loadLecturer(position);

        ImageView email = (ImageView) view.findViewById( R.id.email_ibtn );
        ImageView website = (ImageView) view.findViewById( R.id.website_ibtn );
        ImageView phone = (ImageView) view.findViewById( R.id.phone_ibtn );
        ImageView home = (ImageView) view.findViewById( R.id.home_ibtn );

        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById( R.id.frame );
        LinearLayout linearLayout = (LinearLayout) view.findViewById( R.id.linear );
        relativeLayout.setBackgroundColor(getActivity().getResources().getColor( R.color.colorPrimary));
        linearLayout.setBackgroundColor(getActivity().getResources().getColor( R.color.colorPrimary));

        ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicture_iv);
        getActivity().setTitle( lecturer.getTitle() + " " +
                lecturer.getName() + " " +
                lecturer.getLastName() );

        final Uri uri = Uri.parse( lecturer.getPictureUrl() );
        Picasso.with( getActivity() ).load(uri).into( profilePic );


        email.setOnClickListener(this);
        website.setOnClickListener(this);
        phone.setOnClickListener(this);
        home.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_ibtn:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { lecturer.getEmail() });
                startActivity(intent);
                break;
            case R.id.website_ibtn:
                Intent intentWeb = new Intent(Intent.ACTION_VIEW);
                intentWeb.setData(Uri.parse( lecturer.getWebsite()));
                startActivity( intentWeb );
                break;
            case R.id.phone_ibtn:
                Intent intentPhone = new Intent(Intent.ACTION_DIAL);
                String p = "tel:" + lecturer.getPhoneNumber();
                intentPhone.setData(Uri.parse(p));
                startActivity(intentPhone);
                break;
            case R.id.home_ibtn:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Büro")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage( lecturer.getOfficeNumber()+ "\n" + lecturer.getAddress())
                        .setPositiveButton( "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick( DialogInterface dialog, int which ) {
                                dialog.dismiss();
                            }
                        } ).show();
                break;
        }
    }

    private void loadLecturer(int position) {
        LecturerData instance = LecturerData.getInstance( getActivity() );
        lecturer = instance.getLecturer(position);
    }

}

package de.fhws.campusapp.adapter;


import android.content.Context;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import de.fhws.campusapp.entity.Lecturer;

public class LecturerData {

    private static LecturerData INSTANCE;

    public static LecturerData getInstance( Context context ) {
        INSTANCE = new LecturerData( context );
        return INSTANCE;
    }

    public static LecturerData getInstance() {
        return INSTANCE;
    }

    private final List<Lecturer> setOfLecturers;

    private final Map<Integer, Lecturer> mapIdToLecturer;

    private LecturerData( Context context ) {
        this.setOfLecturers = new LinkedList<>();
        this.mapIdToLecturer = new HashMap<>();
        loadResources( context );
    }

    public Lecturer getLecturerById( int id ) {
        return this.mapIdToLecturer.get( id );
    }

    public Lecturer getLecturer( int position ) {
        return this.setOfLecturers.get( position );
    }

    public int size() {
        return this.setOfLecturers.size();
    }

    public List<Lecturer> getLecturers() {
        List<Lecturer> returnValue = new LinkedList<>();

        returnValue.addAll( this.setOfLecturers );

        return returnValue;
    }

    public final Collection<Lecturer> findByFirstNameAndLastName(
            String firstName,
            String lastName ) {
        Collection<Lecturer> returnValue = new LinkedList<>();

        for ( Lecturer lecturer : this.setOfLecturers ) {
            if( lecturer.getName().equalsIgnoreCase( firstName )
                    && lecturer.getLastName().equalsIgnoreCase( lastName ) ) {
                returnValue.add( lecturer );
            }
        }

        return returnValue;
    }

    private void loadResources( Context context ) {
        try {
            final InputStream is = context.getAssets().open( "lecturers.csv" );
            final InputStreamReader inputReader = new InputStreamReader( is );
            final CSVParser csvParser = new CSVParser( inputReader, CSVFormat.newFormat( ';' ) );
            int i = 0;

            for ( CSVRecord lecturerRecord : csvParser ) {
                readLecturer( lecturerRecord, i );
                i++;
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    private void readLecturer( CSVRecord record, int id ) {
        final Lecturer lecturer = new Lecturer();
        int column = 0;

        lecturer.setId( id );
        lecturer.setName( record.get( column++ ) );
        lecturer.setLastName( record.get( column++ ) );
        lecturer.setTitle( record.get( column++ ) );
        lecturer.setEmail( record.get( column++ ) );
        lecturer.setPhoneNumber( record.get( column++ ) );
        lecturer.setWebsite( record.get( column++ ) );
        lecturer.setPictureUrl( record.get( column++ ) );
        lecturer.setPhoneNumber( record.get( column++ ) );
        lecturer.setAddress( record.get( column++ ) );
        lecturer.setOfficeNumber( record.get( column ) );

        this.setOfLecturers.add( lecturer );
        this.mapIdToLecturer.put( id, lecturer );

    }
}

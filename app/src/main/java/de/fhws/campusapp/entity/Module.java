package de.fhws.campusapp.entity;


public class Module {

    private int id;
    private boolean bec;
    private boolean bin;
    private boolean bwi;
    private String contents;
    private String dozenten;
    private int ects;
    private String goals;
    private int hoursLectures;
    private int hoursSelfStudy;
    private String language;
    private int level;
    private String literature;
    private String lvid;
    private String lvnameEnglish;
    private String lvnameGerman;
    private String media;
    private boolean mis;
    private String programm;
    private String qualification;
    private String semesterInYear;
    private int sws;
    private String typeOfClass;
    private String typeOfExam;
    private boolean visited;

    public interface Program {
        String BEC = "bec";
        String BIN = "bin";
        String BWI = "bwi";
    }

    public interface Language {
        String GER = "ger";
        String EN = "en";
    }

    public interface Level {
        String ONE = "1";
        String TWO = "2";
        String THREE = "3";
        String FOUR = "4";
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public boolean isBec() {
        return bec;
    }

    public void setBec( boolean bec ) {
        this.bec = bec;
    }

    public boolean isBin() {
        return bin;
    }

    public void setBin( boolean bin ) {
        this.bin = bin;
    }

    public boolean isBwi() {
        return bwi;
    }

    public void setBwi( boolean bwi ) {
        this.bwi = bwi;
    }

    public String getContents() {
        return contents;
    }

    public void setContents( String contents ) {
        this.contents = contents;
    }

    public String getDozenten() {
        return dozenten;
    }

    public void setDozenten( String dozenten ) {
        this.dozenten = dozenten;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts( int ects ) {
        this.ects = ects;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals( String goals ) {
        this.goals = goals;
    }

    public int getHoursLectures() {
        return hoursLectures;
    }

    public void setHoursLectures( int hoursLectures ) {
        this.hoursLectures = hoursLectures;
    }

    public int getHoursSelfStudy() {
        return hoursSelfStudy;
    }

    public void setHoursSelfStudy( int hoursSelfStudy ) {
        this.hoursSelfStudy = hoursSelfStudy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage( String language ) {
        this.language = language;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel( int level ) {
        this.level = level;
    }

    public String getLiterature() {
        return literature;
    }

    public void setLiterature( String literature ) {
        this.literature = literature;
    }

    public String getLvid() {
        return lvid;
    }

    public void setLvid( String lvid ) {
        this.lvid = lvid;
    }

    public String getLvnameEnglish() {
        return lvnameEnglish;
    }

    public void setLvnameEnglish( String lvnameEnglish ) {
        this.lvnameEnglish = lvnameEnglish;
    }

    public String getLvnameGerman() {
        return lvnameGerman;
    }

    public void setLvnameGerman( String lvnameGerman ) {
        this.lvnameGerman = lvnameGerman;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia( String media ) {
        this.media = media;
    }

    public boolean isMis() {
        return mis;
    }

    public void setMis( boolean mis ) {
        this.mis = mis;
    }

    public String getProgramm() {
        return programm;
    }

    public void setProgramm( String programm ) {
        this.programm = programm;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification( String qualification ) {
        this.qualification = qualification;
    }

    public String getSemesterInYear() {
        return semesterInYear;
    }

    public void setSemesterInYear( String semesterInYear ) {
        this.semesterInYear = semesterInYear;
    }

    public int getSws() {
        return sws;
    }

    public void setSws( int sws ) {
        this.sws = sws;
    }

    public String getTypeOfClass() {
        return typeOfClass;
    }

    public void setTypeOfClass( String typeOfClass ) {
        this.typeOfClass = typeOfClass;
    }

    public String getTypeOfExam() {
        return typeOfExam;
    }

    public void setTypeOfExam( String typeOfExam ) {
        this.typeOfExam = typeOfExam;
    }

    public String getLecturers()
    {
        return dozenten.replace( "#", "\n" );
    }

    public String getStudy()
    {
        String result = "";

        if ( bin )
        {
            result = result + Globals.BIN_TEXT_GER + " ";
        }
        if ( bwi )
        {
            result = result + Globals.BWI_TEXT_GER + " ";
        }
        if ( bec )
        {
            result = result + Globals.BEC_TEXT_GER + " ";
        }
        result = result.trim();
        return result.replace( " ", ", " );
    }

    public String getLectureType()
    {
        return typeOfClass.replace( "#", "\n" );
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited( boolean visited ) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        Module thisModule = (Module)this;
        Module otherModule = (Module)o;
        return thisModule.getLvnameGerman().equals(otherModule.getLvnameGerman());
    }
}
package de.fhws.campusapp.entity;

import com.google.gson.annotations.SerializedName;

public class Lecturer {

    private int id;
    @SerializedName( "firstName" )
    private String name;
    private String lastName;
    private String title;
    private String email;
    @SerializedName( "urlWelearn" )
    private String website;
    @SerializedName( "urlProfileImage" )
    private String pictureUrl;
    @SerializedName( "phone" )
    private String phoneNumber;
    private String address;
    @SerializedName( "roomNumber" )
    private String officeNumber;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }

    @Override
    public String toString() {

        return "name: " + name +
                "\nlastName: " + lastName +
                "\ntitle: " + title +
                "\nemail: " + email +
                "\nwebsite: " + website +
                "\npicUrl: " + pictureUrl +
                "\nphoneNumber: " + phoneNumber +
                "\naddress: " + address +
                "\nofficeNr: " + officeNumber;
    }

    public String getFullName()
    {
        String fullName = title + " " + name + " " + lastName;

        return fullName.trim();
    }

    public String getStreet()
    {
        String street = "";

        String[] addressAsArray = address.split( " " );

        if ( addressAsArray.length == 4 )
        {
            street = addressAsArray[0] + " " + addressAsArray[1];
        }
        return street;
    }

    public String getTown()
    {
        String town = "";

        String[] addressAsArray = address.split( " " );

        if ( addressAsArray.length == 4 )
        {
            town = addressAsArray[2] + " " + addressAsArray[3];
        }
        return town;
    }

    public String getSubject()
    {
        return Globals.INFORMATION_NOT_AVAILABLE_YET;
    }
}
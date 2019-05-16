package ml.oopscpp.interweb;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

public class Event implements Parcelable {

    private String eventTitle;
    private String eventDate;
    private String eventImage;
    private String eventVenue;
    private ArrayList<String> eventParticipants;
    private ArrayList<String> eventCollaborators;

    public Event(){

    }

    public Event(String imageUri, String title, String date, String venue, ArrayList<String> participants, ArrayList<String> collaborators){
        this.eventImage = imageUri;
        this.eventTitle = title;
        this.eventDate = date;
        this.eventVenue = venue;
        this.eventParticipants = participants;
        this.eventCollaborators = collaborators;
    }

    public Event(Parcel source) {
        eventImage = source.readString();
        eventTitle = source.readString();
        eventDate = source.readString();
        eventVenue = source.readString();
        eventParticipants = source.createStringArrayList();
        eventCollaborators = source.createStringArrayList();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventTitle);
        dest.writeString(eventDate);
        dest.writeString(eventImage);
        dest.writeString(eventVenue);
        dest.writeStringList(eventParticipants);
        dest.writeStringList(eventCollaborators);
    }


    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public void setEventImage(String eventImageUri) {
        this.eventImage = eventImageUri;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public void setEventParticipants(ArrayList<String> eventParticipants) {
        this.eventParticipants = eventParticipants;
    }

    public void setEventCollaborators(ArrayList<String> eventCollaborators) {
        this.eventCollaborators = eventCollaborators;
    }


    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventImage() {
        return eventImage;
    }

    public String getEventVenue(){ return eventVenue;}

    public ArrayList<String> getEventParticipants() {
        return eventParticipants;
    }

    public ArrayList<String> getEventCollaborators() {
        return eventCollaborators;
    }


    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }
    };


}

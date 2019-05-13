package ml.oopscpp.interweb;

import java.util.ArrayList;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {

    private String eventTitle;
    private String eventDate;
    private String eventImageUri;
    private String eventVenue;

    public Event(Uri imageUri,String title,String date,String venue){
        eventImageUri = imageUri.toString();
        eventTitle = title;
        eventDate = date;
        eventVenue = venue;
    }

    public Event(Parcel source) {
        eventImageUri = source.readString();
        eventTitle = source.readString();
        eventDate = source.readString();
        eventVenue = source.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventImageUri);
        dest.writeString(eventTitle);
        dest.writeString(eventDate);
        dest.writeString(eventVenue);

    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventImage() {
        return eventImageUri;
    }

    public String getEventVenue(){ return eventVenue;}


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

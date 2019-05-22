package ml.oopscpp.interweb;

import android.os.Parcel;
import android.os.Parcelable;

public class Participant implements Parcelable {

    private String participantImage;
    private String participantName;
    private String participantContact;
    private String participantAffiliation;
    private String participantAge;
    private String participantAbout;

    Participant(){
        this.participantImage = "";
        this.participantName = "";
        this.participantContact = "";
        this.participantAffiliation = "";
        this.participantAge = "";
        this.participantAbout = "";
    }

    Participant(String image,String name){
        this.participantImage = image;
        this.participantAge = name;
    }

    Participant(String image,String name, String contact,String affiliation,String age, String about){
        this.participantImage = image;
        this.participantName = name;
        this.participantContact = contact;
        this.participantAffiliation = affiliation;
        this.participantAge = age;
        this.participantAbout = about;
    }

    public Participant(Parcel source){
        participantImage = source.readString();
        participantName = source.readString();
        participantContact = source.readString();
        participantAffiliation = source.readString();
        participantAge = source.readString();
        participantAbout = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(participantImage);
        dest.writeString(participantName);
        dest.writeString(participantContact);
        dest.writeString(participantAffiliation);
        dest.writeString(participantAge);
        dest.writeString(participantAbout);
    }



    public String getParticipantImage() {
        return participantImage;
    }

    public String getParticipantName() {
        return participantName;
    }

    public String getParticipantContact() {
        return participantContact;
    }

    public String getParticipantAffiliation() {
        return participantAffiliation;
    }

    public String getParticipantAge() {
        return participantAge;
    }

    public String getParticipantAbout() {
        return participantAbout;
    }


    public static final Creator<Participant> CREATOR = new Creator<Participant>() {
        @Override
        public Participant createFromParcel(Parcel in) {
            return new Participant(in);
        }

        @Override
        public Participant[] newArray(int size) {
            return new Participant[size];
        }
    };

}

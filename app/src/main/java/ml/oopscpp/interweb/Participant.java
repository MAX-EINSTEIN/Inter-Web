package ml.oopscpp.interweb;

public class Participant {

    private int participantImage;
    private String participantName;
    private String participantContact;
    private String participantAffiliation;
    private int participantAge;
    private String participantAbout;

    Participant(int image,String name, String contact){
        this.participantImage = image;
        this.participantName = name;
        this.participantContact = contact;
        this.participantAffiliation = "affiliation";
        this.participantAge = 20;
        this.participantAbout = "about";
    }

    Participant(int image,String name, String contact,String affiliation,int age, String about){
        this.participantImage = image;
        this.participantName = name;
        this.participantContact = contact;
        this.participantAffiliation = affiliation;
        this.participantAge = age;
        this.participantAbout = about;
    }

    public int getParticipantImage() {
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

    public int getParticipantAge() {
        return participantAge;
    }

    public String getParticipantAbout() {
        return participantAbout;
    }

}
